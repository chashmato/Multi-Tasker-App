package rtp.raidtechpro.co_tasker;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import rtp.raidtechpro.co_tasker.adapters.ChatListAdapter;
import rtp.raidtechpro.co_tasker.models.ChatListModel;

public class Providerchatuserlist extends AppCompatActivity {
    RecyclerView chatuserslist;
    FirebaseFirestore fb;
    ArrayList<ChatListModel> chatmodellist;
    EditText txtmessage;
    Button btnsend;

    SwipeRefreshLayout swiperefresh;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerchatuserlist);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Provider Profile");
        getSupportActionBar().setSubtitle("Provider Profile");

        fb = FirebaseFirestore.getInstance();
        chatmodellist = new ArrayList<>();
        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh123);

//        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadChat();
//            }
//        });
//        chatuserslist = (RecyclerView) findViewById(R.id.chatuserslist123);
//        loadChat();

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