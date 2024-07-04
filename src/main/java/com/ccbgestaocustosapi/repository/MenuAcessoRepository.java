package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.MenuAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuAcessoRepository extends JpaRepository<MenuAcesso, Integer> {

    @Query(value = """

            select
            \t ma.id_menu as idMenu, ma.id_menu_pai as idMenuPai, ma.nome_menu as nomeMenu, ma.icon_class as iconClass, ma.path_route as pathRoute
            from
            \tccb.menu_acesso ma
            inner join ccb.cruzamento_perfil_acesso cpa on
            \tma.id_acesso = cpa.id_acesso
            inner join ccb.cruzamento_perfil_usuario cpu on
            \tcpa.id_perfil = cpu.id_perfil
            where cpu.id_usuario  = :idUsuario
            order by ma.id_menu_pai is not null""", nativeQuery = true)
    List<Object[]> findAllMenuAcessoByIdUsuario(@Param("idUsuario") Integer idUsuario);
}
