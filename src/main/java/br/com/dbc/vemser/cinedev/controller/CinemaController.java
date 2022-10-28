package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.controller.documentInterface.OperationControllerCinema;
import br.com.dbc.vemser.cinedev.dto.CinemaCreateDTO;
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
public class CinemaController implements OperationControllerCinema {

    private final CinemaService cinemaService;

    @Override
    @GetMapping
    public List<CinemaDTO> list() throws RegraDeNegocioException, BancoDeDadosException {
        return cinemaService.listarCinema();
    }

    @Override
    @GetMapping
    public CinemaDTO listaPorId(@PathVariable("idCinema")Integer id) throws RegraDeNegocioException, BancoDeDadosException{
        return cinemaService.listarCinemaPorId(id);
    }

    @Override
    @PostMapping
    public ResponseEntity<CinemaDTO> cadastrarCinema(@Valid @RequestBody CinemaCreateDTO cinemaCreateDTO)
            throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(cinemaService.adicionarCinema(cinemaCreateDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CinemaDTO> update(Integer id, CinemaCreateDTO cinemaCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(cinemaService.atualizarCinema(id, cinemaCreateDTO), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idCinema}")
    public ResponseEntity<Void> delete(@PathVariable Integer idCinema) throws
            RegraDeNegocioException, BancoDeDadosException {
        cinemaService.deletarCinema(idCinema);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}