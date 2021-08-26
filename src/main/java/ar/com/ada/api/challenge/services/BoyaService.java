package ar.com.ada.api.challenge.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.challenge.entities.Boya;
import ar.com.ada.api.challenge.repositories.BoyaRepository;

@Service
public class BoyaService {
    
    @Autowired 
    private BoyaRepository repo;
    
    public Integer crearBoya(Double longitud, Double latitud){
        Boya boya = new Boya();
        boya.setLongitudInstalacion(longitud);
        boya.setLatitudInstalacion(latitud);
        repo.save(boya);
        return boya.getBoyaId();
    }

    public List<Boya> traerBoyas(){
        return repo.findAll();
    }

    public Boya traerBoya(Integer id){
        return repo.findByBoyaId(id);
    }

    public String actualizarColorBoya(Integer id, String color){
        Boya boya = repo.findByBoyaId(id);
        boya.setColorLuz(color.toUpperCase());
        repo.save(boya);
        return boya.getColorLuz();
    }

    public void cambiarColorLuzBoya(Boya boya,Double alturaNivelMar){
        if (alturaNivelMar < -100 || alturaNivelMar > 100){
            boya.setColorLuz("ROJO");
        } else if(alturaNivelMar < -50 || alturaNivelMar > 50){
            boya.setColorLuz("AMARILLO");
        } else {
            boya.setColorLuz("VERDE");
        }
    }

}
