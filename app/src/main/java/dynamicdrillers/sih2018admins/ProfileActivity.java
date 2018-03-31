package dynamicdrillers.sih2018admins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profile;
    TextView Name,Email,Mobile,Gender,ChangePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile = findViewById(R.id.admin_profile);
        Name = findViewById(R.id.admin_name);
        Email = findViewById(R.id.admin_email);
        Mobile  =findViewById(R.id.admin_mobile);
        Gender = findViewById(R.id.admin_gender);


        String Admin_id = getIntent().getStringExtra("admin_id");
        String Admin_type = getIntent().getStringExtra("admin_type");


        //Toast.makeText(this, Admin_id, Toast.LENGTH_SHORT).show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        if(Admin_type.equals("state_admin"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("state_admin");
        } else  if (Admin_type.equals("region_admin"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("region_admin");

        }else  if (Admin_type.equals("district_admin"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("district_admin");

        }
        else  if (Admin_type.equals("authority_admin"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("authority_admin");

        }
        else
        {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("admin");

        }


        databaseReference.child(Admin_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Name.setText(dataSnapshot.child("name").getValue().toString());
                Email.setText(dataSnapshot.child("email").getValue().toString());
                Gender.setText(dataSnapshot.child("gender").getValue().toString());
                Mobile.setText(dataSnapshot.child("mobileno").getValue().toString());
                Picasso.with(getBaseContext()).load(dataSnapshot.child("image").getValue().toString()).into(profile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
