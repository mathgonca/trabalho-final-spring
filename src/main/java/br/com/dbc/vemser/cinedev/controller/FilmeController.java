package br.com.dbc.vemser.cinedev.controller;


import br.com.dbc.vemser.cinedev.dto.FilmeCreateDTO;
import br.com.dbc.vemser.cinedev.dto.FilmeDTO;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.service.FilmeService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class FilmeController implements OperationController<Integer, FilmeDTO, FilmeCreateDTO> {

    private final FilmeService filmeService;

    @Override
    @GetMapping
    public List<FilmeDTO> list() throws RegraDeNegocioException, BancoDeDadosException {
        return filmeService.listarTodosFilmes();
    }

    @GetMapping("/{idFilme}")
    @Hidden
    public FilmeDTO listarByIdFilme(@PathVariable("idFilme") Integer idFilme) throws BancoDeDadosException, RegraDeNegocioException {
        return filmeService.findById(idFilme);
    }

    @GetMapping("/horario/cinema/{idFilme}/{idCinema}")
    @Hidden
    public List<LocalDateTime> listarFilmePorHorario(@PathVariable("idFilme") Integer idFilme, @PathVariable("idCinema") Integer idCinema) throws RegraDeNegocioException, BancoDeDadosException {
        return filmeService.listarFilmesPorHorario(idFilme, idCinema);
    }

    @Operation(summary = "Cadastro.", description = "Cadastramento de dados de usuários")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cadastro realizado com Sucesso!"),
            @ApiResponse(responseCode = "403", description = "Erro na inserção de dados!"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")})
    @PostMapping
    public ResponseEntity<FilmeDTO> cadastrarFilme(@Valid @RequestBody FilmeCreateDTO filme) throws RegraDeNegocioException,
            RegraDeNegocioException, BancoDeDadosException {
        return new ResponseEntity<>(filmeService.adicionarFilme(filme), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{idFilme}")
    public ResponseEntity<FilmeDTO> update(@PathVariable("idFilme") Integer id, @Valid @RequestBody FilmeCreateDTO filmeAtualizar) throws RegraDeNegocioException, BancoDeDadosException {
        return new ResponseEntity<>(filmeService.editarFilme(id, filmeAtualizar), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idFilme}")
    public ResponseEntity<Void> delete(@PathVariable("idFilme") Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        filmeService.removerFilme(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Hidden
    public ResponseEntity<FilmeDTO> create(Integer id, FilmeCreateDTO filmeCreateDTO) {
        return null;
    }

}
