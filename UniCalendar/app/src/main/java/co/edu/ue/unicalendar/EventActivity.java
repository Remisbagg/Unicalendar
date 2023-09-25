package co.edu.ue.unicalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EventActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Obtiene la fecha seleccionada del Intent.
        String selectedDate = getIntent().getStringExtra("selected_date");

        // Ahora puedes utilizar la fecha seleccionada para cargar y mostrar eventos relacionados con esa fecha.
        // Por ejemplo, puedes usar una base de datos o una lista de eventos.

        // En esta actividad, puedes mostrar los eventos en un RecyclerView o cualquier otro diseño que desees.
    }
}
