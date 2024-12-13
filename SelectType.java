package rtp.raidtechpro.co_tasker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import rtp.raidtechpro.co_tasker.provider.ProviderSetupActivity;
import rtp.raidtechpro.co_tasker.seeker.SetUpProfile;

public class SelectType extends Activity {


    RadioButton rbt1 , rbt2;
    Button btn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);

        rbt1 = (RadioButton) findViewById(R.id.simpleRadioButton);
        rbt2 = (RadioButton) findViewById(R.id.simpleRadioButton1);

        btn = (Button) findViewById(R.id.btn_selecttype) ;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rbt1.isChecked()== true){
                    Intent t= new Intent(SelectType.this, SetUpProfile.class);
                    t.putExtra("type" , "seeker");
                    startActivity(t);
                }else{

                    Intent t= new Intent(SelectType.this, ProviderSetupActivity.class);
                    t.putExtra("type" , "provider");
                    startActivity(t);
                }
            }
        });

    }
}