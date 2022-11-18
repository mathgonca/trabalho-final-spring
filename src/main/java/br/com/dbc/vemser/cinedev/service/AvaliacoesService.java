package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.UsuarioDTO;
import br.com.dbc.vemser.cinedev.dto.avaliacoes.AvaliacoesCreateDTO;
import br.com.dbc.vemser.cinedev.dto.avaliacoes.AvaliacoesDTO;
import br.com.dbc.vemser.cinedev.dto.avaliacoes.AvaliacoesDTOContador;
import br.com.dbc.vemser.cinedev.dto.avaliacoes.AvaliacoesNotaDTO;
import br.com.dbc.vemser.cinedev.dto.cinemadto.CinemaCreateDTO;
import br.com.dbc.vemser.cinedev.dto.cinemadto.CinemaDTO;
import br.com.dbc.vemser.cinedev.entity.AvaliacoesEntity;
import br.com.dbc.vemser.cinedev.entity.CinemaEntity;
import br.com.dbc.vemser.cinedev.entity.UsuarioEntity;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.AvaliacoesRepository;
import br.com.dbc.vemser.cinedev.service.emails.TipoEmails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AvaliacoesService {

    private final AvaliacoesRepository avaliacoesRepository;

    private final ObjectMapper objectMapper;

    public void salvarAvaliacoes(AvaliacoesDTO avaliacoesDTO) {
        var avaliacao = new AvaliacoesEntity();
        BeanUtils.copyProperties(avaliacoesDTO, avaliacao);
        avaliacoesRepository.save(avaliacao);
    }

    public AvaliacoesDTO adicionarAvaliacao(AvaliacoesCreateDTO avaliacoesCreateDTO) throws RegraDeNegocioException {
        String avaliacaoNome = avaliacoesCreateDTO.getNome();
        Optional<AvaliacoesEntity> avaliacoes = avaliacoesRepository.findByNome(avaliacaoNome);

        if (avaliacoes.isPresent()) {
            throw new RegraDeNegocioException("Erro! Avaliação do filme já consta em nosso cadastro!");
        }

        Double nota = avaliacoesCreateDTO.getNota();
        String comentario = avaliacoesCreateDTO.getComentario();
        AvaliacoesEntity avaliacoesEntity = new AvaliacoesEntity();
        avaliacoesEntity.setNome(avaliacaoNome);
        avaliacoesEntity.setNota(nota);
        avaliacoesEntity.setComentario(comentario);
        avaliacoesRepository.save(avaliacoesEntity);

        AvaliacoesDTO avaliacoesDTO = objectMapper.convertValue(avaliacoesEntity, AvaliacoesDTO.class);
        return avaliacoesDTO;
    }

    public List<AvaliacoesDTOContador> groupByNotaAndCount() throws RegraDeNegocioException{
        return this.avaliacoesRepository.groupByNota().stream().map(l->{
            return new AvaliacoesDTOContador(l.getNome(), l.getNota());
        }).collect(Collectors.toList());
    }

    public List<AvaliacoesDTO> listByNotaContains(Double nota) throws RegraDeNegocioException {
        return avaliacoesRepository.findAllByNotaContains(nota).stream().map(avaliacoesEntity -> objectMapper.convertValue(avaliacoesEntity, AvaliacoesDTO.class)).collect(Collectors.toList());
    }


}
