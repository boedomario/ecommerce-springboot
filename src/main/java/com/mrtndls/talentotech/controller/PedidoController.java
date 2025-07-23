package com.mrtndls.talentotech.controller;

import com.mrtndls.talentotech.dto.PedidoRequestDTO;
import com.mrtndls.talentotech.model.EstadoPedido;
import com.mrtndls.talentotech.model.Pedido;
import com.mrtndls.talentotech.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/pedidos")
    public Pedido crearPedido(@RequestBody PedidoRequestDTO request) {
        return pedidoService.crearPedido(request.getProductos(), request.getUsuarioId());
    }

    @GetMapping("/usuarios/{usuarioId}/pedidos")
    public List<Pedido> obtenerPedidosUsuario(@PathVariable int usuarioId) {
        return pedidoService.obtenerPedidosPorUsuario(usuarioId);
    }

    @PutMapping("/pedidos/{id}/estado")
    public Pedido actualizarEstado(
            @PathVariable int id,
            @RequestBody EstadoPedido nuevoEstado) {
        return pedidoService.actualizarEstado(id, nuevoEstado);
    }

}
