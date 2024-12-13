package com.fst.ticket_microservice.repos;

import com.fst.ticket_microservice.domain.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Optional<Categorie> findByCodeCategorie(String codeCategorie);
}
