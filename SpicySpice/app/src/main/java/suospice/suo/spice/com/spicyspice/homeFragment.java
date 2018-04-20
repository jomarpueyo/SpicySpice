package suospice.suo.spice.com.spicyspice;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment implements View.OnClickListener{

    private ImageButton searchButton;
    private ImageButton uploadButton;
    //private ImageButton settingsButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        searchButton = (ImageButton) v.findViewById(R.id.searchImageButton);
        uploadButton = (ImageButton) v.findViewById(R.id.uploadImageButton);
       // settingsButton = (ImageButton) v.findViewById(R.id.settingsImageButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), sharingPages.class);
                startActivity(myIntent);
                ((Activity) getActivity()).overridePendingTransition(0,0);
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), uploadPage.class);
                startActivity(myIntent);
                ((Activity) getActivity()).overridePendingTransition(0,0);
            }
        });

//        settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Settings!", Toast.LENGTH_SHORT).show();
//            }
//        });

        return v;
    }

    public homeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View view){

    }
}