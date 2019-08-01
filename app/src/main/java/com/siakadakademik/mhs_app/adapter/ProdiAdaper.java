package com.siakadakademik.mhs_app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.siakadakademik.mhs_app.R;
import com.siakadakademik.mhs_app.data.ProdiData;

import java.util.List;

/**
 * Created by xyzz on 1/22/2018.
 */

public class ProdiAdaper extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ProdiData> prodiItems;


    public ProdiAdaper (Activity activity, List<ProdiData> prodiItems){
        this.activity = activity;
        this.prodiItems = prodiItems;
    }

    @Override
    public int getCount() {return prodiItems.size(); }
    @Override
    public Object getItem(int location) {return prodiItems.get(location); }

    @Override
    public long getItemId(int position) {return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_one_line, null);

        TextView nama_prodi = (TextView) convertView.findViewById(R.id.id_line_1);

        ProdiData prodi = prodiItems.get(position);
        nama_prodi.setText(prodi.getNama_prodi());


        return convertView;
    }
}
