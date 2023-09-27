package co.edu.ue.unicalendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import co.edu.ue.unicalendar.db.DbUsuarios;

public class seeEvents extends AppCompatActivity {
    private LinearLayout container;
    private Button btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_events);

        container = findViewById(R.id.container);

        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("selected_date");

        DbUsuarios dbEventos = new DbUsuarios(seeEvents.this);
        List<Evento> eventos = dbEventos.getEventosPorFecha(selectedDate);

        // Infla un nuevo LinearLayout por cada evento
        // Infla un nuevo LinearLayout por cada evento
        for (Evento evento : eventos) {
            View view = getLayoutInflater().inflate(R.layout.event_item, null);

            TextView nombreTv = view.findViewById(R.id.etNom);
            nombreTv.setText(evento.getProfesor());

            String className = evento.getProfesor();

            // Crea una nueva referencia al botón para cada evento
            Button btnView = view.findViewById(R.id.seeEvent);

            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent seeEvent = new Intent(seeEvents.this, eventView.class);
                    seeEvent.putExtra("selected_date", selectedDate);
                    seeEvent.putExtra("nombre_evento", className);
                    startActivity(seeEvent);
                }
            });

            container.addView(view);
        }

    }
}
