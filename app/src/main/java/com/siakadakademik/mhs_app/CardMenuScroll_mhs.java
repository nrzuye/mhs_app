package com.siakadakademik.mhs_app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by xyzz on 12/12/2017.
 */

public class CardMenuScroll_mhs extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    LinearLayout ln_mahasiswa, ln_dosen, ln_krs, ln_keuangan, ln_khs, ln_transkip;
    String id;
    TextView count;


    public static final String TAG_ID_MHS ="id";


    Handler handler;
    Runnable runnable;

    public CardMenuScroll_mhs(){}
    View lnMenuScrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        lnMenuScrollView = inflater.inflate(R.layout.home_menu_mhs, container, false);

        getActivity().setTitle("");
        return lnMenuScrollView;

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        ln_mahasiswa = (LinearLayout) view.findViewById(R.id.ln_mahasiswa);
        ln_dosen = (LinearLayout)view.findViewById(R.id.ln_dosen);
        ln_krs = (LinearLayout) view.findViewById(R.id.ln_krs);
        ln_keuangan = (LinearLayout) view.findViewById(R.id.ln_keuangan);
        ln_khs = (LinearLayout)view.findViewById(R.id.ln_khs);
        ln_transkip = (LinearLayout)view.findViewById(R.id.ln_transkip);
        count = view.findViewById(R.id.count);

        id = getActivity().getIntent().getStringExtra(TAG_ID_MHS);






        ln_krs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), KrsActivity.class);
                intent.putExtra(TAG_ID_MHS, id);
                getActivity().startActivity(intent);
            }
        });


        ln_keuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TagihanActivity.class);
                intent.putExtra(TAG_ID_MHS, id);
                getActivity().startActivity(intent);

            }
        });


        ln_khs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PeriodeActivity.class);
                intent.putExtra(TAG_ID_MHS, id);
                getActivity().startActivity(intent);
            }
        });

        ln_transkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), TranskipActivity.class);
                intent.putExtra(TAG_ID_MHS, id);
                Constant.ID_MHS = String.valueOf(Integer.parseInt(id));
                Log.e("nim",""+ Integer.parseInt(id));
                getActivity().startActivity(intent);
            }
        });

    }




    @Override
    public void onRefresh() {

    }
}
