package com.fst.ticket_microservice.repos;

import com.fst.ticket_microservice.domain.Evenement;
import com.fst.ticket_microservice.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findFirstByEvenements(Evenement evenement);

}
