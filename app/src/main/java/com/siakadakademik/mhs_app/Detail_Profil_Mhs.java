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
 * Created by xyzz on 4/18/2018.
 */

public class Detail_Profil_Mhs extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    TextView nim, nama_mahasiswa, fakultas, prodi, periode, kelas,status, tgl_lahir;
    SwipeRefreshLayout swipe;
    String id_mhs;
    Intent intent;
    SharedPreferences sharedPreferences;

    private static final String TAG = Detail_Profil_Mhs.class.getSimpleName();

    public static final String TAG_ID_MHS ="id";
    public static final String TAG_ID_NAMA ="nama";
    public static final String TAG_FAKULTAS ="fakultas";
    public static final String TAG_PRODI ="prodi";
    public static final String TAG_PERIODE ="periode";
    public static final String TAG_KELAS ="kelas";
    public static final String TAG_STATUS = "status";
    public static final String TAG_TGL_LAHIR = "tgl_lahir";

    private static final String url_detail  = Server.URL + "detail_profil_mhs.php";
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_profilmhs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detail mhs");

        nim = (TextView) findViewById(R.id.nim);
        nama_mahasiswa = (TextView) findViewById(R.id.nama_mahasiswa);
        fakultas = (TextView) findViewById(R.id.fakultas);
        prodi = (TextView) findViewById(R.id.prodi);
        periode = (TextView) findViewById(R.id.periode);
        kelas = (TextView) findViewById(R.id.kelas);
        status = (TextView) findViewById(R.id.status);
        tgl_lahir = (TextView) findViewById(R.id.tgl_lahir);

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

                    String Nim = getIntent().getStringExtra(TAG_ID_MHS);
                    String Nama_mahasiswa = obj.getString(TAG_ID_NAMA);
                    String Fakultas = obj.getString(TAG_FAKULTAS);
                    String Prodi = obj.getString(TAG_PRODI);
                    String Periode = obj.getString(TAG_PERIODE);
                    String Kelas = obj.getString(TAG_KELAS);
                    String Status = obj.getString(TAG_STATUS);
                    String Tgl_lahir =obj.getString(TAG_TGL_LAHIR);

                    nim.setText(Nim);
                    nama_mahasiswa.setText(Nama_mahasiswa);
                    fakultas.setText(Fakultas);
                    prodi.setText(Prodi);
                    periode.setText(Periode);
                    kelas.setText(Kelas);
                    status.setText(Status);
                    tgl_lahir.setText(Tgl_lahir);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Detail Mhs Error: " + error.getMessage());
                Toast.makeText(Detail_Profil_Mhs.this, "", Toast.LENGTH_SHORT).show();
                swipe.setRefreshing(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("nim", id_mhs);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
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
