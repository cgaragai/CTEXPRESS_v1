package cl.android.trabajo.ctexpress.Mantenedor;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Modelo.Equipo;

/**
 * Created by Elaps-Merlina on 30-09-2017.
 */

public class MantenedorEquipo {

    private DB_Helper conector;
    private Context context;
    private ArrayList<String> columnas;
    private String tabla;

    public MantenedorEquipo(Context context){

        this.context = context;
        tabla = "equipo";
        columnas = new ArrayList<String>();
        columnas.add("codigoEquipo");
        columnas.add("descripcion");
        columnas.add("tipoEquipo");
        columnas.add("codigoSala");

    }

    public ArrayList<Equipo> getAll(String condicion) {
        ArrayList<Equipo> equipos = new ArrayList<Equipo>();
        this.conector = new DB_Helper(this.context);
        try {
            String query = "SELECT * FROM " + tabla + condicion;
            Cursor resultado = this.conector.select(query);
            if (resultado.moveToFirst()) {
                do {
                    Equipo equipo = this.setEquipo(resultado);
                    equipos.add(equipo);
                } while (resultado.moveToNext());
            }
        }catch (Exception e){

        }
        conector.close();
        return equipos;
    }

    public ArrayList<Equipo> getAllByCodigoSala(String codigoSala) {
        String condicion = " WHERE codigoSala = '" + codigoSala + "'";
        return getAll(condicion);
    }

    public ArrayList<String> getAllTipoEquipoByCodigoSala(String codigoSala) {
        ArrayList<String> tipoEquipos = new ArrayList<>();
        this.conector = new DB_Helper(this.context);
        try {
            String query = "SELECT distinct(tipoEquipo) FROM " + tabla + " WHERE codigoSala = '" + codigoSala + "'";
            Cursor resultado = this.conector.select(query);
            if (resultado.moveToFirst()) {
                do {
                    String tipoEquipo = resultado.getString(0);
                    tipoEquipos.add(tipoEquipo);
                } while (resultado.moveToNext());
            }

        }catch (Exception e){

        }
        conector.close();
        return tipoEquipos;
    }

    public ArrayList<String> getCodByCodSalaAndTipoEquipo(String codigoSala, String tipoEquipo) {
        ArrayList<String> codigosEquipo = new ArrayList<>();
        this.conector = new DB_Helper(this.context);
        try {
            String query = "SELECT codigoEquipo FROM " + tabla + " WHERE codigoSala = '" + codigoSala + "' AND tipoEquipo = '" + tipoEquipo + "'";
            Cursor resultado = this.conector.select(query);
            if (resultado.moveToFirst()) {
                do {
                    String codigoEquipo = resultado.getString(0);
                    codigosEquipo.add(codigoEquipo);
                } while (resultado.moveToNext());
            }
        }catch(Exception e){

        }
        conector.close();
        return codigosEquipo;
    }

    public Equipo getByCodigoEquipo(String codigoEquipo) {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla + " WHERE codigoEquipo = '" + codigoEquipo + "'";
        Cursor resultado = this.conector.select(query);
        Equipo equipo = new Equipo();
        if (resultado.moveToFirst()) {
            equipo = this.setEquipo(resultado);
        }
        conector.close();
        return equipo;
    }

    public boolean insert(Equipo equipo) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(equipo);
        long id = this.conector.insert(tabla, columnas, valores);
        conector.close();
        return id != -1;
    }

    public void insertEquiposIniciales() {
        if(getAll("").isEmpty()) {
            this.conector = new DB_Helper(this.context);
            ArrayList<String> valores = new ArrayList<>();
            valores.add("AA001");
            valores.add("Modelo 001");
            valores.add("Aire Acondicionado");
            valores.add("101");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "CO001");
            valores.set(2, "Computador");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "TE001");
            valores.add(2, "Televisor");
            valores.set(3, "102");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "PO001");
            valores.set(2, "Proyector");
            valores.set(3, "103");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "PO002");
            valores.set(1, "Modelo 002");
            valores.set(3, "201");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "CO002");
            valores.set(2, "Computador");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "TE002");
            valores.set(2, "Televisor");
            valores.set(3, "202");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "AA002");
            valores.set(2, "Aire Acondicionado");
            valores.set(3, "301");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "CO003");
            valores.set(1, "Modelo 003");
            valores.set(2, "Computador");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "PO003");
            valores.set(2, "Proyector");
            valores.set(3, "L1");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "AA003");
            valores.set(2, "Aire Acondicionado");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "TE003");
            valores.set(2, "Televisor");
            valores.set(3, "L2");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "TE004");
            valores.set(1, "Modelo 004");
            valores.set(3, "L3");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "AA004");
            valores.set(2, "Aire Acondicionado");
            valores.set(3, "L4");
            this.conector.insert(tabla, columnas, valores);
            conector.close();
        }
    }

    public boolean update(Equipo equipo) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(equipo);
        String condicion = "codigoEquipo = '" + equipo.getCodigoEquipo() + "'";
        int cantidadAfectados = this.conector.update(tabla, columnas, valores, condicion);
        conector.close();
        return cantidadAfectados > 0;

    }

    public boolean delete(String codigoEquipo) {
        this.conector = new DB_Helper(this.context);
        String condicion = "codigoEquipo = '" + codigoEquipo + "'";
        int cantidadAfectados = this.conector.delete(tabla, condicion);
        conector.close();
        return cantidadAfectados > 0;

    }

    private Equipo setEquipo(Cursor resultado){
        Equipo equipo = new Equipo();
        equipo.setCodigoEquipo(resultado.getString(0));
        equipo.setDescripcion(resultado.getString(1));
        equipo.setTipoEquipo(resultado.getString(2));
        equipo.setCodigoSala(resultado.getString(3));
        return equipo;
    }

    private ArrayList<String> valores(Equipo equipo){
        ArrayList<String> valores = new ArrayList<String>();
        valores.add(equipo.getCodigoEquipo());
        valores.add(equipo.getDescripcion());
        valores.add(equipo.getTipoEquipo());
        valores.add(equipo.getCodigoSala());
        return valores;
    }

    public void valoresEquipo(Equipo equipo){
        Log.i("getCodigoEquipo", equipo.getCodigoEquipo());
        Log.i("getDescripcion", equipo.getDescripcion());
        Log.i("getTipoEquipo", equipo.getTipoEquipo());
        Log.i("getCodigoSala", equipo.getCodigoSala());
    }
}
