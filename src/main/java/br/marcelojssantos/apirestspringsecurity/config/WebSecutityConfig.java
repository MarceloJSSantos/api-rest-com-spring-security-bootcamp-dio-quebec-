package br.marcelojssantos.apirestspringsecurity.config;

import br.marcelojssantos.apirestspringsecurity.security.JWTFilter;
import br.marcelojssantos.apirestspringsecurity.service.SecurityDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecutityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

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
        http.headers().frameOptions().disable();
        http.cors().and().csrf().disable()
                .addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .antMatchers(HttpMethod.POST,"/login-security").permitAll()
                .antMatchers(HttpMethod.POST,"/users").permitAll()
                .antMatchers(HttpMethod.GET,"/users").hasAnyRole("USERS","ADMINISTRADORES")
                .antMatchers("/administradores").hasRole("ADMINISTRADORES")
                .antMatchers("/usuarios").hasAnyRole("ADMINISTRADORES", "USERS")
//                .anyRequest().authenticated().and().formLogin(); --> mudada para usar BD
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        ;
    }

    //permite acessarmos o console do H2
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }
}
