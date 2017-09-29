package cl.android.trabajo.ctexpress.Modelo;

/**
 * Created by Leonardo on 28-09-2017.
 */

public class SolucionPropuesta {
    private String codigoSolucion;
    private String descripcionSolucion;

    public SolucionPropuesta(String codigoSolucion, String descripcionSolucion) {
        this.codigoSolucion = codigoSolucion;
        this.descripcionSolucion = descripcionSolucion;
    }

    public String getCodigoSOlucion() {
        return codigoSolucion;
    }

    public void setCodigoSOlucion(String codigoSOlucion) {
        this.codigoSolucion = codigoSOlucion;
    }

    public String getDescripcionSolucion() {
        return descripcionSolucion;
    }

    public void setDescripcionSolucion(String descripcionSolucion) {
        this.descripcionSolucion = descripcionSolucion;
    }
}
