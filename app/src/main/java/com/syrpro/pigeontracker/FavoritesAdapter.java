package com.syrpro.pigeontracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesHolder> {

    List<Favorite> data;
    Context context;

    public FavoritesAdapter(List<Favorite> data, Context context){
        this.data = data;
        this.context = context;
    }
    @NonNull
    @Override
    public FavoritesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.favorite_view_holder, parent,false);
        return new FavoritesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.status.setText(data.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FavoritesHolder extends RecyclerView.ViewHolder{
        TextView title,status;
        public FavoritesHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.favoriteHolderTitle);
            status = itemView.findViewById(R.id.favoriteHolderStatus);
        }
    }
}
