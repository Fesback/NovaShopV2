package com.fescode.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarritoResponseDTO {
    private Long idCarrito;
    private List<CarritoItemResponseDTO> items; // Lista de productos en el carrito
    private BigDecimal total;  // Suma de todos los subtotales
}
