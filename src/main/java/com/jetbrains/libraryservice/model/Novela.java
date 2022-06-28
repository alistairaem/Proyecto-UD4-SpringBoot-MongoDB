package com.jetbrains.libraryservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Novela {
  @Id
  private String idNovela;
  private String isbn;
  private String titulo;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fechaPublicacion;
  private int LongitudImpresion;
  private String editorial;
  private String autor;
  private String tema;
  private String subgenero;

  public Novela() {
  }

  public Novela(String isbn, String titulo, LocalDate fechaPublicacion, int longitudImpresion,
                String editorial, String autor, String tema, String subgenero) {
    this.isbn = isbn;
    this.titulo = titulo;
    this.fechaPublicacion = fechaPublicacion;
    LongitudImpresion = longitudImpresion;
    this.editorial = editorial;
    this.autor = autor;
    this.tema = tema;
    this.subgenero = subgenero;
  }

  public String getIdNovela() {
    return idNovela;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public LocalDate getFechaPublicacion() {
    return fechaPublicacion;
  }

  public void setFechaPublicacion(LocalDate fechaPublicacion) {
    this.fechaPublicacion = fechaPublicacion;
  }

  public int getLongitudImpresion() {
    return LongitudImpresion;
  }

  public void setLongitudImpresion(int longitudImpresion) {
    LongitudImpresion = longitudImpresion;
  }

  public String getEditorial() {
    return editorial;
  }

  public void setEditorial(String editorial) {
    this.editorial = editorial;
  }

  public String getAutor() {
    return autor;
  }

  public void setAutor(String autor) {
    this.autor = autor;
  }

  public String getTema() {
    return tema;
  }

  public void setTema(String tema) {
    this.tema = tema;
  }

  public String getSubgenero() {
    return subgenero;
  }

  public void setSubgenero(String subgenero) {
    this.subgenero = subgenero;
  }
}
