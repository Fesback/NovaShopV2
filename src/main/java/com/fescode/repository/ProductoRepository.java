package com.fescode.repository;

import com.fescode.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByActivoTrue();
    List<Producto> findByCategoria_IdAndActivoTrue(Long id);
    List<Producto> findByPrecioBetweenAndActivoTrue(BigDecimal precioMin, BigDecimal precioMax);
    List<Producto> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);

    @Modifying
    @Query("UPDATE Producto p SET p.stock = p.stock - :cantidad WHERE p.idProducto = :idProducto AND p.stock >= :cantidad")
    int reducirStock(@Param("idProducto") Long idProducto, @Param("cantidad") int cantidad);
    List<Producto> findByStockLessThanAndActivoTrue(int stockMinimo);
    List<Producto> findByImagenIsNotNullAndActivoTrue();
}
