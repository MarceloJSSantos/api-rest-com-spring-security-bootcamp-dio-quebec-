package br.marcelojssantos.apirestspringsecurity.service;

import br.marcelojssantos.apirestspringsecurity.model.Usuario;
import br.marcelojssantos.apirestspringsecurity.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SecurityDatabaseService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuarioEntity = usuarioRepository.findByLogin(username);
        if(usuarioEntity==null)
            throw new UsernameNotFoundException(username);
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        usuarioEntity.getPapeis().forEach(papel -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + papel));
        });
        UserDetails usuario = new User(usuarioEntity.getLogin(),
                usuarioEntity.getSenha(),
                authorities);
        return usuario;
    }
}
