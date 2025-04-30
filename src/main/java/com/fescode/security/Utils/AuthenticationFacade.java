package com.fescode.security.Utils;

import com.fescode.entity.Usuario;
import com.fescode.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AuthenticationFacade(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String getUsernameActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }
        return authentication.getName();
    }

    public Usuario getUsuarioActual() {
        String username = getUsernameActual();
        return usuarioRepository.findByEmail(username)
               .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
