package com.mrtndls.talentotech.exceptions;

public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException() {
        super("Stock insuficiente para realizar el pedido.");
    }

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
