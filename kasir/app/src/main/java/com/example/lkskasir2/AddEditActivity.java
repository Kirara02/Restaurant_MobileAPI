package com.example.lkskasir2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddEditActivity extends AppCompatActivity {
    EditText name,price,desc;
    AppCompatButton tombol;
    ImageButton back;
    int metode;
    String url = "http://10.0.2.2:44359/menu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        desc = findViewById(R.id.desc);
        back = findViewById(R.id.back);
        tombol = findViewById(R.id.tombol);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        if (intent.hasExtra("id")){
            name.setText(intent.getExtras().getString("name"));
            price.setText(intent.getExtras().getString("price"));
            desc.setText(intent.getExtras().getString("desc"));
            tombol.setText("Edit");
            metode = Request.Method.PUT;
            url = url +"/"+ intent.getExtras().getString("id");
        }else{
            tombol.setText("Add");
            metode = Request.Method.POST;
        }
        tombol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length()==0){
                    name.setError("Masukan Name");
                }else  if (price.getText().toString().length()==0){
                    price.setError("Masukan price");
                }else  if (desc.getText().toString().length()==0){
                    desc.setError("Masukan Description");
                }else {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(metode, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (intent.hasExtra("id")){
                                Toast.makeText(AddEditActivity.this, "Data berhasil diedit", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MenuActivity.class));
                            }else{
                                Toast.makeText(AddEditActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MenuActivity.class));
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/x-www-form-urlencoded; charset=UTF-8";
                        }

                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            if (intent.hasExtra("id")){
                                params.put("id",intent.getExtras().getString("id"));
                            }
                            params.put("name", name.getText().toString());
                            params.put("price", price.getText().toString());
                            params.put("description", desc.getText().toString());
                            return params;
                        }
                    };
                    requestQueue.getCache().clear();
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
}