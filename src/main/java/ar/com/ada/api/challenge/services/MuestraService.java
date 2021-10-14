package ar.com.ada.api.challenge.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.challenge.entities.Anomalia;
import ar.com.ada.api.challenge.entities.Boya;
import ar.com.ada.api.challenge.entities.Muestra;
import ar.com.ada.api.challenge.repositories.MuestraRepository;

@Service
public class MuestraService {
    
    @Autowired
    private MuestraRepository repo;

    @Autowired
    private BoyaService boyaService;

    @Autowired 
    private AnomaliaService anomaliaService;

    public Muestra crearMuestra(Integer boyaId, Date horario, String matricula,
    Double latitud, Double longitud, Double alturaNivelDelMar){
        
        Muestra m = new Muestra();
        
        Boya boya = boyaService.traerBoya(boyaId);    
        boyaService.cambiarColorLuzBoya(boya, alturaNivelDelMar);

        m.setHorario(horario);
        m.setAlturaNivelDelMar(alturaNivelDelMar);
        m.setLatitud(latitud);
        m.setLongitud(longitud);
        m.setMatriculaEmbarcacion(matricula);
        
        boya.agregarMuesta(m);
        
        repo.save(m);
        anomaliaService.crearAnomaliaSiExiste(m);

        return m;
    }

    public List<Muestra> traerMuestras(Integer id){
        Boya boya = boyaService.traerBoya(id);
        return boya.getMuestras();
    }

    public Boya actualizarColorBoyaAzul(Integer muestraId){
        Muestra muestra = repo.findByMuestraId(muestraId);
        muestra.getBoya().setColorLuz("AZUL");
        repo.save(muestra);
        return muestra.getBoya();
    }

    public List<Muestra> traerPorColor(String color){
        List<Boya> boyas = boyaService.traerPorColor(color);
        List<Muestra> muestras = new ArrayList();
        for(Boya boya : boyas){
            muestras.addAll(boya.getMuestras());
        }
        return muestras;
    }

    public Muestra traerMuestraMarMinimo(Integer idBoya){
       
        Boya boya = boyaService.traerBoya(idBoya);
        Muestra muestraMarMinimo = new Muestra();
       
        for(Muestra m : boya.getMuestras()){
            if(muestraMarMinimo.getMuestraId() == null){
                muestraMarMinimo = m;
            } else if(muestraMarMinimo.getAlturaNivelDelMar() > m.getAlturaNivelDelMar()){
                muestraMarMinimo = m;
            }
        }
        return muestraMarMinimo;
    }

    public Anomalia buscarAnomalia(Integer boyaId){
        Boya boya = boyaService.traerBoya(boyaId);
        Integer ultimaAnomalia = boya.getAnomalias().size() - 1; 
        Anomalia anomalia = boya.getAnomalias().get(ultimaAnomalia);
        return anomalia;
    }
}
