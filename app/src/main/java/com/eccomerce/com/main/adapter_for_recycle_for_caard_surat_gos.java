package com.eccomerce.com.main;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.eccomerce.com.R;
import com.eccomerce.com.model.model_surat_gos;
import com.eccomerce.com.shopin.shopadd;

import java.util.ArrayList;
import java.util.List;


public class adapter_for_recycle_for_caard_surat_gos extends RecyclerView.Adapter<adapter_for_recycle_for_caard_surat_gos.Reklama_viewholder> {
    static ArrayList<model_surat_gos> data;
    Context cntx;
    List<String> idlar = new ArrayList<>();
    String table;


    public class Reklama_viewholder extends RecyclerView.ViewHolder {
        ImageView imageView1, imageView2;

        public Reklama_viewholder(View itemView) {

            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.image_card_surat_gosh);
            imageView2 = (ImageView) itemView.findViewById(R.id.image_delete_card_surat_gosh);

            //  time=(TextView)itemView.findViewById(id.item_time);
            // description=(TextView)itemView.findViewById(id.item_description);

        }
    }

    public adapter_for_recycle_for_caard_surat_gos(ArrayList <model_surat_gos> items, Context ctx) {
        this.data = items;
        this.cntx = ctx;
    }

    @Override
    public Reklama_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_surat_gos, parent, false);
        final Reklama_viewholder view = new Reklama_viewholder(v);
        return view;

    }

    @Override
    public void onBindViewHolder(Reklama_viewholder holder, final int position) {
        Log.d("image.pos",""+position);
        Glide.with(cntx)
                .load(data.get(position).getUri())
                .into(holder.imageView1);
        holder.imageView2.setImageResource(R.drawable.delete);

       holder.imageView2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               data.remove(position);
               notifyDataSetChanged();
               if(position==1) {
                   shopadd.image = "";
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public  void setData(ArrayList<model_surat_gos> list){
        this.data=list;
        notifyDataSetChanged();
        Log.d("data","get");

    }

}
