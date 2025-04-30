package com.fescode.mapper;

import com.fescode.dto.request.ProductoRequestDTO;
import com.fescode.dto.response.ProductoResponseDTO;
import com.fescode.entity.Producto;
import com.fescode.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductoMapper {

    private final CategoriaRepository categoriaRepository;

    public Producto toEntity(ProductoRequestDTO requestDTO) {
        Producto producto = new Producto();
        producto.setNombre(requestDTO.getNombre());
        producto.setDescripcion(requestDTO.getDescripcion());
        producto.setPrecio(requestDTO.getPrecio());
        producto.setStock(requestDTO.getStock());
        producto.setImagen(requestDTO.getImagen());

        if(requestDTO.getIdCategoria() != null) {
            producto.setCategoria(categoriaRepository.findById(requestDTO.getIdCategoria())
                    .orElse(null));
        }
        return producto;

    }

    public ProductoResponseDTO toResponse(Producto producto) {
        ProductoResponseDTO responseDTO = new ProductoResponseDTO();
        responseDTO.setIdProducto(producto.getIdProducto());
        responseDTO.setNombre(producto.getNombre());
        responseDTO.setDescripcion(producto.getDescripcion());
        responseDTO.setPrecio(producto.getPrecio());
        responseDTO.setStock(producto.getStock());
        responseDTO.setImagen(producto.getImagen());
        responseDTO.setFechaCreacion(producto.getFechaCreacion());
        responseDTO.setActivo(producto.getActivo());
        if (producto.getCategoria() != null) {
            responseDTO.setIdCategoria(producto.getCategoria().getId());
            responseDTO.setNombreCategoria(producto.getCategoria().getNombreCategoria());
        }
        return responseDTO;
    }
}
