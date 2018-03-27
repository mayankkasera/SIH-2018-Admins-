package dynamicdrillers.sih2018admins;

import android.app.ProgressDialog;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminsRegistrationActivity extends AppCompatActivity {


    private TextInputLayout Name,Email,Password,MobileNo,State,District,Authority;
    private RadioGroup Gender;
    private LinearLayout DistrictLayout,AuthorityLayout,StateLayout,RegionLayout;
    private Button button;
    private String Gender_s="Male",Type;
    private String id;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ProgressDialog progressBar;
    public static final String SharedprefenceName = "USER_DATA";
    LinearLayout choose_location;
    TextView choose_location_textview;
    String User_Type = SharedpreferenceHelper.getInstance(this).getType();
    int PLACE_PICKER_REQUEST = 1;
    Double Lat,Long;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_registration);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("INITIALIZING ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();


        //Initialization of variables
        init();

        //setting Visiblity of District Layout
        setDistrictVisiblity();

        if(User_Type.equals("district_admin"))
        {
            choose_location.setVisibility(View.VISIBLE);
        }

        choose_location_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(AdminsRegistrationActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }


            }
        });



        //Registration
        button = findViewById(R.id.register_btn_admin_reg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = new ProgressDialog(view.getContext());
                progressBar.setMessage("Registering...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.show();

                registerAdmins();

            }
        });

        //Geting Gender value
        Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId=Gender.getCheckedRadioButtonId();
                RadioButton radioSexButton=(RadioButton)findViewById(selectedId);
                Gender_s = radioSexButton.getText().toString();
            }
        });


    }

    private void registerAdmins() {

        //Signout curent user
        FirebaseAuth.getInstance().signOut();

        //Registration of State or district admin
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(Email.getEditText().getText().toString(),Password.getEditText().getText().toString())
                .addOnCompleteListener(AdminsRegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            //Setting type of user in database
                            FirebaseUser user = mAuth.getCurrentUser();
                            database = FirebaseDatabase.getInstance();
                            myRef= database.getReference().child("Management_Users").child(user.getUid());


                            final HashMap<String,String> userInfo = new HashMap<String, String>();
                            if(Type.equals("admin"))
                                userInfo.put("type","state_admin");
                            else if(Type.equals("state_admin"))
                                userInfo.put("type","district_admin");
                            else if(Type.equals("district_admin")){
                                userInfo.put("type","authority_admin");
                            }


                            myRef.setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(AdminsRegistrationActivity.this, "register....state",
                                            Toast.LENGTH_SHORT).show();

                                    //setting person detail
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if(Type.equals("admin")) {
                                        myRef = database.getReference().child("state_admin").child(user.getUid());                                    }
                                    else if(Type.equals("state_admin"))
                                        myRef = database.getReference().child("district_admin").child(user.getUid());
                                    else if(Type.equals("district_admin")){
                                        myRef = database.getReference().child("region_admin").child(user.getUid());

                                    }


                                    HashMap<String,String> userInfo1 = new HashMap<String, String>();
                                    userInfo1.put("name",Name.getEditText().getText().toString());
                                    userInfo1.put("password",Password.getEditText().getText().toString());
                                    userInfo1.put("email",Email.getEditText().getText().toString());
                                    userInfo1.put("gender",Gender_s);
                                    userInfo1.put("mobileno",MobileNo.getEditText().getText().toString());
                                    userInfo1.put("image","default");

                                    if(Type.equals("admin")){
                                        userInfo1.put("state",State.getEditText().getText().toString());
                                    }
                                    else if(Type.equals("state_admin")) {
                                        userInfo1.put("state","ghgj");
                                        userInfo1.put("district",District.getEditText().getText().toString());
                                    }
                                    else if(Type.equals("district_admin")){
                                        userInfo1.put("state","ghgj");
                                        userInfo1.put("district","dbhbdsh");
                                        userInfo1.put("lat",Lat.toString());
                                        userInfo1.put("long",Long.toString());

                                        userInfo1.put("authority",Authority.getEditText().getText().toString());
                                    }

                                    myRef.setValue(userInfo1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(AdminsRegistrationActivity.this, "register....info ...state",
                                                    Toast.LENGTH_SHORT).show();
                                            progressBar.dismiss();
                                        }
                                    });

                                    //Sign out registered user
                                    FirebaseAuth.getInstance().signOut();

                                    //Sign in curent user

                                    SharedPreferences sharedPreferences = getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
                                    Toast.makeText(AdminsRegistrationActivity.this,sharedPreferences.getString("email",null)+sharedPreferences.getString("password",null), Toast.LENGTH_SHORT).show();
                                    fun(sharedPreferences.getString("email",null),sharedPreferences.getString("password",null));


                                }
                            });




                        } else {
                            progressBar.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(AdminsRegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    private void setDistrictVisiblity() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        DatabaseReference refType = database.getReference().child("Management_Users").child(user.getUid());


        refType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Type = dataSnapshot.child("type").getValue().toString();
                Toast.makeText(AdminsRegistrationActivity.this, Type,
                        Toast.LENGTH_SHORT).show();
                if(Type.equals("admin")){
                    DistrictLayout.setVisibility(View.GONE);
                    AuthorityLayout.setVisibility(View.GONE);
                    StateLayout.setVisibility(View.VISIBLE);
                    progressBar.dismiss();
                }


                else if(Type.equals("state_admin")){
                    DistrictLayout.setVisibility(View.VISIBLE);
                    StateLayout.setVisibility(View.GONE);
                    AuthorityLayout.setVisibility(View.GONE);
                    progressBar.dismiss();
                }


                else if(Type.equals("district_admin")){//district_admin
                    DistrictLayout.setVisibility(View.GONE);
                    StateLayout.setVisibility(View.GONE);
                    RegionLayout.setVisibility(View.VISIBLE);
                    choose_location.setVisibility(View.VISIBLE);

                    progressBar.dismiss();
                }
                else if(Type.equals("region_admin")){//district_admin
                    DistrictLayout.setVisibility(View.GONE);
                    StateLayout.setVisibility(View.GONE);
                    AuthorityLayout.setVisibility(View.VISIBLE);
                    progressBar.dismiss();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void init() {
        Name = findViewById(R.id.name_txt_admin_reg);
        Email = findViewById(R.id.email_txt_admin_reg);
        Password = findViewById(R.id.password_txt_admin_reg);
        MobileNo = findViewById(R.id.mobile_no__txt_admin_reg);
        Gender = findViewById(R.id.gender_txt_admin_reg);
        State = findViewById(R.id.state__txt_admin_reg);
        District = findViewById(R.id.district__txt_admin_reg);
        Authority = findViewById(R.id.authority__txt_admin_reg);
        DistrictLayout = findViewById(R.id.district_layout_admin_reg);
        AuthorityLayout = findViewById(R.id.authority_layout_admin_reg);
        StateLayout = findViewById(R.id.state_layout_admin_reg);
        choose_location = findViewById(R.id.choose_location_layout);
        choose_location_textview = findViewById(R.id.choose_location_textview);
        RegionLayout = findViewById(R.id.region_layout_admin_reg);

    }

    private void fun(String email,String password){

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(AdminsRegistrationActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(AdminsRegistrationActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();


                        } else {
                            // If sign in fails, display a message to the user.
                            startActivity(new Intent(getBaseContext(),MainActivity.class));

                            Toast.makeText(AdminsRegistrationActivity.this, "failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                Lat = place.getLatLng().latitude;
                Long= place.getLatLng().longitude;


                choose_location_textview.setText(place.getAddress());
            }
        }
    }





}
