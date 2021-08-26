package ar.com.ada.api.challenge.models.response;

import java.util.Date;

import ar.com.ada.api.challenge.entities.Muestra;

public class ListaMuestraResponse {
    public Integer muestraId;
    public Date horarioMuestra;
    public String matriculaEmbarcacion;
    public Double longitud;
    public Double latitud;
    public Double alturaNivelDelMar;

    public static ListaMuestraResponse convertirDesde(Muestra muestra){
        ListaMuestraResponse muestraSinBoya = new ListaMuestraResponse();
        muestraSinBoya.muestraId = muestra.getMuestraId();
        muestraSinBoya.horarioMuestra = muestra.getHorario();
        muestraSinBoya.matriculaEmbarcacion = muestra.getMatriculaEmbarcacion();
        muestraSinBoya.longitud = muestra.getLongitud();
        muestraSinBoya.latitud = muestra.getLatitud();
        muestraSinBoya.alturaNivelDelMar = muestra.getAlturaNivelDelMar();
        return muestraSinBoya;
    }

}
