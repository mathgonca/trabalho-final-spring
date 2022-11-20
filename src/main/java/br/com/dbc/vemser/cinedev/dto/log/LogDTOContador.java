package br.com.dbc.vemser.cinedev.dto.log;

import br.com.dbc.vemser.cinedev.entity.enums.TipoLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogDTOContador {

    @Field("_id")
    private TipoLog tipoLog;

    private Integer quantidade;

}
