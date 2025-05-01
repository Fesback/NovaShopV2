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
      // MÉTODOS EXISTENTES (se mantienen igual)
    @Query("SELECT DISTINCT p FROM Pedido p " +
           "LEFT JOIN FETCH p.items i " +
           "LEFT JOIN FETCH i.producto " +
           "WHERE p.usuario.idUsuario = :usuarioId")
    List<Pedido> findByUsuarioIdWithDetails(@Param("usuarioId") Long usuarioId);

    // MÉTODOS EXISTENTES (se mantienen igual)
    @Query("SELECT DISTINCT p FROM Pedido p " +
           "LEFT JOIN FETCH p.items i " +
           "LEFT JOIN FETCH i.producto")
    List<Pedido> findAllWithDetails();

    // ÚNICO CAMBIO NECESARIO (totalmente seguro)
    @Query("SELECT p FROM Pedido p " +
           "LEFT JOIN FETCH p.usuario u " + // SOLO AÑADIMOS ESTO
           "LEFT JOIN FETCH p.items i " +
           "LEFT JOIN FETCH i.producto " +
           "WHERE p.id = :id")
    Optional<Pedido> findByIdWithDetails(@Param("id") Long id);
}

