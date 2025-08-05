package com.hermann.cepservice.command;

import com.hermann.cepservice.dto.CepResponseDTO;
import com.hermann.cepservice.service.CepService;

public class BuscarCepCommand implements Command<CepResponseDTO> {
    private final CepService cepService;

    public BuscarCepCommand(CepService cepService) {
        this.cepService = cepService;
    }

    @Override
    public CepResponseDTO execute(String cep) {
        return cepService.buscarCep(cep);
    }
}