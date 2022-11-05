package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.controller.documentInterface.OperationControllerCliente;
import br.com.dbc.vemser.cinedev.dto.clientedto.ClienteCreateDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.ClienteDTO;
import br.com.dbc.vemser.cinedev.dto.relatorios.RelatorioCadastroCinemaFilmeDTO;
import br.com.dbc.vemser.cinedev.dto.relatorios.RelatorioCadastroIngressoClienteDTO;
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
public class ClienteController implements OperationControllerCliente {

    private final ClienteService clienteService;

    @Override
    @GetMapping
    public List<ClienteDTO> list() throws RegraDeNegocioException {
        return clienteService.listarTodosClientes();
    }

    @GetMapping("/{idCliente}")
    public ClienteDTO listarClientePeloId(@PathVariable Integer idCliente) throws RegraDeNegocioException {
        return clienteService.listarClienteDTOPeloId(idCliente);
    }

    @Override
    @PostMapping
    public ResponseEntity<ClienteDTO> cadastrarCliente(@Valid @RequestBody ClienteCreateDTO clienteCreateDTO)
            throws BancoDeDadosException, RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.cadastrarCliente(clienteCreateDTO), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer idCliente,
                                             @Valid @RequestBody ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.atualizarCliente(idCliente, clienteCreateDTO), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idCliente}")
    public void delete(@PathVariable Integer idCliente) throws RegraDeNegocioException {
        clienteService.deletarCliente(idCliente);
    }

    @GetMapping("/cliente-relatorio")
    public List<RelatorioCadastroIngressoClienteDTO> listarRelatorioPersonalizado(@RequestParam(required = false, name = "idCliente") Integer idCliente){
        return clienteService.listarRelatorioPersonalizado(idCliente);
    }
}


