package br.com.dbc.vemser.cinedev.entity.enums;

public enum Disponibilidade {
    S("S"), N("N");

    private final String disponibilidade;

    Disponibilidade(String descricao) {
        this.disponibilidade = descricao;
    }

    public String isDisponibilidade() {
        return disponibilidade;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }
}
