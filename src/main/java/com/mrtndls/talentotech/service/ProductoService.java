package com.mrtndls.talentotech.service;

import java.util.List;

import com.mrtndls.talentotech.model.Producto;

public interface ProductoService {

    List<Producto> obtenerTodos();

    Producto obtenerPorId(int id);

    Producto guardar(Producto producto);

    Producto actualizar(int id, Producto producto);

    void eliminar(int id);

    List<Producto> buscarPorNombre(String nombre);

    List<Producto> obtenerConStockMenorA(int umbral);

}
