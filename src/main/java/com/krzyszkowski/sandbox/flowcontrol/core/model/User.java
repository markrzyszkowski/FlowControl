package com.krzyszkowski.sandbox.flowcontrol.core.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getPermissions();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public enum Role {
        USER(List.of()),
        MANAGER(List.of(Permission.ACCESS_MANAGEMENT_PANEL)),
        ADMIN(List.of(Permission.ACCESS_ADMIN_PANEL));

        private List<Permission> permissions;

        Role(List<Permission> permissions) {
            this.permissions = permissions;
        }

        public List<Permission> getPermissions() {
            return List.copyOf(permissions);
        }
    }

    public enum Permission implements GrantedAuthority {
        ACCESS_MANAGEMENT_PANEL,
        ACCESS_ADMIN_PANEL;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}
