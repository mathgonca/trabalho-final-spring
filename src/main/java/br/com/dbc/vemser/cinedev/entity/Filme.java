package br.com.dbc.vemser.cinedev.entity;

import br.com.dbc.vemser.cinedev.entity.enums.Idioma;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Filme {
    private Integer idFilme;
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

    public Filme(Integer idFilme, String nome, Idioma idioma, int classificacaoEtaria, int duracao) {
        this.idFilme = idFilme;
        this.nome = nome;
        this.idioma = idioma;
        this.classificacaoEtaria = classificacaoEtaria;
        this.duracao = duracao;
    }

}
