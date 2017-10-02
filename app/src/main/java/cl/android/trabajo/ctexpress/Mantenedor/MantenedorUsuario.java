package cl.android.trabajo.ctexpress.Mantenedor;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Modelo.Usuario;

/**
 * Created by Elaps-Merlina on 29-09-2017.
 */

public class MantenedorUsuario{

    private DB_Helper conector;
    private Context context;
    private ArrayList<String> columnas;
    private String tabla;

    public MantenedorUsuario(Context context){

        this.context = context;
        tabla = "usuario";
        columnas = new ArrayList<String>();
        columnas.add("nombre");
        columnas.add("apellido");
        columnas.add("correo");
        columnas.add("clave");
        columnas.add("tipoUsuario");

    }

    public ArrayList<Usuario> getAll() {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla;
        Cursor resultado = this.conector.select(query);
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        if (resultado.moveToFirst()) {
            do {
                Usuario usuario = this.setUsuario(resultado);
                usuarios.add(usuario);
            } while (resultado.moveToNext());
        }
        conector.close();
        return usuarios;
    }

    public Usuario getByRut(String rut) {
        this.conector = new DB_Helper(this.context);
        String query = "SELECT * FROM " + tabla + " WHERE rut = '" + rut + "'";
        Cursor resultado = this.conector.select(query);
        Usuario usuario = new Usuario();
        if (resultado.moveToFirst()) {
            usuario = this.setUsuario(resultado);
        }
        conector.close();
        return usuario;
    }

    public void insert(Usuario usuario) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(usuario);
        this.conector.insert(tabla, columnas, valores);
        conector.close();
    }

    public void insertUsuariosIniciales() {
        if(getAll().size() == 0) {
            this.conector = new DB_Helper(this.context);
            ArrayList<String> valores = new ArrayList<>();
            valores.add("1-1");
            valores.add("Cristian");
            valores.add("Garagai");
            valores.add("cg@ctexpress");
            valores.add("abc123");
            valores.add("admin");
            this.conector.insert(tabla, columnas, valores);
            valores.add("1-2");
            valores.add("Leonardo");
            valores.add("Godoy");
            valores.add("lg@ctexpress");
            this.conector.insert(tabla, columnas, valores);
            conector.close();
        }
    }

    public void update(Usuario usuario) {
        this.conector = new DB_Helper(this.context);
        ArrayList<String> valores = this.valores(usuario);
        String condicion = "rut = '" + usuario.getRut() + "'";
        this.conector.update(tabla, columnas, valores, condicion);
        conector.close();
    }

    public void delete(String rut) {
        this.conector = new DB_Helper(this.context);
        String condicion = "rut = '" + rut + "'";
        this.conector.delete(tabla, condicion);
        conector.close();
    }

    private Usuario setUsuario(Cursor resultado){
        Usuario usuario = new Usuario();
        usuario.setRut(resultado.getString(0));
        usuario.setNombre(resultado.getString(1));
        usuario.setApellido(resultado.getString(2));
        usuario.setCorreo(resultado.getString(3));
        usuario.setClave(resultado.getString(4));
        usuario.setTipoUsuario(resultado.getString(5));
        return usuario;
    }

    private ArrayList<String> valores(Usuario usuario){
        ArrayList<String> valores = new ArrayList<String>();
        valores.add(usuario.getNombre());
        valores.add(usuario.getApellido());
        valores.add(usuario.getCorreo());
        valores.add(usuario.getClave());
        valores.add(usuario.getTipoUsuario());
        return valores;
    }
}
