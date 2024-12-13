package rtp.raidtechpro.co_tasker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.models.Chat;


public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<Chat> chatlist;
    Context context;
    String userid;
    public ChatAdapter(Context context, ArrayList<Chat> _dietPlans ) {
        this.context = context;
        this.chatlist = _dietPlans;
    }

    public static class LeftTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtmsg;
        TextView txtdate;

        public LeftTypeViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtmsg = (TextView) itemView.findViewById(R.id.txtlmessage);
            txtdate = (TextView) itemView.findViewById(R.id.txtldate);

        }
    }

    public static class RightTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtmsg;
        TextView txtdate;

        public RightTypeViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtmsg = (TextView) itemView.findViewById(R.id.txtrmessageright);
            txtdate = (TextView) itemView.findViewById(R.id.txtrdateright);

        }
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case 1:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rightmaessage, parent, false);
                return new RightTypeViewHolder(view);

            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leftmaessage, parent, false);
                return new LeftTypeViewHolder(view);

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (chatlist.get(position).getSts().toString()) {
            case "0":
                return 1;
            default:
                return 2;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        Chat object = chatlist.get(listPosition);
        if (object != null) {
            switch (object.getSts()) {
                case "0":
                    ((RightTypeViewHolder) holder).txtdate.setText(object.getDatetime());
                    ((RightTypeViewHolder) holder).txtmsg.setText(object.getMessage());
                    break;
                default:
                    ((LeftTypeViewHolder) holder).txtmsg.setText(object.getMessage());
                    ((LeftTypeViewHolder) holder).txtdate.setText(object.getDatetime());
                    break;

            }}
    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }
}