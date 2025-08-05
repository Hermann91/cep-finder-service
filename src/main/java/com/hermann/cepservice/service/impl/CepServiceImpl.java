package com.hermann.cepservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hermann.cepservice.dto.CepResponseDTO;
import com.hermann.cepservice.entity.ConsultaLog;
import com.hermann.cepservice.exceptions.CepNotFoundException;
import com.hermann.cepservice.repository.ConsultaLogRepository;
import com.hermann.cepservice.service.CepService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class CepServiceImpl implements CepService {

    private final RestTemplate restTemplate;
    private final ConsultaLogRepository logRepository;
    private final ObjectMapper objectMapper;

    @Value("${cep.api.url}")
    public String cepApiUrl;

    public CepServiceImpl(RestTemplate restTemplate,
                          ConsultaLogRepository logRepository,
                          ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.logRepository = logRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public CepResponseDTO buscarCep(String cep) {
        String url = cepApiUrl + "/" + cep + "/json";
        CepResponseDTO response;

        try {
            response = restTemplate.getForObject(url, CepResponseDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new CepNotFoundException("CEP não encontrado na API externa.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar API externa de CEP.", e);
        }

        if (response == null) {
            throw new CepNotFoundException("Resposta vazia da API externa.");
        }

        ConsultaLog log = new ConsultaLog();
        log.setCep(cep);
        log.setDataConsulta(LocalDateTime.now());
        log.setLogradouro(response.getLogradouro());
        log.setBairro(response.getBairro());
        log.setLocalidade(response.getLocalidade());
        log.setUf(response.getUf());

        try {
            log.setResultadoJson(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            log.setResultadoJson("Erro ao serializar resposta");
        }

        try {
            logRepository.save(log);
        } catch (Exception ignored) {
        }

        return response;
    }
}
