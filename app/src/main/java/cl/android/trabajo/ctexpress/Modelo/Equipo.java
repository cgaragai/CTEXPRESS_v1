package cl.android.trabajo.ctexpress.Modelo;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class Equipo {
    private String codigoEquipo;
    private String descripcion;
    private String tipoEquipo;
    private String codigoSala;

    public Equipo() {
    }

    public Equipo(String codigoEquipo, String descripcion, String tipoEquipo, String codigoSala) {
        this.codigoEquipo = codigoEquipo;
        this.descripcion = descripcion;
        this.tipoEquipo = tipoEquipo;
        this.codigoSala = codigoSala;
    }

    public String getCodigoEquipo() {
        return codigoEquipo;
    }

    public void setCodigoEquipo(String codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public String getCodigoSala() {
        return codigoSala;
    }

    public void setCodigoSala(String codigoSala) {
        this.codigoSala = codigoSala;
    }
}
