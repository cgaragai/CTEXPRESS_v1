package cl.android.trabajo.ctexpress.Mantenedor;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cl.android.trabajo.ctexpress.Modelo.HistorialTicket;

/**
 * Created by Elaps-Merlina on 26-10-2017.
 */

public class MantenedorHistorialTicket {

    private DB_Helper conector;
    private Context context;
    private ArrayList<String> columnas;
    private String tabla;

    public MantenedorHistorialTicket(Context context){

        this.context = context;
        tabla = "historial_ticket";
        columnas = new ArrayList<String>();
        columnas.add("codigoTicket");
        columnas.add("estado");
        columnas.add("fechaCambio");

    }

    public ArrayList<HistorialTicket> getAll(String condicion) {
        ArrayList<HistorialTicket> historialTickets = new ArrayList<HistorialTicket>();
        this.conector = new DB_Helper(this.context);
        try {
            String query = "SELECT * FROM " + tabla + condicion;
            Cursor resultado = this.conector.select(query);
            if (resultado.moveToFirst()) {
                do {
                    HistorialTicket historialTicket = this.setHistorialTicket(resultado);
                    historialTickets.add(historialTicket);
                } while (resultado.moveToNext());
            }
        }catch (Exception e){

        }
        conector.close();
        return historialTickets;
    }

    public HistorialTicket getByCodigoTicket(int codigoTicket) {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla + " WHERE codigoTicket = " + codigoTicket;
        Cursor resultado = this.conector.select(query);
        HistorialTicket historialTicket = new HistorialTicket();
        if (resultado.moveToFirst()) {
            historialTicket = this.setHistorialTicket(resultado);
        }
        conector.close();
        return historialTicket;
    }

    public boolean insert(HistorialTicket historialTicket) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(historialTicket);
        long id = this.conector.insert(tabla, columnas, valores);
        conector.close();
        return id != -1;
    }

    public boolean update(HistorialTicket historialTicket) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(historialTicket);
        String condicion = "codigoTicket = " + historialTicket.getCodigoTicket();
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

    private HistorialTicket setHistorialTicket(Cursor resultado){
        HistorialTicket historialTicket = new HistorialTicket();
        historialTicket.setCodigoTicket(resultado.getInt(0));
        historialTicket.setEstado(resultado.getString(1));
        Date auxDate = new Date(resultado.getLong(2));
        historialTicket.setFechaCambio(auxDate);
        return historialTicket;
    }

    private ArrayList<String> valores(HistorialTicket historialTicket){
        ArrayList<String> valores = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");

        valores.add(String.valueOf(historialTicket.getCodigoTicket()));
        valores.add(historialTicket.getEstado());
        valores.add(sdf.format(historialTicket.getFechaCambio()));
        return valores;
    }

    public void valoresHistorialTicket(HistorialTicket historialTicket){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
        Log.i("getCodigoTicket", String.valueOf(historialTicket.getCodigoTicket()));
        Log.i("getEstado", historialTicket.getEstado());
        Log.i("getFechaCambio", sdf.format(historialTicket.getFechaCambio()));
    }
}
