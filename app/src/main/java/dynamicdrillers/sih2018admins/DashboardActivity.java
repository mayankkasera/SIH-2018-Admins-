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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        SharedPreferences sharedPreferences = getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);


        Type = getIntent().getStringExtra("type");

//        if(Type.equals("authority_admin"))
  //          startActivity(new Intent(this,AuthorityDashboardActivity.class));

        if(Type.equals("admin"))
            Admins.setText("State Admins");
        else  if(Type.equals("state_admin"))
            Admins.setText("District Admins");
        else if(Type.equals("district_admin"))
            Admins.setText("Regions Admins");
        else
            Admins.setText("Authority Admins");




        CardComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(DashboardActivity.this);
                dialog.setContentView(R.layout.complaint_dialog_layout);
                dialog.setTitle("Choose  Action ");

                final TextView State = (TextView) dialog.findViewById(R.id.state);
                final TextView District = (TextView) dialog.findViewById(R.id.district);
                final TextView Region = (TextView) dialog.findViewById(R.id.region);

                if(Type.equals("admin")) {
                   State.setVisibility(View.VISIBLE);
                   District.setVisibility(View.VISIBLE);
                   Region.setVisibility(View.VISIBLE);
                }
                else  if(Type.equals("state_admin")){
                    State.setVisibility(View.GONE);
                    District.setVisibility(View.VISIBLE);
                    Region.setVisibility(View.VISIBLE);
                }
                else if(Type.equals("district_admin")){
                    State.setVisibility(View.GONE);
                    District.setVisibility(View.GONE);
                    Region.setVisibility(View.VISIBLE);
                }

                State.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(DashboardActivity.this,FilterActivity.class);
                        intent.putExtra("type","state");
                        startActivity(intent);
                    }
                });

                District.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(DashboardActivity.this,FilterActivity.class);
                        intent.putExtra("type","district");
                        startActivity(intent);
                    }
                });

                Region.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(DashboardActivity.this,FilterActivity.class);
                        intent.putExtra("type","region");
                        startActivity(intent);
                    }
                });



                dialog.show();


            }
        });


        CardAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(DashboardActivity.this);
                dialog.setContentView(R.layout.admins_dialog_layout);
                dialog.setTitle("Choose  Action ");



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
                        if(Type.equals("admin")){
                            intent = new Intent(DashboardActivity.this,StateAdminsActivity.class);
                        }
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
                //startActivity(new Intent(DashboardActivity.this,StateAdminsActivity.class));
            }
        });


        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,ProfileActivity.class));
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
