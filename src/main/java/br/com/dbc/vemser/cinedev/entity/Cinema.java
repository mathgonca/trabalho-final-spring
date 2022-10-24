package br.com.dbc.vemser.cinedev.entity;

import br.com.dbc.vemser.cinedev.dto.CinemaCreateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Cinema extends @Valid CinemaCreateDto {

    private Integer idCinema;
    private String nome;
    private String estado;
    private String cidade;

    public Cinema() {

    }

}