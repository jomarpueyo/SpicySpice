package suospice.suo.spice.com.spicyspice;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import suospice.suo.spice.com.spicyspice.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class settingsFragment extends Fragment {


    public settingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

}
