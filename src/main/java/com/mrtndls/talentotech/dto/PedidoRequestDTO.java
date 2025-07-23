package com.mrtndls.talentotech.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PedidoRequestDTO {

    private int usuarioId;
    private List<LineaPedidoRequestDTO> productos;
}
