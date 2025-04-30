package com.fescode.service.impl;

import com.fescode.dto.request.ProductoRequestDTO;
import com.fescode.dto.response.ProductoResponseDTO;
import com.fescode.entity.Producto;
import com.fescode.exception.RecursoNoEncontradoException;
import com.fescode.mapper.ProductoMapper;
import com.fescode.repository.ProductoRepository;
import com.fescode.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO requestDTO) {
        Producto producto = productoMapper.toEntity(requestDTO);
        producto.setActivo(true);
        producto = productoRepository.save(producto);
        return productoMapper.toResponse(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarTodos() {
        return productoRepository.findByActivoTrue().stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO buscarPorId(Long idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));
        return productoMapper.toResponse(producto);
    }

    @Override
    @Transactional
    public ProductoResponseDTO actualizar(Long idProducto, ProductoRequestDTO requestDTO) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));
        producto.setNombre(requestDTO.getNombre());
        producto.setDescripcion(requestDTO.getDescripcion());
        producto.setPrecio(requestDTO.getPrecio());
        producto.setStock(requestDTO.getStock());
        producto.setImagen(requestDTO.getImagen());

        if (requestDTO.getIdCategoria() != null) {
            producto.setCategoria(productoMapper.toEntity(requestDTO).getCategoria());
        } else {
            producto.setCategoria(null);
        }

        producto = productoRepository.save(producto);
        return productoMapper.toResponse(producto);
    }

    @Override
    @Transactional
    public void desactivar(Long idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));
        producto.setActivo(false);
        productoRepository.save(producto);
    }
}
