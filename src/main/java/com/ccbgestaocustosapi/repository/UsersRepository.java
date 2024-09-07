package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Usuarios, Integer> {

    Optional<Usuarios> findByEmail(String email);

    Boolean existsByNome(String nome);

    Boolean existsByEmail(String email);


    @Query(value =
            """
                    \s
                    select  COUNT(*) over() as total_records,
                                            	u.id_usuario,
                                                u.nome_usuario ,
                                               	u.email_usuario,
                                                a.adm_nome,
                                                s.setor_nome,
                                                co.igr_id
                                                      from
                                                ccb.usuarios u
                                                 inner join ccb.administracao a on
                                                       u.adm_id = a.adm_id
                                                       left join ccb.setores s on
                                                       s.setor_id = u.setor_id
                                                       left join ccb.casa_oracoes co on
                                                       co.igr_id = u.igr_id\s
                                                        LIMIT :size OFFSET (:page - 1) * :size\s
                    """
            , nativeQuery = true)
    List<Object[]> findAllUsuarios(@Param("page") Integer page, @Param("size") Integer size);

    @Query(value =
            """
                     SELECT
                          COUNT(*) OVER() AS total_records,
                          u.id_usuario,
                          u.nome_usuario,
                          u.email_usuario,
                          a.adm_nome,
                          s.setor_nome,
                          co.igr_id
                      FROM
                          ccb.usuarios u
                          INNER JOIN ccb.administracao a ON u.adm_id = a.adm_id
                          LEFT JOIN ccb.setores s ON s.setor_id = u.setor_id
                          LEFT JOIN ccb.casa_oracoes co ON co.igr_id = u.igr_id
                      ORDER BY
                          CASE\s
                              WHEN :orderBy = 'idUsuario' THEN cast(u.id_usuario as varchar)
                              WHEN :orderBy = 'nomeUsuario' THEN u.nome_usuario
                              WHEN :orderBy = 'emailUsuario' THEN u.email_usuario
                              WHEN :orderBy = 'nomeAdm' THEN a.adm_nome
                              WHEN :orderBy = 'setorNome' THEN s.setor_nome
                              WHEN :orderBy = 'nomeIgr' THEN co.igr_nome
                          END\s
                      ASC
                      LIMIT :size OFFSET (:page - 1) * :size
                                                                                                
                                                        
                    """
            , nativeQuery = true)
    List<Object[]> findAllUsuariosOrderbyAsc(@Param("page") Integer page, @Param("size") Integer size, @Param("orderBy") String orderBY);



    @Query(value =
            """
                     SELECT
                          COUNT(*) OVER() AS total_records,
                          u.id_usuario,
                          u.nome_usuario,
                          u.email_usuario,
                          a.adm_nome,
                          s.setor_nome,
                          co.igr_id
                      FROM
                          ccb.usuarios u
                          INNER JOIN ccb.administracao a ON u.adm_id = a.adm_id
                          LEFT JOIN ccb.setores s ON s.setor_id = u.setor_id
                          LEFT JOIN ccb.casa_oracoes co ON co.igr_id = u.igr_id
                      ORDER BY
                          CASE\s
                              WHEN :orderBy = 'idUsuario' THEN cast(u.id_usuario as varchar)
                              WHEN :orderBy = 'nomeUsuario' THEN u.nome_usuario
                              WHEN :orderBy = 'emailUsuario' THEN u.email_usuario
                              WHEN :orderBy = 'nomeAdm' THEN a.adm_nome
                              WHEN :orderBy = 'setorNome' THEN s.setor_nome
                              WHEN :orderBy = 'nomeIgr' THEN co.igr_nome
                          END\s
                      DESC
                      LIMIT :size OFFSET (:page - 1) * :size
                                                                                                
                                                        
                    """
            , nativeQuery = true)
    List<Object[]> findAllUsuariosOrderbyDesc(@Param("page") Integer page, @Param("size") Integer size, @Param("orderBy") String orderBY);

    @Query(value =
            """
                    \s
                    select  COUNT(*) over() as total_records,
                                            	u.id_usuario,
                                                u.nome_usuario ,
                                               	u.email_usuario,
                                                a.adm_nome,
                                                s.setor_nome,
                                                co.igr_nome
                                                      from
                                                ccb.usuarios u
                                                 inner join ccb.administracao a on
                                                       u.adm_id = a.adm_id
                                                       left join ccb.setores s on
                                                       s.setor_id = u.setor_id
                                                       left join ccb.casa_oracoes co on
                                                       co.igr_id = u.igr_id\s
                                                  where upper(u.nome_usuario) like upper('%' || :nomeUsuario || '%')
                                                        \s
                    """
            , nativeQuery = true)
    List<Object[]> findByNomeUsuario(@Param("nomeUsuario") String nomeUsuario);




    @Query(value = """
              select ccb.usuarios.adm_id
                                    from ccb.usuarios\s
                                    where ccb.usuarios.id_usuario  = :idUsuario
            """, nativeQuery = true)
    Integer findAdmIdByIdUsuarios(@Param("idUsuario") Integer idUsuario);


}
