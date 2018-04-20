package suospice.suo.spice.com.spicyspice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private CheckBox checkBox;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_EMAIL = "email";
    private static final String PREF_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        editTextEmail = (EditText) findViewById(R.id.editEmail);
        editTextPassword = (EditText) findViewById(R.id.editPassword);
        textViewSignUp = (TextView) findViewById(R.id.textSignUp);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);

        //Save Pref
        SharedPreferences  pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String email = pref.getString(PREF_EMAIL, null);
        String password = pref.getString(PREF_PASSWORD,null);

        progressDialog.setMessage("Loading..");
        progressDialog.show();
        //Auto-Login onCreate Activity
        if (email != null && password != null){
            //Attempt silent login through pref
            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.hide();
                                // Toast.makeText(MainActivity.this, "TEST: AUTO LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                                // >> Move Activities
                                Intent myIntent = new Intent(MainActivity.this, MenuActivity.class);
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                startActivity(myIntent);
                            }
                            else{
                                //Clear Prefs file 'Deleted Prefs account'
                                progressDialog.hide();
                                getSharedPreferences(PREFS_NAME,0)
                                        .edit()
                                        .clear()
                                        .apply();
                                // Toast.makeText(MainActivity.this, "TEST: DELETED PREFS FILE", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        progressDialog.hide();
        //Normal Activity
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }

    private void signInUser(){
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty((email))){
            //email field is empty
            Toast.makeText(this, "Empty email field.",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password field is empty
            Toast.makeText(this, "Empty password field.",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Verifying credentials...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Successful Login
                            progressDialog.hide();
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            if (checkBox.isChecked()){
                                //Remember Me section
                                getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                                        .edit()
                                        .putString(PREF_EMAIL, email)
                                        .putString(PREF_PASSWORD, password)
                                        .apply();

                            }
                            // >> Move Activities
                            startActivity(new Intent(MainActivity.this, MenuActivity.class));
                        }
                        else{
                            //Failed Login
                            progressDialog.hide();
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void RegisterUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty((email))){
            //email field is empty
            Toast.makeText(this, "Empty email field.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password field is empty
            Toast.makeText(this, "Empty password field.",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //user is successfully registered and logged in
                            //we will start profile activity
                            progressDialog.hide();
                            Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                        }
                        else{
                            //user has failed to register
                            progressDialog.hide();
                            Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view){
        if(view == buttonSignIn){
            signInUser();
        }
        if(view == textViewSignUp){
            RegisterUser();
        }
    }
}
