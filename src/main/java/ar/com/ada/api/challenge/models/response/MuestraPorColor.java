package ar.com.ada.api.challenge.models.response;

import java.util.Date;

import ar.com.ada.api.challenge.entities.Muestra;

public class MuestraPorColor {
    public Integer boyaId;
    public Date horario;
    public Double alturaNivelDelMar;

    public static MuestraPorColor convertirDesde(Muestra muestra){
        MuestraPorColor m = new MuestraPorColor();
        m.boyaId = muestra.getBoya().getBoyaId();
        m.horario = muestra.getHorario();
        m.alturaNivelDelMar = muestra.getAlturaNivelDelMar(); 
        return m;
    }
}
