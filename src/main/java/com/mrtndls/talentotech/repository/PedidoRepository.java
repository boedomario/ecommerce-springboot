package com.mrtndls.talentotech.repository;

import com.mrtndls.talentotech.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuarioId(int usuarioId);
}
