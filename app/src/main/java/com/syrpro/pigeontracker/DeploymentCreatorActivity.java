package com.syrpro.pigeontracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DeploymentCreatorActivity extends AppCompatActivity {

    private TextInputEditText nicknameEdt, descriptionEdt, notesEdt, trackingEdt;
    private Button submitButton;
    private RecyclerView recyclerView;
    private TextInputLayout editLayout;
    private List<String> tracking_ids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deployment_creator);

        nicknameEdt = findViewById(R.id.deploymentNicknameEdit);
        descriptionEdt = findViewById(R.id.deploymentDescriptionEdit);
        notesEdt = findViewById(R.id.deploymentNoteEdtTxt);
        editLayout = findViewById(R.id.deploymentTrackingLayout);
        submitButton = findViewById(R.id.deploymentSumbitBtn);
        trackingEdt = findViewById(R.id.deploymentTrackingEdit);
        recyclerView = findViewById(R.id.deploymentRecyclerView);
        tracking_ids = new ArrayList<>();
        trackingEdt.setText(getIntent().getCharSequenceExtra(DashboardActivity.TRACK_EXTRA).toString());
        DeploymentAdapter adapter = new DeploymentAdapter(tracking_ids,DeploymentCreatorActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(DeploymentCreatorActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> values = new HashMap<>();
                Map<String,String> tracker = new HashMap<>();
                values.put("Nickname", nicknameEdt.getText().toString());
                values.put("Description", descriptionEdt.getText().toString());
                values.put("Owner", FirebaseAuth.getInstance().getUid());
                values.put("TrackingIDs", tracking_ids);
                for(String id : tracking_ids){
                    tracker.put("TrackingID",id);
                    Task t = FirebaseFirestore.getInstance().collection("TrackingID").document(id).set(tracker, SetOptions.merge());
                    while(!t.isComplete()){}
                    if(!t.isSuccessful()){
                        System.out.println("Error couldn't finish uploading");
                        return;
                    }
                }
                FirebaseFirestore.getInstance().collection("Deployment").document().set(values, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(DeploymentCreatorActivity.this, "Package Uploaded",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DeploymentCreatorActivity.this, DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }
                    }
                });
            }
        });
        editLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tracking_ids.add(trackingEdt.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

    }
}