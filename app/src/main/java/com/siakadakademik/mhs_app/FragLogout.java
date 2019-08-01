package com.siakadakademik.mhs_app;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.siakadakademik.mhs_app.app.AppController;
import com.siakadakademik.mhs_app.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyzz on 12/4/2017.
 */

public class FragLogout extends Fragment {
    Button btn_yes, btn_no, btn_poplogout, btn_detail;
    TextView txt_id, txt_username, nim, nama_mahasiswa, fakultas, prodi, periode, kelas,status, tgl_lahir;
    String id, username;
    SharedPreferences sharedpreferences;
    Intent intent;
    ImageButton image_mhs;


    public static final String TAG_ID = "id";
    public static final String TAG_ID_MHS ="id";
    public static final String TAG_USERNAME = "username";

    public static final String TAG_ID_NIM ="nim";
    public static final String TAG_ID_NAMA ="nama";
    public static final String TAG_FAKULTAS ="fakultas";
    public static final String TAG_PRODI ="prodi";
    public static final String TAG_PERIODE ="periode";
    public static final String TAG_KELAS ="kelas";
    public static final String TAG_STATUS = "status";
    public static final String TAG_TGL_LAHIR = "tgl_lahir";

    private static final String url_detail  = Server.URL + "detail_profil_mhs.php";
    String tag_json_obj = "json_obj_req";

    public FragLogout(){}
    View fraglogutView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fraglogutView = inflater.inflate(R.layout.card_profil_mhs, container, false);

        getActivity().setTitle("Profile");
        Log.e("Root", "uYE Nav Drawer");

        return fraglogutView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txt_id = (TextView)view.findViewById(R.id.txt_id);
        txt_username = (TextView)view.findViewById(R.id.txt_username);

        btn_yes = (Button)view.findViewById(R.id.btn_yes);
        btn_no = (Button)view.findViewById(R.id.btn_no);
        btn_poplogout = (Button)view.findViewById(R.id.btn_poplogout);
        btn_detail = (Button)view.findViewById(R.id.btn_detail);

        nim = (TextView) view.findViewById(R.id.nim);
        nama_mahasiswa = (TextView) view.findViewById(R.id.nama_mahasiswa);
        fakultas = (TextView) view.findViewById(R.id.fakultas);
        prodi = (TextView) view.findViewById(R.id.prodi);
        periode = (TextView) view.findViewById(R.id.periode);
        kelas = (TextView) view.findViewById(R.id.kelas);
        status = (TextView) view.findViewById(R.id.status);
        tgl_lahir = (TextView) view.findViewById(R.id.tgl_lahir);



        sharedpreferences = this.getActivity().getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        id = getActivity().getIntent().getStringExtra(TAG_ID_MHS);
        username = getActivity().getIntent().getStringExtra(TAG_USERNAME);

        //txt_id.setText("ID : " + id);
//        txt_username.setText("USERNAME : " + username);


//        btn_poplogout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                // update login session ke FALSE dan mengosongkan nilai id dan username
//
//                intent = new Intent(getActivity(), PopActivity_Logout.class);
//                getActivity().startActivity(intent);
//            }
//        });
//
//        btn_detail.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//                intent = new Intent(getActivity(), Detail_Profil_Mhs.class);
//                intent.putExtra(TAG_ID_MHS, id);
//                getActivity().startActivity(intent);
//            }
//        });

        callDetailMhs(id);

    }

    private void callDetailMhs(final String id) {


        StringRequest strReq = new StringRequest(Request.Method.POST, url_detail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONObject obj = new JSONObject(response);

                    String Nim = obj.getString(TAG_ID_NIM);
                    String Nama_mahasiswa = obj.getString(TAG_ID_NAMA);
                    String Fakultas = obj.getString(TAG_FAKULTAS);
                    String Prodi = obj.getString(TAG_PRODI);
                    String Periode = obj.getString(TAG_PERIODE);
                    String Kelas = obj.getString(TAG_KELAS);
                    String Status = obj.getString(TAG_STATUS);
                    String Tgl_lahir =obj.getString(TAG_TGL_LAHIR);

                    if (Status.equals("A")){
                        status.setTextColor(Color.parseColor("#FF15E70A"));
                    }

                    nim.setText(Nim);
                    nama_mahasiswa.setText(Nama_mahasiswa);
                    fakultas.setText(Fakultas);
                    prodi.setText(Prodi);
//                    periode.setText(Periode);
                    kelas.setText(Kelas);
                    status.setText(Status);
                    tgl_lahir.setText(Tgl_lahir);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("nim", id);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

}
