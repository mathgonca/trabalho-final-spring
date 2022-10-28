package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.controller.documentInterface.OperationControllerIngresso;
import br.com.dbc.vemser.cinedev.dto.IngressoCompradoDTO;
import br.com.dbc.vemser.cinedev.dto.IngressoCreateDTO;
import br.com.dbc.vemser.cinedev.dto.IngressoDTO;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.service.IngressoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/ingresso")

public class IngressoController implements OperationControllerIngresso {

    private final IngressoService ingressoService;

    @Override
    @GetMapping
    public List<IngressoCompradoDTO> listarIngressosComprados(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        return ingressoService.listarIngressosComprados(id);
    }

    @Override
    @GetMapping
    public List<IngressoDTO> listarIngressos() throws RegraDeNegocioException, BancoDeDadosException {
        return ingressoService.listarIngressos();
    }

    @Override
    @PostMapping
    public ResponseEntity<IngressoDTO> createIngresso(@Valid @RequestBody IngressoCreateDTO ingresso) throws RegraDeNegocioException,
            RegraDeNegocioException, BancoDeDadosException {
        return new ResponseEntity<>(ingressoService.createIngresso(ingresso), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{idIngresso}")
    public ResponseEntity<IngressoDTO> updateIngresso(@PathVariable("idIngresso") Integer id,
                                                      @Valid @RequestBody IngressoCreateDTO ingresso) throws RegraDeNegocioException, BancoDeDadosException {
        return new ResponseEntity<>(ingressoService.updateIngresso(id, ingresso), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idIngresso}")
    public ResponseEntity<Void> removeIngresso(@PathVariable("idIngresso") Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        ingressoService.removeIngresso(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


