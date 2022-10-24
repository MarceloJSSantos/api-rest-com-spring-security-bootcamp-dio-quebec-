package br.marcelojssantos.apirestspringsecurity.security;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JWTObject {
    private String subject; //nome do usuário
    private Date issuedAt; //data de criação do token
    private Date expiration; //data de expiração do token
    private List<String> roles; //perfis de acesso

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issueAt) {
        this.issuedAt = issueAt;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date experation) {
        this.expiration = experation;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setRoles(String ... roles) {
        this.roles = Arrays.asList(roles);
    }
}
