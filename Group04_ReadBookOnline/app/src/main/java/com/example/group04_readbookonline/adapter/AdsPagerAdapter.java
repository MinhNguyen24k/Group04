package com.example.group04_readbookonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group04_readbookonline.R;

import java.util.List;

public class AdsPagerAdapter extends RecyclerView.Adapter<AdsPagerAdapter.ViewHolder> {

    private Context context;
    private List<Integer> imageResIds;

    public AdsPagerAdapter(Context context, List<Integer> imageResIds) {
        this.context = context;
        this.imageResIds = imageResIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ad_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageViewAd.setImageResource(imageResIds.get(position));
    }

    @Override
    public int getItemCount() {
        return imageResIds.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAd;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAd = itemView.findViewById(R.id.imageViewAd);
        }
    }
}
