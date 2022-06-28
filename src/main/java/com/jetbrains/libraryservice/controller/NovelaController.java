package com.jetbrains.libraryservice.controller;


import com.jetbrains.libraryservice.data.NovelaData;
import com.jetbrains.libraryservice.exception.NovelaNotFoundException;
import com.jetbrains.libraryservice.model.Novela;
import com.jetbrains.libraryservice.service.NovelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class NovelaController {

  @Autowired
  private NovelaService novelaService;

  @GetMapping("/novela/nueva")
  public String formNuevaNovela(@ModelAttribute NovelaData novelaData, Model model) {

    return "formNuevaNovela";
  }

  @PostMapping("/novela/nueva")
  public String nuevoNovela(@ModelAttribute NovelaData novelaData, Model model, RedirectAttributes flash) {
    if (novelaService.existeNovela(novelaData.getIsbn())) {
      flash.addFlashAttribute("mensaje", "Ya existe una novela con ese ISBN");
      return "formNuevaNovela";
    } else {
      novelaService.nuevaNovela(novelaData.getIsbn(), novelaData.getTitulo(), novelaData.getFechaPublicacion(), novelaData.getLongitudImpresion(),
              novelaData.getEditorial(), novelaData.getAutor(), novelaData.getTema(), novelaData.getSubgenero());
      flash.addFlashAttribute("mensaje", "Cliente creado con éxito");
      return "redirect:/novela/lista";
    }
  }

  @GetMapping("/novela/lista")
  public String listadoNovelas(Model model) {
    List<Novela> novelas = novelaService.allNovelas();
    model.addAttribute("novelas", novelas);
    return "listaNovelas";
  }

  @GetMapping("/novela/{id}/editar")
  public String formEditaNovela(@PathVariable(value = "id") String id, @ModelAttribute NovelaData novelaData,
                                Model model) {
    Novela novela = novelaService.findById(id);
    if (novela == null) {
      throw new NovelaNotFoundException("No existe una novela con id: " + id);
    }
    model.addAttribute("novela", novela);
    novelaData.setTitulo(novela.getTitulo());
    novelaData.setFechaPublicacion(novela.getFechaPublicacion());
    novelaData.setLongitudImpresion(novela.getLongitudImpresion());
    novelaData.setEditorial(novela.getEditorial());
    novelaData.setAutor(novela.getAutor());
    novelaData.setTema(novela.getTema());
    novelaData.setSubgenero(novela.getSubgenero());
    return "formEditarNovela";
  }

  @PostMapping("/novela/{id}/editar")
  public String editarNovela(@PathVariable(value = "id") String id, @ModelAttribute NovelaData novelaData,
                             Model model, RedirectAttributes flash) {
    Novela novela = novelaService.findById(id);
    if (novela == null) {
      throw new NovelaNotFoundException("No existe una novela con id: " + id);
    }
    novelaService.modificaNovela(id, novelaData.getTitulo(), novelaData.getFechaPublicacion(), novelaData.getLongitudImpresion(),
            novelaData.getEditorial(), novelaData.getAutor(), novelaData.getTema(), novelaData.getSubgenero());
    flash.addFlashAttribute("mensaje", "Novela modificada correctamente");
    return "redirect:/novela/lista";
  }

  @GetMapping("/novela/{id}/eliminar")
  public String formBorrarNovela(@PathVariable(value = "id") String id, @ModelAttribute NovelaData novelaData,
                                 Model model) {

    Novela novela = novelaService.findById(id);
    if (novela == null) {
      throw new NovelaNotFoundException("No existe una novela con id: " + id);
    }
    model.addAttribute("novela", novela);
    novelaData.setIsbn(novela.getIsbn());
    novelaData.setTitulo(novela.getTitulo());
    novelaData.setFechaPublicacion(novela.getFechaPublicacion());
    novelaData.setLongitudImpresion(novela.getLongitudImpresion());
    novelaData.setEditorial(novela.getEditorial());
    novelaData.setAutor(novela.getAutor());
    novelaData.setTema(novela.getTema());
    novelaData.setSubgenero(novela.getSubgenero());
    return "formEliminarNovela";
  }

  @PostMapping("/novela/{id}/eliminar")
  public String borrarNovela(@PathVariable(value = "id") String id, @ModelAttribute NovelaData novelaData,
                             Model model, RedirectAttributes flash) {
    Novela novela = novelaService.findById(id);
    if (novela == null) {
      throw new NovelaNotFoundException("No existe una novela con id: " + id);
    }

    novelaService.borraNovela(id);
    flash.addFlashAttribute("mensaje", "Novela eliminada correctamente");
    return "redirect:/novela/lista";
  }
}
