package com.mrtndls.talentotech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrtndls.talentotech.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

  // buscar por nmobre
  List<Producto> findByNombreContainingIgnoreCase(String nombre);

  List<Producto> findByStockLessThan(int umbral);

}
