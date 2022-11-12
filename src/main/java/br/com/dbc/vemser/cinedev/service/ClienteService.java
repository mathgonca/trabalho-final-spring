package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.UsuarioDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.ClienteCreateDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.ClienteDTO;
import br.com.dbc.vemser.cinedev.dto.relatorios.RelatorioCadastroIngressoClienteDTO;
import br.com.dbc.vemser.cinedev.entity.ClienteEntity;
import br.com.dbc.vemser.cinedev.entity.UsuarioEntity;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.ClienteRepository;
import br.com.dbc.vemser.cinedev.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    private final UsuarioRepository usuarioRepository;
//    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    private final UsuarioService usuarioService;

    public ClienteEntity findById(Integer id) throws RegraDeNegocioException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }
    public List<UsuarioDTO> listarTodosClientes() {
        List<ClienteEntity> clienteEntityList = clienteRepository.findAll();
        return clienteEntityList.stream()
                .map(clienteEntity -> objectMapper.convertValue(clienteEntity, UsuarioDTO.class))
                .toList();
    }
    public ClienteEntity listarClientePorUsuario(Integer idUsuario) throws RegraDeNegocioException {
        return clienteRepository.findByIdUsuario(usuarioService.getIdLoggedUser())
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));
    }

    public UsuarioDTO listarClienteDTOPeloId(Integer idCliente) throws RegraDeNegocioException {
        return objectMapper.convertValue(listarClientePeloId(idCliente), UsuarioDTO.class);
    }

    public UsuarioDTO cadastrarCliente(ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {
        String clienteCadastroCPF = clienteCreateDTO.getCpf();
        Optional<ClienteEntity> clientePorCPF = clienteRepository.findByCpf(clienteCadastroCPF);

//        String clienteCadastroEmail = clienteCreateDTO.getEmail();
//        Optional<ClienteEntity> clientePorEmail = clienteRepository.findByEmail(clienteCadastroEmail);
//
//        if (clientePorCPF.isPresent() || clientePorEmail.isPresent()) {
//            throw new RegraDeNegocioException("Cliente já cadastrado com os mesmos dados");
//        }

        ClienteEntity clienteEntity = objectMapper.convertValue(clienteCreateDTO, ClienteEntity.class);
        ClienteEntity clienteEntityCadastrado = clienteRepository.save(clienteEntity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(clienteEntityCadastrado, UsuarioDTO.class);
//        emailService.sendEmail(clienteDTO, TipoEmails.CREATE);
        return usuarioDTO;
    }

    public ClienteEntity listarClientePeloId(Integer idCliente) throws RegraDeNegocioException {
        Optional<ClienteEntity> clienteOptional = clienteRepository.findById(idCliente);

        if (clienteOptional.isEmpty()) {
            throw new RegraDeNegocioException("Cliente não cadastrado");
        }

        return clienteOptional.get();
    }
    public UsuarioDTO atualizarCliente(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {
        listarClientePeloId(idCliente);

        ClienteEntity clienteEntity = objectMapper.convertValue(clienteCreateDTO, ClienteEntity.class);
        clienteEntity.setIdCliente(idCliente);

        ClienteEntity clienteEntityAtualizado = clienteRepository.save(clienteEntity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(clienteEntityAtualizado, UsuarioDTO.class);
//        emailService.sendEmail(clienteDTO, TipoEmails.UPDATE);

        return usuarioDTO;
    }

    public ClienteDTO atualizarClientePorUsuario(ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {

        ClienteEntity clienteRecuperado = listarClientePorUsuario(usuarioService.getIdLoggedUser());
        clienteRecuperado.setPrimeiroNome(clienteCreateDTO.getPrimeiroNome());
        clienteRecuperado.setUltimoNome(clienteCreateDTO.getUltimoNome());
        clienteRecuperado.setDataNascimento(clienteCreateDTO.getDataNascimento());
        clienteRecuperado.setCpf(clienteCreateDTO.getCpf());

        ClienteEntity clienteEntityAtualizado = clienteRepository.save(clienteRecuperado);
        ClienteDTO clienteDTO = objectMapper.convertValue(clienteEntityAtualizado, ClienteDTO.class);
//        emailService.sendEmail(clienteDTO, TipoEmails.UPDATE);

        return clienteDTO;
    }

    public void deletarCliente(Integer idCliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = listarClientePeloId(idCliente);
        ClienteDTO clienteDTO = objectMapper.convertValue(clienteEntity, ClienteDTO.class);
        UsuarioEntity usuario = new UsuarioEntity();
        UsuarioEntity usuarioEntity = clienteEntity.getUsuario();


//        emailService.sendEmail(clienteDTO, TipoEmails.DELETE);
        clienteRepository.deleteById(clienteEntity.getIdCliente());
        usuarioEntity.isEnabled();
    }

    public void deletarUsuarioCliente(Integer idCliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = listarClientePeloId(idCliente);
        UsuarioEntity usuario = new UsuarioEntity();
        UsuarioEntity usuarioEntity = clienteEntity.getUsuario();

//        emailService.sendEmail(clienteDTO, TipoEmails.DELETE);
        clienteRepository.deleteById(clienteEntity.getIdCliente());
        usuarioEntity.setAtivo('N');
        usuarioRepository.save(usuarioEntity);

    }
    public List<RelatorioCadastroIngressoClienteDTO> listarRelatorioPersonalizado(Integer idCliente){
        return clienteRepository.listarRelatorioPersonalizado(idCliente);
    }

}
