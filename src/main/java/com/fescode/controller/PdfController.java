package com.fescode.controller;

import com.fescode.entity.Pedido;
import com.fescode.exception.PdfGenerationException;
import com.fescode.exception.RecursoNoEncontradoException;
import com.fescode.repository.PedidoRepository;
import com.fescode.service.PdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {
    private final PdfService pdfService;
    private final PedidoRepository pedidoRepository;

    @GetMapping("/boleta/{pedidoId}")
    public ResponseEntity<byte[]> descargarBoleta(
            @PathVariable Long pedidoId,
            @AuthenticationPrincipal UserDetails userDetails) {

        final String userEmail = userDetails != null ? userDetails.getUsername() : "N/A";

        try {
            log.info("Solicitud de boleta - Pedido: {} | Usuario: {}", pedidoId, userEmail);

            // 1. Validación básica de parámetros
            if (pedidoId == null || pedidoId <= 0) {
                log.warn("ID de pedido inválido: {}", pedidoId);
                return ResponseEntity.badRequest().body("ID de pedido inválido".getBytes());
            }

            // 2. Obtención segura del pedido
            Pedido pedido = pedidoRepository.findByIdWithDetails(pedidoId)
                .orElseThrow(() -> {
                    log.warn("Pedido no encontrado - ID: {} | Usuario: {}", pedidoId, userEmail);
                    return new RecursoNoEncontradoException("Pedido no encontrado");
                });

            // 3. Validación de propiedad reforzada
            if (pedido.getUsuario() == null) {
                log.error("Pedido sin usuario asociado - ID: {}", pedidoId);
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Pedido no tiene usuario asociado".getBytes());
            }

            if (!pedido.getUsuario().getEmail().equals(userEmail)) {
                log.warn("Intento de acceso no autorizado - Pedido: {} | Usuario solicitante: {}",
                        pedidoId, userEmail);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Acceso no autorizado".getBytes());
            }

            // 4. Validación de integridad de datos
            if (pedido.getItems() == null || pedido.getItems().isEmpty()) {
                log.error("Pedido sin items - ID: {} | Usuario: {}", pedidoId, userEmail);
                return ResponseEntity.badRequest()
                    .body("El pedido no contiene items".getBytes());
            }

            // 5. Generación controlada del PDF
            try {
                byte[] pdfBytes = pdfService.generarBoletaPedido(pedido);

                if (pdfBytes == null || pdfBytes.length == 0) {
                    throw new IllegalStateException("PDF generado vacío");
                }

                log.info("PDF generado exitosamente - Tamaño: {} KB | Pedido: {}",
                        pdfBytes.length / 1024, pedidoId);

                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=boleta_" + pedidoId + ".pdf")
                    .body(pdfBytes);

            } catch (PdfGenerationException e) {
                log.error("Error técnico al generar PDF - Pedido: {} | Error: {}",
                        pedidoId, e.getMessage());
                return ResponseEntity.internalServerError()
                    .body("Error técnico al generar la boleta".getBytes());
            }

        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error inesperado - Pedido: {} | Usuario: {} | Error: {}",
                    pedidoId, userEmail, e.getMessage());
            return ResponseEntity.internalServerError()
                .body("Error interno del servidor".getBytes());
        }
    }
}
