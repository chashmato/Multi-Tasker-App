package rtp.raidtechpro.co_tasker.seeker.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.adapters.CategoryAdapter;
import rtp.raidtechpro.co_tasker.adapters.SliderAdapter;
import rtp.raidtechpro.co_tasker.models.AdsModel;
import rtp.raidtechpro.co_tasker.models.CategoriesModel;


public class DashBoardFragment extends Fragment {



    SliderView sliderView;

    FirebaseFirestore fb;
    EditText txtmessage;
    Button btnsend;
    TextView chatusername;
    String username = "";

    ImageView imageView;

    private static CategoryAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<CategoriesModel> categoriesModels;
    private ProgressDialog progressBar;
    RecyclerView listview;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        fb = FirebaseFirestore.getInstance();
        categoriesModels = new ArrayList<>();
        listview = (RecyclerView) view.findViewById(R.id.categorieslist);
        progressBar = new ProgressDialog (getContext());
        progressBar.setMessage("Loading Please Wait ... ");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        sliderView = view.findViewById(R.id.image_slider);

        try {

            loaddata();
            loaddataimages();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  view;
    }


    void loaddata(){
        progressBar.show();
        categoriesModels.clear();
        Task<QuerySnapshot> root_collection = fb.collection("categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoriesModel model = new CategoriesModel(
                                        document.getId().toString(),
                                        document.getString("name").toString(),
                                        document.getString("icon").toString()
                                );

                                categoriesModels.add(model);
                            }
                            adapter= new CategoryAdapter(getContext(),categoriesModels);
                            listview.setAdapter(adapter);//sets the adapter for listView
                            progressBar.dismiss();

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


    //-----------------------------------------------------------------------------------------------------
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}