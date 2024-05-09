package com.ponatosik.kanban.presentation.controllers.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("api/account")
@SecurityRequirement(name = "bearer")
public class AccountController {

    @GetMapping()
    public String user(@AuthenticationPrincipal Jwt principal) {
        return principal.getClaims().toString();
    }
}
