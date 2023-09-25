package co.edu.ue.unicalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.edu.ue.unicalendar.activity.InicioActivity;

public class MainActivity extends AppCompatActivity {
    TextView tv_registrar;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Intent intentLogin =new Intent(MainActivity.this, InicioActivity.class);
                MainActivity.this.startActivity(intentLogin);
            }

        });
    }
}
