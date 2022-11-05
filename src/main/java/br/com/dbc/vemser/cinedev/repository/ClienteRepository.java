package br.com.dbc.vemser.cinedev.repository;

import br.com.dbc.vemser.cinedev.dto.relatorios.RelatorioCadastroIngressoClienteDTO;
import br.com.dbc.vemser.cinedev.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
    Optional<ClienteEntity> findByCpf(String cpf);

    Optional<ClienteEntity> findByEmail(String email);

    @Query(" select new br.com.dbc.vemser.cinedev.dto.relatorios.RelatorioCadastroIngressoClienteDTO(" +
            " c.idCliente, " +
            " c.primeiroNome, " +
            " c.ultimoNome, " +
            " c.cpf, " +
            " c.dataNascimento, " +
            " c.email, " +
            " ci.idIngresso, " +
            " ci.idFilme, " +
            " ci.filme.nome, " +
            " ci.idCinema, " +
            " ci.dataHora, " +
            " ci.cinema.nome, " +
            " ci.cinema.estado, " +
            " ci.cinema.cidade " +
            ")" +
            " from Cliente c " +
            " left join c.ingresso ci " +
            " where (:idCliente is null or c.idCliente = :idCliente and ci.idIngresso is not null)")
    List<RelatorioCadastroIngressoClienteDTO> listarRelatorioPersonalizado(Integer idCliente);
}
