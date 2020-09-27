package com.thunderboarsolution.quicksell.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thunderboarsolution.quicksell.Model.Item;
import com.thunderboarsolution.quicksell.R;

import java.util.List;

public class AdapterMyItem extends RecyclerView.Adapter<AdapterMyItem.ViewholderMyItem> {
    private Context context;
    private List<Item> mlist;
    private Onitemclicklistener listener;

    public AdapterMyItem(Context context, List<Item> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public ViewholderMyItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.view_my_item,parent,false);

        return new ViewholderMyItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderMyItem holder, int position) {
        Item item=mlist.get(position);



            holder.tvname.setText(""+item.getItemName());
        holder.tvname.setText(""+item.getItemName());
        holder.tvdescription.setText(""+item.getItemDescription());
        holder.tvprice.setText(""+item.getItemPrice()+" Rs");

        Picasso.get()
                .load(item.getItemImage())
                .centerCrop()
                .fit()
                .into(holder.iv);

        if(item.getItemDescription().equals("Sold"))
        {

        }
        else
        {
            holder.btn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewholderMyItem extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private Button btn;
        private TextView tvname,tvdescription,tvprice;
        private ImageView iv;

        public ViewholderMyItem(@NonNull View itemView) {
            super(itemView);

            btn=itemView.findViewById(R.id.addtocardid);

            tvname=itemView.findViewById(R.id.tvname);
            tvdescription=itemView.findViewById(R.id.tvdes);
            tvprice=itemView.findViewById(R.id.tvprice);

            iv=itemView.findViewById(R.id.imageView1);
            itemView.setOnCreateContextMenuListener(this);

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

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(listener!=null)
            {
                int position=getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION)
                {
                    switch (menuItem.getItemId())
                    {
                          case 1:
                              listener.update(position);
                              return true;

                        case 2:
                            listener.delete(position);
                            return true;
                    }

                }
            }
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Quick Sell");
               MenuItem menuItem2=contextMenu.add(Menu.NONE,1,1,"Update this");
            MenuItem menuItem=contextMenu.add(Menu.NONE,2,2,"Delete this");

            menuItem.setOnMenuItemClickListener(this);
            menuItem2.setOnMenuItemClickListener(this);
        }
    }
    public interface Onitemclicklistener
    {
        void clickitem(int position);
        void delete(int position);
        void update(int position);
    }

    public void setOnitemclicklistener(Onitemclicklistener mlistener)
    {
        listener=mlistener;
    }
}
