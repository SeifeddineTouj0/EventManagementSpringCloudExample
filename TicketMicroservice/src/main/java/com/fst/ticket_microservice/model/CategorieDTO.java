package com.fst.ticket_microservice.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategorieDTO {

    //private Long idCategorie;

    @Size(max = 255)
    private String codeCategorie;

    @Size(max = 255)
    private String nomCategorie;

}
