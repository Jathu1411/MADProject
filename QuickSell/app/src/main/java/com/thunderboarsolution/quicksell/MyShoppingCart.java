package com.thunderboarsolution.quicksell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thunderboarsolution.quicksell.Adapter.AdapterCard;
import com.thunderboarsolution.quicksell.Adapter.AdapterHome;
import com.thunderboarsolution.quicksell.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class MyShoppingCart extends AppCompatActivity implements AdapterCard.Onitemclicklistener {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    List<Item> mlist;
    AdapterCard madapter;

    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shopping_cart);
        progressBar=findViewById(R.id.progressbarid);


        firebaseAuth=FirebaseAuth.getInstance();

        recyclerView=findViewById(R.id.recyclerviewid);
        databaseReference= FirebaseDatabase.getInstance().getReference("Card Items").child(firebaseAuth.getUid());

        recyclerView=findViewById(R.id.recyclerviewid);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mlist=new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mlist.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Item item1=dataSnapshot1.getValue(Item.class);
                    mlist.add(item1);
                }

                madapter=new AdapterCard(MyShoppingCart.this,mlist);
                recyclerView.setAdapter(madapter);

                madapter.setOnitemclicklistener(MyShoppingCart.this);

                madapter.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void clickitem(int position) {
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
    }
}