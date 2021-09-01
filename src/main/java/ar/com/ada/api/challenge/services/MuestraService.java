package ar.com.ada.api.challenge.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.challenge.entities.Boya;
import ar.com.ada.api.challenge.entities.Muestra;
import ar.com.ada.api.challenge.repositories.MuestraRepository;

@Service
public class MuestraService {
    
    @Autowired
    private MuestraRepository repo;

    @Autowired
    private BoyaService boyaService;

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
            } else if(muestraMarMinimo.getAlturaNivelDelMar() < m.getAlturaNivelDelMar()){
                // tiene que quedar la altura mas grande? entre -5, 10 y 20, 20 seria la alturaMinima?
                muestraMarMinimo = m;
            }
        }
        return muestraMarMinimo;
    }

    public AnomaliaEnum identificarAnomalia(Integer idBoya){
        
        Boya boya = boyaService.traerBoya(idBoya);
        
        Muestra muestraReciente = boya.getMuestras().get(boya.getMuestras().size()-1);
        Muestra muestraAnterior = boya.getMuestras().get(boya.getMuestras().size()-2);

        if ( (muestraReciente.getAlturaNivelDelMar() > 200 && muestraAnterior.getAlturaNivelDelMar() > 200) 
           || (muestraReciente.getAlturaNivelDelMar() < -200 && muestraAnterior.getAlturaNivelDelMar() < -200) ){
               
            if(diffHorariaMas10Min(muestraReciente.getHorario(), muestraAnterior.getHorario())){
                return AnomaliaEnum.KAIJU;
            }
        }  

        Double diffAltura = muestraReciente.getAlturaNivelDelMar() - muestraAnterior.getAlturaNivelDelMar();
        if ( diffAltura < -500 || diffAltura > 500){
            return AnomaliaEnum.IMPACTO;
        }

        return AnomaliaEnum.NORMAL;
    } 

    public Muestra traerMuestraReciente(Integer idBoya){
        Boya boya = boyaService.traerBoya(idBoya);
        Muestra muestraReciente = boya.getMuestras().get(boya.getMuestras().size()-1);
        return muestraReciente;
    }

    public Muestra traerMuestraAnterior(Integer idBoya){
        Boya boya = boyaService.traerBoya(idBoya);
        Muestra muestraAnterior = boya.getMuestras().get(boya.getMuestras().size()-2);
        return muestraAnterior;
    }

    public boolean diffHorariaMas10Min(Date horario1, Date horario2){
        long diffTiempo = horario1.getTime() - horario2.getTime();
        if(diffTiempo > 600000 || diffTiempo < -600000 ){
            return true;
        }
        return false;
    }

    public enum AnomaliaEnum {
        KAIJU, IMPACTO, NORMAL
    }
}
