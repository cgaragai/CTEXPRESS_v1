package cl.android.trabajo.ctexpress.Mantenedor;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Modelo.Ticket;

/**
 * Created by Elaps-Merlina on 30-09-2017.
 */

public class MantenedorTicket {

    private DB_Helper conector;
    private Context context;
    private ArrayList<String> columnas;
    private String tabla;

    public MantenedorTicket(Context context){

        this.context = context;
        tabla = "ticket";
        columnas = new ArrayList<String>();
        columnas.add("rutUsuario");
        columnas.add("codigoFalla");
        columnas.add("codigoEquipo");
        columnas.add("detalle");
        columnas.add("estado");
        columnas.add("rutTecnico");

    }

    public ArrayList<Ticket> getAll() {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla;
        Cursor resultado = this.conector.select(query);
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        if (resultado.moveToFirst()) {
            do {
                Ticket ticket = this.setTicket(resultado);
                tickets.add(ticket);
            } while (resultado.moveToNext());
        }
        conector.close();
        return tickets;
    }

    public ArrayList<Ticket> getAllByEstado(String estado) {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla + " WHERE estado = '" + estado+"'";
        Cursor resultado = this.conector.select(query);
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        if (resultado.moveToFirst()) {
            do {
                Ticket ticket = this.setTicket(resultado);
                tickets.add(ticket);
            } while (resultado.moveToNext());
        }
        conector.close();
        return tickets;
    }

    public ArrayList<String> getAllByTecnico() {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT rutTecnico, Count(codigoTicket) as cantidad FROM " + tabla +" GROUP BY rutTecnico";
        Cursor resultado = this.conector.select(query);
        ArrayList<String> tickets = new ArrayList<String>();
        if (resultado.moveToFirst()) {
            do {
                tickets.add("Rut Tecnico: "+resultado.getString(0)+" Cantidad de ticket: "+resultado.getString(1));
            } while (resultado.moveToNext());
        }
        conector.close();
        return tickets;
    }

    public ArrayList<String> getAllByProfesor() {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT rutUsuario, Count(codigoTicket) as cantidad FROM " + tabla +" GROUP BY rutUsuario";
        Cursor resultado = this.conector.select(query);
        ArrayList<String> tickets = new ArrayList<String>();
        if (resultado.moveToFirst()) {
            do {
                tickets.add("Rut Usuario: "+resultado.getString(0)+" Cantidad de ticket: "+resultado.getString(1));
            } while (resultado.moveToNext());
        }
        conector.close();
        return tickets;
    }

    public ArrayList<Ticket> getAllByEstadoAndRutTecnico(String estado, String rutTecnico) {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla + " WHERE estado = '" + estado + "' AND rutTecnico = '" + rutTecnico+"'";
        Cursor resultado = this.conector.select(query);
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        if (resultado.moveToFirst()) {
            do {
                Ticket ticket = this.setTicket(resultado);
                tickets.add(ticket);
            } while (resultado.moveToNext());
        }
        conector.close();
        return tickets;
    }

    public Ticket getByCodigoTicket(int codigoTicket) {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla + " WHERE codigoTicket = " + codigoTicket;
        Cursor resultado = this.conector.select(query);
        Ticket ticket = new Ticket();
        if (resultado.moveToFirst()) {
            ticket = this.setTicket(resultado);
        }
        else {ticket = null;}
        conector.close();
        return ticket;
    }

    public int insert(Ticket ticket) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(ticket);
        long id = this.conector.insert(tabla, columnas, valores);
        conector.close();
        return (int)id;

    }

    public boolean update(Ticket ticket) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(ticket);
        String condicion = "codigoTicket = " + ticket.getCodigoTicket();
        int cantidadAfectados = this.conector.update(tabla, columnas, valores, condicion);
        conector.close();
        return cantidadAfectados > 0;

    }

    public boolean delete(int codigoTicket) {
        this.conector = new DB_Helper(this.context);
        String condicion = "codigoTicket = " + codigoTicket;
        int cantidadAfectados = this.conector.delete(tabla, condicion);
        conector.close();
        return cantidadAfectados > 0;

    }

    private Ticket setTicket(Cursor resultado){
        Ticket ticket = new Ticket();
        ticket.setCodigoTicket(resultado.getInt(0));
        ticket.setRutUsuario(resultado.getString(1));
        ticket.setCodigoFalla(resultado.getInt(2));
        ticket.setCodigoEquipo(resultado.getString(3));
        ticket.setDetalle(resultado.getString(4));
        ticket.setEstado(resultado.getString(5));
        ticket.setRutTecnico(resultado.getString(6));
        return ticket;
    }

    private ArrayList<String> valores(Ticket ticket){
        ArrayList<String> valores = new ArrayList<String>();
        valores.add(ticket.getRutUsuario());
        valores.add(Integer.toString(ticket.getCodigoFalla()));
        valores.add(ticket.getCodigoEquipo());
        valores.add(ticket.getDetalle());
        valores.add(ticket.getEstado());
        valores.add(ticket.getRutTecnico());

        return valores;
    }
}
