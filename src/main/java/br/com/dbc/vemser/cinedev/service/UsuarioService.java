package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.UsuarioDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.UsuarioCreateClienteDTO;
import br.com.dbc.vemser.cinedev.dto.login.LoginDTO;
import br.com.dbc.vemser.cinedev.dto.recuperarsenhadto.RecuperarSenhaDTO;
import br.com.dbc.vemser.cinedev.entity.CargoEntity;
import br.com.dbc.vemser.cinedev.entity.ClienteEntity;
import br.com.dbc.vemser.cinedev.entity.UsuarioEntity;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.ClienteRepository;
import br.com.dbc.vemser.cinedev.repository.UsuarioRepository;
import br.com.dbc.vemser.cinedev.security.TokenService;
import br.com.dbc.vemser.cinedev.service.emails.EmailService;
import br.com.dbc.vemser.cinedev.service.emails.TipoEmails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    public static final int ROLE_ADMIN_ID = 1;
    public static final int ROLE_CLIENTE_ID = 2;
    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado!";
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final ClienteRepository clienteRepository;
    private final EmailService emailService;
    private final CargoService cargoService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    public Integer getIdLoggedUser() {
        return Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public UsuarioEntity getLoggedUser() throws RegraDeNegocioException {
        return findById(getIdLoggedUser());
    }

    public Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha) {
        return usuarioRepository.findByEmailAndSenha(login, senha);
    }

    public Optional<UsuarioEntity> findByID(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public UsuarioEntity findByEmail(String login) throws RegraDeNegocioException {
        return usuarioRepository.findByEmail(login)
                .orElseThrow(() -> new RegraDeNegocioException(USUARIO_NAO_ENCONTRADO));
    }

    public UsuarioEntity findById(Integer idLoggedUser) throws RegraDeNegocioException {
        return usuarioRepository.findById(idLoggedUser).orElseThrow(() -> new RegraDeNegocioException(USUARIO_NAO_ENCONTRADO));
    }

    public UsuarioDTO create(LoginDTO loginDTO) {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(loginDTO, UsuarioEntity.class);

        String senha = passwordEncoder.encode(usuarioEntity.getSenha()); //p codificar a senha
        usuarioEntity.setSenha(senha);
        usuarioRepository.save(usuarioEntity);
        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }

    public UsuarioDTO cadastrarAdministrador(LoginDTO loginDTO) throws RegraDeNegocioException {
        CargoEntity cargo = cargoService.findById(ROLE_ADMIN_ID);
        UsuarioEntity usuarioCapturado = objectMapper.convertValue(loginDTO, UsuarioEntity.class);
        String senha = passwordEncoder.encode(loginDTO.getSenha());
        usuarioCapturado.setSenha(senha);
        usuarioCapturado.setCargos(Set.of(cargo));
        UsuarioEntity usuarioSalvo = usuarioRepository.save(usuarioCapturado);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioSalvo, UsuarioDTO.class);
        return usuarioDTO;
    }

    public UsuarioDTO cadastrarCliente(UsuarioCreateClienteDTO clienteCreateDTO) throws RegraDeNegocioException {
//        String clienteCadastroCPF = clienteCreateDTO.getCpf();
//        Optional<ClienteEntity> clientePorCPF = clienteRepository.findByCpf(clienteCadastroCPF);
        CargoEntity cargo = cargoService.findById(ROLE_CLIENTE_ID);

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        String senha = passwordEncoder.encode(clienteCreateDTO.getSenha());
        usuarioEntity.setEmail(clienteCreateDTO.getEmail());
        usuarioEntity.setSenha(senha);
        usuarioEntity.setCargos(Set.of(cargo));
        usuarioEntity.setAtivo('S');
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
        UsuarioDTO usuarioDTO = objectMapper.convertValue(clienteEntityCadastrado, UsuarioDTO.class);
//        emailService.sendEmail(clienteDTO, TipoEmails.CREATE);
        return usuarioDTO;
    }

    public UsuarioEntity cadastrarUsuario(String email, String senha, Integer idRole) throws RegraDeNegocioException {
        CargoEntity cargo = cargoService.findById(idRole);
        String senhaEncode = passwordEncoder.encode(senha);

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setEmail(email);
        usuarioEntity.setSenha(senhaEncode);
        usuarioEntity.setCargos(Set.of(cargo));
        usuarioEntity.setAtivo('S');

        return usuarioRepository.save(usuarioEntity);
    }

    public String autenticar(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                );
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // UsuarioEntity
        Object principal = authenticate.getPrincipal();
        UsuarioEntity usuarioEntity = (UsuarioEntity) principal;

        return tokenService.getToken(usuarioEntity);
    }

    public void recuperarSenha(RecuperarSenhaDTO emailDTO, Integer idRoleRec) throws RegraDeNegocioException {

        UsuarioEntity usuarioEntity = findByEmail(emailDTO.getEmail());
        String token = tokenService.getTokenTrocarSenha(usuarioEntity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        emailService.sendEmail(usuarioDTO, TipoEmails.REC_SENHA, token);
        CargoEntity cargo = cargoService.findById(idRoleRec);
        usuarioEntity.getCargos().add(cargo);
        usuarioRepository.save(usuarioEntity);
    }

    public void mudarSenha(String senha, Integer idRoleRec) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = this.findByEmail(getLoggedUser().getEmail());
        UsuarioEntity usuarioAtualizado = removerCargo(usuarioEntity, idRoleRec);
        String senhaNova = passwordEncoder.encode(senha);
        usuarioEntity.setSenha(senhaNova);
        usuarioRepository.save(usuarioAtualizado);
    }

    public UsuarioEntity removerCargo(UsuarioEntity usuario, Integer idCargo) throws RegraDeNegocioException {
        CargoEntity cargoRemovido = cargoService.findById(idCargo);
        usuario.getCargos().stream()
                .filter(cargo -> cargo.getIdCargo() == cargoRemovido.getIdCargo())
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Senha já foi alterada!"));
        usuario.getCargos().remove(cargoRemovido);
        return usuario;
    }

}
