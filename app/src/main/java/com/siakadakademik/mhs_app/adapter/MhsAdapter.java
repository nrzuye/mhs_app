package com.siakadakademik.mhs_app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.siakadakademik.mhs_app.R;
import com.siakadakademik.mhs_app.data.MhsData;

import java.util.List;

/**
 * Created by xyzz on 11/16/2017.
 */

public class MhsAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<MhsData> mhsItems;

    public MhsAdapter(Activity activity, List<MhsData> mhsItems){
        this.activity = activity;
        this.mhsItems = mhsItems;
    }

    @Override
    public int getCount(){return mhsItems.size(); }
    @Override
    public Object getItem(int location) {return mhsItems.get(location); }

    @Override
    public long getItemId(int position) {return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView ==null)
            convertView = inflater.inflate(R.layout.list_row_mhs, null);

        TextView id_mhs = (TextView) convertView.findViewById(R.id.id_mhs);
        TextView nama_mhs = (TextView) convertView.findViewById(R.id.mhs_nama);


        MhsData mhs = mhsItems.get(position);
         id_mhs.setText(mhs.getId_mhs());
         nama_mhs.setText(mhs.getNama_mhs());


         return convertView;

    }

}
