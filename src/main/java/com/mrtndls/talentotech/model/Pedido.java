package com.mrtndls.talentotech.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id") // FK en tabla linea_pedido
    private List<LineaPedido> lineasPedido;

    private LocalDate fecha;

    private double total;

    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id") // FK en tabla pedido
    private Usuario usuario;  // <-- Agregás el usuario que hizo el pedido
}
