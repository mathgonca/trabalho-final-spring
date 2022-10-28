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


    public List<IngressoCompradoDTO> listarIngressosComprados(Integer id) throws BancoDeDadosException {
        return ingressoRepository.listarIngressoComprado(id);
    }

    public List<IngressoDTO> listarIngressos() throws BancoDeDadosException {
        List<Ingresso> ingressoslist = ingressoRepository.listar();
        return ingressoslist.stream()
                .map(ingresso -> objectMapper.convertValue(ingresso, IngressoDTO.class))
                .toList();
    }

    public IngressoDTO createIngresso(IngressoCreateDTO ingressoCreateDTO) throws BancoDeDadosException {
        Ingresso ingresso = objectMapper.convertValue(ingressoCreateDTO, Ingresso.class);
        Ingresso ingressoSalvo = ingressoRepository.adicionar(ingresso);
        IngressoDTO ingressoDTO = objectMapper.convertValue(ingressoSalvo,IngressoDTO.class);
        return ingressoDTO;
    }

    public List<IngressoCompradoDTO> comprarIngresso(Integer id, IngressoCreateDTO ingressoCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        Ingresso ingresso = objectMapper.convertValue(ingressoCreateDTO, Ingresso.class);
        Cliente cliente = clienteService.listarClientePeloId(ingresso.getIdCliente());
        ClienteDTO clienteDTO = objectMapper.convertValue(cliente, ClienteDTO.class);
        emailService.sendEmail(clienteDTO, TipoEmails.ING_COMPRADO);
        return  ingressoRepository.editar(id,ingresso);
    }

    public IngressoDTO findById(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        IngressoDTO ingressoDTO= objectMapper.convertValue(ingressoRepository.listarIngressoPeloId(id),IngressoDTO.class);
        return ingressoDTO;
    }
    public void removeIngresso(Integer id) throws BancoDeDadosException {
        ingressoRepository.remover(id);
    }

}
