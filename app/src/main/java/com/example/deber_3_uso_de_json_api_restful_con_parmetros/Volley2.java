package com.example.deber_3_uso_de_json_api_restful_con_parmetros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Volley2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley2);

        TextView muestra = (TextView)findViewById(R.id.textView5);


        Bundle send=this.getIntent().getExtras();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/clientes/search";
        JSONObject params = new JSONObject();
        try {
            params.put("fuente", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        //guardar datos
                        String guardar ="";
                        try {
                            JSONArray lis = response.getJSONArray("clientes");
                            for(int i=0; i< lis.length();i++) {
                                JSONObject cliente = lis.getJSONObject(i);
                                guardar = guardar + cliente.getString("identificacion").toString()+
                                        " "+ cliente.getString("nombre").toString()+"\n";
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        muestra.setText(guardar);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        muestra.setText("Sin Datos Para Mostrar");

                    }
                }) {
            @Override
            // De esta forma se puede enviar el token para poder realizar la consulta
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer"+ " " + send.getString("acesso_token"));
                return headers;
            }
        };

        queue.add(request);

    }

}