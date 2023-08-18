package com.example.whatistheicon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class IconSelectionActivity extends AppCompatActivity {

    private Spinner spinner;
    private ArrayList<IconSign> signs;
    private SpinnerAdapter adapter;
    private FirebaseServices fbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_selection);

        spinner = findViewById(R.id.spIcons);
        signs = new ArrayList<>();
        fbs = FirebaseServices.getInstance();

        getSigns();
    }

    private void getSigns() {
        fbs.getFire().collection("icon_signs").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    IconSign is = dataSnapshot.toObject(IconSign.class);
                    signs.add(is);
                }
/*
*         fbs.getFire().collection("cars").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    Car car= dataSnapshot.toObject(Car.class);
                    list.add(car);
                }


                myAdapter.notifyDataSetChanged();
            }*/
                adapter = new IconSpinnerAdapter(getApplicationContext(), signs);
                spinner.setAdapter(adapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}