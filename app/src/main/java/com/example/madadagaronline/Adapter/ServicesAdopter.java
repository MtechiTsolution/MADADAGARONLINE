package com.example.madadagaronline.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.madadagaronline.ListJobs_Act;
import com.example.madadagaronline.Models.ScreenItem;
import com.example.madadagaronline.R;

import java.util.ArrayList;



public class ServicesAdopter  extends  RecyclerView.Adapter<ServicesAdopter.holder>{
    Context context;
    ArrayList<ScreenItem> arrayList;
    public ServicesAdopter(Context context, ArrayList<ScreenItem> arrayList){
        this.context=context;
        this.arrayList=arrayList;

    }
    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater Inflater=LayoutInflater.from(parent.getContext());
        View view=Inflater.inflate(R.layout.card_services,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {
        holder.text1.setText(arrayList.get(position).getTitle());
        holder.text2.setText(arrayList.get(position).getDescription());
        holder.imgcontaxt.setImageResource(arrayList.get(position).getScreenImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListJobs_Act.catogory=arrayList.get(position).getTitle();
                Intent intent=new Intent(context, ListJobs_Act.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder{
         ImageView imgcontaxt;
        TextView text1,text2;
        public holder(@NonNull View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.servititle);
            text2=itemView.findViewById(R.id.servcedesc);
            imgcontaxt=itemView.findViewById(R.id.img_plumpingk);
        }
    }
}
