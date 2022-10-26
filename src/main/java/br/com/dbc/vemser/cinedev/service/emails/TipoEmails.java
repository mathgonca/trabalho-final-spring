package br.com.dbc.vemser.cinedev.service.emails;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoEmails {
    CREATE("Cadastro realizado com sucesso!"),
    UPDATE("Alteração de Dados Cadastrais!"),
    DELETE("Acesso da conta encerrado!"),
    ING_COMPRADO("Compra do Ingresso Realizada!");

    private final String descricao;


}
