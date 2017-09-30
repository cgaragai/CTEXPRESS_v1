package cl.android.trabajo.ctexpress.Mantenedor;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

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
        tabla = "solucionPropuesta";
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
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla + " WHERE codigoSolucion = " + codigoSolucion;
        Cursor resultado = this.conector.select(query);
        SolucionPropuesta solucionPropuesta = new SolucionPropuesta();
        if (resultado.moveToFirst()) {
            solucionPropuesta = this.setSolucionPropuesta(resultado);
        }
        conector.close();
        return solucionPropuesta;
    }

    public void insert(SolucionPropuesta solucionPropuesta) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(solucionPropuesta);
        this.conector.insert(tabla, columnas, valores);
        conector.close();
    }

    public void update(SolucionPropuesta solucionPropuesta) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(solucionPropuesta);
        String condicion = "codigoSolucion = " + solucionPropuesta.getCodigoSOlucion();
        this.conector.update(tabla, columnas, valores, condicion);
        conector.close();
    }

    public void delete(int codigoTicket) {
        this.conector = new DB_Helper(this.context);
        String condicion = "codigoSolucion = " + codigoTicket;
        this.conector.delete(tabla, condicion);
        conector.close();
    }

    private SolucionPropuesta setSolucionPropuesta(Cursor resultado){
        SolucionPropuesta solucionPropuesta = new SolucionPropuesta();
        solucionPropuesta.setCodigoSOlucion(resultado.getString(0));
        solucionPropuesta.setDescripcionSolucion(resultado.getString(1));
        return solucionPropuesta;
    }

    private ArrayList<String> valores(SolucionPropuesta solucionPropuesta){
        ArrayList<String> valores = new ArrayList<String>();
        valores.add(solucionPropuesta.getCodigoSOlucion());
        valores.add(solucionPropuesta.getDescripcionSolucion());
        return valores;
    }
}