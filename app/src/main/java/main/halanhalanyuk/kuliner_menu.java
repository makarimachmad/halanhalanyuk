package main.halanhalanyuk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import main.halanhalanyuk.Model.KulinerModel;

public class kuliner_menu extends AppCompatActivity {

    ImageView img;
    TextView txJudul,txKeterangan;
    FirebaseDatabase database;
    DatabaseReference ref;
    String s;
    KulinerModel k;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuliner_menu);
        s =getIntent().getStringExtra("idkuliner").toString();
        database = FirebaseDatabase.getInstance();
        ref= database.getReference("kuliner");
        img = findViewById(R.id.KulinerDetailImage);
        txJudul = findViewById(R.id.KulinerDetailNama);
        txKeterangan =findViewById(R.id.KulinerDetailKeterangan);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


              k =  dataSnapshot.child(s).getValue(KulinerModel.class);
                Picasso.with(kuliner_menu.this).load(k.getUrl()).into(img);
                txJudul.setText(k.getJudul());
                txKeterangan.setText(k.getKeterangan());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
