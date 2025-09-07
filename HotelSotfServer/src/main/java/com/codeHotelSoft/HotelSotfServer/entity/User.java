package com.codeHotelSoft.HotelSotfServer.entity;

import com.codeHotelSoft.HotelSotfServer.dto.UserDto;
import com.codeHotelSoft.HotelSotfServer.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entidad que representa a un usuario del sistema.
 * Se almacena en base de datos mediante JPA.
 */
@Entity
@Data
public class User implements UserDetails {

    /** Identificador único autogenerado. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Correo electrónico (usado como credencial). */
    private String email;

    /** Contraseña (debe almacenarse en formato encriptado). */
    private String password;

    /** Nombre del usuario. */
    private String name;

    /** Rol del usuario dentro del sistema (ADMIN, CLIENTE, etc.). */
    private UserRole userRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }

    public UserDto getUserDto() {

        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setName(name);
        dto.setEmail(email);
        dto.setUserRole(userRole);

        return dto;


    }
}
