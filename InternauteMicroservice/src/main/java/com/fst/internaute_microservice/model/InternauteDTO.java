package com.fst.internaute_microservice.model;


public class InternauteDTO {

    private Long idInternaute;
    private Long identifiant;
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
