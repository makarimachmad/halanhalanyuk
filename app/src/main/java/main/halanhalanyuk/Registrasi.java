package main.halanhalanyuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import main.halanhalanyuk.Model.User;

public class Registrasi extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    Button bregistrasi;
    TextView tsudah;
    EditText eusernamereg, epasswordreg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("User");
        bregistrasi = findViewById(R.id.btn_save_data);
        tsudah = findViewById(R.id.txt_login);

        eusernamereg = findViewById(R.id.edt_username_registrasi);
        epasswordreg = findViewById(R.id.edt_password_registrasi);

        bregistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eusernamereg.getText().toString() != null && epasswordreg.getText().toString() != null){

                    final ProgressDialog progressDialog = new ProgressDialog(Registrasi.this);
                    progressDialog.show();
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.child(eusernamereg.getText().toString()).exists()) {
                                User user = new User();
                                user.setPassword(epasswordreg.getText().toString());
                                reference.child(eusernamereg.getText().toString()).setValue(user, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        Toast.makeText(Registrasi.this, "Succes daftar", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(Registrasi.this, Login.class));
                                    }
                                });
                            }else{
                                Toast.makeText(Registrasi.this, "Username sudah terdaftar", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }
        });
        tsudah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registrasi.this, Login.class));
                finish();
            }
        });
    }



    //    public void save_data(View view){
//        Toast.makeText(getApplicationContext(), "Belum Tersedia", Toast.LENGTH_SHORT).show();
//    }
//    public void back_to_login(View view){
//        startActivity(new Intent(Registrasi.this, Login.class));
//        finish();
//    }
}
