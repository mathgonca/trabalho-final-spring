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

    public List<CinemaDTO> listarCinema() throws BancoDeDadosException {
        List<Cinema> cinemaList = cinemaRepository.listar();
        return cinemaList.stream()
                .map(cinema -> objectMapper.convertValue(cinema, CinemaDTO.class))
                .toList();
    }

    public CinemaDTO adicionarCinema(CinemaCreateDTO cinemaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        String cinemaCadastroNOME = cinemaCreateDTO.getNome();
        Optional<Cinema> cinemaPorNOME = cinemaRepository.listarCinemaId(Integer.parseInt(cinemaCadastroNOME));


        if (cinemaPorNOME.isEmpty()) {
            Cinema cinema = objectMapper.convertValue(cinemaCreateDTO, Cinema.class);
            Cinema cinemaCadastrado = cinemaRepository.adicionar(cinema);

            return objectMapper.convertValue(cinemaCadastrado, CinemaDTO.class);
        } else {
            throw new RegraDeNegocioException("Cinema já cadastrado com os mesmos dados");
        }
    }

    public Cinema listarCinemaID(Integer idCinema) throws BancoDeDadosException, RegraDeNegocioException {
        Optional<Cinema> cinemaOptional = cinemaRepository.listarCinemaId(idCinema);

        if (cinemaOptional.isEmpty()) {
            throw new RegraDeNegocioException("cinema não cadastrado");
        }

        return cinemaOptional.get();
    }

    public CinemaDTO listarCinemaPorId(Integer idCinema) throws BancoDeDadosException, RegraDeNegocioException {
        return objectMapper.convertValue(listarCinemaID(idCinema),CinemaDTO.class);
    }
    public CinemaDTO atualizarCinema(Integer idCinema, CinemaCreateDTO cinemaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        listarCinemaID(idCinema);

        Cinema cinema = objectMapper.convertValue(cinemaCreateDTO, Cinema.class);
        Cinema cinemaAtualizado = cinemaRepository.editar(idCinema, cinema);

        return objectMapper.convertValue(cinemaAtualizado, CinemaDTO.class);
    }

    public void deletarCinema(Integer idCinema) throws BancoDeDadosException, RegraDeNegocioException {
        listarCinemaID(idCinema);
        cinemaRepository.remover(idCinema);
    }
}