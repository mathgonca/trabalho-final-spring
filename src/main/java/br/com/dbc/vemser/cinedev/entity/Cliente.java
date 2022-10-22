package br.com.dbc.vemser.cinedev.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    private Integer idCliente;
    private String primeiroNome;
    private String ultimoNome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
}
