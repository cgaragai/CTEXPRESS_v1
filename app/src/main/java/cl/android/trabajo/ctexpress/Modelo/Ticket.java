package cl.android.trabajo.ctexpress.Modelo;

import java.io.Serializable;

/**
 * Created by Leonardo on 28-09-2017.
 */

public class Ticket implements Serializable {
    private int codigoTicket;
    private String rutUsuario;
    private int codigoFalla;
    private String codigoEquipo;
    private String detalle;
    private String estado;
    private String rutTecnico;

    public Ticket(){

    }

    public Ticket(int codigoTicket, String rutUsuario, int codigoFalla, String codigoEquipo, String detalle, String estado, String rutTecnico) {
        this.codigoTicket = codigoTicket;
        this.rutUsuario = rutUsuario;
        this.codigoFalla = codigoFalla;
        this.codigoEquipo = codigoEquipo;
        this.detalle = detalle;
        this.estado = estado;
        this.rutTecnico = rutTecnico;
    }

    public int getCodigoTicket() {
        return codigoTicket;
    }

    public void setCodigoTicket(int codigoTicket) {
        this.codigoTicket = codigoTicket;
    }

    public String getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(String rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    public int getCodigoFalla() {
        return codigoFalla;
    }

    public void setCodigoFalla(int codigoFalla) {
        this.codigoFalla = codigoFalla;
    }

    public String getCodigoEquipo() {
        return codigoEquipo;
    }

    public void setCodigoEquipo(String codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRutTecnico() {
        return rutTecnico;
    }

    public void setRutTecnico(String rutTecnico) {
        this.rutTecnico = rutTecnico;
    }
}
