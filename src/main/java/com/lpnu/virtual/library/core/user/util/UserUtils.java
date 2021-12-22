package com.lpnu.virtual.library.core.user.util;

import com.lpnu.virtual.library.core.user.model.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class UserUtils {
    public static Collection<? extends GrantedAuthority> getAuthorities() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user != null ? user.getAuthorities() : Collections.emptyList();
    }

    public static Collection<String> getAuthoritiesAsString() {
        return isAuthorized() ? getAuthorities().stream()
                .map(GrantedAuthority::toString)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    public static String getUserLogin() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    public static UserDetails getUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin() {
        return isAuthorized() && getAuthoritiesAsString().contains(Roles.ADMIN.name());
    }

    public static Boolean isAuthorized() {
        return !getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::toString)
                .collect(Collectors.toList())
                .contains(Roles.ROLE_ANONYMOUS.name());
    }
}
