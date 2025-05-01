package com.fescode.controller;

import com.fescode.entity.Pedido;
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
    private final PedidoRepository pedidoRepository; // Añade esta línea

     @GetMapping("/boleta/{pedidoId}")
    public ResponseEntity<byte[]> descargarBoleta(
            @PathVariable Long pedidoId,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            log.debug("Iniciando generación de PDF para pedido ID: {}", pedidoId);

            // 1. Obtener pedido con relaciones cargadas (incluyendo usuario)
            Pedido pedido = pedidoRepository.findByIdWithDetails(pedidoId)
                .orElseThrow(() -> {
                    log.warn("Pedido no encontrado: {}", pedidoId);
                    return new RecursoNoEncontradoException("Pedido no encontrado con ID: " + pedidoId);
                });

            // 2. Validar propiedad del pedido (ahora seguro porque usuario está cargado)
            if (pedido.getUsuario() == null || !pedido.getUsuario().getEmail().equals(userDetails.getUsername())) {
                log.warn("Intento de acceso no autorizado. Pedido: {}, Usuario: {}",
                        pedidoId, userDetails.getUsername());
                throw new AccessDeniedException("No tienes permiso para acceder a este pedido");
            }

            // 3. Validar datos mínimos
            if (pedido.getItems() == null || pedido.getItems().isEmpty()) {
                log.error("Pedido {} no tiene items", pedidoId);
                throw new IllegalStateException("El pedido no contiene items");
            }

            // 4. Generar PDF
            byte[] pdfBytes;
            try {
                pdfBytes = pdfService.generarBoletaPedido(pedido);
                log.debug("PDF generado correctamente para pedido {}", pedidoId);
            } catch (Exception e) {
                log.error("Error al generar PDF para pedido {}", pedidoId, e);
                throw new RuntimeException("Error al generar PDF: " + e.getMessage(), e);
            }

            // 5. Verificar PDF generado
            if (pdfBytes == null || pdfBytes.length == 0) {
                log.error("PDF generado vacío para pedido {}", pedidoId);
                throw new IllegalStateException("El PDF generado está vacío");
            }

            // 6. Retornar respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "boleta_" + pedidoId + ".pdf");

            log.info("PDF entregado exitosamente para pedido {}", pedidoId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (RecursoNoEncontradoException e) {
            log.error("Recurso no encontrado: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            log.error("Acceso denegado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                   .body(e.getMessage().getBytes(StandardCharsets.UTF_8));
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error("Error de validación: {}", e.getMessage());
            return ResponseEntity.badRequest()
                   .body(e.getMessage().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Error interno al generar PDF para pedido {}: {}", pedidoId, e.getMessage(), e);
            return ResponseEntity.internalServerError()
                   .body(("Error interno al generar PDF. Detalles: " + e.getMessage())
                   .getBytes(StandardCharsets.UTF_8));
        }
    }
}

