# Consulta de CEP com Logs

Este projeto é uma API simples desenvolvida em **Java 17 com Spring Boot 3** que permite consultar informações de um CEP através de uma API externa (mockada com **WireMock**) e registrar as consultas realizadas no banco de dados **PostgreSQL**.

A ideia é simular um cenário real de uso com:

* Integração com serviços externos (API de CEP)
* Registro e rastreabilidade de consultas
* Boas práticas como SOLID, arquitetura limpa e padrões de projeto (Command)
* Camadas bem definidas (Controller, UseCase, Service, Repository)

---

## ✅ O que essa aplicação faz

* Permite buscar um CEP (formato 8 dígitos, sem traço)
* Consulta uma API externa (mockada com WireMock)
* Salva no banco de dados:

  * O horário da consulta
  * Os dados retornados da API
* Expõe também um endpoint para consultar os logs das buscas

  * Com filtros opcionais por **CEP** e por **intervalo de datas**

---

## 🚀 Como rodar o projeto

### Pré-requisitos

* Docker e Docker Compose instalados

### Subir a aplicação:

```bash
docker-compose up --build
```

Isso irá subir **3 containers**:

* `cep_app`: aplicação Spring Boot
* `postgres`: banco de dados PostgreSQL
* `wiremock`: mock da API de CEP

---

## 🔗 Endpoints disponíveis

### 📍 Buscar CEP

`GET /api/cep/{cep}`

Exemplo:

```
GET /api/cep/01001000
```

### 📄 Consultar Logs

`GET /api/logs`

#### Parâmetros opcionais:

* `cep` → filtra por CEP exato
* `inicio` → data inicial (formato `YYYY-MM-DD`)
* `fim` → data final (formato `YYYY-MM-DD`)

#### Exemplos:

```
GET /api/logs
GET /api/logs?cep=01001000
GET /api/logs?inicio=2025-08-01&fim=2025-08-05
GET /api/logs?cep=01001000&inicio=2025-08-01&fim=2025-08-05
```

---

## 🛠 Tecnologias Utilizadas

* Java 17
* Spring Boot 3
* PostgreSQL
* Docker e Docker Compose
* WireMock
* JPA/Hibernate

---

## 🧱 Estrutura de Camadas da Aplicação

```mermaid
flowchart TD
    A[Usuário (cliente)] -->|HTTP GET /api/cep/{cep}| B[CepController]
    B --> C[BuscarCepUseCase]
    C --> D[BuscarCepCommand]
    D --> E[CepServiceImpl]
    E -->|Chamada HTTP| F[API Externa - WireMock]
    E -->|Grava log| G[ConsultaLogRepository]
    G -->|Persistência| H[(PostgreSQL)]

    subgraph "Consulta de Logs"
        I[Usuário (cliente)] -->|GET /api/logs| J[ConsultaLogController]
        J --> K[LogServiceImpl]
        K --> L[ConsultaLogRepository]
        L --> H
    end
```

---

## 📌 Organização em Camadas

* `Controller`: expõe os endpoints REST
* `UseCase`: orquestra os fluxos (command pattern)
* `Service`: implementa regras de negócio e integra com API externa
* `Repository`: acesso ao banco de dados com JPA

---

## 📂 Estrutura esperada da resposta da API externa (exemplo WireMock)

`GET http://wiremock:8080/ws/01001000/json`

```json
{
  "cep": "01001-000",
  "logradouro": "Praça da Sé",
  "bairro": "Sé",
  "localidade": "São Paulo",
  "uf": "SP"
}
```

---

## 🧪 Testes

O projeto possui testes unitários para:

* Controllers (MockMvc)
* Serviços (Mockito)
* UseCases e comandos

Execute com:

```bash
./mvnw test
```

---

## 📬 Contato

Douglas Hermann – [LinkedIn](https://www.linkedin.com/in/douglas-hermann-de-araujo/)
