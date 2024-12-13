package com.fst.ticket_microservice.service;

import com.fst.ticket_microservice.domain.Categorie;
import com.fst.ticket_microservice.domain.Evenement;
import com.fst.ticket_microservice.domain.Ticket;
import com.fst.ticket_microservice.model.CategorieDTO;
import com.fst.ticket_microservice.model.EvenementDTO;
import com.fst.ticket_microservice.model.InternauteDTO;
import com.fst.ticket_microservice.model.TypeTicket;
import com.fst.ticket_microservice.repos.CategorieRepository;
import com.fst.ticket_microservice.repos.EvenementRepository;
import com.fst.ticket_microservice.repos.TicketRepository;
import com.fst.ticket_microservice.util.NotFoundException;
import com.fst.ticket_microservice.util.ReferencedWarning;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Transactional
public class EvenementService {

    private final EvenementRepository evenementRepository;
    private final CategorieRepository categorieRepository;
    private final TicketRepository ticketRepository;
    private final RestTemplate restTemplate;


    public EvenementService(final EvenementRepository evenementRepository,
            final CategorieRepository categorieRepository,
            final TicketRepository ticketRepository,
                            final RestTemplate restTemplate) {
        this.evenementRepository = evenementRepository;
        this.categorieRepository = categorieRepository;
        this.ticketRepository = ticketRepository;
        this.restTemplate=restTemplate;
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

    public Double montantRecupereParEvtEtTypeTicket(String nomEvt, TypeTicket typeTicket) {
        // Récupérer l'événement par nom
        Evenement evenement = (Evenement) evenementRepository.findByNomEvenement(nomEvt)
                .orElseThrow(() -> new NotFoundException("Événement introuvable avec le nom : " + nomEvt));

        // Récupérer les tickets associés à l'événement
        List<Ticket> tickets = ticketRepository.findByEvenementsAndTypeTicket(evenement, typeTicket);

        // Calculer le montant total
        return tickets.stream()
                .mapToDouble(Ticket::getPrixTicket)
                .sum();
    }

    public String internauteLePlusActif() {
        // 1. Calculer l'internaute avec le plus de tickets
        Map<Long, Long> internauteTicketsCount = ticketRepository.findAll().stream()
                .collect(Collectors.groupingBy(Ticket::getIdInternaute, Collectors.counting()));

        Long mostActiveInternauteId = internauteTicketsCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new RuntimeException("Aucun internaute trouvé."))
                .getKey();

        // 2. Récupérer la liste des internautes via le microservice
        String url = "http://localhost:8085/api/internautes";
        InternauteDTO[] internautes = restTemplate.getForObject(url, InternauteDTO[].class);

        if (internautes == null || internautes.length == 0) {
            throw new RuntimeException("Impossible de récupérer les internautes.");
        }

        // 3. Trouver l'identifiant de l'internaute le plus actif
        for (InternauteDTO internaute : internautes) {
            if (internaute.getIdInternaute().equals(mostActiveInternauteId)) {
                return internaute.getIdentifiant();
            }
        }

        throw new RuntimeException("L'internaute le plus actif n'a pas été trouvé dans le microservice.");
    }


    private EvenementDTO mapToDTO(final Evenement evenement, final EvenementDTO evenementDTO) {
        evenementDTO.setIdEvenement(evenement.getIdEvenement());
        evenementDTO.setNomEvenement(evenement.getNomEvenement());
        evenementDTO.setNbPlacesRestantes(evenement.getNbPlacesRestantes());
        evenementDTO.setNbPlacesRestants(evenement.getNbPlacesRestants());
        evenementDTO.setDateEvenement(evenement.getDateEvenement());
        evenementDTO.setCategories(evenement.getCategories().stream()
                .map(categorie -> {
                    CategorieDTO categorieDTO = new CategorieDTO();
                    categorieDTO.setCodeCategorie(categorie.getCodeCategorie());
                    categorieDTO.setNomCategorie(categorie.getNomCategorie());
                    return categorieDTO;
                })
                .toList());
        return evenementDTO;
    }


    private Evenement mapToEntity(final EvenementDTO evenementDTO, final Evenement evenement) {
        evenement.setNomEvenement(evenementDTO.getNomEvenement());
        evenement.setNbPlacesRestantes(evenementDTO.getNbPlacesRestantes());
        evenement.setNbPlacesRestants(evenementDTO.getNbPlacesRestants());
        evenement.setDateEvenement(evenementDTO.getDateEvenement());

        // Map categories
        if (evenementDTO.getCategories() != null) {
            List<Categorie> categories = evenementDTO.getCategories().stream()
                    .map(dto -> categorieRepository.findByCodeCategorie(dto.getCodeCategorie())
                            .orElseGet(() -> {
                                Categorie newCategorie = new Categorie();
                                newCategorie.setCodeCategorie(dto.getCodeCategorie());
                                newCategorie.setNomCategorie(dto.getNomCategorie());
                                return categorieRepository.save(newCategorie); // Save if new
                            }))
                    .toList();
            evenement.setCategories(new HashSet<>(categories));
        } else {
            evenement.setCategories(Collections.emptySet());
        }

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
