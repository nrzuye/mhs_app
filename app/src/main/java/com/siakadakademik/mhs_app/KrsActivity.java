package com.siakadakademik.mhs_app;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.siakadakademik.mhs_app.adapter.Krs_Adapter;
import com.siakadakademik.mhs_app.app.AppController;
import com.siakadakademik.mhs_app.data.KrsDataList;
import com.siakadakademik.mhs_app.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KrsActivity extends AppCompatActivity{
    ProgressBar progressBar;
    Krs_Adapter krs_adapter;
    List<KrsDataList> listkrs = new ArrayList<KrsDataList>();
    String id_mhs,url;
    RecyclerView recyclerView;
    LinearLayout empty_state;
    TextView msg_no_krs,txtback;

    private static final String TAG = KrsActivity.class.getSimpleName();

    public static final String TAG_ID_MHS = "id";
    public static final String TAG_NIM = "nim";
    public static final String TAG_NAMA_MHS = "nama_mahasiswa";
    public static final String TAG_KODEMK = "kodemk";
    public static final String TAG_NAMAMK = "namamk";
    public static final String TAG_SKSMK = "sksmk";
    public static final String TAG_NAMA_KELAS = "nama_kelas";
    public static final String TAG_PENGAMPU = "pengampu";
    public static final String TAG_IDPERIODE = "idperiode";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krs_card);
        setTitle("Krs gan");

        recyclerView = findViewById(R.id.recycler_view_krs);
        empty_state = findViewById(R.id.empty_state);
        progressBar = findViewById(R.id.progressBar);
        msg_no_krs = findViewById(R.id.no_item_msg_krs);
        txtback = findViewById(R.id.textback);

        id_mhs = getIntent().getStringExtra(TAG_ID_MHS);
        url = Server.URL+"krs.php?nim="+id_mhs;

        final Toolbar toolbar = findViewById(R.id.toolbar_collapsing);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Krs");
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(3), true));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        callData();

        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void callData() {
        listkrs.clear();
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jArrcoy = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

                if (response.length() != 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            KrsDataList item = new KrsDataList();

                            item.setNim(object.getString(TAG_NIM));
                            item.setNama_mahasiswa(object.getString(TAG_NAMA_MHS));
                            item.setKodemk(object.getString(TAG_KODEMK));
                            item.setNamamk(object.getString(TAG_NAMAMK));
                            item.setSksmk(object.getString(TAG_SKSMK));
                            item.setNamakelas(object.getString(TAG_NAMA_KELAS));
                            item.setPengampu(object.getString(TAG_PENGAMPU));
                            item.setIdperiode(object.getString(TAG_IDPERIODE));

                            listkrs.add(item);
                            } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    setAdapterToRecyclerCoy();
                    progressBar.setVisibility(View.GONE);

                }else {
                    empty_state.setVisibility(View.VISIBLE);
                    msg_no_krs.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG,"Ereor" + error.getMessage());
                empty_state.setVisibility(View.VISIBLE);
            }
        });

        AppController.getInstance().addToRequestQueue(jArrcoy);
    }

    private void setAdapterToRecyclerCoy() {
        krs_adapter = new Krs_Adapter(this, listkrs);
        recyclerView.setAdapter(krs_adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;


            case R.id.menu_moreapp:
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.play_more_apps))));
                return true;

            case R.id.menu_rateapp:
                final String appName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id="
                                    + appName)));
                }
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }



}
