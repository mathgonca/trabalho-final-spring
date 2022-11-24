package br.com.dbc.vemser.cinedev.entity;

import br.com.dbc.vemser.cinedev.entity.enums.NotasFiscais;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class NotasEntity {

    // vamo perguntar pro rafa e o maicon amanha

    NotasFiscais notasFiscais;
    List<IngressoEntity> ingressos;
}
