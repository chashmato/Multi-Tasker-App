package rtp.raidtechpro.co_tasker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {


    EditText txtusername;
    EditText txtpassword;
    EditText txtrepassword;
    TextView txtresponse;
    TextView txtsignupbtn;
    Button loginbutton;
    private ProgressDialog progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_sign_up);
        progressBar = new ProgressDialog (this);
        progressBar.setMessage("Loading Please wait ... ");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);

        mAuth = FirebaseAuth.getInstance();
        txtusername = (EditText) findViewById(R.id.txtsignup_username);
        txtpassword = (EditText) findViewById(R.id.txtsignup_password);
        txtrepassword = (EditText) findViewById(R.id.txtsignup_reenterpassword);
        txtresponse  = (TextView) findViewById(R.id.txtsignupresponse);
        loginbutton = (Button) findViewById(R.id.btn_signup);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent t = new Intent(SignupActivity.this,DashboardActivity.class);
//                startActivity(t);
                if(txtusername.getText().toString().equals("")){
                    txtresponse.setText("Enter Email first");
                } else if(txtpassword.getText().toString().equals("")){
                    txtresponse.setText("Enter Password first");
                } else if(txtrepassword.getText().toString().equals("")){
                    txtresponse.setText("Re enter Password");
                } else if (!txtpassword.getText().toString().equals(txtrepassword.getText().toString())){
                    txtresponse.setText("Password Miss Match");
                }
                else {
                    progressBar.show();
                    // create new user or register new user
                    mAuth.createUserWithEmailAndPassword(txtusername.getText().toString(), txtpassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful()) {
                                        progressBar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                        // if the user created intent to login activity
                                        Intent intent = new Intent(SignUp.this, SelectType.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        progressBar.dismiss();
                                        // Registration failed
                                        Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
                                        txtresponse.setText("SignUp Failed");
                                        // hide the progress bar
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Failed to Sign Up "+e.toString(),Toast.LENGTH_LONG).show();
                                    txtresponse.setText("Failed :"+e.toString());
                                }
                            });
                }
            }
        });

        txtsignupbtn = (TextView) findViewById(R.id.txtbtnlogin);
        txtsignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(SignUp.this,LoginActivity.class);
                startActivity(t);
            }
        });
    }
}