package com.syrpro.pigeontracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class PackageCreatorActivity extends AppCompatActivity {

    static final String COLLECTION_TRACKING = "TrackingID";
    static final String COLLECTION_PACKAGE = "Package";

    TextInputEditText trackingNumberEdtBox, nicknameEdtBox, descriptionEdtBox, notesEdtBox;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_creator);

        trackingNumberEdtBox = findViewById(R.id.packageTrackingNumberEdtTxt);
        nicknameEdtBox = findViewById(R.id.packageNickNameEdtTxt);
        descriptionEdtBox = findViewById(R.id.packageDescriptionEdtTxt);
        notesEdtBox = findViewById(R.id.packageNoteEdtTxt);
        submitBtn = findViewById(R.id.packageSubmitBtn);

        trackingNumberEdtBox.setText(getIntent().getCharSequenceExtra(DashboardActivity.TRACK_EXTRA));

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> values = new HashMap<>();
                values.put("TrackingID", trackingNumberEdtBox.getText().toString());
                FirebaseFirestore.getInstance()
                        .collection(COLLECTION_TRACKING)
                        .document(trackingNumberEdtBox.getText().toString())
                        .set(values)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                        values.put("Nickname", nicknameEdtBox.getText().toString());
                        values.put("Notes", notesEdtBox.getText().toString());
                        values.put("Description", descriptionEdtBox.getText().toString());
                        values.put("Owner", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        values.put("Favorite", false);

                        FirebaseFirestore.getInstance()
                                .collection(COLLECTION_PACKAGE)
                                .document()
                                .set(values, SetOptions.merge())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(PackageCreatorActivity.this, "Package Uploaded",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(PackageCreatorActivity.this, DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                        }else{
                                            Toast.makeText(PackageCreatorActivity.this, "Error Sending Package",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        }else{
                            Toast.makeText(PackageCreatorActivity.this, "Error Sending Package",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}