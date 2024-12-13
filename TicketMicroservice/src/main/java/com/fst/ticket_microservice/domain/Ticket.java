package com.fst.ticket_microservice.domain;

import com.fst.ticket_microservice.model.TypeTicket;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Ticket {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    @Column
    private String codeTicket;

    @Column
    private Double prixTicket;

    @Column
    @Enumerated(EnumType.STRING)
    private TypeTicket typeTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evenements_id", nullable = false)
    private Evenement evenements;

    private Long idInternaute;

}
