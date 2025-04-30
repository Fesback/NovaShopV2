package com.fescode.repository;

import com.fescode.entity.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
    @Query("SELECT ci FROM CarritoItem ci " +
           "JOIN FETCH ci.producto p " +
           "WHERE ci.carrito.id = :carritoId AND p.idProducto = :productoId")
    Optional<CarritoItem> findItemConProducto(
        @Param("carritoId") Long carritoId,
        @Param("productoId") Long productoId
    );

    @Modifying
    @Query("DELETE FROM CarritoItem ci WHERE ci.carrito.id = :carritoId AND ci.producto.idProducto = :productoId")
    void eliminarItem(
        @Param("carritoId") Long carritoId,
        @Param("productoId") Long productoId
    );

    int countByCarritoId(Long carritoId);
}