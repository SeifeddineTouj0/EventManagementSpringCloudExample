package com.fst.ticket_microservice.service;

import com.fst.ticket_microservice.domain.Categorie;
import com.fst.ticket_microservice.domain.Evenement;
import com.fst.ticket_microservice.domain.Ticket;
import com.fst.ticket_microservice.model.EvenementDTO;
import com.fst.ticket_microservice.repos.CategorieRepository;
import com.fst.ticket_microservice.repos.EvenementRepository;
import com.fst.ticket_microservice.repos.TicketRepository;
import com.fst.ticket_microservice.util.NotFoundException;
import com.fst.ticket_microservice.util.ReferencedWarning;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class EvenementService {

    private final EvenementRepository evenementRepository;
    private final CategorieRepository categorieRepository;
    private final TicketRepository ticketRepository;

    public EvenementService(final EvenementRepository evenementRepository,
            final CategorieRepository categorieRepository,
            final TicketRepository ticketRepository) {
        this.evenementRepository = evenementRepository;
        this.categorieRepository = categorieRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<EvenementDTO> findAll() {
        final List<Evenement> evenements = evenementRepository.findAll(Sort.by("idEvenement"));
        return evenements.stream()
                .map(evenement -> mapToDTO(evenement, new EvenementDTO()))
                .toList();
    }

    public EvenementDTO get(final Long idEvenement) {
        return evenementRepository.findById(idEvenement)
                .map(evenement -> mapToDTO(evenement, new EvenementDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EvenementDTO evenementDTO) {
        final Evenement evenement = new Evenement();
        mapToEntity(evenementDTO, evenement);
        return evenementRepository.save(evenement).getIdEvenement();
    }

    public void update(final Long idEvenement, final EvenementDTO evenementDTO) {
        final Evenement evenement = evenementRepository.findById(idEvenement)
                .orElseThrow(NotFoundException::new);
        mapToEntity(evenementDTO, evenement);
        evenementRepository.save(evenement);
    }

    public void delete(final Long idEvenement) {
        evenementRepository.deleteById(idEvenement);
    }

    private EvenementDTO mapToDTO(final Evenement evenement, final EvenementDTO evenementDTO) {
        evenementDTO.setIdEvenement(evenement.getIdEvenement());
        evenementDTO.setNomEvenement(evenement.getNomEvenement());
        evenementDTO.setNbPlacesRestantes(evenement.getNbPlacesRestantes());
        evenementDTO.setNbPlacesRestants(evenement.getNbPlacesRestants());
        evenementDTO.setDateEvenement(evenement.getDateEvenement());
        evenementDTO.setCategories(evenement.getCategories().stream()
                .map(categorie -> categorie.getIdCategorie())
                .toList());
        return evenementDTO;
    }

    private Evenement mapToEntity(final EvenementDTO evenementDTO, final Evenement evenement) {
        evenement.setNomEvenement(evenementDTO.getNomEvenement());
        evenement.setNbPlacesRestantes(evenementDTO.getNbPlacesRestantes());
        evenement.setNbPlacesRestants(evenementDTO.getNbPlacesRestants());
        evenement.setDateEvenement(evenementDTO.getDateEvenement());
        final List<Categorie> categories = categorieRepository.findAllById(
                evenementDTO.getCategories() == null ? Collections.emptyList() : evenementDTO.getCategories());
        if (categories.size() != (evenementDTO.getCategories() == null ? 0 : evenementDTO.getCategories().size())) {
            throw new NotFoundException("one of categories not found");
        }
        evenement.setCategories(new HashSet<>(categories));
        return evenement;
    }

    public ReferencedWarning getReferencedWarning(final Long idEvenement) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Evenement evenement = evenementRepository.findById(idEvenement)
                .orElseThrow(NotFoundException::new);
        final Ticket evenementsTicket = ticketRepository.findFirstByEvenements(evenement);
        if (evenementsTicket != null) {
            referencedWarning.setKey("evenement.ticket.evenements.referenced");
            referencedWarning.addParam(evenementsTicket.getIdTicket());
            return referencedWarning;
        }
        return null;
    }

}
