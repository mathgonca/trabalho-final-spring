package br.com.dbc.vemser.cinedev.repository;

import br.com.dbc.vemser.cinedev.entity.IngressoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngressoRepository extends JpaRepository<IngressoEntity,Integer>{

}
