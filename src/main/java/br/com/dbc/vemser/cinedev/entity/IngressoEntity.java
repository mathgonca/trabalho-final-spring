package br.com.dbc.vemser.cinedev.entity;

import br.com.dbc.vemser.cinedev.entity.enums.Disponibilidade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

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
    private Integer idIngresso;

    @Column(name = "id_filme", insertable = false, updatable = false)
    private Integer idFilme;

    @Column(name = "id_cinema", insertable = false, updatable = false)
    private Integer idCinema;
    @Column(name = "id_cliente", insertable = false, updatable = false)
    private Integer idCliente;

    @Column(name = "valor")
    private Double preco;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "disponibilidade")
    @Enumerated(EnumType.STRING)
    private Disponibilidade disponibilidade;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cinema", referencedColumnName = "id_cinema")
    private CinemaEntity cinema;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private ClienteEntity cliente;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinColumn(name = "id_filme", referencedColumnName = "id_filme")
    private FilmeEntity filme;

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
