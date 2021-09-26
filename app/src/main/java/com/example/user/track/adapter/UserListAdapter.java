package com.example.user.track.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.track.R;
import com.example.user.track.activity.FriendLocationActivity;
import com.example.user.track.activity.HomeActivity;
import com.example.user.track.pojo.friendlist;
import com.example.user.track.pojo.userlist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserListAdapter extends  RecyclerView.Adapter<UserListAdapter.ItemViewHolder> {

    private Context mcontext;
    private ArrayList<userlist> arrayList;
    public static final String TAG="userlist";
    int fid,uid;
    String url="",ip="";

    public UserListAdapter(Context mcontext, ArrayList<userlist> arrayList,String ip) {
        this.mcontext = mcontext;
        this.arrayList = arrayList;
        this.ip=ip;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.from(parent.getContext()).inflate(R.layout.raw_item_userlist,parent,false);
        ItemViewHolder itemViewHolder= new ItemViewHolder(view);
        return  itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        final userlist item=arrayList.get(position);
        holder.text_name.setText(item.getName());
       // Toast.makeText(mcontext, ""+String.valueOf(item.getStatus()), Toast.LENGTH_SHORT).show();
        String st=String.valueOf(item.getStatus());
        if(st.contains("accepted")){
            holder.img_share.setText("Friends");
        }
        else if(st.contains("requested")){
            holder.img_share.setText("Requested");
        }
        else if(st.contains("rejected")){
            holder.img_share.setText("Rejected");
        }
        else if(st.contains("notrequest")){
            holder.img_share.setText("Send Request");
        }
        else{

        }
       // Toast.makeText(mcontext, "item"+item.getCategory(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onBindViewHolder: "+item.getName());
         Picasso.with(mcontext).load(item.getImage_url()).into(holder.img_friend);
        holder.img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p=holder.getAdapterPosition();

                fid=arrayList.get(p).getFid();
                uid=arrayList.get(p).getUid();
               // Toast.makeText(mcontext, ""+item.getFid()+"DD"+item.getUid()+"FFF"+p+"dddddddddddd"+arrayList.get(p).getFid()+arrayList.get(p).getUid(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mcontext);


                alertDialogBuilder.setTitle("Confirm Friend Request")
                        .setMessage("Click 'Yes' to confirm your request")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                       Requestsend();
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();

                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
             alertDialog.show();
                /*
                Intent i = new Intent(mcontext,R.class);
                i.putExtra("fid",arrayList.get(p).getFid());
                i.putExtra("uid",arrayList.get(p).getUid());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(i);*/
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

        public ImageView img_friend;
        Button img_share;
        public CardView cardView;
        public TextView text_name;
        @SuppressLint("CutPasteId")
        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.card_view);
            img_friend=(ImageView)itemView.findViewById(R.id.img_card_frnd);
            text_name=(TextView) itemView.findViewById(R.id.txtname);
            img_share=(Button)itemView.findViewById(R.id.btnreq);

        }
    }


    public void Requestsend() {

        url = "http://" + ip + "/navigation/send_request.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res",response);
               // Toast.makeText(mcontext, ""+response, Toast.LENGTH_SHORT).show();

                if(response.equals("failed")){

                    Toast.makeText(mcontext, "Request Failed", Toast.LENGTH_SHORT).show();
                }
                else if(response.equals("requested")){

                    Toast.makeText(mcontext, "Already Send Request", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mcontext, "Request Send Successfully", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("res", "onErrorResponse: "+error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("uid",""+uid);

                params.put("fid",""+fid);
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
        requestQueue.add(stringRequest);
    }



}
