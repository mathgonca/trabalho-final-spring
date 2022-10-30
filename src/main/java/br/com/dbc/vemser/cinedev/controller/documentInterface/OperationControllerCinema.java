package br.com.dbc.vemser.cinedev.controller.documentInterface;

import br.com.dbc.vemser.cinedev.dto.CinemaCreateDTO;
import br.com.dbc.vemser.cinedev.dto.CinemaDTO;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;


public interface OperationControllerCinema {

    @Operation(summary = "Realiza a listagem de Cinemas ", description = "Lista os cinemas que constam em nosso sistema")

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "A algo de errado com as inserções de sua pesquisa"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    List<CinemaDTO> list() throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Realiza a listagem de Cinemas por Id!", description = "Lista os cinemas que constam em nosso sistema por Id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    CinemaDTO listaPorId(@PathVariable("idCinema") Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Realiza o cadastramento dos Cinemas ", description = "Cadastramento de dados dos Cinemas")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cinema cadastrado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    ResponseEntity<CinemaDTO> cadastrarCinema(@Valid @RequestBody CinemaCreateDTO cinemaCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Realiza atualização de dados dos Cinemas ", description = "Realiza a alteração de dados dos Cinemas De acordo com o 'idCinema' !")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Atualização de dados realizada!"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    ResponseEntity<CinemaDTO> update(@PathVariable("idCinema") Integer id,
                                     @Valid @RequestBody CinemaCreateDTO cinemaCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Realiza Remoção dos Cinemas", description = "Realiza a remoção dos Cinemas a partir do 'idCinema'!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Remoção de cinema realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    ResponseEntity<Void> delete(@PathVariable("idCinema") Integer id) throws RegraDeNegocioException;
}