package com.jetbrains.libraryservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;


public class Cliente {

  @Id
  private String idCliente;
  private String nombre;
  private String apellido;

  @Indexed(unique=true, background = true)
  private String dni;
  public String direccion;
  private int edad;

  ArrayList<String> listaPrestamos;


  public Cliente(String nombre, String apellido, String dni, String direccion, int edad) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.dni = dni;
    this.direccion = direccion;
    this.edad = edad;
    this.listaPrestamos = new ArrayList<>();
  }

  public String getIdCliente() {
    return idCliente;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public int getEdad() {
    return edad;
  }

  public void setEdad(int edad) {
    this.edad = edad;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public ArrayList<String> getListaPrestamos() {
    return listaPrestamos;
  }

  public void addListaPrestamos(String idPrestamo) {
    this.listaPrestamos.add(idPrestamo);
  }

  @Override
  public String toString() {
    return "Cliente{" +
            "idCliente=" + idCliente +
            ", nombre='" + nombre + '\'' +
            ", apellido='" + apellido + '\'' +
            ", dni='" + dni + '\'' +
            ", direccion='" + direccion + '\'' +
            ", edad=" + edad +
            '}';
  }
}
