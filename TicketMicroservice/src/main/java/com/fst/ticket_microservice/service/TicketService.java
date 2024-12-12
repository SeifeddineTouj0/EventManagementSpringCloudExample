package com.fst.ticket_microservice.service;

import com.fst.ticket_microservice.domain.Evenement;
import com.fst.ticket_microservice.domain.Ticket;
import com.fst.ticket_microservice.model.TicketDTO;
import com.fst.ticket_microservice.repos.EvenementRepository;
import com.fst.ticket_microservice.repos.TicketRepository;
import com.fst.ticket_microservice.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EvenementRepository evenementRepository;

    public TicketService(final TicketRepository ticketRepository,
            final EvenementRepository evenementRepository) {
        this.ticketRepository = ticketRepository;
        this.evenementRepository = evenementRepository;
    }

    public List<TicketDTO> findAll() {
        final List<Ticket> tickets = ticketRepository.findAll(Sort.by("idTicket"));
        return tickets.stream()
                .map(ticket -> mapToDTO(ticket, new TicketDTO()))
                .toList();
    }

    public TicketDTO get(final Long idTicket) {
        return ticketRepository.findById(idTicket)
                .map(ticket -> mapToDTO(ticket, new TicketDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TicketDTO ticketDTO) {
        final Ticket ticket = new Ticket();
        mapToEntity(ticketDTO, ticket);
        return ticketRepository.save(ticket).getIdTicket();
    }

    public void update(final Long idTicket, final TicketDTO ticketDTO) {
        final Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(NotFoundException::new);
        mapToEntity(ticketDTO, ticket);
        ticketRepository.save(ticket);
    }

    public void delete(final Long idTicket) {
        ticketRepository.deleteById(idTicket);
    }

    private TicketDTO mapToDTO(final Ticket ticket, final TicketDTO ticketDTO) {
        ticketDTO.setIdTicket(ticket.getIdTicket());
        ticketDTO.setCodeTicket(ticket.getCodeTicket());
        ticketDTO.setPrixTicket(ticket.getPrixTicket());
        ticketDTO.setTypeTicket(ticket.getTypeTicket());
        ticketDTO.setEvenements(ticket.getEvenements() == null ? null : ticket.getEvenements().getIdEvenement());
        return ticketDTO;
    }

    private Ticket mapToEntity(final TicketDTO ticketDTO, final Ticket ticket) {
        ticket.setCodeTicket(ticketDTO.getCodeTicket());
        ticket.setPrixTicket(ticketDTO.getPrixTicket());
        ticket.setTypeTicket(ticketDTO.getTypeTicket());
        final Evenement evenements = ticketDTO.getEvenements() == null ? null : evenementRepository.findById(ticketDTO.getEvenements())
                .orElseThrow(() -> new NotFoundException("evenements not found"));
        ticket.setEvenements(evenements);
        return ticket;
    }

}
