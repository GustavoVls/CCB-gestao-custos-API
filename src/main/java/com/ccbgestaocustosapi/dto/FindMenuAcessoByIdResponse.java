package com.ccbgestaocustosapi.dto;


import lombok.Data;

@Data
public class FindMenuAcessoByIdResponse {
    private  Integer idMenu;
    private Integer idMenuPai;
    private String nomeMenu;
    private String iconClass;
    private String pathRoute;

}



