package rtp.raidtechpro.co_tasker.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.models.OrderModel;
import rtp.raidtechpro.co_tasker.seeker.OrderDetails;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    ArrayList<OrderModel> orderModels;
    Context context;
    public OrderAdapter(Context context, ArrayList<OrderModel> _postModels) {
        this.context = context;
        this.orderModels = _postModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {
        // set the data in items
        // implement setOnClickListener event on item view.

        holder.txtorderid.setText(orderModels.get(position).getId().toString());
        holder.txtseekername.setText(orderModels.get(position).getSeekername().toString());
        holder.txtprovidername.setText(orderModels.get(position).getProvidername().toString());
        holder.txtdate.setText(orderModels.get(position).getDate().toString());
        holder.txtstatus.setText(orderModels.get(position).getStatus().toString());
        Picasso.get().load(orderModels.get(position).getSeekerimage()).into(holder.seekerimage);
        Picasso.get().load(orderModels.get(position).getProviderimage()).into(holder.providerimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(context, OrderDetails.class);
                t.putExtra("docid",orderModels.get(position).getId().toString());
                t.putExtra("status",orderModels.get(position).getStatus().toString());
                context.startActivity(t);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  txtorderid,txtseekername,txtprovidername,txtdate,txtstatus,txtcategory;
        ImageView seekerimage,providerimage;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtorderid = (TextView) itemView.findViewById(R.id.order_no);
            txtseekername = (TextView) itemView.findViewById(R.id.order_seeker_title);
            txtprovidername = (TextView) itemView.findViewById(R.id.order_provider_name);
            txtdate = (TextView) itemView.findViewById(R.id.order_date);
            txtstatus = (TextView) itemView.findViewById(R.id.order_status);
            txtcategory = (TextView) itemView.findViewById(R.id.order_provider_category);
            seekerimage = (ImageView) itemView.findViewById(R.id.order_seeker_image);
            providerimage = (ImageView) itemView.findViewById(R.id.order_provider_image);
        }
    }
}

