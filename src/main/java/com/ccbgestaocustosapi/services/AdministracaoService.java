package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.models.Administracao;
import com.ccbgestaocustosapi.repository.AdministracaoRepository;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministracaoService {
   private final AdministracaoRepository administracaoRepository;


   public PaginatedResponse<Administracao> getAllAdministracao(int page, int size) {
      Pageable paginador = PageRequest.of(page, size);
      Page<Administracao> administracaoPage = this.administracaoRepository.findAll(paginador);
      return new PaginatedResponse<>(administracaoPage.getContent(), administracaoPage.getTotalElements(), null);
   }

}
