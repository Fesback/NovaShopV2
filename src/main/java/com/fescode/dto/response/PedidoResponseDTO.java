package com.fescode.dto.response;

import com.fescode.enums.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {
    private Long idPedido;
    private BigDecimal total;
    private EstadoPedido estado;
    private LocalDateTime fechaPedido;
    private List<DetallePedidoResponseDTO> items; // Lista de productos en el pedido
}
