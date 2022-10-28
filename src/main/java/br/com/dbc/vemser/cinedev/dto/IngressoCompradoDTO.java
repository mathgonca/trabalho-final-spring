package br.com.dbc.vemser.cinedev.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class IngressoCompradoDTO {

    private int idCliente;
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
