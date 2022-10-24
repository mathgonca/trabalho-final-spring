package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.dto.CinemaCreateDto;
import br.com.dbc.vemser.cinedev.dto.CinemaDTO;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/cinema")
public class CinemaController {

    private final CinemaService cinemaService;

    @GetMapping
    public List<CinemaDTO> listarcinema() throws BancoDeDadosException {
        return cinemaService.listarCinema();
    }

    @PostMapping
    public ResponseEntity<CinemaDTO> adicionarCinema(@Valid @RequestBody CinemaCreateDto cinemaCreateDto)
            throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(cinemaService.adicionarCinema(cinemaCreateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{idCinema}")
    public void removerCinema(@PathVariable Integer idCinema) throws BancoDeDadosException, RegraDeNegocioException {
        cinemaService.deletarCinema(idCinema);
    }
}
