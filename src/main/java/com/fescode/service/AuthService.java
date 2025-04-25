package com.fescode.service;


import com.fescode.dto.request.LoginRequestDTO;
import com.fescode.dto.request.RegisterRequestDTO;
import com.fescode.dto.response.LoginResponseDTO;
import com.fescode.dto.response.RegisterResponseDTO;
import com.fescode.entity.Role;
import com.fescode.entity.Usuario;
import com.fescode.repository.RoleRepository;
import com.fescode.repository.UsuarioRepository;
import com.fescode.security.jwt.JwtService;
import com.fescode.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fescode.exception.RoleNotFoundException;

import java.time.LocalDateTime;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterResponseDTO register(RegisterRequestDTO request) {
        Role userRole = roleRepository.findByNombreRol("USER")
                .orElseThrow(() -> new RoleNotFoundException("Rol USER no encontrado"));

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .roles(Set.of(userRole))
                .activo(true)
                .fechaRegistro(LocalDateTime.now())
                .build();

        usuarioRepository.save(usuario);

        return RegisterResponseDTO.builder()
                .mensaje("Usuario registrado exitosamente")
                .token(jwtService.generateToken(new UserDetailsImpl(usuario)))
                .build();
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getContrasena()
                    )
            );

            Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            String rol = usuario.getRoles().stream()
                    .findFirst()
                    .map(Role::getNombreRol)
                    .orElse("USER");

            return LoginResponseDTO.builder()
                    .token(jwtService.generateToken(new UserDetailsImpl(usuario)))
                    .nombre(usuario.getNombre())
                    .rol(rol)
                    .build();
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciales inválidas", e);
        }
    }
}
