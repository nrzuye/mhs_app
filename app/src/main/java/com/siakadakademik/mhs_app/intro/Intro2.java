package com.siakadakademik.mhs_app.intro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siakadakademik.mhs_app.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Intro2 extends Fragment {


    public Intro2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro2, container, false);
    }

}
