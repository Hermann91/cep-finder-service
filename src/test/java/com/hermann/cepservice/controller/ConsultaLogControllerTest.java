package com.hermann.cepservice.controller;

import com.hermann.cepservice.entity.ConsultaLog;
import com.hermann.cepservice.service.LogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultaLogController.class)
class ConsultaLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogService logService;

    @Test
    void deveRetornarLogsFiltrandoPorCep() throws Exception {
        String cep = "01001000";

        ConsultaLog log = new ConsultaLog();
        log.setId(1L);
        log.setCep(cep);
        log.setLogradouro("Praça da Sé");
        log.setBairro("Sé");
        log.setLocalidade("São Paulo");
        log.setUf("SP");
        log.setDataConsulta(LocalDateTime.now());
        log.setResultadoJson("{\"cep\":\"01001-000\"}");

        List<ConsultaLog> logs = List.of(log);

        when(logService.buscarLogs(cep, null, null)).thenReturn(logs);

        mockMvc.perform(get("/api/logs").param("cep", cep))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cep").value("01001000"))
                .andExpect(jsonPath("$[0].bairro").value("Sé"))
                .andExpect(jsonPath("$[0].localidade").value("São Paulo"))
                .andExpect(jsonPath("$[0].uf").value("SP"));
    }

    @Test
    void deveRetornarLogsFiltrandoPorPeriodo() throws Exception {
        LocalDate inicio = LocalDate.of(2025, 8, 1);
        LocalDate fim = LocalDate.of(2025, 8, 5);

        ConsultaLog log = new ConsultaLog();
        log.setId(1L);
        log.setCep("01001000");
        log.setLogradouro("Praça da Sé");
        log.setBairro("Sé");
        log.setLocalidade("São Paulo");
        log.setUf("SP");
        log.setDataConsulta(LocalDateTime.of(2025, 8, 2, 14, 0));
        log.setResultadoJson("{\"cep\":\"01001-000\"}");

        when(logService.buscarLogs(null, inicio, fim)).thenReturn(List.of(log));

        mockMvc.perform(get("/api/logs")
                        .param("inicio", "2025-08-01")
                        .param("fim", "2025-08-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cep").value("01001000"));
    }

    @Test
    void deveRetornarListaVaziaSeNaoEncontrarLogs() throws Exception {
        when(logService.buscarLogs("99999999", null, null)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/logs").param("cep", "99999999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
