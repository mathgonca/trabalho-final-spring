package br.com.dbc.vemser.cinedev.controller.documentInterface;

import br.com.dbc.vemser.cinedev.dto.*;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface OperationControllerIngresso {

    @Operation(summary = "Listagem de Ingressos comprados ", description = "Lista os Ingressos comprados referentes a busca do banco")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna a lista de Ingressos comprados de acordo com a pesquisa"),
            @ApiResponse(responseCode = "403", description = "A algo de errado com as inserções de sua pesquisa"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @GetMapping
    public List<IngressoCompradoDTO> listarIngressosComprados(Integer id) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Listagem de Ingressos por ID ", description = "Lista os Ingressos por IdIngresso")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna a lista de Ingressos comprados de acordo com a pesquisa"),
            @ApiResponse(responseCode = "403", description = "A algo de errado com as inserções de sua pesquisa"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @GetMapping("/{idIngresso}")
    public IngressoDTO listarIngressosPorId(@PathVariable("idIngresso") Integer id) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Lista os Ingressos ", description = "Lista os Ingressos referentes a busca do banco")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna a lista de Ingressos de acordo com a pesquisa"),
            @ApiResponse(responseCode = "403", description = "A algo de errado com as inserções de sua pesquisa"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @GetMapping
    public List<IngressoDTO> listarIngressos() throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Cadastro.", description = "Cadastramento de dados de usuários")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cadastro realizado com Sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso!!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PostMapping
    public ResponseEntity<IngressoDTO> createIngresso(@Valid @RequestBody IngressoCreateDTO ingresso) throws RegraDeNegocioException,
            RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Realiza a atualização de Ingressos", description = " Realzia a alteração de dados do Ingresso a partir da referencia de pesquisa 'ID'!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Atualização de Ingresso realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso!!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PutMapping("/{idIngresso}")
    public List<IngressoCompradoDTO> updateIngresso(@PathVariable("idPessoa") Integer id,
                                                      @Valid @RequestBody IngressoCreateDTO ingressoCreateDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Remove Um Ingresso ", description = "Remoção do Ingresso  partir do 'id'!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Remoção do Ingresso realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso!!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @DeleteMapping("/{idIngresso}") // localhost:8080/ingresso/10
    public ResponseEntity<Void> removeIngresso(@PathVariable("idPessoa") Integer id) throws RegraDeNegocioException, BancoDeDadosException;

}