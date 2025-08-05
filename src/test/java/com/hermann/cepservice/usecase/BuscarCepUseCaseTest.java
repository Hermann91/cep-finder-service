package com.hermann.cepservice.usecase;

import com.hermann.cepservice.dto.CepResponseDTO;
import com.hermann.cepservice.service.CepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BuscarCepUseCaseTest {

    private CepService cepService;
    private BuscarCepUseCase buscarCepUseCase;

    @BeforeEach
    void setUp() {
        cepService = mock(CepService.class);
        buscarCepUseCase = new BuscarCepUseCase(cepService);
    }

    @Test
    void deveExecutarBuscaDeCepComSucesso() {
        String cep = "01001000";
        CepResponseDTO dto = new CepResponseDTO(
                "01001-000", "Praça da Sé", null,
                "Sé", "São Paulo", "SP", "3550308",
                "1004", "11", "7107"
        );

        when(cepService.buscarCep(cep)).thenReturn(dto);

        CepResponseDTO resultado = buscarCepUseCase.execute(cep);

        assertNotNull(resultado);
        assertEquals("01001-000", resultado.getCep());
        assertEquals("Praça da Sé", resultado.getLogradouro());
        verify(cepService).buscarCep(cep);
    }
}
