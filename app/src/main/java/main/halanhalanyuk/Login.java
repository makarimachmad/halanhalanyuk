package main.halanhalanyuk;

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

import main.halanhalanyuk.Common.LoginUser;
import main.halanhalanyuk.Model.User;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button bmanual, bgoogle;
    TextView tekregistrasi;
    EditText eusername, epassword;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("User");

        username = findViewById(R.id.edt_username);
        password = findViewById(R.id.edt_password);

        bmanual = findViewById(R.id.btn_login_manual);
        bgoogle = findViewById(R.id.btn_login_google);
        tekregistrasi = findViewById(R.id.txt_registrasi);

        eusername = findViewById(R.id.edt_username);
        epassword = findViewById(R.id.edt_password);

        bmanual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_manual();
            }
        });

        bgoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Belum dibuat gan", Toast.LENGTH_SHORT).show();
            }
        });

        tekregistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registrasi.class));
                finish();
            }
        });

    }

    private void login_manual() {

        if (!eusername.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")) {
            final String user = eusername.getText().toString().trim();
            final String pw = password.getText().toString().trim();

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(user).exists()) {
                        User u = dataSnapshot.child(user).getValue(User.class);

                        if (u.getPassword().equals(pw)) {
                            LoginUser.user = u;
                            LoginUser.LoginUsername = user;
                            startActivity(new Intent(Login.this, MainActivity.class));
                        }

                    } else {
                        Toast.makeText(Login.this, "Username atau password Salah", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}


