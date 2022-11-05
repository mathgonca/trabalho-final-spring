package br.com.dbc.vemser.cinedev.dto.ingressodto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngressoCompradoDTO extends IngressoDTO {

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
    private LocalDateTime DataHora;

}
