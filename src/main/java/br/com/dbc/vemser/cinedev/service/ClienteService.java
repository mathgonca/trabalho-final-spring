package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.ClienteCreateDTO;
import br.com.dbc.vemser.cinedev.dto.ClienteDTO;
import br.com.dbc.vemser.cinedev.entity.Cliente;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;

    public List<ClienteDTO> listarTodosClientes() throws BancoDeDadosException {
        List<Cliente> clienteList = clienteRepository.listar();
        return clienteList.stream()
                .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                .toList();
    }

    public ClienteDTO cadastrarCliente(ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        String clienteCadastroCPF = clienteCreateDTO.getCpf();
        Optional<Cliente> clientePorCPF = clienteRepository.listarClientePorCPF(clienteCadastroCPF);

        String clienteCadastroEmail = clienteCreateDTO.getEmail();
        Optional<Cliente> clientePorEmail = clienteRepository.listarClientePorEmail(clienteCadastroEmail);

        if (clientePorCPF.isEmpty() && clientePorEmail.isEmpty()) {
            Cliente cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
            Cliente clienteCadastrado = clienteRepository.adicionar(cliente);

            return objectMapper.convertValue(clienteCadastrado, ClienteDTO.class);
        } else {
            throw new RegraDeNegocioException("Cliente já cadastrado com os mesmos dados");
        }
    }

    public Cliente listarClientePeloId(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        Optional<Cliente> clienteOptional = clienteRepository.listarClientePorId(idCliente);

        if (clienteOptional.isEmpty()) {
            throw new RegraDeNegocioException("Cliente não cadastrado");
        }

        return clienteOptional.get();
    }

    public ClienteDTO atualizarCliente(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        listarClientePeloId(idCliente);

        Cliente cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
        Cliente clienteAtualizado = clienteRepository.editar(idCliente, cliente);

        return objectMapper.convertValue(clienteAtualizado, ClienteDTO.class);
    }

    public void deletarCliente(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        listarClientePeloId(idCliente);
        clienteRepository.remover(idCliente);
    }
}
