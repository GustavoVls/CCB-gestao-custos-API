package com.ccbgestaocustosapi.controllers;


import com.ccbgestaocustosapi.models.Categorias;
import com.ccbgestaocustosapi.services.CategoriasService;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.CentralExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-ccb/categorias")
@RequiredArgsConstructor
public class CategoriasController {


    private final CategoriasService categoriasService;


    @GetMapping
    public ResponseEntity<PaginatedResponse<Categorias>> findCategorias(@RequestParam Integer page,
                                                                        @RequestParam Integer size,
                                                                        @RequestParam(required = false) Integer id) {
        try {
            // caso não tenha nenhum filtro, ele realizar um getAll
            if (id == null) {
                int pageValue = page - 1;
                PaginatedResponse<Categorias> response = this.categoriasService.getAllCategorias(pageValue, size);
                return ResponseEntity.ok(response);
            }
            PaginatedResponse<Categorias> response = this.categoriasService.getbyCategorias(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Erro na busca de dados de Categorias.");
        }
    }


    @PostMapping
    public ResponseEntity<PaginatedResponse<Categorias>> createNewCategoria(@RequestBody Categorias categorias) {
        try {
            this.categoriasService.createNewCategoria(categorias);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Categoria cadastrada com sucesso", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Não foi possível cadastrar essa Categoria.");
        }
    }

    @PutMapping
    public ResponseEntity<PaginatedResponse<Categorias>> updateCategoria(@RequestBody Categorias categorias) {
        try {
            this.categoriasService.updateCategoria(categorias);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Campos atualizados com sucesso.", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<PaginatedResponse<Categorias>> deleteCategoria(@RequestParam Integer id) {
        try {
            this.categoriasService.deleteCategoria(id);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Categoria deletada com sucesso.", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }
}
