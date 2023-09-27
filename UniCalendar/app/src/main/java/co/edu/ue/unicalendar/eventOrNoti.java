package co.edu.ue.unicalendar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.edu.ue.unicalendar.EventActivity;
import co.edu.ue.unicalendar.R;
import co.edu.ue.unicalendar.db.DbUsuarios;

public class eventOrNoti extends AppCompatActivity {
    private Button verEventosBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_or_noti);

        Button addEventButton = findViewById(R.id.addEvent);
        Button addReco = findViewById(R.id.addReco);
        verEventosBtn = findViewById(R.id.btn_ver_eventos);
        DbUsuarios dbEventos = new DbUsuarios(eventOrNoti.this);
        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("selected_date");
        boolean existeEvento = dbEventos.existeEventoConFecha(selectedDate);
        if (existeEvento) {
            // Si existe un evento con la fecha seleccionada, haz visible el botón "Ver eventos"
            verEventosBtn.setVisibility(View.VISIBLE);
        }
        addReco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtenemos el valor de "selected_date" del Intent actual
                Intent intent = getIntent();
                if (intent != null) {
                    String selectedDate = intent.getStringExtra("selected_date");

                    // Luego, iniciamos la actividad EventActivity pasando la fecha seleccionada
                    Intent recoIntent = new Intent(eventOrNoti.this, noti.class);
                    recoIntent.putExtra("selected_date", selectedDate);
                    startActivity(recoIntent);
                }
            }
        });
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtenemos el valor de "selected_date" del Intent actual
                Intent intent = getIntent();
                if (intent != null) {
                    String selectedDate = intent.getStringExtra("selected_date");

                    // Luego, iniciamos la actividad EventActivity pasando la fecha seleccionada
                    Intent eventIntent = new Intent(eventOrNoti.this, EventActivity.class);
                    eventIntent.putExtra("selected_date", selectedDate);
                    startActivity(eventIntent);
                }
            }
        });
        verEventosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inicia la actividad seeEvents
                Intent seeEventsIntent = new Intent(eventOrNoti.this, seeEvents.class);
                seeEventsIntent.putExtra("selected_date", selectedDate);
                startActivity(seeEventsIntent);
            }
        });

    }
}
