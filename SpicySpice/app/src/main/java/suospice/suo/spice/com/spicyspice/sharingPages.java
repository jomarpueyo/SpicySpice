package suospice.suo.spice.com.spicyspice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class sharingPages extends AppCompatActivity {

    private RecyclerView mRecipeList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing_page);

        mRecipeList = (RecyclerView) findViewById(R.id.recipeList);
        mRecipeList.setHasFixedSize(true);
        mRecipeList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Item");

    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<getValues,FoodViewHolder> FBRA = new FirebaseRecyclerAdapter<getValues, FoodViewHolder>(

                getValues.class,
                R.layout.single_card_view,
                FoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, getValues model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                final String food_key = getRef(position).getKey().toString();
                viewHolder.mView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent singleFoodActivity = new Intent(sharingPages.this, SingleFoodActivity.class);
                        singleFoodActivity.putExtra("FoodID",food_key);
                        startActivity(singleFoodActivity);
                    }
                });
            }
        };
        mRecipeList.setAdapter(FBRA);
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public FoodViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }


        public void setName(String name){
            TextView food_name = (TextView) mView.findViewById(R.id.foodName);
            food_name.setText(name);
        }
        public void setDesc(String desc){
            TextView food_desc = (TextView) mView.findViewById(R.id.foodDesc);
            food_desc.setText(desc);
        }
//        public void setIngred(String ingred){
//        }

        public void setImage(Context ctx, String image){
            ImageView food_image = (ImageView) mView.findViewById(R.id.foodImage);
            Picasso.with(ctx).load(image).into(food_image);
        }

    }

}
