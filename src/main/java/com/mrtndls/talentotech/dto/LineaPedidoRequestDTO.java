package com.mrtndls.talentotech.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineaPedidoRequestDTO {

    private int productoId;
    private int cantidad;
}
