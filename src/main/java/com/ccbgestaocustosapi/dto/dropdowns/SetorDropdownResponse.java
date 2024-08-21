package com.ccbgestaocustosapi.dto.dropdowns;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetorDropdownResponse {

    private Integer setorId;
    private String setorNome;
}
