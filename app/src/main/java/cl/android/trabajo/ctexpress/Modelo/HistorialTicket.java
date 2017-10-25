package cl.android.trabajo.ctexpress.Modelo;

import java.util.Date;

/**
 * Created by cetecom on 25-10-2017.
 */

public class HistorialTicket {

    private int codigoTicket;
    private String estado;
    private Date fechaCambio;

    public HistorialTicket() {
    }

    public HistorialTicket(int codigoTicket, String estado, Date fechaCambio) {
        this.codigoTicket = codigoTicket;
        this.estado = estado;
        this.fechaCambio = fechaCambio;
    }

    public int getCodigoTicket() {
        return codigoTicket;
    }

    public void setCodigoTicket(int codigoTicket) {
        this.codigoTicket = codigoTicket;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }
}
