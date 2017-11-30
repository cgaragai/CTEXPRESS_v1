package cl.android.trabajo.ctexpress.Mantenedor.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Mantenedor.SQLite.DB_Helper;
import cl.android.trabajo.ctexpress.Modelo.Falla;

/**
 * Created by Elaps-Merlina on 30-09-2017.
 */

public class MantenedorFalla {

    private DB_Helper conector;
    private Context context;
    private ArrayList<String> columnas;
    private String tabla;

    public MantenedorFalla(Context context){

        this.context = context;
        tabla = "falla";
        columnas = new ArrayList<String>();
        columnas.add("descripcionFalla");
        columnas.add("codigoSolucion");

    }

    public ArrayList<Falla> getAll() {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla;
        Cursor resultado = this.conector.select(query);
        ArrayList<Falla> fallas = new ArrayList<Falla>();
        if (resultado.moveToFirst()) {
            do {
                Falla falla = this.setFalla(resultado);
                fallas.add(falla);
            } while (resultado.moveToNext());
        }
        conector.close();
        return fallas;
    }

    public ArrayList<String> getCodFallaAndDescripcion() {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT codigoFalla, descripcionFalla FROM " + tabla;
        Cursor resultado = this.conector.select(query);
        ArrayList<String> codAndDetalles = new ArrayList<>();
        if (resultado.moveToFirst()) {
            do {
                String codAndDetalle = String.valueOf(resultado.getInt(0)) + "-" + resultado.getString(1);
                codAndDetalles.add(codAndDetalle);
            } while (resultado.moveToNext());
        }
        conector.close();
        return codAndDetalles;
    }

    public Falla getByCodigoFalla(int codigoFalla) {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla + " WHERE codigoFalla = " + codigoFalla;
        Cursor resultado = this.conector.select(query);
        Falla falla = new Falla();
        if (resultado.moveToFirst()) {
            falla = this.setFalla(resultado);
        }
        conector.close();
        return falla;
    }

    public boolean insert(Falla falla) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(falla);
        long id = this.conector.insert(tabla, columnas, valores);
        conector.close();
        return id != -1;

    }

    public void insertFallasIniciales() {
        if(getAll().isEmpty()) {
            this.conector = new DB_Helper(this.context);
            ArrayList<String> valores = new ArrayList<>();
            valores.add("Otro");
            valores.add(null);
            this.conector.insert(tabla, columnas, valores);
            valores.set(0,"Equipo no enciende");
            valores.set(1,"SO001");
            this.conector.insert(tabla, columnas, valores);
            valores.set(0, "No muestra imagen");
            valores.set(1, "SO002");
            this.conector.insert(tabla, columnas, valores);
            conector.close();
        }
    }

    public boolean update(Falla falla) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(falla);
        String condicion = "codigoFalla = " + falla.getCodigoFalla();
        int cantidadAfectados = this.conector.update(tabla, columnas, valores, condicion);
        conector.close();
        return cantidadAfectados > 0;

    }

    public boolean delete(int codigoFalla) {
        this.conector = new DB_Helper(this.context);
        String condicion = "codigoFalla = " + codigoFalla;
        int cantidadAfectados = this.conector.delete(tabla, condicion);
        conector.close();
        return cantidadAfectados > 0;

    }

    private Falla setFalla(Cursor resultado){
        Falla falla = new Falla();
        falla.setCodigoFalla(resultado.getInt(0));
        falla.setDescripcionFalla(resultado.getString(1));
        falla.setCodigoSolucion(resultado.getString(2));
        return falla;
    }

    private ArrayList<String> valores(Falla falla){
        ArrayList<String> valores = new ArrayList<String>();
        valores.add(falla.getDescripcionFalla());
        valores.add(falla.getCodigoSolucion());
        return valores;
    }

    public void valoresFalla(Falla falla){
        Log.i("getCodigoFalla", String.valueOf(falla.getCodigoFalla()));
        Log.i("getDescripcionFalla", falla.getDescripcionFalla());
        Log.i("getCodigoSolucion", falla.getCodigoSolucion());
    }

}
