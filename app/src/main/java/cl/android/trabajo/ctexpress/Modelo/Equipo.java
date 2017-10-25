package cl.android.trabajo.ctexpress.Modelo;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class Equipo {
    private String codigoEquipo;
    private String descripcion;
    private int codigoTipoEquipo;
    private String codigoSala;

    public Equipo() {
    }

    public Equipo(String codigoEquipo, String descripcion, int codigoTipoEquipo, String codigoSala) {
        this.codigoEquipo = codigoEquipo;
        this.descripcion = descripcion;
        this.codigoTipoEquipo = codigoTipoEquipo;
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

    public int getCodigoTipoEquipo() {
        return codigoTipoEquipo;
    }

    public void setCodigoTipoEquipo(int codigoTipoEquipo) {
        this.codigoTipoEquipo = codigoTipoEquipo;
    }

    public String getCodigoSala() {
        return codigoSala;
    }

    public void setCodigoSala(String codigoSala) {
        this.codigoSala = codigoSala;
    }
}
