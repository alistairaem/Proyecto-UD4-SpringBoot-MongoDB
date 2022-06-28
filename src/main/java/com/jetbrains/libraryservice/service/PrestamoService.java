package com.jetbrains.libraryservice.service;


import com.jetbrains.libraryservice.model.Prestamo;
import com.jetbrains.libraryservice.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {


  private PrestamoRepository prestamoRepository;

  @Autowired
  public PrestamoService(PrestamoRepository prestamoRepository) {
    this.prestamoRepository = prestamoRepository;
  }
  public void nuevoPrestamo(Prestamo prestamo) {
    prestamoRepository.save(prestamo);
  }

  @Transactional(readOnly = true)
  public List<Prestamo> allPrestamos() {
    List<Prestamo> prestamos = new ArrayList<>();
    prestamoRepository.findAll().forEach(prestamos::add);
    return prestamos;
  }

  @Transactional(readOnly = true)
  public Prestamo findById(String id) {
    return prestamoRepository.findById(id).orElse(null);
  }

  public List<Prestamo> prestamosSemana() {
    List<Prestamo> prestamos;
    prestamos = prestamoRepository.findByFechaDevolucion(LocalDate.now(), LocalDate.now().plusDays(7));
    return prestamos;
  }

  public List<Prestamo> prestamosSinDevolver() {
    List<Prestamo> prestamos;
    prestamos = prestamoRepository.findByNoDevuelto();
    return prestamos;
  }
}
