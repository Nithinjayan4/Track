package com.example.user.track.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.track.R;
import com.example.user.track.activity.ChatActivity;
import com.example.user.track.activity.FriendLocationActivity;
import com.example.user.track.pojo.friendlist;
import com.example.user.track.pojo.msglist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MessageAdapter extends  RecyclerView.Adapter<MessageAdapter.ItemViewHolder> {

    private Context mcontext;
    private ArrayList<msglist> arrayList;
    public static final String TAG="msglist";


    public MessageAdapter(Context mcontext, ArrayList<msglist> arrayList) {
        this.mcontext = mcontext;
        this.arrayList = arrayList;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.from(parent.getContext()).inflate(R.layout.raw_item_msglist,parent,false);
        ItemViewHolder itemViewHolder= new ItemViewHolder(view);
        return  itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        final msglist item=arrayList.get(position);
        //Toast.makeText(mcontext, "right"+item.getSid(), Toast.LENGTH_SHORT).show();
        String sid=String.valueOf(item.getSid());
        String uid=String.valueOf(item.getUid());
       // Toast.makeText(mcontext, sid+uid, Toast.LENGTH_SHORT).show();
        if(sid==uid){
           // Toast.makeText(mcontext, "right", Toast.LENGTH_SHORT).show();
            holder.cright.setVisibility(View.VISIBLE);
            holder.cleft.setVisibility(View.GONE);
holder.cright.setCardBackgroundColor(Color.parseColor("#32b1a4"));
            holder.text_right.setText(item.getName()+"  :  "+item.getMsg());
        }
        else{
           // Toast.makeText(mcontext, "left", Toast.LENGTH_SHORT).show();
            holder.cleft.setVisibility(View.VISIBLE);
            holder.cright.setVisibility(View.GONE);
            holder.cleft.setCardBackgroundColor(Color.parseColor("#fd11c4ce"));
            holder.text_left.setText(item.getName()+"  :  "+item.getMsg());
        }
        //holder.text_right.setText(item.getName());
       // Toast.makeText(mcontext, "item"+item.getCategory(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onBindViewHolder: "+item.getName());



    }

    @Override
    public int getItemCount() {

        if (arrayList != null) {
            return arrayList.size();

        }

        return 0;
}
    public static class ItemViewHolder extends RecyclerView.ViewHolder{


        public TextView text_right,text_left;
        CardView cleft,cright;
        @SuppressLint("CutPasteId")
        public ItemViewHolder(View itemView) {
            super(itemView);

            text_right=(TextView) itemView.findViewById(R.id.txtright);
            text_left=(TextView) itemView.findViewById(R.id.txtleft);
            cleft=itemView.findViewById(R.id.card_view_left);
            cright=itemView.findViewById(R.id.card_view_right);
        }
    }






}
