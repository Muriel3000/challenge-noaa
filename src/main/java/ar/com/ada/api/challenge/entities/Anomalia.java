package ar.com.ada.api.challenge.entities;

import java.util.Date;
import ar.com.ada.api.challenge.entities.TipoAlerta.TipoAlertaEnum;

import javax.persistence.*;

@Entity
@Table(name = "anomalia")
public class Anomalia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anomalia_id")
    private Integer anomaliaId;

    @Column(name = "nivel_mar_actual")
    private Double nivelDelMarActual;
    
    @Column(name = "horario_inicio")
    private Date horarioInicio;

    @Column(name = "horario_fin")
    private Date horarioFin;
    
    @Column(name = "tipo_alerta")
    private Integer tipoAlerta;

    @ManyToOne
    @JoinColumn(name = "boya_id", referencedColumnName = "boya_id")
    private Boya boya;

    public Boya getBoya() {
        return boya;
    }

    public void setBoya(Boya boya) {
        this.boya = boya;
    }

    public Integer getAnomaliaId() {
        return anomaliaId;
    }

    public void setAnomaliaId(Integer anomaliaId) {
        this.anomaliaId = anomaliaId;
    }

    public Double getNivelDelMarActual() {
        return nivelDelMarActual;
    }

    public void setNivelDelMarActual(Double nivelDelMarActual) {
        this.nivelDelMarActual = nivelDelMarActual;
    }

    public Date getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(Date horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Date getHorarioFin() {
        return horarioFin;
    }

    public void setHorarioFin(Date horarioFin) {
        this.horarioFin = horarioFin;
    }

    public TipoAlertaEnum getTipoAlerta() {
        return TipoAlertaEnum.parse(this.tipoAlerta);
    }

    public void setTipoAlerta(TipoAlertaEnum tipoAlerta) {
        this.tipoAlerta = tipoAlerta.getValue();
    }

    


}
