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
import rtp.raidtechpro.co_tasker.models.CategoriesModel;
import rtp.raidtechpro.co_tasker.seeker.ProvidersList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    ArrayList<CategoriesModel> categoriesModels;
    Context context;
    public CategoryAdapter(Context context, ArrayList<CategoriesModel> _dietPlans) {
        this.context = context;
        this.categoriesModels = _dietPlans;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {
        // set the data in items
        // implement setOnClickListener event on item view.

        holder.txttitle.setText(categoriesModels.get(position).getName().toString());
        Picasso.get().load(categoriesModels.get(position).getIcon()).into(holder.imageView);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(context, ProvidersList.class);

                t.putExtra("url",categoriesModels.get(position).getIcon().toString());
                t.putExtra("name",categoriesModels.get(position).getName().toString());
                context.startActivity(t);
            }
        });

    }


    @Override
    public int getItemCount() {
        return categoriesModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  txttitle;
        ImageView imageView;



        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            txttitle = (TextView) itemView.findViewById(R.id.cat_list_title);
            imageView = (ImageView) itemView.findViewById(R.id.cat_list_image);
        }
    }
}

