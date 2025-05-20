package com.fescode.security.config;

import com.fescode.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .cors(Customizer.withDefaults()) // habilito cors para todas las peticiones
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/error").permitAll()

            //  Permitir pre-flight CORS requests
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            // Endpoints públicos
            .requestMatchers(
                    "/swagger-ui.html",
                "/auth/register",
                "/auth/login",
                "/v3/api-docs/**",
                "/swagger-ui/**"
            ).permitAll()

            // Solo registro de administradores
            .requestMatchers("/auth/register-admin").hasAuthority("ROLE_ADMIN")

            // Endpoints GET públicos
            .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()

            // Acceso al carrito para USER y ADMIN
            .requestMatchers("/api/carrito/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

            // Permitir pedidos a USER y ADMIN
            .requestMatchers(HttpMethod.POST, "/api/pedidos").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

            // Métodos protegidos solo para ADMIN
            .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ROLE_ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ROLE_ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ROLE_ADMIN")

            .anyRequest().authenticated()
        )
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
}


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(true);
    }
}
