package ar.com.ada.api.challenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.challenge.entities.Muestra;

@Repository
public interface MuestraRepository extends JpaRepository <Muestra, Integer>{
    public Muestra findByMuestraId(Integer id);
}
