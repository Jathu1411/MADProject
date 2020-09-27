package com.thunderboarsolution.quicksell.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thunderboarsolution.quicksell.Model.Item;
import com.thunderboarsolution.quicksell.R;

import java.util.List;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewholderHome> {
    private Context context;
    private List<Item> mlist;
    private Onitemclicklistener listener;


    public AdapterHome(Context context, List<Item> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public ViewholderHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.cardviewitem,parent,false);

        return new ViewholderHome(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderHome holder, int position) {
        Item item=mlist.get(position);

        holder.tvname.setText(""+item.getItemName());
        holder.tvdescription.setText(""+item.getItemDescription());
        holder.tvprice.setText(""+item.getItemPrice()+" Rs");

        Picasso.get()
                .load(item.getItemImage())
                .centerCrop()
                .fit()
                .into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    public class ViewholderHome extends RecyclerView.ViewHolder{

        private Button btn;
        private TextView tvname,tvdescription,tvprice;
        private ImageView iv;

        public ViewholderHome(@NonNull View itemView) {
            super(itemView);

            btn=itemView.findViewById(R.id.addtocardid);

            tvname=itemView.findViewById(R.id.tvname);
            tvdescription=itemView.findViewById(R.id.tvdes);
            tvprice=itemView.findViewById(R.id.tvprice);

            iv=itemView.findViewById(R.id.imageView1);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.clickitem(position);
                        }

                    }
                }
            });

        }
    }

    public interface Onitemclicklistener
    {
        void clickitem(int position);
    }

    public void setOnitemclicklistener(Onitemclicklistener mlistener)
    {
        listener=mlistener;
    }
}
