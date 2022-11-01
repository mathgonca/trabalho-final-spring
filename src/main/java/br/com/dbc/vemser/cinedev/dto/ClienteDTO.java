package br.com.dbc.vemser.cinedev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO extends ClienteCreateDTO {
    private Integer idCliente;
}
