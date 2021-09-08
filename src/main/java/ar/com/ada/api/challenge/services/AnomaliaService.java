package ar.com.ada.api.challenge.services;

import java.util.Date;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.challenge.entities.Anomalia;
import ar.com.ada.api.challenge.entities.Boya;
import ar.com.ada.api.challenge.entities.Muestra;
import ar.com.ada.api.challenge.repositories.AnomaliaRepository;

@Service
public class AnomaliaService {
    
    @Autowired
    private AnomaliaRepository repo;

    public void crearAnomaliaSiExiste(Muestra muestraReciente){
        this.crearAnomaliaKAIJU(muestraReciente);
        this.crearAnomaliaIMPACTO(muestraReciente);
    }

    public void crearAnomaliaKAIJU(Muestra muestraReciente){
        
        Boya boya = muestraReciente.getBoya();
        boolean anomaliaCreada = false; 

        do { 
            for(int i = (boya.getMuestras().size() - 2); i >= 0; i--){

                Muestra m = boya.getMuestras().get(i);

                if ( (muestraReciente.getAlturaNivelDelMar() > 200 && m.getAlturaNivelDelMar() > 200) 
                 || (muestraReciente.getAlturaNivelDelMar() < -200 && m.getAlturaNivelDelMar() < -200) ){
                
                    if(diffHorariaMas10Min(muestraReciente.getHorario(), m.getHorario())){
                        Anomalia anomalia = new Anomalia();  
                        anomalia.setHorarioInicio(m.getHorario());
                        anomalia.setHorarioFin(muestraReciente.getHorario());
                        anomalia.setNivelDelMarActual(muestraReciente.getAlturaNivelDelMar();
                        anomalia.setTipoAnomalia(TipoAnomaliEnum.KAIJU);
                        boya.agregarAnomalia(anomalia);//may cause trouble
                        repo.save(anomalia);
                        anomaliaCreada = true;
                    }
                }  
            }
        } while(!anomaliaCreada);

    }

    public void crearAnomaliaIMPACTO(Muestra muestraReciente){
        
        Boya boya = muestraReciente.getBoya();
        Muestra muestraAnterior = boya.getMuestras().get(boya.getMuestras().size()-2);
        Double diffAltura = muestraReciente.getAlturaNivelDelMar() - muestraAnterior.getAlturaNivelDelMar();
      
        if ( diffAltura < -500 || diffAltura > 500){
            Anomalia anomalia = new Anomalia();
            anomalia.setHorarioInicio(muestraAnterior.getHorario());
            anomalia.setHorarioFin(muestraReciente.getHorario());
            anomalia.setNivelDelMarActual(muestraReciente.getAlturaNivelDelMar());
            anomalia.setTipoAnomalia(TipoAnomaliaEnum.IMPACTO);
            boya.agregarAnomalia(anomalia);//may cause trouble
            repo.save(anomalia);
        }
    }

    public boolean diffHorariaMas10Min(Date horario1, Date horario2){
        long diffTiempo = horario1.getTime() - horario2.getTime();
        if(diffTiempo > 600000 || diffTiempo < -600000 ){
            return true;
        }
        return false;
    }
}