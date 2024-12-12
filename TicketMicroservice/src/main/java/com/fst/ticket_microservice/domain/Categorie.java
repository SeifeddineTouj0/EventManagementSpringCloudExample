package com.fst.ticket_microservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Categorie {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategorie;

    @Column
    private String codeCategorie;

    @Column
    private String nomCategorie;

    @ManyToMany(mappedBy = "categories")
    private Set<Evenement> evenements;

}
