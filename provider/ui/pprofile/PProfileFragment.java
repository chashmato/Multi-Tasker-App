package rtp.raidtechpro.co_tasker.provider.ui.pprofile;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.models.ServiceProviderModel;
import rtp.raidtechpro.co_tasker.sharedpreferences.SharedPreference;


public class PProfileFragment extends Fragment {

    EditText txtname,txtnumber,txtabout,txtcity;
    ImageView imageView;
    TextView txtresponse;
    Button btnupload;
    Spinner spinner;

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;

    CollectionReference citiesRef;
    FirebaseFirestore db;

    String docid;
    FirebaseStorage storage;
    StorageReference storageReference;

    String profilepath = "";
    ProgressDialog progressDialog;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_p_profile2, container, false);


        progressDialog = new ProgressDialog(getContext());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db = FirebaseFirestore.getInstance();
        citiesRef = db.collection("categories");
        txtname = (EditText) view.findViewById(R.id.pro_txtprofile_name1);
        txtnumber = (EditText) view.findViewById(R.id.pro_profile_number1);
        txtabout = (EditText) view.findViewById(R.id.pro_profile_about1);
        txtcity = (EditText) view.findViewById(R.id.pro_profile_city1);
        imageView = (ImageView) view.findViewById(R.id.pro_profileimage1);
        spinner = (Spinner) view.findViewById(R.id.procategorySpinner1);
        populateSpinner();
        btnupload = (Button) view.findViewById(R.id.pro_btn_uploadprofile1);

        txtresponse = (TextView) view.findViewById(R.id.pro_txtresponse1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        loadprofiledata();

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtname.getText().toString().equals("")){
                    txtresponse.setText("Enter name firt");
                } else  if(txtnumber.getText().toString().equals("")) {
                    txtresponse.setText("Enter Phone Number First");
                } else  if(txtcity.getText().toString().equals("")) {
                    txtresponse.setText("Enter City First");
                } else  if(txtabout.getText().toString().equals("")) {
                    txtresponse.setText("Enter About");
                } else if (filePath==null){
                    txtresponse.setText("Choose Profile Image");
                } else{
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
//
                    StorageReference ref = storageReference.child("userimages/"+ UUID.randomUUID().toString());
                    ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();

                                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                            new OnCompleteListener<Uri>() {

                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {

                                                    String imagepath = task.getResult().toString();

                                                    ServiceProviderModel model = new ServiceProviderModel(
                                                            "",
                                                            FirebaseAuth.getInstance().getUid().toString(),
                                                            spinner.getSelectedItem().toString(),
                                                            txtname.getText().toString(),
                                                            FirebaseAuth.getInstance().getCurrentUser().getEmail().toString(),
                                                            txtnumber.getText().toString(),
                                                          "",
                                                            txtcity.getText().toString(),
                                                           " txtcity.getText().toString()",
                                                            imagepath,
                                                            txtabout.getText().toString(),
                                                            "Provider",
                                                            txtabout.getText().toString(),
                                                            "",""
                                                            );

                                                            String docid= SharedPreference.getName(getContext(),"docid");
                                                            FirebaseFirestore.getInstance().collection("users").document(docid).set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    SharedPreference.saveName( getContext(),"name",model.getName());
                                                                    SharedPreference.saveName( getContext(),"phone",model.getPhonenumber());
                                                                    SharedPreference.saveName( getContext(),"city",model.getCity());
                                                                    SharedPreference.saveName( getContext(),"category",model.getCategory());
                                                                    SharedPreference.saveName( getContext(),"about",model.getAbout());
                                                                    SharedPreference.saveName( getContext(),"type","Provider");
                                                                    SharedPreference.saveName( getContext(),"profileimagepath",imagepath);
                                                                    String name11 = SharedPreference.getName(getContext(),"name");
                                                                    Toast.makeText(getContext(), "Profile is updated ", Toast.LENGTH_SHORT).show();

                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(getContext(), "Profile Updation is Failed ", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                            });
                                }
                            });
                }
            }
        });
        return  view;
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }



    void populateSpinner(){

        citiesRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> citiesList = new ArrayList<>();

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String name = documentSnapshot.getString("name");
                    citiesList.add(name.toString());
                }

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, citiesList);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    void   loadprofiledata(){

        try {
            txtname.setText(SharedPreference.getName(getContext(), "name"));
            txtnumber.setText(SharedPreference.getName(getContext(), "phone"));
            txtcity.setText(SharedPreference.getName(getContext(), "city"));
            txtabout.setText(SharedPreference.getName(getContext(), "about"));


            Picasso.get().load(SharedPreference.getName(getContext(), "profileimagepath").toString()).into(imageView);

        } catch (Exception e){

        }
    }
}