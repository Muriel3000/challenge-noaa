package ar.com.ada.api.challenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.challenge.entities.Boya;
import ar.com.ada.api.challenge.models.request.ColorBoya;
import ar.com.ada.api.challenge.models.request.InfoBoyaNueva;
import ar.com.ada.api.challenge.models.response.GenericResponse;
import ar.com.ada.api.challenge.services.BoyaService;

@RestController
public class BoyaController {
    
    @Autowired
    private BoyaService service;

    @PostMapping("/boyas")
    public ResponseEntity<GenericResponse> crearBoya(@RequestBody InfoBoyaNueva infoBoya){
        Integer boyaId = service.crearBoya(infoBoya.longitudInstalacion, infoBoya.latitudInstalacion);
        GenericResponse r = new GenericResponse();
        r.isOk = true;
        r.message = "La boya ha sido creada con exito";
        r.id = boyaId;
        return ResponseEntity.ok(r);
    }

    @GetMapping("/boyas")
    public ResponseEntity<List<Boya>> traerBoyas(){
        List<Boya> boyas = service.traerBoyas();
        return ResponseEntity.ok(boyas);
    }

    @GetMapping("/boyas/{id}")
    public ResponseEntity<Boya> traerBoya(@PathVariable Integer id){
        Boya boya = service.traerBoya(id);
        return ResponseEntity.ok(boya);
    }

    @PutMapping("/boyas/{id}")
    public ResponseEntity<GenericResponse> actualizarColorBoya(@PathVariable Integer id, @RequestBody ColorBoya colorBoya){
        String color = service.actualizarColorBoya(id, colorBoya.color);
        GenericResponse r = new GenericResponse();
        r.isOk = true;
        r.id = id;
        r.message = "La luz ha sido actualizada a " + color + " correctamente";
        return ResponseEntity.ok(r);
    }
}
