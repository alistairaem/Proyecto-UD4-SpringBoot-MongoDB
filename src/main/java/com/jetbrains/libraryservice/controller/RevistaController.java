package com.jetbrains.libraryservice.controller;

import com.jetbrains.libraryservice.data.RevistaData;
import com.jetbrains.libraryservice.exception.RevistaNotFoundException;
import com.jetbrains.libraryservice.model.Revista;
import com.jetbrains.libraryservice.service.RevistaService;
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
public class RevistaController {

  @Autowired
  private RevistaService revistaService;

  @GetMapping("/revista/nueva")
  public String formNuevaRevista(@ModelAttribute RevistaData revistaData, Model model) {

    return "formNuevaRevista";
  }

  @PostMapping("/revista/nueva")
  public String nuevaRevista(@ModelAttribute RevistaData revistaData, Model model, RedirectAttributes flash) {
    if (revistaService.existeRevista(revistaData.getIsbn())) {
      flash.addFlashAttribute("mensaje", "Ya existe una revista con ese ISBN");
      return "formNuevaRevista";
    } else {
      revistaService.nuevaRevista(revistaData.getIsbn(), revistaData.getTitulo(), revistaData.getFechaPublicacion(), revistaData.getLongitudImpresion(),
              revistaData.getEditorial(), revistaData.getTipo());
      flash.addFlashAttribute("mensaje", "Revista creado con éxito");
      return "redirect:/revista/lista";
    }
  }

  @GetMapping("/revista/lista")
  public String listadoNovelas(Model model) {
    List<Revista> revistas = revistaService.allRevistas();
    model.addAttribute("revistas", revistas);
    return "listaRevistas";
  }

  @GetMapping("/revista/{id}/editar")
  public String formEditaNovela(@PathVariable(value = "id") String id, @ModelAttribute RevistaData revistaData,
                                Model model) {
    Revista revista = revistaService.findById(id);
    if (revista == null) {
      throw new RevistaNotFoundException("No existe una revista con id: " + id);
    }
    model.addAttribute("revista", revista);
    revistaData.setTitulo(revista.getTitulo());
    revistaData.setFechaPublicacion(revista.getFechaPublicacion());
    revistaData.setLongitudImpresion(revista.getLongitudImpresion());
    revistaData.setEditorial(revista.getEditorial());
    revistaData.setTipo(revista.getTipo());
    return "formEditarRevista";
  }

  @PostMapping("/revista/{id}/editar")
  public String editarRevista(@PathVariable(value = "id") String id, @ModelAttribute RevistaData revistaData,
                              Model model, RedirectAttributes flash) {
    Revista revista = revistaService.findById(id);
    if (revista == null) {
      throw new RevistaNotFoundException("No existe una revista con id: " + id);
    }
    revistaService.modificaRevista(id, revistaData.getTitulo(), revistaData.getFechaPublicacion(), revistaData.getLongitudImpresion(),
            revistaData.getEditorial(), revistaData.getTipo());
    flash.addFlashAttribute("mensaje", "revista modificada correctamente");
    return "redirect:/revista/lista";
  }

  @GetMapping("/revista/{id}/eliminar")
  public String formBorrarRevista(@PathVariable(value = "id") String id, @ModelAttribute RevistaData revistaData,
                                  Model model) {

    Revista revista = revistaService.findById(id);
    if (revista == null) {
      throw new RevistaNotFoundException("No existe una revista con id: " + id);
    }
    model.addAttribute("revista", revista);
    revistaData.setIsbn(revista.getIsbn());
    revistaData.setTitulo(revista.getTitulo());
    revistaData.setFechaPublicacion(revista.getFechaPublicacion());
    revistaData.setLongitudImpresion(revista.getLongitudImpresion());
    revistaData.setEditorial(revista.getEditorial());
    revistaData.setTipo(revista.getTipo());
    return "formEliminarRevista";
  }

  @PostMapping("/revista/{id}/eliminar")
  public String borrarRevista(@PathVariable(value = "id") String id, @ModelAttribute RevistaData revistaData,
                              Model model, RedirectAttributes flash) {
    Revista revista = revistaService.findById(id);
    if (revista == null) {
      throw new RevistaNotFoundException("No existe una revista con id: " + id);
    }

    revistaService.borraRevista(id);
    flash.addFlashAttribute("mensaje", "revista eliminada correctamente");
    return "redirect:/revista/lista";
  }
}
