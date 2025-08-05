package com.hermann.cepservice.controller;

import com.hermann.cepservice.entity.ConsultaLog;
import com.hermann.cepservice.repository.ConsultaLogRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class ConsultaLogController {

    private final ConsultaLogRepository logRepository;

    public ConsultaLogController(ConsultaLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @GetMapping
    public ResponseEntity<List<ConsultaLog>> listarLogs(
            @RequestParam(required = false) String cep,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim
    ) {
        List<ConsultaLog> logs;

        if (cep != null && inicio != null && fim != null) {
            logs = logRepository.findByCepAndDataConsultaBetween(cep, inicio, fim);
        } else if (cep != null) {
            logs = logRepository.findByCep(cep);
        } else if (inicio != null && fim != null) {
            logs = logRepository.findByDataConsultaBetween(inicio, fim);
        } else {
            logs = logRepository.findAll();
        }

        return ResponseEntity.ok(logs);
    }
}
