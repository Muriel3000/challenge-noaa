package ar.com.ada.api.challenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.challenge.entities.Anomalia;

@Repository
public interface AnomaliaRepository extends JpaRepository <Anomalia, Integer> {
    public Anomalia findByAnomaliaId(Integer id);
}
