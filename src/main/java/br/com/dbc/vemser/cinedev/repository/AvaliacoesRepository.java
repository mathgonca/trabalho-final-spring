package br.com.dbc.vemser.cinedev.repository;

import br.com.dbc.vemser.cinedev.dto.avaliacoes.AvaliacoesDTO;
import br.com.dbc.vemser.cinedev.entity.AvaliacoesEntity;
import br.com.dbc.vemser.cinedev.entity.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacoesRepository extends MongoRepository<AvaliacoesEntity, String> {

    @Aggregation(pipeline = {
            "{ '$unwind' : '$nota' }",
            "{'$group':{ '_id': '$nome', 'nota' : {'$sum': 1} }}"
    })
    List<AvaliacoesEntity> groupByNota();

    List<AvaliacoesEntity> findAllByNotaContains(Double nota);

    Optional<AvaliacoesEntity> findByNome(String nome);

}
