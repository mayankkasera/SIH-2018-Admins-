package dynamicdrillers.sih2018admins;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplaintsDesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String key,AuthorityKey;
    private String Name,Dis,Add,Vote,Share,time,Status;
    private TextView NameTxt,TimeTxt,DisTxt,AddTxt,VoteTxt,ShareTxt,StatusTxt;
    private String Authority,UserIdToken,UserMobileNo;
    private Toolbar toolbar;
    private ProgressDialog progressBar;
    private StorageReference mStorage;
    private static final int INTENT_REQUEST_GET_IMAGES = 13;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_dis);

        toolbar = findViewById(R.id.forward_toolbar);
        forward();
        mStorage = FirebaseStorage.getInstance().getReference();


        recyclerView = findViewById(R.id.complaint_des_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        key = getIntent().getStringExtra("key");
        Name = getIntent().getStringExtra("name");
        time = getIntent().getStringExtra("time");
        Dis = getIntent().getStringExtra("description");
        Add = getIntent().getStringExtra("add");
        Status = getIntent().getStringExtra("status");

        Toast.makeText(this, ""+key, Toast.LENGTH_SHORT).show();

        NameTxt = findViewById(R.id.name_com_txt);
        TimeTxt = findViewById(R.id.time_com_txt);
        DisTxt = findViewById(R.id.des_com_txt);
        AddTxt = findViewById(R.id.add_com_txt);
        VoteTxt = findViewById(R.id.vote_com_txt);
        ShareTxt = findViewById(R.id.share_com_txt);
        StatusTxt = findViewById(R.id.status_com_txt);

        long l = Long.parseLong(time);
        String s = Time.getTimeAgo(l,this);
        Toast.makeText(this, s+" "+time, Toast.LENGTH_SHORT).show();
        NameTxt.setText(key);
        TimeTxt.setText(Time.getTimeAgo(l,this));
        DisTxt.setText(Dis);
        AddTxt.setText(Add);
        StatusTxt.setText(Status);


        StatusTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(SharedpreferenceHelper.getInstance(getBaseContext()).getType().equals("authority_admin")){
                    if(!StatusTxt.getText().equals("Resolved")){
                        if(!StatusTxt.getText().equals("Reject"))
                            checkPermissions();
                    }
                    else{
                        FirebaseDatabase.getInstance().getReference().child("complaints").child(key)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.child("complaint_resolved_by").getValue().toString().equals("admin")){
                                            FirebaseDatabase.getInstance().getReference().child("authority_admin")
                                                    .child(dataSnapshot.child("complaint_forwardto").getValue().toString()).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Toast.makeText(ComplaintsDesActivity.this, " Complaint Resolved by "+
                                                            dataSnapshot.child("authority").getValue().toString(), Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                        else{
                                            Toast.makeText(ComplaintsDesActivity.this, " Complaint Resolved by User"
                                                    , Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                    }

                }

                if(SharedpreferenceHelper.getInstance(getBaseContext()).getType().equals("region_admin")){
                    FirebaseDatabase.getInstance().getReference().child("complaints").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("complaint_status").getValue().toString().equals("Reject")){
                                Toast.makeText(ComplaintsDesActivity.this,
                                        "Compalint Alredy Rejected"+dataSnapshot.child("Reject Reason").getValue().toString() ,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if(dataSnapshot.child("complaint_status").getValue().toString().equals("Resolved"))
                            {
                                 if(dataSnapshot.child("complaint_resolved_by").getValue().toString().equals("admin")){
                                     FirebaseDatabase.getInstance().getReference().child("authority_admin")
                                             .child(dataSnapshot.child("complaint_forwardto").getValue().toString()).addValueEventListener(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(DataSnapshot dataSnapshot) {
                                             Toast.makeText(ComplaintsDesActivity.this, " Complaint Resolved by "+
                                                     dataSnapshot.child("authority").getValue().toString(), Toast.LENGTH_SHORT).show();
                                         }

                                         @Override
                                         public void onCancelled(DatabaseError databaseError) {

                                         }
                                     });
                                 }
                                 else{
                                     Toast.makeText(ComplaintsDesActivity.this, " Complaint Resolved by User"
                                                    , Toast.LENGTH_SHORT).show();
                                 }

                            }

                            else{
                                regectComplaint();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

        DatabaseReference referenceVote = FirebaseDatabase.getInstance().getReference()
                .child("complaints").child(key);

        referenceVote.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Vote = dataSnapshot.child("complaint_votes").getValue().toString();
                Share = dataSnapshot.child("complaint_share").getValue().toString();
                VoteTxt.setText(dataSnapshot.child("complaint_votes").getValue()+ " Votes");
                ShareTxt.setText(Share+" Share");
                StatusTxt.setText(dataSnapshot.child("complaint_status").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        VoteTxt.setText(Vote+ " Votes");

        final ImageView VoteImg = findViewById(R.id.vote_img);


    }

    private void regectComplaint() {
        final Dialog dialog = new Dialog(ComplaintsDesActivity.this);
        dialog.setContentView(R.layout.reject_complaint_dialog_layout);
        dialog.setTitle("Reject Complaint ");

        final EditText editText = (EditText)dialog.findViewById(R.id.reject_edt);
        Button button = (Button)dialog.findViewById(R.id.reject_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String[] mobileno = new String[1];
                final String[] token = new String[1];

                FirebaseDatabase.getInstance().getReference().child("complaints").child(key)
                        .child("complaint_status").setValue("Reject");
                FirebaseDatabase.getInstance().getReference().child("complaints").child(key)
                        .child("Reject Reason").setValue(editText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("complaints").child(key)
                        .child("complaint_reject_time").setValue(ServerValue.TIMESTAMP);
                FirebaseDatabase.getInstance().getReference().child("complaints").child(key)
                        .child("complaint_reject_by").setValue("region_admin");

                FirebaseDatabase.getInstance().getReference().child("complaints").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        FirebaseDatabase.getInstance().getReference().child("Users").child(dataSnapshot.child("complainer_id").getValue().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                UserMobileNo = dataSnapshot.child("mobile").getValue().toString();
                                UserIdToken = dataSnapshot.child("token").getValue().toString();
//
//                                final DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference().child("notification").child(dataSnapshot.getKey().toString());
//
//                                final String T  = mRoot.push().getKey();
//                                HashMap<String,String> param = new HashMap<>();
//                                param.put("title","Complaint "+ key +"  rejected");
//                                param.put("description","Reason : "+editText.getText().toString());
//                                param.put("status","true");
//                                param.put("time", String.valueOf(12345));
//
//
//
//                                mRoot.child(T).setValue(param).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//
//                                        mRoot.child(T).child("time").child("t").setValue(ServerValue.TIMESTAMP);
//                                        mRoot.child(T).child("time").addListenerForSingleValueEvent(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                                Long time =  Long.parseLong(dataSnapshot.child("t").getValue().toString());
//                                                mRoot.child(T).child("time").setValue(time.toString());
//                                            }
//
//                                            @Override
//                                            public void onCancelled(DatabaseError databaseError) {
//
//                                            }
//                                        });
//
//
//                                        Toast.makeText(ComplaintsDesActivity.this, "notification rcyler sent", Toast.LENGTH_SHORT).show();
//                                    }
//                                });


                                sendSmsToPhone(UserMobileNo,key,"reject",editText.getText().toString());
                                sendPushNotification(UserIdToken,"Complaint Rejected",editText.getText().toString());

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





                //startActivity(new Intent(ComplaintsDesActivity.this,DashboardActivity.class));
                Toast.makeText(ComplaintsDesActivity.this, "Complaint Rejected", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show() ;


    }

    public void sendPushNotification(final String TokenUID , final String Type, final String Message) {

        //Toast.makeText(this, TokenUID +"\n\n" +ComplaintCatagory + Address, Toast.LENGTH_SHORT).show();




        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, "http://happystore.16mb.com/sihapi/SendNotificationByOneSignal.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(ComplaintsDesActivity.this, "Notification send" + response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ComplaintsDesActivity.this, ""+ error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                final HashMap<String,String> param = new HashMap<>();
                param.put("receivertoken",TokenUID);
                param.put("description",Message);
                param.put("title",Type);
                return  param;
            }
        };


        MySingleton.getInstance(ComplaintsDesActivity.this).addToRequestQueue(stringRequest);





    }

    private void sendSmsToPhone(final String mobileNumber, final String Complaintid, final String Type, final String Message) {

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, "http://happystore.16mb.com/sihapi/SmsApi.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ComplaintsDesActivity.this, "Sms Sent", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("mobileno",mobileNumber);

                if(Type.equals("reject"))
                {
                    param.put("message","Your Complaint is Rejected \n\n Complaint id "+Complaintid+"\n\n Reject Reason : "+Message+" \n\n ADMIN SIH 2018\n\n(Byte Walker)");

                }
                else if(Type.equals("inprogress"))
                {
                    param.put("message","Your Complaint is in Progress \n\n Complaint id "+Complaintid+"\n\n it will be resolved soon \n\n ADMIN SIH 2018\n\n(Byte Walker)");

                }
                else
                {
                    param.put("message","Your Complaint is Resolved \n\n Complaint id "+Complaintid+"\n\nCheck Status On Application  Thankyou for participating in to cleaning the India  \n\n ADMIN SIH 2018\n\n(Byte Walker)");

                }
                return  param;
            }



        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    void checkPermissions(){
        String s[]={android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                getImages();
            }
            else{
                ActivityCompat.requestPermissions((Activity) this,s,123);
            }
        }
        else{
            ActivityCompat.requestPermissions((Activity) this,s,123);
        }
    }

    private void getImages() {
       Config config = new Config();
        config.setSelectionMin(1);
        config.setSelectionLimit(4);
        ImagePickerActivity.setConfig(config);
        Intent intent  = new Intent(this, ImagePickerActivity.class);
        startActivityForResult(intent,INTENT_REQUEST_GET_IMAGES);

    }

    void forward(){
        String Type = SharedpreferenceHelper.getInstance(this).getType();

        if(Type.equals("region_admin")){

            ImageView imageView = findViewById(R.id.forword);

            imageView.setVisibility(View.VISIBLE);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    FirebaseDatabase.getInstance().getReference().child("complaints").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("complaint_status").getValue().toString().equals("Reject")){

                                  Toast.makeText(ComplaintsDesActivity.this, "Compalint Alredy Rejected", Toast.LENGTH_SHORT).show();
                            }
                            else if(dataSnapshot.child("complaint_status").getValue().toString().equals("In Progress")){
                                String s = dataSnapshot.child("complaint_forwardto").getValue().toString();
                                FirebaseDatabase.getInstance().getReference().child("authority_admin").child(s).addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        Toast.makeText(ComplaintsDesActivity.this,
                                                "Compalint Alredy Forwarded to "
                                                +dataSnapshot.child("authority").getValue().toString()
                                                , Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            else if(dataSnapshot.child("complaint_status").getValue().toString().equals("Resolved")){
                                if(dataSnapshot.child("complaint_resolved_by").getValue().toString().equals("admin")){
                                    FirebaseDatabase.getInstance().getReference().child("authority_admin")
                                            .child(dataSnapshot.child("complaint_forwardto").getValue().toString()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Toast.makeText(ComplaintsDesActivity.this, " Complaint Resolved by "+
                                                    dataSnapshot.child("authority").getValue().toString(), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(ComplaintsDesActivity.this, " Complaint Resolved by User"
                                            , Toast.LENGTH_SHORT).show();
                                } }
                            else{
                                  forwardedToAuthority();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }


            });



        }

    }

    public  void forwardedToAuthority(){

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

                FirebaseDatabase.getInstance().getReference()
                        .child("complaints").child(key).child("complaint_status").setValue("In Progress");

                FirebaseDatabase.getInstance().getReference()
                         .child("complaints").child(key).child("complaint_forward_time").setValue(ServerValue.TIMESTAMP);

                FirebaseDatabase.getInstance().getReference().child("complaints").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        FirebaseDatabase.getInstance().getReference().child("Users").child(dataSnapshot.child("complainer_id").getValue().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


//                                final DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference().child("notification").child(dataSnapshot.getKey().toString());
//
//                                final String T  = mRoot.push().getKey();
//                                HashMap<String,String> param = new HashMap<>();
//                                param.put("title","Complaint "+ key +"  in Progress");
//                                param.put("description","Your Complaint Will be Resolved Soon");
//                                param.put("status","true");
//                                param.put("time", String.valueOf(12345));
//
//
//
//                                mRoot.child(T).setValue(param).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//
//                                        mRoot.child(T).child("time").child("t").setValue(ServerValue.TIMESTAMP);
//                                        mRoot.child(T).child("time").addListenerForSingleValueEvent(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                                Long time =  Long.parseLong(dataSnapshot.child("t").getValue().toString());
//                                                mRoot.child(T).child("time").setValue(time.toString());
//                                            }
//
//                                            @Override
//                                            public void onCancelled(DatabaseError databaseError) {
//
//                                            }
//                                        });
//
//
//                                        Toast.makeText(ComplaintsDesActivity.this, "notification rcyler sent", Toast.LENGTH_SHORT).show();
//                                    }
//                                });

                                sendSmsToPhone(dataSnapshot.child("mobile").getValue().toString(),key,"inprogress"," ");
                                sendPushNotification(dataSnapshot.child("token").getValue().toString(),"Your Complaint in Progress ","It will be Resolved Shortly ");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialog.dismiss();
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

    @Override
    public void onActivityResult(int requestCode, int resuleCode, final Intent intent) {
        super.onActivityResult(requestCode, resuleCode, intent);

        if (requestCode == INTENT_REQUEST_GET_IMAGES && resuleCode == Activity.RESULT_OK ) {
            progressBar = new ProgressDialog(this);
            progressBar.setMessage("INITIALIZING ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();


            ArrayList<Uri> image_uris = intent.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
            Toast.makeText(this, image_uris.size()+" "+image_uris.toString(), Toast.LENGTH_SHORT).show();

            final DatabaseReference  reference = FirebaseDatabase.getInstance().getReference();
            for (int i = 0;i<image_uris.size();i++ )
            {
                final int finalI = i;
                Toast.makeText(this, ""+key, Toast.LENGTH_SHORT).show();
                Uri img_uri = Uri.fromFile(new File(String.valueOf(image_uris.get(i))));
                mStorage.child("complaints").child(key+"_request_response_"+(i+1)+".jpg").putFile(img_uri)
                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                Toast.makeText(getApplicationContext(), "image " + finalI + "uploaded", Toast.LENGTH_SHORT).show();
                                HashMap<String,String> data = new HashMap<>();
                                data.put("image",task.getResult().getDownloadUrl().toString());
                                data.put("type","request response");
                                reference.child("complaints").child(key).child("images").push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressBar.dismiss();
                                        Intent intent1 = new Intent(ComplaintsDesActivity.this,AuthorityDashboardActivity.class);
                                        startActivity(intent1);
                                        finish();
                                        FirebaseDatabase.getInstance().getReference().child("complaints").child(key)
                                                .child("complaint_resolved_by").setValue("admin");

                                        FirebaseDatabase.getInstance().getReference().child("complaints").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                FirebaseDatabase.getInstance().getReference().child("Users").child(dataSnapshot.child("complainer_id").getValue().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {


//                                                        final DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference().child("notification").child(dataSnapshot.getKey().toString());
//
//                                                        final String T  = mRoot.push().getKey();
//
//                                                        HashMap<String,String> param = new HashMap<>();
//                                                        param.put("title","Complaint "+ key +"  Resolved");
//                                                        param.put("description","Thank You");
//                                                        param.put("status","true");
//                                                        param.put("time", String.valueOf(12345));
//
//
//                                                        mRoot.child(T).setValue(param).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<Void> task) {
//
//                                                                mRoot.child(T).child("time").child("t").setValue(ServerValue.TIMESTAMP);
//                                                                mRoot.child(T).child("time").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                                                        Long time =  Long.parseLong(dataSnapshot.child("t").getValue().toString());
//                                                                        mRoot.child(T).child("time").setValue(time.toString());
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//
//                                                                Toast.makeText(ComplaintsDesActivity.this, "notification rcyler sent", Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        });


                                                        UserMobileNo = dataSnapshot.child("mobile").getValue().toString();
                                                        UserIdToken = dataSnapshot.child("token").getValue().toString();
                                                        sendSmsToPhone(UserMobileNo,key,"resolved"," ");
                                                        sendPushNotification(UserIdToken,"Complain Resolved ","ThankYou For Participating in To clean India");

                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                        FirebaseDatabase.getInstance().getReference().child("complaints").child(key)
                                                .child("complaint_resolved_time").setValue(ServerValue.TIMESTAMP);

                                        FirebaseDatabase.getInstance().getReference()
                                                .child("complaints").child(key).child("complaint_status").setValue("Resolved");
                                    }
                                });
                            }
                        });
            }
            //do something//
            progressBar.dismiss();
        }

    }


}
