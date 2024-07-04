package com.ccbgestaocustosapi.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "MENU_ACESSO", schema = "CCB",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id_menu", "id_acesso"}),
                @UniqueConstraint(columnNames = "id_menu"),
                @UniqueConstraint(columnNames = "id_acesso"),
        })
@AllArgsConstructor
@NoArgsConstructor
public class MenuAcesso {

    @Id
    @Column(nullable = false, name = "id_menu")
    private Integer idMenu;

    @Column( name = "id_acesso")
    private Integer idAcesso;

    @Column(name = "id_menu_pai")
    private Integer idMenuSuper;

    @Column(name = "nome_menu", length = 50)
    private String nomeMenu;

    @Column(name = "icon_class", length = 30)
    private String iconClass;

    @Column(name = "path_route", length = 50)
    private String pathRoute;

}
