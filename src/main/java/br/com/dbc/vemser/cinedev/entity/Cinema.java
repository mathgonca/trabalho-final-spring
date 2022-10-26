package br.com.dbc.vemser.cinedev.entity;

import lombok.*;

import javax.validation.Valid;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cinema  {

    private Integer idCinema;
    private String nome;
    private String estado;
    private String cidade;

}