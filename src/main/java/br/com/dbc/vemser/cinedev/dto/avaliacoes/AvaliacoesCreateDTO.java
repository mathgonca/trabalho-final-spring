package br.com.dbc.vemser.cinedev.dto.avaliacoes;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class AvaliacoesCreateDTO {
    @NotNull
    @Schema(example = "9.5")
    private Double nota;

    @Schema(example = "Desenho mutio engraçado e bom pra ver com a familia!")
    private String comentario;
}