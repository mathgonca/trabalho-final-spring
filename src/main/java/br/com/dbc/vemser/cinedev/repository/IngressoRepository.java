package br.com.dbc.vemser.cinedev.repository;

import br.com.dbc.vemser.cinedev.dto.ingressodto.IngressoCompradoDTO;
import br.com.dbc.vemser.cinedev.entity.IngressoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngressoRepository extends JpaRepository<IngressoEntity,Integer> {


    @Query("SELECT i FROM Ingresso i WHERE i.disponibilidade = 'N'")
    List<IngressoEntity> findIngressoComprados();

    @Query("SELECT i FROM Ingresso i WHERE i.disponibilidade = 'S'")
    List<IngressoEntity> findIngressoDisponiveis();

}