package com.siakadakademik.mhs_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.siakadakademik.mhs_app.adapter.Adapter_Khs;
import com.siakadakademik.mhs_app.app.MySingleton;
import com.siakadakademik.mhs_app.data.KhsDataList;
import com.siakadakademik.mhs_app.util.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyzz on 6/2/2018.
 */

public class KhsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_Khs objadapter;
    List<KhsDataList> arrayOfKhs;
    AlertDialogManager alert = new AlertDialogManager();
    ArrayList<String> allListKhs, allListNama;
    ArrayList<String> allListKhsNim,allListKhsKodemk,allListKhsNamamk,allListKhsSksmk,allListKhsNamaKelas,allListKhsPengampu,allListKhsPeriode,allListKhsNilai;
    String [] allArrayKhs, allArrayNama;
    String [] allArrayKhsNim, allArrayKhsKodemk,allArrayKhsNamamk,allArrayKhsSksmk,allArrayKhsNamaKelas,allArrayKhsPengampu,allArrayKhsPeriode,allArrayKhsNilai;
    private KhsDataList objAllBean;
    LinearLayout empty_state;
    private int columnWidth;
    JsonUtils utils;
    int tetlenght= 0;
    LinearLayout header_ipk_khs;
    TextView totalsks,ipk,msg_empty,textback;
    String id_mhs,idshared,idperiode,periode,url_count;
    Intent intent;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    AppBarLayout appBar;


    public static final String TAG_ID_MHSC = "id";
    public static final String TAG_FROM_SHAREDPREFERENCES="id";
    public static final String TAG_IDPERIODE = "idperiode";
    public static final String TAG_PERIODE = "periode";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khs_card);

        progressBar = findViewById(R.id.progressBar);
        totalsks = (TextView) findViewById(R.id.totalsks);
        ipk = (TextView) findViewById(R.id.ipk);
        recyclerView = findViewById(R.id.recycler_view);
        empty_state = findViewById(R.id.empty_state);
        msg_empty = findViewById(R.id.no_item_msg_khs);
        textback = findViewById(R.id.textback);
        header_ipk_khs =findViewById(R.id.header_ipk_khs);
        appBar = findViewById(R.id.appbar);


        final Toolbar toolbar = findViewById(R.id.toolbar_collapsing);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(Constant.NAMAPERIODE);
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(3), true));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        arrayOfKhs = new ArrayList<KhsDataList>();
        allListKhs = new ArrayList<String>();
        allListNama = new ArrayList<String>();
        allListKhsNim = new ArrayList<String>();
        allListKhsKodemk = new ArrayList<String>();
        allListKhsNamamk = new ArrayList<String>();
        allListKhsSksmk = new ArrayList<String>();
        allListKhsNamaKelas = new ArrayList<String>();
        allListKhsPengampu = new ArrayList<String>();
        allListKhsPeriode = new ArrayList<String>();
        allListKhsNilai = new ArrayList<String>();

        allArrayKhs = new String[allListKhs.size()];
        allArrayNama = new String[allListNama.size()];
        allArrayKhsNim = new String[allListKhsNim.size()];
        allArrayKhsKodemk = new String[allListKhsKodemk.size()];
        allArrayKhsNamamk = new String[allListKhsNamamk.size()];
        allArrayKhsSksmk = new String[allListKhsSksmk.size()];
        allArrayKhsNamaKelas = new String[allListKhsNamaKelas.size()];
        allArrayKhsPengampu = new String[allListKhsPengampu.size()];
        allArrayKhsPeriode = new String[allListKhsPeriode.size()];
        allArrayKhsNilai = new String[allListKhsNim.size()];


        utils = new JsonUtils(getApplicationContext());
        sharedPreferences =getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        idshared = getIntent().getStringExtra(TAG_FROM_SHAREDPREFERENCES);
        id_mhs = getIntent().getStringExtra(TAG_ID_MHSC);
        periode = getIntent().getStringExtra(TAG_PERIODE);
        idperiode = getIntent().getStringExtra(TAG_IDPERIODE);

        if (JsonUtils.isNetworkAvailable(KhsActivity.this)){
            new MyTask().execute(Constant.KHS_URL+id_mhs+"&periode="+Constant.IDPERIODE);
            url_count = (Constant.KHS_URL+id_mhs+"&periode="+Constant.IDPERIODE);
        }else {
            showToast("No internet COnnection Coy");
            alert.showAlertDialog(KhsActivity.this, "Internet ERROR","Please connect to the network", false);
        }

        textback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        JsonObjectRequest jsonObjectTotalsks = new JsonObjectRequest(Request.Method.POST, url_count, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    totalsks.setText(response.getString("totalsks"));
                    ipk.setText(response.getString("ipk"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(KhsActivity.this, "SKS dan IPK tidak terhitung coy 'mohon bersabar ini ujian'", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });


        MySingleton.getInstance(KhsActivity.this).addToRequestque(jsonObjectTotalsks);


    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private class MyTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressBar.setVisibility(View.GONE);

            if (result.length() != 0 ) {

                try {
                    Object json = new JSONTokener(result).nextValue();
                    if (json instanceof JSONObject)
                    {
                        header_ipk_khs.setVisibility(View.VISIBLE);
                        appBar.setVisibility(View.VISIBLE);
                        JSONObject mainJson = new JSONObject(result);
                        JSONArray jsonArray = mainJson.getJSONArray(Constant.KHS_ARRAY_NAME);
                        JSONObject objJson = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            objJson = jsonArray.getJSONObject(i);

                            KhsDataList objItem = new KhsDataList();

                            objItem.setNim(objJson.getString(Constant.KHS_ITEM_NIM));
                            objItem.setKodemk(objJson.getString(Constant.KHS_ITEM_KODEMK));
                            objItem.setNamamk(objJson.getString(Constant.KHS_ITEM_NAMAMK));
                            objItem.setSksmk(objJson.getString(Constant.KHS_ITEM_SKSMK));
                            objItem.setNama_kelas(objJson.getString(Constant.KHS_ITEM_NAMA_KELAS));
                            objItem.setPeriode(objJson.getString(Constant.KHS_ITEM_PERIODE));
                            objItem.setPengampu(objJson.getString(Constant.KHS_ITEM_PENGAMPU));
                            objItem.setNilai(objJson.getString(Constant.KHS_ITEM_NILAI));

                            arrayOfKhs.add(objItem);
                        }
                    }else {
                        //Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_connect_network2), Toast.LENGTH_SHORT).show();
                        empty_state.setVisibility(View.VISIBLE);
                        msg_empty.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                showToast("Data tidak ada kak");
                alert.showAlertDialog(KhsActivity.this, "Internet ERROR","Please connect to the network", false);

            }


                for (int j = 0; j < arrayOfKhs.size(); j++) {
                    objAllBean = arrayOfKhs.get(j);

                    allListKhsNim.add(objAllBean.getNim());
                    allArrayKhsNim = allListKhs.toArray(allArrayKhsNim);

                    allListKhsKodemk.add(objAllBean.getKodemk());
                    allArrayKhsKodemk = allListKhsKodemk.toArray(allArrayKhsKodemk);


                    allListKhsNamamk.add(String.valueOf(objAllBean.getNamamk()));
                    allArrayKhsNamamk = allListKhsNamamk.toArray(allArrayKhsNamamk);

                    allListKhsSksmk.add(String.valueOf(objAllBean.getSksmk()));
                    allArrayKhsSksmk = allListKhsSksmk.toArray(allArrayKhsSksmk);

                    allListKhsNamaKelas.add(String.valueOf(objAllBean.getNama_kelas()));
                    allArrayKhsNamaKelas = allListKhsNamaKelas.toArray(allArrayKhsNamaKelas);

                    allListKhsPengampu.add(String.valueOf(objAllBean.getPengampu()));
                    allArrayKhsPengampu = allListKhsPengampu.toArray(allArrayKhsPengampu);

                    allListKhsPeriode.add(String.valueOf(objAllBean.getPeriode()));
                    allArrayKhsPeriode = allListKhsPeriode.toArray(allArrayKhsPeriode);

                    allListKhsNilai.add(String.valueOf(objAllBean.getNilai()));
                    allArrayKhsNilai = allListKhsNilai.toArray(allArrayKhsNilai);


                }
                setAdapterToListview();


            }


    }




    private void setAdapterToListview() {

        objadapter = new Adapter_Khs(KhsActivity.this,arrayOfKhs);
        recyclerView.setAdapter(objadapter);
    }

    private void showToast(String msg) {
        Toast.makeText(KhsActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView)
                MenuItemCompat.getActionView(menu.findItem(R.id.search));

        final MenuItem searchMenuItem = menu.findItem(R.id.search);

        searchView.setQueryHint("Cari Mata Kuliah...");
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus){
                    searchMenuItem.collapseActionView();
                    searchView.setQuery("",false);
                }

            }
        });

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener(){
        @Override
        public boolean onQueryTextChange(String newText){

            tetlenght = newText.length();
            arrayOfKhs.clear();

            for (int i=0 ; i < allArrayKhsNamamk.length ; i++)
            {
                if (tetlenght <= allArrayKhsNamamk[i].length())
                {
                    if (newText.toString().equalsIgnoreCase((String) allArrayKhsNamamk[i].subSequence(0,tetlenght)))
                    {
                      KhsDataList objItem = new KhsDataList();

//                      objItem.setNim(allArrayKhsNim[i]);
                      objItem.setKodemk(allArrayKhsKodemk[i]);
                      objItem.setNamamk(allArrayKhsNamamk[i]);
                      objItem.setSksmk(allArrayKhsSksmk[i]);
                      objItem.setNama_kelas(allArrayKhsNamaKelas[i]);
                      objItem.setPengampu(allArrayKhsPengampu[i]);
                      objItem.setPeriode(allArrayKhsPeriode[i]);
                      objItem.setNilai(allArrayKhsNilai[i]);

                      arrayOfKhs.add(objItem);
                    }
                }
            }
            setAdapterToListview();
            return false;
        }
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do something
                return true;
            }

        });

        return super.onCreateOptionsMenu(menu);
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

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }



}
