package rtp.raidtechpro.co_tasker.provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.Status;

public class CompleteWork extends AppCompatActivity {


    EditText txt1,txt2,txt3;
    Button btn;

    TextView txttextresponse;
    String docid = "";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_work);

        Intent t  = getIntent();
        docid = t.getStringExtra("docid");

        txt1 = (EditText) findViewById(R.id.txttotalrateph12);
        txt2 = (EditText) findViewById(R.id.txttotalhours12);
        txt3 = (EditText) findViewById(R.id.txttotalamount12);

        txt1.setText(t.getStringExtra("rateperhour"));

        txttextresponse = (TextView) findViewById(R.id.txttextresponse);

        btn  = (Button) findViewById(R.id.btn_completework);

        txt2.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if(s.toString().equals("")){

                } else{
                    double rte =0,hours =0;
                    hours = Double.parseDouble(s.toString());
                    rte = Double.parseDouble(txt1.getText().toString());
                    double total = rte * hours;

                    txt3.setText(total+"");

                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(txt1.getText().equals("")){
                    txttextresponse.setText("Enter Rate per Hour Missings");
                }
                else if(txt2.getText().equals("")){
                    txttextresponse.setText("Enter Number od Hours");
                }
                else if(txt3.getText().equals("")){
                    txttextresponse.setText("Enter Total Number of Amount");
                } else{

                    FirebaseFirestore.getInstance().collection("order").document(docid).update(
                            "status", Status.Complete.toString(),
                            "n_o_hours",txt2.getText().toString(),
                            "totalamount",txt3.getText().toString()
                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(),"Work has been Marked Completed",Toast.LENGTH_LONG).show();
                            finish();

                            openDialogue();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failed to Update Status",Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });

    }



    public void openDialogue(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Rate this Provider");
                alertDialogBuilder.setPositiveButton("Rate",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(CompleteWork.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                            }
                        });

                        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}