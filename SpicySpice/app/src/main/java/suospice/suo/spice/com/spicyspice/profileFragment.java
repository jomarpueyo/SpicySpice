package suospice.suo.spice.com.spicyspice;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import suospice.suo.spice.com.spicyspice.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {

    TextView eText, nameText, verifiedText;

    public profileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        eText = (TextView) v.findViewById(R.id.emailText);
//        nameText = (TextView) v.findViewById(R.id.displayName);
        verifiedText = (TextView) v.findViewById(R.id.verifiedBool);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            boolean emailVerified = user.isEmailVerified();

            //Append names to the text for app
            eText.append(" "+email);

            if (emailVerified ){
                verifiedText.append(" True");
            }
            else{
                verifiedText.append(" False");
            }

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
        // Inflate the layout for this fragment
        return v;
    }

}
