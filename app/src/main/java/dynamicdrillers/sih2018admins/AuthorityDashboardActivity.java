package dynamicdrillers.sih2018admins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class AuthorityDashboardActivity extends AppCompatActivity {

    CardView CardComplaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_dashboard);

        CardComplaint = findViewById(R.id.CardComplaints);
        CardComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id  = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Intent intent = new Intent(getBaseContext(),ComplaintsActivity.class);
                intent.putExtra("type","complaint_forwardto");
                intent.putExtra("data",id);
                startActivity(intent);
            }
        });
    }
}
