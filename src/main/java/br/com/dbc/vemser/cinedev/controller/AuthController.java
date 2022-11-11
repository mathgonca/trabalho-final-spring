package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.dto.UsuarioDTO;
import br.com.dbc.vemser.cinedev.dto.cinemadto.CinemaDTO;
import br.com.dbc.vemser.cinedev.dto.cinemadto.UsuarioCreateCinemaDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.ClienteDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.UsuarioCreateClienteDTO;
import br.com.dbc.vemser.cinedev.dto.login.LoginDTO;
import br.com.dbc.vemser.cinedev.entity.UsuarioEntity;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.security.TokenService;
import br.com.dbc.vemser.cinedev.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    private final ObjectMapper objectMapper;

    //FIXME injetar AuthenticationManager
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public String autenticar(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
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

        String token = tokenService.getToken(usuarioEntity);

        return token;
    }

    @GetMapping("/usuario-logado")
    public ResponseEntity<UsuarioDTO> retornarUsuario() throws RegraDeNegocioException {
        return new ResponseEntity<>(objectMapper.convertValue(usuarioService.getLoggedUser(), UsuarioDTO.class), HttpStatus.OK);
    }

    @PostMapping("/novo-cliente")
    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody @Valid UsuarioCreateClienteDTO criarClienteDTO) throws RegraDeNegocioException {

        ClienteDTO usuarioDTO = usuarioService.cadastrarCliente(criarClienteDTO);

        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PostMapping("/novo-cinema")
    public ResponseEntity<CinemaDTO> criarcinema(@RequestBody @Valid UsuarioCreateCinemaDTO criarCinema) throws RegraDeNegocioException {

        CinemaDTO cinemaDTO = usuarioService.cadastrarCinema(criarCinema);

        return new ResponseEntity<>(cinemaDTO, HttpStatus.OK);
    }
}
