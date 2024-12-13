package rtp.raidtechpro.co_tasker.seeker;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.adapters.OrderAdapter;
import rtp.raidtechpro.co_tasker.adapters.ProvidersAdapter;
import rtp.raidtechpro.co_tasker.models.OrderModel;
import rtp.raidtechpro.co_tasker.models.ServiceProviderModel;

public class ProvidersList extends AppCompatActivity {



    RatingBar ratingBar;
    FirebaseFirestore fb;
    TextView txtcategory;
    String url = "";
    String title = "";
    TextView imageView;
    private static ProvidersAdapter adapter;

    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<ServiceProviderModel> providerModels;
    private ProgressDialog progressBar;
    RecyclerView listview;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_postslist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Service Providers");


        Intent t = getIntent();
        title = t.getStringExtra("name");
        getSupportActionBar().setSubtitle(title.toString());
        url = t.getStringExtra("url");
        fb = FirebaseFirestore.getInstance();
        providerModels = new ArrayList<>();
        listview = (RecyclerView) findViewById(R.id.seekerJobslist);
        progressBar = new ProgressDialog (this);
        progressBar.setMessage("Loading Data ... ");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        txtcategory = (TextView) findViewById(R.id.textpostcategorytitle);
        imageView = (TextView) findViewById(R.id.post_list_category_image);


//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutpostlist);
//
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//                // User defined method to shuffle the array list items
//                loaddata();
//            }
//        });


        txtcategory.setText(title);
            loaddata();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                     onBackPressed();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void loaddata(){
        progressBar.show();
        providerModels.clear();
        Task<QuerySnapshot> root_collection = fb.collection("users").whereEqualTo("category",title)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ServiceProviderModel model = new ServiceProviderModel(
                                        document.getId().toString(),
                                        document.getString("uid").toString(),
                                        document.getString("category").toString(),
                                        document.getString("name").toString(),
                                        document.getString("email").toString(),
                                        document.getString("phonenumber").toString(),
                                        document.getString("province").toString(),
                                        document.getString("city").toString(),
                                        document.getString("address").toString(),
                                        document.getString("photopath").toString(),
                                        document.getString("about").toString(),
                                        document.getString("type").toString(),
                                        document.getString("rate_per_hour").toString(),
                                        document.getString("rating").toString(),
                                        document.getString("count").toString()

                                );
                                providerModels.add(model);
                            }
                            adapter= new ProvidersAdapter(getApplicationContext(),providerModels);
                            listview.setAdapter(adapter);//sets the adapter for listView
                            progressBar.dismiss();
                        } else {
                            Log.d(TAG, "Error getting Categories Data: ", task.getException());
                        }
                    }
                });
    }
}