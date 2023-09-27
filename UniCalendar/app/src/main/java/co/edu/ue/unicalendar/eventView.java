package co.edu.ue.unicalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import co.edu.ue.unicalendar.db.DbUsuarios;

public class eventView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        // Obtén los extras del Intent
        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("selected_date");
        String nombreEvento = intent.getStringExtra("nombre_evento");

        // Usa nombreEvento para buscar los datos del evento en la base de datos
        DbUsuarios dbEventos = new DbUsuarios(eventView.this);
        Evento evento = dbEventos.getEventoPorNombre(nombreEvento);

        // Llena los campos de texto con los datos del evento
        TextView tvFecha = findViewById(R.id.tv_fecha);
        TextView tvClase = findViewById(R.id.tv_clase);
        TextView tvHorai = findViewById(R.id.tv_horaI);
        TextView tvHoraF = findViewById(R.id.tv_horaF);
        TextView tvProfesor = findViewById(R.id.tv_profesor);
        TextView tvDetalle = findViewById(R.id.tv_detalle);

        tvFecha.setText(selectedDate);
        tvClase.setText(evento.getClase());
        tvHorai.setText(evento.getHora_inicio());
        tvHoraF.setText(evento.getHora_final());
        tvProfesor.setText(evento.getProfesor());
        tvDetalle.setText(evento.getSalon());
    }


}