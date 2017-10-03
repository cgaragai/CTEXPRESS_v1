package cl.android.trabajo.ctexpress.Mantenedor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Modelo.Sala;

/**
 * Created by Elaps-Merlina on 30-09-2017.
 */

public class MantenedorSala {

    private DB_Helper conector;
    private Context context;
    private ArrayList<String> columnas;
    private String tabla;

    public MantenedorSala(Context context){

        this.context = context;
        tabla = "sala";
        columnas = new ArrayList<String>();
        columnas.add("codigoSala");
        columnas.add("piso");

    }

    public ArrayList<Sala> getAll() {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla;
        Cursor resultado = this.conector.select(query);
        ArrayList<Sala> salas = new ArrayList<Sala>();
        if (resultado.moveToFirst()) {
            do {
                Sala sala = this.setSala(resultado);
                salas.add(sala);
            } while (resultado.moveToNext());
        }
        conector.close();
        return salas;
    }

    public Sala getByCodigoSala(String codigoSala) {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla + " WHERE codigoSala = '" + codigoSala + "'";
        Cursor resultado = this.conector.select(query);
        Sala sala = new Sala();
        if (resultado.moveToFirst()) {
            sala = this.setSala(resultado);
        }
        conector.close();
        return sala;
    }

    public boolean insert(Sala sala) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(sala);
        long id = this.conector.insert(tabla, columnas, valores);
        conector.close();
        if(id == -1){
            return false;
        }

        return true;
    }

    public boolean update(Sala sala) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(sala);
        String condicion = "codigoSala = '" + sala.getCodigoSala() + "'";
        int cantidadAfectados = this.conector.update(tabla, columnas, valores, condicion);
        conector.close();
        if(cantidadAfectados > 0)
            return true;

        return false;
    }

    public boolean delete(String codigoSala) {
        this.conector = new DB_Helper(this.context);
        String condicion = "codigoSala = '" + codigoSala + "'";
        int cantidadAfectados = this.conector.delete(tabla, condicion);
        conector.close();
        if(cantidadAfectados > 0)
            return true;

        return false;
    }

    private Sala setSala(Cursor resultado){
        Sala sala = new Sala();
        sala.setCodigoSala(resultado.getString(0));
        sala.setPiso(resultado.getInt(1));
        return sala;
    }

    private ArrayList<String> valores(Sala sala){
        ArrayList<String> valores = new ArrayList<String>();
        valores.add(sala.getCodigoSala());
        valores.add(Integer.toString(sala.getPiso()));
        return valores;
    }

}
