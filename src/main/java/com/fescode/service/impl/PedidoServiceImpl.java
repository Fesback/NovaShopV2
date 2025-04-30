package com.fescode.service.impl;

import com.fescode.dto.response.CarritoResponseDTO;
import com.fescode.dto.response.PedidoResponseDTO;
import com.fescode.entity.DetallePedido;
import com.fescode.entity.Pedido;
import com.fescode.entity.Producto;
import com.fescode.entity.Usuario;
import com.fescode.enums.EstadoPedido;
import com.fescode.exception.BusinessException;
import com.fescode.exception.RecursoNoEncontradoException;
import com.fescode.mapper.PedidoMapper;
import com.fescode.repository.PedidoRepository;
import com.fescode.security.Utils.AuthenticationFacade;
import com.fescode.service.CarritoService;
import com.fescode.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CarritoService carritoService;
    private final PedidoMapper pedidoMapper;
    private final AuthenticationFacade authenticationFacade;


    @Override
    public PedidoResponseDTO crearPedidoDesdeCarrito(String direccionEnvio) {

        /*
        *  ESTE METODO CREA UN PEDIDO A PARTIR DEL CARRITO ACTUAL DEL USUARIO.
        * */

        // PRIMERO: VALIDAR QUE EL CARRITO NO ESTÉ VACÍO
        CarritoResponseDTO carrito = carritoService.obtenerCarritoCompleto();
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new BusinessException("No se puede crear un pedido con el carrito vacío");
        }

        //  SEGUNDO: Verificar que la dirección no sea nula o vacía
        if (direccionEnvio == null || direccionEnvio.trim().isEmpty()) {
            throw new BusinessException("La dirección de envío es obligatoria");
        }

        // TERCERO: Crear un nuevo pedido con la dirección de envío
        Usuario usuario = authenticationFacade.getUsuarioActual();
        Pedido pedido = Pedido.builder()
            .usuario(usuario)
            .total(carrito.getTotal())
            .estado(EstadoPedido.PENDIENTE)
            .direccionEnvio(direccionEnvio)  // Asignar la dirección de envío
            .build();

        // CUARTO: Asignar los detalles del pedido
        List<DetallePedido> detalles = carrito.getItems().stream()
            .map(item -> DetallePedido.builder()
                    .pedido(pedido)
                    .producto(Producto.builder().idProducto(item.getIdProducto()).build())
                    .cantidad(item.getCantidad())
                    .precioUnitario(item.getPrecioUnitario())
                    .subtotal(item.getSubtotal())
                    .build())
            .collect(Collectors.toList());

            pedido.setItems(detalles);

            // Guardar el pedido en la base de datos
            pedidoRepository.save(pedido);
            carritoService.vaciarCarrito();

        return pedidoMapper.toDto(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> obtenerHistorialUsuario() {
        Long usuarioId = authenticationFacade.getUsuarioActual().getIdUsuario();
        return pedidoRepository.findByUsuarioIdWithDetails(usuarioId).stream()
                .map(pedidoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> obtenerTodosPedidos() {
        return pedidoRepository.findAllWithDetails().stream()
                .map(pedidoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void actualizarEstadoPedido(Long idPedido, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findByIdWithDetails(idPedido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado"));

        if (!EstadoPedido.validarTransicion(pedido.getEstado(), nuevoEstado)) {
            throw new BusinessException("Transición de estado no permitida");
        }

        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);
    }
}

