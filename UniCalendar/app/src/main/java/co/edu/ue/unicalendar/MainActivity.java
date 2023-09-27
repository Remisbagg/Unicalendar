package co.edu.ue.unicalendar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import co.edu.ue.unicalendar.db.DbUsuarios;
import androidx.appcompat.app.AppCompatActivity;

import co.edu.ue.unicalendar.activity.InicioActivity;

public class MainActivity extends AppCompatActivity {
    TextView tv_registrar;
    Button btn_login;
    EditText et_correo,et_contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_correo= (EditText) findViewById(R.id.et_correo);
        et_contra= (EditText) findViewById(R.id.et_contra);
        tv_registrar= (TextView) findViewById(R.id.tv_reg);
        btn_login =  findViewById(R.id.loginButton);
        tv_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg =new Intent(MainActivity.this, Registro.class);
                MainActivity.this.startActivity(intentReg);
            }

        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = et_correo.getText().toString();
                String contraseña = et_contra.getText().toString();
                DbUsuarios dbUsuarios = new DbUsuarios(MainActivity.this);

                // Realizar la consulta a la base de datos para verificar las credenciales
                if (dbUsuarios.validarCredenciales(correo, contraseña)) {
                    // Credenciales válidas, permitir el acceso

                    // Guardar el estado de la sesión en SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("is_logged_in", true);
                    editor.putString("user_email", correo);
                    editor.putString("password", contraseña);
                    editor.apply();

                    Intent intentLogin = new Intent(MainActivity.this, InicioActivity.class);
                    startActivity(intentLogin);
                } else {
                    // Credenciales incorrectas, mostrar mensaje de error
                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
