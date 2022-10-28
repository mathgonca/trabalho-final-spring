package br.com.dbc.vemser.cinedev.controller.documentInterface;

import br.com.dbc.vemser.cinedev.dto.ClienteCreateDTO;
import br.com.dbc.vemser.cinedev.dto.ClienteDTO;
import br.com.dbc.vemser.cinedev.dto.FilmeCreateDTO;
import br.com.dbc.vemser.cinedev.dto.FilmeDTO;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface OperationControllerCliente {


    @Operation(summary = "Realiza Listagem dos Clientes Cadastrados", description = "Realiza a Listagem dos dados referente a busca por Clientes.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna a lista de dados de acordo com a pesquisa"),
            @ApiResponse(responseCode = "403", description = "A algo de errado com as inserções de sua pesquisa"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @GetMapping
    public List<ClienteDTO> list() throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Realiza o cadastramento de Clientes", description = "Realiza o cadastramento de dados dos Clientes!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Atualização de dados realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PostMapping
    ResponseEntity<ClienteDTO> cadastrarCliente(@Valid @RequestBody ClienteCreateDTO clienteCreateDTO)
            throws BancoDeDadosException, RegraDeNegocioException;

    @Operation(summary = "Realiza a atualização das informações dos Clientes", description = "Realiza a alteração de dados dos usuários a partir da busca por ID!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Atualização de dados realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso!!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PutMapping("/{idPessoa}")
    public ResponseEntity<ClienteDTO> update(@PathVariable("idPessoa") Integer id,
                                             @Valid @RequestBody ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Realiza a remoção de dados cliente", description = "Realiza a remoção dos Cliente a partir do 'ID'!")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Remoção do Cliente realizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso!!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> delete(@PathVariable("idCliente") Integer id) throws RegraDeNegocioException, BancoDeDadosException;

}
