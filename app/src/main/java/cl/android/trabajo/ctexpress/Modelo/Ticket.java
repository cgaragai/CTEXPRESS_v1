package cl.android.trabajo.ctexpress.Modelo;

import java.io.Serializable;

/**
 * Created by Leonardo on 28-09-2017.
 */

public class Ticket implements Serializable {
    private int codigoTicket;
    private String rutUsuario;
    private int codigoFalla;
    private int codigoTipoEquipo;
    private String codigoEquipo;
    private String codigoSala;
    private String detalle;
    private String estado;
    private String rutTecnico;

    public Ticket(){

    }

    public Ticket(int codigoTicket, String rutUsuario, int codigoFalla, int codigoTipoEquipo, String codigoEquipo, String codigoSala, String detalle, String estado, String rutTecnico) {
        this.codigoTicket = codigoTicket;
        this.rutUsuario = rutUsuario;
        this.codigoFalla = codigoFalla;
        this.codigoTipoEquipo = codigoTipoEquipo;
        this.codigoEquipo = codigoEquipo;
        this.codigoSala = codigoSala;
        this.detalle = detalle;
        this.estado = estado;
        this.rutTecnico = rutTecnico;
    }

    public String getCodigoSala() {
        return codigoSala;
    }

    public void setCodigoSala(String codigoSala) {
        this.codigoSala = codigoSala;
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

    public int getCodigoTipoEquipo() {
        return codigoTipoEquipo;
    }

    public void setCodigoTipoEquipo(int codigoTipoEquipo) {
        this.codigoTipoEquipo = codigoTipoEquipo;
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
