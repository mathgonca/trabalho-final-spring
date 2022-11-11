package br.com.dbc.vemser.cinedev.dto.clientedto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateClienteDTO extends ClienteCreateDTO {

    @NotNull
    @NotEmpty
    @Schema(description = "Email do usuário - o mesmo não pode ser usado para mais de um cadastro!", example = "noahbispo@yahoo.com.br")
    private String login;

    @NotBlank
    @Schema(description = "Senha não pode conter espaços!", example = "123")
    private String senha;
}
