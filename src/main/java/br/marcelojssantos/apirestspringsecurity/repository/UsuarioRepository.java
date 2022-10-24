package br.marcelojssantos.apirestspringsecurity.repository;

import br.marcelojssantos.apirestspringsecurity.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT e FROM Usuario e JOIN FETCH e.papeis WHERE e.login =  (:login)")
    public Usuario findByLogin(@Param("login") String login);
}
