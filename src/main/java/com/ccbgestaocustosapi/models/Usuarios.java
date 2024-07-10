package com.ccbgestaocustosapi.models;


import com.ccbgestaocustosapi.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "USUARIOS", schema = "CCB",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"nome_usuario", "email_usuario"}),
                @UniqueConstraint(columnNames = {"nome_usuario"}),
                @UniqueConstraint(columnNames = {"email_usuario"})
        })
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuarios implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_id_usuario_seq")
    @SequenceGenerator(schema = "CCB", name = "usuarios_id_usuario_seq", sequenceName = "usuarios_id_usuario_seq", allocationSize = 1)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @ManyToOne
    @JoinColumn(name = "adm_id")
    private Administracao adm;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "setor_id")
    private Setores setor;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "igr_id")
    private CasaOracoes igr;

    @Column(name = "nome_usuario", nullable = false, length = 100)
    private String nome;
    @Column(name = "senha_usuario", nullable = false, length = 100)
    private String senha;

    @Column(name = "email_usuario", nullable = false, unique = true, length = 100)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_permissao", nullable = false, length = 50)
    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}