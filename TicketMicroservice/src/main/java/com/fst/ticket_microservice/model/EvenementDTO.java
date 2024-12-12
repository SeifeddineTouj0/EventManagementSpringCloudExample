package com.fst.ticket_microservice.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EvenementDTO {

    private Long idEvenement;

    @Size(max = 255)
    private String nomEvenement;

    private Long nbPlacesRestantes;

    private Long nbPlacesRestants;

    private LocalDate dateEvenement;

    private List<Long> categories;

}
