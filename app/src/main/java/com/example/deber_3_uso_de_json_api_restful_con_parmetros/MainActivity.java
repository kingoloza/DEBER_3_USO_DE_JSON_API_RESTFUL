package com.example.deber_3_uso_de_json_api_restful_con_parmetros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void btconsulta (View view){
        EditText user = (EditText)findViewById(R.id.txtnombre);
        EditText clav = (EditText)findViewById(R.id.txtclave);
        String usuarios, clave;
        usuarios = user.getText().toString();
        clave = clav.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/login";
        JSONObject params = new JSONObject();
        try {
            params.put("correo", usuarios);
            params.put("clave", clave);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String token = response.getString("access_token");
                            Bundle send =new Bundle();
                            // extraer token de respuesta
                            send.putString("acesso_token", token);
                            Intent intent = new Intent(MainActivity.this, Volley2.class);
                            intent.putExtras(send);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Mostramos el mensaje de error
                        TextView alerta = (TextView)findViewById(R.id.textView4);
                        alerta.setText("Datos incorrectos");
                    }
                });
        queue.add(request);

    }

    public  void btlimpiar (View view){

        EditText user = (EditText)findViewById(R.id.txtnombre);
        EditText clav = (EditText)findViewById(R.id.txtnombre);
        user.setText("");
        clav.setText("");
    }
}