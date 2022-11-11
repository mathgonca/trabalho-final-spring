package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.UsuarioDTO;
import br.com.dbc.vemser.cinedev.dto.cinemadto.CinemaDTO;
import br.com.dbc.vemser.cinedev.dto.cinemadto.UsuarioCreateCinemaDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.ClienteDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.UsuarioCreateClienteDTO;
import br.com.dbc.vemser.cinedev.dto.login.LoginDTO;
import br.com.dbc.vemser.cinedev.entity.CargoEntity;
import br.com.dbc.vemser.cinedev.entity.CinemaEntity;
import br.com.dbc.vemser.cinedev.entity.ClienteEntity;
import br.com.dbc.vemser.cinedev.entity.UsuarioEntity;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.CargoRepository;
import br.com.dbc.vemser.cinedev.repository.CinemaRepository;
import br.com.dbc.vemser.cinedev.repository.ClienteRepository;
import br.com.dbc.vemser.cinedev.repository.UsuarioRepository;
import br.com.dbc.vemser.cinedev.service.emails.EmailService;
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

    private final CinemaRepository cinemaRepository;

    private final EmailService emailService;

    private final CargoRepository cargoRepository;

    private final PasswordEncoder passwordEncoder;

    // FIXME construir métodoss necessários para o usuário




    public Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha){
        return usuarioRepository.findByEmailAndSenha(login, senha);
    }

    public Optional<UsuarioEntity> findByID(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    // FIXME CRIAR FIND POR LOGIN
    public Optional<UsuarioEntity> findByEmail(String login) {
        return usuarioRepository.findByEmail(login);
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
        String senha = passwordEncoder.encode(clienteCreateDTO.getSenha());
        usuarioEntity.setEmail(clienteCreateDTO.getEmail());
        usuarioEntity.setSenha(senha);
        usuarioEntity.setCargos(Set.of(cargo.get()));
        usuarioEntity.setAtivo("S");
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

    public CinemaDTO cadastrarCinema(UsuarioCreateCinemaDTO cinemaCapturado) throws RegraDeNegocioException {
        String cinemaNome = cinemaCapturado.getNome();
        Optional<CinemaEntity> cinemaPorNome = cinemaRepository.findByNome(cinemaNome);

        if (cinemaPorNome.isPresent()) {
            throw new RegraDeNegocioException("Erro! Nome do Cinema já consta em nossa lista de cadastros!");
        }
        Optional<CargoEntity> cargo = cargoRepository.findById(1);
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        String senha = passwordEncoder.encode(cinemaCapturado.getSenha());
        usuarioEntity.setEmail(cinemaCapturado.getEmail());
        usuarioEntity.setSenha(senha);
        usuarioEntity.setCargos(Set.of(cargo.get()));
        usuarioEntity.setAtivo("S");
        usuarioRepository.save(usuarioEntity);
        CinemaEntity cinema = new CinemaEntity();
        cinema.setUsuario(usuarioEntity);
        cinema.setNome(cinemaCapturado.getNome());
        cinema.setEstado(cinemaCapturado.getEstado());
        cinema.setCidade(cinemaCapturado.getCidade());
        CinemaEntity cinemaEntitySalvo = cinemaRepository.save(cinema);
        CinemaDTO cinemaDTO = objectMapper.convertValue(cinemaEntitySalvo, CinemaDTO.class);

        return cinemaDTO;
    }
}
