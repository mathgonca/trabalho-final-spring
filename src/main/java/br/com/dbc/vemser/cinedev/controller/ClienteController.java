package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.dto.ClienteCreateDTO;
import br.com.dbc.vemser.cinedev.dto.ClienteDTO;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.service.ClienteService;
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
@RequestMapping("/cliente")
public class ClienteController implements OperationController<Integer, ClienteDTO, ClienteCreateDTO> {

    private final ClienteService clienteService;

    @Override
    @GetMapping
    public List<ClienteDTO> list() throws RegraDeNegocioException, BancoDeDadosException {
        return clienteService.listarTodosClientes();
    }

    @Operation(summary = "Cadastro.", description = "Cadastramento de dados de usuários")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cadastro realizado com Sucesso!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PostMapping
    public ResponseEntity<ClienteDTO> cadastrarCliente(@Valid @RequestBody ClienteCreateDTO clienteCreateDTO)
            throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.cadastrarCliente(clienteCreateDTO), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer idCliente,
                                             @Valid @RequestBody ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        return new ResponseEntity<>(clienteService.atualizarCliente(idCliente, clienteCreateDTO), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> delete(@PathVariable Integer idCliente) throws RegraDeNegocioException, BancoDeDadosException {
        clienteService.deletarCliente(idCliente);
        return null;
    }

    @Override
    @Hidden
    public ResponseEntity<ClienteDTO> create(Integer id, ClienteCreateDTO clienteCreateDTO) {
        return null;
    }
}
