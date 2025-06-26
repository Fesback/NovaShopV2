package com.fescode.controller;

import com.fescode.dto.response.PedidoPdfDTO;
import com.fescode.entity.Pedido;
import com.fescode.exception.RecursoNoEncontradoException;
import com.fescode.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {
    private final PedidoRepository pedidoRepository;

    @GetMapping("/boleta/datos/{pedidoId}")
    public ResponseEntity<PedidoPdfDTO> obtenerDatosParaBoleta(
            @PathVariable Long pedidoId,
            @AuthenticationPrincipal UserDetails userDetails) {

        final String userEmail = userDetails != null ? userDetails.getUsername() : "N/A";

        try {
            Pedido pedido = pedidoRepository.findByIdWithDetails(pedidoId)
                    .orElseThrow(() -> {
                        log.warn("Pedido no encontrado - ID: {} | Usuario: {}", pedidoId, userEmail);
                        return new RecursoNoEncontradoException("Pedido no encontrado");
                    });

            if (pedido.getUsuario() == null || !pedido.getUsuario().getEmail().equals(userEmail)) {
                log.warn("Acceso no autorizado - Pedido: {} | Usuario: {}", pedidoId, userEmail);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            if (pedido.getItems() == null || pedido.getItems().isEmpty()) {
                log.warn("Pedido sin items - ID: {}", pedidoId);
                return ResponseEntity.badRequest().build();
            }

            PedidoPdfDTO dto = new PedidoPdfDTO();
            dto.setIdPedido(pedido.getId());
            dto.setFechaPedido(pedido.getFechaPedido().toString());
            dto.setNombreCliente(pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido());
            dto.setEmailCliente(pedido.getUsuario().getEmail());
            dto.setDireccionEnvio(pedido.getDireccionEnvio());
            dto.setTotal(pedido.getTotal().doubleValue());

            List<PedidoPdfDTO.ItemPedidoDTO> items = pedido.getItems().stream().map(item -> {
                PedidoPdfDTO.ItemPedidoDTO itemDTO = new PedidoPdfDTO.ItemPedidoDTO();
                itemDTO.setNombreProducto(item.getProducto().getNombre());
                itemDTO.setCantidad(item.getCantidad());
                itemDTO.setPrecio(item.getPrecioUnitario().doubleValue());
                return itemDTO;
            }).toList();

            dto.setItems(items);

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            log.error("Error inesperado al obtener datos de boleta: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
