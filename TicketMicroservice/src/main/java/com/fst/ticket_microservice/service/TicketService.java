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
        Evenement evenement = evenementRepository.findById(ticketDTO.getEvenements())
                .orElseThrow(() -> new NotFoundException("Evenement not found"));

        if(evenement.getNbPlacesRestantes()-1<0){
            throw new java.lang.UnsupportedOperationException("nombre de places demandées indisponible");
        }
        evenement.setNbPlacesRestantes(evenement.getNbPlacesRestantes()-1);

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

    public List<TicketDTO> ajouterTicketsEtAffecterAEvenementEtInternaute(List<TicketDTO> ticketDTOs,
                                                                          Long idEvenement,
                                                                          Long idInternaute) {
        // Fetch the associated Evenement and Internaute
        Evenement evenement = evenementRepository.findById(idEvenement)
                .orElseThrow(() -> new NotFoundException("Evenement not found"));

        if(evenement.getNbPlacesRestantes()-ticketDTOs.stream().count()<0){
            throw new java.lang.UnsupportedOperationException("nombre de places demandées indisponible");
        }
        evenement.setNbPlacesRestantes(evenement.getNbPlacesRestantes()-ticketDTOs.stream().count());

        // Map DTOs to entities and assign the evenement and internaute
        List<Ticket> tickets = ticketDTOs.stream().map(ticketDTO -> {
            Ticket ticket = mapToEntity(ticketDTO, new Ticket());
            ticket.setEvenements(evenement);
            ticket.setIdInternaute(idInternaute);
            return ticket;
        }).toList();

        // Save all tickets
        List<Ticket> savedTickets = ticketRepository.saveAll(tickets);

        // Convert saved entities back to DTOs and return
        return savedTickets.stream().map(ticket -> mapToDTO(ticket, new TicketDTO())).toList();
    }


    private TicketDTO mapToDTO(final Ticket ticket, final TicketDTO ticketDTO) {
        ticketDTO.setIdTicket(ticket.getIdTicket());
        ticketDTO.setCodeTicket(ticket.getCodeTicket());
        ticketDTO.setPrixTicket(ticket.getPrixTicket());
        ticketDTO.setEvenements(ticket.getEvenements() == null ? null : ticket.getEvenements().getIdEvenement());
        ticketDTO.setTypeTicket(ticket.getTypeTicket());
        ticketDTO.setIdInternaute(ticket.getIdInternaute());
        return ticketDTO;
    }

    private Ticket mapToEntity(final TicketDTO ticketDTO, final Ticket ticket) {
        ticket.setCodeTicket(ticketDTO.getCodeTicket());
        ticket.setPrixTicket(ticketDTO.getPrixTicket());
        ticket.setTypeTicket(ticketDTO.getTypeTicket());
        ticket.setIdInternaute(ticketDTO.getIdInternaute());
        final Evenement evenements = ticketDTO.getEvenements() == null ? null : evenementRepository.findById(ticketDTO.getEvenements())
                .orElseThrow(() -> new NotFoundException("evenements not found"));
        ticket.setEvenements(evenements);
        return ticket;
    }


}
