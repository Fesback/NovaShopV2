package com.fescode.dto.request;

import com.fescode.model.Rol;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String nombre;
    private String apellido;
    private String contrasena;
    private String email;
    private Rol rol;


}
