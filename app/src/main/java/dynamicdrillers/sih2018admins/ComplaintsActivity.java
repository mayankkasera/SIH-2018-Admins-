package dynamicdrillers.sih2018admins;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ComplaintsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String UserId="123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        recyclerView = findViewById(R.id.complaints_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void onStart() {
        super.onStart();

        Toast.makeText(this, getIntent().getStringExtra("type")+
                " "+getIntent().getStringExtra("data"), Toast.LENGTH_SHORT).show();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("complaints").orderByChild(getIntent().getStringExtra("type")).equalTo(getIntent().getStringExtra("data").toLowerCase());

        FirebaseRecyclerOptions<ComplaintModal> options =
                new FirebaseRecyclerOptions.Builder<ComplaintModal>()
                        .setQuery(query, ComplaintModal.class)
                        .build();

        FirebaseRecyclerAdapter<ComplaintModal,ComplaintViewHolder> adapter
                = new FirebaseRecyclerAdapter<ComplaintModal,ComplaintViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ComplaintViewHolder holder, final int position, @NonNull final ComplaintModal user) {
                final int pos = position;
                holder.setName("Name");
                holder.setStatus(user.getComplaint_status());
                holder.setTime(user.getComplaint_request_time());
                holder.setDes(user.getComplaint_description());
                holder.setAdd(user.getComplaint_full_address());
                holder.setShare(user.getComplaint_share());
                holder.setVote(user.getComplaint_votes());
                holder.setImage(getRef(position).getKey());
                final LinearLayout VoteLayout = holder.getView().findViewById(R.id.vote_layout);
                final LinearLayout ShareLayout = holder.getView().findViewById(R.id.vote_layout);
                final ImageView VoteImg = holder.getView().findViewById(R.id.vote_img);



                holder.getView().findViewById(R.id.com_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ComplaintsActivity.this,ComplaintsDesActivity.class);
                        intent.putExtra("key",getRef(position).getKey());
                        intent.putExtra("name","name");
                        intent.putExtra("time",user.getComplaint_request_time());
                        intent.putExtra("description",user.getComplaint_description());
                        intent.putExtra("add",user.getComplaint_full_address());
                        intent.putExtra("vote",user.getComplaint_votes());
                        intent.putExtra("share",user.getComplaint_share());
                        startActivity(intent);
                    }
                });





            }

            @Override
            public ComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_complaint_layout, parent, false);

                return new ComplaintViewHolder(mView);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }


    public class ComplaintViewHolder extends RecyclerView.ViewHolder {
        View mView;


        public ComplaintViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String title){
            TextView textView = mView.findViewById(R.id.name_com_txt);
            textView.setText(title);
        }

        public void setStatus(String title){
            TextView textView = mView.findViewById(R.id.status_com_txt);
            textView.setText(title);
        }


        public void setCategiryImage(String image) {
            ImageView img =  mView.findViewById(R.id.categiry_com_img);;
            Picasso.with(ComplaintsActivity.this).load(image).into(img);
        }

        public void setTime(String title){

            long l = Long.parseLong(title);

            String s = Time.getTimeAgo(l,ComplaintsActivity.this);
            TextView textView = mView.findViewById(R.id.time_com_txt);
            textView.setText(s);
        }

        public void setDes(String title){
            TextView textView = mView.findViewById(R.id.des_com_txt);
            textView.setText(title);
        }

        public void setAdd(String title){
            TextView textView = mView.findViewById(R.id.add_com_txt);
            textView.setText(title);
        }

        public void setVote(String title){
            TextView textView = mView.findViewById(R.id.vote_com_txt);
            textView.setText(title+ " Votes");
        }

        public void setShare(String title){
            TextView textView = mView.findViewById(R.id.share_com_txt);
            textView.setText(title+" Share");
        }

        public void setImage(String id){
            final ImageView imageView = mView.findViewById(R.id.vote_img);
            final DatabaseReference referenceVote = FirebaseDatabase.getInstance().getReference()
                    .child("vote").child(id);

            referenceVote.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(UserId)){
                        if(dataSnapshot.child(UserId).getValue().equals("true")){
                            imageView.setImageResource(R.drawable.like);
                        }
                        else {
                            imageView.setImageResource(R.drawable.unlike);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }



        public View getView(){
            return this.mView;
        }

    }


}
