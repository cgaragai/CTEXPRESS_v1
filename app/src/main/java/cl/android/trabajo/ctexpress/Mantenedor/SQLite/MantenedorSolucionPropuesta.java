package cl.android.trabajo.ctexpress.Mantenedor.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Mantenedor.SQLite.DB_Helper;
import cl.android.trabajo.ctexpress.Modelo.SolucionPropuesta;

/**
 * Created by Elaps-Merlina on 30-09-2017.
 */

public class MantenedorSolucionPropuesta {

    private DB_Helper conector;
    private Context context;
    private ArrayList<String> columnas;
    private String tabla;

    public MantenedorSolucionPropuesta(Context context){

        this.context = context;
        tabla = "solucion_propuesta";
        columnas = new ArrayList<String>();
        columnas.add("codigoSolucion");
        columnas.add("descripcionSolucion");

    }

    public ArrayList<SolucionPropuesta> getAll() {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla;
        Cursor resultado = this.conector.select(query);
        ArrayList<SolucionPropuesta> solucionesPropuestas = new ArrayList<SolucionPropuesta>();
        if (resultado.moveToFirst()) {
            do {
                SolucionPropuesta solucionPropuesta = this.setSolucionPropuesta(resultado);
                solucionesPropuestas.add(solucionPropuesta);
            } while (resultado.moveToNext());
        }
        conector.close();
        return solucionesPropuestas;
    }

    public SolucionPropuesta getByCodigoSolucion(String codigoSolucion) {
        SolucionPropuesta solucionPropuesta = new SolucionPropuesta();
        this.conector = new DB_Helper(this.context);
        try {
            Log.i("codigoSolucion", codigoSolucion);
            String query = "SELECT * FROM " + tabla + " WHERE codigoSolucion = '" + codigoSolucion + "'";
            Cursor resultado = this.conector.select(query);
            if (resultado.moveToFirst()) {
                solucionPropuesta = this.setSolucionPropuesta(resultado);
            }
        }catch (Exception e){

        }
        conector.close();
        return solucionPropuesta;
    }

    public boolean insert(SolucionPropuesta solucionPropuesta) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(solucionPropuesta);
        long id = this.conector.insert(tabla, columnas, valores);
        conector.close();
        return id != -1;

    }

    public void insertSolucionesIniciales() {
        if(getAll().isEmpty()) {
            this.conector = new DB_Helper(this.context);
            ArrayList<String> valores = new ArrayList<>();
            valores.add("SO001");
            valores.add("Revisar enchufe");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "SO002");
            valores.set(1, "Apagar y volver a encender equipo");
            this.conector.insert(tabla, columnas, valores);
            conector.close();
        }
    }

    public boolean update(SolucionPropuesta solucionPropuesta) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(solucionPropuesta);
        String condicion = "codigoSolucion = " + solucionPropuesta.getCodigoSolucion();
        int cantidadAfectados = this.conector.update(tabla, columnas, valores, condicion);
        conector.close();
        return cantidadAfectados > 0;

    }

    public boolean delete(int codigoTicket) {
        this.conector = new DB_Helper(this.context);
        String condicion = "codigoSolucion = " + codigoTicket;
        int cantidadAfectados = this.conector.delete(tabla, condicion);
        conector.close();
        return cantidadAfectados > 0;

    }

    private SolucionPropuesta setSolucionPropuesta(Cursor resultado){
        SolucionPropuesta solucionPropuesta = new SolucionPropuesta();
        solucionPropuesta.setCodigoSolucion(resultado.getString(0));
        solucionPropuesta.setDescripcionSolucion(resultado.getString(1));
        return solucionPropuesta;
    }

    private ArrayList<String> valores(SolucionPropuesta solucionPropuesta){
        ArrayList<String> valores = new ArrayList<String>();
        valores.add(solucionPropuesta.getCodigoSolucion());
        valores.add(solucionPropuesta.getDescripcionSolucion());
        return valores;
    }
}
