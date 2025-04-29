package com.fescode.service;

import com.fescode.dto.request.UsuarioCreateRequestDTO;
import com.fescode.dto.request.UsuarioUpdateRequestDTO;
import com.fescode.dto.response.UsuarioResponseDTO;
import com.fescode.security.user.UserDetailsImpl;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO crearUsuario(UsuarioCreateRequestDTO usuarioDTO);
    List<UsuarioResponseDTO> listarTodos();
    UsuarioResponseDTO obtenerPorId(Long id);
    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateRequestDTO usuarioDTO);
    void desactivarUsuario(Long id);
    void activarUsuario(Long id);
}
