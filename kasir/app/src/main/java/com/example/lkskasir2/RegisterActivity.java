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

public class RegisterActivity extends AppCompatActivity {
    EditText email,pass,confirm;
    AppCompatButton regist;
    ImageButton back;
    String url = "http://10.0.2.2:44359/register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        confirm = findViewById(R.id.confirm);
        back = findViewById(R.id.back);
        regist = findViewById(R.id.regist);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().length()==0){
                    email.setError("Masukan Email");
                }else  if (pass.getText().toString().length()==0){
                    pass.setError("Masukan Password");
                }else  if (confirm.getText().toString().length()==0){
                    confirm.setError("Masukan Password Konfirmasi");
                }else {
                    if (confirm.getText().toString().equals(pass.getText().toString())){
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject.getString("status").equals("success")){
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        Toast.makeText(RegisterActivity.this, "Berhasil ,mendaftar", Toast.LENGTH_SHORT).show();
                                    }else if(jsonObject.getString("status").equals("failed")){
                                        Toast.makeText(RegisterActivity.this, "Email ini sudah terdaftar", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
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
                                params.put("email", email.getText().toString());
                                params.put("password", pass.getText().toString());
                                return params;
                            }
                        };
                        requestQueue.getCache().clear();
                        requestQueue.add(stringRequest);
                    }else{
                        Toast.makeText(RegisterActivity.this, "Password tidak sesuai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}