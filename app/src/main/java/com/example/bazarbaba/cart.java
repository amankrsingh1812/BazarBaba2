package com.example.bazarbaba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    private String uid;
    private ProgressDialog progressDialog;
    private Button buttonm2;
    private String token;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent=new Intent(cart.this,Main2Activity.class);
//                    intent.putExtra("List",(ArrayList<Product_details>)cartList);
//                intent.putExtra("uemail",uemail);

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

        Intent intent=getIntent();
        cartList=intent.getParcelableArrayListExtra("List");
;
        setUpRecyclerViewcart();
    }
    @Override
    public void ondd(Product_details it) {
        Toast.makeText(getApplicationContext(),"Removed", Toast.LENGTH_SHORT).show();
        cartList.remove(it);
        setUpRecyclerViewcart();
    }
    private void setUpRecyclerViewcart(){

        long s=0,ds=0;
        for(Product_details item:cartList)
        {
            s+=item.getPrice();
        }
        ds=s;
        pri=findViewById(R.id.price_editable);
        dpri=findViewById(R.id.delivery_charge_editable);
        pri.setText(Long.toString(s));
        dpri.setText(Long.toString(ds));
        RecyclerView recyclerView = findViewById(R.id.recycler_viewcart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter2=new Cart_adapter(cartList,this,this);
        recyclerView.setAdapter(adapter2);
//        itemList.clear();
        adapter2.filterList(cartList);

    }
}
