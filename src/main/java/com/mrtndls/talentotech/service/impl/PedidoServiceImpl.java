package com.mrtndls.talentotech.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mrtndls.talentotech.dto.LineaPedidoRequestDTO;
import com.mrtndls.talentotech.exceptions.StockInsuficienteException;
import com.mrtndls.talentotech.model.EstadoPedido;
import com.mrtndls.talentotech.model.LineaPedido;
import com.mrtndls.talentotech.model.Pedido;
import com.mrtndls.talentotech.model.Producto;
import com.mrtndls.talentotech.model.Usuario;
import com.mrtndls.talentotech.repository.PedidoRepository;
import com.mrtndls.talentotech.repository.ProductoRepository;
import com.mrtndls.talentotech.repository.UsuarioRepository;
import com.mrtndls.talentotech.service.PedidoService;

import jakarta.transaction.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public Pedido crearPedido(List<LineaPedidoRequestDTO> productos, int usuarioId) {
        List<LineaPedido> lineas = new ArrayList<>();
        double total = 0.0;

        for (LineaPedidoRequestDTO req : productos) {
            Producto producto = productoRepository.findById(req.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + req.getProductoId()));

            if (producto.getStock() < req.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - req.getCantidad());
            productoRepository.save(producto);

            LineaPedido linea = new LineaPedido();
            linea.setProducto(producto);
            linea.setCantidad(req.getCantidad());
            double subtotal = producto.getPrecio() * req.getCantidad();
            linea.setSubTotal(subtotal);

            lineas.add(linea);
            total += subtotal;
        }

        Pedido pedido = new Pedido();
        // asignar usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
        pedido.setUsuario(usuario);

        pedido.setLineasPedido(lineas);
        pedido.setTotal(total);
        pedido.setFecha(LocalDate.now());
        pedido.setEstado(EstadoPedido.PENDIENTE);

        return pedidoRepository.save(pedido);
    }

    @Override
    public List<Pedido> obtenerPedidosPorUsuario(int usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    @Override
    @Transactional
    public Pedido actualizarEstado(int pedidoId, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + pedidoId));

        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }

}
