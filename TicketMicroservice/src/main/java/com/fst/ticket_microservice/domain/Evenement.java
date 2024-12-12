package com.fst.ticket_microservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Evenement {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvenement;

    @Column
    private String nomEvenement;

    @Column
    private Long nbPlacesRestantes;

    @Column
    private Long nbPlacesRestants;

    @Column
    private LocalDate dateEvenement;

    @OneToMany(mappedBy = "evenements")
    private Set<Ticket> idTicket;

    @ManyToMany
    @JoinTable(
            name = "EventCategories",
            joinColumns = @JoinColumn(name = "idEvenement"),
            inverseJoinColumns = @JoinColumn(name = "idCategorie")
    )
    private Set<Categorie> categories;

}
