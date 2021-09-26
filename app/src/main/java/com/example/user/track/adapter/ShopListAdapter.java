package com.example.user.track.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.track.R;
import com.example.user.track.activity.ShopLocationActivity;
import com.example.user.track.pojo.shoplist;

import java.util.ArrayList;


public class ShopListAdapter extends  RecyclerView.Adapter<ShopListAdapter.ItemViewHolder> {

    private Context mcontext;
    private ArrayList<shoplist> arrayList;
    public static final String TAG="shoplist";


    public ShopListAdapter(Context mcontext, ArrayList<shoplist> arrayList) {
        this.mcontext = mcontext;
        this.arrayList = arrayList;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.from(parent.getContext()).inflate(R.layout.raw_item_shoplist,parent,false);
        ItemViewHolder itemViewHolder= new ItemViewHolder(view);
        return  itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        final shoplist item=arrayList.get(position);
        holder.text_name.setText(item.getShop_name());
       // Toast.makeText(mcontext, "item"+item.getCategory(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onBindViewHolder: "+item.getShop_name());
        // Picasso.with(mcontext).load(item.getImage_url()).into(holder.img_friend);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p=holder.getAdapterPosition();

              //  Toast.makeText(mcontext, ""+item.getFid()+"DD"+item.getUid()+"FFF"+p+"dddddddddddd"+arrayList.get(p).getFid()+arrayList.get(p).getUid(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(mcontext, ShopLocationActivity.class);
                i.putExtra("fid",""+arrayList.get(p).getSid());
                i.putExtra("uid",""+arrayList.get(p).getUid());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {

        if (arrayList != null) {
            return arrayList.size();

        }

        return 0;
}
    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public ImageView img_friend,img_share;
        public CardView cardView;
        public TextView text_name;
        @SuppressLint("CutPasteId")
        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.card_view);
          //  img_friend=(ImageView)itemView.findViewById(R.id.img_card_frnd);
            text_name=(TextView) itemView.findViewById(R.id.txtname);
            img_share=(ImageView)itemView.findViewById(R.id.share);

        }
    }






}
