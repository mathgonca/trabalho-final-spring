package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.CinemaCreateDTO;
import br.com.dbc.vemser.cinedev.dto.CinemaDTO;
import br.com.dbc.vemser.cinedev.entity.Cinema;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.CinemaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final ObjectMapper objectMapper;

    public List<CinemaDTO> listarCinema() throws RegraDeNegocioException {
        List<Cinema> cinemaList;
        try {
            cinemaList = cinemaRepository.listar();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao retornar a lista de Cinema, tente de novo mais tarde.");
        }
        return cinemaList.stream()
                .map(cinema -> objectMapper.convertValue(cinema, CinemaDTO.class))
                .toList();
    }


    public CinemaDTO adicionarCinema(CinemaCreateDTO cinemaCreateDTO) throws RegraDeNegocioException {
        String cinemaCadastroNOME = cinemaCreateDTO.getNome();
        Optional<Cinema> cinemaPorNOME;
        try {
            cinemaPorNOME = cinemaRepository.listarCinemaId(Integer.parseInt(cinemaCadastroNOME));
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao cadastrar o nome do Cinema, tente de novo mais tarde.");
        }


        if (cinemaPorNOME.isEmpty()) {
            Cinema cinema = objectMapper.convertValue(cinemaCreateDTO, Cinema.class);
            Cinema cinemaCadastrado;
            try {
                cinemaCadastrado = cinemaRepository.adicionar(cinema);
            } catch (BancoDeDadosException e) {
                throw new RegraDeNegocioException("Ocorreu um erro ao cadastrar um Cinema, tente de novo mais tarde.");
            }

            return objectMapper.convertValue(cinemaCadastrado, CinemaDTO.class);
        } else {
            throw new RegraDeNegocioException("Cinema já cadastrado com os mesmos dados");
        }
    }

    public Cinema listarCinemaID(Integer idCinema) throws RegraDeNegocioException {
        Optional<Cinema> cinemaOptional;
        try {
            cinemaOptional = cinemaRepository.listarCinemaId(idCinema);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao retornar a lista de Cinema por Id, tente de novo mais tarde.");
        }

        if (cinemaOptional.isEmpty()) {
            throw new RegraDeNegocioException("cinema não cadastrado");
        }

        return cinemaOptional.get();
    }

    public CinemaDTO listarCinemaPorId(Integer idCinema) throws RegraDeNegocioException {
        return objectMapper.convertValue(listarCinemaID(idCinema), CinemaDTO.class);
    }

    public CinemaDTO atualizarCinema(Integer idCinema, CinemaCreateDTO cinemaCreateDTO) throws RegraDeNegocioException {
        listarCinemaID(idCinema);

        Cinema cinema = objectMapper.convertValue(cinemaCreateDTO, Cinema.class);
        Cinema cinemaAtualizado;
        try {
            cinemaAtualizado = cinemaRepository.editar(idCinema, cinema);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao atualizar um Cinema, tente de novo mais tarde");
        }

        return objectMapper.convertValue(cinemaAtualizado, CinemaDTO.class);
    }

    public void deletarCinema(Integer idCinema) throws RegraDeNegocioException {
        listarCinemaID(idCinema);
        try {
            cinemaRepository.remover(idCinema);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao deletar um Cinema, tente de novo mais tarde");
        }
    }
}