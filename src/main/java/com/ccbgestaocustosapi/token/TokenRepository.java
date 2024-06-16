package com.ccbgestaocustosapi.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value =
            """
        select t from Token t inner join Usuarios u\s
        on t.user.idUsuario = u.idUsuario\s
        where u.idUsuario = :id and (t.expired = false or t.revoked = false)\s
        """)
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);


    Optional<Token> findByCodeValid(String codeValid);
}
