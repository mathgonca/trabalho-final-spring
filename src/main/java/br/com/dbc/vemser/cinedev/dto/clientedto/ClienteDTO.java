package br.com.dbc.vemser.cinedev.dto.clientedto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO extends ClienteCreateDTO {

    @Schema(description = "Id do cliente", example = "1")
    private Integer idCliente;
}
