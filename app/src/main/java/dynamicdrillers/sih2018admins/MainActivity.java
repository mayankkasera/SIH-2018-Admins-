package dynamicdrillers.sih2018admins;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mDataRoot = FirebaseDatabase.getInstance().getReference();

    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // FirebaseAuth.getInstance().signOut();
        // fun("district@gmail.com","sejal@123");

        //Checking User Is Logged In or Not
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AdminsRegistrationActivity.class));
            }
        });





        if(!isUserLoggedIn())
        {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        else
        {
            startActivity(new Intent(this,DashboardActivity.class));
            finish();
        }




    }

    void fun(String email,String password){

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("sign ining ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
                            progressBar.dismiss();


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.dismiss();
                        }

                        // ...
                    }
                });
    }

    private boolean isUserLoggedIn() {

        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
