package ar.com.ada.api.challenge.services;

import java.util.Date;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.challenge.entities.Anomalia;
import ar.com.ada.api.challenge.entities.Boya;
import ar.com.ada.api.challenge.entities.Muestra;
import ar.com.ada.api.challenge.entities.TipoAlerta.TipoAlertaEnum;
import ar.com.ada.api.challenge.repositories.AnomaliaRepository;

@Service
public class AnomaliaService {
    
    @Autowired
    private AnomaliaRepository repo;

    public void crearAnomaliaSiExiste(Muestra muestraReciente){
        if(muestraReciente.getAlturaNivelDelMar() > 200 || muestraReciente.getAlturaNivelDelMar() < -200){
            this.crearAnomaliaKAIJU(muestraReciente);
        }
        this.crearAnomaliaIMPACTO(muestraReciente);
    }

    public void crearAnomaliaKAIJU(Muestra muestraReciente){
        
        Boya boya = muestraReciente.getBoya();
        Double altura = null;

        for(int i = (boya.getMuestras().size() - 2 ); i >= 0; i = (i - 1)){
            
            Muestra muestraPrevia = boya.getMuestras().get(i); 
            
            if ( (muestraReciente.getAlturaNivelDelMar() > 200 && muestraPrevia.getAlturaNivelDelMar() > 200) 
              || (muestraReciente.getAlturaNivelDelMar() < -200 && muestraPrevia.getAlturaNivelDelMar() < -200) ){
                        
                if(diffHorariaMas10Min(muestraReciente.getHorario(), muestraPrevia.getHorario())){
                    Anomalia anomalia = new Anomalia();  
                    anomalia.setHorarioInicio(muestraPrevia.getHorario());
                    anomalia.setHorarioFin(muestraReciente.getHorario());
                    anomalia.setNivelDelMarActual(muestraReciente.getAlturaNivelDelMar());
                    anomalia.setTipoAlerta(TipoAlertaEnum.KAIJU);
                    boya.agregarAnomalia(anomalia);//may cause trouble
                    repo.save(anomalia);
                    break;
                }

            } else {
                break;
            }
                //   1.05(2)100     1.10(3)100     1.15(4)-250    1.20(5)100     1.25(6)-250
                //   1.05(2)250     1.10(3)250     1.15(4)250     1.20(5)250 ??? 
                //   si quisiera poner la fecha de inicio y no solo la primera despues de los 10 min?
                //   anomalia .00 - .15 (se elimine)  -> anomalia .00 - .20
                //   (que la primera forme parte de la segunda para no repetir anomalias, si la altura es constante, .00-.10, .00-.15, .00-.30)
        }
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
            anomalia.setTipoAlerta(TipoAlertaEnum.IMPACTO);
            boya.agregarAnomalia(anomalia);//may cause trouble
            repo.save(anomalia);
        }
    }

    public boolean diffHorariaMas10Min(Date horario1, Date horario2){
        long diffTiempo = horario1.getTime() - horario2.getTime();
        return (diffTiempo > 600000 || diffTiempo < -600000);
    }
}
