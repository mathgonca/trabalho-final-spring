package br.com.dbc.vemser.cinedev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDTO extends CinemaCreateDTO {
    private Integer idCinema;
}
