package dynamicdrillers.sih2018admins;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class ComplaintsDesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String key,AuthorityKey;
    private String Name,Dis,Add,Vote,Share,time;
    private TextView NameTxt,TimeTxt,DisTxt,AddTxt,VoteTxt,ShareTxt;
    private String Authority;
    private Toolbar toolbar;
    private ProgressDialog progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_dis);

        toolbar = findViewById(R.id.forward_toolbar);
        forward();







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


    void forward(){
        String Type = SharedpreferenceHelper.getInstance(this).getType();

        if(Type.equals("region_admin")){
            ImageView imageView = findViewById(R.id.forword);

            imageView.setVisibility(View.VISIBLE);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(ComplaintsDesActivity.this);
                    dialog.setContentView(R.layout.foeward_dialog_layout);
                    dialog.setTitle("Choose  Authority ");

                    String reg = SharedpreferenceHelper.getInstance(getBaseContext()).getRegion();

                    final Spinner AuthoritySpn = (Spinner) dialog.findViewById(R.id.autority_spn);
                    Button Forward = (Button)dialog.findViewById(R.id.forward_btn);

                    Forward.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressBar = new ProgressDialog(ComplaintsDesActivity.this);
                            progressBar.setMessage("INITIALIZING ...");
                            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressBar.show();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("complaints").child(key);
                            reference.child("complaint_forwardto").setValue(AuthorityKey);
                            startActivity(new Intent(ComplaintsDesActivity.this,DashboardActivity.class));
                            progressBar.dismiss();
                        }
                    });



                    final Query database = FirebaseDatabase.getInstance().getReference().child("authority_admin").orderByChild("region").equalTo(reg);

                    database.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            final List<String> areas = new ArrayList<String>();


                            for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                String areaName = areaSnapshot.child("authority").getValue(String.class);
                                String authkey = areaSnapshot.getKey();

                                areas.add(areaName.toUpperCase());
                            }


                            ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(ComplaintsDesActivity.this, android.R.layout.simple_spinner_item, areas);
                            areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            AuthoritySpn.setAdapter(areasAdapter);



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                    AuthoritySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();

                            // Showing selected spinner item
                            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                            Authority = item;

                            final Query database = FirebaseDatabase.getInstance().getReference().child("authority_admin").orderByChild("authority").equalTo(Authority.toLowerCase());
                            database.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot data  : dataSnapshot.getChildren()){

                                        Toast.makeText(ComplaintsDesActivity.this, data.getKey()
                                                , Toast.LENGTH_SHORT).show();

                                            AuthorityKey = data.getKey();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    dialog.show();
                }


            });



        }

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
