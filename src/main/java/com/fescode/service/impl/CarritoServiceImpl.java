package com.fescode.service.impl;

import com.fescode.dto.request.CarritoItemRequestDTO;
import com.fescode.dto.response.CarritoResponseDTO;
import com.fescode.entity.Carrito;
import com.fescode.entity.CarritoItem;
import com.fescode.entity.Producto;
import com.fescode.entity.Usuario;
import com.fescode.exception.BusinessException;
import com.fescode.exception.RecursoNoEncontradoException;
import com.fescode.mapper.CarritoMapper;
import com.fescode.repository.CarritoRepository;
import com.fescode.repository.ProductoRepository;
import com.fescode.security.Utils.AuthenticationFacade;
import com.fescode.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;
    private final CarritoMapper carritoMapper;
    private final AuthenticationFacade authenticationFacade;

    private Long obtenerUsuarioId() {
        return authenticationFacade.getUsuarioActual().getIdUsuario();
    }

    @Override
    public CarritoResponseDTO obtenerCarritoCompleto() {
        Long usuarioId = obtenerUsuarioId();
        Carrito carrito = carritoRepository.findCarritoCompletoByUsuarioId(usuarioId)
                .orElseGet(() -> crearCarritoVacio(usuarioId));

        return carritoMapper.toResponseDTO(carrito);
    }

    @Override
    public CarritoResponseDTO agregarItem(CarritoItemRequestDTO requestDTO) {
        Long usuarioId = obtenerUsuarioId();

        Producto producto = productoRepository.findById(requestDTO.getIdProducto())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

        if (!producto.getActivo() || producto.getStock() < requestDTO.getCantidad()) {
            throw new BusinessException("Producto no disponible o stock insuficiente");
        }

        Carrito carrito = carritoRepository.findCarritoCompletoByUsuarioId(usuarioId)
                .orElseGet(() -> crearCarritoVacio(usuarioId));

        carrito.getItems().stream()
                .filter(item -> item.getProducto().getIdProducto().equals(requestDTO.getIdProducto()))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setCantidad(item.getCantidad() + requestDTO.getCantidad()),
                        () -> carrito.agregarItem(
                                CarritoItem.builder()
                                        .producto(producto)
                                        .cantidad(requestDTO.getCantidad())
                                        .build()
                        )
                );

        carritoRepository.save(carrito);
        return carritoMapper.toResponseDTO(carrito);
    }

    @Override
    public CarritoResponseDTO actualizarItem(Long idProducto, Integer cantidad) {
        if (cantidad <= 0) {
            throw new BusinessException("La cantidad debe ser mayor a cero");
        }

        Long usuarioId = obtenerUsuarioId();
        Carrito carrito = carritoRepository.findCarritoCompletoByUsuarioId(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrito no encontrado"));

        CarritoItem item = carrito.getItems().stream()
                .filter(i -> i.getProducto().getIdProducto().equals(idProducto))
                .findFirst()
                .orElseThrow(() -> new RecursoNoEncontradoException("Ítem no encontrado en el carrito"));

        item.setCantidad(cantidad);
        carritoRepository.save(carrito);

        return carritoMapper.toResponseDTO(carrito);
    }

    @Override
    public void eliminarItem(Long idProducto) {
        Long usuarioId = obtenerUsuarioId();

        Carrito carrito = carritoRepository.findCarritoCompletoByUsuarioId(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrito no encontrado"));

        boolean removed = carrito.getItems().removeIf(
                item -> item.getProducto().getIdProducto().equals(idProducto)
        );

        if (!removed) {
            throw new RecursoNoEncontradoException("Ítem no encontrado en el carrito");
        }

        carritoRepository.save(carrito);
    }

    @Override
    public void eliminarItemPorId(Long idItem) {
        Long usuarioId = obtenerUsuarioId();

        Carrito carrito = carritoRepository.findCarritoCompletoByUsuarioId(usuarioId)
            .orElseThrow(() -> new RecursoNoEncontradoException("Carrito no encontrado"));

        boolean removed = carrito.getItems().removeIf(
            item -> item.getId().equals(idItem)
        );

        if (!removed) {
            throw new RecursoNoEncontradoException("Ítem no encontrado en el carrito");
        }

        carritoRepository.save(carrito);
    }

    @Override
    public void vaciarCarrito() {
        Long usuarioId = obtenerUsuarioId();

        Carrito carrito = carritoRepository.findCarritoCompletoByUsuarioId(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrito no encontrado"));

        carrito.getItems().clear();
        carritoRepository.save(carrito);
    }

    @Override
    public BigDecimal calcularTotalCarrito() {
        Long usuarioId = obtenerUsuarioId();

        Carrito carrito = carritoRepository.findCarritoCompletoByUsuarioId(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrito no encontrado"));

        return carrito.getItems().stream()
                .map(item -> item.getProducto().getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Carrito crearCarritoVacio(Long usuarioId) {
        return carritoRepository.save(
                Carrito.builder()
                        .usuario(Usuario.builder().idUsuario(usuarioId).build())
                        .build()
        );
    }
}
