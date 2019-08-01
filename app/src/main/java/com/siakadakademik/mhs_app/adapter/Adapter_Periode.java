package com.siakadakademik.mhs_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siakadakademik.mhs_app.Constant;
import com.siakadakademik.mhs_app.KhsActivity;
import com.siakadakademik.mhs_app.Login;
import com.siakadakademik.mhs_app.R;
import com.siakadakademik.mhs_app.data.PeriodeData;

import java.util.List;

public class Adapter_Periode extends RecyclerView.Adapter<Adapter_Periode.ViewHolder> {
    private Context context;
    private List<PeriodeData> arrayofperiode;
    PeriodeData periodeData;
    SharedPreferences sharedPreferences;
    String id = Login.my_shared_preferences;

    public static final String TAG_ID = "id";


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idperiode;
        public TextView periode;
        public RelativeLayout relativeLayout;

        public ViewHolder(View view){
            super(view);

            idperiode = view.findViewById(R.id.idperiode);
            periode = view.findViewById(R.id.periode);
            relativeLayout = view.findViewById(R.id.linearLayout);

            sharedPreferences =context.getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
            id = sharedPreferences.getString(TAG_ID,id);


        }
    }

    public Adapter_Periode(Context mContext, List<PeriodeData>arrayofperiode){
        this.context = mContext;
        this.arrayofperiode = arrayofperiode;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_periode,parent,false);
        return  new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        periodeData = arrayofperiode.get(position);

        Typeface font1 = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface font2 = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf");

        holder.idperiode.setTypeface(font1);
        holder.periode.setTypeface(font1);

        holder.idperiode.setText(periodeData.getIdperiode());
        holder.periode.setText(periodeData.getPeriode());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                periodeData = arrayofperiode.get(position);
                String idp = periodeData.getIdperiode();
                String namaperiode = periodeData.getPeriode();
                Log.e("idperiode", "" + idp);
                Log.e("namaperiode",""+namaperiode);
                Constant.NAMAPERIODE = namaperiode;
                Constant.IDPERIODE = Integer.parseInt(periodeData.getIdperiode());
                Intent intent =new Intent(context, KhsActivity.class);
                intent.putExtra(TAG_ID,id);
                Log.e("idmahasiswa",""+id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return arrayofperiode.size();
    }

}
