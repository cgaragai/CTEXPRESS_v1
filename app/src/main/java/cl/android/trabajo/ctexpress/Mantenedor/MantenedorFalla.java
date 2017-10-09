package cl.android.trabajo.ctexpress.Mantenedor;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

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
        columnas.add("codigoFalla");
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

    public Falla getByCodigoFalla(String codigoFalla) {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla + " WHERE codigoFalla = '" + codigoFalla + "'";
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
        if(id == -1){
            return false;
        }

        return true;
    }

    public boolean update(Falla falla) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(falla);
        String condicion = "codigoFalla = '" + falla.getCodigoFalla() + "'";
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

    private Falla setFalla(Cursor resultado){
        Falla falla = new Falla();
        falla.setCodigoFalla(resultado.getInt(0));
        falla.setCodigoSolucion(resultado.getString(1));
        falla.setDescripcionFalla(resultado.getString(2));
        return falla;
    }

    private ArrayList<String> valores(Falla falla){
        ArrayList<String> valores = new ArrayList<String>();
        valores.add(String.valueOf(falla.getCodigoFalla()));
        valores.add(falla.getDescripcionFalla());
        valores.add(falla.getCodigoSolucion());
        return valores;
    }


}
