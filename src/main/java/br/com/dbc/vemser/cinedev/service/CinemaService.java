package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.CinemaCreateDTO;
import br.com.dbc.vemser.cinedev.dto.CinemaDTO;
import br.com.dbc.vemser.cinedev.entity.CinemaEntity;
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

    public List<CinemaDTO> listarCinema() {
        List<CinemaEntity> cinemaEntityList = cinemaRepository.findAll();
        return cinemaEntityList.stream()
                .map(cinemaEntity -> objectMapper.convertValue(cinemaEntity, CinemaDTO.class))
                .toList();
    }

    public CinemaDTO adicionarCinema(CinemaCreateDTO cinemaCapturado) throws RegraDeNegocioException {
        String cinemaNome = cinemaCapturado.getNome();
        Optional<CinemaEntity> cinemaPorNome = cinemaRepository.findByNome(cinemaNome);

        if (cinemaPorNome.isPresent()) {
            throw new RegraDeNegocioException("Erro! Nome do Cinema já consta em nossa lista de cadastros!");
        }

        CinemaEntity cinemaEntityTransform = objectMapper.convertValue(cinemaCapturado, CinemaEntity.class);
        CinemaEntity cinemaEntitySalvo = cinemaRepository.save(cinemaEntityTransform);
        CinemaDTO cinemaDTO = objectMapper.convertValue(cinemaEntitySalvo, CinemaDTO.class);

        return cinemaDTO;
    }

    public CinemaEntity listarCinemaID(Integer idCinema) throws RegraDeNegocioException {
        Optional<CinemaEntity> cinemaOptional = cinemaRepository.findById(idCinema);

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

        CinemaEntity cinemaEntity = objectMapper.convertValue(cinemaCreateDTO, CinemaEntity.class);
        cinemaEntity.setIdCinema(idCinema);

        CinemaEntity cinemaEntityAtualizado = cinemaRepository.save(cinemaEntity);

        return objectMapper.convertValue(cinemaEntityAtualizado, CinemaDTO.class);
    }

    public void deletarCinema(Integer idCinema) throws RegraDeNegocioException {
        CinemaEntity cinema = listarCinemaID(idCinema);
        cinemaRepository.delete(cinema);
    }
}