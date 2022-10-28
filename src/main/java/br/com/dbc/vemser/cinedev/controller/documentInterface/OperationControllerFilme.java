package br.com.dbc.vemser.cinedev.controller.documentInterface;

import br.com.dbc.vemser.cinedev.dto.FilmeCreateDTO;
import br.com.dbc.vemser.cinedev.dto.FilmeDTO;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface OperationControllerFilme {

    @Operation(summary = "Realiza a Listagem de Dados do filme", description = "Lista os dados referentes a busca do banco")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna a lista de dados de acordo com a pesquisa"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @GetMapping
    public List<FilmeDTO> list() throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Realiza o Cadastro do Filme.", description = "Realiza o Cadastramento de dados do Filme")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cadastro realizado com Sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso!!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PostMapping
    public ResponseEntity<FilmeDTO> cadastrarFilme(@Valid @RequestBody FilmeCreateDTO filmeCreateDTO) throws RegraDeNegocioException,
            RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Realiza a Atualização de Dados do filme ", description = "Edição e alteração de dados dos usuários a partir da referencia de pesquisa 'ID'!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Atualização de dados realizada!"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso!!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PutMapping("/{idPessoa}")
    public ResponseEntity<FilmeDTO> update(@PathVariable("idPessoa") Integer id,
                                           @Valid @RequestBody FilmeCreateDTO filmeCreateDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Remoção de Dados", description = "Remoção de dados dos usuários a partir da 'ID'!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Remoção de dados realizada!"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso!!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @DeleteMapping("/{idPessoa}") // localhost:8080/pessoa/10
    public ResponseEntity<Void> delete(@PathVariable("idPessoa") Integer id) throws RegraDeNegocioException, BancoDeDadosException;

}
