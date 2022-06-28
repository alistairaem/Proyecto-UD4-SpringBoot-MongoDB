package com.jetbrains.libraryservice.controller;

import com.jetbrains.libraryservice.data.MangaData;
import com.jetbrains.libraryservice.exception.MangaNotFoundException;
import com.jetbrains.libraryservice.model.Manga;
import com.jetbrains.libraryservice.service.MangaService;
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
public class MangaController {
  @Autowired
  private MangaService mangaService;

  @GetMapping("/manga/nuevo")
  public String formNuevoManga(@ModelAttribute MangaData mangaData, Model model) {

    return "formNuevoManga";
  }

  @PostMapping("/manga/nuevo")
  public String nuevoManga(@ModelAttribute MangaData mangaData, Model model, RedirectAttributes flash) {
    if (mangaService.existeManga(mangaData.getIsbn())) {
      flash.addFlashAttribute("mensaje", "Ya existe un manga con ese ISBN");
      return "redirect:/manga/nuevo";
    } else {
      mangaService.nuevoManga(mangaData.getIsbn(), mangaData.getTitulo(), mangaData.getFechaPublicacion(), mangaData.getLongitudImpresion(),
              mangaData.getEditorial(), mangaData.getAutor(), mangaData.getDemografia(), mangaData.getGenero(), mangaData.isColor());
      flash.addFlashAttribute("mensaje", "Manga creado con éxito");
      return "redirect:/manga/lista";
    }
  }

  @GetMapping("/manga/lista")
  public String listadoMangas(Model model) {
    List<Manga> mangas = mangaService.allMangas();
    model.addAttribute("mangas", mangas);
    return "listaMangas";
  }

  @GetMapping("/manga/{id}/editar")
  public String formEditaManga(@PathVariable(value = "id") String id, @ModelAttribute MangaData mangaData,
                               Model model) {
    Manga manga = mangaService.findById(id);
    if (manga == null) {
      throw new MangaNotFoundException("No existe un manga con id: " + id);
    }
    model.addAttribute("manga", manga);
    mangaData.setTitulo(manga.getTitulo());
    mangaData.setFechaPublicacion(manga.getFechaPublicacion());
    mangaData.setLongitudImpresion(manga.getLongitudImpresion());
    mangaData.setEditorial(manga.getEditorial());
    mangaData.setAutor(manga.getAutor());
    mangaData.setDemografia(manga.getDemografia());
    mangaData.setGenero(manga.getGenero());
    mangaData.setColor(manga.isColor());
    return "formEditarManga";
  }

  @PostMapping("/manga/{id}/editar")
  public String editarManga(@PathVariable(value = "id") String id, @ModelAttribute MangaData mangaData,
                            Model model, RedirectAttributes flash) {
    Manga manga = mangaService.findById(id);
    if (manga == null) {
      throw new MangaNotFoundException("No existe un manga con id: " + id);
    }
    mangaService.modificaManga(id, mangaData.getTitulo(), mangaData.getFechaPublicacion(), mangaData.getLongitudImpresion(),
            mangaData.getEditorial(), mangaData.getAutor(), mangaData.getDemografia(), mangaData.getGenero(), mangaData.isColor());
    flash.addFlashAttribute("mensaje", "Manga modificado correctamente");
    return "redirect:/manga/lista";
  }

  @GetMapping("/manga/{id}/eliminar")
  public String formBorrarManga(@PathVariable(value = "id") String id, @ModelAttribute MangaData mangaData,
                                Model model) {

    Manga manga = mangaService.findById(id);
    if (manga == null) {
      throw new MangaNotFoundException("No existe una novela con id: " + id);
    }
    model.addAttribute("manga", manga);
    mangaData.setIsbn(manga.getIsbn());
    mangaData.setTitulo(manga.getTitulo());
    mangaData.setFechaPublicacion(manga.getFechaPublicacion());
    mangaData.setLongitudImpresion(manga.getLongitudImpresion());
    mangaData.setEditorial(manga.getEditorial());
    mangaData.setAutor(manga.getAutor());
    mangaData.setDemografia(manga.getDemografia());
    mangaData.setGenero(manga.getGenero());
    mangaData.setColor(manga.isColor());
    return "formEliminarManga";
  }

  @PostMapping("/manga/{id}/eliminar")
  public String borrarManga(@PathVariable(value = "id") String id, @ModelAttribute MangaData mangaData,
                            Model model, RedirectAttributes flash) {
    Manga manga = mangaService.findById(id);
    if (manga == null) {
      throw new MangaNotFoundException("No existe una novela con id: " + id);
    }

    mangaService.borraManga(id);
    flash.addFlashAttribute("mensaje", "Manga eliminado correctamente");
    return "redirect:/manga/lista";
  }
}
