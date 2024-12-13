package rtp.raidtechpro.co_tasker.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.models.OrderModel;
import rtp.raidtechpro.co_tasker.models.ServiceProviderModel;
import rtp.raidtechpro.co_tasker.provider.ProviderProfiles;


public class ProvidersAdapter extends RecyclerView.Adapter<ProvidersAdapter.MyViewHolder> {
    ArrayList<ServiceProviderModel> serviceProviderModels;
    Context context;
    public ProvidersAdapter(Context context, ArrayList<ServiceProviderModel> _postModels) {
        this.context = context;
        this.serviceProviderModels = _postModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seekerlayput, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {
        // set the data in items
        // implement setOnClickListener event on item view.

        holder.ratingBar.setMax(5);
        holder.ratingBar.setNumStars(5);
        String  rating = (serviceProviderModels.get(position).getRating().toString());
        String  count = (serviceProviderModels.get(position).getCount().toString());
        int avgrating = 1;
        if(rating == "0" || count == "" || rating == "" || count == ""){
            avgrating =1;
        }else{

            try{
                avgrating  = (Integer.parseInt(rating) / Integer.parseInt(count));
            } catch(ArithmeticException e){
                avgrating =1;
            }

        }


        holder.txtpersonname.setText(serviceProviderModels.get(position).getName().toString());
        holder.txtcategory.setText(serviceProviderModels.get(position).getCategory().toString());
        holder.txtcity.setText(serviceProviderModels.get(position).getCity().toString());
        holder.txtratephour.setText(serviceProviderModels.get(position).getRate_per_hour().toString());
        holder.ratingBar.setRating((avgrating));
        Picasso.get().load(serviceProviderModels.get(position).getPhotopath()).into(holder.image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(context, ProviderProfiles.class);
                t.putExtra("uid",serviceProviderModels.get(position).getId().toString());
                t.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(t);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceProviderModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  txtpersonname,txtcategory,txtcity,txtratephour;
        ImageView image;

        RatingBar ratingBar;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtpersonname = (TextView) itemView.findViewById(R.id.txtseekertitle);
            txtcategory = (TextView) itemView.findViewById(R.id.txtpersoncategory);
            txtcity = (TextView) itemView.findViewById(R.id.textpersoncity);
            txtratephour = (TextView) itemView.findViewById(R.id.textpersonrateperhour);
            image = (ImageView) itemView.findViewById(R.id.imageViewseeker);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }
}

