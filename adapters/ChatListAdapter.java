package rtp.raidtechpro.co_tasker.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import rtp.raidtechpro.co_tasker.ChatUserList;
import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.models.ChatListModel;
import rtp.raidtechpro.co_tasker.provider.ProviderChatActivity;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolderChatUi> {

    ArrayList<ChatListModel> chatlist;
    Context context;
    public ChatListAdapter(Context context, ArrayList<ChatListModel> _chat) {
        this.context = context;
        this.chatlist = _chat;
    }


    @Override
    public MyViewHolderChatUi onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatuilayout, parent, false);
        MyViewHolderChatUi vh = new MyViewHolderChatUi(v); // pass the view to View Holder

        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolderChatUi holder, int position) {
        // set the data in items
        // implement setOnClickListener event on item view.
//        holder.txtname.setText(chatlist.get(position).getEmail().toString());
//        holder.txtemail.setText(chatlist.get(position).getEmail().toString());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                  Intent t = new Intent(context, ProviderChatActivity.class);
//                  t.putExtra("userid",chatlist.get(position).getId().toString());
//                  t.putExtra("username",chatlist.get(position).getName().toString());
//                  t.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                  context.startActivity(t);
//            }
//        });
    }



    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public class MyViewHolderChatUi extends RecyclerView.ViewHolder {

        TextView txtname;
        TextView txtemail;


        public MyViewHolderChatUi(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtname = (TextView) itemView.findViewById(R.id.txtchatuiname);
            txtemail = (TextView) itemView.findViewById(R.id.txtchatuiemail);


        }
    }


}
