package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.ClienteDTO;
import br.com.dbc.vemser.cinedev.dto.IngressoCompradoDTO;
import br.com.dbc.vemser.cinedev.dto.IngressoCreateDTO;
import br.com.dbc.vemser.cinedev.dto.IngressoDTO;
import br.com.dbc.vemser.cinedev.entity.Cliente;
import br.com.dbc.vemser.cinedev.entity.Ingresso;
import br.com.dbc.vemser.cinedev.entity.enums.Disponibilidade;
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


    public List<IngressoDTO> listarIngressos() throws RegraDeNegocioException {
        try {
            List<Ingresso> ingressoslist = ingressoRepository.listarIngressos();
            return ingressoslist.stream()
                    .map(ingresso -> objectMapper.convertValue(ingresso, IngressoDTO.class))
                    .toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de comunicação com o Banco de Dados!" +
                    "Não foi possivel obter a lista requisitada.");
        }
    }
    public List<IngressoDTO> listarIngressosComprados() throws RegraDeNegocioException {
        try {
            List<Ingresso> ingressoslist = ingressoRepository.listarIngressosComprados();
            return ingressoslist.stream()
                    .map(ingresso -> objectMapper.convertValue(ingresso, IngressoDTO.class))
                    .toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de comunicação com o Banco de Dados!" +
                    "Não foi possivel obter a lista requisitada.");
        }
    }

    public List<IngressoCompradoDTO> listarIngressosCompradosPorCliente(Integer id) throws RegraDeNegocioException {
        try {
            return ingressoRepository.listarIngressoCompradoPorCliente(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de comunicação com o Banco de Dados!" +
                    "Verifique se a inserção ID está correta.");
        }
    }

    public IngressoDTO createIngresso(IngressoCreateDTO ingressoCreateDTO) throws RegraDeNegocioException {

        try {
            Ingresso ingresso = objectMapper.convertValue(ingressoCreateDTO, Ingresso.class);
            Ingresso ingressoSalvo = ingressoRepository.adicionar(ingresso);
            IngressoDTO ingressoDTO = objectMapper.convertValue(ingressoSalvo, IngressoDTO.class);
            return ingressoDTO;
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de comunicação com o Banco de Dados!" +
                    "Não foi possível realizar a inserção desse novo ingresso.");
        }
    }

    public IngressoCompradoDTO comprarIngresso(Integer idCliente, Integer idIngresso) throws RegraDeNegocioException {

        try {
            Disponibilidade disponibilidade = findById(idIngresso).getDisponibilidade();
            if (disponibilidade.getDisponibilidade().equals("S")) {
                Cliente cliente = clienteService.listarClientePeloId(idCliente);
                ClienteDTO clienteDTO = objectMapper.convertValue(cliente, ClienteDTO.class);
                emailService.sendEmail(clienteDTO, TipoEmails.ING_COMPRADO);
                return ingressoRepository.editar(idCliente, idIngresso);
            } else {
                throw new RegraDeNegocioException("Erro!Este ingresso não está disponível");
            }
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de comunicação com o Banco de Dados");
        }
    }

    public IngressoDTO findById(Integer id) throws RegraDeNegocioException {
        try {
            IngressoDTO ingressoDTO = objectMapper.convertValue(ingressoRepository.listarIngressoPeloId(id), IngressoDTO.class);
            return ingressoDTO;
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de comunicação com o Banco de Dados!" +
                    "O IdIngresso informado não pode ser encontrado!");
        }
    }

    public void removeIngresso(Integer id) throws RegraDeNegocioException {
        try {
            ingressoRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro!Não foi possivel realizar essa remoção de dados!");
        }
    }
}
