package dynamicdrillers.sih2018admins;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class DistrictAdminsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_admins);

        recyclerView = findViewById(R.id.district_recyclerView);
        recyclerView.setHasFixedSize(true);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

    }

    public void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("district_admin");


        FirebaseRecyclerOptions<DistrictModal> options =
                new FirebaseRecyclerOptions.Builder<DistrictModal>()
                        .setQuery(query, DistrictModal.class)
                        .build();

        FirebaseRecyclerAdapter<DistrictModal,DistrictAdminsViewHolder> adapter
                = new FirebaseRecyclerAdapter<DistrictModal,DistrictAdminsViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull DistrictAdminsViewHolder holder, int position, @NonNull DistrictModal user) {
                final int pos = position;
                holder.setImage(user.getImage());
                holder.settitle(user.getDistrict());

                holder.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }

            @Override
            public DistrictAdminsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_admin_layout, parent, false);

                return new DistrictAdminsViewHolder(mView);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    public class DistrictAdminsViewHolder extends RecyclerView.ViewHolder {
        View mView;


        public DistrictAdminsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void settitle(String title){
            TextView textView = mView.findViewById(R.id.txt_admin);
            textView.setText(title);
        }

        public void setImage(String image) {
            ImageView img =  mView.findViewById(R.id.image_admin);;
            Picasso.with(DistrictAdminsActivity.this).load(image).into(img);
        }



        public View getView(){
            return this.mView;
        }

    }
}
