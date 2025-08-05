package com.hermann.cepservice.controller;

import com.hermann.cepservice.entity.ConsultaLog;
import com.hermann.cepservice.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Consultar logs de buscas de CEPs",
            description = "Permite consultar o histórico de buscas realizadas, com filtros opcionais por CEP e intervalo de datas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<ConsultaLog>> buscarLogs(
            @Parameter(description = "Filtrar pelo CEP buscado", example = "01001000")
            @RequestParam(required = false) String cep,

            @Parameter(description = "Data inicial do filtro (formato YYYY-MM-DD)", example = "2025-08-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

            @Parameter(description = "Data final do filtro (formato YYYY-MM-DD)", example = "2025-08-05")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        return ResponseEntity.ok(logService.buscarLogs(cep, inicio, fim));
    }
}
