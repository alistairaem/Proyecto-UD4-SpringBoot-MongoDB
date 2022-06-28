package com.jetbrains.libraryservice.data;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class PrestamoData {
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fechaFin;

  private String listaLibros;

  public LocalDate getFechaFin() {
    return fechaFin;
  }

  private String dni;

  public void setFechaFin(LocalDate fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getListaLibros() {
    return listaLibros;
  }

  public void setListaLibros(String listaLibros) {
    this.listaLibros = listaLibros;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }
}
