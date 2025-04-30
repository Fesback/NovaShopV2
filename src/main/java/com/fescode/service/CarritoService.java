package com.fescode.service;

import com.fescode.dto.request.CarritoItemRequestDTO;
import com.fescode.dto.response.CarritoResponseDTO;

import java.math.BigDecimal;

public interface CarritoService {
    CarritoResponseDTO obtenerCarritoCompleto();
    CarritoResponseDTO agregarItem(CarritoItemRequestDTO requestDTO);
    CarritoResponseDTO actualizarItem(Long idProducto, Integer cantidad);
    void eliminarItem(Long idProducto);
    void eliminarItemPorId(Long idItem);
    void vaciarCarrito();
    BigDecimal calcularTotalCarrito();
}
