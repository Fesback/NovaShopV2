package com.fescode.controller;

import com.fescode.dto.request.CategoriaRequestDTO;
import com.fescode.dto.response.CategoriaResponseDTO;
import com.fescode.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    // SOLO ADMINISTRADORES PUEDEN CREAR CATEGORÍAS
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(
        @Valid @RequestBody CategoriaRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(categoriaService.crearCategoria(request));
    }

    // LISTAR CATEGORIAS ACTIVAS (PÚBLICO)
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategoriasActivas() {
        return ResponseEntity.ok(categoriaService.listarCategoriasActivas());
    }

    // Obtener por ID (Público)
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.obtenerCategoriaActiva(id));
    }

    // Actualizar (ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(
        @PathVariable Long id,
        @Valid @RequestBody CategoriaRequestDTO request
    ) {
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, request));
    }

    // Desactivar (ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> desactivarCategoria(@PathVariable Long id) {
        categoriaService.desactivarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
