package br.com.dbc.vemser.cinedev.dto.log;

import br.com.dbc.vemser.cinedev.entity.enums.TipoLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {

    private String id;

    //@Enumerated(EnumType.STRING)
    @Column(name = "nome")
    private String nome;

    private TipoLog tipoLog;

    private LocalDate data;

}
