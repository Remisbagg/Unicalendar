package co.edu.ue.unicalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Usuario {
    private String id;
    private String name;
    private String email;
    private String password;
    private String foto;

    // Agrega getters y setters para los campos si es necesario

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
