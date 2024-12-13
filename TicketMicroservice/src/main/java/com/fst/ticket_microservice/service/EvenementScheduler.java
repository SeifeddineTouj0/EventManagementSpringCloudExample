package com.fst.ticket_microservice.service;

import com.fst.ticket_microservice.domain.Categorie;
import com.fst.ticket_microservice.domain.Evenement;
import com.fst.ticket_microservice.repos.CategorieRepository;
import com.fst.ticket_microservice.repos.EvenementRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvenementScheduler {
    private final CategorieRepository categorieRepository;
    private final EvenementRepository evenementRepository;

    public EvenementScheduler(CategorieRepository categorieRepository, EvenementRepository evenementRepository) {
        this.categorieRepository = categorieRepository;
        this.evenementRepository = evenementRepository;
    }

    @Scheduled(fixedRate = 15000) // Déclenchement toutes les 15 secondes
    public void listeEvenementsParCategorie() {
        // Récupérer toutes les catégories
        List<Categorie> categories = categorieRepository.findAll();

        // Parcourir chaque catégorie
        categories.forEach(categorie -> {
            // Récupérer les événements liés à cette catégorie
            List<Evenement> evenements = evenementRepository.findByCategories_IdCategorie(categorie.getIdCategorie());

            // Afficher les informations sur la console
            System.out.println("Catégorie : " + categorie.getNomCategorie());
            evenements.forEach(evenement ->
                    System.out.println("\t- " + evenement.getNomEvenement() + " (" + evenement.getDateEvenement() + ")"));
        });
    }
}
