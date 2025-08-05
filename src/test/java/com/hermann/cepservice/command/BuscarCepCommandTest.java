package com.hermann.cepservice.command;

import com.hermann.cepservice.dto.CepResponseDTO;
import com.hermann.cepservice.service.CepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BuscarCepCommandTest {

    private CepService cepService;
    private BuscarCepCommand buscarCepCommand;

    @BeforeEach
    void setUp() {
        cepService = mock(CepService.class);
        buscarCepCommand = new BuscarCepCommand(cepService);
    }

    @Test
    void deveBuscarCepComSucesso() {
        // Arrange
        String cep = "01001000";
        CepResponseDTO esperado = new CepResponseDTO();
        esperado.setCep("01001-000");
        esperado.setLogradouro("Praça da Sé");
        esperado.setBairro("Sé");
        esperado.setLocalidade("São Paulo");
        esperado.setUf("SP");

        when(cepService.buscarCep(cep)).thenReturn(esperado);

        // Act
        CepResponseDTO resultado = buscarCepCommand.execute(cep);

        // Assert
        assertNotNull(resultado);
        assertEquals("01001-000", resultado.getCep());
        assertEquals("Praça da Sé", resultado.getLogradouro());
        assertEquals("Sé", resultado.getBairro());
        assertEquals("São Paulo", resultado.getLocalidade());
        assertEquals("SP", resultado.getUf());

        verify(cepService, times(1)).buscarCep(cep);
    }
}
