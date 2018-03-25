package dynamicdrillers.sih2018admins;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class StateAdminsActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    EditText mserachtext;
    String searching_text ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_list);

        mserachtext = findViewById(R.id.search_admin_txt);

        recyclerView = findViewById(R.id.admins_recyclerView);
        recyclerView.setHasFixedSize(true);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mserachtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                searching_text = s.toString();
                onStart();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searching_text = s.toString();
                onStart();
            }

            @Override
            public void afterTextChanged(Editable s) {
                searching_text = s.toString();
                onStart();
            }
        });

    }

    public void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("state_admin").orderByChild("state").startAt(searching_text.toLowerCase())
                .endAt(searching_text.toLowerCase() + "\uf8ff");


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
                holder.settitle(user.getState().toUpperCase());
                holder.setAdminName( "Admin :" +user.getName());

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

        public  void setAdminName(String title)
        {
            TextView textView = mView.findViewById(R.id.txt_admin_name);
            textView.setText(title);
        }



        public View getView(){
            return this.mView;
        }

    }
}
