package co.edu.ue.unicalendar.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import co.edu.ue.unicalendar.EditarPerfilActivity;
import co.edu.ue.unicalendar.MainActivity;
import co.edu.ue.unicalendar.R;
import co.edu.ue.unicalendar.databinding.ActivityInicioBinding;
import co.edu.ue.unicalendar.db.DbUsuarios;

public class InicioActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioBinding binding;
    private TextView userEmailTextView;
    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarInicio.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top-level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Inicializar las referencias a los TextView donde mostrarás el correo y el nombre del usuario
        userEmailTextView = navigationView.getHeaderView(0).findViewById(R.id.Usermail);
        userNameTextView = navigationView.getHeaderView(0).findViewById(R.id.Username);

        // Obtener los datos de la sesión desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);
        String userEmail = sharedPreferences.getString("user_email", "");

        if (isLoggedIn) {
            // El usuario ha iniciado sesión, muestra el correo y el nombre en los TextViews
            userEmailTextView.setText(userEmail);
            DbUsuarios dbUsuarios = new DbUsuarios(InicioActivity.this);
            // Obtener el nombre del usuario desde la base de datos (suponiendo que DbUsuarios tiene un método getUserNameByEmail)
            String userName = dbUsuarios.getUserNameByEmail(userEmail);
            userNameTextView.setText(userName);
        } else {
            // El usuario no ha iniciado sesión, realiza alguna acción apropiada
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit_profile) {
            // Abrir el activity para editar el perfil
            Intent editarPerfilIntent = new Intent(this, EditarPerfilActivity.class);
            startActivity(editarPerfilIntent);
            return true;
        } else if (id == R.id.action_logout) {
            // Borrar el estado de la sesión en SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            // Redirigir al usuario al MainActivity
            Intent loginIntent = new Intent(this, MainActivity.class);
            startActivity(loginIntent);
            finish();  // Esto cerrará la actividad actual

            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
