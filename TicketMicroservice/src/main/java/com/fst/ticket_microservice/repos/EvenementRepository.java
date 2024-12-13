package com.fst.ticket_microservice.repos;

import com.fst.ticket_microservice.domain.Categorie;
import com.fst.ticket_microservice.domain.Evenement;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface EvenementRepository extends JpaRepository<Evenement, Long> {

    Evenement findFirstByCategories(Categorie categorie);

    List<Evenement> findAllByCategories(Categorie categorie);

    Optional<Object> findByNomEvenement(String nomEvt);

    List<Evenement> findByCategories_IdCategorie(Long idCategorie);
}
