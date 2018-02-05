package suospice.suo.spice.com.spicyspice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTextMessage;

    private ImageButton searchButton;
    private ImageButton uploadButton;
    private ImageButton dispenseButton;
    private ImageButton settingsButton;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    private void searchButton(){
        Toast.makeText(MenuActivity.this, "Searching!", Toast.LENGTH_SHORT).show();
    }

    private void uploadButton(){
        Toast.makeText(MenuActivity.this, "Uploading!", Toast.LENGTH_SHORT).show();
    }

    private void dispenseButton(){
        Toast.makeText(MenuActivity.this, "Dispensing!", Toast.LENGTH_SHORT).show();
    }

    private void settingsButton(){
        Toast.makeText(MenuActivity.this, "Settings!", Toast.LENGTH_SHORT).show();
    }


    @Override
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        searchButton = (ImageButton) findViewById(R.id.searchImageButton);
        uploadButton = (ImageButton) findViewById(R.id.uploadImageButton);
        dispenseButton = (ImageButton) findViewById(R.id.dispenseImageButton);
        settingsButton = (ImageButton) findViewById(R.id.settingsImageButton);


    }


}


