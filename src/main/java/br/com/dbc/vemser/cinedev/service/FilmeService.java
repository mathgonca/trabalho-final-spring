package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.entity.Filme;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmeRepository;


    public Filme adicionarFilme(Filme filme) throws BancoDeDadosException {
            return filmeRepository.adicionar(filme);

    }

    public void removerFilme(Integer id) throws BancoDeDadosException {

            boolean realizouRemover = filmeRepository.remover(id);


    }

    public Filme editarFilme(Integer id, Filme filme) throws BancoDeDadosException {
        return filmeRepository.editar(id, filme);

    }

    public List<Filme> listarTodosFilmes() throws BancoDeDadosException{
        return filmeRepository.listar();

    }

    public List<LocalDateTime> listarFilmesPorHorario(int idFilme, int idCinema) throws BancoDeDadosException{
        return filmeRepository.listaHorariosDoFilme(idFilme,idCinema);
    }

    public List<String> ListarFilmesPorCinema(int idCinema) throws BancoDeDadosException{
        return filmeRepository.listaFilmesPorCinema(idCinema);

    }


}
