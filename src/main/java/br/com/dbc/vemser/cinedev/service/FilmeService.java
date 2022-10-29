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

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmeService {
    private final FilmeRepository filmeRepository;
    private final ObjectMapper objectMapper;

    public FilmeDTO adicionarFilme(FilmeCreateDTO filmeCapturado) throws RegraDeNegocioException {
        Filme filmeTransform = objectMapper.convertValue(filmeCapturado, Filme.class);
        Filme filmeSalvo = null;
        try {
            filmeSalvo = filmeRepository.adicionar(filmeTransform);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro na comunicação com o Banco de Dados");
        }
        FilmeDTO filmeDTO = objectMapper.convertValue(filmeSalvo, FilmeDTO.class);
        return filmeDTO;
    }
    public void removerFilme(Integer id) throws RegraDeNegocioException {
        try {
             filmeRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        }
    }
    public FilmeDTO editarFilme(Integer id, FilmeCreateDTO filmeCapturado) throws RegraDeNegocioException {
        Filme filmeTransf = objectMapper.convertValue(filmeCapturado, Filme.class);
        Filme filmeSalvo = null;
        try {
            filmeSalvo = filmeRepository.editar(id, filmeTransf);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro na comunicação com o Banco de Dados");
        }
        FilmeDTO filmeDTO = objectMapper.convertValue(filmeSalvo, FilmeDTO.class);
        return filmeDTO;
    }
    public List<FilmeDTO> listarTodosFilmes() throws RegraDeNegocioException {
        List<Filme> filmeList = null;
        try {
            filmeList = filmeRepository.listar();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro na comunicação com o Banco de Dados");
        }
        return filmeList.stream()
                .map(filme -> objectMapper.convertValue(filme, FilmeDTO.class))
                .toList();
    }
    public FilmeDTO findById(Integer id) throws RegraDeNegocioException {
        Filme filmeEncontrado = null;
        try {
            filmeEncontrado = filmeRepository.findById(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro na comunicação com o Banco de Dados");
        }
        FilmeDTO filmeDTO = objectMapper.convertValue(filmeEncontrado, FilmeDTO.class);
       return filmeDTO;
    }
}
