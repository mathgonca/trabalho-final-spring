package br.com.dbc.vemser.cinedev.dto.avaliacoes;

import lombok.Data;

import javax.persistence.Column;

@Data
public class AvaliacoesCreateDTO {

    @Column(name = "Nome Filme")
    private String nome;

    @Column(name = "nota")
    private Double nota;

    @Column(name = "comentario")
    private String comentario;
}