package com.jetbrains.libraryservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;

public class Prestamo {
  @Id
  private String idPrestamo;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fechaInicio;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fechaFin;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fechaDevolucion;
  private Boolean devuelto;

  private ArrayList<String> listaRevistas;
  private ArrayList<String> listaMangas;
  private ArrayList<String> listaNovelas;


  public Prestamo(LocalDate fechaFin) {
    this.fechaFin = fechaFin;
    listaRevistas = new ArrayList<String>();
    listaMangas = new ArrayList<String>();
    listaNovelas = new ArrayList<String>();

  }

  public String getIdPrestamo() {
    return idPrestamo;
  }

  public LocalDate getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(LocalDate fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public LocalDate getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(LocalDate fechaFin) {
    this.fechaFin = fechaFin;
  }

  public Boolean getDevuelto() {
    return devuelto;
  }

  public void setDevuelto(Boolean devuelto) {
    this.devuelto = devuelto;
  }

  public LocalDate getFechaDevolucion() {
    return fechaDevolucion;
  }

  public void setFechaDevolucion(LocalDate fechaDevolucion) {
    this.fechaDevolucion = fechaDevolucion;
  }

  public ArrayList<String> getListaRevistas() {
    return listaRevistas;
  }

  public ArrayList<String> getListaMangas() {
    return listaMangas;
  }

  public ArrayList<String> getListaNovelas() {
    return listaNovelas;
  }

  public void addListaRevistas(String idRevista) {
    this.listaRevistas.add(idRevista);
  }

  public void addListaMangas(String idManga) {
    this.listaMangas.add(idManga);
  }

  public void addListaNovelas(String idNovela) {
    this.listaNovelas.add(idNovela);
  }


  @Override
  public String toString() {
    return "Prestamo{" +
            "idPrestamo=" + idPrestamo +
            ", fechaInicio=" + fechaInicio +
            ", fechaFin=" + fechaFin +
            ", fechaDevolucion=" + fechaDevolucion +
            ", devuelto=" + devuelto +
            '}';
  }
}
