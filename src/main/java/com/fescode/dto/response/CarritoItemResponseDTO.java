package com.fescode.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarritoItemResponseDTO {
     private Long idItem;
    private Long idProducto;
    private String nombreProducto;
    private String imagenProducto;
    private BigDecimal precioUnitario;
    private Integer cantidad;
    private BigDecimal subtotal;  // precioUnitario * cantidad
}
