package com.siakadakademik.mhs_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.siakadakademik.mhs_app.adapter.Adapter_Transkip;
import com.siakadakademik.mhs_app.app.MySingleton;
import com.siakadakademik.mhs_app.data.TranskipDataList;
import com.siakadakademik.mhs_app.util.JsonUtils;
import com.siakadakademik.mhs_app.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyzz on 4/20/2018.
 */

public class TranskipActivity extends AppCompatActivity {

     RecyclerView recyclerView;
     List<TranskipDataList> arrayOfTranskip;
     Adapter_Transkip objAdapter;
     AlertDialogManager alert = new AlertDialogManager();
     ArrayList<String> allListTranskip,allListNama;
     ArrayList<String> allListTranskipNim,allListTranskipKodeMk,allListTranskipNamaMk,allListTranskipKurikulum,allListTranskipNilaiHuruf,allListTranskipNilaiAngka,allListTranskipLulus,allListTranskipPeriode;
     String[] allArrayTranskip,allArrayNama;
     String[] allArrayTranskipNim,allArrayTranskipKodeMk,allArrayTranskipNamaMk,allArrayTranskipKurikulum,allArrayTranskipNilaiHuruf,allArrayTranskipNilaiAngka,allArrayTranskipLulus,allArrayTranskipPeriode;
     TranskipDataList objAllBean;
     ProgressBar progressBar;
     LinearLayout bgempty,header_ipk;
     private Context context;
     JsonUtils util;
     int textlenght = 0 ;

     String id_mhs,url_count;
     Intent intent;
     TextView jml_bobot, ipk,textback;

     public static final String TAG_ID_MHSC = "id";
     public static final String TAG_FROM_SHAREDPREFERENCES="id";

