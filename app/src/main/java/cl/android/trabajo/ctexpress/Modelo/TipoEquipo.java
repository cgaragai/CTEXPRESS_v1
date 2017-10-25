package cl.android.trabajo.ctexpress.Modelo;

/**
 * Created by cetecom on 25-10-2017.
 */

public class TipoEquipo {

    private int codigoTipoEquipo;
    private String nombre;

    public TipoEquipo(){ }

    public TipoEquipo(int codigoTipoEquipo, String nombre) {
        this.codigoTipoEquipo = codigoTipoEquipo;
        this.nombre = nombre;
    }

    public int getCodigoTipoEquipo() {
        return codigoTipoEquipo;
    }

    public void setCodigoTipoEquipo(int codigoTipoEquipo) {
        this.codigoTipoEquipo = codigoTipoEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
