package br.com.dbc.vemser.cinedev.repository;

import br.com.dbc.vemser.cinedev.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
    Optional<ClienteEntity> findByCpf(String cpf);

    Optional<ClienteEntity> findByEmail(String email);
}
