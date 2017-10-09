package cl.android.trabajo.ctexpress.Mantenedor;

import android.content.Context;
import android.database.Cursor;

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
        columnas.add("codigoTicket");
        columnas.add("rutUsuario");
        columnas.add("codigoFalla");
        columnas.add("codigoEquipo");
        columnas.add("detalle");
        columnas.add("estado");

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

    public Ticket getByCodigoTicket(String codigoTicket) {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla + " WHERE codigoTicket = " + codigoTicket;
        Cursor resultado = this.conector.select(query);
        Ticket ticket = new Ticket();
        if (resultado.moveToFirst()) {
            ticket = this.setTicket(resultado);
        }
        conector.close();
        return ticket;
    }

    public boolean insert(Ticket ticket) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(ticket);
        long id = this.conector.insert(tabla, columnas, valores);
        conector.close();
        if(id == -1){
            return false;
        }

        return true;
    }

    public boolean update(Ticket ticket) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(ticket);
        String condicion = "codigoTicket = " + ticket.getCodigoTicket();
        int cantidadAfectados = this.conector.update(tabla, columnas, valores, condicion);
        conector.close();
        if(cantidadAfectados > 0)
            return true;

        return false;
    }

    public boolean delete(int codigoTicket) {
        this.conector = new DB_Helper(this.context);
        String condicion = "codigoTicket = " + codigoTicket;
        int cantidadAfectados = this.conector.delete(tabla, condicion);
        conector.close();
        if(cantidadAfectados > 0)
            return true;

        return false;
    }

    private Ticket setTicket(Cursor resultado){
        Ticket ticket = new Ticket();
        ticket.setCodigoTicket(resultado.getInt(0));
        ticket.setRutUsuario(resultado.getString(1));
        ticket.setCodigoFalla(resultado.getInt(2));
        ticket.setCodigoEquipo(resultado.getString(3));
        ticket.setDetalle(resultado.getString(4));
        ticket.setDetalle(resultado.getString(5));
        return ticket;
    }

    private ArrayList<String> valores(Ticket ticket){
        ArrayList<String> valores = new ArrayList<String>();
        valores.add(String.valueOf(ticket.getCodigoTicket()));
        valores.add(ticket.getRutUsuario());
        valores.add(Integer.toString(ticket.getCodigoFalla()));
        valores.add(ticket.getCodigoEquipo());
        valores.add(ticket.getDetalle());
        valores.add(ticket.getEstado());
        return valores;
    }
}
