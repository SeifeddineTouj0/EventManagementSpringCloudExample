package com.fst.ticket_microservice.rest;

import com.fst.ticket_microservice.model.CategorieDTO;
import com.fst.ticket_microservice.service.CategorieService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategorieResource {

    private final CategorieService categorieService;

    public CategorieResource(final CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping
    public ResponseEntity<List<CategorieDTO>> getAllCategories() {
        return ResponseEntity.ok(categorieService.findAll());
    }

    @GetMapping("/{idCategorie}")
    public ResponseEntity<CategorieDTO> getCategorie(
            @PathVariable(name = "idCategorie") final Long idCategorie) {
        return ResponseEntity.ok(categorieService.get(idCategorie));
    }

    @PostMapping
    public ResponseEntity<Long> createCategorie(
            @RequestBody @Valid final CategorieDTO categorieDTO) {
        final Long createdIdCategorie = categorieService.create(categorieDTO);
        return new ResponseEntity<>(createdIdCategorie, HttpStatus.CREATED);
    }

    @PutMapping("/{idCategorie}")
    public ResponseEntity<Long> updateCategorie(
            @PathVariable(name = "idCategorie") final Long idCategorie,
            @RequestBody @Valid final CategorieDTO categorieDTO) {
        categorieService.update(idCategorie, categorieDTO);
        return ResponseEntity.ok(idCategorie);
    }

    @DeleteMapping("/{idCategorie}")
    public ResponseEntity<Void> deleteCategorie(
            @PathVariable(name = "idCategorie") final Long idCategorie) {
        categorieService.delete(idCategorie);
        return ResponseEntity.noContent().build();
    }

}
