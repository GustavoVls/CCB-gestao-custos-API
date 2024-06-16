package com.ccbgestaocustosapi.token;

import com.ccbgestaocustosapi.models.Usuarios;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TOKEN",schema="CCB", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"token", "code_valid"}),
        @UniqueConstraint(columnNames = {"token"}),
        @UniqueConstraint(columnNames = {"code_valid"})
})
public class Token {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "token_seq")
    @SequenceGenerator(schema = "CCB", name = "token_seq", sequenceName = "token_seq", allocationSize = 1)
    public Integer id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @Column(unique = true, name = "code_valid")
    public String codeValid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    public Usuarios user;

}
