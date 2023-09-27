package co.edu.ue.unicalendar.activity.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import co.edu.ue.unicalendar.EventActivity; // Asegúrate de importar la clase EventActivity
import co.edu.ue.unicalendar.R;
import co.edu.ue.unicalendar.databinding.FragmentHomeBinding;
import co.edu.ue.unicalendar.eventOrNoti;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        // Encuentra el CalendarView y configura el OnDateChangeListener
        CalendarView calendarView = root.findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Aquí obtén la fecha seleccionada en el formato que prefieras.
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;

                // Crea un Intent para abrir la actividad de eventos y pasa la fecha seleccionada como extra.
                Intent intent = new Intent(getActivity(), eventOrNoti.class);
                intent.putExtra("selected_date", selectedDate);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}