package com.hermann.cepservice.usecase;

import com.hermann.cepservice.command.BuscarCepCommand;
import com.hermann.cepservice.dto.CepResponseDTO;
import com.hermann.cepservice.service.CepService;
import org.springframework.stereotype.Component;

@Component
public class BuscarCepUseCase {
    private final BuscarCepCommand buscarCepCommand;

    public BuscarCepUseCase(CepService cepService) {
        this.buscarCepCommand = new BuscarCepCommand(cepService);
    }

    public CepResponseDTO execute(String cep) {
        return buscarCepCommand.execute(cep);
    }
}