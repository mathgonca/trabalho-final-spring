package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.UsuarioDTO;
import br.com.dbc.vemser.cinedev.dto.login.LoginDTO;
import br.com.dbc.vemser.cinedev.entity.UsuarioEntity;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final ObjectMapper objectMapper;

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
}
