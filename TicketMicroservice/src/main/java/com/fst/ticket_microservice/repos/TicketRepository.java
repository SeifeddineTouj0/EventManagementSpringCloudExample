package com.fst.ticket_microservice.repos;

import com.fst.ticket_microservice.domain.Evenement;
import com.fst.ticket_microservice.domain.Ticket;
import com.fst.ticket_microservice.model.TypeTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findFirstByEvenements(Evenement evenement);

    List<Ticket> findByEvenementsAndTypeTicket(Evenement evenement, TypeTicket typeTicket);
}
