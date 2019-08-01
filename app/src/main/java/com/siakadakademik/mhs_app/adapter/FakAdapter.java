package com.siakadakademik.mhs_app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.siakadakademik.mhs_app.R;
import com.siakadakademik.mhs_app.data.FakData;

import java.util.List;

/**
 * Created by xyzz on 1/21/2018.
 */

public class FakAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<FakData> fakItems;


    public FakAdapter (Activity activity, List<FakData> fakItems){
        this.activity = activity;
        this.fakItems = fakItems;
    }

    @Override
    public int getCount() {return fakItems.size(); }
    @Override
    public Object getItem(int location) {return fakItems.get(location); }

    @Override
    public long getItemId(int position) {return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_one_line, null);

        TextView id_fak = (TextView) convertView.findViewById(R.id.id_line_1); //tidak di munculkan
        TextView fakultas = (TextView) convertView.findViewById(R.id.id_line_1);

        FakData fak = fakItems.get(position);
        id_fak.setText(fak.getId_fak());
        fakultas.setText(fak.getFakultas());


        return convertView;
    }

}
