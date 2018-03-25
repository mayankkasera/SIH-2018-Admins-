package dynamicdrillers.sih2018admins;

import android.app.ProgressDialog;
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

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminsRegistrationActivity extends AppCompatActivity {


    private TextInputLayout Name,Email,Password,MobileNo,State,District,Authority,Region;
    private RadioGroup Gender;
    private LinearLayout DistrictLayout,AuthorityLayout,StateLayout,RegionLayout;
    private Button button;
    private String Gender_s="Male";
    private String id;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ProgressDialog progressBar;
    public static final String SharedprefenceName = "USER_DATA";
    LinearLayout choose_location;
    TextView choose_location_textview;
    String Type;
    SharedPreferences sharedPreferences ;
    int PLACE_PICKER_REQUEST = 1;
    Double Lat,Long;
    String RegionState=" ";
    String RegionDistrict = " ";
    Place Myplace;
    int Flag = 0;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_registration);
        sharedPreferences = getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        Type = SharedpreferenceHelper.getInstance(this).getType();
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("INITIALIZING ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();


        //Initialization of variables
        init();

        //setting Visiblity of Registration LAyout
        setFormAttributesVisiblity();



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


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = new ProgressDialog(view.getContext());
                progressBar.setMessage("Registering...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.show();

                if(Type.equals("district_admin")){
                    String dis = SharedpreferenceHelper.getInstance(AdminsRegistrationActivity.this).getDistrict();
                    if(RegionDistrict.equals(dis)){
                        registerAdmins();
                    }
                    else{
                        progressBar.dismiss();
                        Toast.makeText(AdminsRegistrationActivity.this, "select region from your district", Toast.LENGTH_SHORT).show();
                    }
                }
                else
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


                            final HashMap<String,String> TypeInfo = new HashMap<String, String>();
                            if(Type.equals("admin"))
                                TypeInfo.put("type","state_admin");
                            else if(Type.equals("state_admin"))
                                TypeInfo.put("type","district_admin");
                            else if(Type.equals("district_admin")){
                                TypeInfo.put("type","region_admin");
                            } else
                                TypeInfo.put("type","authority_admin");



                            myRef.setValue(TypeInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                    Toast.makeText(AdminsRegistrationActivity.this, "In Manager USer Added Succesfuly",
                                            Toast.LENGTH_SHORT).show();

                                    //setting person detail
                                    final FirebaseUser user = mAuth.getCurrentUser();
                                    HashMap<String,String> UserInfo = new HashMap<String, String>();
                                    UserInfo.put("name",Name.getEditText().getText().toString());
                                    UserInfo.put("password",Password.getEditText().getText().toString());
                                    UserInfo.put("email",Email.getEditText().getText().toString());
                                    UserInfo.put("gender",Gender_s);
                                    UserInfo.put("mobileno",MobileNo.getEditText().getText().toString());
                                    UserInfo.put("image","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR7hYNfHxOzH6TLbREidb_qhnNm_aGjsxsY-GjQVDJIEhc0A5ab");


                                    if(Type.equals("admin")) {
                                        myRef = database.getReference().child("state_admin").child(user.getUid());
                                        UserInfo.put("state",State.getEditText().getText().toString().toLowerCase());
                                    }
                                    else if(Type.equals("state_admin")) {
                                        myRef = database.getReference().child("district_admin").child(user.getUid());
                                        UserInfo.put("state",sharedPreferences.getString("state",null).toLowerCase());
                                        UserInfo.put("district",District.getEditText().getText().toString().toLowerCase());

                                    }
                                    else if(Type.equals("district_admin")) {
                                        myRef = database.getReference().child("region_admin").child(user.getUid());
                                        UserInfo.put("state",RegionState.toLowerCase());
                                        UserInfo.put("district",RegionDistrict.toLowerCase());

                                        UserInfo.put("lat",Lat.toString());
                                        UserInfo.put("long",Long.toString());
                                        UserInfo.put("region",Region.getEditText().getText().toString().toLowerCase());
                                    }
                                    else if(Type.equals("region_admin")){
                                            myRef = database.getReference().child("authority_admin").child(user.getUid());
                                            UserInfo.put("name",Authority.getEditText().getText().toString().toLowerCase());
                                            UserInfo.put("district",SharedpreferenceHelper.getInstance(AdminsRegistrationActivity.this).getDistrict());
                                            UserInfo.put("state",SharedpreferenceHelper.getInstance(AdminsRegistrationActivity.this).getState());
                                            UserInfo.put("region",SharedpreferenceHelper.getInstance(AdminsRegistrationActivity.this).getRegion());


                                    }


                                    myRef.setValue(UserInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(AdminsRegistrationActivity.this, "Registration SuccessFuly..",
                                                    Toast.LENGTH_SHORT).show();

                                            if(Type.equals("district_admin"))
                                            {
                                                Toast.makeText(AdminsRegistrationActivity.this, RegionDistrict+""+RegionState, Toast.LENGTH_SHORT).show();

                                                DatabaseReference myRef1 = database.getReference().child("region_places").child(RegionState.toLowerCase()).child(RegionDistrict.toLowerCase());
                                                GeoFire geoFire = new GeoFire(myRef1);
                                                geoFire.setLocation(user.getUid(),new GeoLocation(Lat,Long));


                                                RegionState = " ";
                                                RegionDistrict = " ";
                                            }

                                            progressBar.dismiss();
                                        }
                                    });

                                    //Sign out registered user
                                    FirebaseAuth.getInstance().signOut();

                                    //Sign in curent user

                                    Toast.makeText(AdminsRegistrationActivity.this,sharedPreferences.getString("email",null)+sharedPreferences.getString("password",null), Toast.LENGTH_SHORT).show();
                                    Login(sharedPreferences.getString("email",null),sharedPreferences.getString("password",null));


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

    private void setFormAttributesVisiblity() {

                if(Type.equals("admin")){
                    DistrictLayout.setVisibility(View.GONE);
                    AuthorityLayout.setVisibility(View.GONE);
                    StateLayout.setVisibility(View.VISIBLE);
                    button.setEnabled(true);
                    progressBar.dismiss();
                }


                else if(Type.equals("state_admin")){
                    DistrictLayout.setVisibility(View.VISIBLE);
                    StateLayout.setVisibility(View.GONE);
                    AuthorityLayout.setVisibility(View.GONE);
                    button.setEnabled(true);
                    progressBar.dismiss();
                }


                else if(Type.equals("district_admin")){//district_admin
                    DistrictLayout.setVisibility(View.GONE);
                    StateLayout.setVisibility(View.GONE);
                    RegionLayout.setVisibility(View.VISIBLE);
                    choose_location.setVisibility(View.VISIBLE);

                    progressBar.dismiss();
                } else if(Type.equals("region_admin")){//district_admin
                    DistrictLayout.setVisibility(View.GONE);
                    StateLayout.setVisibility(View.GONE);
                    AuthorityLayout.setVisibility(View.VISIBLE);
                    Authority.setVisibility(View.VISIBLE);
                    button.setEnabled(true);
                    progressBar.dismiss();
                }





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
        button = findViewById(R.id.register_btn_admin_reg);
        button.setEnabled(false);
        Region = findViewById(R.id.region__txt_admin_reg);
        RegionLayout = findViewById(R.id.region_layout_admin_reg);

    }

    private void Login(String email, String password){

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

        RegionState = "";
        RegionDistrict = "";
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Myplace = PlacePicker.getPlace(data, this);

                Lat = Myplace.getLatLng().latitude;
                Long = Myplace.getLatLng().longitude;

                if (Myplace.getAddress() != null) {
                    String address[] = Myplace.getAddress().toString().split(",");


                    if (address.length <= 3) {
                        Toast.makeText(AdminsRegistrationActivity.this, "select exact location", Toast.LENGTH_SHORT).show();

                    } else {
                        String state  = address[address.length - 2].replaceAll("\\d","");

                        String dis_s[] = address[address.length-3].split(" ");
                        for (int j = 0; j < dis_s.length; j++) {

                            RegionDistrict = RegionDistrict + dis_s[j];


                        }

                        String st_s[] = state.split(" ");

                        for (int j = 0; j < st_s.length; j++) {

                            RegionState = RegionState + st_s[j];


                        }

                         RegionDistrict =  RegionDistrict.trim().toLowerCase();
                        RegionState = RegionState.trim().toLowerCase();
                        Toast.makeText(this, RegionState +"  " + RegionDistrict, Toast.LENGTH_SHORT).show();


                        button.setEnabled(true);


                    }

                    choose_location_textview.setText(Myplace.getAddress());
                }
            }
        }


    }


}
