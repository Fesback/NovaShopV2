package com.fescode.mapper;

import com.fescode.dto.response.CarritoItemResponseDTO;
import com.fescode.dto.response.CarritoResponseDTO;
import com.fescode.entity.Carrito;
import com.fescode.entity.CarritoItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarritoMapper {
    public CarritoResponseDTO toResponseDTO(Carrito carrito) {
        return CarritoResponseDTO.builder()
                .idCarrito(carrito.getId())
                .items(mapItemsToDTO(carrito.getItems()))
                .total(calculateTotal(carrito.getItems()))
                .build();
    }

    private List<CarritoItemResponseDTO> mapItemsToDTO(List<CarritoItem> items) {
        return items.stream().map(item ->
            CarritoItemResponseDTO.builder()
                .idItem(item.getId())
                .idProducto(item.getProducto().getIdProducto())
                .nombreProducto(item.getProducto().getNombre())
                .imagenProducto(item.getProducto().getImagen())
                .precioUnitario(item.getProducto().getPrecio())
                .cantidad(item.getCantidad())
                .subtotal(item.getProducto().getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())))
                .build()
        ).collect(Collectors.toList());
    }

    private BigDecimal calculateTotal(List<CarritoItem> items) {
        return items.stream()
                .map(item -> item.getProducto().getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
