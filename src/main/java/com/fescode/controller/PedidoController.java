package com.fescode.controller;

import com.fescode.dto.request.PedidoRequestDTO;
import com.fescode.dto.response.PedidoResponseDTO;
import com.fescode.enums.EstadoPedido;
import com.fescode.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    // Endpoint para crear un pedido desde el carrito
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<PedidoResponseDTO> crearPedido(@Valid @RequestBody PedidoRequestDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedidoService.crearPedidoDesdeCarrito(requestDTO.getDireccionEnvio()));
    }

    // Endpoint para obtener el historial de pedidos del usuario autenticado
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> obtenerHistorialUsuario() {
        return ResponseEntity.ok(pedidoService.obtenerHistorialUsuario());
    }

    // Endpoint para que los admins vean todos los pedidos
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<PedidoResponseDTO>> obtenerTodosPedidos() {
        return ResponseEntity.ok(pedidoService.obtenerTodosPedidos());
    }

    // Endpoint para actualizar el estado de un pedido (solo admin)
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> actualizarEstadoPedido(
            @PathVariable Long id,
            @RequestParam EstadoPedido estado) {
        pedidoService.actualizarEstadoPedido(id, estado);
        return ResponseEntity.noContent().build();
    }
}

