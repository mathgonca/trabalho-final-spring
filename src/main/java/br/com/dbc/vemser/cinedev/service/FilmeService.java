package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.filmedto.FilmeCreateDTO;
import br.com.dbc.vemser.cinedev.dto.filmedto.FilmeDTO;
import br.com.dbc.vemser.cinedev.entity.FilmeEntity;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.FilmeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmeService {
    private final FilmeRepository filmeRepository;
    private final ObjectMapper objectMapper;

    public FilmeDTO adicionarFilme(FilmeCreateDTO filmeCapturado) throws RegraDeNegocioException {
        String filmeNome = filmeCapturado.getNome();
        Optional<FilmeEntity> filmePorNome = filmeRepository.findByNome(filmeNome);

        if (filmePorNome.isPresent()) {
            throw new RegraDeNegocioException("Erro! Nome do filme já consta em nossa lista de cadastros!");
        }

        FilmeEntity filmeEntityTransform = objectMapper.convertValue(filmeCapturado, FilmeEntity.class);
        FilmeEntity filmeEntitySalvo = filmeRepository.save(filmeEntityTransform);
        FilmeDTO filmeDTO = objectMapper.convertValue(filmeEntitySalvo, FilmeDTO.class);
        return filmeDTO;
    }

    public void removerFilme(Integer id) throws RegraDeNegocioException {
        FilmeEntity filme = listarPeloId(id);
        filmeRepository.deleteById(id);
    }

    public FilmeDTO editarFilme(Integer id, FilmeCreateDTO filmeCapturado) throws RegraDeNegocioException {
        FilmeEntity filme = listarPeloId(id);

        FilmeEntity filmeEntityTransf = objectMapper.convertValue(filmeCapturado, FilmeEntity.class);
        FilmeEntity filmeEntitySalvo = filmeRepository.save(filmeEntityTransf);
        FilmeDTO filmeDTO = objectMapper.convertValue(filmeEntitySalvo, FilmeDTO.class);
        return filmeDTO;
    }

    public List<FilmeDTO> listarTodosFilmes() throws RegraDeNegocioException {
        return filmeRepository.findAll().stream()
                .map(filmeEntity -> objectMapper.convertValue(filmeEntity, FilmeDTO.class))
                .toList();
    }

    public FilmeEntity listarPeloId(Integer idFilme) throws RegraDeNegocioException {
        return filmeRepository.findById(idFilme)
                .orElseThrow(() -> new RegraDeNegocioException("Filme não encontrado com Id procurado."));
    }

    public FilmeDTO listarDTOPeloId(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(listarPeloId(id), FilmeDTO.class);
    }
}
