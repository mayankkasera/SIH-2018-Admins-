package dynamicdrillers.sih2018admins;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static dynamicdrillers.sih2018admins.SharedpreferenceHelper.SharedprefenceName;

public class DashboardActivity extends AppCompatActivity {

    SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(this);
    FirebaseAuth mAuth =  FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    TextView Admins,ViewComplaints;
    CardView CardAdmin,CardComplaint,sendNotification;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Admins = (TextView)findViewById(R.id.admin_dashboard_text);
        ViewComplaints = (TextView)findViewById(R.id.viewComplaints_dashboard_txt);
        CardAdmin = (CardView)findViewById(R.id.CardAdmins);
        CardComplaint = (CardView)findViewById(R.id.CardComplaints);
        sendNotification = (CardView)findViewById(R.id.sendNotifications_dashboard_cardview);

        Toast.makeText(this, sharedpreferenceHelper.getName(), Toast.LENGTH_SHORT).show();


    }
}
