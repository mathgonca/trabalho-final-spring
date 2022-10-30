package br.com.dbc.vemser.cinedev.controller;

import br.com.dbc.vemser.cinedev.controller.documentInterface.OperationControllerIngresso;
import br.com.dbc.vemser.cinedev.dto.IngressoCompradoDTO;
import br.com.dbc.vemser.cinedev.dto.IngressoCreateDTO;
import br.com.dbc.vemser.cinedev.dto.IngressoDTO;
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
    public List<IngressoDTO> listarIngressos() throws RegraDeNegocioException {
        return ingressoService.listarIngressos();
    }
    @Override
    @GetMapping("/{idIngresso}")
    public IngressoDTO listarIngressosPorId(@PathVariable("idIngresso") Integer id) throws RegraDeNegocioException {
        return ingressoService.findById(id);
    }

    @Override
    @GetMapping("/comprado")
    public List<IngressoDTO> listarIngressosComprados() throws RegraDeNegocioException {
        return ingressoService.listarIngressosComprados();
    }

    @Override
    @GetMapping("/comprado/{idCliente}")
    public List<IngressoCompradoDTO> listarIngressosCompradosPorCliente(@PathVariable("idCliente") Integer id) throws RegraDeNegocioException {
        return ingressoService.listarIngressosCompradosPorCliente(id);
    }

    @Override
    @PostMapping
    public ResponseEntity<IngressoDTO> createIngresso(@Valid @RequestBody IngressoCreateDTO ingresso) throws RegraDeNegocioException {
        return new ResponseEntity<>(ingressoService.createIngresso(ingresso), HttpStatus.OK);
    }

    @Override
    @PutMapping("/comprar/{idCliente}/ingresso/{idIngresso}")
    public ResponseEntity<IngressoCompradoDTO> comprarIngresso(@PathVariable("idCliente") Integer idCliente,
                                                               @PathVariable("idIngresso") Integer idIngresso) throws RegraDeNegocioException{
        return new ResponseEntity<>(ingressoService.comprarIngresso(idCliente, idIngresso), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idIngresso}")
    public ResponseEntity<Void> removeIngresso(@PathVariable("idIngresso") Integer id) throws RegraDeNegocioException {
        ingressoService.removeIngresso(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


