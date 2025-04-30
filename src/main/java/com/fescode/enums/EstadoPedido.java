package com.fescode.enums;

public enum EstadoPedido {
    PENDIENTE,
    PROCESANDO,
    ENVIADO,
    ENTREGADO,
    CANCELADO;

     // Metodo para validar transiciones de estado
    public static boolean validarTransicion(EstadoPedido estadoActual, EstadoPedido nuevoEstado) {
        return switch (estadoActual) {
            case PENDIENTE -> nuevoEstado == PROCESANDO || nuevoEstado == CANCELADO;
            case PROCESANDO -> nuevoEstado == ENVIADO;
            case ENVIADO -> nuevoEstado == ENTREGADO;
            default -> false; // Estados ENTREGADO y CANCELADO son finales
        };
    }
}
