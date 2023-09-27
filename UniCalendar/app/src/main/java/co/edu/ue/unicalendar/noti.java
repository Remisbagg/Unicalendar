package co.edu.ue.unicalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import co.edu.ue.unicalendar.db.DbUsuarios;

public class noti extends AppCompatActivity {
    private TextView fechaTv;
    private EditText Hora;
    private EditText Recordatorio;
    private Button guardarBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti);

        // Obtén la fecha seleccionada del Intent.
        String selectedDate = getIntent().getStringExtra("selected_date");

        // Encuentra los Views y establece la fecha seleccionada como el texto del TextView.
        fechaTv = findViewById(R.id.tv_fecha);
        fechaTv.setText(selectedDate);
        Hora = findViewById(R.id.et_hora);
        Recordatorio=findViewById(R.id.et_reco);
        guardarBtn = findViewById(R.id.btn_guardar);

        // Abre un TimePickerDialog cuando se hace clic en el EditText de la hora
        Hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(noti.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Establece la hora seleccionada en el EditText de la hora
                                Hora.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, hour, minute, true);

                timePickerDialog.show();
            }
        });

        // Maneja el clic del botón "Guardar".
        guardarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén los detalles del evento de los EditTexts.
                String hora = Hora.getText().toString();
                String recordatorio = Recordatorio.getText().toString();

                // Inserta el evento en la base de datos.
                DbUsuarios dbEventos = new DbUsuarios(noti.this);
                long id = dbEventos.insertarReco(selectedDate, hora,recordatorio);

                if (id > 0) {
                    Toast.makeText(noti.this, "Recordatorio guardado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(noti.this, "Error al guardar Recordatorio", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
