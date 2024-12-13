package rtp.raidtechpro.co_tasker.seeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.Status;
import rtp.raidtechpro.co_tasker.UserType;
import rtp.raidtechpro.co_tasker.models.OrderModel;
import rtp.raidtechpro.co_tasker.models.RatingModel;
import rtp.raidtechpro.co_tasker.provider.CompleteWork;
import rtp.raidtechpro.co_tasker.provider.ProviderChatActivity;
import rtp.raidtechpro.co_tasker.sharedpreferences.SharedPreference;

public class OrderDetails extends AppCompatActivity {
    String count = "0";
    String rating  = "0";
    String proUid  = "";
    TextView txtcategory , txtprovidername ,txtstartdate, txtenddate, txtnumberofhours, txtrate,txttotalamount,txtlocation,txtstatus;
    ImageView imageView;
    Button btn;
    FirebaseFirestore fb;
    String imagepath = "";
    String title="";
    private ProgressDialog progressBar;
    String jobid;

    String seekerid = "";
    String seekername = "";
    String providerid = "";
    String providername = "";
    Button btnopenchat;




    String lati = "";
    String longi = "";

    Button opnMapsButton;
    String btnsts = "1";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order Details");

        getSupportActionBar().setSubtitle(title.toString());
        Intent t = getIntent();
        jobid = t.getStringExtra("docid");
        btnsts = t.getStringExtra("status");

        btn  =(Button) findViewById(R.id.btn_adimage);

