package com.hermann.cepservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ConsultaLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private LocalDateTime dataConsulta;
    private String resultadoJson;
}
