package com.fescode.service.impl;

import com.fescode.dto.request.CategoriaRequestDTO;
import com.fescode.dto.response.CategoriaResponseDTO;
import com.fescode.entity.Categoria;
import com.fescode.exception.CategoriaExistenteException;
import com.fescode.exception.RecursoNoEncontradoException;
import com.fescode.mapper.CategoriaMapper;
import com.fescode.repository.CategoriaRepository;
import com.fescode.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;


    // Crear
    @Override
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO request) {
        if (categoriaRepository.existsByNombreCategoria(request.getNombre())) {
            throw new CategoriaExistenteException("La categoría '" + request.getNombre() + "' ya existe");
        }

        return categoriaMapper.toResponseDTO(categoriaRepository.save(categoriaMapper.toEntity(request)));
    }

    // Listar activas
    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarCategoriasActivas() {
        return categoriaRepository.findByActivoTrue().stream()
            .map(categoriaMapper::toResponseDTO)
            .toList();
    }

    // Obtener por ID
    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO obtenerCategoriaActiva(Long id) {
        return categoriaMapper.toResponseDTO(
            categoriaRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada"))
        );
    }

    // Actualizar
    @Override
    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO request) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada"));

        // Validar nombre único (excepto para sí misma)
        if (!categoria.getNombreCategoria().equals(request.getNombre())
            && categoriaRepository.existsByNombreCategoria(request.getNombre())) {
            throw new CategoriaExistenteException("La categoría '" + request.getNombre() + "' ya existe");
        }

        categoria.setNombreCategoria(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        return categoriaMapper.toResponseDTO(categoriaRepository.save(categoria));
    }

    // Desactivar (borrado lógico)
    @Override
    public void desactivarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada"));
        categoria.setActivo(false);
        categoriaRepository.save(categoria);
    }
}
