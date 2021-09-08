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
//LIBNEW
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
//

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class Login <Private> extends AppCompatActivity {

    TextView lblbienvenido, lblnota, lblregistro;
    ImageView imglogoTrans;
    private TextInputLayout lblusuario, lblpassword;
    //MaterialButton btninicio;
    TextInputLayout email,pass1;
    MaterialButton btnselect;
    RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lblbienvenido = findViewById(R.id.lblbienvenido);
        lblnota = findViewById(R.id.lblnota);
        lblregistro = findViewById(R.id.lblregistro);
        imglogoTrans = findViewById(R.id.imglogoTrans);

        email = findViewById(R.id.lblusuario);
        pass1 = findViewById(R.id.lblpassword);
        btnselect = findViewById(R.id.btninicio);

        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarDatos("http://192.168.100.96/mecaBD/buscar_datos.php?passuno=" + pass1.getEditText().getText() + "");
            }
        });

        //SE REALIZAN LAS TRANSACCIONES Y SE ENTRA AL LA PANTALLA PRINCIPAL.
        lblregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),Registro.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(imglogoTrans, "imgregistroTrans");
                pairs[1] = new Pair<View, String>(lblbienvenido, "BienvenidoTrans");
                pairs[2] = new Pair<View, String>(lblregistro, "RegTrans");
                pairs[3] = new Pair<View, String>(lblnota, "userTrans");
                pairs[4] = new Pair<View, String>(lblusuario, "emailTrans");
                pairs[5] = new Pair<View, String>(lblpassword, "passTrans");
                pairs[6] = new Pair<View, String>(btnselect, "btnregistroTrans");



                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                    //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                    //startActivity(intent,options.toBundle());
                    startActivity(intent);
                }else{
                    startActivity(intent);
                    finish();
                }


            }
        });

    }

        private void buscarDatos(String URL){
            JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            Toast.makeText(getApplicationContext(),"Bienvenido " + email.getEditText().getText(), Toast.LENGTH_SHORT).show();
                            Intent pasarpantalla = new Intent(getApplicationContext(),Principal.class);
                            startActivity(pasarpantalla);
                            //email.getEditText().setText(jsonObject.getString("email"));
                            //pass1.getEditText().setText(jsonObject.getString("passuno"));
                        } catch (JSONException e) {
                            Intent pasarpantalla = new Intent(getApplicationContext(),Principal.class);
                            startActivity(pasarpantalla);
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "No existe ese usuario, registrate o bien revisa tus datos.", Toast.LENGTH_SHORT).show();
                    limpiarFormulario();
                }
            }
            );

            requestQueue=Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);
        }

    private void limpiarFormulario(){
        email.getEditText().setText("");
        pass1.getEditText().setText("");
    }




    public boolean validaremail () {

        String email = lblusuario.getEditText().getText().toString().trim();

        if (email.isEmpty()) {
            lblusuario.setError("Ingresa los campos");
            return false;
        } else {

            lblusuario.setError(null);
            return true;
        }

    }

    public boolean validarpass () {

        String email = lblpassword.getEditText().getText().toString().trim();

        if (email.isEmpty()) {
            lblpassword.setError("Ingresa los campos");
            return false;
        } else {

            lblusuario.setError(null);
            return true;
        }

    }


//VALIDACION DEL FORMULARIO

    public void validar (View view){
        if(!validaremail() | !validarpass()){
            Toast.makeText(this,"Revisa tus campos para ingresar",Toast.LENGTH_SHORT).show();
            return;
        }else{
            Intent pasarpantalla = new Intent(getApplicationContext(),Principal.class);
            startActivity(pasarpantalla);
        }

    }


//LLAVE FINAL
}

