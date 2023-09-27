package co.edu.ue.unicalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.ue.unicalendar.db.DbUsuarios;

public class Registro extends AppCompatActivity {

    EditText etNombre, etCorreo,  etPassword;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = findViewById(R.id.et_nombre);
        etCorreo = findViewById(R.id.et_correo);
        etPassword = findViewById(R.id.et_contra);
        btnRegistrar = findViewById(R.id.btnRegitrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etNombre.getText().toString().equals("") &&
                        !etCorreo.getText().toString().equals("") &&
                        !etPassword.getText().toString().equals("")) {

                    DbUsuarios dbUsuarios = new DbUsuarios(Registro.this);
                    long id = dbUsuarios.insertarUsuario(
                            etNombre.getText().toString(),
                            etCorreo.getText().toString(),
                            etPassword.getText().toString()
                    );

                    if (id > 0) {
                        Toast.makeText(Registro.this, "USUARIO REGISTRADO", Toast.LENGTH_LONG).show();
                        limpiar();
                    } else {
                        Toast.makeText(Registro.this, "ERROR AL REGISTRAR USUARIO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Registro.this, "DEBE LLENAR TODOS LOS CAMPOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void limpiar() {
        etNombre.setText("");
        etCorreo.setText("");
        etPassword.setText("");
    }
}
