package br.com.dbc.vemser.cinedev.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class IngressoCompradoDTO {
    @NotNull
    @NotEmpty
    @Schema(description = "ID do ingresso comprado ", example = "1")
    private int idIngressoComprado;
    @NotNull
    @NotEmpty
    @Schema(description = "Nome do cliente ", example = "Pablo Horacio Guiñazú")
    private String nomeCliente;
    @NotNull
    @NotEmpty
    @Schema(description = "Filme escolhido", example = "Adão Negro")
    private String nomeFilme;
    @NotNull
    @NotEmpty
    @Schema(description = "Cinema escolhido ", example = "GNC Iguatemi', 'RS', 'Porto Alegre'")
    private String nomeCinema;
    @NotNull
    @NotEmpty
    @Schema(description = " horario e a data escolhido ", example = "'21/10/2022 21:30")
    private LocalDateTime DataHora;

    @Override
    public String toString() {
        return "Informações do Ingresso : " +
                "ID do Ingresso=" + idIngressoComprado + '\'' +
                ", Cliente = " + nomeCliente + '\'' +
                ", Filme ='" + nomeFilme + '\'' +
                ", Cinema ='" + nomeCinema + '\'' +
                ", Data e Horario =" + DataHora +
                '}';
    }
}
