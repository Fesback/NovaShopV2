package com.fescode.mapper;

import com.fescode.dto.request.CategoriaRequestDTO;
import com.fescode.dto.response.CategoriaResponseDTO;
import com.fescode.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return CategoriaResponseDTO.builder()
            .id(categoria.getId())
            .nombre(categoria.getNombreCategoria())
            .descripcion(categoria.getDescripcion())
            .imagen(categoria.getImagen())
            .activo(categoria.isActivo())
            .build();
    }
    public Categoria toEntity(CategoriaRequestDTO dto) {
        return Categoria.builder()
            .nombreCategoria(dto.getNombre())
            .descripcion(dto.getDescripcion())
            .imagen(dto.getImagen())
            .activo(true)
            .build();
    }
}
