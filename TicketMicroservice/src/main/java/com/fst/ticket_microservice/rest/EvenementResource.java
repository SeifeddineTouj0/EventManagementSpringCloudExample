package com.fst.ticket_microservice.rest;

import com.fst.ticket_microservice.model.EvenementDTO;
import com.fst.ticket_microservice.model.TypeTicket;
import com.fst.ticket_microservice.service.EvenementService;
import com.fst.ticket_microservice.util.ReferencedException;
import com.fst.ticket_microservice.util.ReferencedWarning;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/evenements", produces = MediaType.APPLICATION_JSON_VALUE)
public class EvenementResource {

    private final EvenementService evenementService;

    public EvenementResource(final EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    @GetMapping
    public ResponseEntity<List<EvenementDTO>> getAllEvenements() {
        return ResponseEntity.ok(evenementService.findAll());
    }

    @GetMapping("/{idEvenement}")
    public ResponseEntity<EvenementDTO> getEvenement(
            @PathVariable(name = "idEvenement") final Long idEvenement) {
        return ResponseEntity.ok(evenementService.get(idEvenement));
    }

    @PostMapping
    public ResponseEntity<Long> createEvenement(
            @RequestBody @Valid final EvenementDTO evenementDTO) {
        final Long createdIdEvenement = evenementService.create(evenementDTO);
        return new ResponseEntity<>(createdIdEvenement, HttpStatus.CREATED);
    }

    @PutMapping("/{idEvenement}")
    public ResponseEntity<Long> updateEvenement(
            @PathVariable(name = "idEvenement") final Long idEvenement,
            @RequestBody @Valid final EvenementDTO evenementDTO) {
        evenementService.update(idEvenement, evenementDTO);
        return ResponseEntity.ok(idEvenement);
    }

    @DeleteMapping("/{idEvenement}")
    public ResponseEntity<Void> deleteEvenement(
            @PathVariable(name = "idEvenement") final Long idEvenement) {
        final ReferencedWarning referencedWarning = evenementService.getReferencedWarning(idEvenement);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        evenementService.delete(idEvenement);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/montant-par-type")
    public ResponseEntity<Double> montantRecupereParEvtEtTypeTicket(
            @RequestParam String nomEvt,
            @RequestParam TypeTicket typeTicket) {
        Double montant = evenementService.montantRecupereParEvtEtTypeTicket(nomEvt, typeTicket);
        return ResponseEntity.ok(montant);
    }

    @GetMapping("/internaute-plus-actif")
    public String internauteLePlusActif() {
        return evenementService.internauteLePlusActif();
    }

}
