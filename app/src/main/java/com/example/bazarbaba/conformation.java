package com.example.bazarbaba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class conformation extends AppCompatActivity {
    public TextView textstatus,otp;
    private List<Product_details> itemList,cartList;
    private ListView list;
    private List ans;
    private ProgressDialog progressDialog;

    private String uid,ot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation);

        ans= new ArrayList<String>();
        textstatus=findViewById(R.id.confirmation_processing_text);
        Intent intent=getIntent();
        cartList=intent.getParcelableArrayListExtra("List");
        uid=intent.getStringExtra("uid");
        ot=intent.getStringExtra("orderid");
        otp=findViewById(R.id.confirmation_otp);
        for(Product_details item:cartList)
        {
            ans.add(item.getName());
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final DocumentReference docRef = db.collection("Orders").document(uid);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("abc", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("abc", "Current data: " + snapshot.getData());
                    textstatus.setText((String)snapshot.get("status"));
                    otp.setText(ot);
                    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,-1);

                } else {
                    Log.d("abc", "Current data: null");
                }
            }
        });


//        list.setAdapter(list);

    }
}
