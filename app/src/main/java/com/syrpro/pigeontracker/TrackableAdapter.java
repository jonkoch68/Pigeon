package com.syrpro.pigeontracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TrackableAdapter extends RecyclerView.Adapter<TrackableAdapter.TrackableHolder> {

    List<Trackable> data;
    Context context;

    static final String DOC = "Document";

    public TrackableAdapter(List<Trackable> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public TrackableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.trackable_view_holder, parent,false);
        return new TrackableHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackableHolder holder, int position) {
        if(data.get(position).isFavorite()){
            holder.isFavorite = true;
            holder.favoriteToggleButton.setImageResource(R.drawable.ic_baseline_star_24);
        }else{
            holder.isFavorite = false;
            holder.favoriteToggleButton.setImageResource(R.drawable.ic_baseline_star_border_24);
        }
        holder.isPackage = data.get(position).isPackage();
        holder.trackableTitle.setText(data.get(position).getName());
        holder.docName = data.get(position).getDoc();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TrackableHolder extends  RecyclerView.ViewHolder{

        boolean isPackage;
        ImageButton favoriteToggleButton;
        TextView trackableTitle;
        boolean isFavorite;
        String docName;


        public TrackableHolder(@NonNull View itemView) {
            super(itemView);
            favoriteToggleButton = itemView.findViewById(R.id.trackableFavoriteButton);
            trackableTitle = itemView.findViewById(R.id.trackableHolderTextView);
            favoriteToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isFavorite){
                        isFavorite =false;
                        favoriteToggleButton.setImageResource(R.drawable.ic_baseline_star_border_24);
                        FirebaseFirestore.getInstance().document(docName).update("Favorite", false);
                    }else {
                        isFavorite = true;
                        favoriteToggleButton.setImageResource(R.drawable.ic_baseline_star_24);
                        FirebaseFirestore.getInstance().document(docName).update("Favorite",true);
                    }
                }
            });
            trackableTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isPackage){
                        Intent i = new Intent(TrackableAdapter.this.context,PackageViewerActivity.class);
                        i.putExtra(DOC,docName);
                        context.startActivity(i);
                    }
                    else{
                        //
                    }
                }
            });
            //OnLongClickToEdit
        }
    }
}
