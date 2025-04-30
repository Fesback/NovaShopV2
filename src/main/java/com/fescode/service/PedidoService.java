package com.fescode.service;

import com.fescode.dto.response.PedidoResponseDTO;
import com.fescode.enums.EstadoPedido;

import java.util.List;

public interface PedidoService {
    PedidoResponseDTO crearPedidoDesdeCarrito(String direccionEnvio);
    List<PedidoResponseDTO> obtenerHistorialUsuario();
    List<PedidoResponseDTO> obtenerTodosPedidos();
    void actualizarEstadoPedido(Long idPedido, EstadoPedido nuevoEstado);
}
