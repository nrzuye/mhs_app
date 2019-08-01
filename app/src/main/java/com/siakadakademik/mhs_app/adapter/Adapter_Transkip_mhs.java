package com.siakadakademik.mhs_app.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.siakadakademik.mhs_app.R;
import com.siakadakademik.mhs_app.data.TranskipDataList;

import java.util.List;

/**
 * Created by xyzz on 4/19/2018.
 */

public class Adapter_Transkip_mhs extends ArrayAdapter<TranskipDataList> {

    private Activity activity;
    private List<TranskipDataList> itemstranskiplist;
    private TranskipDataList objtranskiplistBean;
    private int row;


    public Adapter_Transkip_mhs (AppCompatActivity act, int resource,List<TranskipDataList> arrayList, int columnWidth){
        super(act,resource,arrayList);
        this.activity = act;
        this.row= resource;
        this.itemstranskiplist = arrayList;
    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent){
        View view = convertView;
        ViewHolderTranskip holder;
        if (view == null){
            LayoutInflater inflater= (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =inflater.inflate(row,null);

            holder = new ViewHolderTranskip();
            view.setTag(holder);
        }else {
            holder = (ViewHolderTranskip) view.getTag();
        }

        if ((itemstranskiplist ==null) || ((position + 1) > itemstranskiplist.size()))
            return view;

        objtranskiplistBean = itemstranskiplist.get(position);

        holder.txt_transkipkode = (TextView)view.findViewById(R.id.txt_transkipkode);
        holder.txt_transkipnama = (TextView)view.findViewById(R.id.txt_transkipnama);
        holder.txt_kurikulum = (TextView)view.findViewById(R.id.txt_kurikulum);
        holder.txt_nilai_huruf = (TextView)view.findViewById(R.id.txt_nilai_huruf);

        holder.txt_transkipkode.setText(objtranskiplistBean.getKodemk().toString());
        holder.txt_transkipnama.setText(objtranskiplistBean.getNamamk().toString());
        holder.txt_kurikulum.setText("Kurikulum : "+objtranskiplistBean.getKurikulum().toString());
        holder.txt_nilai_huruf.setText("Grade : "+objtranskiplistBean.getNilai_huruf().toString());

        return view;
    }


    public class ViewHolderTranskip{

        public TextView txt_transkipkode;
        public TextView txt_transkipnama;
        public TextView txt_kurikulum;
        public TextView txt_nilai_huruf;
        public TextView txt_nilai_angka;

    }


}
