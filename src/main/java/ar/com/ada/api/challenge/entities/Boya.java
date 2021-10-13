package ar.com.ada.api.challenge.entities;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "boya")
public class Boya {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boya_id")
    private Integer boyaId;

    @Column(name = "color_luz")
    private String colorLuz;

    @Column(name = "longitud_instalacion")
    private Double longitudInstalacion;

    @Column(name = "latitud_instalacion")
    private Double latitudInstalacion;

    @JsonIgnore
    @OneToMany(mappedBy = "boya", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Muestra> muestras;

    @JsonIgnore
    @OneToMany(mappedBy= "boya", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Anomalia> anomalias;

    public List<Anomalia> getAnomalias() {
        return anomalias;
    }

    public void setAnomalias(List<Anomalia> anomalias) {
        this.anomalias = anomalias;
    }

    public void agregarMuesta(Muestra muestra){
        this.muestras.add(muestra);
        muestra.setBoya(this);
    }

    public void agregarAnomalia(Anomalia anomalia){
        this.anomalias.add(anomalia);
        anomalia.setBoya(this);
    }
    public List<Muestra> getMuestras() {
        return muestras;
    }

    public void setMuestras(List<Muestra> muestras) {
        this.muestras = muestras;
    }

    public Integer getBoyaId() {
        return boyaId;
    }

    public void setBoyaId(Integer boyaId) {
        this.boyaId = boyaId;
    }

    public String getColorLuz() {
        return colorLuz;
    }

    public void setColorLuz(String colorLuz) {
        this.colorLuz = colorLuz;
    }

    public Double getLongitudInstalacion() {
        return longitudInstalacion;
    }

    public void setLongitudInstalacion(Double longitudInstalacion) {
        this.longitudInstalacion = longitudInstalacion;
    }

    public Double getLatitudInstalacion() {
        return latitudInstalacion;
    }

    public void setLatitudInstalacion(Double latitudInstalacion) {
        this.latitudInstalacion = latitudInstalacion;
    }
}
