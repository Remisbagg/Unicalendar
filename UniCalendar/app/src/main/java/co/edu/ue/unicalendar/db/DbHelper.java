package co.edu.ue.unicalendar.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3; // Incrementa la versión de la base de datos
    private static final String DATABASE_NOMBRE = "Unicalendar.db";
    public static final String TABLE_USUARIOS = "usuarios";
    public static final String TABLE_EVENTO = "evento";
    public static final String TABLE_RECORDATORIO = "recordatorio";
    public static final String TABLE_NOTIFICACION = "notificacion";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Creación de la tabla Usuarios
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USUARIOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "foto BLOB)"); // Agregamos el campo para la foto como BLOB (Binary Large Object)

        // Creación de la tabla Evento
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_EVENTO + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fecha TEXT NOT NULL," +
                "hora_inicio TEXT NOT NULL," +
                "hora_final TEXT NOT NULL," +
                "clase TEXT NOT NULL," +
                "profesor TEXT NOT NULL," +
                "salon TEXT NOT NULL)");

        // Creación de la tabla Recordatorio
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_RECORDATORIO + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fecha TEXT NOT NULL," +
                "hora TEXT NOT NULL," +
                "recordatorio TEXT NOT NULL)");

        // Creación de la tabla Notificación
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NOTIFICACION + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "detalle TEXT NOT NULL," +
                "fecha TEXT NOT NULL," +
                "hora TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDATORIO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICACION);
        onCreate(sqLiteDatabase);
    }
}
