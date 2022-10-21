package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.entity.Cliente;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository repository) {
        this.clienteRepository = repository;
    }

    public List<Cliente> listarTodosClients() throws BancoDeDadosException {
        return clienteRepository.listar();
    }

    public Cliente cadastrarCliente(Cliente cliente) throws BancoDeDadosException, RegraDeNegocioException {
        String clienteCadastroCPF = cliente.getCpf();
        Optional<Cliente> clientePorCPF = clienteRepository.listarClientePorCPF(clienteCadastroCPF);

        String clienteCadastroEmail = cliente.getEmail();
        Optional<Cliente> clientePorEmail = clienteRepository.listarClientePorEmail(clienteCadastroEmail);

        if (clientePorCPF.isEmpty() && clientePorEmail.isEmpty()) {
            return clienteRepository.adicionar(cliente);
        } else {
            throw new RegraDeNegocioException("Cliente já cadastrado com os mesmos dados");
        }
    }
}
