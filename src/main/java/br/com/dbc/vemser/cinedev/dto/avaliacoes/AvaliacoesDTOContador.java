package br.com.dbc.vemser.cinedev.dto.avaliacoes;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
public class AvaliacoesDTOContador {

    @Field("_id")
    private Double nota;

    private Integer quantidade;

}
