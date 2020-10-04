package com.thunderboarsolution.quicksell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thunderboarsolution.quicksell.Model.Delivery;

import java.util.HashMap;
import java.util.Map;

public class DeleveryDetails extends AppCompatActivity {

    private EditText edphn,edadd,edcard;
    private Button btn;

    String itemid="",adminid="";

    DatabaseReference databaseReference; // delivery details
    FirebaseAuth firebaseAuth;

    DatabaseReference databaseReferenceCard,dbupdate,databaseReferenceitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delevery_details);

         itemid= getIntent().getStringExtra("itemid");
         adminid = getIntent().getStringExtra("userid");

         firebaseAuth=FirebaseAuth.getInstance();
        databaseReferenceCard= FirebaseDatabase.getInstance().getReference("Card Items").child(firebaseAuth.getUid());
        dbupdate= FirebaseDatabase.getInstance().getReference("My Items");

        databaseReference= FirebaseDatabase.getInstance().getReference("Delivery Details");

        databaseReferenceitem= FirebaseDatabase.getInstance().getReference("Items");


        edphn=findViewById(R.id.editTextPhone2);
        edadd=findViewById(R.id.editTextTextPersonName4);
        edcard=findViewById(R.id.editText2);

        btn=findViewById(R.id.button4);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phn=edphn.getText().toString().trim();
                String add=edadd.getText().toString().trim();
                String cart=edcard.getText().toString().trim();

                if(phn.equals(""))
                {
                    Toast.makeText(DeleveryDetails.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                }
                else if(add.equals(""))
                {
                    Toast.makeText(DeleveryDetails.this, "Enter Address", Toast.LENGTH_SHORT).show();
                }
                else if(cart.equals(""))
                {
                    Toast.makeText(DeleveryDetails.this, "Enter Card Details", Toast.LENGTH_SHORT).show();
                }
                else
                {

                  //  sendMesssageToAdmin(itemid);

                    String bookingid=databaseReference.push().getKey();
                    Delivery delivery=new Delivery();

                    delivery.setBookingid(bookingid);
                    delivery.setPhn(phn);
                    delivery.setAddress(add);
                    delivery.setCartno(cart);
                    delivery.setAdminid(adminid);
                    delivery.setItemid(itemid);
                    delivery.setUserid(firebaseAuth.getUid());

                    addToCart(databaseReferenceitem.child(itemid),bookingid,delivery);
                    sendMesssageToAdmin(itemid);

//                    addToCard(databaseReferenceitem.child(itemid),databaseReferenceCard.child(itemid));
//
//                    databaseReference.child(key).setValue(delivery).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            startActivity(new Intent(DeleveryDetails.this,MyItems.class));
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                        }
//                    });



                }


            }
        });

    }

    private void addToCart(final DatabaseReference originalvalue, final String bookingid, final Delivery delivery) {

        originalvalue.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReferenceCard.child(bookingid).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {
                            Toast.makeText(DeleveryDetails.this, "Try again", Toast.LENGTH_SHORT).show();
                        }
                        else
                            {
                           // StoreBookingDetails(th)
                            originalvalue.setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    databaseReference.child(bookingid).setValue(delivery).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            startActivity(new Intent(DeleveryDetails.this,MyShoppingCart.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });


                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMesssageToAdmin(String itemid) {
        Map<String, Object> map = new HashMap<>();
        map.put("itemDescription", "Sold");

        dbupdate.child(adminid).child(itemid).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void addToCard(final DatabaseReference child, final DatabaseReference databaseReferenceCard) {
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReferenceCard.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            child.setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(DeleveryDetails.this, "Added to Card", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(DeleveryDetails.this, "Fail", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else if(task.isCanceled())
                        {
                            Toast.makeText(DeleveryDetails.this, "failed to add card", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        child.addListenerForSingleValueEvent(valueEventListener);
    }
}