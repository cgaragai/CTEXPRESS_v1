package cl.android.trabajo.ctexpress.Mantenedor;

import android.content.Context;
import android.database.Cursor;

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

    public ArrayList<Equipo> getAll() {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla;
        Cursor resultado = this.conector.select(query);
        ArrayList<Equipo> equipos = new ArrayList<Equipo>();
        if (resultado.moveToFirst()) {
            do {
                Equipo equipo = this.setEquipo(resultado);
                equipos.add(equipo);
            } while (resultado.moveToNext());
        }
        conector.close();
        return equipos;
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
        if(id == -1){
            return false;
        }

        return true;
    }

    public boolean update(Equipo equipo) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(equipo);
        String condicion = "codigoEquipo = '" + equipo.getCodigoEquipo() + "'";
        int cantidadAfectados = this.conector.update(tabla, columnas, valores, condicion);
        conector.close();
        if(cantidadAfectados > 0)
            return true;

        return false;
    }

    public boolean delete(String codigoEquipo) {
        this.conector = new DB_Helper(this.context);
        String condicion = "codigoEquipo = '" + codigoEquipo + "'";
        int cantidadAfectados = this.conector.delete(tabla, condicion);
        conector.close();
        if(cantidadAfectados > 0)
            return true;

        return false;
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
}
