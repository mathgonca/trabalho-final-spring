//package br.com.dbc.vemser.cinedev.service;
//
//import br.com.dbc.vemser.cinedev.dto.log.LogCreateDTO;
//import br.com.dbc.vemser.cinedev.dto.log.LogDTO;
//import br.com.dbc.vemser.cinedev.dto.log.LogDTOContador;
//import br.com.dbc.vemser.cinedev.entity.LogEntity;
//import br.com.dbc.vemser.cinedev.entity.enums.TipoLog;
//import br.com.dbc.vemser.cinedev.exception.RegraDeNegocioException;
//import br.com.dbc.vemser.cinedev.repository.LogRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class LogService {
//
//    private final LogRepository logRepository;
//    private final ObjectMapper objectMapper;
//
////    public void salvarLog(LogDTO logDTO) {
////        var log = new LogEntity();
////        BeanUtils.copyProperties(logDTO, log);
////        log.setData(sdfComplete.format(new Date()));
////        logRepository.save(log);
////    }
//
//    public List<LogDTO> listAllLogs() {
//        return logRepository.findAll().stream().map(log -> objectMapper.convertValue(log, LogDTO.class)).collect(Collectors.toList());
//    }
//
////    public List<LogDTO> listLogsByTipoLog(TipoLog tipoLog) {
////        return logRepository.findAllByTipoLog(tipoLog).stream().map(log -> objectMapper.convertValue(log, LogDTO.class)).collect(Collectors.toList());
////    }
//
//    public List<LogDTO> listAllByCidade(LogCreateDTO logCreateDTO){
//        LogEntity log = (LogEntity) logRepository.findLogEntityByCidade(logCreateDTO.getCidade());
//        return this.logRepository.findLogEntityByCidade(logCreateDTO.getCidade()).stream().map(l->
//                return new
//    }
//
//    public List<LogDTOContador> groupByTipoLogAndCount() {
//        return this.logRepository.groupByTipoLogAndCount().stream().map(l->{
//            return new LogDTOContador(l.getTipoLog(), l.getQuantidade());
//        }).collect(Collectors.toList());
//    }
//
//    public List<LogDTO> listAllByCidade(LogCreateDTO logCreateDTO) throws RegraDeNegocioException{
//        LogEntity log = new LogEntity();
//        if(logRepository.findLogEntityByCidade(logCreateDTO.getCidade()) != null){
//            return this.logRepository.findLogEntityByCidade(logCreateDTO.getCidade()).stream().forEach();
//        }
//    }
//
//    public List<LogDTO> listAllByData(String date) throws Exception {
//        String dataCorrected = date.replace("/","-");
//        Date dataAtual = new Date();
//        Date dateReceived = sdfDayMonthYear.parse(dataCorrected);
//        if(dateReceived.after(dataAtual)){
//            throw new Exception("Esse dia não chegou!");
//        }
//        return logRepository.findAllByDataContains(date).stream().map(logEntity -> objectMapper.convertValue(logEntity, LogDTO.class)).collect(Collectors.toList());
//    }
//
//    public Long countLogsByCidade() throws RegraDeNegocioException{
//        LogEntity log = new LogEntity();
//        log.setCidade();
//    }
//
//    public Long countLogsByData() throws Exception {
//        LogEntity log = new LogEntity();
//        log.setData(sdfDayMonthYear.format(new Date()));
//        return logRepository.countAllByDataContains(log.getData());
//    }
//
//
//    public LogDTOContador countLogsByTipo(TipoLog tipoLog){
//        LogDTOContador log = new LogDTOContador();
//        log.setQuantidade(logRepository.countByTipoLog(tipoLog));
//        log.setTipoLog(tipoLog);
//        return log;
//    }
//}
