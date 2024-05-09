package com.ponatosik.kanban.presentation.controllers.rest;

import com.ponatosik.kanban.presentation.authorization.JwtClaimsFacade;
import com.ponatosik.kanban.core.entities.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/account")
@SecurityRequirement(name = "bearer")
public class AccountController {

    private final JwtClaimsFacade jwtClaimsFacade;

    public AccountController(JwtClaimsFacade jwtClaimsFacade) {
        this.jwtClaimsFacade = jwtClaimsFacade;
    }

    @GetMapping()
    public User user() {
        return jwtClaimsFacade.getUser().get();
    }
}
