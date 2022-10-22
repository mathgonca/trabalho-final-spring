package br.com.dbc.vemser.cinedev.dto;

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
    private String primeiroNome;
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 35)
    private String ultimoNome;
    @NotNull
    @NotEmpty
    @Size(min = 11, max = 11)
    private String cpf;
    @Past
    @NotNull
    private LocalDate dataNascimento;
    @NotNull
    @NotEmpty
    private String email;
}
