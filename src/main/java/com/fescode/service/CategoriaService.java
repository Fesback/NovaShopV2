package com.fescode.service;

import com.fescode.dto.request.CategoriaRequestDTO;
import com.fescode.dto.response.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {
    CategoriaResponseDTO crearCategoria(CategoriaRequestDTO request);
    List<CategoriaResponseDTO> listarCategoriasActivas();
    CategoriaResponseDTO obtenerCategoriaActiva(Long id);
    CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO request);
    void desactivarCategoria(Long id);
}
