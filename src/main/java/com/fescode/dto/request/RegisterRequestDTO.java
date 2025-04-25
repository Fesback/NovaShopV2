package com.fescode.dto.request;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
    private String direccion;
    private String telefono;
}
