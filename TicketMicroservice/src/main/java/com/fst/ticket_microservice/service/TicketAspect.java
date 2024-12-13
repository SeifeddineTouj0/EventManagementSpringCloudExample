package com.fst.ticket_microservice.service;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TicketAspect {

    

    // Interception de la méthode ajouterTicketsEtAffecterAEvenementEtInternaute
    @AfterThrowing(
            pointcut = "execution(* com.fst.ticket_microservice.service.TicketService.ajouterTicketsEtAffecterAEvenementEtInternaute(..))",
            throwing = "exception"
    )
    public void handleException(Exception exception) {
        // Afficher le message
        System.out.println("Le nombre de places restantes dépasse le nombre de tickets demandés.");
    }
}
