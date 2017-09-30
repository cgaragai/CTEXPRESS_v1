package cl.android.trabajo.ctexpress.Mantenedor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import cl.android.trabajo.ctexpress.Modelo.Usuario;

/**
 * Created by Elaps-Merlina on 29-09-2017.
 */

public class MantenedorUsuario{
    private Context context = this.context;

    public void insertarDatos(Usuario usuario){

        DB_Helper usuarioDB = new DB_Helper(context);
        SQLiteDatabase db = usuarioDB.getWritableDatabase();//Ayuda a ejecutar consultas SQL
        if(db != null) {

            db.execSQL("INSERT INTO usuario "
                    + "(rut, nombre, apellido, correo, clave, tipo_usuario) "
                    + "VALUES ("
                    + "'" + usuario.getRut()
                    + "', '" + usuario.getNombre()
                    + "', '" + usuario.getApellido()
                    + "', '" + usuario.getCorreo()
                    + "', '" + usuario.getClave()
                    + "', '" + usuario.getTipoUsuario()
                    + "');");

        }
        db.close();
    }

    public void modificarDatos(Usuario usuario){

        DB_Helper usuarioDB = new DB_Helper(context);
        SQLiteDatabase db = usuarioDB.getWritableDatabase();//Ayuda a ejecutar consultas SQL
        if(db != null) {

            db.execSQL("UPDATE usuario "
                    + "SET nombre = '" + usuario.getNombre() + "', "
                    + "apellido = '" + usuario.getApellido() + "', "
                    + "correo = '" + usuario.getCorreo() + "', "
                    + "clave = '" + usuario.getClave() + "', "
                    + "tipo_usuario = '" + usuario.getTipoUsuario() + "' "
                    + "WHERE rut = '" + usuario.getRut() + "';");

        }
        db.close();
    }

    public void eliminarUsuario(String rut){

        DB_Helper usuarioDB = new DB_Helper(context);
        SQLiteDatabase db = usuarioDB.getWritableDatabase();//Ayuda a ejecutar consultas SQL
        if(db != null) {

            db.execSQL("DELETE FROM usuario "
                    + "WHERE rut = '" + rut + "';");

        }
        db.close();

    }

    public List<Usuario> retornaUsuarios()
    {
        DB_Helper usuarioDB = new DB_Helper(context);
        SQLiteDatabase db= usuarioDB.getWritableDatabase();
        List<Usuario> auxListaUsuario = new ArrayList<>();

        Cursor auxCursor = db.rawQuery("SELECT * FROM usuario;",null);

        auxCursor.moveToFirst();

        do
        {
            Usuario auxUsuario = new Usuario();
            auxUsuario.setRut(auxCursor.getString(0));
            auxUsuario.setNombre(auxCursor.getString(1));
            //
            auxListaUsuario.add(auxUsuario);


        } while(auxCursor.moveToNext());

        auxCursor.close();
        db.close();
        return auxListaUsuario;

    }

    public Usuario buscarCliente(String rut)
    {
        DB_Helper usuarioDB = new DB_Helper(context);
        SQLiteDatabase db = usuarioDB.getWritableDatabase();
        Usuario auxUsuario = new Usuario();

        Cursor auxCursor = db.rawQuery("SELECT * FROM usuario "
                + " WHERE rut = '" + rut +"';",null);

        auxCursor.moveToFirst();

        if (auxCursor != null)
        {
            auxUsuario.setRut(auxCursor.getString(0));
            auxUsuario.setNombre(auxCursor.getString(1));

        }
        else
        {
            auxUsuario.setRut("");
            auxUsuario.setNombre("");
        }
        auxCursor.close();
        db.close();
        return auxUsuario;

    }
}
