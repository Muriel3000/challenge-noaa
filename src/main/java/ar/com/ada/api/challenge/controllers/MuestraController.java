package ar.com.ada.api.challenge.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.challenge.entities.Boya;
import ar.com.ada.api.challenge.entities.Muestra;
import ar.com.ada.api.challenge.models.request.InfoMuestraNueva;
import ar.com.ada.api.challenge.models.response.GenericResponse;
import ar.com.ada.api.challenge.models.response.ListaMuestraResponse;
import ar.com.ada.api.challenge.models.response.MuestraResponse;
import ar.com.ada.api.challenge.services.MuestraService;

@RestController
public class MuestraController {
    
    @Autowired
    private MuestraService service;

    @PostMapping("/muestras")
    public ResponseEntity<MuestraResponse> crearMuestra(@RequestBody InfoMuestraNueva infoMuestra){
        Muestra muestraCompleta = service.crearMuestra(infoMuestra.boyaId, infoMuestra.horario, 
        infoMuestra.matricula, infoMuestra.latitud, infoMuestra.longitud, infoMuestra.alturaNivelDelMar);
        
        MuestraResponse r = new MuestraResponse();
        r.id = muestraCompleta.getMuestraId();
        r.color = muestraCompleta.getBoya().getColorLuz();
        r.message = "La muestra se ha creado con exito";
        return ResponseEntity.ok(r);
        
    }

    @GetMapping("/muestras/boyas/{idBoya}")
    public ResponseEntity<List<ListaMuestraResponse>> traerMuestrasDeBoya(@PathVariable Integer idBoya){
        List<Muestra> muestras = service.traerMuestras(idBoya);
        List<ListaMuestraResponse> muestrasAFront = new ArrayList();
        for(Muestra m : muestras){
            ListaMuestraResponse muestraSinBoya = ListaMuestraResponse.convertirDesde(m);
            muestrasAFront.add(muestraSinBoya);
        }
        return ResponseEntity.ok(muestrasAFront);
    }

    @DeleteMapping("/muestras/{id}")
    public ResponseEntity<GenericResponse> actualizarColorBoyaAzul(@PathVariable Integer id){
        GenericResponse r = new GenericResponse();
        Boya boya = service.actualizarColorBoyaAzul(id);
        r.isOk = true;
        r.id = id;
        r.message = "Se ha actualizado con exito el color de la boya " +
        boya.getBoyaId() + " a " + boya.getColorLuz();
        return ResponseEntity.ok(r);
    }
}
