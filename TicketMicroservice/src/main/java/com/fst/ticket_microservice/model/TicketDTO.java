package com.fst.ticket_microservice.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketDTO {

    private Long idTicket;

    @Size(max = 255)
    private String codeTicket;

    private Double prixTicket;

    private TypeTicket typeTicket;

    @NotNull
    private Long evenements;

}
