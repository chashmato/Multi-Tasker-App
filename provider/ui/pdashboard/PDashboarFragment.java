package rtp.raidtechpro.co_tasker.provider.ui.pdashboard;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.adapters.OrderAdapter;
import rtp.raidtechpro.co_tasker.adapters.SliderAdapter;
import rtp.raidtechpro.co_tasker.models.OrderModel;


public class PDashboarFragment extends Fragment {
    FirebaseFirestore fb;
    private static OrderAdapter adapter;
    SliderView sliderView;
    ArrayList<OrderModel> orderModels;

    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressBar;
    RecyclerView listview;


    SwipeRefreshLayout swipeRefreshpdashboard;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pdashboard, container, false);
        fb = FirebaseFirestore.getInstance();
        orderModels = new ArrayList<>();
        listview = (RecyclerView) view.findViewById(R.id.dashboardlist);
        progressBar = new ProgressDialog (getContext());
        progressBar.setMessage("Loading  Requests ... ");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshpdashboard);
        sliderView = view.findViewById(R.id.image_slider12);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                // User defined method to shuffle the array list items
                loaddata();
            }
        });


        try {
            loaddata();
            loaddataimages();
        } catch (Exception e){
        }

        return  view;

    }

    void loaddata(){
        progressBar.show();
        orderModels.clear();
        Task<QuerySnapshot> root_collection = fb.collection("order").whereEqualTo("providerid", FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                OrderModel model = new OrderModel(
                                        document.getId().toString(),
                                        document.getString("seekerid").toString(),
                                        document.getString("seekername").toString(),
                                        document.getString("seekerimage").toString(),
                                        document.getString("providerid").toString(),
                                        document.getString("providername").toString(),
                                        document.getString("providerimage").toString(),
                                        document.getString("category").toString(),
                                        document.getString("date").toString(),
                                        document.getString("sdate").toString(),
                                        document.getString("edate").toString(),
                                        document.getString("location").toString(),
                                        document.getString("totalhours").toString(),
                                        document.getString("n_o_hours").toString(),
                                        document.getString("rate_per_hour").toString(),
                                        document.getString("totalamount").toString(),
                                        document.getString("lati").toString(),
                                        document.getString("longi").toString(),
                                        document.getString("status").toString()
                                );
                                orderModels.add(model);
                            }
                            adapter= new OrderAdapter(getContext(),orderModels);
                            listview.setAdapter(adapter);//sets the adapter for listView
                            progressBar.dismiss();

                        } else {
                            Log.d(TAG, "Error getting Categories Data: ", task.getException());
                        }
                    }
                });
    }


    void loaddataimages(){


        Task<QuerySnapshot> root_collection = fb.collection("ads")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i =0;
                            String[] images = new String[task.getResult().size()];

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                images[i]  =document.getString("imagepath");
                                i++;
                            }
                            SliderAdapter sliderAdapter = new SliderAdapter(images);
                            progressBar.dismiss();
                            sliderView.setSliderAdapter(sliderAdapter);
                            sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                            sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
                            sliderView.startAutoCycle();//sets the adapter for listView


                        } else {
                            Toast.makeText(getContext(),"Data Fetching Failed ",Toast.LENGTH_LONG).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Data Fetching Failed :"+e.toString(),Toast.LENGTH_LONG).show();
                    }
                });
    }
}