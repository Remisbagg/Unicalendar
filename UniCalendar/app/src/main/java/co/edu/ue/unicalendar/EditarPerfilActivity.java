package co.edu.ue.unicalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.edu.ue.unicalendar.db.DbUsuarios;

public class EditarPerfilActivity extends AppCompatActivity {

    private EditText nombreEditText;
    private EditText correoEditText;
    private Button editarNombreButton;
    private Button editarCorreoButton;
    private ImageView fotoPerfilImageView;
    private Button cargarFotoButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imagenSeleccionadaUri;
    private ImageView backButton;
    private Button backupButton;
    private Button seeBackUps;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);


        // Obtén referencias a los elementos en el diseño
        nombreEditText = findViewById(R.id.etNom);
        correoEditText = findViewById(R.id.etCorreo);
        editarNombreButton = findViewById(R.id.editName);
        editarCorreoButton = findViewById(R.id.editEmail);
        fotoPerfilImageView = findViewById(R.id.fotoPerfil);
        cargarFotoButton = findViewById(R.id.uploadPhoto);
        backButton = findViewById(R.id.backButton);
        backupButton=findViewById(R.id.savebackup);
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        DbUsuarios dbUsuarios = new DbUsuarios(EditarPerfilActivity.this);
        String userEmail = sharedPreferences.getString("user_email", "");
        String userPassword = sharedPreferences.getString("password", "");
        String userName = dbUsuarios.getUserNameByEmail(userEmail);
        nombreEditText.setText(userName);
        correoEditText.setText(userEmail);
        seeBackUps = findViewById(R.id.seeBackUps);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        // Configura OnClickListener para el botón de editar nombre
        editarNombreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén el nuevo nombre desde el EditText
                String nuevoNombre = nombreEditText.getText().toString();

                // Actualiza el nombre en la base de datos (reemplaza con tu lógica real)
                actualizarNombreEnBaseDeDatos(nuevoNombre);

                // Opcional: Muestra un mensaje de éxito o realiza cualquier otra acción necesaria
                Toast.makeText(EditarPerfilActivity.this, "Nombre actualizado correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura OnClickListener para el botón de editar correo
        editarCorreoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén el nuevo correo desde el EditText
                String nuevoCorreo = correoEditText.getText().toString();

                // Actualiza el correo en la base de datos (reemplaza con tu lógica real)
                actualizarCorreoEnBaseDeDatos(nuevoCorreo);

                // Opcional: Muestra un mensaje de éxito o realiza cualquier otra acción necesaria
                Toast.makeText(EditarPerfilActivity.this, "Correo actualizado correctamente", Toast.LENGTH_SHORT).show();
            }
        });
        cargarFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSelectorDeImagen();

            }
        });
        backupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nombre = nombreEditText.getText().toString();
                String Correo = correoEditText.getText().toString();

                backuperfil(Nombre,Correo,userPassword);
            }
        });
        seeBackUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent seeBackup = new Intent(EditarPerfilActivity.this, seeBackup.class);
                startActivity(seeBackup);
            }
        });
    }

    // Método para actualizar el nombre en la base de datos (reemplaza con tu lógica real)
    private void actualizarNombreEnBaseDeDatos(String nuevoNombre) {
        // Obtén el ID del usuario de alguna manera (por ejemplo, desde SharedPreferences)
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        DbUsuarios dbUsuarios = new DbUsuarios(EditarPerfilActivity.this);
        String userEmail = sharedPreferences.getString("user_email", "");
        String ID = dbUsuarios.getIdByEmail(userEmail);

        // Llama a la función editarUsuario para actualizar el nombre
        boolean actualizacionExitosa = dbUsuarios.editarUsuario(Integer.parseInt(ID), nuevoNombre, userEmail);

        if (actualizacionExitosa) {
            // Actualización exitosa, realiza alguna acción si es necesario
            Toast.makeText(this, "Nombre actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            // Error en la actualización, maneja el error de acuerdo a tus necesidades
            Toast.makeText(this, "Error al actualizar el nombre", Toast.LENGTH_SHORT).show();
        }
    }
    private void backuperfil(String nuevoNombre, String nuevoCorreo, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        DbUsuarios dbUsuarios = new DbUsuarios(EditarPerfilActivity.this);
        String userEmail = sharedPreferences.getString("user_email", "");
        String ID = dbUsuarios.getIdByEmail(userEmail);

        // Realiza una solicitud GET para verificar si el usuario existe
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
                                    // El usuario con el mismo ID existe, realiza una solicitud PUT para actualizarlo
                                    realizarSolicitudPUT(ID, nuevoNombre, nuevoCorreo, password);
                                    return;
                                }
                            }

                            // Si llega hasta aquí, significa que no se encontró el usuario con el ID buscado
                            // Realiza una solicitud POST para crearlo
                            realizarSolicitudPOST(ID, nuevoNombre, nuevoCorreo, password);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja los errores de la solicitud GET aquí
                        Toast.makeText(EditarPerfilActivity.this, "Error al verificar el usuario", Toast.LENGTH_SHORT).show();
                    }
                });

        // Agrega la solicitud GET a la cola de Volley
        Volley.newRequestQueue(this).add(getRequest);
    }




    private void realizarSolicitudPOST(String userID,String nuevoNombre, String nuevoCorreo, String password) {
        // Realiza una solicitud POST para crear un nuevo usuario
        String urlPost = "https://remiapi.azurewebsites.net/myapi/controller/user/controller.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Procesa la respuesta del servidor aquí
                        Log.d("Solicitud POST", "Respuesta del servidor: " + response);
                        Toast.makeText(EditarPerfilActivity.this, "Carga de Datos exitosa", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja los errores de la solicitud POST aquí
                        Log.e("Solicitud POST", "Error al hacer la solicitud", error);
                        Toast.makeText(EditarPerfilActivity.this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Crea un mapa de parámetros (nombre, correo y contraseña)
                Map<String, String> params = new HashMap<>();
                params.put("id", userID);
                params.put("name", nuevoNombre);
                params.put("email", nuevoCorreo);
                params.put("password", password);


                // Registra los parámetros enviados en el log
                Log.d("Solicitud POST", "Parámetros enviados: " + params.toString());

                return params;
            }
        };

        // Agrega la solicitud POST a la cola de Volley
        Volley.newRequestQueue(this).add(postRequest);
    }


    private void realizarSolicitudPUT(String userID, String nuevoNombre, String nuevoCorreo, String password) {
        // Realiza una solicitud PUT para actualizar los datos del usuario
        String urlPut = "https://remiapi.azurewebsites.net/myapi/controller/user/controller.php?id=" + userID;
        StringRequest putRequest = new StringRequest(Request.Method.PUT, urlPut,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Procesa la respuesta del servidor aquí
                        Log.d("Solicitud PUT", "Respuesta del servidor: " + response);
                        Toast.makeText(EditarPerfilActivity.this, "Actualización de Datos exitosa", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja los errores de la solicitud PUT aquí
                        Log.e("Solicitud PUT", "Error al hacer la solicitud", error);
                        Toast.makeText(EditarPerfilActivity.this, "Error al actualizar el usuario", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                // Crea un mapa de parámetros (nombre, correo y contraseña)
                Map<String, String> params = new HashMap<>();
                params.put("id", userID);
                params.put("name", nuevoNombre);
                params.put("email", nuevoCorreo);
                params.put("password", password);

                // Convierte el mapa a una cadena JSON
                String jsonParams = new JSONObject(params).toString();

                // Registra los parámetros enviados en el log
                Log.d("Solicitud PUT", "Parámetros enviados: " + jsonParams);

                // Devuelve los parámetros como un array de bytes
                return jsonParams.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        // Agrega la solicitud PUT a la cola de Volley
        Volley.newRequestQueue(this).add(putRequest);
    }





    // Método para actualizar el correo en la base de datos
    private void actualizarCorreoEnBaseDeDatos(String nuevoCorreo) {
        // Obtén el ID del usuario de alguna manera (por ejemplo, desde SharedPreferences)
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        DbUsuarios dbUsuarios = new DbUsuarios(EditarPerfilActivity.this);
        String userEmail = sharedPreferences.getString("user_email", "");
        String userName = dbUsuarios.getUserNameByEmail(userEmail);
        String ID = dbUsuarios.getIdByEmail(userEmail);

        // Llama a la función editarUsuario para actualizar el correo
        boolean actualizacionExitosa = dbUsuarios.editarUsuario(Integer.parseInt(ID), userName, nuevoCorreo);

        if (actualizacionExitosa) {
            // Actualización exitosa, realiza alguna acción si es necesario
            Toast.makeText(this, "Correo actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            // Error en la actualización, maneja el error de acuerdo a tus necesidades
            Toast.makeText(this, "Error al actualizar el correo", Toast.LENGTH_SHORT).show();
        }
    }
    private void abrirSelectorDeImagen() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen de perfil"), PICK_IMAGE_REQUEST);
    }

    // Método para manejar el resultado de la selección de imágenes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagenSeleccionadaUri = data.getData();

            try {
                // Cargar la imagen seleccionada en el ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagenSeleccionadaUri);
                fotoPerfilImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
