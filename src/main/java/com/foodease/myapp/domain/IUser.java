package com.foodease.myapp.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface IUser {
    Collection<? extends GrantedAuthority> getAuthorities();

    String getUsername();

    String getPassword();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();

    boolean isEnabled();
}
