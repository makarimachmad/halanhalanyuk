package main.halanhalanyuk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import main.halanhalanyuk.Model.Acara;

public class Detail_acara extends AppCompatActivity {

    TextView Judul, Tanggal, lokasi, detail, author;
    ImageView img;
    Acara acara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_acara);
        final String id = getIntent().getStringExtra("idacara");
        Judul = findViewById(R.id.acaradetail_judul);
        Tanggal = findViewById(R.id.acaradetail_tanggal);
        lokasi = findViewById(R.id.acaradetail_lokasi);
        detail = findViewById(R.id.acaradetail_keterangan);
        author =findViewById(R.id.acaradetail_author);
        img = findViewById(R.id.acaradetail_image);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference r = database.getReference("event");
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                acara =  dataSnapshot.child(id).getValue(Acara.class);

               if(acara.getJudul()!= null) Judul.setText(acara.getJudul());
               Tanggal.setText(acara.getTanggal());
               lokasi.setText(acara.getLokasi());
               detail.setText(acara.getKeterangan());
               author.setText(acara.getAuthor());
                Picasso.with(Detail_acara.this).load(acara.getGambarUri()).into(img);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
