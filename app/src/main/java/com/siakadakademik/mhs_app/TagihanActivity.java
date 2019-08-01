package com.siakadakademik.mhs_app;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.siakadakademik.mhs_app.adapter.Tagihan_Adapter;
import com.siakadakademik.mhs_app.app.AppController;
import com.siakadakademik.mhs_app.data.TagihanData;
import com.siakadakademik.mhs_app.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TagihanActivity extends AppCompatActivity{
    ProgressBar progressBar;
    Tagihan_Adapter tagihan_adapter;
    List<TagihanData> listTagihan = new ArrayList<TagihanData>();
    String id_mhs, url;
    RecyclerView recyclerView;
    LinearLayout empty_state;
    ImageView img_empty,img_gone;
    TextView txt_oops,txt_money,txt_msg_tagihan,txtback;

    private static final String TAG = TagihanActivity.class.getSimpleName();


    public static final String TAG_ID_MHS = "id";
    public static final String TAG_NIM = "nim";
    public static final String TAG_NAMA_MHS = "nama_mhs";
    public static final String TAG_PRODI = "prodi";
    public static final String TAG_KODE_TAGIHAN = "kode_tagihan";
    public static final String TAG_NAMA_TAGIHAN = "nama_tagihan";
    public static final String TAG_KODEMK ="kode_mk";
    public static final String TAG_NAMAMK ="nama_mk";
    public static final String TAG_JML_TAGIHAN ="jml_tagihan";
    public static final String TAG_STATUS_TAGIHAN = "status_tagihan";
    public static final String TAG_PERIODE = "periode";
    public static final String TAG_IDPERIODE = "idperiode";



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan_card);
        setTitle("Tagihan");

        recyclerView = findViewById(R.id.recycler_view_tagihan);
        empty_state = findViewById(R.id.empty_state);
        img_empty = findViewById(R.id.img_no_item_payment);
        img_gone =findViewById(R.id.img_no_item);
        txt_oops= findViewById(R.id.Oops);
        txt_money = findViewById(R.id.money_time);
        txt_msg_tagihan = findViewById(R.id.no_item_msg_tagihan);
        txtback = findViewById(R.id.textback);
        progressBar = findViewById(R.id.progressBar);

        id_mhs= getIntent().getStringExtra(TAG_ID_MHS);
        url = Server.URL+"tagihan.php?nim="+id_mhs;

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
        listTagihan.clear();
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jArrTgh = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

                if (response.length() != 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            TagihanData item = new TagihanData();


                            item.setNim(object.getString(TAG_NIM));
                            item.setNama_mhs(object.getString(TAG_NAMA_MHS));
                            item.setProdi(object.getString(TAG_PRODI));
                            item.setKode_tagihan(object.getString(TAG_KODE_TAGIHAN));
                            item.setNama_tagihan(object.getString(TAG_NAMA_TAGIHAN));
                            item.setKode_mk(object.getString(TAG_KODEMK));
                            item.setNama_mk(object.getString(TAG_NAMAMK));
                            item.setJml_tagihan(object.getString(TAG_JML_TAGIHAN));
                            item.setStatus_tagihan(object.getString(TAG_STATUS_TAGIHAN));
                            item.setPeriode(object.getString(TAG_PERIODE));
                            item.setIdperiode(object.getString(TAG_IDPERIODE));

                            listTagihan.add(item);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    setAdapterToRecycler();
                    progressBar.setVisibility(View.GONE);


                } else {
                    empty_state.setVisibility(View.VISIBLE);
                    img_empty.setVisibility(View.VISIBLE);
                    txt_money.setVisibility(View.VISIBLE);
                    txt_msg_tagihan.setVisibility(View.VISIBLE);
                    img_gone.setVisibility(View.GONE);
                    txt_oops.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error "+ error.getMessage());
                empty_state.setVisibility(View.VISIBLE);
            }
        });

        AppController.getInstance().addToRequestQueue(jArrTgh);
    }

    private void setAdapterToRecycler() {
        tagihan_adapter = new Tagihan_Adapter(this, listTagihan);
        recyclerView.setAdapter(tagihan_adapter);
    }


}


// DecimalFormat formatter = new DecimalFormat("#,###,###");
// item.setJml_tagihan(formatter.format(Integer.parseInt(object.getString(TAG_JML_TAGIHAN))));