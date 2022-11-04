package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.FilmeCreateDTO;
import br.com.dbc.vemser.cinedev.dto.FilmeDTO;
import br.com.dbc.vemser.cinedev.entity.FilmeEntity;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.FilmeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmeService {
    private final FilmeRepository filmeRepository;
    private final ObjectMapper objectMapper;

    public FilmeDTO adicionarFilme(FilmeCreateDTO filmeCapturado) throws RegraDeNegocioException {
        try {
            String filmeNome = filmeCapturado.getNome();
            Optional<FilmeEntity> filmePorNome = filmeRepository.listarPorNome(filmeNome);

            if (filmePorNome.isEmpty()) {
                    FilmeEntity filmeEntityTransform = objectMapper.convertValue(filmeCapturado, FilmeEntity.class);
                    FilmeEntity filmeEntitySalvo = filmeRepository.adicionar(filmeEntityTransform);
                    FilmeDTO filmeDTO = objectMapper.convertValue(filmeEntitySalvo, FilmeDTO.class);
                    return filmeDTO;
            } else {
                throw new RegraDeNegocioException("Erro!Nome do filme já consta em nossa lista de cadastros!");
            }
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de verificação no banco de dados!");
        }
    }
    public void removerFilme(Integer id) throws RegraDeNegocioException {
        try {
             filmeRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro! Não foi possível realizar a remoção de dados");
        }
    }
    public FilmeDTO editarFilme(Integer id, FilmeCreateDTO filmeCapturado) throws RegraDeNegocioException {

        try {
            String filmeNome = filmeCapturado.getNome();
            Optional<FilmeEntity> filmePorNome = filmeRepository.listarPorNome(filmeNome);
            if (filmePorNome.isEmpty()) {
            FilmeEntity filmeEntityTransf = objectMapper.convertValue(filmeCapturado, FilmeEntity.class);
            FilmeEntity filmeEntitySalvo = filmeRepository.editar(id, filmeEntityTransf);
            FilmeDTO filmeDTO = objectMapper.convertValue(filmeEntitySalvo, FilmeDTO.class);
            return filmeDTO;} else {
                throw new RegraDeNegocioException("Erro!Nome do filme já consta em nossa lista de cadastros!");
            }
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro!Não foi possivel realizar a alteração de dados!");
        }
    }
    public List<FilmeDTO> listarTodosFilmes() throws RegraDeNegocioException {

        try {
            List<FilmeEntity> filmeEntityList = filmeRepository.listar();
            return filmeEntityList.stream()
                    .map(filmeEntity -> objectMapper.convertValue(filmeEntity, FilmeDTO.class))
                    .toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro na comunicação com o Banco de Dados!Não foi possivel realizar listagem.");
        }
    }
    public FilmeDTO findById(Integer id) throws RegraDeNegocioException {

        try {
            FilmeEntity filmeEntityEncontrado = filmeRepository.findById(id);
            FilmeDTO filmeDTO = objectMapper.convertValue(filmeEntityEncontrado, FilmeDTO.class);
            return filmeDTO;
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro na comunicação com o Banco de Dados!Não foi possivel realizar listagem.");
        }

    }
}
