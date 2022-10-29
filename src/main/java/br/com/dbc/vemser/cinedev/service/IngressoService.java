package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.ClienteDTO;
import br.com.dbc.vemser.cinedev.dto.IngressoCompradoDTO;
import br.com.dbc.vemser.cinedev.dto.IngressoCreateDTO;
import br.com.dbc.vemser.cinedev.dto.IngressoDTO;
import br.com.dbc.vemser.cinedev.entity.Cliente;
import br.com.dbc.vemser.cinedev.entity.Ingresso;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
import br.com.dbc.vemser.cinedev.repository.IngressoRepository;
import br.com.dbc.vemser.cinedev.service.emails.EmailService;
import br.com.dbc.vemser.cinedev.service.emails.TipoEmails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngressoService {

    private final IngressoRepository ingressoRepository;
    private final ClienteService clienteService;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;


    public List<IngressoCompradoDTO> listarIngressosComprados(Integer id) throws RegraDeNegocioException {
        try {
            return ingressoRepository.listarIngressoComprado(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de comunicação com o Banco de Dados");
        }
    }

    public List<IngressoDTO> listarIngressos() throws RegraDeNegocioException {
        List<Ingresso> ingressoslist = null;
        try {
            ingressoslist = ingressoRepository.listar();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de comunicação com o Banco de Dados");
        }
        return ingressoslist.stream()
                .map(ingresso -> objectMapper.convertValue(ingresso, IngressoDTO.class))
                .toList();
    }

    public IngressoDTO createIngresso(IngressoCreateDTO ingressoCreateDTO) throws RegraDeNegocioException {
        Ingresso ingresso = objectMapper.convertValue(ingressoCreateDTO, Ingresso.class);
        Ingresso ingressoSalvo = null;
        try {
            ingressoSalvo = ingressoRepository.adicionar(ingresso);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de comunicação com o Banco de Dados");
        }
        IngressoDTO ingressoDTO = objectMapper.convertValue(ingressoSalvo,IngressoDTO.class);
        return ingressoDTO;
    }

    public IngressoCompradoDTO comprarIngresso(Integer idCliente, Integer idIngresso) throws  RegraDeNegocioException {
        Cliente cliente = clienteService.listarClientePeloId(idCliente);
        ClienteDTO clienteDTO = objectMapper.convertValue(cliente, ClienteDTO.class);
        emailService.sendEmail(clienteDTO, TipoEmails.ING_COMPRADO);
        try {
            return  ingressoRepository.editar(idCliente,idIngresso);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de comunicação com o Banco de Dados");
        }
    }

    public IngressoDTO findById(Integer id) throws RegraDeNegocioException {
        IngressoDTO ingressoDTO= null;
        try {
            ingressoDTO = objectMapper.convertValue(ingressoRepository.listarIngressoPeloId(id), IngressoDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de comunicação com o Banco de Dados");
        }
        return ingressoDTO;
    }
    public void removeIngresso(Integer id) throws RegraDeNegocioException {
        try {
            ingressoRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de comunicação com o Banco de Dados");
        }
    }

}
