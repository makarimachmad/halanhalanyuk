package main.halanhalanyuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import main.halanhalanyuk.Common.LoginUser;

public class EditProfile extends AppCompatActivity {
    Uri filePath;
    EditText nama,username, nohp, email;
    Button btnSave, btnGantiImg;
    CircleImageView img;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase db;
    DatabaseReference dbref;
    ProgressDialog pd;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("User");
        nama = findViewById(R.id.edit_profile_nama);
        email = findViewById(R.id.edit_profile_email);
        nohp = findViewById(R.id.edit_profile_no_hp);
        username=findViewById(R.id.edit_profile_username);
        img = findViewById(R.id.edit_profile_image);
        btnSave = findViewById(R.id.edit_btnSave);
        btnGantiImg = findViewById(R.id.edit_gantiImg);
        nama.setText(LoginUser.user.getNama());
        email.setText(LoginUser.user.getEmail());
        nohp.setText(LoginUser.user.getNoHp());
        username.setText(LoginUser.LoginUsername);
        if(LoginUser.user.getImgProfile() != null)Picasso.with(this).load(LoginUser.user.getImgProfile()).into(img);
        btnGantiImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_image();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_change();
            }
        });

    }

    private void save_change() {


        if( nama.getText() !=null )
            LoginUser.user.setNama(nama.getText().toString());
        if(nohp.getText() != null)
            LoginUser.user.setNoHp(nohp.getText().toString());
        if( email.getText() != null)
            LoginUser.user.setEmail(email.getText().toString());


        if(filePath != null ) {
             pd = new ProgressDialog(EditProfile.this);
             pd.setMessage("Loading");
            pd.show();
            StorageReference ref = storageReference.child("images/profile"+ UUID.randomUUID());
            ref.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    pd.dismiss();


                    String url = task.getResult().getDownloadUrl().toString();
                    LoginUser.user.setImgProfile(url);
                    dbref.child(LoginUser.LoginUsername).setValue(LoginUser.user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(EditProfile.this, "Sukses Edit", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else{
            dbref.child(LoginUser.LoginUsername).setValue(LoginUser.user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(EditProfile.this, "Sukses Edit", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    private void load_image() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,71);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == 71){
            if(data != null && data.getData() != null){
                filePath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                    img.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
