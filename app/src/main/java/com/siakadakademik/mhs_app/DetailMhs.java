package com.siakadakademik.mhs_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.siakadakademik.mhs_app.app.AppController;
import com.siakadakademik.mhs_app.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyzz on 11/17/2017.
 */

public class DetailMhs extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    TextView nama, alamat, telepon, semester, jenis_kelamin,status;
    SwipeRefreshLayout swipe;
    String id_mhs;
    Intent intent;
    SharedPreferences sharedPreferences;

    private static final String TAG = DetailMhs.class.getSimpleName();

    public static final String TAG_ID_MHS ="id_mhs";
    public static final String TAG_ID_NAMA ="nama_mhs";
    public static final String TAG_ALAMAT ="alamat";
    public static final String TAG_TELEPON ="telepon";
    public static final String TAG_SEMESTER ="semester";
    public static final String TAG_JENIS_KELAMIN ="jenis_kelamin";
    public static final String TAG_STATUS = "status";

    private static final String url_detail  = Server.URL + "detail_mhs.php";
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detail mhs");

        nama = (TextView) findViewById(R.id.nama_mhs);
        alamat = (TextView) findViewById(R.id.alamat);
        telepon = (TextView) findViewById(R.id.telepon);
        semester = (TextView) findViewById(R.id.semester);
        jenis_kelamin = (TextView) findViewById(R.id.jenis_kelamin);
        status = (TextView) findViewById(R.id.status);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        sharedPreferences =getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);


        id_mhs = getIntent().getStringExtra(TAG_ID_MHS);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
            @Override
            public void run() {
                if (!id_mhs.isEmpty()){
                    callDetailMhs(id_mhs);
                }
            }
        }
        );
    }

    private void callDetailMhs(final String id_mhs) {
        swipe.setRefreshing(true);

        StringRequest strReq = new StringRequest(Request.Method.POST, url_detail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response.toString());
                swipe.setRefreshing(false);

                try {
                    JSONObject obj = new JSONObject(response);

                    String Nama = obj.getString(TAG_ID_NAMA);
                    String Alamat = obj.getString(TAG_ALAMAT);
                    String Telepon = obj.getString(TAG_TELEPON);
                    String Semester = obj.getString(TAG_SEMESTER);
                    String Jenis_kelamin = obj.getString(TAG_JENIS_KELAMIN);
                    String Status = obj.getString(TAG_STATUS);

                    nama.setText(Nama);
                    alamat.setText(Alamat);
                    telepon.setText(Telepon);
                    semester.setText(Semester);
                    jenis_kelamin.setText(Jenis_kelamin);
                    status.setText(Status);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Detail Mhs Error: " + error.getMessage());
                Toast.makeText(DetailMhs.this, "", Toast.LENGTH_SHORT).show();
                swipe.setRefreshing(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_mhs", id_mhs);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(DetailMhs.this, DrawActivity.class);
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
            this.finish();
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        callDetailMhs(id_mhs);
    }
}
