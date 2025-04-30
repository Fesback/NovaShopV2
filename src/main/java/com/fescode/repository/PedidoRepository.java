package com.fescode.repository;

import com.fescode.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
      // Metodo para obtener pedidos de un usuario con detalles
    @Query("SELECT DISTINCT p FROM Pedido p " +
           "LEFT JOIN FETCH p.items i " +
           "LEFT JOIN FETCH i.producto " +
           "WHERE p.usuario.idUsuario = :usuarioId")
    List<Pedido> findByUsuarioIdWithDetails(@Param("usuarioId") Long usuarioId);

    // Metodo para obtener todos los pedidos con detalles
    @Query("SELECT DISTINCT p FROM Pedido p " +
           "LEFT JOIN FETCH p.items i " +
           "LEFT JOIN FETCH i.producto")
    List<Pedido> findAllWithDetails();

    // Metodo para buscar un pedido por ID con detalles
    @Query("SELECT p FROM Pedido p " +
           "LEFT JOIN FETCH p.items i " +
           "LEFT JOIN FETCH i.producto " +
           "WHERE p.id = :id")
    Optional<Pedido> findByIdWithDetails(@Param("id") Long id);
}

