package co.edu.ue.unicalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Evento {
    private int id;
    private String fecha;
    private String hora_inicio;
    private String hora_final;
    private String clase;
    private String profesor;
    private String salon;

    public Evento(int id, String fecha, String hora_inicio, String hora_final, String clase, String profesor, String salon) {
        this.id = id;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
        this.clase = clase;
        this.profesor = profesor;
        this.salon = salon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_final() {
        return hora_final;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }
}

