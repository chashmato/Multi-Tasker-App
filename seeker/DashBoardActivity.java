package rtp.raidtechpro.co_tasker.seeker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import rtp.raidtechpro.co_tasker.ChatUserList;
import rtp.raidtechpro.co_tasker.R;
import rtp.raidtechpro.co_tasker.databinding.ActivityDashBoardBinding;
import rtp.raidtechpro.co_tasker.sharedpreferences.SharedPreference;

public class DashBoardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashBoardBinding binding;

    ImageView profileimage;
    TextView txtprofilename , txtprofileemail;

    String username  = "";
    String useremail  = "";
    String type  = "";
    String profileimagepath  = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         username  = SharedPreference.getName(getApplicationContext(),"name").toString();
         useremail  = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
         type  = SharedPreference.getName(getApplicationContext(),"seeker").toString();
         profileimagepath  = SharedPreference.getName(getApplicationContext(),"profileimagepath").toString();


        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView1.getHeaderView(0);
        ImageView navUserImage = (ImageView) headerView.findViewById(R.id.imageview);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textViewusername);
        TextView navUseremali = (TextView) headerView.findViewById(R.id.textViewuseremail);

        Picasso.get().load(profileimagepath).into(navUserImage);
        navUsername.setText(username);
        navUseremali.setText(useremail);

        setSupportActionBar(binding.appBarDashBoard.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard,
                R.id.nav_profile,
                R.id.nav_about,
                R.id.nav_help
                )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dash_board);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }





    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dash_board);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}