package suospice.suo.spice.com.spicyspice;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import suospice.suo.spice.com.spicyspice.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment{

    private ImageButton searchButton;
    private ImageButton uploadButton;
    private ImageButton dispenseButton;
    private ImageButton settingsButton;

    public homeFragment() {
        // Required empty public constructor
    }

    private void searchButton(){
        //method
        Toast.makeText(getActivity(), "Searching!", Toast.LENGTH_SHORT).show();
    }

    private void uploadButton(){
        //method
        Toast.makeText(getActivity(), "Uploading!", Toast.LENGTH_SHORT).show();
    }

    private void dispenseButton(){
        //method
        Toast.makeText(getActivity(), "Dispensing!", Toast.LENGTH_SHORT).show();
    }

    private void settingsButton(){
        //method
        Toast.makeText(getActivity(), "Settings!", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view){
        if (view == searchButton){
            searchButton();
        }
        if (view == uploadButton){
            uploadButton();
        }
        if (view == dispenseButton){
            dispenseButton();
        }
        if (view == settingsButton){
            settingsButton();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        searchButton = (ImageButton) v.findViewById(R.id.searchImageButton);
        uploadButton = (ImageButton) v.findViewById(R.id.uploadImageButton);
        dispenseButton = (ImageButton) v.findViewById(R.id.dispenseImageButton);
        settingsButton = (ImageButton) v.findViewById(R.id.settingsImageButton);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}
