package com.example.user.track.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.track.R;
import com.example.user.track.activity.OfferDetailsActivity;
import com.example.user.track.activity.ShopLocationActivity;
import com.example.user.track.pojo.offerlist;
import com.example.user.track.pojo.shoplist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class OfferListAdapter extends  RecyclerView.Adapter<OfferListAdapter.ItemViewHolder> {

    private Context mcontext;
    private ArrayList<offerlist> arrayList;
    public static final String TAG="shoplist";


    public OfferListAdapter(Context mcontext, ArrayList<offerlist> arrayList) {
        this.mcontext = mcontext;
        this.arrayList = arrayList;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.from(parent.getContext()).inflate(R.layout.raw_item_offerlistdetails,parent,false);
        ItemViewHolder itemViewHolder= new ItemViewHolder(view);
        return  itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        final offerlist item=arrayList.get(position);
        holder.text_shopname.setText(item.getShop_name());
        holder.text_item.setText(item.getItem());
        holder.text_offerprice.setText(item.getOffer_price());
        holder.text_originalprice.setText(item.getOriginal_price());

        holder.text_originalprice.setPaintFlags(holder.text_originalprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

       // Toast.makeText(mcontext, "item"+item.getCategory(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onBindViewHolder: "+item.getShop_name());
      Picasso.with(mcontext).load(item.getImg_url()).into(holder.img_item);
        holder.bloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p=holder.getAdapterPosition();

              //  Toast.makeText(mcontext, ""+item.getFid()+"DD"+item.getUid()+"FFF"+p+"dddddddddddd"+arrayList.get(p).getFid()+arrayList.get(p).getUid(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(mcontext, ShopLocationActivity.class);
                i.putExtra("fid",""+arrayList.get(p).getSid());
                i.putExtra("offerid",""+arrayList.get(p).getOfferid());
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


        public CardView cardView;
        ImageView img_item;
        public TextView text_shopname,text_item,text_offerprice,text_originalprice;
        Button  bloc;
        @SuppressLint("CutPasteId")
        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.card_view);
           img_item=(ImageView)itemView.findViewById(R.id.img_card_item);
            text_shopname=(TextView) itemView.findViewById(R.id.txtshop);
            text_item=(TextView) itemView.findViewById(R.id.txtitem);
            text_offerprice=(TextView)itemView.findViewById(R.id.txtoprice);
            text_originalprice=(TextView) itemView.findViewById(R.id.txtprice);
            bloc=(Button) itemView.findViewById(R.id.btnloc);




        }
    }






}
