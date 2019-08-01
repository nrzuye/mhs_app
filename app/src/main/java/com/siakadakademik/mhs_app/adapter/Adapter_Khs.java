package com.siakadakademik.mhs_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siakadakademik.mhs_app.R;
import com.siakadakademik.mhs_app.data.KhsDataList;

import java.util.List;

public class Adapter_Khs extends RecyclerView.Adapter<Adapter_Khs.ViewHolder> {
    private Context context;
    private List<KhsDataList> arrayofkhs;
    KhsDataList khsDataList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_khskodemk,
                        txt_khsnamamk,
                        txt_sks,
                        txt_pengampu,
                        txt_nilai;

        public LinearLayout linearLayout;
        public ViewHolder (View view){
            super(view);

            txt_khskodemk = view.findViewById(R.id.txt_khskodemk);
            txt_khsnamamk = view.findViewById(R.id.txt_khsnamamk);
            txt_sks = view.findViewById(R.id.txt_sks);
            txt_pengampu = view.findViewById(R.id.txt_pengampu);
            txt_nilai = view.findViewById(R.id.txt_nilai);
        }
    }
    public Adapter_Khs (Context mContext, List<KhsDataList>arrayofkhs){
        this.context = mContext;
        this.arrayofkhs = arrayofkhs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_khs,parent,false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        khsDataList = arrayofkhs.get(position);

        holder.txt_khskodemk.setText(khsDataList.getKodemk());
        holder.txt_khsnamamk.setText(khsDataList.getNamamk());
        holder.txt_sks.setText("Sks : "+khsDataList.getSksmk());
        holder.txt_pengampu.setText("Pengampu : "+khsDataList.getPengampu());
        holder.txt_nilai.setText("Grade : "+khsDataList.getNilai());
    }
    @Override
    public int getItemCount(){
        return arrayofkhs.size();
    }


}
