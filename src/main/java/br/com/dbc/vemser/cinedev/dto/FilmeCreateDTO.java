package br.com.dbc.vemser.cinedev.dto;

import br.com.dbc.vemser.cinedev.entity.enums.Idioma;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
public class FilmeCreateDTO {

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 200)
    private String nome;
    @NotNull
    private Idioma idioma;
    @NotNull
    private int classificacaoEtaria;
    @NotNull
    private int duracao;
}
