package dynamicdrillers.sih2018admins;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(this);
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    TextView Admins, ViewComplaints;
    CardView CardAdmin, CardComplaint, sendNotification;
    public static final String SharedprefenceName = "USER_DATA";
    private String Type;
    Button button;
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    ImageView profile_Icon;
    private ImageView drawer_open_icon;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    CircleImageView ProfileImage;
    TextView name,mobile,state;



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

                if(Type.equals("region_admin")){
                    Intent intent = new Intent(DashboardActivity.this,ComplaintsActivity.class);
                    intent.putExtra("type","complainer_region");
                    intent.putExtra("data",SharedpreferenceHelper.getInstance(getBaseContext()).getRegion());
                    startActivity(intent);
                }
                else {
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
                        State.setVisibility(View.VISIBLE);
                        District.setVisibility(View.VISIBLE);
                        Region.setVisibility(View.VISIBLE);

                    }
                    else if(Type.equals("district_admin")){
                        State.setVisibility(View.GONE);
                        District.setVisibility(View.VISIBLE);
                        Region.setVisibility(View.VISIBLE);
                    }


                    State.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(Type.equals("admin")){
                                dialog.dismiss();
                                Intent intent = new Intent(DashboardActivity.this,FilterActivity.class);
                                intent.putExtra("type","state");
                                startActivity(intent);
                            }
                            else {
                                dialog.dismiss();
                                Intent intent = new Intent(DashboardActivity.this,ComplaintsActivity.class);
                                intent.putExtra("type","complainer_state");
                                intent.putExtra("data",SharedpreferenceHelper.getInstance(getBaseContext()).getState());
                                startActivity(intent);
                            }

                        }
                    });

                    District.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(Type.equals("district_admin")){
                                Toast.makeText(DashboardActivity.this, SharedpreferenceHelper.getInstance(getBaseContext()).getDistrict(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(DashboardActivity.this,ComplaintsActivity.class);
                                intent.putExtra("type","complaint_district");
                                intent.putExtra("data",SharedpreferenceHelper.getInstance(getBaseContext()).getDistrict());
                                startActivity(intent);
                            }
                            else {
                                dialog.dismiss();
                                Intent intent = new Intent(DashboardActivity.this,FilterActivity.class);
                                intent.putExtra("type","district");
                                startActivity(intent);
                            }

                        }
                    });

                    Region.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(Type.equals("region_admin")){
                                Toast.makeText(DashboardActivity.this, SharedpreferenceHelper.getInstance(getBaseContext()).getDistrict(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(DashboardActivity.this,ComplaintsActivity.class);
                                intent.putExtra("type","complainer_region");
                                intent.putExtra("data",SharedpreferenceHelper.getInstance(getBaseContext()).getRegion());
                                startActivity(intent);
                            }
                            else {
                                dialog.dismiss();
                                Intent intent = new Intent(DashboardActivity.this,FilterActivity.class);
                                intent.putExtra("type","region");
                                startActivity(intent);
                            }

                        }
                    });



                    dialog.show();


                }


            }
        });
        profile_Icon = findViewById(R.id.toolbar_profile_icon);
        drawerLayout = findViewById(R.id.drawer_main);
        drawer_open_icon = findViewById(R.id.navigation_icon);



        drawer_open_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(Gravity.START);
            }
        });

        navigationView = (NavigationView)findViewById(R.id.main_activity_nav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case  R.id.navigation_profile:
                        Intent i = new Intent(DashboardActivity.this,ProfileActivity.class);
                        i.putExtra("admin_id",mAuth.getCurrentUser().getUid().toString());
                        i.putExtra("admin_type",Type);
                        startActivity(i);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;

                    case  R.id.navigation_logout:
                        Toast.makeText(getBaseContext(), "logout successfuly", Toast.LENGTH_SHORT).show();
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();


                        mAuth.signOut();

                        goToLoginPage();
                        drawerLayout.closeDrawer(Gravity.START);
                        break;



                    case  R.id.navigation_aboutus:
                        Toast.makeText(getBaseContext(), "aboutus  item Clicked", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(Gravity.START);
                        break;



                }

                return true;

            }
        });

        profile_Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DashboardActivity.this,ProfileActivity.class);
                i.putExtra("admin_id",mAuth.getCurrentUser().getUid().toString());
                i.putExtra("admin_type",Type);
                startActivity(i);
            }
        });


        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setNavigationItem();


        CardAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(DashboardActivity.this);
                dialog.setContentView(R.layout.admins_dialog_layout);
                dialog.setTitle("Choose  Action ");



                final Button AddAdmin = (Button) dialog.findViewById(R.id.add_admin);
                final Button AdminList = (Button) dialog.findViewById(R.id.admin_list);

                if(Type.equals("admin")){
                    AddAdmin.setText("Add State");
                    AdminList.setText("State Admins List");
                }

                else  if(Type.equals("state_admin")){
                    AddAdmin.setText("Add District");
                    AdminList.setText("District Admins List");
                }
                else if(Type.equals("district_admin")){
                    AddAdmin.setText("Add Region");
                    AdminList.setText("Region Admins");
                }
                else {
                    AddAdmin.setText("Add Authority");
                    AdminList.setText("Authority Admins List");
                }


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

        //Toast.makeText(this, sharedPreferences.getString("email",null)+" "+sharedPreferences.getString("password",null), Toast.LENGTH_SHORT).show();

    }

    /**
     *   Initialization of views
     */
    private void init(){
        Admins = findViewById(R.id.admin_dashboard_text);
        ViewComplaints = findViewById(R.id.viewComplaints_dashboard_txt);
        CardAdmin = findViewById(R.id.CardAdmins);
        CardComplaint =  findViewById(R.id.CardComplaints);
    }


    public  void setNavigationItem()

    {
        navigationView = (NavigationView)findViewById(R.id.main_activity_nav);
        View v = navigationView.getHeaderView(0);
        name = v.findViewById(R.id.navigation_header_name);
        mobile = v.findViewById(R.id.navigation_header_mobileno);
        ProfileImage = v.findViewById(R.id.navigation_header_image);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();

        mRoot.child(Type).child(mAuth.getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Picasso.with(DashboardActivity.this).load(dataSnapshot.child("image").getValue().toString()).into(ProfileImage);
                name.setText(dataSnapshot.child("name").getValue().toString());
                mobile.setText("Mob :"+ dataSnapshot.child("mobileno").getValue().toString());
                // state.setText(dataSnapshot.child(""));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Method For going on login page
    private void goToLoginPage() {

        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }


}
