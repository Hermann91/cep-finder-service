package com.hermann.cepservice.service.impl;

import com.hermann.cepservice.entity.ConsultaLog;
import com.hermann.cepservice.exceptions.InvalidCepFormatException;
import com.hermann.cepservice.exceptions.InvalidDateRangeException;
import com.hermann.cepservice.repository.ConsultaLogRepository;
import com.hermann.cepservice.service.LogService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    private final ConsultaLogRepository logRepository;

    public LogServiceImpl(ConsultaLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public List<ConsultaLog> buscarLogs(String cep, LocalDate inicio, LocalDate fim) {
        if (cep != null && !cep.matches("\\d{8}")) {
            throw new InvalidCepFormatException(cep);
        }

        if (inicio != null && fim != null && inicio.isAfter(fim)) {
            throw new InvalidDateRangeException();
        }

        LocalDateTime inicioDateTime = inicio != null ? inicio.atStartOfDay() : null;
        LocalDateTime fimDateTime = fim != null ? fim.atTime(LocalTime.MAX) : null;

        if (cep != null && !cep.isBlank() && inicioDateTime != null && fimDateTime != null) {
            return logRepository.findByCepAndDataConsultaBetween(cep, inicioDateTime, fimDateTime);
        } else if (inicioDateTime != null && fimDateTime != null) {
            return logRepository.findByDataConsultaBetween(inicioDateTime, fimDateTime);
        } else if (cep != null && !cep.isBlank()) {
            return logRepository.findByCep(cep);
        } else {
            return logRepository.findAll();
        }
    }


}
