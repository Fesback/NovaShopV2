package com.fescode.service;

import com.fescode.dto.request.ProductoRequestDTO;
import com.fescode.dto.response.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {
    ProductoResponseDTO crear(ProductoRequestDTO requestDTO);
    List<ProductoResponseDTO> listarTodos();
    ProductoResponseDTO buscarPorId(Long idProducto);
    ProductoResponseDTO actualizar(Long idProducto, ProductoRequestDTO requestDTO);
    void desactivar(Long idProducto);
}
