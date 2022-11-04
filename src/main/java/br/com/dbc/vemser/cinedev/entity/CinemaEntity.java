package br.com.dbc.vemser.cinedev.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cinema")
public class CinemaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CINEMA_SEQ")
    @SequenceGenerator(name = "CINEMA_SEQ", sequenceName = "seq_id_cinema", allocationSize = 1)
    private Integer idCinema;

    @Column(name = "nome")
    private String nome;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cidade")
    private String cidade;
}