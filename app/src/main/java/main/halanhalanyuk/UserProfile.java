package main.halanhalanyuk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import main.halanhalanyuk.Common.LoginUser;
import main.halanhalanyuk.Model.User;

public class UserProfile extends AppCompatActivity {

    CircleImageView img_profile;
    TextView txNama, txUsername, txEmail, txNohp;
    Button btnEdit,btnGantiImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        img_profile = findViewById(R.id.profile_image);
        txNama = findViewById(R.id.profile_nama);
        txUsername = findViewById(R.id.profile_username);
        txEmail = findViewById(R.id.profile_email);
        txNohp = findViewById(R.id.profile_no_hp);
        if(LoginUser.user.getImgProfile() != null)Picasso.with(this).load(LoginUser.user.getImgProfile()).into(img_profile);
        txNama.setText(LoginUser.user.getNama());
        txNohp.setText(LoginUser.user.getNoHp());
        txEmail.setText(LoginUser.user.getEmail());
        txUsername.setText(LoginUser.LoginUsername);
        btnEdit = findViewById(R.id.btnEdit);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfile.this,EditProfile.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
