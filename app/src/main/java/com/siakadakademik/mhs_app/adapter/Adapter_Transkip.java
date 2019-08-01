package com.siakadakademik.mhs_app.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siakadakademik.mhs_app.R;
import com.siakadakademik.mhs_app.data.TranskipDataList;

import java.util.List;

public class Adapter_Transkip extends RecyclerView.Adapter<Adapter_Transkip.ViewHolder> {

    private Context context;
    private List<TranskipDataList> arrayoftranskip;
    TranskipDataList transkipDataList;


    public class ViewHolder extends  RecyclerView.ViewHolder {
        public TextView kodemk;
        public TextView namamk;
        public TextView nilaihuruf;
        public LinearLayout linearLayout;

        public ViewHolder(View view){
            super(view);

            kodemk =(TextView) view.findViewById(R.id.txt_transkipkode2);
            namamk = (TextView) view.findViewById(R.id.txt_transkipnama2);
            nilaihuruf = (TextView) view.findViewById(R.id.txt_nilai_huruf2);

        }
    }

    public Adapter_Transkip(Context mContext, List<TranskipDataList>arrayoftranskip){
        this.context = mContext;
        this.arrayoftranskip= arrayoftranskip;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_transkip_list,parent,false);
        return  new ViewHolder(itemView);
    }

    @Override
    public  void onBindViewHolder(final  ViewHolder holder, final int position){
        transkipDataList = arrayoftranskip.get(position);

        Typeface font1 = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface font2 = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf");
        holder.kodemk.setTypeface(font1);
        holder.namamk.setTypeface(font1);
        holder.nilaihuruf.setTypeface(font2);

//        if (transkipDataList.getNilai_huruf().equals("A")){
//            holder.nilaihuruf.setTextColor(Color.parseColor("#FF15E70A"));
//        }

        holder.kodemk.setText(transkipDataList.getKodemk());
        holder.namamk.setText(transkipDataList.getNamamk());
        holder.nilaihuruf.setText("Grade : "+transkipDataList.getNilai_huruf());



    }
    @Override
    public int getItemCount(){
        return arrayoftranskip.size();
    }

}
