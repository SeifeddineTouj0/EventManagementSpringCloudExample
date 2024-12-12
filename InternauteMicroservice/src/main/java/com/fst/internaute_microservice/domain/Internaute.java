package com.fst.internaute_microservice.domain;

import com.fst.internaute_microservice.model.TrancheAge;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Internaute {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInternaute;

    @Column
    private Long identifiant;

    @Column
    @Enumerated(EnumType.STRING)
    private TrancheAge trancheAge;

    public Long getIdInternaute() {
        return idInternaute;
    }

    public void setIdInternaute(final Long idInternaute) {
        this.idInternaute = idInternaute;
    }

    public Long getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(final Long identifiant) {
        this.identifiant = identifiant;
    }

    public TrancheAge getTrancheAge() {
        return trancheAge;
    }

    public void setTrancheAge(final TrancheAge trancheAge) {
        this.trancheAge = trancheAge;
    }

}
