package com.jetbrains.libraryservice.controller;

import com.jetbrains.libraryservice.data.ClienteData;
import com.jetbrains.libraryservice.exception.ClienteNotFoundException;
import com.jetbrains.libraryservice.model.Cliente;
import com.jetbrains.libraryservice.model.Prestamo;
import com.jetbrains.libraryservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClienteController {
  @Autowired
  private ClienteService clienteService;

  @Autowired
  private PrestamoService prestamoService;

  @Autowired
  private RevistaService revistaService;

  @Autowired
  private MangaService mangaService;

  @Autowired
  private NovelaService novelaService;

  @GetMapping("/cliente/nuevo")
  public String formNuevoCliente(@ModelAttribute ClienteData clienteData, Model model) {

    return "formNuevoCliente";
  }

  @PostMapping("/cliente/nuevo")
  public String nuevoCliente(@ModelAttribute ClienteData clienteData, Model model, RedirectAttributes flash) {
    if (clienteService.existeCliente(clienteData.getDni())) {
      flash.addFlashAttribute("mensaje", "Ya existe un cliente con ese DNI");
      return "formNuevoCliente";
    } else {
      clienteService.nuevoCliente(clienteData.getNombre(), clienteData.getApellido(), clienteData.getDni(),
              clienteData.getDireccion(), clienteData.getEdad());
      flash.addFlashAttribute("mensaje", "Cliente creado con éxito");
      return "redirect:/cliente/lista";
    }

  }

  @GetMapping("/cliente/lista")
  public String listadoClientes(Model model) {
    List<Cliente> clientes = clienteService.allClientes();
    model.addAttribute("clientes", clientes);
    return "listaClientes";
  }

  @GetMapping("/cliente/{id}/editar")
  public String formEditaCliente(@PathVariable(value = "id") String id, @ModelAttribute ClienteData clienteData,
                                 Model model) {

    Cliente cliente = clienteService.findById(id);

    if (cliente == null) {
      throw new ClienteNotFoundException("No existe un cliente con id: " + id);
    }

    model.addAttribute("cliente", cliente);
    clienteData.setNombre(cliente.getNombre());
    clienteData.setApellido(cliente.getApellido());
    clienteData.setDireccion(cliente.getDireccion());
    clienteData.setEdad(cliente.getEdad());
    return "formEditarCliente";
  }

  @GetMapping("/cliente/{id}/prestamos")
  public String formPrestamosCliente(@PathVariable(value = "id") String id, @ModelAttribute ClienteData clienteData,
                                     Model model) {

    Cliente cliente = clienteService.findById(id);

    if (cliente == null) {
      throw new ClienteNotFoundException("No existe un cliente con id: " + id);
    }
    ArrayList<Prestamo> prestamos = new ArrayList<>();
    for (int i = 0; i < cliente.getListaPrestamos().size(); i++) {
      prestamos.add(prestamoService.findById(cliente.getListaPrestamos().get(i)));
    }
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
    model.addAttribute("prestamo", prestamos);

    return "listaPrestamosCliente";
  }

  @PostMapping("/cliente/{id}/editar")
  public String editarCliente(@PathVariable(value = "id") String id, @ModelAttribute ClienteData clienteData,
                              Model model, RedirectAttributes flash) {
    Cliente cliente = clienteService.findById(id);
    if (cliente == null) {
      throw new ClienteNotFoundException("No existe un cliente con id: " + id);
    }

    clienteService.modificaCliente(id, clienteData.getNombre(), clienteData.getApellido(), clienteData.getDireccion(), clienteData.getEdad());
    flash.addFlashAttribute("mensaje", "Cliente modificado correctamente");
    return "redirect:/cliente/lista";
  }

  @GetMapping("/cliente/{id}/eliminar")
  public String formBorrarCliente(@PathVariable(value = "id") String id, @ModelAttribute ClienteData clienteData,
                                  Model model) {

    Cliente cliente = clienteService.findById(id);
    if (cliente == null) {
      throw new ClienteNotFoundException("No existe un cliente con id: " + id);
    }

    model.addAttribute("cliente", cliente);
    clienteData.setNombre(cliente.getNombre());
    clienteData.setApellido(cliente.getApellido());
    clienteData.setDireccion(cliente.getDireccion());
    clienteData.setDni(cliente.getDni());
    clienteData.setEdad(cliente.getEdad());
    return "formEliminarCliente";
  }

  @PostMapping("/cliente/{id}/eliminar")
  public String borrarCliente(@PathVariable(value = "id") String id, @ModelAttribute ClienteData clienteData,
                              Model model, RedirectAttributes flash) {
    Cliente cliente = clienteService.findById(id);
    if (cliente == null) {
      throw new ClienteNotFoundException("No existe un cliente con id: " + id);
    }

    clienteService.borraCliente(id);
    flash.addFlashAttribute("mensaje", "Cliente eliminado correctamente");
    return "redirect:/cliente/lista";
  }


}
