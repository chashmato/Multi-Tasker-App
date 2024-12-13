package rtp.raidtechpro.co_tasker;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class ChatUserList extends AppCompatActivity {


    RecyclerView chatuserslist;
    FirebaseFirestore fb;
    ArrayList<ChatListModel> chatmodellist;
    EditText txtmessage;
    Button btnsend;

    SwipeRefreshLayout swiperefresh;

    private static ChatListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Provider Profile");
        getSupportActionBar().setSubtitle("Provider Profile");

        fb = FirebaseFirestore.getInstance();
        chatmodellist = new ArrayList<>();
        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadChat();
            }
        });
        chatuserslist = (RecyclerView) findViewById(R.id.chatuserslist);
        loadChat();

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


    void  loadChat() {
        chatmodellist.clear();
        fb.collection("messages").document(FirebaseAuth.getInstance().getUid().toString()).collection("chats")
                // .whereEqualTo("from",FirebaseAuth.getInstance().toString())
                // .whereEqualTo("to","Admin")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                ChatListModel model  = new ChatListModel(
//                                        document.getId().toString()
////                                        document.getString("id").toString(),
////                                        document.getString("name").toString()
//
//                                );
                                //chatmodellist.add(model);
                            }
                            adapter= new ChatListAdapter(getApplicationContext(),chatmodellist);
                            chatuserslist.setAdapter(adapter);//sets the adapter for listView
                        } else {
                            Log.d(TAG, "Error geeting Chat List: ", task.getException());
                        }
                    }
                });
    }
}