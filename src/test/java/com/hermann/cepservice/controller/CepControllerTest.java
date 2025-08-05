package com.hermann.cepservice.controller;

import com.hermann.cepservice.dto.CepResponseDTO;
import com.hermann.cepservice.usecase.BuscarCepUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CepController.class)
class CepControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuscarCepUseCase buscarCepUseCase;

    @Test
    void deveRetornarCepComSucesso() throws Exception {
        String cep = "01001000";
        CepResponseDTO dto = new CepResponseDTO(
                "01001-000", "Praça da Sé", null,
                "Sé", "São Paulo", "SP", "3550308",
                "1004", "11", "7107"
        );

        when(buscarCepUseCase.execute(cep)).thenReturn(dto);

        mockMvc.perform(get("/api/cep/{cep}", cep))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep").value("01001-000"))
                .andExpect(jsonPath("$.logradouro").value("Praça da Sé"))
                .andExpect(jsonPath("$.bairro").value("Sé"))
                .andExpect(jsonPath("$.localidade").value("São Paulo"))
                .andExpect(jsonPath("$.uf").value("SP"));
    }

    @Test
    void deveRetornarErroParaCepInvalido() throws Exception {
        mockMvc.perform(get("/api/cep/{cep}", "123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }
}
