package com.siakadakademik.mhs_app;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.siakadakademik.mhs_app.adapter.Adapter_Periode;
import com.siakadakademik.mhs_app.app.AppController;
import com.siakadakademik.mhs_app.data.PeriodeData;
import com.siakadakademik.mhs_app.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyzz on 6/4/2018.
 */

public class PeriodeActivity extends AppCompatActivity {
    TextView txt_hasil;
    Spinner spinner_periode;
    ProgressDialog pDialog;
    Adapter_Periode adapter_periode;
    List<PeriodeData> listperiode = new ArrayList<PeriodeData>();
    String id_mhs,idperiode,periode;
    RecyclerView recyclerView;
    LinearLayout empty_state;
    ProgressBar progressBar;

    public static final String url = Server.URL + "periode.php";

    private static final String TAG = PeriodeActivity.class.getSimpleName();


    public static final String TAG_ID_MHSC = "id";
    public static final String TAG_IDPERIODE = "idperiode";
    public static final String TAG_PERIODE = "periode";

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periode_card);
        setTitle("Pilih Semester");

        txt_hasil = (TextView) findViewById(R.id.txt_hasil);
        spinner_periode = (Spinner) findViewById(R.id.spinner_periode);
        recyclerView = findViewById(R.id.recycler_view_periode);
        empty_state = findViewById(R.id.empty_state);
        progressBar = findViewById(R.id.progressBar);

        id_mhs = getIntent().getStringExtra(TAG_ID_MHSC);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(3), true));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        callData();

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void callData() {
        listperiode.clear();

        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jArr = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

//              parsing json coy

                if (response.length() != 0)
                {
                    for (int i = 0; i < response.length(); i++)
                    {
                        try {
                                JSONObject obj = response.getJSONObject(i);
                                PeriodeData item = new PeriodeData();

                                item.setIdperiode(obj.getString(TAG_IDPERIODE));
                                item.setPeriode(obj.getString(TAG_PERIODE));

                                listperiode.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                        }
                    }
                    setAdapterToRecyclerView();
                    progressBar.setVisibility(View.GONE);
                }else {
                    empty_state.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_connect_network2), Toast.LENGTH_SHORT).show();
                empty_state.setVisibility(View.VISIBLE);

            }
        });
        AppController.getInstance().addToRequestQueue(jArr);


    }

    private void setAdapterToRecyclerView() {
        adapter_periode = new Adapter_Periode(this,listperiode);
        recyclerView.setAdapter(adapter_periode);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
