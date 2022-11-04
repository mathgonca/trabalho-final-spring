package br.com.dbc.vemser.cinedev.entity;

import br.com.dbc.vemser.cinedev.entity.enums.Disponibilidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name= "Ingresso")
public class IngressoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INGRESSO")
    @SequenceGenerator(name = "SEQ_INGRESSO", sequenceName = "seq_id_ingresso", allocationSize = 1)
    @Column(name = "id_ingresso")
    private int idIngresso;

    @Column(name = "id_filme")
    private int idFilme;

    @Column(name = "id_cinema")
    private int idCinema;

    @Column(name = "id_cliente")
    private int idCliente;

    @Column(name = "valor")
    private double preco;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "disponibilidade")
    private Disponibilidade disponibilidade;
    
    @Override
    public String toString() {
        return "IngressoEntity{" +
                "idIngresso=" + idIngresso +
                ", idCliente=" + idCliente +
                ", preco=" + preco +
                ", dataHora=" + dataHora +
                ", disponibilidade=" + disponibilidade +
                '}';

    }

}
