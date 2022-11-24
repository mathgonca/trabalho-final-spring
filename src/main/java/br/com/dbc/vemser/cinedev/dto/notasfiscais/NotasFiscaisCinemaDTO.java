package br.com.dbc.vemser.cinedev.dto.notasfiscais;

import br.com.dbc.vemser.cinedev.dto.ingressodto.IngressoCompradoDTO;
import br.com.dbc.vemser.cinedev.entity.IngressoEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NotasFiscaisCinemaDTO {

    private String usuario;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private IngressoCompradoDTO ingressoCompradoDTOS;
}
