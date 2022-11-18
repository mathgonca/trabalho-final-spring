package br.com.dbc.vemser.cinedev.entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;

@Document(collection = "Avaliacoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacoesEntity {

    @Id
    private String id;

    @Column(name = "Nome Filme")
    private String nome;

    @Column(name = "nota")
    private Double nota;

    @Column(name = "comentario")
    private String comentario;

}
