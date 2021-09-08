package com.example.mecanimex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class Registro <Private> extends AppCompatActivity {

    TextView lblwelcome, txtregistro,textoyatienescuenta;
    ImageView imglogo;
    TextInputLayout nom, email, pass1, pass2, telefono;
    MaterialButton btninsert;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        imglogo = findViewById(R.id.imglogo);
        lblwelcome = findViewById(R.id.lblwelcome);
        txtregistro = findViewById(R.id.txtregistro);
        textoyatienescuenta = findViewById(R.id.textoyatienescuenta);
        nom = (TextInputLayout) findViewById(R.id.inputnombre);
        email = (TextInputLayout) findViewById(R.id.inputemail);
        pass1 = (TextInputLayout) findViewById(R.id.inputpass);
        pass2 = (TextInputLayout) findViewById(R.id.inputconfpass);
        telefono = (TextInputLayout) findViewById(R.id.inputtelefono);
        btninsert = (MaterialButton) findViewById(R.id.btnregistrar);

        textoyatienescuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transisicionregreso();
            }
        });

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("http://192.168.100.96/mecaBD/insertar_datos.php");
            }
        });

    }

    private void ejecutarServicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registrado con Exito", Toast.LENGTH_SHORT).show();
                limpiarFormulario();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", nom.getEditText().getText().toString());
                parametros.put("email", email.getEditText().getText().toString());
                parametros.put("passuno", pass1.getEditText().getText().toString());
                parametros.put("passdos", pass2.getEditText().getText().toString());
                parametros.put("telefono", telefono.getEditText().getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void limpiarFormulario(){
        nom.getEditText().setText("");
        email.getEditText().setText("");
        pass1.getEditText().setText("");
        pass2.getEditText().setText("");
        telefono.getEditText().setText("");
    }

    @Override
    public void onBackPressed(){
        transisicionregreso();
    }




    public void transisicionregreso (){
        Intent intent = new Intent(Registro.this,Login.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(imglogo, "imgregistroTrans");
        pairs[1] = new Pair<View, String>(lblwelcome, "BienvenidoTrans");
        pairs[2] = new Pair<View, String>(txtregistro, "RegTrans");
        pairs[3] = new Pair<View, String>(textoyatienescuenta, "userTrans");
        pairs[4] = new Pair<View, String>(email, "emailTrans");
        pairs[5] = new Pair<View, String>(pass1, "passTrans");
        pairs[6] = new Pair<View, String>(btninsert, "btnregistroTrans");


        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Registro.this,pairs);
            startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);
            finish();
        }

    }

    public void registro (View view){
        Intent registrar = new Intent(getApplicationContext(),Login.class);
        startActivity(registrar);
    }

//LAVE FINAL
}