package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.clientedto.ClienteDTO;
import br.com.dbc.vemser.cinedev.dto.ingressodto.IngressoCompradoDTO;
import br.com.dbc.vemser.cinedev.dto.ingressodto.IngressoCreateDTO;
import br.com.dbc.vemser.cinedev.dto.ingressodto.IngressoDTO;
import br.com.dbc.vemser.cinedev.entity.ClienteEntity;
import br.com.dbc.vemser.cinedev.entity.IngressoEntity;
import br.com.dbc.vemser.cinedev.entity.enums.Disponibilidade;
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
        List<IngressoEntity> ingressoslist = ingressoRepository.findAll();
        return ingressoslist.stream()
                .map(ingressoEntity -> objectMapper.convertValue(ingressoEntity, IngressoDTO.class))
                .toList();
    }
    public List<IngressoDTO> listarIngressosComprados() throws RegraDeNegocioException {
        List<IngressoEntity> ingressoslist = ingressoRepository.findIngressoComprados();
        return ingressoslist.stream()
                .map(ingressoEntity -> objectMapper.convertValue(ingressoEntity, IngressoDTO.class))
                .toList();
    }

    public List<IngressoCompradoDTO> listarIngressosCompradosPorCliente(Integer id) throws RegraDeNegocioException {
        return ingressoRepository.findIngressoCompradosPorCliente(id)
                .stream()
                .map(ingressoEntity -> objectMapper.convertValue(ingressoEntity, IngressoCompradoDTO.class))
                .toList();
    }

    public IngressoDTO createIngresso(IngressoCreateDTO ingressoCreateDTO) throws RegraDeNegocioException {

        IngressoEntity ingressoEntity = objectMapper.convertValue(ingressoCreateDTO, IngressoEntity.class);
        IngressoEntity ingressoEntitySalvo = ingressoRepository.save(ingressoEntity);
        IngressoDTO ingressoDTO = objectMapper.convertValue(ingressoEntitySalvo, IngressoDTO.class);
        return ingressoDTO;
    }

    public IngressoCompradoDTO comprarIngresso(Integer idCliente, Integer idIngresso) throws RegraDeNegocioException {
        IngressoEntity ingressoRecuperado = objectMapper.convertValue(findById(idIngresso), IngressoEntity.class);
        ClienteEntity clienteRecuperado = clienteService.listarClientePeloId(idCliente);

        ingressoRecuperado.setIdCliente(clienteRecuperado.getIdCliente());
        ingressoRecuperado.setDisponibilidade(Disponibilidade.N);
        ingressoRecuperado.setPreco(30.00);
        ingressoRecuperado = ingressoRepository.save(ingressoRecuperado);

        IngressoCompradoDTO ingressoCompradoDTO = objectMapper.convertValue(ingressoRecuperado, IngressoCompradoDTO.class);
        ClienteDTO clienteDTO = objectMapper.convertValue(clienteRecuperado, ClienteDTO.class);

        emailService.sendEmail(clienteDTO,TipoEmails.ING_COMPRADO);
        return ingressoCompradoDTO;
    }

    public IngressoDTO findById(Integer id) throws RegraDeNegocioException {
        IngressoDTO ingressoDTO = objectMapper.convertValue(ingressoRepository.findById(id), IngressoDTO.class);
        return ingressoDTO;
    }

    public void removeIngresso(Integer id) throws RegraDeNegocioException {
        ingressoRepository.deleteById(id);
    }
}
