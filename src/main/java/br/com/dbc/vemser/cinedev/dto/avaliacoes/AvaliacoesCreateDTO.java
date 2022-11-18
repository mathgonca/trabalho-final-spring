package br.com.dbc.vemser.cinedev.dto.avaliacoes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class AvaliacoesCreateDTO {

    @Column(name = "Nome Filme")
    private String nome;

    @Column(name = "nota")
    private Double nota;

    @Column(name = "comentario")
    private String comentario;
}