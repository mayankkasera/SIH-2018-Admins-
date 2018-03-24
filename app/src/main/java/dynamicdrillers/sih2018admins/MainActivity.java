package dynamicdrillers.sih2018admins;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static dynamicdrillers.sih2018admins.SharedpreferenceHelper.SharedprefenceName;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mDataRoot = FirebaseDatabase.getInstance().getReference();


    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);

        if(!isUserLoggedIn())
        {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        else
        {
            String ee = SharedpreferenceHelper.getInstance(this).getType();
            Intent i = new Intent(this,DashboardActivity.class);
            i.putExtra("type",ee);
              startActivity(i);

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
