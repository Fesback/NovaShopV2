package com.fescode.repository;

import com.fescode.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    @Query("SELECT DISTINCT c FROM Carrito c " +
           "LEFT JOIN FETCH c.items i " +
           "LEFT JOIN FETCH i.producto p " +
           "WHERE c.usuario.idUsuario = :usuarioId")

    Optional<Carrito> findCarritoCompletoByUsuarioId(@Param("usuarioId") Long usuarioId);
    Optional<Carrito> findByUsuarioIdUsuario(Long usuarioId);
    boolean existsByUsuarioIdUsuario(Long usuarioId);
}
