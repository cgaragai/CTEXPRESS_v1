package cl.android.trabajo.ctexpress.Modelo;

/**
 * Created by Leonardo on 28-09-2017.
 */

public class Falla {
    private int codigoFalla;
    private String descripcionFalla;
    private String codigoSolucion;

    public Falla(int codigoFalla, String descripcionFalla, String codigoSolucion) {
        this.codigoFalla = codigoFalla;
        this.descripcionFalla = descripcionFalla;
        this.codigoSolucion = codigoSolucion;
    }

    public int getCodigoFalla() {
        return codigoFalla;
    }

    public void setCodigoFalla(int codigoFalla) {
        this.codigoFalla = codigoFalla;
    }

    public String getDescripcionFalla() {
        return descripcionFalla;
    }

    public void setDescripcionFalla(String descripcionFalla) {
        this.descripcionFalla = descripcionFalla;
    }

    public String getCodigoSolucion() {
        return codigoSolucion;
    }

    public void setCodigoSolucion(String codigoSolucion) {
        this.codigoSolucion = codigoSolucion;
    }
}
