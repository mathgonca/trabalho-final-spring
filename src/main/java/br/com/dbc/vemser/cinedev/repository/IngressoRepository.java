package br.com.dbc.vemser.cinedev.repository;

import br.com.dbc.vemser.cinedev.entity.IngressoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngressoRepository extends JpaRepository<IngressoEntity,Integer>{


    @Query("SELECT i FROM Ingresso i WHERE i.disponibilidade = 'N'")
    List<IngressoEntity> findIngressoComprados();

    @Query("SELECT i, " +
            "t.primeiroNome, " +
            "c.nome, " +
            "f.nome " +
            "FROM Ingresso i " +
            "inner join i.cinema c " +
            "inner join i.filme f " +
            "inner join i.cliente t " +
            "where i.idCliente = :idCliente and i.disponibilidade = 'N'")
    List<IngressoEntity> findIngressoCompradosPorCliente(@Param("idCliente")Integer idCliente);
}
//    WHERE i. = 'N' and i.idCliente = :idCliente