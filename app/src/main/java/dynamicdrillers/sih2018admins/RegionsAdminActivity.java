package dynamicdrillers.sih2018admins;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class RegionsAdminActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public static final String SharedprefenceName = "USER_DATA";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String district;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regions_admin);

        recyclerView = findViewById(R.id.regions_recyclerView);
        recyclerView.setHasFixedSize(true);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);



        Toast.makeText(RegionsAdminActivity.this, district, Toast.LENGTH_SHORT).show();


    }

    public void onStart() {
        super.onStart();


        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("region_admin");


        FirebaseRecyclerOptions<RegionModal> options =
                new FirebaseRecyclerOptions.Builder<RegionModal>()
                        .setQuery(query, RegionModal.class)
                        .build();

        FirebaseRecyclerAdapter<RegionModal,RegionAdminsViewHolder> adapter
                = new FirebaseRecyclerAdapter<RegionModal,RegionAdminsViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull RegionAdminsViewHolder holder, int position, @NonNull RegionModal user) {
                final int pos = position;
                holder.setImage(user.getImage());
                holder.settitle(user.getRegion());

                holder.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }

            @Override
            public RegionAdminsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_admin_layout, parent, false);

                return new RegionAdminsViewHolder(mView);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    public class RegionAdminsViewHolder extends RecyclerView.ViewHolder {
        View mView;


        public RegionAdminsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void settitle(String title){
            TextView textView = mView.findViewById(R.id.txt_admin);
            textView.setText(title);
        }

        public void setImage(String image) {
            ImageView img =  mView.findViewById(R.id.image_admin);;
            Picasso.with(RegionsAdminActivity.this).load(image).into(img);
        }



        public View getView(){
            return this.mView;
        }

    }


}
