package com.siakadakademik.mhs_app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.siakadakademik.mhs_app.adapter.FakAdapter;
import com.siakadakademik.mhs_app.app.AppController;
import com.siakadakademik.mhs_app.data.FakData;
import com.siakadakademik.mhs_app.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyzz on 1/21/2018.
 */

public class FragActivityFakultas extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ListView list;
    SwipeRefreshLayout swipe;
    List<FakData> faklist = new ArrayList<FakData>();

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mTonggle;

    private static final String TAG = FragActivityFakultas.class.getSimpleName();

    private static String url_list = Server.URL + "fakultas.php?offset=";

    private int offset = 0;

    int no;


    FakAdapter adapter;

    public static final String TAG_NO = "no";
    public static final String TAG_ID_FAK = "id_fak";
    public static final String TAG_FAKULTAS = "fakultas";

    android.os.Handler handler;
    Runnable runnable;

    public FragActivityFakultas(){}
    View FragActivityFakultasView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FragActivityFakultasView = inflater.inflate(R.layout.activity_mhs, container, false);

        getActivity().setTitle("Fakultas");
        return FragActivityFakultasView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipe = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        list = (ListView)view.findViewById(R.id.list_mhs);
        faklist.clear();

        mDrawerLayout = (DrawerLayout)view.findViewById(R.id.mhs_draw);
        mTonggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mTonggle);
        mTonggle.syncState();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id_fak) {
                Intent intent = new Intent(getActivity(), ActivityProdi.class);
                intent.putExtra(TAG_ID_FAK, faklist.get(position).getId_fak());
                startActivity(intent);
            }
        });

        adapter = new FakAdapter(getActivity(), faklist);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                faklist.clear();
                adapter.notifyDataSetChanged();
                callFak(0);
            }
        });

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount && this.currentScrollState == SCROLL_STATE_IDLE){
                    swipe.setRefreshing(true);
                    handler = new android.os.Handler();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            callFak(offset);
                        }
                    };
                    handler.postDelayed(runnable,3000);
                }
            }

        });
    }


    private void callFak(int page){
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
                                    FakData fak = new FakData();

                                    no = obj.getInt(TAG_NO);

                                    fak.setId_fak(obj.getString(TAG_ID_FAK));
                                    fak.setFakultas(obj.getString(TAG_FAKULTAS));


                                    faklist.add(fak);

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
        faklist.clear();
        adapter.notifyDataSetChanged();
        callFak(0);
    }

    public void onBackPressed() {
        Intent intent = new Intent(getActivity(), DrawActivity.class);
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
