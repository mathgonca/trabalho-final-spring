package br.com.dbc.vemser.cinedev.controller;


import br.com.dbc.vemser.cinedev.entity.Cliente;
import br.com.dbc.vemser.cinedev.entity.Filme;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.service.FilmeService;
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
public class FilmeController {

    private final FilmeService filmeService;

    @GetMapping
    public List<Filme> listarFilmes() throws  BancoDeDadosException {
        return filmeService.listarTodosFilmes();
    }
    @GetMapping("/horario/cinema/{idFilme}/{idCinema}")
    public List<LocalDateTime> listarFilmePorHorario(@PathVariable("idFilme") Integer idFilme, @PathVariable("idCinema") Integer idCinema) throws  BancoDeDadosException {
        return filmeService.listarFilmesPorHorario(idFilme, idCinema);
    }

    @PostMapping
    public ResponseEntity<Filme> cadastrarFilme(@Valid @RequestBody Filme filme) throws BancoDeDadosException,
            RegraDeNegocioException {
        return new ResponseEntity<>(filmeService.adicionarFilme(filme), HttpStatus.OK);
    }

    @PutMapping("/{idFilme}")
    public ResponseEntity<Filme> editarFilme(@PathVariable("idFilme") Integer id, @Valid @RequestBody Filme filmeAtualizar) throws BancoDeDadosException{
        return new ResponseEntity<>(filmeService.editarFilme(id, filmeAtualizar), HttpStatus.OK);
    }

    @DeleteMapping("/{idFilme}")
    public ResponseEntity<Filme> removerFilme(@PathVariable("idFilme") Integer id) throws BancoDeDadosException{
        filmeService.removerFilme(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
