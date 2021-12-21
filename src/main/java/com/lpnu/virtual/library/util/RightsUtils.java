package com.lpnu.virtual.library.util;

import com.lpnu.virtual.library.core.user.model.Roles;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@UtilityClass
public class RightsUtils {
    public static Boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
        return isAdmin(authorities) || ValuesUtils.hasElements(authorities)
                && Roles.contain(authority)
                && authorities.stream()
                .anyMatch(a -> authority.equals(a.getAuthority()));
    }

    public static Boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        return ValuesUtils.hasElements(authorities)
                && authorities.stream()
                .anyMatch(a -> Roles.ADMIN.name().equals(a.getAuthority()));
    }
}
