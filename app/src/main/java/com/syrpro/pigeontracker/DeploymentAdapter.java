package com.syrpro.pigeontracker;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class DeploymentAdapter extends  RecyclerView.Adapter<DeploymentAdapter.DeploymentHolder>{


    List<String> data;
    Context context;

    public DeploymentAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public DeploymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.deployment_view_holder, parent,false);
        return new DeploymentAdapter.DeploymentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeploymentHolder holder, int position) {
        holder.trackingView.setText(data.get(position));
        holder.pos = position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DeploymentHolder extends RecyclerView.ViewHolder{

        TextView trackingView;
        int pos = 0;

        public DeploymentHolder(@NonNull View itemView) {
            super(itemView);
            trackingView = itemView.findViewById(R.id.deploymentHolderText);
            //Add in long click listener here to remove items
        }
    }
}
