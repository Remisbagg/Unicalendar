package co.edu.ue.unicalendar.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import co.edu.ue.unicalendar.Evento;
import co.edu.ue.unicalendar.adaptadores.Usuarios;

public class DbUsuarios extends DbHelper {

    Context context;

    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarUsuario(String name, String email, String password) {
        long id = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("email", email);
            values.put("password", password);

            id = db.insert(TABLE_USUARIOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }
    public long insertarEvento(String fecha, String hora_inicio, String hora_final, String clase, String profesor, String salon) {
        long id = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("fecha", fecha);
            values.put("hora_inicio", hora_inicio);
            values.put("hora_final", hora_final);
            values.put("clase", clase);
            values.put("profesor", profesor);
            values.put("salon", salon);

            id = db.insert(TABLE_EVENTO, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }
    public long insertarNoti(String fecha, String nombre, String hora, String detalle) {
        long id = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("fecha", fecha);
            values.put("nombre", nombre);
            values.put("hora", hora);
            values.put("detalle", detalle);

            id = db.insert(TABLE_NOTIFICACION, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }
    public long insertarReco(String fecha, String hora, String recordatorio) {
        long id = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("fecha", fecha);
            values.put("hora", hora);
            values.put("recordatorio", recordatorio);

            id = db.insert(TABLE_RECORDATORIO, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }
    public boolean existeEventoConFecha(String fecha) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Consulta para obtener los eventos con la fecha dada
            String query = "SELECT * FROM " + TABLE_EVENTO + " WHERE fecha = ?";
            cursor = db.rawQuery(query, new String[]{fecha});

            // Si el cursor tiene resultados, significa que existe un evento con la fecha dada
            if (cursor != null && cursor.moveToFirst()) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        // Si no se encontró ningún evento con la fecha dada, devuelve false
        return false;
    }



    public ArrayList<String> mostrarCorreos() {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String> listaCorreos = new ArrayList<>();
        Cursor cursorCorreos;

        cursorCorreos = db.rawQuery("SELECT password FROM " + TABLE_USUARIOS + " ORDER BY email ASC", null);

        if (cursorCorreos.moveToFirst()) {
            do {
                String correo = cursorCorreos.getString(0);
                listaCorreos.add(correo);
            } while (cursorCorreos.moveToNext());
        }

        cursorCorreos.close();

        return listaCorreos;
    }
    public String getUserNameByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String userName = null;

        try {
            // Consulta para obtener el nombre del usuario por correo electrónico
            String query = "SELECT name FROM " + TABLE_USUARIOS + " WHERE email = ?";
            cursor = db.rawQuery(query, new String[]{email});

            // Verificar si el cursor tiene resultados
            if (cursor != null && cursor.moveToFirst()) {
                userName = cursor.getString(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return userName;
    }
    public String getIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String id = null;

        try {
            // Consulta para obtener el nombre del usuario por correo electrónico
            String query = "SELECT id FROM " + TABLE_USUARIOS + " WHERE email = ?";
            cursor = db.rawQuery(query, new String[]{email});

            // Verificar si el cursor tiene resultados
            if (cursor != null && cursor.moveToFirst()) {
                id = cursor.getString(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return id;
    }
    public ArrayList<Evento> getEventosPorFecha(String fecha) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Evento> eventos = new ArrayList<>();

        // Consulta para obtener los eventos con la fecha dada
        String query = "SELECT * FROM " + TABLE_EVENTO + " WHERE fecha = ?";
        Cursor cursor = db.rawQuery(query, new String[]{fecha});

        // Si el cursor tiene resultados, añade cada evento a la lista
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);  // Asume que "id" es la primera columna
            String hora_inicio = cursor.getString(1);  // Asume que "hora_inicio" es la segunda columna
            String hora_final = cursor.getString(2);  // Asume que "hora_final" es la tercera columna
            String clase = cursor.getString(3);  // Asume que "clase" es la cuarta columna
            String profesor = cursor.getString(4);  // Asume que "profesor" es la quinta columna
            String salon = cursor.getString(5);  // Asume que "salon" es la sexta columna

            Evento evento = new Evento(id, fecha, hora_inicio, hora_final, clase, profesor, salon);
            eventos.add(evento);
        }

        cursor.close();
        db.close();

        return eventos;
    }
    public Evento getEventoPorNombre(String nombreEvento) {
        SQLiteDatabase db = this.getReadableDatabase();
        Evento evento = null;

        // Consulta para obtener el evento con el nombre dado
        String query = "SELECT * FROM " + TABLE_EVENTO + " WHERE clase = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nombreEvento});

        // Si el cursor tiene resultados, crea un nuevo objeto Evento con los datos del primer resultado
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);  // Asume que "id" es la primera columna
            String fecha = cursor.getString(1);  // Asume que "fecha" es la segunda columna
            String hora_inicio = cursor.getString(2);  // Asume que "hora_inicio" es la tercera columna
            String hora_final = cursor.getString(3);  // Asume que "hora_final" es la cuarta columna
            String clase = cursor.getString(4);  // Asume que "clase" es la quinta columna
            String profesor = cursor.getString(5);  // Asume que "profesor" es la sexta columna
            String salon = cursor.getString(6);  // Asume que "salon" es la séptima columna

            evento = new Evento(id, fecha, hora_inicio, hora_final, clase, profesor, salon);
        }

        cursor.close();
        db.close();

        return evento;
    }





    public Usuarios verUsuario(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Usuarios usuario = null;
        Cursor cursorUsuarios;

        cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorUsuarios.moveToFirst()) {
            usuario = new Usuarios();
            usuario.setId(cursorUsuarios.getInt(0));
            usuario.setName(cursorUsuarios.getString(1));
            usuario.setEmail(cursorUsuarios.getString(2));
            usuario.setPassword(cursorUsuarios.getString(3));
        }

        cursorUsuarios.close();

        return usuario;
    }

    public boolean editarUsuario(int id, String name, String email) {
        boolean correcto = false;

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("email", email);

            db.update(TABLE_USUARIOS, values, "id=?", new String[]{String.valueOf(id)});
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarUsuario(int id) {
        boolean correcto = false;

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(TABLE_USUARIOS, "id=?", new String[]{String.valueOf(id)});
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean validarCredenciales(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Consulta para verificar las credenciales
            String query = "SELECT * FROM " + TABLE_USUARIOS + " WHERE email = ? AND password = ?";
            Log.d("Email", email);
            Log.d("Contraseña", password);
            cursor = db.rawQuery(query, new String[]{email, password});

            // Si hay un resultado en el cursor, las credenciales son válidas
            if (cursor != null && cursor.getCount() > 0) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        // Si no se encontraron credenciales válidas
        return false;
    }
    public boolean guardarImagenEnBaseDeDatos(int usuarioID, byte[] imagen) {
        boolean correcto = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("foto", imagen);

            // Actualiza la fila del usuario con el ID correspondiente
            db.update(TABLE_USUARIOS, values, "id=?", new String[]{String.valueOf(usuarioID)});
            correcto = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }



}