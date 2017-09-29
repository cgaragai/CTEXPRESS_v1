package cl.android.trabajo.ctexpress.Modelo;

/**
 * Created by Leonardo on 28-09-2017.
 */

public class Sala {
    private String codigoSala;
    private int piso;

    public Sala(String codigoSala, int piso) {
        this.codigoSala = codigoSala;
        this.piso = piso;
    }

    public String getCodigoSala() {
        return codigoSala;
    }

    public void setCodigoSala(String codigoSala) {
        this.codigoSala = codigoSala;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }
}
