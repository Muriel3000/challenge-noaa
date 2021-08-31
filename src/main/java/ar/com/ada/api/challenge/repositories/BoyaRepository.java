package ar.com.ada.api.challenge.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.challenge.entities.Boya;

@Repository
public interface BoyaRepository extends JpaRepository <Boya, Integer>{
    public Boya findByBoyaId(Integer id);
    public List<Boya> findByColorLuz(String colorLuz);
}
