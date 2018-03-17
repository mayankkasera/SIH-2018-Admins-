package dynamicdrillers.sih2018admins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mDataRoot = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checking User Is Logged In or Not
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
