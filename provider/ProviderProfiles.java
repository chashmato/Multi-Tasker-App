package rtp.raidtechpro.co_tasker.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.type.LatLng;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rtp.raidtechpro.co_tasker.MyFirebaseMessagingService;
import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.Status;
import rtp.raidtechpro.co_tasker.models.OrderModel;
import rtp.raidtechpro.co_tasker.sharedpreferences.SharedPreference;


public class ProviderProfiles extends AppCompatActivity implements LocationListener {
    int LOCATION_REFRESH_TIME = 500; // 15 seconds to update
    int LOCATION_REFRESH_DISTANCE = 500;
    private final int PICK_IMAGE_REQUEST = 71;
    private FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    private Uri filePath;
    TextView txtresponse;
    String txtproviderid = "";
    TextView txtname;
    TextView txtname1;
    TextView txtcategory;
    TextView txtphonenumber;
    TextView txtcity;
    TextView txtabout;
    TextView txtrateperhour;
    ImageView profileimage;


    String imagepath;
    Button btn;
    CollectionReference citiesRef;
    FirebaseFirestore db;
    String lati = "";
    String longi = "'";
    FirebaseStorage storage;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    String address = "";
    private int locationRequestCode = 1000;

    private LocationManager locationManager;
    protected LocationListener locationListener;
    private Location location;
    protected boolean gps_enabled, network_enabled;


    String latitude ="" , longitude= "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Criteria criteria = new Criteria();
        // method to get the location
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);

        } else {
            // already permission granted
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);


        if (location != null) {

            onLocationChanged(location);
            Toast.makeText(getApplicationContext(), "The Location is :" + location.getLatitude() + "", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "The Location is Null", Toast.LENGTH_LONG).show();

        }

        locationManager.requestLocationUpdates(provider, 20000, 0, this);


        Intent t = getIntent();


        setContentView(R.layout.activity_provider_profile);
        progressDialog = new ProgressDialog(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        txtname = (TextView) findViewById(R.id.txtprovidername);
        txtname1 = (TextView) findViewById(R.id.txtprovidername1);
        txtcategory = (TextView) findViewById(R.id.txtprovidercategory);
        txtcity = (TextView) findViewById(R.id.txtprovidercity);
        txtabout = (TextView) findViewById(R.id.txtprovideabout);
        txtrateperhour = (TextView) findViewById(R.id.txtproviderrateperhour);
        txtresponse = (TextView) findViewById(R.id.txtprovider_response);
        db = FirebaseFirestore.getInstance();
        btn = (Button) findViewById(R.id.btn_providerrequest);

        profileimage = (ImageView) findViewById(R.id.image_provideimage);
        loaddata(t.getStringExtra("uid"));


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                String name  = SharedPreference.getName( getApplicationContext(),"name");
                String image  = SharedPreference.getName( getApplicationContext(),"profileimagepath");
                OrderModel model = new OrderModel(
                        "",
                        FirebaseAuth.getInstance().getUid().toString(),
                        name.toString(),
                        image.toString(),
                        txtproviderid,
                        txtname.getText().toString(),
                        imagepath.toString(),
                        txtcategory.getText().toString(), formattedDate,
                        formattedDate,
                        formattedDate,
                        address,
                "0",
                "0",
                        txtrateperhour.getText().toString(),
                "0",
                        latitude.toString(),
                        longitude.toString(),
                        Status.Request.toString()
                        );
                FirebaseFirestore.getInstance().collection("order").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(getApplicationContext(),"Request Has been sent",Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Request sending Has Failed",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }


    void loaddata(String id) {

        FirebaseFirestore.getInstance().collection("users").document(id.toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                txtname.setText(documentSnapshot.getString("name").toString());
                txtname1.setText(documentSnapshot.getString("name").toString());
                txtcategory.setText(documentSnapshot.getString("category").toString());
                txtabout.setText(documentSnapshot.getString("about").toString());
                txtcity.setText(documentSnapshot.getString("city").toString());
                txtrateperhour.setText(documentSnapshot.getString("rate_per_hour"));
                Picasso.get().load(documentSnapshot.getString("photopath")).into(profileimage);
                imagepath = documentSnapshot.getString("photopath");
                txtproviderid = documentSnapshot.getString("uid");
                address = documentSnapshot.getString("address");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to Fatch Data :" + e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

      if(location == null){
          Toast.makeText(getApplicationContext(), "Location is NUll ",  Toast.LENGTH_LONG).show();

      } else{
          Toast.makeText(getApplicationContext(), "The Location is  : ( " + location.getLatitude() + " " + location.getLongitude() + "  _", Toast.LENGTH_LONG).show();
          latitude  = location.getLatitude()+"";
          longitude = location.getLongitude()+"";
      }
    }


    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getApplicationContext(), "disable", Toast.LENGTH_LONG).show();

    }



    //------------------------------------------------------------------------------------


}