package com.hermann.cepservice.service;

import com.hermann.cepservice.dto.CepResponseDTO;

public interface CepService {
    CepResponseDTO buscarCep(String cep);
}
