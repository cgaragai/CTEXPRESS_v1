package cl.android.trabajo.ctexpress.Modelo;

/**
 * Created by Leonardo on 28-09-2017.
 */

public class SolucionPropuesta {
    private String codigoSolucion;
    private String descripcionSolucion;

    public  SolucionPropuesta(){

    }
    public SolucionPropuesta(String codigoSolucion, String descripcionSolucion) {
        this.codigoSolucion = codigoSolucion;
        this.descripcionSolucion = descripcionSolucion;
    }

    public String getCodigoSolucion() {
        return codigoSolucion;
    }

    public void setCodigoSolucion(String codigoSolucion) {
        this.codigoSolucion = codigoSolucion;
    }

    public String getDescripcionSolucion() {
        return descripcionSolucion;
    }

    public void setDescripcionSolucion(String descripcionSolucion) {
        this.descripcionSolucion = descripcionSolucion;
    }
}
