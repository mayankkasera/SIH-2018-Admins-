package dynamicdrillers.sih2018admins;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

public class ComplaintsDesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String key;
    private String Name,Dis,Add,Vote,Share,time;
    private TextView NameTxt,TimeTxt,DisTxt,AddTxt,VoteTxt,ShareTxt;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_dis);



        recyclerView = findViewById(R.id.complaint_des_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        key = getIntent().getStringExtra("key");
        Name = getIntent().getStringExtra("name");
        time = getIntent().getStringExtra("time");
        Dis = getIntent().getStringExtra("description");
        Add = getIntent().getStringExtra("add");

        Toast.makeText(this, ""+key, Toast.LENGTH_SHORT).show();

        NameTxt = findViewById(R.id.name_com_txt);
        TimeTxt = findViewById(R.id.time_com_txt);
        DisTxt = findViewById(R.id.des_com_txt);
        AddTxt = findViewById(R.id.add_com_txt);
        VoteTxt = findViewById(R.id.vote_com_txt);
        ShareTxt = findViewById(R.id.share_com_txt);

        long l = Long.parseLong(time);
        String s = Time.getTimeAgo(l,this);
        Toast.makeText(this, s+" "+time, Toast.LENGTH_SHORT).show();
        NameTxt.setText(Name);
        TimeTxt.setText(Time.getTimeAgo(l,this));
        DisTxt.setText(Dis);
        AddTxt.setText(Add);


        DatabaseReference referenceVote = FirebaseDatabase.getInstance().getReference()
                .child("complaints").child(key);

        referenceVote.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Vote = dataSnapshot.child("complaint_votes").getValue().toString();
                Share = dataSnapshot.child("complaint_share").getValue().toString();
                VoteTxt.setText(dataSnapshot.child("complaint_votes").getValue()+ " Votes");
                ShareTxt.setText(Share+" Share");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        VoteTxt.setText(Vote+ " Votes");

        final ImageView VoteImg = findViewById(R.id.vote_img);


    }


    public void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("complaints").child(key).child("images");


        FirebaseRecyclerOptions<ComplaintDesModal> options =
                new FirebaseRecyclerOptions.Builder<ComplaintDesModal>()
                        .setQuery(query, ComplaintDesModal.class)
                        .build();

        FirebaseRecyclerAdapter<ComplaintDesModal,ComplaintDisViewHolder> adapter
                = new FirebaseRecyclerAdapter<ComplaintDesModal,ComplaintDisViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ComplaintDisViewHolder holder, final int position, @NonNull final ComplaintDesModal user) {
                final int pos = position;
                holder.setName(user.getType());
                holder.setCategiryImage(user.getImage());



            }

            @Override
            public ComplaintDisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.complaint_dis_layout, parent, false);

                return new ComplaintDisViewHolder(mView);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    public class ComplaintDisViewHolder extends RecyclerView.ViewHolder {
        View mView;


        public ComplaintDisViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String title){
            Button button = mView.findViewById(R.id.before_com_dis);
            button.setText(title);
        }


        public void setCategiryImage(String image) {
            ImageView img =  mView.findViewById(R.id.img_com_dis);;
            Picasso.with(ComplaintsDesActivity.this).load(image).into(img);
        }



        public View getView(){
            return this.mView;
        }

    }

}
