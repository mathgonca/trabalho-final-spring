package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.ClienteCreateDTO;
import br.com.dbc.vemser.cinedev.dto.ClienteDTO;
import br.com.dbc.vemser.cinedev.entity.Cliente;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.ClienteRepository;
import br.com.dbc.vemser.cinedev.service.emails.EmailService;
import br.com.dbc.vemser.cinedev.service.emails.TipoEmails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public List<ClienteDTO> listarTodosClientes() throws RegraDeNegocioException {
        try {
            List<Cliente> clienteList = clienteRepository.listar();
            return clienteList.stream()
                    .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                    .toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao retornar a lista de Cliente, tente de novo mais tarde.");
        }
    }

    public ClienteDTO cadastrarCliente(ClienteCreateDTO clienteCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        String clienteCadastroCPF = clienteCreateDTO.getCpf();
        Optional<Cliente> clientePorCPF = clienteRepository.listarClientePorCPF(clienteCadastroCPF);

        String clienteCadastroEmail = clienteCreateDTO.getEmail();
        Optional<Cliente> clientePorEmail = clienteRepository.listarClientePorEmail(clienteCadastroEmail);

        if (clientePorCPF.isEmpty() && clientePorEmail.isEmpty()) {
            try {
                Cliente cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
                Cliente clienteCadastrado = clienteRepository.adicionar(cliente);
                ClienteDTO clienteDTO = objectMapper.convertValue(clienteCadastrado, ClienteDTO.class);
                emailService.sendEmail(clienteDTO, TipoEmails.CREATE);
                return clienteDTO;
            } catch (BancoDeDadosException e) {
                throw new RegraDeNegocioException("Ocorreu um erro ao cadastrar o Cliente, tente de novo mais tarde.");
            }
        } else {
            throw new RegraDeNegocioException("Cliente já cadastrado com os mesmos dados");
        }
    }

    public Cliente listarClientePeloId(Integer idCliente) throws RegraDeNegocioException {
        try {
            Optional<Cliente> clienteOptional = clienteRepository.listarClientePorId(idCliente);

            if (clienteOptional.isEmpty()) {
                throw new RegraDeNegocioException("Cliente não cadastrado");
            }

            return clienteOptional.get();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao retornar o Cliente, tente de novo mais tarde.");
        }
    }

    public ClienteDTO listarClienteDTOPeloId(Integer idCliente) throws RegraDeNegocioException {
        return objectMapper.convertValue(listarClientePeloId(idCliente), ClienteDTO.class);
    }

    public ClienteDTO atualizarCliente(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {
        listarClientePeloId(idCliente);
        try {
            Cliente cliente = objectMapper.convertValue(clienteCreateDTO, Cliente.class);
            Cliente clienteAtualizado = clienteRepository.editar(idCliente, cliente);
            ClienteDTO clienteDTO = objectMapper.convertValue(clienteAtualizado, ClienteDTO.class);
            emailService.sendEmail(clienteDTO, TipoEmails.UPDATE);
            return clienteDTO;
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao atualizar o Cliente, tente de novo mais tarde.");
        }
    }

    public void deletarCliente(Integer idCliente) throws BancoDeDadosException, RegraDeNegocioException {
        Cliente cliente = listarClientePeloId(idCliente);
        try {
            ClienteDTO clienteDTO = objectMapper.convertValue(cliente, ClienteDTO.class);
            emailService.sendEmail(clienteDTO, TipoEmails.DELETE);
            clienteRepository.remover(idCliente);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao deletar o Cliente, tente de novo mais tarde.");
        }
    }
}
