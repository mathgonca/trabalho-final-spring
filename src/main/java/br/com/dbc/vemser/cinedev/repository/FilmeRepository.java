package br.com.dbc.vemser.cinedev.repository;

import br.com.dbc.vemser.cinedev.entity.FilmeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<FilmeEntity, Integer> {

}
