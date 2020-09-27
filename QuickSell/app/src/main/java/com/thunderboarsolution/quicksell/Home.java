package com.thunderboarsolution.quicksell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.thunderboarsolution.quicksell.Adapter.AdapterHome;
import com.thunderboarsolution.quicksell.Model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity implements AdapterHome.Onitemclicklistener {

    ProgressBar progressBar;
    FloatingActionButton fabbtn;

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    List<Item> mlist;
    AdapterHome madapter;

    //-------------------------//
  //  DatabaseReference databaseReferenceCard,databaseReferenceMy;
    FirebaseAuth firebaseAuth;
    //-------------------------//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Home");


        progressBar=findViewById(R.id.progressbarid);
        fabbtn=findViewById(R.id.fab);

        firebaseAuth=FirebaseAuth.getInstance();

        recyclerView=findViewById(R.id.recyclerviewid);
        databaseReference= FirebaseDatabase.getInstance().getReference("Items");

//        databaseReferenceCard= FirebaseDatabase.getInstance().getReference("Card Items").child(firebaseAuth.getUid());
//        databaseReferenceMy= FirebaseDatabase.getInstance().getReference("My Items").child(firebaseAuth.getUid());


        recyclerView=findViewById(R.id.recyclerviewid);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

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

                madapter=new AdapterHome(Home.this,mlist);
                recyclerView.setAdapter(madapter);

                madapter.setOnitemclicklistener(Home.this);

                madapter.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fabbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,MyShoppingCart.class));

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.homemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1id:
                startActivity(new Intent(Home.this, MyItems.class));
                break;

            case R.id.item2id:
                startActivity(new Intent(Home.this, UserPage.class));
                break;

            case R.id.item3id:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Home.this,LoginPage.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clickitem(int position) {

        final Item item=mlist.get(position);
        final String itemid=item.getItemid();
        final String userid=item.getUserid();

        new AlertDialog.Builder(Home.this)
                .setTitle("Confirm To Buy This Item")
                .setMessage("Press Confirm To Buy this item ")
                .setCancelable(true)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        addToCard(databaseReference.child(itemid),databaseReferenceCard.child(itemid));
//                        sendMesssageToAdmin(itemid);

                        Intent in=new Intent(Home.this,DeleveryDetails.class);

                        in.putExtra("itemid",itemid);
                        in.putExtra("userid",userid);

                        startActivity(in);



                    }
                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

    }

}