package br.marcelojssantos.apirestspringsecurity.start;

import br.marcelojssantos.apirestspringsecurity.model.Usuario;
import br.marcelojssantos.apirestspringsecurity.repository.UsuarioRepository;
import br.marcelojssantos.apirestspringsecurity.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CarregaUsuariosBDSecurity implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    UsuarioService service;
    @Override
    public void run(String... args) throws Exception {
        Usuario usuario = usuarioRepository.findByLogin("admin");
        if(usuario==null){
            usuario = new Usuario("Usuário Administrador", "admin", "123456");
            usuario.getPapeis().add("ADMINISTRADORES");
            //adaptado para uso de criptografia
            service.createUsuario(usuario);
        }

        usuario = usuarioRepository.findByLogin("user");
        if(usuario==null){
            usuario = new Usuario("Usuário Inicial", "user", "123456");
            usuario.getPapeis().add("USERS");
            //adaptado para uso de criptografia
            service.createUsuario(usuario);
        }
    }
}
