package dynamicdrillers.sih2018admins;

import android.app.Dialog;
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

public class StateAdminsActivity extends AppCompatActivity {


    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_list);

        recyclerView = findViewById(R.id.admins_recyclerView);
        recyclerView.setHasFixedSize(true);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

    }

    public void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("state_admin");


        FirebaseRecyclerOptions<AdminsModal> options =
                new FirebaseRecyclerOptions.Builder<AdminsModal>()
                        .setQuery(query, AdminsModal.class)
                        .build();

        FirebaseRecyclerAdapter<AdminsModal,AdminsViewHolder> adapter
                = new FirebaseRecyclerAdapter<AdminsModal,AdminsViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull AdminsViewHolder holder, int position, @NonNull final AdminsModal user) {
                final int pos = position;
                holder.setImage(user.getImage());
                holder.settitle(user.getState());

                holder.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        final Dialog dialog = new Dialog(StateAdminsActivity.this);
                        dialog.setContentView(R.layout.admin_list_action_layout);
                        dialog.setTitle("Choose  Action ");

                        final TextView Action1 = (TextView) dialog.findViewById(R.id.action1);
                        final TextView Action2 = (TextView) dialog.findViewById(R.id.action2);

                        dialog.show();


                       // Intent intent = new Intent(StateAdminsActivity.this,DistrictAdminsActivity.class);

                       // startActivity(intent);

                    }
                });

            }

            @Override
            public AdminsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_admin_layout, parent, false);

                return new AdminsViewHolder(mView);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }


    public class AdminsViewHolder extends RecyclerView.ViewHolder {
        View mView;


        public AdminsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void settitle(String title){
            TextView textView = mView.findViewById(R.id.txt_admin);
            textView.setText(title);
        }

        public void setImage(String image) {
            ImageView img =  mView.findViewById(R.id.image_admin);;
            Picasso.with(StateAdminsActivity.this).load(image).into(img);
        }



        public View getView(){
            return this.mView;
        }

    }
}
