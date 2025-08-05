package com.hermann.cepservice.service;

import com.hermann.cepservice.entity.ConsultaLog;

import java.time.LocalDate;
import java.util.List;

public interface LogService {
    List<ConsultaLog> buscarLogs(String cep, LocalDate inicio, LocalDate fim);
}
