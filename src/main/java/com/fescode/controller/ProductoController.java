package com.fescode.controller;

import com.fescode.dto.request.ProductoRequestDTO;
import com.fescode.dto.response.ProductoResponseDTO;
import com.fescode.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    // Endpoint para Listar los productos
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarTodos(){
        return ResponseEntity.ok(productoService.listarTodos());
    }

    // Endpoint para buscar un producto por ID
    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoResponseDTO> buscarPorId(@PathVariable Long idProducto){
        return ResponseEntity.ok(productoService.buscarPorId(idProducto));
    }

    // Endpoint para Crear un producto
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(requestDTO));
    }

    // Endpoint para Actualizar un producto
    @PutMapping("/{idProducto}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long idProducto,
            @Valid @RequestBody ProductoRequestDTO requestDTO
    ){
        return ResponseEntity.ok(productoService.actualizar(idProducto, requestDTO));
    }

    // Endpoint para Desactivar un producto
    @DeleteMapping("/{idProducto}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> desactivar(@PathVariable Long idProducto){
        productoService.desactivar(idProducto);
        return ResponseEntity.noContent().build();
    }


}
