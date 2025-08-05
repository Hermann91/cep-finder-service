Consulta de CEP com Logs
Esse projeto é uma API simples feita em Java com Spring Boot que permite consultar informações de um CEP através de uma API externa (mockada com WireMock) e registrar as consultas realizadas no banco de dados.

A ideia aqui foi simular um cenário real de uso de APIs externas e persistência de dados, usando princípios de arquitetura limpa, SOLID e boas práticas de desenvolvimento.

O que essa aplicação faz
Permite buscar um CEP (formato 8 dígitos, sem traço)

Faz a consulta numa API externa mockada com WireMock

Salva no banco de dados a consulta realizada, junto com o horário

Os dados retornados da API

Expõe também um endpoint para consultar os logs das buscas, com filtros opcionais por CEP e por intervalo de datas

Como rodar:
- Pré-requisitos
- Docker e Docker Compose instalados

Subir o projeto:
- comando
docker-compose up --build


A aplicação irá subir 3 containers:

cep_app: a aplicação Spring Boot

cep_postgres: banco PostgreSQL

cep_wiremock: mock da API externa de CEP

Endpoints disponíveis:
Buscar CEP
Exemplo:
GET /api/cep/01001000

Consultar logs
GET /api/logs

Parâmetros opcionais:
cep: filtra por CEP exato
inicio: data de início (formato YYYY-MM-DD)
fim: data de fim (formato YYYY-MM-DD)

Exemplos:
GET /api/logs
GET /api/logs?cep=01001000
GET /api/logs?inicio=2025-08-01&fim=2025-08-05
GET /api/logs?cep=01001000&inicio=2025-08-01&fim=2025-08-05

Tecnologias usadas:
Java 17
Spring Boot 3
PostgreSQL
WireMock
Docker e Docker Compose

     

Camadas Internas da Aplicação

[ Controller ]         --> expõe os endpoints REST ->
     
[ UseCase / Command ]  --> orquestra as regras e uso dos serviços ->
    
[ Service ]            --> lida com regras de negócio, chamada à API externa ->
    
[ Repository ]         --> grava e consulta dados no banco de dados ->






Fluxo de Consulta de CEP:

Usuário chama: /api/cep/{cep} ->
    
CepController ->
        
        
BuscarCepUseCase (usa Command Pattern) ->
        
CepServiceImpl->
        
        
Chama API ViaCEP (mockada com Wiremock/Mockoon) ->
        
      
Recebe resposta e grava log da consulta no banco ->
        
        
Retorna DTO com os dados do CEP ->



Fluxo de Consulta de Logs:


Usuário chama: /api/logs?cep=XXXX&inicio=YYYY-MM-DD&fim=YYYY-MM-DD ->

        
ConsultaLogController ->
        
        
LogServiceImpl ->
        
        
ConsultaLogRepository → consulta o banco e retorna os logs









