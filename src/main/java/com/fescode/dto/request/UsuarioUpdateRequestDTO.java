package com.fescode.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateRequestDTO {
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
}
