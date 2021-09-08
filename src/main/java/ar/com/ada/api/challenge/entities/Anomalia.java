package ar.com.ada.api.challenge.entities;

import java.util.Date;

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
        return TipoAlertaEnum.parse(tipoAlerta);
    }

    public void setTipoAlerta(TipoAlertaEnum) {
        this.tipoAlerta = TipoAlertaEnum.getValue();
    }

    public enum TipoAlertaEnum {
        KAIJU(1), IMPACTO(2);

        private final Integer value;

        // NOTE: Enum constructor tiene que estar en privado
        private TipoAlertaEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static TipoAlertaEnum parse(Integer id) {
            TipoAlertaEnum status = null; // Default
            for (TipoAlertaEnum item : TipoAlertaEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }


}
