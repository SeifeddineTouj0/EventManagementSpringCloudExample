package com.fst.ticket_microservice.rest;

import com.fst.ticket_microservice.model.TicketDTO;
import com.fst.ticket_microservice.service.TicketService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketResource {

    private final TicketService ticketService;

    public TicketResource(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @GetMapping("/{idTicket}")
    public ResponseEntity<TicketDTO> getTicket(
            @PathVariable(name = "idTicket") final Long idTicket) {
        return ResponseEntity.ok(ticketService.get(idTicket));
    }

    @PostMapping
    public ResponseEntity<Long> createTicket(@RequestBody @Valid final TicketDTO ticketDTO) {
        final Long createdIdTicket = ticketService.create(ticketDTO);
        return new ResponseEntity<>(createdIdTicket, HttpStatus.CREATED);
    }

    @PutMapping("/{idTicket}")
    public ResponseEntity<Long> updateTicket(@PathVariable(name = "idTicket") final Long idTicket,
            @RequestBody @Valid final TicketDTO ticketDTO) {
        ticketService.update(idTicket, ticketDTO);
        return ResponseEntity.ok(idTicket);
    }

    @DeleteMapping("/{idTicket}")
    public ResponseEntity<Void> deleteTicket(@PathVariable(name = "idTicket") final Long idTicket) {
        ticketService.delete(idTicket);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<TicketDTO>> ajouterTicketsEtAffecterAEvenementEtInternaute(
            @RequestBody List<TicketDTO> ticketDTOs,
            @RequestParam Long idEvenement,
            @RequestParam Long idInternaute) {
        List<TicketDTO> savedTickets = ticketService.ajouterTicketsEtAffecterAEvenementEtInternaute(ticketDTOs, idEvenement, idInternaute);
        return ResponseEntity.ok(savedTickets);
    }

}
