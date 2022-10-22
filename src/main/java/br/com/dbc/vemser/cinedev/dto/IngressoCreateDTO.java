package br.com.dbc.vemser.cinedev.dto;

import br.com.dbc.vemser.cinedev.entity.enums.Disponibilidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngressoCreateDTO {

    private  int idCliente;
    private int idIngressoComprado;
    private String nomeFilme;
    private String nomeCinema;
    private LocalDateTime DataHora;

    @Override
    public String toString() {
        return "Informações do Ingresso : " +
                "ID do Ingresso=" + idIngressoComprado +
                ", Filme ='" + nomeFilme + '\'' +
                ", Cinema ='" + nomeCinema + '\'' +
                ", Data e Horario =" + DataHora +
                '}';
    }
}
