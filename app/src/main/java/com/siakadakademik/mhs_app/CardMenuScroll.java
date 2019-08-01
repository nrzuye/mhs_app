package com.siakadakademik.mhs_app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

/**
 * Created by xyzz on 12/12/2017.
 */

public class CardMenuScroll extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    LinearLayout ln_mahasiswa, ln_dosen, ln_fakultas, ln_keuangan, ln_absensi, ln_transkip;
    String id;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mTonggle;

    public static final String TAG_ID_MHS ="id";


    Handler handler;
    Runnable runnable;

    public CardMenuScroll(){}
    View lnMenuScrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        lnMenuScrollView = inflater.inflate(R.layout.home_menu, container, false);

        getActivity().setTitle("Mahasiswa Dashbard");
        return lnMenuScrollView;

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        ln_mahasiswa = (LinearLayout) view.findViewById(R.id.ln_mahasiswa);
        ln_dosen = (LinearLayout)view.findViewById(R.id.ln_dosen);
        ln_fakultas = (LinearLayout) view.findViewById(R.id.ln_fakultas);
        ln_keuangan = (LinearLayout) view.findViewById(R.id.ln_keuangan);
        ln_absensi = (LinearLayout)view.findViewById(R.id.ln_absensi);
        ln_transkip = (LinearLayout)view.findViewById(R.id.ln_transkip);

        id = getActivity().getIntent().getStringExtra(TAG_ID_MHS);



        //Jadikan textview tombol coy

        ln_mahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MhsAktif.class);
                getActivity().startActivity(intent);
            }
        });


        ln_dosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MhsNonaktif.class);
                getActivity().startActivity(intent);
            }
        });

        ln_fakultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragActivityFakultas fragActivityFakultas = new FragActivityFakultas();
                fragmentTransaction.replace(R.id.frame_container, fragActivityFakultas);
                fragmentTransaction.commit();
            }
        });


        ln_keuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MhsCuti.class);
                getActivity().startActivity(intent);
            }
        });


        ln_absensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Fragment_StoryList.class);
                intent.putExtra(TAG_ID_MHS, id);
                getActivity().startActivity(intent);
            }
        });

        ln_transkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), TranskipActivity.class);
                intent.putExtra(TAG_ID_MHS, id);
                getActivity().startActivity(intent);
            }
        });

    }




    @Override
    public void onRefresh() {

    }
}
