package com.syrpro.pigeontracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    Button newDeploymentBtn, newPackageBtn;
    ImageView accountBtn;
    TextInputEditText trackingNumberEditText;
    RecyclerView favoritesView, trackableView;
    SwipeRefreshLayout refreshLayout;
    static final String TRACK_EXTRA = "tracking_num";
    FavoritesAdapter favoritesAdapter;
    TrackableAdapter trackableAdapter;
    List<Favorite> favoritesData;
    List<Trackable> trackableData;
    private final List<ListenerRegistration> subscribedDocList= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        refreshLayout = findViewById(R.id.dashboardSwipeRefreshLayout);
        newDeploymentBtn = findViewById(R.id.newDeploymentButton);
        newPackageBtn = findViewById(R.id.newPackageButton);
        accountBtn = findViewById(R.id.accountBtn);
        trackingNumberEditText = findViewById(R.id.trackingNumberInputText);

        //Init Favorites
        favoritesView = findViewById(R.id.favoritesView);
        favoritesData = new ArrayList<>();
        favoritesAdapter = new FavoritesAdapter(favoritesData,DashboardActivity.this);
        favoritesView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, RecyclerView.HORIZONTAL, false));
        favoritesView.setAdapter(favoritesAdapter);
        getFavoritesData();

        //Init Trackable
        trackableView = findViewById(R.id.trackableView);
        trackableData = new ArrayList<>();
        trackableAdapter = new TrackableAdapter(trackableData, DashboardActivity.this);
        trackableView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this,RecyclerView.VERTICAL,false));
        trackableView.setAdapter(trackableAdapter);
        getTrackableData();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFavoritesData();
                getTrackableData();
                refreshLayout.setRefreshing(false);
            }
        });

        newPackageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, PackageCreatorActivity.class);
                String trackingNumber = trackingNumberEditText.getText().toString();
                i.putExtra(TRACK_EXTRA,trackingNumber);
                startActivity(i);
            }
        });

        newDeploymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(DashboardActivity.this, DeploymentCreatorActivity.class);
                String trackingNumber = trackingNumberEditText.getText().toString();
                i.putExtra(TRACK_EXTRA,trackingNumber);
                startActivity(i);
            }
        });

    }
    private void getFavoritesData(){
        if(!favoritesData.isEmpty()){
            favoritesData.clear();
        }
        String uID = FirebaseAuth.getInstance().getUid();
        Task<QuerySnapshot> task1 = FirebaseFirestore.getInstance().collection(PackageCreatorActivity.COLLECTION_PACKAGE)
                .whereEqualTo("Owner", uID)
                .whereEqualTo("Favorite",true).get();
        while (!task1.isComplete()){} //Waits for query to finish
        for( DocumentSnapshot doc : task1.getResult().getDocuments()){
            String title,tID,status;
            title = doc.get("Nickname").toString();
            tID = doc.get("TrackingID").toString();
            Task<DocumentSnapshot> task2 = FirebaseFirestore.getInstance().collection(PackageCreatorActivity.COLLECTION_TRACKING).document(tID).get();
            while (!task2.isComplete()){}
            if(task2.isSuccessful() && task2.getResult().exists()){
                status = (String) task2.getResult().getData().get("Status");
            } else{
                status = "unknown";
            }
            Favorite data = new Favorite(title,status);
            favoritesData.add(data);
        }
        favoritesAdapter.notifyDataSetChanged();
    }
    private void getTrackableData(){
        if(!trackableData.isEmpty()){
            trackableData.clear();
        }
        String uID = FirebaseAuth.getInstance().getUid();
        Task<QuerySnapshot> task1 = FirebaseFirestore.getInstance().collection(PackageCreatorActivity.COLLECTION_PACKAGE)
                .whereEqualTo("Owner", uID).get();
        while(!task1.isComplete()){}
        for(DocumentSnapshot doc : task1.getResult().getDocuments()){
            trackableData.add(new Trackable(doc.get("Nickname").toString(), PackageCreatorActivity.COLLECTION_PACKAGE+"/"+doc.getId(), (boolean)doc.get("Favorite"),true));
        }
        Task<QuerySnapshot> task2 = FirebaseFirestore.getInstance().collection("Deployment")
                .whereEqualTo("Owner", uID).get();
        while (!task2.isComplete()){}
        for(DocumentSnapshot doc : task2.getResult().getDocuments()){
            trackableData.add(new Trackable(doc.get("Nickname").toString(), "Deployment/"+doc.getId(), (boolean)doc.get("Favorite"),false));
        }
        trackableAdapter.notifyDataSetChanged();
    }

} //end of DisplayActivity