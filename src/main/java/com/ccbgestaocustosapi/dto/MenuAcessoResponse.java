package com.ccbgestaocustosapi.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class MenuAcessoResponse {

    @JsonIgnore
    private Integer idMenu;
    private String label;
    private String icon;
    private Boolean expanded;
    private List<Items> items = new ArrayList<>();

    @Data
    public static class Items {
        private String label;
        private String icon;
        private String routerLnk;
    }

    public void addItem(String label, String icon, String routerLnk) {
        Items item = new Items();
        item.setLabel(label);
        item.setIcon(icon);
        item.setRouterLnk(routerLnk);
        items.add(item);
    }
}