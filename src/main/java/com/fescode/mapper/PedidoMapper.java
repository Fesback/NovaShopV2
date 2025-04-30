package com.fescode.mapper;

import com.fescode.dto.response.DetallePedidoResponseDTO;
import com.fescode.dto.response.PedidoResponseDTO;
import com.fescode.entity.DetallePedido;
import com.fescode.entity.Pedido;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoMapper {
    public PedidoResponseDTO toDto(Pedido pedido){
        return PedidoResponseDTO.builder()
                .idPedido(pedido.getId())
                .total(pedido.getTotal())
                .estado(pedido.getEstado())
                .fechaPedido(pedido.getFechaPedido())
                .items(pedido.getItems().stream()
                        .map(this::toDetalleResponseDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public DetallePedidoResponseDTO toDetalleResponseDTO(DetallePedido detalle) {
        return DetallePedidoResponseDTO.builder()
                .nombreProducto(detalle.getProducto().getNombre())
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(detalle.getSubtotal())
                .build();
    }
}
