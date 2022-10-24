package br.marcelojssantos.apirestspringsecurity.service;

import br.marcelojssantos.apirestspringsecurity.model.Usuario;
import br.marcelojssantos.apirestspringsecurity.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    public void createUsuario(Usuario usuario){
        String pass = usuario.getSenha();
        //criptografa a senha antes de salvar no BD
        usuario.setSenha(encoder.encode(pass));
        repository.save(usuario);
    }
}
