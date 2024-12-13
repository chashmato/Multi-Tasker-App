package rtp.raidtechpro.co_tasker;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import rtp.raidtechpro.co_tasker.provider.PDashBoard;
import rtp.raidtechpro.co_tasker.seeker.DashBoardActivity;
import rtp.raidtechpro.co_tasker.sharedpreferences.SharedPreference;

public class LoginActivity extends AppCompatActivity {

    EditText txtusername;
    EditText txtpassword;
    TextView txtresponse;
    TextView txtsignupbtn,txtforgetpassword;
    Button loginbutton;
    private FirebaseAuth mAuth;
    private ProgressDialog progressBar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        progressBar = new ProgressDialog (this);
        progressBar.setMessage("Loading Please wait ... ");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        txtusername = (EditText) findViewById(R.id.txtlogin_username);
        txtpassword = (EditText) findViewById(R.id.txtlogin_password);
        txtresponse  = (TextView) findViewById(R.id.txtloginresponse);
        loginbutton = (Button) findViewById(R.id.btn_login);
        txtsignupbtn = (TextView) findViewById(R.id.txtbtnsignup);
        txtforgetpassword = (TextView) findViewById(R.id.txtforgetpassword);


        txtforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtusername.getText().toString().equals("")){
                    txtresponse.setText("Please Enter an Email Address");
                }else {

                    FirebaseAuth.getInstance().sendPasswordResetEmail(txtusername.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            txtresponse.setText("A Link have to submit on your mail");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            txtresponse.setText("Link sending Failed");
                        }
                    });
                }
            }
        });

        txtsignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent t = new Intent(LoginActivity.this, SignUp.class);
                startActivity(t);
            }
        });


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(txtusername.getText().toString().toString().equals("")){
                    txtresponse.setText("Enter Email first");
                }else if(txtpassword.getText().toString().equals("")){
                    txtresponse.setText("Enter Password first");
                } else{
                    progressBar.show();
                    mAuth.signInWithEmailAndPassword(txtusername.getText().toString(), txtpassword.getText().toString())
                            .addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(
                                                @NonNull Task<AuthResult> task)
                                        {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_LONG).show();
                                                // hide the progress bar

                                                FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid",FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {

                                                                String type =  document.getString("type").toString();
                                                                if(type.equals("ServiceProvider")){

                                                                    progressBar.dismiss();
                                                                    SharedPreference.saveName( getApplicationContext(),"docid",document.getId().toString());
                                                                    SharedPreference.saveName( getApplicationContext(),"name",document.getString("name"));
                                                                    SharedPreference.saveName( getApplicationContext(),"phone",document.getString("phonenumber"));
                                                                    SharedPreference.saveName( getApplicationContext(),"city",document.getString("city"));
                                                                    SharedPreference.saveName( getApplicationContext(),"category",document.getString("category"));
                                                                    SharedPreference.saveName( getApplicationContext(),"about",document.getString("about"));
                                                                    SharedPreference.saveName( getApplicationContext(),"type",UserType.ServiceProvider.toString());
                                                                    SharedPreference.saveName( getApplicationContext(),"profileimagepath",document.getString("photopath"));
                                                                    Intent intent = new Intent(LoginActivity.this, PDashBoard.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else{
                                                                    progressBar.dismiss();
                                                                    SharedPreference.saveName( getApplicationContext(),"docid",document.getId().toString());
                                                                    SharedPreference.saveName( getApplicationContext(),"name",document.getString("name"));
                                                                    SharedPreference.saveName( getApplicationContext(),"phone",document.getString("mobileno"));
                                                                    SharedPreference.saveName( getApplicationContext(),"city",document.getString("city"));
                                                                    SharedPreference.saveName( getApplicationContext(),"qualification",document.getString("qualification"));
                                                                    SharedPreference.saveName( getApplicationContext(),"about",document.getString("about"));
                                                                    SharedPreference.saveName( getApplicationContext(),"type",UserType.ServiceSeeker.toString());
                                                                    SharedPreference.saveName( getApplicationContext(),"profileimagepath",document.getString("imagepath"));
                                                                    Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            }

                                                        } else {
                                                            Log.d(TAG, "Error getting  Data: ", task.getException());
                                                        }
                                                    }

                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        progressBar.dismiss();
                                                        // sign-in failed
                                                        Toast.makeText(getApplicationContext(), "Error" + e.toString(), Toast.LENGTH_LONG).show();
                                                        txtresponse.setText("Error :"+ e.toString());
                                                    }
                                                });
                                            }
                                            else {
                                                progressBar.dismiss();
                                                // sign-in failed
                                                Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();
                                                txtresponse.setText("Login Failed");
                                                // hide the progress bar

                                            }
                                        }
                                    });
                }
            }
        });


    }
}