

#### Autenticação Simples
**MÉTODO 1**
- **Desabilitando a geração aleatória para o 'user'**
  No 'application.properties' incluímos as linhas:
    ````properties
    spring.security.user.name=usuario
    spring.security.user.password=123.usuario
    spring.security.user.roles=USERS  
  ````
**MÉTODO 2**
- **Em memória** (permite criar mais de 1 usuário e perfis)
  Para isso criamos uma classe que extenda de ``WebSecurityConfigurerAdapter`` e no método ``configure(AuthenticationManagerBuilder auth)`` criamos nossos usuários/roles e gerenciamos nossas autenticações.
  
  Após podemos configurar nossas rotas nos controllers com as annotations ``@PreAuthorize("hasAnyRole('ADMINISTRADORES', 'USERS')")`` gerenciando assim quais roles podem ter acesso ao recurso.

**MÉTODO 3**
- Usando o **Configure Adapter** para configurar o acesso as rotas fora dos controllers, para isso sobescrevemos o método ``configure(HttpSecurity http)``.

**MÉTODO 4**
- **Usando BD para persistência dos 'usuario' e 'roles'**.
  Para isso usamos o starter do JPA, incluindo as linhas abaixo no ``pom.xml``
  ````xml
  <dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  ````
  Após definimos nosso modelo de usuário (class @Entity)
  Após criamos nosso repositório do usuário (interface que extende JPARepository c/ @Repository)
  Após criamos um serviço (class ``SecurityDatabaseService`` que implementa ``UserDetailsService`` com a implementação do método ``loadUserByUsername(String username) ``) que trata-rá da transferência do usuário e seus detalhes armazezados para o Spring Security
  Desfazemos o **método 2** em ```WebSecurityConfig```
  Ainda ```WebSecurityConfig``` incluímos as linhas abaixo
  ````java
    @Autowired
    private SecurityDatabaseService securityDatabaseService;

    @Autowired
    private void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(securityDatabaseService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
  ````
  além de modifica a linha comentada pela de baixo:
  ````java
  ...
                .antMatchers("/usuarios").hasAnyRole("ADMINISTRADORES", "USERS")
  // -->        .anyRequest().authenticated().and().formLogin(); --> mudada para usar BD
                .anyRequest().authenticated().and().httpBasic();
  }
  ````
  Como nosso BD não estará populado, pois será usado um BD em memória, criamos a classe ``CarregaUsuariosBDSecurity``que implementa ``CommandLineRunner`` e seu método ``run`` para popular a tabela 'Usuario'
  E por fim, incluímos a dependência abaixo para termos um BD H2 na aplicação:
  ````xml
  <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
  </dependency>
  ````