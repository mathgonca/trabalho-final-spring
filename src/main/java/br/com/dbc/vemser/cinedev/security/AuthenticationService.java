package br.com.dbc.vemser.cinedev.security;

import br.com.dbc.vemser.cinedev.dto.UsuarioDTO;
import br.com.dbc.vemser.cinedev.dto.login.LoginDTO;
import br.com.dbc.vemser.cinedev.dto.recuperarsenhadto.RecuperarSenhaDTO;
import br.com.dbc.vemser.cinedev.entity.UsuarioEntity;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.service.UsuarioService;
import br.com.dbc.vemser.cinedev.service.emails.EmailService;
import br.com.dbc.vemser.cinedev.service.emails.TipoEmails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UsuarioService usuarioService;

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    private final ObjectMapper objectMapper;

    // FIXME buscar usuário pelo login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UsuarioEntity> usuarioOptional = usuarioService.findByEmail(email);
        return usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("E-mail inválido"));
    }

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

    public void recuperarSenha(RecuperarSenhaDTO emailDTO) throws RegraDeNegocioException {
        // FIXME adicionar mecanismo de autenticação para verificar se o usuário é válido e retornar o token
        UsuarioEntity usuarioEntity = (UsuarioEntity) usuarioService.findByEmail(emailDTO.getEmail()).orElseThrow(() ->
                new RegraDeNegocioException("Usuário não encontrado"));
        String token = tokenService.getTokenTrocarSenha(usuarioEntity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        emailService.sendEmail(usuarioDTO, TipoEmails.REC_SENHA, token);
    }
}
