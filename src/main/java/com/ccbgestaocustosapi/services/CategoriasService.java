package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.models.Categorias;
import com.ccbgestaocustosapi.repository.CategoriasRepository;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriasService {

    private final CategoriasRepository categoriasRepository;

    public PaginatedResponse<Categorias> getAllCategorias(int pageValue, Integer size, String valueOrderBY, boolean isOrderByAsc) {

        if (valueOrderBY != null){
            Page<Categorias> categoriasPage = this.categoriasRepository.findAll(PageRequest.of(pageValue, size, Sort.by(isOrderByAsc ?  Sort.Direction.ASC : Sort.Direction.DESC, valueOrderBY)));
            return new PaginatedResponse<>(categoriasPage.getContent(), categoriasPage.getTotalElements());
        }

        Page<Categorias> categoriasPage = this.categoriasRepository.findAll(PageRequest.of(pageValue, size));
        return new PaginatedResponse<>(categoriasPage.getContent(), categoriasPage.getTotalElements());
    }

    public PaginatedResponse<Categorias> getbyCategorias(Integer id) {
        Optional<Categorias> resultId = this.categoriasRepository.findById(id);
        return new PaginatedResponse<>(resultId.stream().toList(), 0);
    }

    @Transactional
    public void createNewCategoria(Categorias categorias) {
        Categorias categoriasValue = new Categorias();
        categoriasValue.setDescricaoCategoria(categorias.getDescricaoCategoria());
        categoriasValue.setTipoCategoria(categorias.getTipoCategoria());
        this.categoriasRepository.save(categoriasValue);
    }

    @Transactional
    public void updateCategoria(Categorias categorias) {
        Optional<Categorias> idFindValues = this.categoriasRepository.findById(categorias.getIdCategoria());
        if (idFindValues.isPresent()) {
            Categorias existingCategoria = idFindValues.get();
            boolean updated = false;
            if (categorias.getDescricaoCategoria() != null) {
                existingCategoria.setDescricaoCategoria(categorias.getDescricaoCategoria()) ;
                updated = true;
            }

            if (categorias.getTipoCategoria() != null) {
                existingCategoria.setTipoCategoria(categorias.getTipoCategoria());
                updated = true;
            }


            if (updated) {
                this.categoriasRepository.save(existingCategoria);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não encontrada.");
        }

    }

    @Transactional
    public void deleteCategoria(Integer id) {
        Optional<Categorias> idEncontrado = this.categoriasRepository.findById(id);

        if (idEncontrado.isPresent()) {
            try{
                this.categoriasRepository.deleteById(id);
            }catch (Exception e){
                throw new DataIntegrityViolationException("Essa categoria já está vinculada com outros serviços. " +
                        "Realize primeiramente a remoção dos serivços vinculados para prosseguir com a remoção.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da Categoria não encontrado para realizar a remoção.");
        }
    }
}