package com.fst.ticket_microservice.service;

import com.fst.ticket_microservice.domain.Categorie;
import com.fst.ticket_microservice.model.CategorieDTO;
import com.fst.ticket_microservice.repos.CategorieRepository;
import com.fst.ticket_microservice.repos.EvenementRepository;
import com.fst.ticket_microservice.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class CategorieService {

    private final CategorieRepository categorieRepository;
    private final EvenementRepository evenementRepository;

    public CategorieService(final CategorieRepository categorieRepository,
            final EvenementRepository evenementRepository) {
        this.categorieRepository = categorieRepository;
        this.evenementRepository = evenementRepository;
    }

    public List<CategorieDTO> findAll() {
        final List<Categorie> categories = categorieRepository.findAll(Sort.by("idCategorie"));
        return categories.stream()
                .map(categorie -> mapToDTO(categorie, new CategorieDTO()))
                .toList();
    }

    public CategorieDTO get(final Long idCategorie) {
        return categorieRepository.findById(idCategorie)
                .map(categorie -> mapToDTO(categorie, new CategorieDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CategorieDTO categorieDTO) {
        final Categorie categorie = new Categorie();
        mapToEntity(categorieDTO, categorie);
        return categorieRepository.save(categorie).getIdCategorie();
    }

    public void update(final Long idCategorie, final CategorieDTO categorieDTO) {
        final Categorie categorie = categorieRepository.findById(idCategorie)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categorieDTO, categorie);
        categorieRepository.save(categorie);
    }

    public void delete(final Long idCategorie) {
        final Categorie categorie = categorieRepository.findById(idCategorie)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        evenementRepository.findAllByCategories(categorie)
                .forEach(evenement -> evenement.getCategories().remove(categorie));
        categorieRepository.delete(categorie);
    }

    private CategorieDTO mapToDTO(final Categorie categorie, final CategorieDTO categorieDTO) {
        //categorieDTO.setIdCategorie(categorie.getIdCategorie());
        categorieDTO.setCodeCategorie(categorie.getCodeCategorie());
        categorieDTO.setNomCategorie(categorie.getNomCategorie());
        return categorieDTO;
    }

    private Categorie mapToEntity(final CategorieDTO categorieDTO, final Categorie categorie) {
        categorie.setCodeCategorie(categorieDTO.getCodeCategorie());
        categorie.setNomCategorie(categorieDTO.getNomCategorie());
        return categorie;
    }

}
