package dynamicdrillers.sih2018admins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    Spinner StateSpinner,DistrictSpinner,RegionSpn;
    String State,District,Region;
    LinearLayout StateLayout,DistrictLayout,RegionLayout;
    Button view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        StateSpinner = (Spinner) findViewById(R.id.state_spn);
        DistrictSpinner = (Spinner)findViewById(R.id.district_spn);
        RegionSpn = (Spinner)findViewById(R.id.region_spn);

        StateLayout = findViewById(R.id.state_layout);
        DistrictLayout = findViewById(R.id.district_layout);
        RegionLayout = findViewById(R.id.region_layout);

        view = findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra("type").equals("state")) {
                    Intent intent = new Intent(getBaseContext(), ComplaintsActivity.class);
                    intent.putExtra("type", "complainer_state");
                    intent.putExtra("data", State);
                    startActivity(intent);
                }
                else if(getIntent().getStringExtra("type").equals("district")){
                    Intent intent = new Intent(getBaseContext(), ComplaintsActivity.class);
                    intent.putExtra("type", "complaint_district");
                    intent.putExtra("data", District);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getBaseContext(), ComplaintsActivity.class);
                    intent.putExtra("type", "complainer_region");
                    intent.putExtra("data", Region);
                    startActivity(intent);
                }
            }
        });

        if(getIntent().getStringExtra("type").equals("state")){
            StateLayout.setVisibility(View.VISIBLE);
            DistrictLayout.setVisibility(View.GONE);
            RegionLayout.setVisibility(View.GONE);
        }
        else if(getIntent().getStringExtra("type").equals("district")){
            StateLayout.setVisibility(View.VISIBLE);
            DistrictLayout.setVisibility(View.VISIBLE);
            RegionLayout.setVisibility(View.GONE);
        }
        else {
            StateLayout.setVisibility(View.VISIBLE);
            DistrictLayout.setVisibility(View.VISIBLE);
            RegionLayout.setVisibility(View.VISIBLE);
        }
        StateSpn();


    }

    void StateSpn(){
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("state_admin");

        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("state").getValue(String.class);
                    areas.add(areaName.toUpperCase());
                }


                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(FilterActivity.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                StateSpinner.setAdapter(areasAdapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        StateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                State = item;
                DistrictSpn();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    void DistrictSpn(){

        final Query database = FirebaseDatabase.getInstance().getReference().child("district_admin").orderByChild("state").equalTo(State.toLowerCase());

        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("district").getValue(String.class);
                    areas.add(areaName.toUpperCase());
                }


                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(FilterActivity.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                DistrictSpinner.setAdapter(areasAdapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        DistrictSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                District = item;
                RegionSpn();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void RegionSpn(){

        final Query database = FirebaseDatabase.getInstance().getReference().child("region_admin").orderByChild("district").equalTo(District.toLowerCase());

        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("region").getValue(String.class);
                    areas.add(areaName.toUpperCase());
                }


                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(FilterActivity.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                RegionSpn.setAdapter(areasAdapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        RegionSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                Region = item;



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
