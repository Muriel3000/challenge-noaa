package ar.com.ada.api.challenge.entities;

public class TipoAlerta {
    
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