        opnMapsButton = (Button) findViewById(R.id.txtviewlocationonmaps);
        opnMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(lati == "" || longi == ""){

                    Toast.makeText(getApplicationContext(),"The LOcation is NUll", Toast.LENGTH_LONG).show();
                } else{
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("geo:"+lati+","+longi));
                    startActivity(intent);
                }
            }
        });


        fb = FirebaseFirestore.getInstance();
        progressBar = new ProgressDialog (this);
        progressBar.setMessage("Loading Data ... ");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        btnopenchat = (Button) findViewById(R.id.btnopenchat);
        txtcategory = (TextView) findViewById(R.id.txtoderdetail_workcategpry);
        txtprovidername = (TextView) findViewById(R.id.txtprovidername);
        txtrate = (TextView) findViewById(R.id.txtjobdesc_rate_per_hour);
        txtstartdate = (TextView) findViewById(R.id.txtjobdesc_sdate);
        txtenddate = (TextView) findViewById(R.id.txtjobdesc_edate);
        txtstatus = (TextView) findViewById(R.id.txtjobdesc_orderstatus);
        txtlocation = (TextView) findViewById(R.id.txtjobdesc_location);
        txtnumberofhours = (TextView) findViewById(R.id.txtjobdesc_number_of_hour);
        txttotalamount = (TextView) findViewById(R.id.txtjobdesc_totalamount);

        imageView = (ImageView) findViewById(R.id.orderdetails_profileimage);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = SharedPreference.getName(getApplicationContext(),"type");
                Toast.makeText(getApplicationContext(),"The type is :"+type.toString(),Toast.LENGTH_LONG).show();
                if(type.equals(UserType.ServiceProvider.toString())){

                    if(txtstatus.getText().equals(Status.Request.toString())){
                        Toast.makeText(getApplicationContext(),"The Button Status : "+btnsts.toString(),Toast.LENGTH_LONG).show();

                        FirebaseFirestore.getInstance().collection("order").document(jobid).update("status",Status.Accepted.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"Requeest Has Been Accepted",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Requeest Status Updating Failed",Toast.LENGTH_LONG).show();

                            }
                        });

                    } else if(txtstatus.getText().equals(Status.Working.toString())){

                        Intent t= new Intent(OrderDetails.this, CompleteWork.class);
                        t.putExtra("rateperhour",txtrate.getText().toString());
                        t.putExtra("docid",jobid.toString());
                        startActivity(t);

                    }
                    else if(txtstatus.getText().equals(Status.Complete.toString())){


                    }
                }
                else{

                    if(txtstatus.getText().equals(Status.Request.toString())){


                    } else if(txtstatus.getText().equals(Status.Accepted.toString())){

                        FirebaseFirestore.getInstance().collection("order").document(jobid).update("status",Status.Working.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"Requeest Has Been Accepted",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Requeest Status Updating Failed",Toast.LENGTH_LONG).show();

                            }
                        });


                    }
                    else if(txtstatus.getText().equals(Status.Complete.toString())){

                        Dialog rankDialog;
                        RatingBar ratingBar;
                        rankDialog = new Dialog(OrderDetails.this, R.style.FullHeightDialog);
                        rankDialog.setContentView(R.layout.rank_dialog);
                        rankDialog.setCancelable(true);
                        ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
                        ratingBar.setRating(0);
                        ratingBar.setStepSize(1);

                        TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
                        text.setText("Rate Service Provider");

                        Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                        updateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseFirestore.getInstance().collection("order").document(jobid).update("status",Status.OrderClosed.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getApplicationContext(),"Requeest Has Been Accepted",Toast.LENGTH_LONG).show();

                                         FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid",providerid.toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                             @Override
                                             public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                 if (task.isSuccessful()) {
                                                     for (QueryDocumentSnapshot document : task.getResult()) {
                                                         rating =  document.getString("rating").toString();
                                                         count =  document.getString("count").toString();
                                                         proUid =  document.getId().toString();
                                                         Toast.makeText(getApplicationContext(),"The count id is :"+ count.toString(),Toast.LENGTH_LONG).show();
                                                         Toast.makeText(getApplicationContext(),"The Rating is :"+ rating,Toast.LENGTH_LONG).show();
                                                     }
                                                     int   newRating = (int) Math.round(ratingBar.getRating());
                                                     count = (Integer.parseInt(count.toString()) + 1)+"";
                                                       rating = (Integer.parseInt(rating.toString()) + newRating )+"";



                                                     FirebaseFirestore.getInstance().collection("users").document(proUid).update("count",count ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                         @Override
                                                         public void onSuccess(Void unused) {

                                                             FirebaseFirestore.getInstance().collection("users").document(proUid).update("rating",rating ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                 @Override
                                                                 public void onSuccess(Void unused) {

                                                                     Toast.makeText(getApplicationContext(),"Rating Has Been Updated", Toast.LENGTH_LONG).show();
                                                                 }
                                                             });
                                                         }
                                                     });
                                                 }

                                             }
                                         });

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"Requeest Status Updating Failed",Toast.LENGTH_LONG).show();

                                    }
                                });
                            }
                        });
                        //now that the dialog is set up, it's time to show it
                        rankDialog.show();


                    }
                }
            }
        });


        btnopenchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t= new Intent(OrderDetails.this, ProviderChatActivity.class);
                String type = SharedPreference.getName(getApplicationContext(),"type");
                Toast.makeText(getApplicationContext(),"The Title is :"+type.toString(),Toast.LENGTH_LONG).show();
                if(type.equals(UserType.ServiceSeeker.toString())){

                    t.putExtra("userid", providerid.toString());
                    t.putExtra("username", providername.toString());
                    t.putExtra("usersts", "0");
                }else{
                    t.putExtra("userid", seekerid.toString());
                    t.putExtra("username", seekername.toString());
                    t.putExtra("usersts", "1");
                }


                startActivity(t);
            }
        });

        try {
            loaddata(jobid.toString());
            Toast.makeText(getApplicationContext(),"ID is  :"+ jobid.toString(),Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"Data Fatching Failed :"+e.toString(),Toast.LENGTH_LONG).show();
        }

    }

    void loaddata(String iiid){
        progressBar.show();
        fb.collection("order").document(iiid.toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               try {
                   seekerid= documentSnapshot.getString("seekerid").toString();
                   seekername= documentSnapshot.getString("seekername").toString();
                   providerid = documentSnapshot.getString("providerid").toString();
                   providername = documentSnapshot.getString("providername").toString();
                   txtprovidername.setText(documentSnapshot.getString("providername").toString());
                   txtcategory.setText(documentSnapshot.getString("category").toString());
                   txtstartdate.setText(documentSnapshot.getString("sdate").toString());
                   txtenddate.setText(documentSnapshot.getString("edate").toString());
                   Picasso.get().load(documentSnapshot.getString("providerimage").toString()).into(imageView);
                   txtrate.setText(documentSnapshot.getString("rate_per_hour").toString());
                   txtnumberofhours.setText(documentSnapshot.getString("n_o_hours").toString());
                   txttotalamount.setText(documentSnapshot.getString("totalamount").toString());
                   txtstatus.setText(documentSnapshot.getString("status").toString());
                   txtlocation.setText(documentSnapshot.getString("location").toString());

                   lati = documentSnapshot.getString("lati").toString();
                   longi  = documentSnapshot.getString("longi").toString();


                   String type = SharedPreference.getName(getApplicationContext(),"type");

                   if(txtstatus.getText().equals(Status.Request.toString())){
                       if(type.equals(UserType.ServiceProvider.toString())){
                           btn.setText("Accept Request");
                       }else{
                           btn.setText("Please Wait for Accept");
                           btn.setEnabled(false);
                       }

                   } else if(txtstatus.getText().equals(Status.Accepted.toString())){

                       if(type.equals(UserType.ServiceProvider.toString())){
                           btn.setText("Please Wait for Start Working ");
                           btn.setEnabled(false);
                       }else{
                           btn.setText("Start Work");
                       }


                   } else if (txtstatus.getText().equals(Status.Working.toString())){

                       if(type.equals(UserType.ServiceProvider.toString())){

                           btn.setText("Complete Work");
                       }else{

                           btn.setText("Please Wait for Complete Work ");
                           btn.setEnabled(false);

                       }
                   }
                   else if (txtstatus.getText().equals(Status.Complete.toString())){
                       if(type.equals(UserType.ServiceProvider.toString())){

                           btn.setText("Please Wait for Order Closed ");
                           btn.setEnabled(false);

                       }else{

                           btn.setText("Close Order");

                       }
                   }

                   progressBar.dismiss();
               } catch (Exception e){
                   Toast.makeText(getApplicationContext(),"Data Fatching Failed 1:"+e.toString(),Toast.LENGTH_LONG).show();
               }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(),"Data Fatching Failed :"+e.toString(),Toast.LENGTH_LONG).show();

            }
        });

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


}