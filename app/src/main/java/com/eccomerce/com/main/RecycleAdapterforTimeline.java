package com.eccomerce.com.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eccomerce.com.R;
import com.eccomerce.com.model.modeltimeline;
import com.eccomerce.com.model.modelvip;

import java.util.ArrayList;

public class RecycleAdapterforTimeline extends RecyclerView.Adapter<RecycleAdapterforTimeline.timeline_viewholder> {

    ArrayList<modeltimeline> list_timeline;
    Context cntx;
    @NonNull
    @Override
    public timeline_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vipcard, parent, false);
        final RecycleAdapterforTimeline.timeline_viewholder view = new RecycleAdapterforTimeline.timeline_viewholder(v);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull timeline_viewholder holder, int position) {

    }

    public RecycleAdapterforTimeline(ArrayList<modeltimeline> items, Context ctx) {
        this.list_timeline = items;
        this.cntx = ctx;
    }


    @Override
    public int getItemCount() {
        return list_timeline.size();
    }

    public void setData(ArrayList<modeltimeline> timelinedata) {
        list_timeline = timelinedata;
        notifyDataSetChanged();
    }

    public class timeline_viewholder   extends RecyclerView.ViewHolder {
        TextView title, price;
        ImageView image;    RelativeLayout rl;
        public timeline_viewholder(@NonNull View itemView) {
            super(itemView);

            rl=(RelativeLayout)itemView.findViewById(R.id.disc);
            image = (ImageView) itemView.findViewById(R.id.imageMain);
            title = (TextView) itemView.findViewById(R.id.title);

        }
    }
}
