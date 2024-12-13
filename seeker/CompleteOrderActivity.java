package rtp.raidtechpro.co_tasker.seeker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import rtp.raidtechpro.co_tasker.R;



public class CompleteOrderActivity extends AppCompatActivity {


    TextView txtorderid;
    TextView txtorderdate;
    TextView txtorderstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);

        txtorderid  =(TextView) findViewById(R.id.txtorder_id);
        txtorderdate  =(TextView) findViewById(R.id.txtorder_date);
        txtorderstatus  =(TextView) findViewById(R.id.txtorder_status);




    }



    void loaddata(){

    }
}