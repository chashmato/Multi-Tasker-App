package rtp.raidtechpro.co_tasker.provider.ui.phelp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rtp.raidtechpro.co_tasker.R;


public class PHelpFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_p_help, container, false);
        return view;
    }
}