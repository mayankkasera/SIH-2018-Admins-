package dynamicdrillers.sih2018admins;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(this);
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    TextView Admins, ViewComplaints;
    CardView CardAdmin, CardComplaint, sendNotification;
    public static final String SharedprefenceName = "USER_DATA";
    private String Type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        //Type = sharedPreferences.getString("type","czc");
        Toast.makeText(DashboardActivity.this,mAuth.getUid(), Toast.LENGTH_SHORT).show();


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Management_Users").child(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Type = dataSnapshot.child("type").getValue().toString();
                Toast.makeText(DashboardActivity.this, Type, Toast.LENGTH_SHORT).show();

                if(Type.equals("admin"))
                    Admins.setText("State Admins");
                else  if(Type.equals("state_admin"))
                    Admins.setText("District Admins");
                else if(Type.equals("district_admin"))
                    Admins.setText("Subregion Admins");
                else
                    Admins.setText("Authority Admins");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        CardAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(DashboardActivity.this);
                dialog.setContentView(R.layout.admins_dialog_layout);
                dialog.setTitle("Chose  Action ");



                final Button AddAdmin = (Button) dialog.findViewById(R.id.add_admin);
                final Button AdminList = (Button) dialog.findViewById(R.id.admin_list);

                AddAdmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(DashboardActivity.this,AdminsRegistrationActivity.class));
                        dialog.dismiss();
                    }
                });

                AdminList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent ;
                        if(Type.equals("admin"))
                            intent = new Intent(DashboardActivity.this,AdminsListActivity.class);
                        else  if(Type.equals("state_admin"))
                            intent = new Intent(DashboardActivity.this,DistrictAdminsActivity.class);
                        else if(Type.equals("district_admin"))
                            intent = new  Intent(DashboardActivity.this,RegionsAdminActivity.class);
                        else
                            intent = new  Intent(DashboardActivity.this,AuthorityAdminActivity.class);

                        intent.putExtra("type",Type);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });


                dialog.show();
                //startActivity(new Intent(DashboardActivity.this,AdminsListActivity.class));
            }
        });

        CardComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,AdminsRegistrationActivity.class));
            }
        });



        Toast.makeText(this, sharedPreferences.getString("email",null)+" "+sharedPreferences.getString("password",null), Toast.LENGTH_SHORT).show();

    }

    /**
     *   Initialization of views
     */
    private void init(){
        Admins = findViewById(R.id.admin_dashboard_text);
        ViewComplaints = findViewById(R.id.viewComplaints_dashboard_txt);
        CardAdmin = findViewById(R.id.CardAdmins);
        CardComplaint =  findViewById(R.id.CardComplaints);
        sendNotification = findViewById(R.id.sendNotifications_dashboard_cardview);
    }
}
