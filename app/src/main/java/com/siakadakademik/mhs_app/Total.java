package com.siakadakademik.mhs_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.siakadakademik.mhs_app.app.MySingleton;
import com.siakadakademik.mhs_app.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xyzz on 12/12/2017.
 */

public class Total extends AppCompatActivity {
    TextView jumlah_data, jumlah_data2;


    private static String url_list = Server.URL + "count_mhs.php";
    private static String url_list2 = Server.URL + "count_mhs2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total);

        jumlah_data = (TextView)findViewById(R.id.jumlah_data);
        jumlah_data2 = (TextView)findViewById(R.id.jumlah_data2) ;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_list, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            jumlah_data.setText(response.getString("jumlah_data"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Total.this, "Kuntull", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url_list2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            jumlah_data2.setText(response.getString("jumlah_data2"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Total.this,"Kuntul", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        MySingleton.getInstance(Total.this).addToRequestque(jsonObjectRequest);
        MySingleton.getInstance(Total.this).addToRequestque(jsonObjectRequest1);

    }

}
