package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.FilmeCreateDTO;
import br.com.dbc.vemser.cinedev.dto.FilmeDTO;
import br.com.dbc.vemser.cinedev.entity.Filme;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.FilmeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmeService {
    private final FilmeRepository filmeRepository;
    private final ObjectMapper objectMapper;

    public FilmeDTO adicionarFilme(FilmeCreateDTO filmeCapturado) throws BancoDeDadosException {
        Filme filmeTransform = objectMapper.convertValue(filmeCapturado, Filme.class);
        Filme filmeSalvo = filmeRepository.adicionar(filmeTransform);
        FilmeDTO filmeDTO = objectMapper.convertValue(filmeSalvo, FilmeDTO.class);
        return filmeDTO;
    }
    public void removerFilme(Integer id) throws BancoDeDadosException {
        boolean realizouRemover = filmeRepository.remover(id);
    }
    public FilmeDTO editarFilme(Integer id, FilmeCreateDTO filmeCapturado) throws BancoDeDadosException, RegraDeNegocioException {
        FilmeDTO filmeRecuperado = findById(id);
        Filme filmeTransf = objectMapper.convertValue(filmeCapturado, Filme.class);
        Filme filmeSalvo = filmeRepository.editar(id, filmeTransf);
        FilmeDTO filmeDTO = objectMapper.convertValue(filmeSalvo, FilmeDTO.class);
        return filmeDTO;
    }
    public List<FilmeDTO> listarTodosFilmes() throws BancoDeDadosException {
        List<Filme> filmeList = filmeRepository.listar();
        return filmeList.stream()
                .map(filme -> objectMapper.convertValue(filme, FilmeDTO.class))
                .toList();
    }
    public List<LocalDateTime> listarFilmesPorHorario(int idFilme, int idCinema) throws BancoDeDadosException {
        return filmeRepository.listaHorariosDoFilme(idFilme, idCinema);
    }
    public List<String> ListarFilmesPorCinema(int idCinema) throws BancoDeDadosException {
        return filmeRepository.listaFilmesPorCinema(idCinema);
    }
    public FilmeDTO findById(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Filme filmeEncontrado = filmeRepository.listar().stream()
                .filter(filme -> filme.getIdFilme().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Não foi posivel localizar esse Filme!"));
        FilmeDTO filmeDTO = objectMapper.convertValue(filmeEncontrado, FilmeDTO.class);
        return filmeDTO;
    }
}
