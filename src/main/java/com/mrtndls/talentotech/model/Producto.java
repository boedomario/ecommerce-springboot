package com.mrtndls.talentotech.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
  @Id
  @GeneratedValue
  private int id;
  private String nombre;
  private String descripcion;
  private double precio;
  private String categoria;
  private String imagenUrl;
  private int stock;

  
}
