package com.syrpro.pigeontracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PackageViewerActivity extends AppCompatActivity {

    TextView title, description, notes, status;
    RecyclerView historyView;
    List<String> history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_viewer);

        title = findViewById(R.id.packageViewerTitle);
        description = findViewById(R.id.packageViewerDesc);
        notes = findViewById(R.id.packageViewNote);
        status = findViewById(R.id.packageViewerStatus);
        historyView = findViewById(R.id.packageViewShippingHistory);


        Task<DocumentSnapshot> doc = FirebaseFirestore.getInstance().document(getIntent().getCharSequenceExtra(TrackableAdapter.DOC).toString()).get();
        while(!doc.isComplete()){}
        title.setText(doc.getResult().get("Nickname").toString());
        description.setText(doc.getResult().get("Description").toString());
        notes.setText(doc.getResult().get("Notes").toString());
        String tID = doc.getResult().get("TrackingID").toString();

        Task<DocumentSnapshot> tracker = FirebaseFirestore.getInstance().collection("TrackingID").document(tID).get();
        while(!tracker.isComplete()){}
        System.out.println(tracker.getResult());
        if(tracker.getResult().get("Status") != null){
            status.setText(tracker.getResult().get("Status").toString());
        }
        if(tracker.getResult().get("History") != null) {
            String[] hist = tracker.getResult().get("History").toString().split("Event No");
            history = Arrays.asList(hist);
            Collections.reverse(history);
        }else {
            history = new ArrayList<>();
        }

        PackageHistoryAdapter adapter = new PackageHistoryAdapter(history,PackageViewerActivity.this);
        historyView.setLayoutManager(new LinearLayoutManager(PackageViewerActivity.this,RecyclerView.VERTICAL,false));
        historyView.setAdapter(adapter);

    }
}