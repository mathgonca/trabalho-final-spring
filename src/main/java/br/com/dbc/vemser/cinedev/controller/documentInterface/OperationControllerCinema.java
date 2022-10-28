package br.com.dbc.vemser.cinedev.controller.documentInterface;

import br.com.dbc.vemser.cinedev.dto.CinemaCreateDTO;
import br.com.dbc.vemser.cinedev.dto.CinemaDTO;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


public interface OperationControllerCinema {
    @Operation(summary = "Listagem de Cinemas ", description = "Listagem de cinemas que constam no banco")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna a lista de dados de acordo com a pesquisa"),
            @ApiResponse(responseCode = "403", description = "A algo de errado com as inserções de sua pesquisa"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @GetMapping
    public List<CinemaDTO> list() throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Cadastramento dos Cinemas ", description = "Cadastramento de dados dos Cinemas")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cinema Cadastrado com Sucesso!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PostMapping("/{idCinema}")
    public ResponseEntity<CinemaDTO> cadastrarCinema(@Valid @RequestBody CinemaCreateDTO cinemaCreateDTO)
            throws BancoDeDadosException, RegraDeNegocioException;

    @Operation(summary = "Edição e Atualização de Dados dos Cinemas ", description = "Edição e alteração de dados dos cinemas por 'ioCinema' !")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Atualização de dados realizada!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PutMapping("/{idCinema}")
    public ResponseEntity<CinemaDTO> update(@PathVariable("idCinema") Integer id,
                                            @Valid @RequestBody CinemaCreateDTO cinemaCreateDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Remoção de Dados dos Cinemas", description = "Remoção de dados dos Cinemas a partir da 'idCinema'!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Remoção de dados realizada!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados para busca!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @DeleteMapping("/{idCinema}") // localhost:8080/pessoa/10
    public ResponseEntity<Void> delete(@PathVariable("idCinema") Integer id) throws RegraDeNegocioException, BancoDeDadosException;
}