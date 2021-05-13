package com.syrpro.pigeontracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PackageHistoryAdapter extends RecyclerView.Adapter<PackageHistoryAdapter.PackageHistoryHolder> {

    List<String> data;
    Context context;

    public PackageHistoryAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public PackageHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.package_history_holder, parent,false);
        return new PackageHistoryAdapter.PackageHistoryHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PackageHistoryHolder holder, int position) {
            holder.historyView.setText(data.get(position));
    }

    class PackageHistoryHolder extends RecyclerView.ViewHolder{

        TextView historyView;
        public PackageHistoryHolder(@NonNull View itemView) {
            super(itemView);
            historyView = itemView.findViewById(R.id.packageHistoryEvent);
        }
    }
}
