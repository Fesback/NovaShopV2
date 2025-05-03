package com.fescode.controller;

import com.fescode.dto.request.CarritoItemRequestDTO;
import com.fescode.dto.response.CarritoResponseDTO;
import com.fescode.service.CarritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    // Obtener carrito completo
    @GetMapping
    public ResponseEntity<CarritoResponseDTO> obtenerCarrito() {
        CarritoResponseDTO carrito = carritoService.obtenerCarritoCompleto();
        return ResponseEntity.ok(carrito);
    }

    // Agregar un item al carrito
    @PostMapping("/item")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<CarritoResponseDTO> agregarItem(@RequestBody @Valid CarritoItemRequestDTO requestDTO) {
        CarritoResponseDTO carrito = carritoService.agregarItem(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(carrito);
    }

    // Actualizar la cantidad de un item en el carrito
    @PutMapping("/item/{idProducto}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<CarritoResponseDTO> actualizarItem(
            @PathVariable Long idProducto, @RequestParam Integer cantidad) {
        CarritoResponseDTO carrito = carritoService.actualizarItem(idProducto, cantidad);
        return ResponseEntity.ok(carrito);
    }

    // Eliminar un item por producto en el carrito
    @DeleteMapping("/item/producto/{idProducto}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long idProducto) {
        carritoService.eliminarItem(idProducto);
        return ResponseEntity.noContent().build();
    }

    // Eliminar un item por ID en el carrito
    @DeleteMapping("/item/{idItem}")
    public ResponseEntity<Void> eliminarItemPorId(@PathVariable Long idItem) {
        carritoService.eliminarItemPorId(idItem);
        return ResponseEntity.noContent().build();
    }

    // Vaciar el carrito
    @DeleteMapping
    public ResponseEntity<Void> vaciarCarrito() {
        carritoService.vaciarCarrito();
        return ResponseEntity.noContent().build();
    }

    // Calcular el total del carrito
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> calcularTotalCarrito() {
        BigDecimal total = carritoService.calcularTotalCarrito();
        return ResponseEntity.ok(total);
    }
}