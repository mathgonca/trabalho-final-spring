package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface OperationController<CHAVE, OBJETO, OBJETO2> {


    @Operation(summary = "Listagem de Dados", description = "Lista os dados referentes a busca do banco")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna a lista de dados de acordo com a pesquisa"),
            @ApiResponse(responseCode = "403", description = "A algo de errado com as inserções de sua pesquisa"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @GetMapping
    public List<OBJETO> list() throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Cadastro.", description = "Cadastramento de dados de usuários")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cadastro realizado com Sucesso!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PostMapping("/{idPessoa}")
    public ResponseEntity<OBJETO> create(CHAVE id, OBJETO2 objeto2);

    @Operation(summary = "Edição e Atualização de Dados", description = "Edição e alteração de dados dos usuários a partir da referencia de pesquisa 'ID'!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Atualização de dados realizada!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PutMapping("/{idPessoa}")
    public ResponseEntity<OBJETO> update(@PathVariable("idPessoa") CHAVE id,
                                         @Valid @RequestBody OBJETO2 objeto2) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Remoção de Dados", description = "Remoção de dados dos usuários a partir da 'ID'!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Remoção de dados realizada!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados para busca!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @DeleteMapping("/{idPessoa}") // localhost:8080/pessoa/10
    public ResponseEntity<Void> delete(@PathVariable("idPessoa") CHAVE id) throws RegraDeNegocioException, BancoDeDadosException;
}
