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
    private List<Children> children = new ArrayList<>();

    @Data
    public static class Children {
        private String label;
        private String icon;
        private String data;
    }

    public void addItem(String label, String icon, String data) {
        Children item = new Children();
        item.setLabel(label);
        item.setIcon(icon);
        item.setData(data);
        children.add(item);
    }
}