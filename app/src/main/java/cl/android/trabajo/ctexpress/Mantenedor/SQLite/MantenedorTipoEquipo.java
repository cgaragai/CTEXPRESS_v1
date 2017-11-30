package cl.android.trabajo.ctexpress.Mantenedor.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Mantenedor.SQLite.DB_Helper;
import cl.android.trabajo.ctexpress.Modelo.TipoEquipo;

/**
 * Created by Elaps-Merlina on 26-10-2017.
 */

public class MantenedorTipoEquipo {
    private DB_Helper conector;
    private Context context;
    private ArrayList<String> columnas;
    private String tabla;

    public MantenedorTipoEquipo(Context context){

        this.context = context;
        tabla = "tipo_equipo";
        columnas = new ArrayList<String>();
        columnas.add("nombre");

    }

    public ArrayList<TipoEquipo> getAll(String condicion) {
        ArrayList<TipoEquipo> tipoEquipos = new ArrayList<TipoEquipo>();
        this.conector = new DB_Helper(this.context);
        try {
            String query = "SELECT * FROM " + tabla + condicion;
            Cursor resultado = this.conector.select(query);
            if (resultado.moveToFirst()) {
                do {
                    TipoEquipo tipoEquipo = this.setTipoEquipo(resultado);
                    tipoEquipos.add(tipoEquipo);
                } while (resultado.moveToNext());
            }
        }catch (Exception e){

        }
        conector.close();
        return tipoEquipos;
    }

    public TipoEquipo getByCodigoTipoEquipo(int codigoTipoEquipo) {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla + " WHERE codigoTipoEquipo = " + codigoTipoEquipo;
        Cursor resultado = this.conector.select(query);
        TipoEquipo tipoEquipo = new TipoEquipo();
        if (resultado.moveToFirst()) {
            tipoEquipo = this.setTipoEquipo(resultado);
        }
        conector.close();
        return tipoEquipo;
    }

    public boolean insert(TipoEquipo tipoEquipo) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(tipoEquipo);
        long id = this.conector.insert(tabla, columnas, valores);
        conector.close();
        return id != -1;
    }

    public void insertTipoEquiposIniciales() {
        if(getAll("").isEmpty()) {
            this.conector = new DB_Helper(this.context);
            ArrayList<String> valores = new ArrayList<>();
            valores.add("Aire Acondicionado");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "Computador");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "Televisor");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "Proyector");
            this.conector.insert(tabla, columnas, valores);
            conector.close();
        }
    }

    public boolean update(TipoEquipo tipoEquipo) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(tipoEquipo);
        String condicion = "codigoTipoEquipo = '" + tipoEquipo.getCodigoTipoEquipo() + "'";
        int cantidadAfectados = this.conector.update(tabla, columnas, valores, condicion);
        conector.close();
        return cantidadAfectados > 0;

    }

    public boolean delete(int codigoTipoEquipo) {
        this.conector = new DB_Helper(this.context);
        String condicion = "codigoTipoEquipo = " + codigoTipoEquipo;
        int cantidadAfectados = this.conector.delete(tabla, condicion);
        conector.close();
        return cantidadAfectados > 0;

    }

    private TipoEquipo setTipoEquipo(Cursor resultado){
        TipoEquipo tipoEquipo = new TipoEquipo();
        tipoEquipo.setCodigoTipoEquipo(resultado.getInt(0));
        tipoEquipo.setNombre(resultado.getString(1));
        return tipoEquipo;
    }

    private ArrayList<String> valores(TipoEquipo tipoEquipo){
        ArrayList<String> valores = new ArrayList<String>();
        valores.add(tipoEquipo.getNombre());
        return valores;
    }

    public void valoresTipoEquipo(TipoEquipo tipoEquipo){
        Log.i("getCodigoTipoEquipo", String.valueOf(tipoEquipo.getCodigoTipoEquipo()));
        Log.i("getNombre", tipoEquipo.getNombre());
    }
}
