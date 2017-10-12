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
        columnas = new ArrayList<>();
        columnas.add("rutUsuario");
        columnas.add("codigoFalla");
        columnas.add("codigoEquipo");
        columnas.add("tipoEquipo");
        columnas.add("codigoSala");
        columnas.add("detalle");
        columnas.add("estado");
        columnas.add("rutTecnico");

    }

    public ArrayList<Ticket> getAll() {
        ArrayList<Ticket> tickets = null;
        try {
            this.conector = new DB_Helper(this.context);
            String query = "SELECT * FROM " + tabla;
            Cursor resultado = this.conector.select(query);
            tickets = new ArrayList<>();
            if (resultado.moveToFirst()) {
                do {
                    Ticket ticket = this.setTicket(resultado);
                    tickets.add(ticket);
                } while (resultado.moveToNext());
            }
        }catch (Exception e){
        }
        conector.close();
        return tickets;
    }

    public ArrayList<Ticket> getAllByEstado(String estado) {
        ArrayList<Ticket> tickets = null;
        try {
            this.conector = new DB_Helper(this.context);
            String query = "SELECT * FROM " + tabla + " WHERE estado = '" + estado + "'";
            Cursor resultado = this.conector.select(query);
            tickets = new ArrayList<>();
            if (resultado.moveToFirst()) {
                do {
                    Ticket ticket = this.setTicket(resultado);
                    tickets.add(ticket);
                } while (resultado.moveToNext());
            }
        }catch(Exception e){

        }
        conector.close();
        return tickets;
    }

    public ArrayList<String> getAllByTecnico() {
        ArrayList<String> tickets = null;
        try {
            this.conector = new DB_Helper(this.context);
            String query = "SELECT rutTecnico, Count(codigoTicket) as cantidad FROM " + tabla + " GROUP BY rutTecnico";
            Cursor resultado = this.conector.select(query);
            tickets = new ArrayList<>();
            if (resultado.moveToFirst()) {
                do {
                    tickets.add("Rut Tecnico: " + resultado.getString(0) + " Cantidad de ticket: " + resultado.getString(1));
                } while (resultado.moveToNext());
            }
        }catch (Exception e){

        }
        conector.close();
        return tickets;
    }

    public ArrayList<String> getAllByProfesor() {
        ArrayList<String> tickets = null;
        try {
            this.conector = new DB_Helper(this.context);
            String query = "SELECT rutUsuario, Count(codigoTicket) as cantidad FROM " + tabla + " GROUP BY rutUsuario";
            Cursor resultado = this.conector.select(query);
            tickets = new ArrayList<>();
            if (resultado.moveToFirst()) {
                do {
                    tickets.add("Rut Usuario: " + resultado.getString(0) + " Cantidad de ticket: " + resultado.getString(1));
                } while (resultado.moveToNext());
            }
        }catch (Exception e){

        }
        conector.close();
        return tickets;
    }

    public ArrayList<Ticket> getAllByEstadoAndRutTecnico(String estado, String rutTecnico) {
        ArrayList<Ticket> tickets = null;
        try {
            this.conector = new DB_Helper(this.context);
            String query = "SELECT * FROM " + tabla + " WHERE estado = '" + estado + "' AND rutTecnico = '" + rutTecnico + "'";
            Cursor resultado = this.conector.select(query);
            tickets = new ArrayList<>();
            if (resultado.moveToFirst()) {
                do {
                    Ticket ticket = this.setTicket(resultado);
                    tickets.add(ticket);
                } while (resultado.moveToNext());
            }
        }catch (Exception e){

        }
        conector.close();
        return tickets;
    }

    public Ticket getByCodigoTicket(int codigoTicket) {
        Ticket ticket = null;
        try {
            this.conector = new DB_Helper(this.context);
            String query = "SELECT * FROM " + tabla + " WHERE codigoTicket = " + codigoTicket;
            Cursor resultado = this.conector.select(query);
            ticket = new Ticket();
            if (resultado.moveToFirst()) {
                ticket = this.setTicket(resultado);
            } else {
                ticket = null;
            }
            conector.close();
        }catch (Exception e){

        }

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
        ticket.setTipoEquipo(resultado.getString(4));
        ticket.setCodigoSala(resultado.getString(5));
        ticket.setDetalle(resultado.getString(6));
        ticket.setEstado(resultado.getString(7));
        ticket.setRutTecnico(resultado.getString(8));
        return ticket;
    }

    private ArrayList<String> valores(Ticket ticket){
        ArrayList<String> valores = new ArrayList<String>();
        valores.add(ticket.getRutUsuario());
        valores.add(Integer.toString(ticket.getCodigoFalla()));
        valores.add(ticket.getCodigoEquipo());
        valores.add(ticket.getTipoEquipo());
        valores.add(ticket.getCodigoSala());
        valores.add(ticket.getDetalle());
        valores.add(ticket.getEstado());
        valores.add(ticket.getRutTecnico());

        return valores;
    }

    public boolean compararTickets(Ticket ticketA, Ticket ticketB){
        boolean diferente = false;
        if(ticketA.getCodigoTicket() == ticketB.getCodigoTicket()) diferente = true;
        else if(ticketA.getRutUsuario().equals(ticketB.getRutUsuario())) diferente = true;
        else if(ticketA.getCodigoFalla() == ticketB.getCodigoFalla()) diferente = true;
        else if(ticketA.getCodigoEquipo().equals(ticketB.getCodigoEquipo())) diferente = true;
        else if(ticketA.getTipoEquipo().equals(ticketB.getTipoEquipo())) diferente = true;
        else if(ticketA.getCodigoSala().equals(ticketB.getCodigoSala())) diferente = true;
        else if(ticketA.getDetalle().equals(ticketB.getDetalle())) diferente = true;
        else if(ticketA.getEstado().equals(ticketB.getEstado())) diferente = true;
        else if(ticketA.getRutTecnico().equals(ticketB.getRutTecnico())) diferente = true;
        Log.i("Diferente", String.valueOf(diferente));
        valoresTicket(ticketA);
        valoresTicket(ticketB);
        return diferente;
    }

    public void valoresTicket(Ticket ticket){
        Log.i("getCodigoTicket", String.valueOf(ticket.getCodigoTicket()));
        Log.i("getRutUsuario", ticket.getRutUsuario());
        Log.i("getCodigoFalla", String.valueOf(ticket.getCodigoFalla()));
        if(ticket.getCodigoEquipo() != null) Log.i("getCodigoEquipo", ticket.getCodigoEquipo());
        Log.i("getTipoEquipo", ticket.getTipoEquipo());
        Log.i("getCodigoSala", ticket.getCodigoSala());
        if(ticket.getDetalle() != null) Log.i("getDetalle", ticket.getDetalle());
        Log.i("getEstado", ticket.getEstado());
        if(ticket.getRutTecnico() != null) Log.i("getRutTecnico", ticket.getRutTecnico());
    }
}
