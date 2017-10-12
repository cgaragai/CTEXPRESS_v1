package cl.android.trabajo.ctexpress.Mantenedor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

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

    public ArrayList<Integer> getAllPiso(){
        this.conector = new DB_Helper(this.context);
        String query = "SELECT distinct(piso) FROM " + tabla;
        Cursor resultado = this.conector.select(query);
        ArrayList<Integer> pisos = new ArrayList<Integer>();
        if (resultado.moveToFirst()) {
            do {
                int piso = resultado.getInt(0);
                pisos.add(piso);
            } while (resultado.moveToNext());
        }
        conector.close();
        return pisos;
    }

    public ArrayList<String> getCodigoSalaByPiso(int piso){
        this.conector = new DB_Helper(this.context);
        String query = "SELECT codigoSala FROM " + tabla +" WHERE piso = " + piso;
        Cursor resultado = this.conector.select(query);
        ArrayList<String> salas = new ArrayList<String>();
        if (resultado.moveToFirst()) {
            do {
                String sala = resultado.getString(0);
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
        return id != -1;

    }

    public void insertSalasIniciales(){
        if(getAll().isEmpty()) {
            this.conector = new DB_Helper(this.context);
            ArrayList<String> valores = new ArrayList<>();
            valores.add("101");
            valores.add("1");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "102");
            valores.set(1, "1");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "103");
            valores.set(1, "1");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "201");
            valores.set(1, "2");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "202");
            valores.set(1, "2");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "301");
            valores.set(1, "3");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "L1");
            valores.set(1, "4");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "L2");
            valores.set(1, "4");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "L3");
            valores.set(1, "4");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "L4");
            valores.set(1, "4");
            this.conector.insert(tabla, columnas, valores);
            conector.close();
        }
    }

    public boolean update(Sala sala) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(sala);
        String condicion = "codigoSala = '" + sala.getCodigoSala() + "'";
        int cantidadAfectados = this.conector.update(tabla, columnas, valores, condicion);
        conector.close();
        return cantidadAfectados > 0;

    }

    public boolean delete(String codigoSala) {
        this.conector = new DB_Helper(this.context);
        String condicion = "codigoSala = '" + codigoSala + "'";
        int cantidadAfectados = this.conector.delete(tabla, condicion);
        conector.close();
        return cantidadAfectados > 0;

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

    public void valoresSala(Sala sala){
        Log.i("getCodigoSala", sala.getCodigoSala());
        Log.i("getPiso", String.valueOf(sala.getPiso()));
    }

}
