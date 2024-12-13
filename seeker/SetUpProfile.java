package rtp.raidtechpro.co_tasker.seeker;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.UserType;
import rtp.raidtechpro.co_tasker.models.ServiceSeekerModel;
import rtp.raidtechpro.co_tasker.sharedpreferences.SharedPreference;

public class SetUpProfile extends AppCompatActivity {

    ImageView profileimage;
    TextView txtresponse;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    EditText txtname,txtabout,txtphonenumber,txtcity,txtprovince,txtaddress;
    Button btn_uploadprofile;
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
        setContentView(R.layout.activity_set_up_profile);
        progressDialog = new ProgressDialog(this);
        profileimage = (ImageView) findViewById(R.id.profileimage);
        txtname = (EditText) findViewById(R.id.txtprofile_name);
        txtabout = (EditText) findViewById(R.id.profile_about);
        txtphonenumber  = (EditText) findViewById(R.id.profile_number);
        txtcity  = (EditText) findViewById(R.id.profile_city);
        txtaddress  = (EditText) findViewById(R.id.profile_city);
        txtprovince  = (EditText) findViewById(R.id.profile_city);
        btn_uploadprofile = (Button) findViewById(R.id.btn_uploadprofile);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db = FirebaseFirestore.getInstance();
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btn_uploadprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtname.getText().toString().equals("")){
                    txtresponse.setText("Enter name firt");
                } else  if(txtphonenumber.getText().toString().equals("")) {
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
                                            ServiceSeekerModel model = new ServiceSeekerModel(
                                                    "",
                                                    FirebaseAuth.getInstance().getUid().toString(),
                                                    txtname.getText().toString(),
                                                    FirebaseAuth.getInstance().getCurrentUser().getEmail().toString(),
                                                    txtphonenumber.getText().toString(),
                                                    txtprovince.getText().toString(),
                                                    txtcity.getText().toString(),
                                                    txtaddress.getText().toString(),
                                                    imagepath,
                                                    UserType.ServiceSeeker.toString()
                                                    );

                                            FirebaseFirestore.getInstance().collection("users").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    String docid  =  documentReference.getId();
                                                    SharedPreference.saveName( getApplicationContext(),"docid",docid.toString());
                                                    SharedPreference.saveName( getApplicationContext(),"name",model.getName());
                                                    SharedPreference.saveName( getApplicationContext(),"phone",model.getMobileno());
                                                    SharedPreference.saveName( getApplicationContext(),"city",model.getCity());
                                                    SharedPreference.saveName( getApplicationContext(),"type", model.getType());
                                                    SharedPreference.saveName( getApplicationContext(),"profileimagepath",imagepath);
                                                    String name11 = SharedPreference.getName(getApplicationContext(),"qualification");

                                                    Toast.makeText(getApplicationContext(),"The Name is  :"+name11.toString(), Toast.LENGTH_LONG).show();
                                                    Intent t= new Intent(SetUpProfile.this, DashBoardActivity.class);
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
                profileimage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    //--------------------------------------------------------------------------------------------------
}