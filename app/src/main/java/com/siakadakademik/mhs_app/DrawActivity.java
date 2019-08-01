package com.siakadakademik.mhs_app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.siakadakademik.mhs_app.app.AppController;
import com.siakadakademik.mhs_app.app.MySingleton;
import com.siakadakademik.mhs_app.util.JsonUtils;
import com.siakadakademik.mhs_app.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DrawActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txt_id, txt_username,count,smsCountTxt,nama_mahasiswa;
    FragmentManager fragmentManager;
    String id, username,url_count,url_cekServer,status;
    Fragment fragment = null;
    Intent intent;
    JsonUtils util;

    SharedPreferences sharedPreferences;


    private static int notif_badge_count;
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_ID_NAMA ="nama";
    private static final String url_detail  = Server.URL + "detail_profil_mhs.php";
    String tag_json_obj = "json_obj_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseMessaging.getInstance().subscribeToTopic("MHS_NOTICE");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hview = navigationView.getHeaderView(0);
        txt_username = (TextView)hview.findViewById(R.id.txt_username_nav);
        sharedPreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        count = findViewById(R.id.count);
        nama_mahasiswa = hview.findViewById(R.id.txt_nama_mahasiswa);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
        url_count = Server.URL+"tagihan_notifier.php?nim="+username;
        url_cekServer = Server.URL+"cek_koneksi.php";

        //txt_id.setText("ID : " + id);
        txt_username.setText(username);

        navigationView.setNavigationItemSelectedListener(this);

        util = new JsonUtils(getApplicationContext());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_count, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                        notif_badge_count = Integer.parseInt(response.getString("jumlah_data"));
                        Log.e("idmahasiswa",""+username);
                        setupBadge();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        MySingleton.getInstance(DrawActivity.this).addToRequestque(jsonObjectRequest);





        if (savedInstanceState == null) {
            fragment = new CardMenuScroll_mhs();
            callFragment(fragment);
        }

        setupIdMs();
        callDetailMhs(username);

    }


    private void setupIdMs() {
        if (JsonUtils.isNetworkAvailable(DrawActivity.this)){
            Constant.ID_MHS = username;
            cekServer();

        }
    }

    private void cekServer() {
        JsonObjectRequest cekserver = new JsonObjectRequest(Request.Method.POST, url_cekServer, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Constant.TAG_STATUS = response.getString("status_server");
                    if (Constant.TAG_STATUS.equals("ok")){
                        Toast.makeText(DrawActivity.this,"server connected ok", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(DrawActivity.this).addToRequestque(cekserver);
    }

    private void callDetailMhs(final String username) {


        StringRequest strReq = new StringRequest(Request.Method.POST, url_detail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONObject obj = new JSONObject(response);


                    String Nama_mahasiswa = obj.getString(TAG_ID_NAMA);


                    nama_mahasiswa.setText(Nama_mahasiswa);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(DrawActivity.this, "", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("nim", username);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void callFragment(Fragment fragment) {
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.draw, menu);
        MenuItem menuItem2 = menu.findItem(R.id.notification_action);

        View actionView = MenuItemCompat.getActionView(menuItem2);
        smsCountTxt = (TextView) actionView.findViewById(R.id.notification_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notif_badge_count == 0){
                    Intent intent = new Intent(DrawActivity.this, Activity_Empty_Notification.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(DrawActivity.this, TagihanActivity.class);
                    intent.putExtra(TAG_ID,id);
                    startActivity(intent);
                }

            }
        });


        return true;
    }

    private void setupBadge() {
        if (smsCountTxt != null) {
            if (notif_badge_count == 0) {
                if (smsCountTxt.getVisibility() != View.GONE) {
                    smsCountTxt.setVisibility(View.GONE);
                }
            } else {
                smsCountTxt.setText(String.valueOf(Math.min(notif_badge_count, 99)));
                if (smsCountTxt.getVisibility() != View.VISIBLE) {
                    smsCountTxt.setVisibility(View.VISIBLE);
                }
            }
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id_menu = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id_menu == R.id.notification_action) {
            Intent intent = new Intent(DrawActivity.this, TagihanActivity.class);
            intent.putExtra(TAG_ID,id);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            fragment = new CardMenuScroll_mhs();
            callFragment(fragment);

        } else if (id == R.id.nav_profil) {
            fragment = new FragLogout();
            callFragment(fragment);
//
//        } else if (id == R.id.nav_slideshow) {
//            fragment = new FragActivityFakultas();
//            callFragment(fragment);
//
//        } else if (id == R.id.nav_manage) {
//            fragment = new Fragment_Books();
//            callFragment(fragment);

        }else if (id == R.id.nav_logout) {
//            intent = new Intent(DrawActivity.this, PopActivity_Logout.class);
//            startActivity(intent);
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(DrawActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.activity_pop__logout, null);
            Button mLogin = (Button) mView.findViewById(R.id.btn_yes);
            Button mNo = (Button) mView.findViewById(R.id.btn_no);


//                mBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Login.session_status, false);
                    editor.putString(TAG_USERNAME, null);
                    editor.commit();

                    Intent intent = new Intent(DrawActivity.this, Login.class);
                    finish();
                    startActivity(intent);
                }
            });

            mNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
