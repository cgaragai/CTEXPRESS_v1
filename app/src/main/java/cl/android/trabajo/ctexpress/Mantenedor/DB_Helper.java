package cl.android.trabajo.ctexpress.Mantenedor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class DB_Helper extends SQLiteOpenHelper {
        private static final int VERSION_BASEDATOS = 1;
        private static final String NOMBRE_BASEDATOS = "ctexpress.db";
        private static final String TABLA_USUARIO = "CREATE TABLE usuario (rut TEXT PRIMARY KEY, "
                + "nombre TEXT, apellido TEXT, correo TEXT, clave TEXT, tipo_usuario TEXT)";
        private static final String TABLA_TICKET = "CREATE TABLE ticket (codigoTicket TEXT PRIMARY KEY, rutUsuario TEXT, codigoFalla INTEGER, codigoEquipo TEXT, detalle TEXT, " +
                "FOREIGN KEY (rutUsuario) REFERENCES usuario(rut)," +
                "FOREIGN KEY (codigoFalla) REFERENCES falla(codigoFalla)," +
                "FOREIGN KEY (codigoEquipo) REFERENCES equipo(codigoEquipo))";
        private static final String TABLA_SALA = "CREATE TABLE sala (codigoSala TEXT PRIMARY KEY, piso INTEGER)";
        private  static final String TABLA_EQUIPO = "CREATE TABLE equipo (codigoEquipo TEXT PRIMARY KEY, descripcion TEXT, codigoSala TEXT, FOREIGN KEY(codigoSala) REFERENCES SALA(codigoSala))";
        private  static final String TABLA_SOLUCION_PROPUESTA = "CREATE TABLE solucion_propuesta (codigoSolucion TEXT PRIMARY KEY, descripcionSolucion TEXT)";
        private  static final String TABLA_FALLA = "CREATE TABLE falla (codigoFalla INTEGER PRIMARY KEY, descripcionFalla TEXT, codigoSolucion TEXT, FOREIGN KEY (codigoSolucion) REFERENCES solucion_propuesta(codigoSolucion))";

        public DB_Helper(Context context) {
            super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
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
}
