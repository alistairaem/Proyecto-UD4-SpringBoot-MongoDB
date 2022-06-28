package com.jetbrains.libraryservice.controller;


import com.jetbrains.libraryservice.data.PrestamoData;
import com.jetbrains.libraryservice.model.*;
import com.jetbrains.libraryservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PrestamoController {
  @Autowired
  private PrestamoService prestamoService;

  @Autowired
  private RevistaService revistaService;

  @Autowired
  private MangaService mangaService;

  @Autowired
  private NovelaService novelaService;

  @Autowired
  private ClienteService clienteService;

  @GetMapping("/prestamo/nuevo")
  public String formNuevoPrestamo(@ModelAttribute PrestamoData prestamoData, Model model) {

    return "formNuevoPrestamo";
  }

  @PostMapping("/prestamo/nuevo")
  public String nuevoPrestamo(@ModelAttribute PrestamoData prestamoData, Model model, RedirectAttributes flash) {
    if (prestamoData.getListaLibros() == null) {
      flash.addFlashAttribute("error", "Debe seleccionar al menos un libro");
      return "redirect:/prestamo/nuevo";
    }else{
      Prestamo prestamo = new Prestamo(prestamoData.getFechaFin());
      String[] libros = prestamoData.getListaLibros().split(",");
      for (String libro : libros) {
        if (novelaService.existeNovela(libro)) {
          Novela novela = novelaService.findByIsbn(libro);
          prestamo.addListaNovelas(novela.getIdNovela());
        } else if (revistaService.existeRevista(libro)) {
          Revista revista = revistaService.findByIsbn(libro);
          prestamo.addListaRevistas(revista.getIdRevista());
        } else if (mangaService.existeManga(libro)) {
          Manga manga = mangaService.findByIsbn(libro);
          prestamo.addListaMangas(manga.getidManga());
        }
      }
      if (prestamo.getListaMangas().size() == 0 && prestamo.getListaNovelas().size() == 0 && prestamo.getListaRevistas().size() == 0) {
        flash.addFlashAttribute("error", "No existe ningun libro con ese ISBN");
        return "redirect:/prestamo/nuevo";
      }
      prestamo.setDevuelto(false);
      prestamo.setFechaInicio(LocalDate.now());
      prestamo.setFechaFin(LocalDate.now().plusDays(15));
      prestamoService.nuevoPrestamo(prestamo);
      Cliente cliente = clienteService.findByDni(prestamoData.getDni());
      if (cliente != null) {
        cliente.addListaPrestamos(prestamo.getIdPrestamo());
        clienteService.añadePrestamo(cliente);
        flash.addFlashAttribute("mensaje", "Prestamo creado con éxito");
        return "redirect:/prestamo/lista";
      }else{
        flash.addFlashAttribute("error", "No existe ningun cliente con ese dni");
        return "redirect:/prestamo/nuevo";
      }
    }
  }

  @GetMapping("/prestamo/lista")
  public String listadoPrestamos(Model model) {
    List<Prestamo> prestamos = prestamoService.allPrestamos();
    for (int i = 0; i < prestamos.size(); i++) {
      if (prestamos.get(i).getListaNovelas().size() > 0) {
        for (int j = 0; j < prestamos.get(i).getListaNovelas().size(); j++) {
          prestamos.get(i).getListaNovelas().set(j, novelaService.findByIdNovela(prestamos.get(i).getListaNovelas().get(j)).getTitulo());
        }
      }
      if (prestamos.get(i).getListaRevistas().size() > 0) {
        for (int j = 0; j < prestamos.get(i).getListaRevistas().size(); j++) {
          prestamos.get(i).getListaRevistas().set(j, revistaService.findByIdRevista(prestamos.get(i).getListaRevistas().get(j)).getTitulo());
        }
      }
      if (prestamos.get(i).getListaMangas().size() > 0) {
        for (int j = 0; j < prestamos.get(i).getListaMangas().size(); j++) {
          prestamos.get(i).getListaMangas().set(j, mangaService.findByIdManga(prestamos.get(i).getListaMangas().get(j)).getTitulo());
        }
      }
    }
    model.addAttribute("prestamos", prestamos);
    return "listaPrestamos";
  }

  @GetMapping("/prestamo/lista/semana")
  public String listadoPrestamosSemana(Model model) {
    List<Prestamo> prestamos = prestamoService.prestamosSemana();
    for (int i = 0; i < prestamos.size(); i++) {
      if (prestamos.get(i).getListaNovelas().size() > 0) {
        for (int j = 0; j < prestamos.get(i).getListaNovelas().size(); j++) {
          prestamos.get(i).getListaNovelas().set(j, novelaService.findByIdNovela(prestamos.get(i).getListaNovelas().get(j)).getTitulo());
        }
      }
      if (prestamos.get(i).getListaRevistas().size() > 0) {
        for (int j = 0; j < prestamos.get(i).getListaRevistas().size(); j++) {
          prestamos.get(i).getListaRevistas().set(j, revistaService.findByIdRevista(prestamos.get(i).getListaRevistas().get(j)).getTitulo());
        }
      }
      if (prestamos.get(i).getListaMangas().size() > 0) {
        for (int j = 0; j < prestamos.get(i).getListaMangas().size(); j++) {
          prestamos.get(i).getListaMangas().set(j, mangaService.findByIdManga(prestamos.get(i).getListaMangas().get(j)).getTitulo());
        }
      }
    }
    model.addAttribute("prestamos", prestamos);
    return "listaPrestamosSemana";
  }

  @GetMapping("/prestamo/lista/sindevolver")
  public String listadoPrestamosSinDevolver(Model model) {
    List<Prestamo> prestamos = prestamoService.prestamosSinDevolver();
    for (int i = 0; i < prestamos.size(); i++) {
      if (prestamos.get(i).getListaNovelas().size() > 0) {
        for (int j = 0; j < prestamos.get(i).getListaNovelas().size(); j++) {
          prestamos.get(i).getListaNovelas().set(j, novelaService.findByIdNovela(prestamos.get(i).getListaNovelas().get(j)).getTitulo());
        }
      }
      if (prestamos.get(i).getListaRevistas().size() > 0) {
        for (int j = 0; j < prestamos.get(i).getListaRevistas().size(); j++) {
          prestamos.get(i).getListaRevistas().set(j, revistaService.findByIdRevista(prestamos.get(i).getListaRevistas().get(j)).getTitulo());
        }
      }
      if (prestamos.get(i).getListaMangas().size() > 0) {
        for (int j = 0; j < prestamos.get(i).getListaMangas().size(); j++) {
          prestamos.get(i).getListaMangas().set(j, mangaService.findByIdManga(prestamos.get(i).getListaMangas().get(j)).getTitulo());
        }
      }
    }
    model.addAttribute("prestamos", prestamos);
    return "listaPrestamosSinDevolver";
  }
}
