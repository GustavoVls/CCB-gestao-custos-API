package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.CategoriaFiltroResponse;
import com.ccbgestaocustosapi.models.Categorias;
import com.ccbgestaocustosapi.repository.CategoriasRepository;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.genericExceptions.BadCredentialException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

        Page<Categorias> categoriasPage = this.categoriasRepository.findAll(PageRequest.of(pageValue, size, Sort.by(Sort.Direction.ASC, "idCategoria")));
        return new PaginatedResponse<>(categoriasPage.getContent(), categoriasPage.getTotalElements());
    }

    public PaginatedResponse<CategoriaFiltroResponse> getbyCategorias(String descricao, String valueOrderBY, boolean isOrderByAsc) {

        List<Object[]> resultId;
        if (valueOrderBY == null){
            resultId = this.categoriasRepository.findByDescricao(descricao);
        }else {
            resultId = this.categoriasRepository.findByDescricaoOrderBy(descricao, valueOrderBY, isOrderByAsc ? "asc" : "desc");
        }

        List<CategoriaFiltroResponse> categoriasDTOs = new ArrayList<>();

        for (Object[] resultado : resultId) {
            Integer totalRecords = ((Number) resultado[0]).intValue();
            Integer idCategoria = ((Number) resultado[1]).intValue();
            String descricaoCategoria = (String) resultado[2];
            String tipoCategoria = (String) resultado[3];

            CategoriaFiltroResponse dto = new CategoriaFiltroResponse(idCategoria, descricaoCategoria, tipoCategoria, totalRecords);
            categoriasDTOs.add(dto);
        }
        return new PaginatedResponse<>(categoriasDTOs.stream().toList(), categoriasDTOs.isEmpty() ? 0 : categoriasDTOs.get(0).getTotalRecords());
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
                throw new BadCredentialException("Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new BadCredentialException("Categoria não encontrada.");
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
            throw new BadCredentialException("Id da Categoria não encontrado para realizar a remoção.");
        }
    }
}