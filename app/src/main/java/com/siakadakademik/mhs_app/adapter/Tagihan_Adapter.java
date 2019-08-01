package com.siakadakademik.mhs_app.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siakadakademik.mhs_app.Login;
import com.siakadakademik.mhs_app.R;
import com.siakadakademik.mhs_app.data.TagihanData;

import java.util.List;

public class Tagihan_Adapter extends RecyclerView.Adapter<Tagihan_Adapter.ViewHolder> {
    private Context context;
    private List<TagihanData> arrayofTagihan;
    TagihanData tagihanData;
    SharedPreferences sharedPreferences;
    String id = Login.my_shared_preferences;

    public static final String TAG_ID = "id";

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nim,nama_mhs,prodi,kode_tagihan,nama_tagihan,kode_mk,nama_mk,jml_tagihan,status_taghan,periode,idperiode;
        public LinearLayout linearLayout;

        public ViewHolder(View view){
            super(view);

            nim = view.findViewById(R.id.nim);
            nama_mhs = view.findViewById(R.id.nama_mhs);
            prodi = view.findViewById(R.id.prodi);
            kode_tagihan = view.findViewById(R.id.kode_tagihan);
            nama_tagihan = view.findViewById(R.id.nama_tagihan);
            kode_mk = view.findViewById(R.id.kodemk);
            nama_mk = view.findViewById(R.id.namamk);
            jml_tagihan = view.findViewById(R.id.jml_tagihan);
            status_taghan = view.findViewById(R.id.status_tagihan);
            periode = view.findViewById(R.id.periode);
            idperiode = view.findViewById(R.id.idperiode);

            linearLayout = view.findViewById(R.id.linearLayout);
            sharedPreferences = context.getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
            id = sharedPreferences.getString(TAG_ID,id);
        }
    }

    public Tagihan_Adapter(Context mContext, List<TagihanData>arrayofTagihan){
        this.context = mContext;
        this.arrayofTagihan = arrayofTagihan;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_tagihan,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        tagihanData = arrayofTagihan.get(position);

        holder.nim.setText(tagihanData.getNim());
        holder.nama_mhs.setText(tagihanData.getNama_mhs());
        holder.prodi.setText(tagihanData.getProdi());
        holder.kode_tagihan.setText(tagihanData.getKode_tagihan());
        holder.nama_tagihan.setText(tagihanData.getNama_tagihan());
        holder.kode_mk.setText(tagihanData.getKode_mk());
        holder.nama_mk.setText(tagihanData.getNama_mk());
        holder.jml_tagihan.setText("Rp. "+tagihanData.getJml_tagihan());
        holder.status_taghan.setText(tagihanData.getStatus_tagihan());
        holder.periode.setText(tagihanData.getPeriode());
        holder.idperiode.setText(tagihanData.getIdperiode());
    }
    @Override
    public int getItemCount(){
        return  arrayofTagihan.size();
    }


}
