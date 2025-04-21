package com.fescode.security.service;

import com.fescode.dto.request.AuthRequest;
import com.fescode.dto.request.RegisterRequest;
import com.fescode.dto.response.AuthResponse;
import com.fescode.model.Rol;
import com.fescode.model.Usuario;
import com.fescode.repository.UsuarioRepository;
import com.fescode.security.jwt.JwtService;
import com.fescode.security.user.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El correo ya está en uso.");
        }

        Rol rolPorDefecto = Rol.builder()
                .id(1L)
                .nombre("USER")
                .build();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolPorDefecto);

        Usuario nuevoUsuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .roles(roles)
                .build();

        usuarioRepository.save(nuevoUsuario);

        UserDetailsImpl userDetails = new UserDetailsImpl(nuevoUsuario);
        String jwt = jwtService.generateToken(userDetails);
        return AuthResponse.builder().token(jwt).build();
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getConstrasena())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        UserDetailsImpl userDetails = new UserDetailsImpl(usuario);
        String jwt = jwtService.generateToken(userDetails);
        return AuthResponse.builder()
                .token(jwt)
                .build();
    }
}
