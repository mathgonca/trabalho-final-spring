package br.com.dbc.vemser.cinedev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class IngressoDTO extends IngressoCreateDTO {
    private Integer idIngresso;
    private int idFilme;
    private int idCinema;
    private int idCliente;


}
