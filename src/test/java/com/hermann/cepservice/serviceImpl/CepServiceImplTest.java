package com.hermann.cepservice.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hermann.cepservice.dto.CepResponseDTO;
import com.hermann.cepservice.entity.ConsultaLog;
import com.hermann.cepservice.exceptions.CepNotFoundException;
import com.hermann.cepservice.repository.ConsultaLogRepository;
import com.hermann.cepservice.service.impl.CepServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CepServiceImplTest {

    private RestTemplate restTemplate;
    private ConsultaLogRepository logRepository;
    private ObjectMapper objectMapper;
    private CepServiceImpl cepService;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        logRepository = mock(ConsultaLogRepository.class);
        objectMapper = new ObjectMapper();
        cepService = new CepServiceImpl(restTemplate, logRepository, objectMapper);
        cepService.cepApiUrl = "https://viacep.com.br/ws";
    }

    @Test
    void deveRetornarCepComSucesso() {
        String cep = "01001000";
        String url = "https://viacep.com.br/ws/" + cep + "/json";

        CepResponseDTO dto = new CepResponseDTO(
                "01001-000", "Praça da Sé", "", "Sé", "São Paulo", "SP", "3550308", "1004", "11", "7107"
        );

        when(restTemplate.getForObject(url, CepResponseDTO.class)).thenReturn(dto);

        CepResponseDTO resultado = cepService.buscarCep(cep);

        assertNotNull(resultado);
        assertEquals("01001-000", resultado.getCep());
        verify(logRepository, times(1)).save(any(ConsultaLog.class));
    }

    @Test
    void deveLancarExcecaoQuandoCepNaoExiste() {
        String cep = "99999999";
        String url = "https://viacep.com.br/ws/" + cep + "/json";

        when(restTemplate.getForObject(url, CepResponseDTO.class))
                .thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(CepNotFoundException.class, () -> cepService.buscarCep(cep));
        verify(logRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoRespostaNula() {
        String cep = "00000000";
        String url = "https://viacep.com.br/ws/" + cep + "/json";

        when(restTemplate.getForObject(url, CepResponseDTO.class)).thenReturn(null);

        assertThrows(CepNotFoundException.class, () -> cepService.buscarCep(cep));
        verify(logRepository, never()).save(any());
    }

    @Test
    void deveIgnorarErroAoSalvarLog() {
        String cep = "01001000";
        String url = "https://viacep.com.br/ws/" + cep + "/json";

        CepResponseDTO dto = new CepResponseDTO(
                "01001-000", "Praça da Sé", "", "Sé", "São Paulo", "SP", "3550308", "1004", "11", "7107"
        );

        when(restTemplate.getForObject(url, CepResponseDTO.class)).thenReturn(dto);
        doThrow(RuntimeException.class).when(logRepository).save(any());

        CepResponseDTO resultado = cepService.buscarCep(cep);

        assertNotNull(resultado);
        assertEquals("01001-000", resultado.getCep());
    }
}
