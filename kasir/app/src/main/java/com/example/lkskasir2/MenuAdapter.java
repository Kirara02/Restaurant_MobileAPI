package com.example.lkskasir2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class MenuAdapter extends ArrayAdapter<MenuModel> {
    private Context context;
    private int resource;
    private List<MenuModel> menuModelList;
    private AlertDialog.Builder adb;

    public MenuAdapter(@NonNull Context context, int resource, @NonNull List<MenuModel> menuModelList, AlertDialog.Builder adb) {
        super(context, resource, menuModelList);
        this.context = context;
        this.resource = resource;
        this.menuModelList = menuModelList;
        this.adb = adb;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(resource,null,false);
        }
        TextView name = convertView.findViewById(R.id.name);
        TextView price = convertView.findViewById(R.id.price);
        TextView desc = convertView.findViewById(R.id.desc);
        ImageView edit = convertView.findViewById(R.id.edit);
        ImageView delete = convertView.findViewById(R.id.delete);

        MenuModel menuModel = menuModelList.get(position);
        name.setText(menuModel.getName());
        price.setText(menuModel.getPrice());
        desc.setText(menuModel.getDesc());
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddEditActivity.class);
                intent.putExtra("id",menuModel.getId());
                intent.putExtra("name",menuModel.getName());
                intent.putExtra("price",menuModel.getPrice());
                intent.putExtra("desc",menuModel.getDesc());
                context.startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adb.setTitle("Delete");
                adb.setMessage("Hapus data ini?");
                adb.setCancelable(false);
                adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest   stringRequest = new StringRequest(Request.Method.DELETE, "http://10.0.2.2:44359/menu/" + menuModel.getId(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(getContext(),MenuActivity.class));
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });
                        requestQueue.getCache().clear();
                        requestQueue.add(stringRequest);
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
        });
        return  convertView;
    }
}
