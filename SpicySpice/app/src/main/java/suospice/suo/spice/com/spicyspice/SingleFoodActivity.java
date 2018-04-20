package suospice.suo.spice.com.spicyspice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class SingleFoodActivity extends AppCompatActivity {

    private String food_key = null;
    private DatabaseReference mDatabase;
    private TextView singleFoodTitle, singleFoodDesc, singleFoodIngredient;
    private ImageView singleFoodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);

        food_key = getIntent().getExtras().getString("FoodID");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Item");

        singleFoodDesc = (TextView) findViewById(R.id.singleDescription);
        singleFoodTitle = (TextView) findViewById(R.id.singleTitle);
        singleFoodIngredient = (TextView) findViewById(R.id.singleIngredients);
        singleFoodImage = (ImageView) findViewById(R.id.singleImageView);

        mDatabase.child(food_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String food_name = (String) dataSnapshot.child("name").getValue();
                String food_desc = (String) dataSnapshot.child("desc").getValue();
                String food_ingr = (String) dataSnapshot.child("ingred").getValue();
                String food_image = (String) dataSnapshot.child("image").getValue();

                singleFoodTitle.setText(food_name);
                singleFoodDesc.setText(food_desc);
                singleFoodIngredient.setText(food_ingr);
                Picasso.with(SingleFoodActivity.this).load(food_image).into(singleFoodImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Nothing
            }
        });
    }
}
