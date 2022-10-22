package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.dto.ClienteCreateDTO;
import br.com.dbc.vemser.cinedev.dto.ClienteDTO;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.service.ClienteService;
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
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public List<ClienteDTO> listarTodosClientes() throws BancoDeDadosException {
        return clienteService.listarTodosClientes();
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> cadastrarCliente(@Valid @RequestBody ClienteCreateDTO clienteCreateDTO)
            throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.cadastrarCliente(clienteCreateDTO), HttpStatus.OK);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Integer idCliente,
                                                       @Valid @RequestBody ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.atualizarCliente(idCliente, clienteCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idCliente}")
    public void deletarCliente(@PathVariable Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        clienteService.deletarCliente(idCliente);
    }
}
