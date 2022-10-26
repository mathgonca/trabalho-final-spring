package br.com.dbc.vemser.cinedev.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
public class ClienteCreateDTO {
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 25)
    @Schema(description = "Primeiro nome da Pessoa", example = "Moises Noah")
    private String primeiroNome;
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 35)
    @Schema(description = "Segundo nome da Pessoa", example = "Silva Bispo")
    private String ultimoNome;
    @NotNull
    @NotEmpty
    @Size(min = 11, max = 11)
    @Schema(description = "Cadastro de Pessoa Física, com mínimo de 11 digitos", example = "11122233344")
    private String cpf;
    @Past
    @NotNull
    @Schema(description = "Data de Nascimento do usuário", example = "1999/02/20")
    private LocalDate dataNascimento;
    @NotNull
    @NotEmpty
    @Schema(description = "Email do usuário - o mesmo não pode ser usado para mais de um cadastro!", example = "noahbispo@yahoo.com.br")
    private String email;
}
