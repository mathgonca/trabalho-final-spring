package br.com.dbc.vemser.cinedev.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {
    @NotNull
    @Schema(example = "matheus_goncalves@dbccompany.com.br")
    private String email;
    @NotNull
    @Schema(example = "123admin")
    private String senha;
}