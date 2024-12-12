package com.fst.ticket_microservice.repos;

import com.fst.ticket_microservice.domain.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
