package br.com.dbc.vemser.cinedev.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmeDTO extends FilmeCreateDTO {
    private Integer idFilme;
}
