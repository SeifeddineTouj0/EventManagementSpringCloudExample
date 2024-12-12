package com.fst.internaute_microservice.rest;

import com.fst.internaute_microservice.model.InternauteDTO;
import com.fst.internaute_microservice.service.InternauteService;
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
@RequestMapping(value = "/api/internautes", produces = MediaType.APPLICATION_JSON_VALUE)
public class InternauteResource {

    private final InternauteService internauteService;

    public InternauteResource(final InternauteService internauteService) {
        this.internauteService = internauteService;
    }

    @GetMapping
    public ResponseEntity<List<InternauteDTO>> getAllInternautes() {
        return ResponseEntity.ok(internauteService.findAll());
    }

    @GetMapping("/{idInternaute}")
    public ResponseEntity<InternauteDTO> getInternaute(
            @PathVariable(name = "idInternaute") final Long idInternaute) {
        return ResponseEntity.ok(internauteService.get(idInternaute));
    }

    @PostMapping
    public ResponseEntity<Long> createInternaute(
            @RequestBody @Valid final InternauteDTO internauteDTO) {
        final Long createdIdInternaute = internauteService.create(internauteDTO);
        return new ResponseEntity<>(createdIdInternaute, HttpStatus.CREATED);
    }

    @PutMapping("/{idInternaute}")
    public ResponseEntity<Long> updateInternaute(
            @PathVariable(name = "idInternaute") final Long idInternaute,
            @RequestBody @Valid final InternauteDTO internauteDTO) {
        internauteService.update(idInternaute, internauteDTO);
        return ResponseEntity.ok(idInternaute);
    }

    @DeleteMapping("/{idInternaute}")
    public ResponseEntity<Void> deleteInternaute(
            @PathVariable(name = "idInternaute") final Long idInternaute) {
        internauteService.delete(idInternaute);
        return ResponseEntity.noContent().build();
    }

}
