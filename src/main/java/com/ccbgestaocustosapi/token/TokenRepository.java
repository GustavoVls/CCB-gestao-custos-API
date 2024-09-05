package com.ccbgestaocustosapi.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = """
            select ccb.token.id_usuario\s
                        from ccb.Token\s
                        where ccb.token.token = :token""", nativeQuery = true)
    Integer findIdUsuarioByToken(@Param("token") String token);



    @Query(value = "select ccb.Usuarios.tipo_permissao " +
            "from ccb.Token " +
            "inner join ccb.Usuarios on Token.id_usuario = ccb.Usuarios.id_usuario " +
            "where ccb.Usuarios.id_usuario = :idUsuario and (Token.expired = false or Token.revoked = false)", nativeQuery = true)
    String findTipoPermissaoByIdUsuario(@Param("idUsuario") Long idUsuario);

    Optional<Token> findByCodeValid(String codeValid);


    @Query(value = "\n" +
            "select t.id  from ccb.token t where t.expired = true", nativeQuery = true)
    List<Object[]> findIdTokenExpired();



    @Modifying
    @Query(value = "delete from ccb.token s where s.id_usuario = :idUsuario"

            ,nativeQuery = true)
    void deleteByIdUsuario(@Param("idUsuario") Integer idUsuario);
}
