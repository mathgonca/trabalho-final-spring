package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.UsuarioDTO;
import br.com.dbc.vemser.cinedev.dto.cinemadto.CinemaDTO;
import br.com.dbc.vemser.cinedev.dto.cinemadto.UsuarioCreateCinemaDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.UsuarioCreateClienteDTO;
import br.com.dbc.vemser.cinedev.dto.login.LoginDTO;
import br.com.dbc.vemser.cinedev.dto.recuperarsenhadto.RecuperarSenhaDTO;
import br.com.dbc.vemser.cinedev.entity.CargoEntity;
import br.com.dbc.vemser.cinedev.entity.CinemaEntity;
import br.com.dbc.vemser.cinedev.entity.ClienteEntity;
import br.com.dbc.vemser.cinedev.entity.UsuarioEntity;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.CinemaRepository;
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
    public static final int ROLE_RECCLIENTE_ID = 4;
    public static final int ROLE_RECCINEMA_ID = 5;
    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado!";
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final ClienteRepository clienteRepository;
    private final CinemaRepository cinemaRepository;
    private final EmailService emailService;
    private final CargoService cargoService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    // FIXME construir métodoss necessários para o usuário

    public String autenticar(@RequestBody @Valid LoginDTO loginDTO) {
        // FIXME adicionar mecanismo de autenticação para verificar se o usuário é válido e retornar o token

        //FIXME criar objeto UsernamePasswordAuthenticationToken com o usuário e senha
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                );

        //FIXME utilizar AuthenticationManager para se autenticar

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // UsuarioEntity
        //FIXME recuperar usuário após da autenticação (getPrincipal())
        Object principal = authenticate.getPrincipal();

        //FIXME GERAR TOKEN (trocar null por usuarioEntity da autenticação)
        UsuarioEntity usuarioEntity = (UsuarioEntity) principal;

        return tokenService.getToken(usuarioEntity);
    }

    public void recuperarSenhaCliente(RecuperarSenhaDTO emailDTO) throws RegraDeNegocioException {
        // FIXME adicionar mecanismo de autenticação para verificar se o usuário é válido e retornar o token
        UsuarioEntity usuarioEntity = findByEmail(emailDTO.getEmail()).orElseThrow(() ->
                new RegraDeNegocioException(USUARIO_NAO_ENCONTRADO));

        String token = tokenService.getTokenTrocarSenha(usuarioEntity);

        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);

        emailService.sendEmail(usuarioDTO, TipoEmails.REC_SENHA, token);

        CargoEntity cargo = cargoService.findById(ROLE_RECCLIENTE_ID);

        usuarioEntity.getCargos().add(cargo);

        usuarioRepository.save(usuarioEntity);
    }

    public void recuperarSenhaCinema(RecuperarSenhaDTO emailDTO) throws RegraDeNegocioException {
        // FIXME adicionar mecanismo de autenticação para verificar se o usuário é válido e retornar o token

        UsuarioEntity usuarioEntity = findByEmail(emailDTO.getEmail()).orElseThrow(() ->
                new RegraDeNegocioException(USUARIO_NAO_ENCONTRADO));

        String token = tokenService.getTokenTrocarSenha(usuarioEntity);

        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);

        emailService.sendEmail(usuarioDTO, TipoEmails.REC_SENHA, token);

        CargoEntity cargo = cargoService.findById(ROLE_RECCINEMA_ID);

        usuarioEntity = objectMapper.convertValue(usuarioRepository.findByEmail(emailDTO.getEmail()), UsuarioEntity.class);

        usuarioEntity.getCargos().add(cargo);

        usuarioRepository.save(usuarioEntity);
    }

    public void mudarSenhaCliente(String senha) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = this.findByEmail(getLoggedUser().getEmail())
                .orElseThrow(() -> new RegraDeNegocioException(USUARIO_NAO_ENCONTRADO));

        CargoEntity cargoRemovido = cargoService.findById(ROLE_RECCLIENTE_ID);

        usuarioEntity.getCargos().stream()
                .filter(cargo -> cargo.getIdCargo() == cargoRemovido.getIdCargo())
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Senha já foi alterada!"));

        String senhaNova = passwordEncoder.encode(senha);
        usuarioEntity.setSenha(senhaNova);
        usuarioEntity.getCargos().remove(cargoRemovido);
        usuarioRepository.save(usuarioEntity);
    }

    public void mudarSenhaCinema(String senha) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = this.findByEmail(getLoggedUser().getEmail()).orElseThrow(()
                -> new RegraDeNegocioException(USUARIO_NAO_ENCONTRADO));

        CargoEntity cargoRemovido = cargoService.findById(ROLE_RECCINEMA_ID);

        usuarioEntity.getCargos().stream()
                .filter(cargo -> cargo.getIdCargo() == cargoRemovido.getIdCargo())
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Senha já foi alterada!"));

        String senhaNova = passwordEncoder.encode(senha);
        usuarioEntity.setSenha(senhaNova);
        usuarioEntity.getCargos().remove(cargoRemovido);
        usuarioRepository.save(usuarioEntity);
    }

    public Integer getIdLoggedUser() {
        return Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha) {
        return usuarioRepository.findByEmailAndSenha(login, senha);
    }

    public Optional<UsuarioEntity> findByID(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    // FIXME CRIAR FIND POR LOGIN
    public Optional<UsuarioEntity> findByEmail(String login) {
        return usuarioRepository.findByEmail(login);
    }

    public UsuarioDTO create(LoginDTO loginDTO) {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(loginDTO, UsuarioEntity.class);

        String senha = passwordEncoder.encode(usuarioEntity.getSenha()); //p codificar a senha
        usuarioEntity.setSenha(senha);
        usuarioRepository.save(usuarioEntity);
        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }


    public UsuarioEntity getLoggedUser() throws RegraDeNegocioException {
        return findById(getIdLoggedUser());
    }

    public UsuarioEntity findById(Integer idLoggedUser) throws RegraDeNegocioException {
        return usuarioRepository.findById(idLoggedUser).orElseThrow(() -> new RegraDeNegocioException(USUARIO_NAO_ENCONTRADO));
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

    public CinemaDTO cadastrarCinema(UsuarioCreateCinemaDTO cinemaCapturado) throws RegraDeNegocioException {
        String cinemaNome = cinemaCapturado.getNome();
        Optional<CinemaEntity> cinemaPorNome = cinemaRepository.findByNome(cinemaNome);

        if (cinemaPorNome.isPresent()) {
            throw new RegraDeNegocioException("Erro! Nome do Cinema já consta em nossa lista de cadastros!");
        }

        CargoEntity cargo = cargoService.findById(ROLE_ADMIN_ID);

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        String senha = passwordEncoder.encode(cinemaCapturado.getSenha());
        usuarioEntity.setEmail(cinemaCapturado.getEmail());
        usuarioEntity.setSenha(senha);
        usuarioEntity.setCargos(Set.of(cargo));
        usuarioEntity.setAtivo('S');
        usuarioRepository.save(usuarioEntity);
        CinemaEntity cinema = new CinemaEntity();
        cinema.setUsuario(usuarioEntity);
        cinema.setNome(cinemaCapturado.getNome());
        cinema.setEstado(cinemaCapturado.getEstado());
        cinema.setCidade(cinemaCapturado.getCidade());
        CinemaEntity cinemaEntitySalvo = cinemaRepository.save(cinema);

        return objectMapper.convertValue(cinemaEntitySalvo, CinemaDTO.class);
    }

}
