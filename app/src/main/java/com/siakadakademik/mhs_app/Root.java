package com.siakadakademik.mhs_app;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Kuncoro on 22/03/2016.
 */
public class Root extends Fragment {

    Button btn_logout, btn_mhs, btn_total;
    SharedPreferences sharedpreferences;
    ImageButton image_mhs;


    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";

    public Root(){}
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.root, container, false);

        getActivity().setTitle("Beranda");
        Log.e("Root", "uYE Nav Drawer");

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_logout = (Button)view.findViewById(R.id.btn_logout);
        btn_mhs = (Button)view.findViewById(R.id.btn_mhs);
        btn_total = (Button)view.findViewById(R.id.btn_total);

        sharedpreferences = this.getActivity().getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);


        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Login.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();



                Intent intent = new Intent(getActivity(), Login.class);

                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });



        btn_mhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivityMhs.class);
                getActivity().startActivity(intent);
            }
        });

        btn_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Total.class);
                getActivity().startActivity(intent);
            }
        });

    }
}
