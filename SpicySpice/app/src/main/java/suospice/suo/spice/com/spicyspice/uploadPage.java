package suospice.suo.spice.com.spicyspice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class uploadPage extends AppCompatActivity {

    // CLEAN UP FOR LOCAL VARIABLES WHEN POSSIBLE
    private ImageButton foodImage;
    private static final int GALLREQ =2;
    private EditText name,desc,ingred;
    private Uri uri = null;
    private FirebaseDatabase database;
    private StorageReference storageReference, storageRef, filepath;
    private DatabaseReference mRef;
    private UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_page);

        name = (EditText) findViewById(R.id.itemName);
        desc = (EditText) findViewById(R.id.itemDesc);
        ingred = (EditText) findViewById(R.id.itemIng);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("Item");
        storageReference = FirebaseStorage.getInstance().getReference();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://glassy-ripsaw-181917.appspot.com");

    }

    public void imageButtonClicked(View v){

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        galleryIntent.setType("Image/*");
        startActivityForResult(galleryIntent,GALLREQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLREQ && resultCode==RESULT_OK){
            uri = data.getData();
            foodImage = (ImageButton) findViewById(R.id.foodImageButton);
            foodImage.setImageURI(uri);
        }
    }

    public void addItemButtonClicked(View v){

        final String name_text = name.getText().toString().trim();
        final String name_desc = desc.getText().toString().trim();
        final String name_ingred = ingred.getText().toString().trim();

//        Toast.makeText(uploadPage.this, "Strings Up: "+name_text+" "+name_desc+" "+name_ingred, Toast.LENGTH_LONG).show();

        if (!TextUtils.isEmpty(name_text) && !TextUtils.isEmpty(name_desc) && !TextUtils.isEmpty(name_ingred))
        {
            filepath = storageRef.child("Images").child(uri.getLastPathSegment());
            uploadTask = filepath.putFile(uri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Image Uploaded to firebase
                    final Uri downloadurl = taskSnapshot.getDownloadUrl();
//                    Toast.makeText(uploadPage.this, "Strings Up: "+name_text+" "+name_desc+" "+name_ingred, Toast.LENGTH_LONG).show();

                    DatabaseReference upToDatabase = mRef.push();
                    upToDatabase.child("name").setValue(name_text);
                    upToDatabase.child("desc").setValue(name_desc);
                    upToDatabase.child("ingred").setValue(name_ingred);
                    upToDatabase.child("image").setValue(downloadurl.toString());

                    Toast.makeText(uploadPage.this, "Uploaded to database", Toast.LENGTH_SHORT).show();

                }
            });


        }
        else{
            Toast.makeText(uploadPage.this, "Error. Empty field", Toast.LENGTH_SHORT).show();
        }

    }

}
