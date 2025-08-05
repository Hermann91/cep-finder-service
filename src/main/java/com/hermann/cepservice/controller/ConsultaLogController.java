package com.hermann.cepservice.controller;

import com.hermann.cepservice.entity.ConsultaLog;
import com.hermann.cepservice.service.LogService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class ConsultaLogController {

    private final LogService logService;

    public ConsultaLogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public ResponseEntity<List<ConsultaLog>> buscarLogs(
            @RequestParam(required = false) String cep,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        return ResponseEntity.ok(logService.buscarLogs(cep, inicio, fim));
    }
}
