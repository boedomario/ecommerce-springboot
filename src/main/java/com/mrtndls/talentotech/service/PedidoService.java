package com.mrtndls.talentotech.service;

import java.util.List;

import com.mrtndls.talentotech.dto.LineaPedidoRequestDTO;
import com.mrtndls.talentotech.model.EstadoPedido;
import com.mrtndls.talentotech.model.Pedido;

public interface PedidoService {

    Pedido crearPedido(List<LineaPedidoRequestDTO> productos, int usuarioId);

    List<Pedido> obtenerPedidosPorUsuario(int usuarioId);

    Pedido actualizarEstado(int pedidoId, EstadoPedido nuevoEstado);

}
