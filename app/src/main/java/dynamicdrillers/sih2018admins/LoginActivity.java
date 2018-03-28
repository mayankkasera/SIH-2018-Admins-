package dynamicdrillers.sih2018admins;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email,password;
    private Button login,reset_password;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDataRoot = FirebaseDatabase.getInstance().getReference();
    private String UserType;
    private String mUserUid;
    private SpotsDialog spotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotsDialog.show();
                String Email = email.getText().toString();
                String Pasaword = password.getText().toString();
                userLogin(Email,Pasaword);

            }
        });

    }

    private  void init(){
        email = (TextInputEditText)findViewById(R.id.email_login_txtinput);
        password = (TextInputEditText)findViewById(R.id.password_login_txtinput);
        login = (Button)findViewById(R.id.login_login_btn);
        reset_password = (Button)findViewById(R.id.resetpassword_login_ntn);
        spotsDialog  = new SpotsDialog(this);
    }


    private void userLogin(String email, String pasaword) {



        mAuth.signInWithEmailAndPassword(email,pasaword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                     mUserUid = mAuth.getCurrentUser().getUid().toString();


                   Toast.makeText(LoginActivity.this,FirebaseAuth.getInstance().getCurrentUser().getUid().toString(),Toast.LENGTH_LONG).show();


                    mDataRoot.child("Management_Users").child(mUserUid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                             UserType = dataSnapshot.child("type").getValue().toString();

                             FirebaseDatabase.getInstance().getReference().child(UserType).child(mUserUid).child("token").setValue(FirebaseInstanceId.getInstance().getToken());




                            //getting User Details By Its Type

                            //Toast.makeText(LoginActivity.this, UserType, Toast.LENGTH_SHORT).show();
                            setUserDetails(UserType);

                            if(UserType.equals("authority_admin")){
                                spotsDialog.dismiss();
                                startActivity(new Intent(LoginActivity.this,AuthorityDashboardActivity.class));
                            }
                            else {
                                spotsDialog.dismiss();
                                Intent i = new Intent(LoginActivity.this,DashboardActivity.class);
                                i.putExtra("type",UserType);
                                startActivity(i);
                            }


                            finish();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            spotsDialog.dismiss();
                            Toast.makeText(getApplicationContext(),databaseError.getMessage().toString(),Toast.LENGTH_SHORT).show();


                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this,task.getException().getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void setUserDetails(String userType) {


        if(userType.equals("admin"))
        {
            //getting user details and setting to sharedPrefences
            mDataRoot.child("admin").child(mUserUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String adminName =  dataSnapshot.child("name").getValue().toString();
                    String adminMobileno = dataSnapshot.child("mobileno").getValue().toString();
                    String adminGender = dataSnapshot.child("gender").getValue().toString();
                    String adminImage = dataSnapshot.child("image").getValue().toString();
                    String adminPassword = password.getText().toString();
                    String adminEmail = mAuth.getCurrentUser().getEmail().toString();

                    SharedpreferenceHelper.getInstance(LoginActivity.this).
                            setAdminData(adminName,adminImage,adminMobileno,adminGender,
                                    "admin",adminPassword,adminEmail);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(getApplicationContext(),databaseError.getMessage().toString(),Toast.LENGTH_SHORT).show();

                }
            });

        }
        else  if(userType.equals("state_admin"))
        {
            //getting user details and setting to sharedPrefences
            mDataRoot.child("state_admin").child(mUserUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String stateAdminName =  dataSnapshot.child("name").getValue().toString();
                    String stateAdminMobileno = dataSnapshot.child("mobileno").getValue().toString();
                    String stateAdminGender = dataSnapshot.child("gender").getValue().toString();
                    String stateAdminImage = dataSnapshot.child("image").getValue().toString();
                    String stateAdminStateName = dataSnapshot.child("state").getValue().toString();
                    String stateAdminPassword = password.getText().toString();
                    String stateEmail = mAuth.getCurrentUser().getEmail().toString();


                    SharedpreferenceHelper.getInstance(LoginActivity.this)
                            .setStateAdminData(stateAdminName,stateAdminImage,stateAdminMobileno,stateAdminGender,
                                    "state_admin",stateAdminStateName,stateAdminPassword,stateEmail);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(getApplicationContext(),databaseError.getMessage().toString(),Toast.LENGTH_SHORT).show();

                }
            });
        }
        else if(userType.equals("district_admin"))
        {
            //getting user details and setting to sharedPrefences
            mDataRoot.child("district_admin").child(mUserUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String districtAdminName =  dataSnapshot.child("name").getValue().toString();
                    String districtAdminMobileno = dataSnapshot.child("mobileno").getValue().toString();
                    String districtAdminGender = dataSnapshot.child("gender").getValue().toString();
                    String districtAdminImage = dataSnapshot.child("image").getValue().toString();
                    String districtAdminStateName = dataSnapshot.child("state").getValue().toString();
                    String districtAdminDistrictName = dataSnapshot.child("district").getValue().toString();
                    String districtAdminPassword = password.getText().toString();
                    String districtEmail = mAuth.getCurrentUser().getEmail().toString();


                    SharedpreferenceHelper.getInstance(LoginActivity.this)
                            .setDistrictAdminData(districtAdminName,districtAdminImage,districtAdminMobileno,districtAdminGender
                                    ,"district_admin",districtAdminStateName,districtAdminDistrictName
                                    ,districtAdminPassword,districtEmail);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(getApplicationContext(),databaseError.getMessage().toString(),Toast.LENGTH_SHORT).show();

                }
            });

        }
        else  if(userType.equals("region_admin"))
        {
            //getting user details and setting to sharedPrefences
            mDataRoot.child("region_admin").child(mUserUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String subRegionAdminName =  dataSnapshot.child("name").getValue().toString();
                    String subRegionAdminMobileno = dataSnapshot.child("mobileno").getValue().toString();
                    String subRegionAdminGender = dataSnapshot.child("gender").getValue().toString();
                    String subRegionAdminImage = dataSnapshot.child("image").getValue().toString();
                    String subRegionAdminStateName = dataSnapshot.child("state").getValue().toString();
                    String subRegionAdminDistrictName = dataSnapshot.child("district").getValue().toString();
                    String subRegionAdminRegionName  = dataSnapshot.child("region").getValue().toString();
                    String subRegionAdminPassword = password.getText().toString();
                    String subRegionAdminEmail = mAuth.getCurrentUser().getEmail().toString();


                    SharedpreferenceHelper.getInstance(LoginActivity.this).setRegionAdminData(subRegionAdminName,subRegionAdminImage,subRegionAdminMobileno
                            ,subRegionAdminGender,"region_admin"
                            ,subRegionAdminStateName,subRegionAdminDistrictName,subRegionAdminRegionName,subRegionAdminPassword,subRegionAdminEmail);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(getApplicationContext(),databaseError.getMessage().toString(),Toast.LENGTH_SHORT).show();

                }
            });
        }
        else if(userType.equals("authority_admin"))
        {
            //getting user details and setting to sharedPrefences
            mDataRoot.child("authority_admin").child(mUserUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//
                    String localAuthorityName =  dataSnapshot.child("name").getValue().toString();
                    String localAuthorityMobileno = dataSnapshot.child("mobileno").getValue().toString();
                    String localAuthorityImage = dataSnapshot.child("image").getValue().toString();
                    String localAuthorityStateName = dataSnapshot.child("state").getValue().toString();
                    String localAuthorityDistrictName = dataSnapshot.child("district").getValue().toString();
                    String localAuthoritySubRegionName  = dataSnapshot.child("name").getValue().toString();
                    String localAuthoritySubRegionPassword = password.getText().toString();
                    String localAuthoritySubRegionAuthorityName = dataSnapshot.child("name").getValue().toString();
                    String localAuthorityEmail = mAuth.getCurrentUser().getEmail().toString();

                    Toast.makeText(LoginActivity.this, "yes", Toast.LENGTH_SHORT).show();
                    SharedpreferenceHelper.getInstance(LoginActivity.this).setAuthorityAdminData(localAuthorityName,localAuthorityImage,localAuthorityMobileno
                            ,"authority_admin",localAuthorityStateName
                            ,localAuthorityDistrictName,localAuthoritySubRegionName,localAuthoritySubRegionPassword,localAuthorityEmail,localAuthoritySubRegionAuthorityName);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(getApplicationContext(),databaseError.getMessage().toString(),Toast.LENGTH_SHORT).show();

                }
            });
        }


    }
}
