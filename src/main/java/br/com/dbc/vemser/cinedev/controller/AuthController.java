package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.dto.UsuarioDTO;
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

    private final AuthenticationManager authenticationManager;

    @PostMapping
    public String autenticar(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getSenha()
                );

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Object principal = authenticate.getPrincipal();

        UsuarioEntity usuarioEntity = (UsuarioEntity) principal;

        String token = tokenService.getToken(usuarioEntity);

        return token;
    }

    @PostMapping("/novo-usuario")
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {

        UsuarioDTO usuarioDTO = usuarioService.create(loginDTO);

        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @GetMapping("/usuario-logado")
    public ResponseEntity<UsuarioDTO> retornarUsuario() throws RegraDeNegocioException {
        return new ResponseEntity<>(objectMapper.convertValue(usuarioService.getLoggedUser(), UsuarioDTO.class), HttpStatus.OK);
    }
}
