package suospice.suo.spice.com.spicyspice;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:

                    //Profile Fragment
                    setTitle("Profile page");
                    changeFragments(0);

                    return true;
                case R.id.navigation_dispense:

                    //Home Fragment
                    setTitle("Home page");
                    changeFragments(1);

                    return true;
                case R.id.navigation_settings:

                    //Settings Fragment
                    setTitle("Profile page");
                    changeFragments(2);

                    return true;
            }
            return false;
        }
    };

    private void changeFragments(int position){
        Fragment newFragment = null;
        if (position == 0) {
            newFragment = new profileFragment();
        } else if (position == 1){
            newFragment = new homeFragment();
        } else {
            newFragment = new settingsFragment();
        }

        getFragmentManager().beginTransaction().replace(R.id.menuFrame,newFragment).commit();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //First Setup Fragment
        changeFragments(0);
    }


}


