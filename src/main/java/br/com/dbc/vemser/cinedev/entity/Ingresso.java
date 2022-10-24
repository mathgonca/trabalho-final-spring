package br.com.dbc.vemser.cinedev.entity;

import br.com.dbc.vemser.cinedev.entity.enums.Disponibilidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingresso {
    private Integer idIngresso;
    private int idFilme;

    private int idCinema;
    private int idCliente;
    @NotNull
    private int cadeira;
    @NotNull
    private double preco;
    @NotNull
    private LocalDateTime dataHora;
    @NotNull
    private Disponibilidade disponibilidade;



    @Override
    public String toString() {
        return "Ingresso{" +
                "idIngresso=" + idIngresso +
                ", cadeira=" + cadeira +
                ", preco=" + preco +
                ", dataHora=" + dataHora +
                ", disponibilidade=" + disponibilidade +
                '}';

    }

}
