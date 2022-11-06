package br.com.dbc.vemser.cinedev.controller;


import br.com.dbc.vemser.cinedev.dto.relatorios.RelatorioCadastroCinemaFilmeDTO;
import br.com.dbc.vemser.cinedev.repository.CinemaRepository;
import br.com.dbc.vemser.cinedev.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/consultas-controller")
public class ControllerTeste {

    private final CinemaService cinemaService;

    private final CinemaRepository cinemaRepository;

    @GetMapping("/cinema-relatorio")
    public List<RelatorioCadastroCinemaFilmeDTO> listarRelatorioPersonalizado(@RequestParam(required = false, name = "idCinema") Integer idCinema) {
        return cinemaService.listarRelatorioPersonalizado(idCinema);
    }

    @GetMapping("/cinema-relatorio-rep")
    public List<RelatorioCadastroCinemaFilmeDTO> listarRelatorioPersonalizadoRep(@RequestParam(required = false, name = "idCinema") Integer idCinema) {
        return cinemaRepository.listarRelatorioPersonalizado(idCinema);
    }

}
