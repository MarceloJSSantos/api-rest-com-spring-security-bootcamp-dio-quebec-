package br.marcelojssantos.apirestspringsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping
    public String welcome(){
        return "Seja bem-vindo a minha API c/ Spring Boot Web e Secutity!";
    }

    @GetMapping("/usuarios")
//    @PreAuthorize("hasAnyRole('ADMINISTRADORES', 'USERS')")  --> feito agora pelo WebSecurityConfig
    public String users(){
        return "Usuário Autorizado!";
    }

    @GetMapping("/administradores")
//    @PreAuthorize("hasRole('ADMINISTRADORES')")  --> feito agora pelo WebSecurityConfig
    public String administradores(){
        return "Administrador Autorizado!";
    }
}
