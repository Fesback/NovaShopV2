package com.fescode.security.user;

import com.fescode.entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.usuario.getRoles().stream()
            .map(role -> {
                String roleName = role.getNombreRol();
                return new SimpleGrantedAuthority(
                    roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName
                );
            })
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.getActivo() != null && usuario.getActivo();
    }

    public Long getId() {
        return this.usuario.getIdUsuario();
    }
}
