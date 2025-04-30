package com.fescode.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarritoItemRequestDTO {
    @NotNull(message = "El ID del producto es obligatorio")
    private Long idProducto;
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
}
