package br.com.dbc.vemser.cinedev.service;

import br.com.dbc.vemser.cinedev.dto.ingressodto.IngressoCompradoDTO;
import br.com.dbc.vemser.cinedev.dto.notasfiscais.NotasFiscaisCinemaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutorService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    //${spring.kafka.consumer.client-id}
    @Value(value = "cinedev")
    private String usuario;

    @Value(value = "inahzhoc-notafiscal")
    private String topico;

    @Value(value = "0")
    private Integer particao;


    public void enviarMensagem(IngressoCompradoDTO ingresso) throws JsonProcessingException {
            NotasFiscaisCinemaDTO notasFiscaisCinemaDTO = new NotasFiscaisCinemaDTO();
            notasFiscaisCinemaDTO.setIdFilme(ingresso.getIdFilme());
            notasFiscaisCinemaDTO.setIdIngresso(ingresso.getIdIngresso());
            notasFiscaisCinemaDTO.setIdCinema(ingresso.getIdCinema());
            notasFiscaisCinemaDTO.setIdCliente(ingresso.getIdCliente());
            notasFiscaisCinemaDTO.setNomeFilme(ingresso.getNomeFilme());
            notasFiscaisCinemaDTO.setNomeCinema(ingresso.getNomeCinema());
            notasFiscaisCinemaDTO.setDataHora(ingresso.getDataHora());
            notasFiscaisCinemaDTO.setPreco(ingresso.getPreco());
            String msg = objectMapper.writeValueAsString(notasFiscaisCinemaDTO);
            // mensagem, chave, topico
            MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(msg)
                    .setHeader(KafkaHeaders.TOPIC, topico)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                    .setHeader(KafkaHeaders.PARTITION_ID, (Integer) particao)
                    ;

            ListenableFuture<SendResult<String, String>> enviadoParaTopico = kafkaTemplate.send(stringMessageBuilder.build());
            enviadoParaTopico.addCallback(new ListenableFutureCallback<>() {

                @Override
                public void onSuccess(SendResult result) {
                    log.info(" Log enviado para o kafka com o texto: {} ", notasFiscaisCinemaDTO);
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.error(" Erro ao publicar duvida no kafka com a mensagem: {}", notasFiscaisCinemaDTO, ex);
                }
            });
    }
}
