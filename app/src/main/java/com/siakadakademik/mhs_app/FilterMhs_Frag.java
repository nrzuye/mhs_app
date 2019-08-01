package com.siakadakademik.mhs_app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.siakadakademik.mhs_app.app.MySingleton;
import com.siakadakademik.mhs_app.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xyzz on 12/12/2017.
 */

public class FilterMhs_Frag extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    TextView jumlah_data, jumlah_data2, jumlah_data3;
    LinearLayout ln_mhs_aktif, ln_mhs_nonaktif, ln_mhs_cuti;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mTonggle;


    private static String url_list = Server.URL + "count_mhs_aktif.php";
    private static String url_list2 = Server.URL + "count_mhs_nonaktif.php";
    private static String url_list3 = Server.URL +"count_mhs_cuti.php";

    Handler handler;
    Runnable runnable;

    public FilterMhs_Frag(){}
    View FilterMhs_FragView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FilterMhs_FragView = inflater.inflate(R.layout.filter_mhs_menu, container, false);

        getActivity().setTitle("Mahasiswa Dashbard");
        return FilterMhs_FragView;

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        jumlah_data = (TextView)view.findViewById(R.id.jumlah_data);
        jumlah_data2 = (TextView)view.findViewById(R.id.jumlah_data2) ;
        jumlah_data3= (TextView)view.findViewById(R.id.jumlah_data3);

        ln_mhs_aktif = (LinearLayout) view.findViewById(R.id.ln_mhs_aktif);
        ln_mhs_nonaktif = (LinearLayout) view.findViewById(R.id.ln_mhs_nonaktif);
        ln_mhs_cuti = (LinearLayout) view.findViewById(R.id.ln_mhs_cuti);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_list, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            jumlah_data.setText(response.getString("jumlah_data"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Kuntull", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url_list2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            jumlah_data2.setText(response.getString("jumlah_data2"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Kuntul", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, url_list3, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            jumlah_data3.setText(response.getString("jumlah_data3"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Kuntul", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        MySingleton.getInstance(getActivity()).addToRequestque(jsonObjectRequest);
        MySingleton.getInstance(getActivity()).addToRequestque(jsonObjectRequest1);
        MySingleton.getInstance(getActivity()).addToRequestque(jsonObjectRequest2);

        //Jadikan textview tombol coy

        ln_mhs_aktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MhsAktif.class);
                getActivity().startActivity(intent);
            }
        });


        ln_mhs_nonaktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MhsNonaktif.class);
                getActivity().startActivity(intent);
            }
        });

        ln_mhs_cuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MhsCuti.class);
                getActivity().startActivity(intent);
            }
        });

    }




    @Override
    public void onRefresh() {

    }
}
