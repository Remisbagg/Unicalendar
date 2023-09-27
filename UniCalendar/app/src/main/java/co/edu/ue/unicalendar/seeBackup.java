package co.edu.ue.unicalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.edu.ue.unicalendar.db.DbUsuarios;

public class seeBackup extends AppCompatActivity {
    private TextView nombreTv;
    private TextView correoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_backup);

        nombreTv = findViewById(R.id.tvNom);
        correoTv = findViewById(R.id.tvCorreo);
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        DbUsuarios dbUsuarios = new DbUsuarios(seeBackup.this);
        String userEmail = sharedPreferences.getString("user_email", "");
        String ID = dbUsuarios.getIdByEmail(userEmail);

        // Realiza una solicitud GET para obtener los datos del usuario
        String urlGet = "https://remiapi.azurewebsites.net/myapi/controller/user/controller.php?id=" + ID;
        StringRequest getRequest = new StringRequest(Request.Method.GET, urlGet,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Elimina "GET" de la respuesta
                            String jsonResponse = response.replace("GET", "");

                            // Convierte la respuesta a un JSONArray
                            JSONArray jsonArray = new JSONArray(jsonResponse);

                            // Recorre cada objeto en el array
                            for (int i = 0; i < jsonArray.length(); i++) {
                                // Obtiene el objeto JSON en el índice actual
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                // Verifica si el ID del objeto JSON es igual al ID buscado
                                if (jsonObject.getString("id").equals(ID)) {
                                    // Establece el nombre y el correo del usuario en los TextViews
                                    nombreTv.setText(jsonObject.getString("name"));
                                    correoTv.setText(jsonObject.getString("email"));
                                    return;
                                }
                            }

                            // Si llega hasta aquí, significa que no se encontró el usuario con el ID buscado
                            Toast.makeText(seeBackup.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja los errores de la solicitud GET aquí
                        Toast.makeText(seeBackup.this, "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show();
                    }
                });

        // Agrega la solicitud GET a la cola de Volley
        Volley.newRequestQueue(this).add(getRequest);
    }

}