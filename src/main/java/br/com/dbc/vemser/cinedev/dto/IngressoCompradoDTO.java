package br.com.dbc.vemser.cinedev.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class IngressoCompradoDTO {
    private int idIngressoComprado;
    private String nomeCliente;
    private String nomeFilme;
    private String nomeCinema;
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
