package br.com.dbc.vemser.cinedev.dto.ingressodto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class IngressoDTO extends IngressoCreateDTO {

    @Schema(description = "Id do ingresso", example = "1")
    private Integer idIngresso;

    @Schema(description = "Id do filme", example = "1")
    private Integer idFilme;

    @Schema(description = "Id do cinema", example = "1")
    private Integer idCinema;

    @Schema(description = "Id do cliente", example = "1")
    private Integer idCliente;
}
