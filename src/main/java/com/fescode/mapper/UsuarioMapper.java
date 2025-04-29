package com.fescode.mapper;

import com.fescode.dto.request.UsuarioCreateRequestDTO;
import com.fescode.dto.response.UsuarioResponseDTO;
import com.fescode.entity.Role;
import com.fescode.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UsuarioMapper {
    public Usuario toEntity(UsuarioCreateRequestDTO dto) {
        return Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .build();
    }

    public UsuarioResponseDTO toDto(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .direccion(usuario.getDireccion())
                .telefono(usuario.getTelefono())
                .fechaRegistro(usuario.getFechaRegistro())
                .activo(usuario.getActivo())
                .roles(usuario.getRoles().stream()
                        .map(Role::getNombreRol)
                        .collect(Collectors.toSet()))
                .build();
    }
}
