package br.com.dbc.vemser.cinedev.controller;


import br.com.dbc.vemser.cinedev.controller.documentInterface.OperationControllerFilme;
import br.com.dbc.vemser.cinedev.dto.FilmeCreateDTO;
import br.com.dbc.vemser.cinedev.dto.FilmeDTO;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.service.FilmeService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/filme")
public class FilmeController implements OperationControllerFilme {

    private final FilmeService filmeService;

    @Override
    @GetMapping
    public List<FilmeDTO> list() throws RegraDeNegocioException, BancoDeDadosException {
        return filmeService.listarTodosFilmes();
    }

    @Override
    @PostMapping
    public ResponseEntity<FilmeDTO> cadastrarFilme(@Valid @RequestBody FilmeCreateDTO filme) throws RegraDeNegocioException,
            RegraDeNegocioException, BancoDeDadosException {
        return new ResponseEntity<>(filmeService.adicionarFilme(filme), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{idFilme}")
    public ResponseEntity<FilmeDTO> update(@PathVariable("idFilme") Integer id,
                                           @Valid @RequestBody FilmeCreateDTO filmeAtualizar) throws RegraDeNegocioException, BancoDeDadosException {
        return new ResponseEntity<>(filmeService.editarFilme(id, filmeAtualizar), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idFilme}")
    public ResponseEntity<Void> delete(@PathVariable("idFilme") Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        filmeService.removerFilme(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{idFilme}")
    @Hidden
    public FilmeDTO listarByIdFilme(@PathVariable("idFilme") Integer idFilme)
            throws BancoDeDadosException, RegraDeNegocioException {
        return filmeService.findById(idFilme);
    }

    @GetMapping("/horario/cinema/{idFilme}/{idCinema}")
    @Hidden
    public List<LocalDateTime> listarFilmePorHorario(@PathVariable("idFilme") Integer idFilme,
                                                     @PathVariable("idCinema") Integer idCinema) throws RegraDeNegocioException, BancoDeDadosException {
        return filmeService.listarFilmesPorHorario(idFilme, idCinema);
    }


}
