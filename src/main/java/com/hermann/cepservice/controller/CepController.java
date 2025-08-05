package com.hermann.cepservice.controller;

import com.hermann.cepservice.dto.CepResponseDTO;
import com.hermann.cepservice.usecase.BuscarCepUseCase;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cep")
@Validated
public class CepController {

    private final BuscarCepUseCase buscarCepUseCase;

    public CepController(BuscarCepUseCase buscarCepUseCase) {
        this.buscarCepUseCase = buscarCepUseCase;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<CepResponseDTO> buscarCep(
            @PathVariable
            @Pattern(regexp = "\\d{8}", message = "CEP deve conter exatamente 8 dígitos numéricos (sem traço)")
            String cep
    ) {
        return ResponseEntity.ok(buscarCepUseCase.execute(cep));
    }
}
