package cl.android.trabajo.ctexpress.Mantenedor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class DB_Helper extends SQLiteOpenHelper {

        private static final int VERSION_BASEDATOS = 1;
        private static final String NOMBRE_BASEDATOS = "ctexpress.db";
        private static final String TABLA_USUARIO = "CREATE TABLE IF NOT EXISTS usuario (rut TEXT PRIMARY KEY, nombre TEXT, apellido TEXT, correo TEXT, clave TEXT, tipoUsuario TEXT)";
        private static final String TABLA_TICKET = "CREATE TABLE IF NOT EXISTS ticket (codigoTicket INTEGER PRIMARY KEY AUTOINCREMENT, rutUsuario TEXT, codigoFalla INTEGER, codigoEquipo TEXT, detalle TEXT, estado TEXT, rutTecnico TEXT, " +
                "FOREIGN KEY (rutUsuario) REFERENCES usuario(rut), " +
                "FOREIGN KEY (codigoFalla) REFERENCES falla(codigoFalla), " +
                "FOREIGN KEY (codigoEquipo) REFERENCES equipo(codigoEquipo), " +
                "FOREIGN KEY (rutTecnico) REFERENCES usuario(rut))";
        private static final String TABLA_SALA = "CREATE TABLE IF NOT EXISTS sala (codigoSala TEXT PRIMARY KEY, piso INTEGER)";

        private  static final String TABLA_EQUIPO = "CREATE TABLE IF NOT EXISTS equipo (codigoEquipo TEXT PRIMARY KEY, descripcion TEXT, tipoEquipo TEXT, codigoSala TEXT, FOREIGN KEY(codigoSala) REFERENCES SALA(codigoSala))";
        private  static final String TABLA_SOLUCION_PROPUESTA = "CREATE TABLE IF NOT EXISTS solucion_propuesta (codigoSolucion TEXT PRIMARY KEY, descripcionSolucion TEXT)";
        private  static final String TABLA_FALLA = "CREATE TABLE IF NOT EXISTS falla (codigoFalla INTEGER PRIMARY KEY, descripcionFalla TEXT, codigoSolucion TEXT, FOREIGN KEY (codigoSolucion) REFERENCES solucion_propuesta(codigoSolucion))";

        private SQLiteDatabase db = null;

        public DB_Helper(Context context) {
            super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
            db = getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLA_SALA);
            db.execSQL(TABLA_SOLUCION_PROPUESTA);
            db.execSQL(TABLA_USUARIO);
            db.execSQL(TABLA_FALLA);
            db.execSQL(TABLA_EQUIPO);
            db.execSQL(TABLA_TICKET);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ TABLA_SALA);
            db.execSQL("DROP TABLE IF EXISTS "+ TABLA_SOLUCION_PROPUESTA);
            db.execSQL("DROP TABLE IF EXISTS "+ TABLA_USUARIO);
            db.execSQL("DROP TABLE IF EXISTS "+ TABLA_FALLA);
            db.execSQL("DROP TABLE IF EXISTS "+ TABLA_EQUIPO);
            db.execSQL("DROP TABLE IF EXISTS "+ TABLA_TICKET);
            onCreate(db);
        }

        public Cursor select(String sql) throws SQLiteException {
            return db.rawQuery(sql, null);
        }

        public long insert(String tabla ,ArrayList<String> columnas, ArrayList<String> valores) throws SQLiteException {
            ContentValues insert = new ContentValues();
            for(int z=0;z<columnas.size();z++) {
                Log.i("Datos",columnas.get(z) + " " + valores.get(z));
                insert.put(columnas.get(z), valores.get(z));
            }
            return db.insert(tabla, null, insert);
        }

        public int update(String tabla ,ArrayList<String> columnas, ArrayList<String> valores, String condicion) throws SQLiteException {
            ContentValues update = new ContentValues();
            for(int z=1;z<columnas.size();z++)
                update.put(columnas.get(z),valores.get(z));
            return db.update(tabla, update, condicion, null);
        }

        public int delete(String tabla, String condicion) throws SQLiteException {
            return db.delete(tabla, condicion, null);
        }

        @Override
        public void close() {
            db.close();
        }
}
