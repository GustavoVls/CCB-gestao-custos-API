package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.FindMenuAcessoByIdResponse;
import com.ccbgestaocustosapi.dto.MenuAcessoResponse;
import com.ccbgestaocustosapi.repository.MenuAcessoRepository;
import com.ccbgestaocustosapi.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuAcessoService {


    private final TokenRepository tokenRepository;

    private final MenuAcessoRepository menuAcessoRepository;

    // TODO: 03/07/2024 verificar se existe uma maneira mais elaborada que dê mais desempenho a aplicação  
    
    public List<MenuAcessoResponse> visualizacoesMenu(String token) {

        // Encontra o idUsuario pelo token
        Integer IdUsuarioFinded = tokenRepository.findIdUsuarioByToken(token);


        List<Object[]> values = menuAcessoRepository.findAllMenuAcessoByIdUsuario(IdUsuarioFinded);

        List<FindMenuAcessoByIdResponse> listaDto = new ArrayList<>();
        
        //adiciona o resultado da query em um dto
        for (Object[] resultado : values) {
            Integer idMenu = (Integer) resultado[0];
            Integer idMenuPai = (Integer) resultado[1];
            String nomeMenu = (String) resultado[2];
            String iconClass = (String) resultado[3];
            String pathRoute = (String) resultado[4];

            FindMenuAcessoByIdResponse dto = new FindMenuAcessoByIdResponse();
            dto.setIdMenu(idMenu);
            dto.setIdMenuPai(idMenuPai);
            dto.setNomeMenu(nomeMenu);
            dto.setIconClass(iconClass);
            dto.setPathRoute(pathRoute);
            listaDto.add(dto);
        }


        List<MenuAcessoResponse> menuResultList = new ArrayList<>();

        for(FindMenuAcessoByIdResponse value: listaDto){

            MenuAcessoResponse menuAcessoResponse = new MenuAcessoResponse();
            
            if (value.getIdMenuPai() == null){
                menuAcessoResponse.setLabel(value.getNomeMenu());
                menuAcessoResponse.setIcon(value.getIconClass());
                menuAcessoResponse.setIdMenu(value.getIdMenu());
                menuResultList.add(menuAcessoResponse);

            }else {
                for (MenuAcessoResponse menu: menuResultList) {
                    if ((value.getIdMenuPai().equals(menu.getIdMenu()))){
                        menu.addItem(value.getNomeMenu(), value.getIconClass(), value.getPathRoute());
                    }
                }
            }

        }
        return menuResultList;
    }
}
