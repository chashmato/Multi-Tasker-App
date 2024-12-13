package rtp.raidtechpro.co_tasker.provider;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.adapters.ChatAdapter;
import rtp.raidtechpro.co_tasker.models.Chat;
import rtp.raidtechpro.co_tasker.models.ChatListModel;
import rtp.raidtechpro.co_tasker.models.ChatModel;
import rtp.raidtechpro.co_tasker.models.ChatTitleModel;
import rtp.raidtechpro.co_tasker.models.MemberGroup;
import rtp.raidtechpro.co_tasker.sharedpreferences.SharedPreference;

public class ProviderChatActivity extends AppCompatActivity {


    RecyclerView chatlist;
    FirebaseFirestore fb;
    ArrayList<Chat> chatmodellist;
    EditText txtmessage;
    Button btnsend;
    TextView chatusername;
    String username = "";
    String userid = "";

    String usersts ="1";


    private static ChatAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Provider Profile");
        getSupportActionBar().setSubtitle("Provider Profile");
        Intent t = getIntent();
        username = t.getStringExtra("username");
        userid = t.getStringExtra("userid");
        usersts = t.getStringExtra("usersts");

        fb = FirebaseFirestore.getInstance();
        chatmodellist = new ArrayList<>();
        chatlist = (RecyclerView) findViewById(R.id.chatmessageslist);
        txtmessage = (EditText) findViewById(R.id.chatMessage);
        btnsend = (Button) findViewById(R.id.sendbutton);
        chatusername  = (TextView) findViewById(R.id.txtchatusername);


        chatusername.setText(username.toString());

        loadChat();

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtmessage.getText().toString().equals("")){
                } else {
                    Date currentTime = Calendar.getInstance().getTime();
                    Chat model = new Chat(
                            FirebaseAuth.getInstance().getUid().toString(),
                            txtmessage.getText().toString(),
                            currentTime.toString(),
                            usersts.toString()
                    );



                    FirebaseFirestore.getInstance().collection("messages")
                            .whereIn("receiverid", Arrays.asList(userid, FirebaseAuth.getInstance().getUid().toString()))
                            .whereIn("senderid", Arrays.asList(userid, FirebaseAuth.getInstance().getUid().toString()))

                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            if(queryDocumentSnapshots.isEmpty()){

                                ChatListModel model= new ChatListModel(

                                        FirebaseAuth.getInstance().getUid().toString(),
                                        SharedPreference.getName(getApplicationContext(),"name"),
                                        userid,
                                        username
                                );

                                FirebaseFirestore.getInstance().collection("messages").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getApplicationContext(),"Created",Toast.LENGTH_LONG).show();
                                    }
                                });


                            } else{
                                 for (QueryDocumentSnapshot document :queryDocumentSnapshots) {
                                    String docid = document.getId().toString();
                                     Toast.makeText(getApplicationContext(),"Exists  :"+docid.toString(),Toast.LENGTH_LONG).show();
                                     FirebaseFirestore.getInstance().collection("messages").document(docid).collection("Conversatiob").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                         @Override
                                         public void onSuccess(DocumentReference documentReference) {
                                             Toast.makeText(getApplicationContext(),"Message Has Been Sent",Toast.LENGTH_LONG).show();
                                             loadChat();
                                             txtmessage.setText("");
                                         }
                                     });

                                 }


                            }
                        }
                    });

                }
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


    void loadChat() {

        chatmodellist.clear();
        fb.collection("messages")
                .whereIn("receiverid", Arrays.asList(userid, FirebaseAuth.getInstance().getUid().toString()))
                .whereIn("senderid", Arrays.asList(userid, FirebaseAuth.getInstance().getUid().toString())).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot document :queryDocumentSnapshots) {

                            String docid = document.getId();

                            chatmodellist.clear();

                            fb.collection("messages").
                                    document(docid).
                                    collection("Conversatiob").orderBy("datetime", Query.Direction.ASCENDING)
                                    // .whereEqualTo("from",FirebaseAuth.getInstance().toString())
                                    // .whereEqualTo("to","Admin")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Chat model  = new Chat(
                                                            document.getString("sender").toString(),
                                                            document.getString("message").toString(),
                                                            document.getString("datetime").toString(),
                                                            document.getString("sts").toString()
                                                    );
                                                    chatmodellist.add(model);
                                                }
                                                adapter= new ChatAdapter(getApplicationContext(),chatmodellist);
                                                chatlist.setAdapter(adapter);//sets the adapter for listView
                                            } else {
                                                Log.d(TAG, "Error geting Chat List: ", task.getException());
                                            }
                                        }
                                    });
                        }
                    }
                });
    }


}