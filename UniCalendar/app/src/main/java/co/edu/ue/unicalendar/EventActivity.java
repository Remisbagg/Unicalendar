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

public class EventActivity extends AppCompatActivity {
    private TextView fechaTv;
    private EditText claseEt;
    private EditText horaEt;
    private EditText horaFin;
    private EditText profesorEt;
    private EditText detalleEt;
    private Button guardarBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Obtén la fecha seleccionada del Intent.
        String selectedDate = getIntent().getStringExtra("selected_date");

        // Encuentra los Views y establece la fecha seleccionada como el texto del TextView.
        fechaTv = findViewById(R.id.tv_fecha);
        fechaTv.setText(selectedDate);
        claseEt = findViewById(R.id.et_clase);
        horaEt = findViewById(R.id.et_hora);
        horaFin=findViewById(R.id.et_horaF);
        profesorEt = findViewById(R.id.et_profesor);
        detalleEt = findViewById(R.id.et_detalle);
        guardarBtn = findViewById(R.id.btn_guardar);

        // Abre un TimePickerDialog cuando se hace clic en el EditText de la hora
        horaEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Establece la hora seleccionada en el EditText de la hora
                                horaEt.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, hour, minute, true);

                timePickerDialog.show();
            }
        });
        horaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Establece la hora seleccionada en el EditText de la hora
                                horaFin.setText(String.format("%02d:%02d", hourOfDay, minute));
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
                String clase = claseEt.getText().toString();
                String horaI = horaEt.getText().toString();
                String horaf = horaFin.getText().toString();
                String profesor = profesorEt.getText().toString();
                String detalle = detalleEt.getText().toString();

                // Inserta el evento en la base de datos.
                DbUsuarios dbEventos = new DbUsuarios(EventActivity.this);
                long id = dbEventos.insertarEvento(selectedDate, horaI, horaf, clase, profesor,detalle);

                if (id > 0) {
                    Toast.makeText(EventActivity.this, "Evento guardado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EventActivity.this, "Error al guardar el evento", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
