package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.dto.cinemadto.CinemaCreateDTO;
import br.com.dbc.vemser.cinedev.dto.cinemadto.CinemaDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.ClienteCreateDTO;
import br.com.dbc.vemser.cinedev.dto.clientedto.ClienteDTO;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.service.CinemaService;
import br.com.dbc.vemser.cinedev.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdministradorController {
    private final CinemaService cinemaService;
    private final ClienteService clienteService;


    @PutMapping("/{idCinema}")
    public ResponseEntity<CinemaDTO> update(@PathVariable Integer idCinema, @Valid @RequestBody CinemaCreateDTO cinemaCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(cinemaService.atualizarCinema(idCinema, cinemaCreateDTO), HttpStatus.OK);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer idCliente,
                                             @Valid @RequestBody ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.atualizarClienteAdmin(idCliente, clienteCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/deletar-cinema-usuario/{idCinema}")
    public ResponseEntity<Void> deletarCinemaPorUsuario(@PathVariable Integer idCinema) throws
            RegraDeNegocioException {
        cinemaService.deletarCinemaPorUsuario(idCinema);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/usuario-cliente/{idCliente}")
    public void deletarUsuarioCliente(@PathVariable Integer idCliente) throws RegraDeNegocioException {
        clienteService.deletarUsuarioCliente(idCliente);
    }
}
