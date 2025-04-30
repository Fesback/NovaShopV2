package com.fescode.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categoria", uniqueConstraints = @UniqueConstraint(columnNames = "nombre_categoria"))
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long id;

    @Column(name = "nombre_categoria",nullable = false, unique = true)
    private String nombreCategoria;

    private String descripcion;

    @Column(name = "imagen")
    private String imagen;

    @Column(nullable = false)
    private boolean activo = true;
}
