package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.UsuarioDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.ClienteCreateDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.ClienteDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.UsuarioCreateClienteDTO;
import br.com.dbc.vemser.cinedev.dto.login.LoginDTO;
import br.com.dbc.vemser.cinedev.entity.CargoEntity;
import br.com.dbc.vemser.cinedev.entity.ClienteEntity;
import br.com.dbc.vemser.cinedev.entity.UsuarioEntity;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.CargoRepository;
import br.com.dbc.vemser.cinedev.repository.ClienteRepository;
import br.com.dbc.vemser.cinedev.repository.UsuarioRepository;
import br.com.dbc.vemser.cinedev.service.emails.EmailService;
import br.com.dbc.vemser.cinedev.service.emails.TipoEmails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final ObjectMapper objectMapper;


    private final ClienteRepository clienteRepository;
    private final EmailService emailService;

    private final CargoRepository cargoRepository;

    private final PasswordEncoder passwordEncoder;
    // FIXME construir métodoss necessários para o usuário




    public Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha){
        return usuarioRepository.findByLoginAndSenha(login, senha);
    }

    public Optional<UsuarioEntity> findByID(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    // FIXME CRIAR FIND POR LOGIN
    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public UsuarioDTO create(LoginDTO loginDTO)throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(loginDTO, UsuarioEntity.class);

        String senha = passwordEncoder.encode(usuarioEntity.getSenha()); //p codificar a senha
        usuarioEntity.setSenha(senha);
        usuarioRepository.save(usuarioEntity);
        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }

    public Integer getIdLoggedUser(){
        return Integer.parseInt ( (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public UsuarioEntity getLoggedUser() throws RegraDeNegocioException{
        return findById(getIdLoggedUser());
    }

    public UsuarioEntity findById(Integer idUsuario) throws RegraDeNegocioException{
        return usuarioRepository.findById(idUsuario).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado!"));
    }

    public ClienteDTO cadastrarCliente(UsuarioCreateClienteDTO clienteCreateDTO) throws RegraDeNegocioException {
//        String clienteCadastroCPF = clienteCreateDTO.getCpf();
//        Optional<ClienteEntity> clientePorCPF = clienteRepository.findByCpf(clienteCadastroCPF);
        Optional<CargoEntity> cargo = cargoRepository.findById(2);
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setLogin(clienteCreateDTO.getLogin());
        usuarioEntity.setSenha(clienteCreateDTO.getSenha());
        usuarioEntity.setCargos(Set.of(cargo.get()));
        usuarioRepository.save(usuarioEntity);

//        String clienteCadastroEmail = clienteCreateDTO.getEmail();
//        Optional<ClienteEntity> clientePorEmail = clienteRepository.findByEmail(clienteCadastroEmail);

//        if (clientePorCPF.isPresent() || clientePorEmail.isPresent()) {
//            throw new RegraDeNegocioException("Cliente já cadastrado com os mesmos dados");
//        }

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setUsuario(usuarioEntity);
        clienteEntity.setCpf(clienteCreateDTO.getCpf());
        clienteEntity.setDataNascimento(clienteCreateDTO.getDataNascimento());
        clienteEntity.setPrimeiroNome(clienteCreateDTO.getPrimeiroNome());
        clienteEntity.setUltimoNome(clienteCreateDTO.getUltimoNome());
        ClienteEntity clienteEntityCadastrado = clienteRepository.save(clienteEntity);
        ClienteDTO clienteDTO = objectMapper.convertValue(clienteEntityCadastrado, ClienteDTO.class);
//        emailService.sendEmail(clienteDTO, TipoEmails.CREATE);
        return clienteDTO;
    }
}
