package com.game.pokedex.config;
import java.util.function.BiFunction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class PermissionValitation {

    private PermissionValitation() { }

    public static BiFunction<Authentication, String, Boolean> validatePermission = (auth, username) ->
            !auth.getName().equals(username) && auth.getAuthorities().stream().noneMatch(it -> it.equals(new SimpleGrantedAuthority("ROLE_ADMIN")));

}
