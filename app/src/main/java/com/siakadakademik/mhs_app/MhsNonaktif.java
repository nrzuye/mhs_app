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
import com.siakadakademik.mhs_app.app.AppController;
import com.siakadakademik.mhs_app.data.MhsData;
import com.siakadakademik.mhs_app.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyzz on 12/15/2017.
 */

public class MhsNonaktif extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    ListView list;
    SwipeRefreshLayout swipe;
    List<MhsData> mhsList = new ArrayList<MhsData>();

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mTonggle;

    private static final String TAG = MhsNonaktif.class.getSimpleName();

    private static String url_list = Server.URL + "mhs_nonaktif.php?offset=";

    private int offset = 0;

    int no;

    MhsAdapter adapter;

    public static final String TAG_NO ="no";
    public static final String TAG_ID_MHS ="id_mhs";
    public static final String TAG_ID_NAMA ="nama_mhs";


    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mhs);
        setTitle("Data Mahasiswa Non Aktif");

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list = (ListView) findViewById(R.id.list_mhs);
        mhsList.clear();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mhs_draw);
        mTonggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mTonggle);
        mTonggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id_mhs) {
                Intent intent = new Intent(MhsNonaktif.this, DetailMhs.class);
                intent.putExtra(TAG_ID_MHS, mhsList.get(position).getId_mhs());
                startActivity(intent);

            }
        });

        adapter = new MhsAdapter(MhsNonaktif.this, mhsList);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                mhsList.clear();
                adapter.notifyDataSetChanged();
                callMhs(0);

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
                                                     callMhs(offset);
                                                 }
                                             };

                                             handler.postDelayed(runnable, 3000);
                                         }
                                     }
                                 }
        );
    }

    private void callMhs(int page){
        swipe.setRefreshing(true);

        JsonArrayRequest arrReq = new JsonArrayRequest(url_list + page,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    MhsData mhs = new MhsData();

                                    no = obj.getInt(TAG_NO);

                                    mhs.setId_mhs(obj.getString(TAG_ID_MHS));
                                    mhs.setNama_mhs(obj.getString(TAG_ID_NAMA));



                                    mhsList.add(mhs);

                                    if (no > offset)
                                        offset = no;

                                    Log.d(TAG, "offset" + offset);
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
        );


        AppController.getInstance().addToRequestQueue(arrReq);
    }

    @Override
    public void onRefresh() {
        mhsList.clear();
        adapter.notifyDataSetChanged();
        callMhs(0);
    }

    public void onBackPressed() {
        Intent intent = new Intent(MhsNonaktif.this, DrawActivity.class);
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


