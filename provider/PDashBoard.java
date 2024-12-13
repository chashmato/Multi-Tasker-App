package rtp.raidtechpro.co_tasker.provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import rtp.raidtechpro.co_tasker.ChatUserList;
import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.UserType;
import rtp.raidtechpro.co_tasker.provider.ui.myorders.MyOrderFragment;
import rtp.raidtechpro.co_tasker.provider.ui.pabout.PAboutFragment;
import rtp.raidtechpro.co_tasker.provider.ui.pdashboard.PDashboarFragment;
import rtp.raidtechpro.co_tasker.provider.ui.phelp.PHelpFragment;
import rtp.raidtechpro.co_tasker.provider.ui.plogout.PLogoutFragment;
import rtp.raidtechpro.co_tasker.provider.ui.pprofile.PProfileFragment;
import rtp.raidtechpro.co_tasker.sharedpreferences.SharedPreference;


public class PDashBoard extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{


    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    String username  = "";

    String userid  = "";
    String newRequests = "0";
    String useremail  = "";
    String type  = "";
    String profileimagepath  = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdash_board);


        username  = SharedPreference.getName(getApplicationContext(),"name").toString();
        userid  = SharedPreference.getName(getApplicationContext(),"docid").toString();
        useremail  = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        type  = SharedPreference.getName(getApplicationContext(), UserType.ServiceProvider.toString()).toString();
        profileimagepath  = SharedPreference.getName(getApplicationContext(),"profileimagepath").toString();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(username);
        getSupportActionBar().setSubtitle("New Requests  :"+ newRequests.toString());
        Toolbar toolbar = findViewById(R.id.maintoolbar);


        NavigationView navigationView1 = (NavigationView) findViewById(R.id.mainnav_view);
        View headerView = navigationView1.getHeaderView(0);
        ImageView navUserImage = (ImageView) headerView.findViewById(R.id.imageview12);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textViewusername12);
        TextView navUseremali = (TextView) headerView.findViewById(R.id.textViewuseremail12);

        Picasso.get().load(profileimagepath).into(navUserImage);
        navUsername.setText(username);
        navUseremali.setText(useremail);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.maindrawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.mainnav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_pdashboard);

        FragmentManager fragmentManager = getSupportFragmentManager();
        PDashboarFragment fragment = new PDashboarFragment();
        fragmentManager.beginTransaction().replace(R.id.mainframeLayout, fragment).commit();

        try {
            getCounts();
        } catch(Exception e){
            Toast.makeText(getApplicationContext(),"Exception Ocured", Toast.LENGTH_LONG).show();
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.nav_pdashboard) {
            fragment = new PDashboarFragment();
        } else if(id == R.id.nav_pmyorders){
            fragment = new MyOrderFragment();
        }


        else if(id == R.id.nav_pprofile){
            fragment = new PProfileFragment();
        }else if(id == R.id.nav_phelp){
            fragment = new PHelpFragment();
        }else if(id == R.id.nav_pabout){
            fragment = new PAboutFragment();
        }else if(id == R.id.nav_plogout){
            fragment = new PLogoutFragment();
        }
        fragmentManager.beginTransaction().replace(R.id.mainframeLayout, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.maindrawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.maindrawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    void getCounts(){


        FirebaseFirestore.getInstance().collection("order").whereEqualTo("providerid",FirebaseAuth.getInstance().getUid().toString()).whereEqualTo("status","Request").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {



                if(task.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), "The User id is :"+ FirebaseAuth.getInstance().getUid().toString() + "  Size  :"+ task.getResult().size(), Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setSubtitle("New Requests  :"+ task.getResult().size());
                }
                else{
                    getSupportActionBar().setSubtitle("New Requests  : 0");
                }


            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // methods to control the operations that will
    // happen when user clicks on the action buttons
    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {

        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}