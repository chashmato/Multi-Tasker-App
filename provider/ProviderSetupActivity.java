package rtp.raidtechpro.co_tasker.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.UserType;
import rtp.raidtechpro.co_tasker.models.ServiceProviderModel;
import rtp.raidtechpro.co_tasker.sharedpreferences.SharedPreference;


public class ProviderSetupActivity extends AppCompatActivity {


    EditText txtname,txtnumber,txtabout,txtcity,txtaddress,txtprovince;
    ImageView imageView;
    TextView txtresponse;
    Button btnupload;
    Spinner spinner;

    EditText txtrate_per_hour;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_setup);
        progressDialog = new ProgressDialog(this);

        txtrate_per_hour = (EditText) findViewById(R.id.pro_rate_per_hour);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db = FirebaseFirestore.getInstance();
        citiesRef = db.collection("categories");
        txtname = (EditText) findViewById(R.id.pro_txtprofile_name);
        txtnumber = (EditText) findViewById(R.id.pro_profile_number);
        txtprovince = (EditText) findViewById(R.id.pro_profile_province);
        txtabout = (EditText) findViewById(R.id.pro_profile_about);
        txtcity = (EditText) findViewById(R.id.pro_profile_city);
        txtaddress = (EditText) findViewById(R.id.pro_profile_address);
        imageView = (ImageView) findViewById(R.id.pro_profileimage);
        spinner = (Spinner) findViewById(R.id.procategorySpinner);
        populateSpinner();
        btnupload = (Button) findViewById(R.id.pro_btn_uploadprofile);

        txtresponse = (TextView)findViewById(R.id.pro_txtresponse);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


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
                                                                txtprovince.getText().toString(),
                                                                txtcity.getText().toString(),
                                                                txtaddress.getText().toString(),
                                                                imagepath,
                                                                txtabout.getText().toString(),
                                                                UserType.ServiceProvider.toString(),
                                                                txtrate_per_hour.getText().toString(),
                                                                 "0",
                                                                 "0"
                                                            );


                                                    FirebaseFirestore.getInstance().collection("users").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {

                                                            SharedPreference.saveName( getApplicationContext(),"docid",documentReference.getId().toString());
                                                            SharedPreference.saveName( getApplicationContext(),"name",model.getName());
                                                            SharedPreference.saveName( getApplicationContext(),"phone",model.getPhonenumber());
                                                            SharedPreference.saveName( getApplicationContext(),"city",model.getCity());
                                                            SharedPreference.saveName( getApplicationContext(),"category",model.getCategory());
                                                            SharedPreference.saveName( getApplicationContext(),"about",model.getAbout());
                                                            SharedPreference.saveName( getApplicationContext(),"type",model.getType());
                                                            SharedPreference.saveName( getApplicationContext(),"rate_per_hour",model.getRate_per_hour());
                                                            SharedPreference.saveName( getApplicationContext(),"profileimagepath",imagepath);
                                                            String name11 = SharedPreference.getName(getApplicationContext(),"name");
                                                            Toast.makeText(getApplicationContext(),"The Name is  :"+name11.toString(), Toast.LENGTH_LONG).show();
                                                            Intent t= new Intent(ProviderSetupActivity.this, PDashBoard.class);
                                                            startActivity(t);
                                                        }
                                                    });

                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                            .getTotalByteCount());
                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });;
                }
            }
        });
    }

    //---------------------------------------------------------------------------------------


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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
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

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, citiesList);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}