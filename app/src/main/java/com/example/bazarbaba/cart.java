package com.example.bazarbaba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class cart extends AppCompatActivity implements Cart_adapter.MyClickListener{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference booksref = db.collection("Items");
    private List<Product_details> itemList,cartList;
    private Product_Adapter adapter;
    private Cart_adapter adapter2;
    private EditText search_et;
    private TextView pri,dpri;
    private FirebaseAuth mAuth;
    private String uid;
    private ProgressDialog progressDialog;
    private Button pickup,dilevary;
    private String token;
//    private String uid;
    double s=0,ds=0;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent=new Intent(cart.this,Main2Activity.class);
//                    intent.putExtra("List",(ArrayList<Product_details>)cartList);
                intent.putExtra("uid",uid);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity)cart.this).toBundle());
                    }
                    else{
                        startActivity(intent);
                    }
                    finish();
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
//                    mTextMessage.setText(R.string.title_home);
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    setUpRecyclerViewcart();

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        BottomNavigationView navView = findViewById(R.id.nav_view);
////        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        cartList=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        Intent intent=getIntent();
        cartList=intent.getParcelableArrayListExtra("List");
        uid=intent.getStringExtra("uid");
        pickup=findViewById(R.id.cart_pickup_button);
        dilevary=findViewById(R.id.cart_deliver_button);
        setUpRecyclerViewcart();
        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(cart.this,checksum.class);
//                    intent.putExtra("List",(ArrayList<Product_details>)cartList);
                intent.putExtra("cost",s);
                intent.putExtra("uid",uid);
                intent.putParcelableArrayListExtra("List",(ArrayList< ? extends Parcelable>)cartList);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity)cart.this).toBundle());
                }
                else{
                    startActivity(intent);
                }
//                finish();
            }

        });
        dilevary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(cart.this,checksum.class);
//                    intent.putExtra("List",(ArrayList<Product_details>)cartList);
                intent.putExtra("cost",ds);
                intent.putExtra("uid",uid);
                intent.putParcelableArrayListExtra("List",(ArrayList< ? extends  Parcelable >)cartList);
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity)cart.this).toBundle());
                }
                else{
                    startActivity(intent);
                }
//                finish();
            }

        });
        if (ContextCompat.checkSelfPermission(cart.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(cart.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }
    }
    @Override
    public void ondd(Product_details it) {
        Toast.makeText(getApplicationContext(),"Removed", Toast.LENGTH_SHORT).show();
        cartList.remove(it);
        setUpRecyclerViewcart();
    }
    private void setUpRecyclerViewcart(){

        s=0;
        ds=0;
        for(Product_details item:cartList)
        {
            s+=item.getPrice();
        }
        ds=s;
        pri=findViewById(R.id.price_editable);
        dpri=findViewById(R.id.delivery_charge_editable);
        pri.setText(Double.toString(s));
        dpri.setText(Double.toString(ds));
        RecyclerView recyclerView = findViewById(R.id.recycler_viewcart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter2=new Cart_adapter(cartList,this,this);
        recyclerView.setAdapter(adapter2);
//        itemList.clear();
        adapter2.filterList(cartList);

    }

}
