package com.siakadakademik.mhs_app.adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siakadakademik.mhs_app.Login;
import com.siakadakademik.mhs_app.R;
import com.siakadakademik.mhs_app.data.KrsDataList;

import java.util.List;

public class Krs_Adapter extends RecyclerView.Adapter<Krs_Adapter.ViewHolder>{
    private Context context;
    private List<KrsDataList> arrayofkrs;
    KrsDataList krsDataList;
    SharedPreferences sharedPreferences;
    String id = Login.my_shared_preferences;

    public static final String TAG_ID= "id";

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nim,nama_mahasiswa,kodemk,namamk,sksmk,nama_kelas,pengampu,idperiode;
        public LinearLayout linearLayout;

        public ViewHolder(View view){
            super(view);

            nim = view.findViewById(R.id.nim);
            nama_mahasiswa = view.findViewById(R.id.nama_mahasiswa);
            kodemk = view.findViewById(R.id.kodemk);
            namamk = view.findViewById(R.id.namamk);
            sksmk = view.findViewById(R.id.sksmk);
            nama_kelas = view.findViewById(R.id.nama_kelas);
            pengampu =view.findViewById(R.id.pengampu);
            idperiode=view.findViewById(R.id.idperiode);

            linearLayout =view.findViewById(R.id.linearLayout);
            sharedPreferences = context.getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
            id = sharedPreferences.getString(TAG_ID,id);
        }
    }

    public Krs_Adapter(Context mContext,List<KrsDataList>arrayofkrs){
        this.context = mContext;
        this.arrayofkrs = arrayofkrs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_krs, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        krsDataList = arrayofkrs.get(position);

        Typeface font1 = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface font2 = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf");

        holder.nim.setText(krsDataList.getNim());
        holder.nama_mahasiswa.setText(krsDataList.getNama_mahasiswa());
        holder.kodemk.setText(krsDataList.getKodemk());
        holder.namamk.setText(krsDataList.getNamamk());
        holder.sksmk.setText("Sks : "+krsDataList.getSksmk());
        holder.nama_kelas.setText("Kelas : "+krsDataList.getNamakelas());
        holder.pengampu.setText("Pengampu : "+krsDataList.getPengampu());
        holder.idperiode.setText(krsDataList.getIdperiode());

    }

    @Override
    public int getItemCount(){return arrayofkrs.size();}

}

