package br.com.dbc.vemser.cinedev.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
public class CinemaCreateDto {

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 350)
    private String nome;
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 250)
    private String estado;
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 350)
    private String cidade;

}

