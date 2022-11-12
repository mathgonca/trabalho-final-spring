package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.dto.UsuarioDTO;
import br.com.dbc.vemser.cinedev.dto.cinemadto.CinemaDTO;
import br.com.dbc.vemser.cinedev.dto.cinemadto.UsuarioCreateCinemaDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.UsuarioCreateClienteDTO;
import br.com.dbc.vemser.cinedev.dto.login.LoginDTO;
import br.com.dbc.vemser.cinedev.dto.recuperarsenhadto.RecuperarSenhaDTO;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.security.AuthenticationService;
import br.com.dbc.vemser.cinedev.security.TokenService;
import br.com.dbc.vemser.cinedev.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

    private final AuthenticationService authenticationService;

    //FIXME injetar AuthenticationManager
    private final AuthenticationManager authenticationManager;

    @PostMapping("/fazer-login")
    public ResponseEntity<String> autenticar(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
       String token = authenticationService.autenticar(loginDTO);
       return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<Void> recuperarSenha(@Valid @RequestBody RecuperarSenhaDTO email) throws RegraDeNegocioException {
       authenticationService.recuperarSenha(email);
       return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario-logado")
    public ResponseEntity<br.com.dbc.vemser.cinedev.dto.UsuarioDTO> retornarUsuario() throws RegraDeNegocioException {
        return new ResponseEntity<>(objectMapper.convertValue(usuarioService.getLoggedUser(), br.com.dbc.vemser.cinedev.dto.UsuarioDTO.class), HttpStatus.OK);
    }

    @PostMapping("/novo-cliente")
    public ResponseEntity<UsuarioDTO> criarCliente(@RequestBody @Valid UsuarioCreateClienteDTO criarClienteDTO) throws RegraDeNegocioException {

        UsuarioDTO usuarioDTO = usuarioService.cadastrarCliente(criarClienteDTO);

        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PostMapping("/novo-cinema")
    public ResponseEntity<CinemaDTO> criarcinema(@RequestBody @Valid UsuarioCreateCinemaDTO criarCinema) throws RegraDeNegocioException {

        CinemaDTO cinemaDTO = usuarioService.cadastrarCinema(criarCinema);

        return new ResponseEntity<>(cinemaDTO, HttpStatus.OK);
    }
}