     @Override
    protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_transkip);

         //setTitle(Constant.ID_MHS);


         progressBar = findViewById(R.id.progressBar);
         recyclerView = findViewById(R.id.recycler_view);
         bgempty = findViewById(R.id.bg_empty);
         header_ipk = findViewById(R.id.header_ipk_transkip);
         ipk = findViewById(R.id.ipk);
         jml_bobot= findViewById(R.id.jml_bobot);
         textback = findViewById(R.id.textback);

         RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
         recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(3), true));
         recyclerView.setLayoutManager(mLayoutManager);
         recyclerView.setItemAnimator(new DefaultItemAnimator());

         firebaseAnalytics();

         final Toolbar toolbar = findViewById(R.id.toolbar_collapsing);
         setSupportActionBar(toolbar);
         final ActionBar actionBar = getSupportActionBar();
         if (actionBar != null) {
             getSupportActionBar().setDisplayHomeAsUpEnabled(true);
             getSupportActionBar().setDisplayHomeAsUpEnabled(true);
             getSupportActionBar().setTitle("Transkip Nilai");
         }



         arrayOfTranskip=new ArrayList<TranskipDataList>();
         allListTranskip = new ArrayList<String>();
         allListNama = new ArrayList<String>();
         allListTranskipNim = new ArrayList<String>();
         allListTranskipKodeMk = new ArrayList<String>();
         allListTranskipNamaMk = new ArrayList<String>();
         allListTranskipKurikulum = new ArrayList<String>();
         allListTranskipNilaiHuruf = new ArrayList<String>();
         allListTranskipNilaiAngka = new ArrayList<String>();
         allListTranskipLulus = new ArrayList<String>();
         allListTranskipPeriode = new ArrayList<String>();

         allArrayTranskip = new String[allListTranskip.size()];
         allArrayNama = new String[allListNama.size()];
         allArrayTranskipNim = new String[allListTranskipNim.size()];
         allArrayTranskipKodeMk = new String[allListTranskipKodeMk.size()];
         allArrayTranskipNamaMk = new String[allListTranskipNamaMk.size()];
         allArrayTranskipKurikulum = new String[allListTranskipKurikulum.size()];
         allArrayTranskipNilaiHuruf = new String[allListTranskipNilaiHuruf.size()];
         allArrayTranskipNilaiAngka = new String[allListTranskipNilaiAngka.size()];
         allArrayTranskipLulus = new String[allListTranskipLulus.size()];
         allArrayTranskipPeriode = new String[allListTranskipPeriode.size()];


         id_mhs=getIntent().getStringExtra(TAG_ID_MHSC);

         util = new JsonUtils(getApplicationContext());
         if (JsonUtils.isNetworkAvailable(TranskipActivity.this)){
                new MyTask().execute(Server.URL+ Constant.TRANSKIP_URL+id_mhs);
                    url_count = (Server.URL+ Constant.TRANSKIP_URL+id_mhs);
         }else {
             showToast("No internet COnnection Coy");
             Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_connect_network), Toast.LENGTH_SHORT).show();
         }


         textback.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 new MyTask().execute(Server.URL+ Constant.TRANSKIP_URL+id_mhs);
                 bgempty.setVisibility(View.GONE);
             }
         });

         JsonObjectRequest jsonObjectTotalsks = new JsonObjectRequest(Request.Method.POST, url_count, null, new Response.Listener<JSONObject>() {
             @Override
             public void onResponse(JSONObject response) {
                 try {
                     jml_bobot.setText(response.getString("jml_bobot"));
                     ipk.setText(response.getString("ipk"));
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(TranskipActivity.this, "SKS dan IPK tidak terhitung coy 'mohon bersabar ini ujian'", Toast.LENGTH_SHORT).show();
                 error.printStackTrace();
             }
         });


         MySingleton.getInstance(TranskipActivity.this).addToRequestque(jsonObjectTotalsks);


     }

    private void firebaseAnalytics() {

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private class MyTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params){
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);

            if (null == result || result.length() == 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_connect_network), Toast.LENGTH_SHORT).show();
            } else {

                try {
                    Object json = new JSONTokener(result).nextValue();
                    if (json instanceof JSONObject)
                    {
                        header_ipk.setVisibility(View.VISIBLE);
                        JSONObject mainJson = new JSONObject(result);
                        JSONArray jsonArray = mainJson.getJSONArray(Constant.TRANSKIP_ARRAY_NAME);
                        JSONObject objJson = null;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            objJson = jsonArray.getJSONObject(i);

                            TranskipDataList objItem = new TranskipDataList();

                            objItem.setNim(objJson.getString(Constant.TRANSKIP_ITEM_NIM));
                            objItem.setKodemk(objJson.getString(Constant.TRANSKIP_ITEM_KODEMK));
                            objItem.setNamamk(objJson.getString(Constant.TRANSKIP_ITEM_NAMAMK));
                            objItem.setKurikulum(objJson.getString(Constant.TRANSKIP_ITEM_KURIKULUM));
                            objItem.setNilai_huruf(objJson.getString(Constant.TRANSKIP_ITEM_NILAI_HURUF));
                            objItem.setNilai_angka(objJson.getString(Constant.TRANSKIP_ITEM_NILAI_ANGKA));
                            objItem.setLulus(objJson.getString(Constant.TRANSKIP_ITEM_LULUS));
                            objItem.setPeriode(objJson.getString(Constant.TRANSKIP_ITEM_PERIODE));



                            arrayOfTranskip.add(objItem);

                        }
                    }else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_connect_network2), Toast.LENGTH_SHORT).show();
                        bgempty.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int j = 0; j < arrayOfTranskip.size(); j++) {
                    objAllBean = arrayOfTranskip.get(j);

                    allListTranskipNim.add(objAllBean.getNim());
                    allArrayTranskipNim = allListTranskipNim.toArray(allArrayTranskipNim);

                    allListTranskipKodeMk.add(objAllBean.getKodemk());
                    allArrayTranskipKodeMk = allListTranskipKodeMk.toArray(allArrayTranskipKodeMk);


                    allListTranskipNamaMk.add(String.valueOf(objAllBean.getNamamk()));
                    allArrayTranskipNamaMk = allListTranskipNamaMk.toArray(allArrayTranskipNamaMk);

                    allListTranskipKurikulum.add(String.valueOf(objAllBean.getKurikulum()));
                    allArrayTranskipKurikulum = allListTranskipKurikulum.toArray(allArrayTranskipKurikulum);

                    allListTranskipNilaiHuruf.add(String.valueOf(objAllBean.getNilai_huruf()));
                    allArrayTranskipNilaiHuruf = allListTranskipNilaiHuruf.toArray(allArrayTranskipNilaiHuruf);

                    allListTranskipNilaiAngka.add(String.valueOf(objAllBean.getNilai_angka()));
                    allArrayTranskipNilaiAngka = allListTranskipNilaiAngka.toArray(allArrayTranskipNilaiAngka);

                    allListTranskipLulus.add(String.valueOf(objAllBean.getLulus()));
                    allArrayTranskipLulus = allListTranskipLulus.toArray(allArrayTranskipLulus);

                    allListTranskipPeriode.add(String.valueOf(objAllBean.getPeriode()));
                    allArrayTranskipPeriode = allListTranskipPeriode.toArray(allArrayTranskipPeriode);


                }
                setAdapterToRecyclerView();


            }
        }
    }

    private class RefresMyTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params){
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressBar.setVisibility(View.GONE);

            if (null == result || result.length() == 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_connect_network), Toast.LENGTH_SHORT).show();
            } else {

                try {
                    Object json = new JSONTokener(result).nextValue();
                    if (json instanceof JSONObject)
                    {
                        JSONObject mainJson = new JSONObject(result);
                        JSONArray jsonArray = mainJson.getJSONArray(Constant.TRANSKIP_ARRAY_NAME);
                        JSONObject objJson = null;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            objJson = jsonArray.getJSONObject(i);

                            TranskipDataList objItem = new TranskipDataList();

                            objItem.setNim(objJson.getString(Constant.TRANSKIP_ITEM_NIM));
                            objItem.setKodemk(objJson.getString(Constant.TRANSKIP_ITEM_KODEMK));
                            objItem.setNamamk(objJson.getString(Constant.TRANSKIP_ITEM_NAMAMK));
                            objItem.setKurikulum(objJson.getString(Constant.TRANSKIP_ITEM_KURIKULUM));
                            objItem.setNilai_huruf(objJson.getString(Constant.TRANSKIP_ITEM_NILAI_HURUF));
                            objItem.setNilai_angka(objJson.getString(Constant.TRANSKIP_ITEM_NILAI_ANGKA));
                            objItem.setLulus(objJson.getString(Constant.TRANSKIP_ITEM_LULUS));
                            objItem.setPeriode(objJson.getString(Constant.TRANSKIP_ITEM_PERIODE));

                            arrayOfTranskip.add(objItem);

                        }
                    }else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_connect_network2), Toast.LENGTH_SHORT).show();
                        bgempty.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int j = 0; j < arrayOfTranskip.size(); j++) {
                    objAllBean = arrayOfTranskip.get(j);

                    allListTranskipNim.add(objAllBean.getNim());
                    allArrayTranskipNim = allListTranskipNim.toArray(allArrayTranskipNim);

                    allListTranskipKodeMk.add(objAllBean.getKodemk());
                    allArrayTranskipKodeMk = allListTranskipKodeMk.toArray(allArrayTranskipKodeMk);


                    allListTranskipNamaMk.add(String.valueOf(objAllBean.getNamamk()));
                    allArrayTranskipNamaMk = allListTranskipNamaMk.toArray(allArrayTranskipNamaMk);

                    allListTranskipKurikulum.add(String.valueOf(objAllBean.getKurikulum()));
                    allArrayTranskipKurikulum = allListTranskipKurikulum.toArray(allArrayTranskipKurikulum);

                    allListTranskipNilaiHuruf.add(String.valueOf(objAllBean.getNilai_huruf()));
                    allArrayTranskipNilaiHuruf = allListTranskipNilaiHuruf.toArray(allArrayTranskipNilaiHuruf);

                    allListTranskipNilaiAngka.add(String.valueOf(objAllBean.getNilai_angka()));
                    allArrayTranskipNilaiAngka = allListTranskipNilaiAngka.toArray(allArrayTranskipNilaiAngka);

                    allListTranskipLulus.add(String.valueOf(objAllBean.getLulus()));
                    allArrayTranskipLulus = allListTranskipLulus.toArray(allArrayTranskipLulus);

                    allListTranskipPeriode.add(String.valueOf(objAllBean.getPeriode()));
                    allArrayTranskipPeriode = allListTranskipPeriode.toArray(allArrayTranskipPeriode);


                }
                setAdapterToRecyclerView();


            }
        }
    }

    private void setAdapterToRecyclerView() {
        objAdapter = new Adapter_Transkip(this,arrayOfTranskip);
        recyclerView.setAdapter(objAdapter);
    }

    private void showToast(String msg) {
        Toast.makeText(TranskipActivity.this, msg, Toast.LENGTH_SHORT).show();
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

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {

                textlenght = newText.length();
                arrayOfTranskip.clear();

                for (int i = 0; i < allArrayTranskipNamaMk.length; i++)
                {
                    if (textlenght <= allArrayTranskipNamaMk[i].length())
                    {
                        if (newText.toString().equalsIgnoreCase((String) allArrayTranskipNamaMk[i].subSequence(0,textlenght)))
                        {
                            TranskipDataList objItem = new TranskipDataList();

                            objItem.setNim(allArrayTranskipNim[i]);
                            objItem.setKodemk(allArrayTranskipKodeMk[i]);
                            objItem.setNamamk(allArrayTranskipNamaMk[i]);
                            objItem.setNilai_huruf(allArrayTranskipNilaiHuruf[i]);
                            objItem.setNilai_angka(allArrayTranskipNilaiAngka[i]);
                            objItem.setKurikulum(allArrayTranskipKurikulum[i]);
                            objItem.setLulus(allArrayTranskipLulus[i]);
                            objItem.setPeriode(allArrayTranskipPeriode[i]);

                            arrayOfTranskip.add(objItem);
                        }
                    }
                }
                setAdapterToRecyclerView();
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
