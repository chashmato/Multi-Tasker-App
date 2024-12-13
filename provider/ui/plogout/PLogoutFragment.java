package rtp.raidtechpro.co_tasker.provider.ui.plogout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import rtp.raidtechpro.co_tasker.LoginActivity;
import rtp.raidtechpro.co_tasker.R;

public class PLogoutFragment extends Fragment {



    Button btn;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_p_logout, container, false);
        btn = (Button) view.findViewById(R.id.btnlogout123);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                SharedPreferences settings = getContext().getSharedPreferences("usersharedpreferences", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                Intent t = new Intent(getContext(), LoginActivity.class);
                startActivity(t);
                getActivity().finish();
            }
        });
        return view;
    }
}