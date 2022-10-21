package br.com.dbc.vemser.cinedev.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    private Integer idCliente;
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

    public Cliente(String primeiroNome, String ultimoNome, String cpf, LocalDate dataNascimento, String email) {
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
    }
}
