package com.fst.internaute_microservice.service;

import com.fst.internaute_microservice.domain.Internaute;
import com.fst.internaute_microservice.model.InternauteDTO;
import com.fst.internaute_microservice.repos.InternauteRepository;
import com.fst.internaute_microservice.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class InternauteService {

    private final InternauteRepository internauteRepository;

    public InternauteService(final InternauteRepository internauteRepository) {
        this.internauteRepository = internauteRepository;
    }

    public List<InternauteDTO> findAll() {
        final List<Internaute> internautes = internauteRepository.findAll(Sort.by("idInternaute"));
        return internautes.stream()
                .map(internaute -> mapToDTO(internaute, new InternauteDTO()))
                .toList();
    }

    public InternauteDTO get(final Long idInternaute) {
        return internauteRepository.findById(idInternaute)
                .map(internaute -> mapToDTO(internaute, new InternauteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final InternauteDTO internauteDTO) {
        final Internaute internaute = new Internaute();
        mapToEntity(internauteDTO, internaute);
        return internauteRepository.save(internaute).getIdInternaute();
    }

    public void update(final Long idInternaute, final InternauteDTO internauteDTO) {
        final Internaute internaute = internauteRepository.findById(idInternaute)
                .orElseThrow(NotFoundException::new);
        mapToEntity(internauteDTO, internaute);
        internauteRepository.save(internaute);
    }

    public void delete(final Long idInternaute) {
        internauteRepository.deleteById(idInternaute);
    }

    private InternauteDTO mapToDTO(final Internaute internaute, final InternauteDTO internauteDTO) {
        internauteDTO.setIdInternaute(internaute.getIdInternaute());
        internauteDTO.setIdentifiant(internaute.getIdentifiant());
        internauteDTO.setTrancheAge(internaute.getTrancheAge());
        return internauteDTO;
    }

    private Internaute mapToEntity(final InternauteDTO internauteDTO, final Internaute internaute) {
        internaute.setIdentifiant(internauteDTO.getIdentifiant());
        internaute.setTrancheAge(internauteDTO.getTrancheAge());
        return internaute;
    }

}
