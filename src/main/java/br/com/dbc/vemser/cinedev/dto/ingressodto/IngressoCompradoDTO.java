package br.com.dbc.vemser.cinedev.dto.ingressodto;

import br.com.dbc.vemser.cinedev.entity.enums.Disponibilidade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngressoCompradoDTO {

    @Schema(description = "Id do ingresso", example = "1")
    private Integer idIngresso;

    @Schema(description = "Id do filme", example = "1")
    private Integer idFilme;

    @Schema(description = "Id do cinema", example = "1")
    private Integer idCinema;

    @Schema(description = "Id do cliente", example = "1")
    private Integer idCliente;
    @NotNull
    @NotEmpty
    @Schema(description = "Nome do cliente ", example = "Pablo Horacio Guiñazú")
    private String nomeCliente;
    @NotNull
    @NotEmpty
    @Schema(description = "FilmeEntity escolhido", example = "Adão Negro")
    private String nomeFilme;
    @NotNull
    @NotEmpty
    @Schema(description = "Cinema escolhido ", example = "GNC Iguatemi")
    private String nomeCinema;
    @NotNull
    @NotEmpty
    @Schema(description = " horario e a data escolhido ", example = "'21/10/2022 21:30")
    private LocalDateTime dataHora;

    @Schema(description = "Disponibilidade", example = "S")
    private Disponibilidade disponibilidade;

    private String ativo;

}
