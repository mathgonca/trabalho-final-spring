package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.controller.documentInterface.OperationControllerCinema;
import br.com.dbc.vemser.cinedev.dto.cinemadto.CinemaCreateDTO;
import br.com.dbc.vemser.cinedev.dto.cinemadto.CinemaDTO;
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
public class CinemaController implements OperationControllerCinema {

    private final CinemaService cinemaService;

    @Override
    @GetMapping
    public List<CinemaDTO> list() throws RegraDeNegocioException {
        return cinemaService.listarCinema();
    }

    @Override
    @GetMapping("/{idCinema}")
    public CinemaDTO listaPorId(@PathVariable("idCinema") Integer id) throws RegraDeNegocioException {
        return cinemaService.listarCinemaPorId(id);
    }

    @Override
    @PostMapping
    public ResponseEntity<CinemaDTO> cadastrarCinema(@Valid @RequestBody CinemaCreateDTO cinemaCreateDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(cinemaService.adicionarCinema(cinemaCreateDTO), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{idCinema}")
    public ResponseEntity<CinemaDTO> update(@PathVariable Integer idCinema, @Valid @RequestBody CinemaCreateDTO cinemaCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(cinemaService.atualizarCinema(idCinema, cinemaCreateDTO), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idCinema}")
    public ResponseEntity<Void> delete(@PathVariable Integer idCinema) throws
            RegraDeNegocioException {
        cinemaService.deletarCinema(idCinema);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}