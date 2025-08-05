package com.hermann.cepservice.controller;

import com.hermann.cepservice.dto.CepResponseDTO;
import com.hermann.cepservice.usecase.BuscarCepUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Buscar informações de um CEP",
            description = "Consulta uma API externa mockada com WireMock e retorna os dados do endereço correspondente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "CEP inválido ou não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{cep}")
    public ResponseEntity<CepResponseDTO> buscarCep(
            @Parameter(description = "CEP no formato 8 dígitos (sem traço)", example = "01001000")
            @PathVariable
            @Pattern(regexp = "\\d{8}", message = "CEP deve conter exatamente 8 dígitos numéricos (sem traço)")
            String cep
    ) {
        return ResponseEntity.ok(buscarCepUseCase.execute(cep));
    }
}
