package br.marcelojssantos.apirestspringsecurity.start;

import br.marcelojssantos.apirestspringsecurity.model.Usuario;
import br.marcelojssantos.apirestspringsecurity.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CarregaUsuariosBDSecurity implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        Usuario usuario = usuarioRepository.findByLogin("admin");
        if(usuario==null){
            usuario = new Usuario("Usuário Administrador", "admin", "123456");
            usuario.getPapeis().add("ADMINISTRADORES");
            usuarioRepository.save(usuario);
        }

        usuario = usuarioRepository.findByLogin("user");
        if(usuario==null){
            usuario = new Usuario("Usuário Inicial", "user", "123456");
            usuario.getPapeis().add("USERS");
            usuarioRepository.save(usuario);
        }
    }
}
