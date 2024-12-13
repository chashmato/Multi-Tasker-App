package rtp.raidtechpro.co_tasker.seeker.ui.profile;

import static android.app.Activity.RESULT_OK;

import static com.google.android.gms.common.util.CollectionUtils.mapOf;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.models.ServiceSeekerModel;
import rtp.raidtechpro.co_tasker.sharedpreferences.SharedPreference;


public class ProfileFragment extends Fragment {

    FirebaseFirestore fb;
    private EditText txtname,txtphoneno,txtcity,txtqualify,txtabout;
    ImageView image;
    private ProgressDialog progressBar;
    Button btn;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    TextView txtresponse;
    FirebaseStorage storage;
    StorageReference storageReference;
    String profilepath = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        fb = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        progressBar = new ProgressDialog(getContext());
        progressBar.setMessage("Loading Data ... ");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);

        txtname = (EditText) view.findViewById(R.id.txtprofile_nameaa);
        txtphoneno = (EditText) view.findViewById(R.id.profile_numberaa);
        txtcity = (EditText) view.findViewById(R.id.profile_cityaa);
        txtqualify = (EditText) view.findViewById(R.id.profile_qualificationaa);
        txtabout = (EditText) view.findViewById(R.id.profile_aboutaa);
        image = (ImageView) view.findViewById(R.id.profileimageaa);
        txtresponse = (TextView) view.findViewById(R.id.txtresponse123);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btn = (Button) view.findViewById(R.id.btn_uploadprofile);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtname.getText().toString().equals("")){
                    txtresponse.setText("Enter name firt");
                } else  if(txtphoneno.getText().toString().equals("")) {
                    txtresponse.setText("Enter Phone Number First");
                } else  if(txtcity.getText().toString().equals("")) {
                    txtresponse.setText("Enter City First");
                } else  if(txtabout.getText().toString().equals("")) {
                    txtresponse.setText("Enter About");
                } else  if(txtqualify.getText().toString().equals("")) {
                    txtresponse.setText("Enter Qualification");
                }else if (filePath==null){
                    txtresponse.setText("Choose Profile Image");
                } else{
                    progressBar.setTitle("Uploading...");
                    progressBar.show();
                    StorageReference ref = storageReference.child("userimages/"+ UUID.randomUUID().toString());
                    ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressBar.dismiss();
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
                                                            txtphoneno.getText().toString(),
                                                            "",
                                                            txtcity.getText().toString(),
                                                            "",
                                                            imagepath,
                                                            "Seeker"
                                                    );
                                                    String docid = SharedPreference.getName(getContext(),"docid");
                                                    FirebaseFirestore.getInstance().collection("users").document(docid.toString()).set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(getContext(), "Profile is Updated" , Toast.LENGTH_LONG).show();
                                                          //  SharedPreference.saveName( getContext(),"docid",docid.toString());
                                                            SharedPreference.saveName( getContext(),"name",model.getName());
                                                            SharedPreference.saveName( getContext(),"phone",model.getMobileno());
                                                            SharedPreference.saveName( getContext(),"city",model.getCity());
                                                            SharedPreference.saveName( getContext(),"type","seeker");
                                                            SharedPreference.saveName( getContext(),"profileimagepath",imagepath);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getContext(), "Profile Updation is Failed" , Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.dismiss();
                                    Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                            .getTotalByteCount());
                                    progressBar.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });;
                }
            }
        });


        try {

            txtname.setText(SharedPreference.getName(getContext(), "name"));
            txtphoneno.setText(SharedPreference.getName(getContext(), "phone"));
            txtcity.setText(SharedPreference.getName(getContext(), "city"));


            Picasso.get().load(SharedPreference.getName(getContext(), "profileimagepath").toString()).into(image);

        } catch (Exception e) {
            Toast.makeText(getContext(), "Exception Occured" + e.toString(), Toast.LENGTH_LONG).show();
        }

        return view;
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
                image.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}