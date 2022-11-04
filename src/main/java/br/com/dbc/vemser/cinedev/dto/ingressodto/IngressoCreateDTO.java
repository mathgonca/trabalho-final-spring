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
public class IngressoCreateDTO {

    @NotNull
    @NotEmpty
    @Schema(description = "id do cliente ", example = "1")
    private int idCliente;

    @NotNull
    @NotEmpty
    @Schema(description = "Valor do ingresso ", example = "30")
    private double preco;

    @NotNull
    @NotEmpty
    @Schema(description = "Data e Hora ", example = "31/10/2022 21:30")
    private LocalDateTime dataHora;

    @NotNull
    @NotEmpty
    @Schema(description = "Disponibilidade", example = "S")
    private Disponibilidade disponibilidade;
}
