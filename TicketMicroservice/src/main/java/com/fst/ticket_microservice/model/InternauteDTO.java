package com.fst.ticket_microservice.model;


public class InternauteDTO {

    private Long idInternaute;
    private String identifiant;
    private TrancheAge trancheAge;

    public Long getIdInternaute() {
        return idInternaute;
    }

    public void setIdInternaute(final Long idInternaute) {
        this.idInternaute = idInternaute;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(final String identifiant) {
        this.identifiant = identifiant;
    }

    public TrancheAge getTrancheAge() {
        return trancheAge;
    }

    public void setTrancheAge(final TrancheAge trancheAge) {
        this.trancheAge = trancheAge;
    }

}
