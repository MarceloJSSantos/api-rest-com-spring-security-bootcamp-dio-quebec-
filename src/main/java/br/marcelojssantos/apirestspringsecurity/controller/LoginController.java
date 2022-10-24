package br.marcelojssantos.apirestspringsecurity.controller;

import br.marcelojssantos.apirestspringsecurity.dto.Login;
import br.marcelojssantos.apirestspringsecurity.dto.Sessao;
import br.marcelojssantos.apirestspringsecurity.model.Usuario;
import br.marcelojssantos.apirestspringsecurity.repository.UsuarioRepository;
import br.marcelojssantos.apirestspringsecurity.security.JWTCreator;
import br.marcelojssantos.apirestspringsecurity.security.JWTObject;
import br.marcelojssantos.apirestspringsecurity.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private SecurityConfig securityConfig;

    @PostMapping("/login-security")
    public Sessao logar(@RequestBody Login login){
        Usuario usuario = repository.findByLogin(login.getUsername());
        if(usuario!=null){
            boolean passwordOK = encoder.matches(login.getPassword(), usuario.getSenha());
            if(!passwordOK)
                throw new RuntimeException("Senha inválida para o login '" + login.getUsername() + "'!");

            var sessao = new Sessao();
            sessao.setLogin(usuario.getLogin());

            var jwtObj = new JWTObject();
            jwtObj.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObj.setExpiration(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION));
            jwtObj.setRoles(usuario.getPapeis());
            sessao.setToken(JWTCreator.create(SecurityConfig.PREFIX,
                    SecurityConfig.KEY,
                    jwtObj));
            return sessao;
        } else{
            throw new RuntimeException("Erro ao tentar fazer login!");
        }
    }
}
