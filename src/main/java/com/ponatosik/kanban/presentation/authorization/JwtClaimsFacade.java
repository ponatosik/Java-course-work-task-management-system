package com.ponatosik.kanban.presentation.authorization;

import com.ponatosik.kanban.application.interfaces.UserService;
import com.ponatosik.kanban.core.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtClaimsFacade implements UserService {
    public Optional<Jwt> getPrincipal() {
        if(!(SecurityContextHolder.getContext().getAuthentication() instanceof Jwt)) {
            return Optional.empty();
        }
        return Optional.of(
                (Jwt) SecurityContextHolder
                        .getContext()
                        .getAuthentication().getPrincipal());
    }

    public Optional<Long> getUserId() {
        return getPrincipal().map(principal -> Long.parseLong(principal.getClaims().get("sub").toString()));
    }

    public Optional<String> getUserName() {
        return getPrincipal().map(principal -> principal.getClaims().get("given_name").toString());
    }

    public Optional<String> getEmail() {
        return getPrincipal().map(principal -> principal.getClaims().get("email").toString());
    }

    public Optional<User> getUser() {
        Optional<Jwt> principal = Optional.of(
                (Jwt) SecurityContextHolder
                        .getContext()
                        .getAuthentication().getPrincipal());

        if (principal.isEmpty()) {
            return Optional.empty();
        }

        Jwt principalValue = principal.get();
        String userId = principalValue.getClaims().get("sub").toString();
        String name = principalValue.getClaims().get("given_name").toString();
        String email = principalValue.getClaims().get("email").toString();

        return Optional.of(new User(userId, name, email, new ArrayList<>()));
    }

    @Override
    public Optional<User> getAuthenticatedUser() {
        return getUser();
    }
}
