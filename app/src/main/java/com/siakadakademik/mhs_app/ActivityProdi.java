package com.siakadakademik.mhs_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.siakadakademik.mhs_app.adapter.MhsAdapter;
import com.siakadakademik.mhs_app.adapter.ProdiAdaper;
import com.siakadakademik.mhs_app.app.AppController;
import com.siakadakademik.mhs_app.data.MhsData;
import com.siakadakademik.mhs_app.data.ProdiData;
import com.siakadakademik.mhs_app.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xyzz on 11/17/2017.
 */

public class ActivityProdi extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    ListView list;
    SwipeRefreshLayout swipe;
    String id_fak;
    List<ProdiData> prodiList = new ArrayList<ProdiData>();

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mTonggle;

    private static final String TAG = ActivityProdi.class.getSimpleName();

    private static String url_list = Server.URL + "prodi.php";



    int no;

    ProdiAdaper adapter;

    public static final String TAG_NO = "no";
    public static final String TAG_ID_PRODI = "id_prodi";
    public static final String TAG_NAMA_PRODI = "nama_prodi";
    public static final String TAG_ID_FAK = "id_fak";


    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mhs);
        setTitle("Data Prodi");

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list = (ListView) findViewById(R.id.list_mhs);
        prodiList.clear();

        id_fak = getIntent().getStringExtra(TAG_ID_FAK);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mhs_draw);
        mTonggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mTonggle);
        mTonggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id_prodi) {
                Intent intent = new Intent(ActivityProdi.this, DetailMhs.class);
                intent.putExtra(TAG_ID_PRODI, prodiList.get(position).getId_prodi());
                startActivity(intent);

            }
        });

        adapter = new ProdiAdaper(ActivityProdi.this, prodiList);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                prodiList.clear();
                adapter.notifyDataSetChanged();
                callMhs(id_fak);

            }
        });

        list.setOnScrollListener(new AbsListView.OnScrollListener(){
                                     private int currentVisibleItemCount;
                                     private int currentScrollState;
                                     private int currentFirstVisibleItem;
                                     private int totalItem;

                                     @Override
                                     public void onScrollStateChanged(AbsListView view, int scrollState) {
                                         this.currentScrollState = scrollState;
                                         this.isScrollCompleted();
                                     }

                                     @Override
                                     public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                         this.currentFirstVisibleItem = firstVisibleItem;
                                         this.currentVisibleItemCount = visibleItemCount;
                                         this.totalItem = totalItemCount;
                                     }

                                     private void isScrollCompleted() {
                                         if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                                                 && this.currentScrollState == SCROLL_STATE_IDLE) {

                                             swipe.setRefreshing(true);
                                             handler = new Handler();

                                             runnable = new Runnable() {
                                                 public void run() {
                                                     if (!id_fak.isEmpty())
                                                     callMhs(id_fak);
                                                 }
                                             };

                                             handler.postDelayed(runnable, 3000);
                                         }
                                     }
                                 }
        );
    }

    private void callMhs(final String id_fak){
        swipe.setRefreshing(true);

        JsonArrayRequest arrReq = new JsonArrayRequest(url_list,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    ProdiData prodi = new ProdiData();

                                    no = obj.getInt(TAG_NO);

                                    prodi.setId_prodi(obj.getString(TAG_ID_PRODI));
                                    prodi.setNama_prodi(obj.getString(TAG_NAMA_PRODI));



                                    prodiList.add(prodi);


                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error : " + e.getMessage());
                                }

                                adapter.notifyDataSetChanged();
                            }
                        }
                        swipe.setRefreshing(false);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error" + error.getMessage());
                swipe.setRefreshing(false);
            }
        }
        ){

            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_fak", id_fak);

                return params;
            }
        };


        AppController.getInstance().addToRequestQueue(arrReq);
    }

    @Override
    public void onRefresh() {
        prodiList.clear();
        adapter.notifyDataSetChanged();
        callMhs(id_fak);
    }

    public void onBackPressed() {
        Intent intent = new Intent(ActivityProdi.this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mTonggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
