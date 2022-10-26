package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.dto.CinemaCreateDTO;
import br.com.dbc.vemser.cinedev.dto.CinemaDTO;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.service.CinemaService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class CinemaController implements OperationController<Integer, CinemaDTO, CinemaCreateDTO> {

    private final CinemaService cinemaService;

    @Override
    @GetMapping
    public List<CinemaDTO> list() throws RegraDeNegocioException, BancoDeDadosException {
        return cinemaService.listarCinema();
    }

    @Operation(summary = "Cadastro.", description = "Cadastramento de dados de usuários")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cadastro realizado com Sucesso!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PostMapping
    public ResponseEntity<CinemaDTO> cadastrarCinema(@Valid @RequestBody CinemaCreateDTO cinemaCreateDTO)
            throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(cinemaService.adicionarCinema(cinemaCreateDTO), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idCinema}")
    public ResponseEntity<Void> delete(@PathVariable Integer idCinema) throws RegraDeNegocioException, BancoDeDadosException {
        cinemaService.deletarCinema(idCinema);
        return null;
    }

    @Override
    @Hidden
    public ResponseEntity<CinemaDTO> create(Integer id, CinemaCreateDTO cinemaCreateDTO) {
        return null;
    }

    @Override
    public ResponseEntity<CinemaDTO> update(Integer id, CinemaCreateDTO cinemaCreateDTO) {
        return null;
    }

}
