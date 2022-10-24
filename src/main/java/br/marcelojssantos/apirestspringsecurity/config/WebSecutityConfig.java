package br.marcelojssantos.apirestspringsecurity.config;

import br.marcelojssantos.apirestspringsecurity.service.SecurityDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecutityConfig extends WebSecurityConfigurerAdapter {

//    Removido pois passaremos usar uma tabela de usuários armazenada em BD
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("usuario")
//                .password("{noop}123.usuario")
//                .roles("USERS")
//                .and()
//                .withUser("admin")
//                .password("{noop}123.admin")
//                .roles("ADMINISTRADORES");
//    }

    @Autowired
    private SecurityDatabaseService securityDatabaseService;

    @Autowired
    private void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(securityDatabaseService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .antMatchers("/administradores").hasRole("ADMINISTRADORES")
                .antMatchers("/usuarios").hasAnyRole("ADMINISTRADORES", "USERS")
//                .anyRequest().authenticated().and().formLogin(); --> mudada para usar BD
                .anyRequest().authenticated().and().httpBasic();
    }
}
