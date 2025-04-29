package com.fescode.service.impl;

import com.fescode.dto.request.UsuarioCreateRequestDTO;
import com.fescode.dto.request.UsuarioUpdateRequestDTO;
import com.fescode.dto.response.UsuarioResponseDTO;
import com.fescode.entity.Role;
import com.fescode.entity.Usuario;
import com.fescode.mapper.UsuarioMapper;
import com.fescode.repository.RoleRepository;
import com.fescode.repository.UsuarioRepository;
import com.fescode.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    //Inyeccion de las dependencias :p
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    //Implementacion de los metodos de la interfaz

    @Override
    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioCreateRequestDTO usuarioDTO) {
        if(usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        Role userRole = roleRepository.findByNombreRol("USER").orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));

        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
        usuario.setRoles(Set.of(userRole));
        usuario.setActivo(true);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuarioGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuarioMapper.toDto(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateRequestDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioDTO.getNombre() != null) {
            usuario.setNombre(usuarioDTO.getNombre());
        }
        if (usuarioDTO.getApellido() != null) {
            usuario.setApellido(usuarioDTO.getApellido());
        }
        if (usuarioDTO.getDireccion() != null) {
            usuario.setDireccion(usuarioDTO.getDireccion());
        }
        if (usuarioDTO.getTelefono() != null) {
            usuario.setTelefono(usuarioDTO.getTelefono());
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuarioActualizado);
    }


    @Override
    @Transactional
    public void desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void activarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }
}
