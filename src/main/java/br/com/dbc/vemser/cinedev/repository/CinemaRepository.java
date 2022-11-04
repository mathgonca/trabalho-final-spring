package br.com.dbc.vemser.cinedev.repository;

import br.com.dbc.vemser.cinedev.entity.CinemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<CinemaEntity, Integer> {

    Optional<CinemaEntity> findByNome(String nome);
}