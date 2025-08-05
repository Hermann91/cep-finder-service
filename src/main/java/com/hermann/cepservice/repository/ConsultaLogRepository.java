package com.hermann.cepservice.repository;

import com.hermann.cepservice.entity.ConsultaLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaLogRepository extends JpaRepository<ConsultaLog, Long> {
    List<ConsultaLog> findByCep(String cep);
    List<ConsultaLog> findByDataConsultaBetween(LocalDateTime inicio, LocalDateTime fim);
    List<ConsultaLog> findByCepAndDataConsultaBetween(String cep, LocalDateTime inicio, LocalDateTime fim);

}
