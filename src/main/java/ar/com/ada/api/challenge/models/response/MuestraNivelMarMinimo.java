package ar.com.ada.api.challenge.models.response;

import java.util.Date;

import ar.com.ada.api.challenge.entities.Muestra;

public class MuestraNivelMarMinimo {
    public String color;
    public Double alturaNivelDelMarMinima;
    public Date horario;

    public static MuestraNivelMarMinimo convertirDesde(Muestra muestra){
        MuestraNivelMarMinimo m = new MuestraNivelMarMinimo();
        m.color = muestra.getBoya().getColorLuz();
        m.alturaNivelDelMarMinima = muestra.getAlturaNivelDelMar();
        m.horario = muestra.getHorario();
        return m;
    }
}
