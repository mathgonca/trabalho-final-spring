package br.com.dbc.vemser.cinedev.repository;

import br.com.dbc.vemser.cinedev.entity.LogByTipo;
import br.com.dbc.vemser.cinedev.entity.LogEntity;
import br.com.dbc.vemser.cinedev.entity.enums.TipoLog;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<LogEntity, String> {

    @Aggregation(pipeline = {
            "{ '$unwind' : '$tipoLog' }",
            "{'$group':{ '_id': '$tipoLog', 'quantidade' : {'$sum': 1} }}"
    })
    List<LogByTipo> groupByTipoLogAndCount();

    List<LogEntity> findLogEntityByCidade(String cidade);

    List<LogEntity> findLogEntityByEstado(String estado);

//    List<LogEntity> findAllByDataContains(String data);

    Long countByCidade(String cidade);

    Long countByEstado(String estado);

//    Long countAllByDataContains(String data);
//
//    Integer countByTipoLog(TipoLog tipoLog);

}
