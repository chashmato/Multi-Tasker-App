package rtp.raidtechpro.co_tasker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

import rtp.raidtechpro.co_tasker.provider.PDashBoard;
import rtp.raidtechpro.co_tasker.seeker.DashBoardActivity;
import rtp.raidtechpro.co_tasker.sharedpreferences.SharedPreference;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               if(FirebaseAuth.getInstance().getUid() == null){
                   Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                   startActivity(intent);
                   finish();
               } else{
                 String type = SharedPreference.getName(getApplicationContext(),"type");
                 if(type.equals("ServiceProvider")){
                     Intent intent=new Intent(MainActivity.this, PDashBoard.class);
                     startActivity(intent);
                     finish();
                 }else{
                     Intent intent=new Intent(MainActivity.this, PDashBoard.class);
                     startActivity(intent);
                     finish();
                 }
               }
            }
        },3000);
    }
}