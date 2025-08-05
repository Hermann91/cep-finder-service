package com.hermann.cepservice.serviceImpl;

import com.hermann.cepservice.entity.ConsultaLog;
import com.hermann.cepservice.repository.ConsultaLogRepository;
import com.hermann.cepservice.service.impl.LogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LogServiceImplTest {

    private ConsultaLogRepository logRepository;
    private LogServiceImpl logService;

    @BeforeEach
    void setUp() {
        logRepository = mock(ConsultaLogRepository.class);
        logService = new LogServiceImpl(logRepository);
    }

    @Test
    void deveBuscarPorCepEPeriodo() {
        String cep = "01001000";
        LocalDate inicio = LocalDate.of(2024, 1, 1);
        LocalDate fim = LocalDate.of(2024, 1, 31);

        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atTime(LocalTime.MAX);

        when(logRepository.findByCepAndDataConsultaBetween(cep, inicioDateTime, fimDateTime))
                .thenReturn(List.of(new ConsultaLog()));

        List<ConsultaLog> resultado = logService.buscarLogs(cep, inicio, fim);

        assertEquals(1, resultado.size());
        verify(logRepository).findByCepAndDataConsultaBetween(cep, inicioDateTime, fimDateTime);
    }

    @Test
    void deveBuscarPorPeriodoApenas() {
        LocalDate inicio = LocalDate.of(2024, 2, 1);
        LocalDate fim = LocalDate.of(2024, 2, 28);

        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atTime(LocalTime.MAX);

        when(logRepository.findByDataConsultaBetween(inicioDateTime, fimDateTime))
                .thenReturn(List.of(new ConsultaLog(), new ConsultaLog()));

        List<ConsultaLog> resultado = logService.buscarLogs(null, inicio, fim);

        assertEquals(2, resultado.size());
        verify(logRepository).findByDataConsultaBetween(inicioDateTime, fimDateTime);
    }

    @Test
    void deveBuscarPorCepApenas() {
        String cep = "12345678";

        when(logRepository.findByCep(cep)).thenReturn(List.of(new ConsultaLog()));

        List<ConsultaLog> resultado = logService.buscarLogs(cep, null, null);

        assertEquals(1, resultado.size());
        verify(logRepository).findByCep(cep);
    }

    @Test
    void deveBuscarTodosQuandoNenhumParametroInformado() {
        when(logRepository.findAll()).thenReturn(List.of(new ConsultaLog(), new ConsultaLog(), new ConsultaLog()));

        List<ConsultaLog> resultado = logService.buscarLogs(null, null, null);

        assertEquals(3, resultado.size());
        verify(logRepository).findAll();
    }
}
