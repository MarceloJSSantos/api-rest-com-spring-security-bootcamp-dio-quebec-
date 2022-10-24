package br.marcelojssantos.apirestspringsecurity.controller;

import br.marcelojssantos.apirestspringsecurity.model.Usuario;
import br.marcelojssantos.apirestspringsecurity.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @PostMapping
    public void postUsuario(@RequestBody Usuario usuario){
        service.createUsuario(usuario);
    }
}
