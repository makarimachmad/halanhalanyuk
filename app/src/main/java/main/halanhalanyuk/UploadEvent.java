package main.halanhalanyuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.io.IOException;
import java.util.UUID;

import main.halanhalanyuk.Common.LoginUser;
import main.halanhalanyuk.Model.Acara;

public class UploadEvent extends AppCompatActivity {

    EditText Nama, Tanggal,Lokasi,Keterangan;
    ImageView Img;
    Button btn,btnUpload;

    FirebaseStorage Storage;
    StorageReference storageReference;
    FirebaseDatabase db;
    DatabaseReference dbref;
    private Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_event);
        Nama = (EditText)findViewById(R.id.namaAcara);
        Tanggal = (EditText) findViewById(R.id.tanggalAcara);
        Lokasi = (EditText)findViewById(R.id.lokasiAcara);
        Keterangan = (EditText)findViewById(R.id.keteranganAcara);
        Img = findViewById(R.id.img_preview);
        btn = findViewById(R.id.btnUpload);
        btnUpload = findViewById(R.id.btnKirim);
        Storage = FirebaseStorage.getInstance();
        storageReference = Storage.getReference();
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("event");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_image();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uplodImage();
            }
        });
    }

    private void uplodImage() {
        if(filePath != null){
             final  ProgressDialog pg = new ProgressDialog(this);
            pg.setTitle("Sedang Upload");
            pg.show();
            StorageReference ref = storageReference.child("images/event/"+ UUID.randomUUID().toString());
            ref.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    Acara event = new Acara();
                    if(Nama.getText() != null && Tanggal.getText() != null && Lokasi.getText() != null ){
                        event.setAuthor(LoginUser.LoginUsername);
                        event.setGambarUri(task.getResult().getDownloadUrl().toString());
                        event.setLokasi(Lokasi.getText().toString());
                        event.setJudul(Nama.getText().toString());
                        event.setTanggal(Tanggal.getText().toString());
                        event.setKeterangan(Keterangan.getText().toString());
                        dbref.child(""+System.currentTimeMillis()).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(UploadEvent.this, "Sukses nambah acara", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UploadEvent.this,MainActivity.class));
                            }
                        });

                    }
                    pg.dismiss();
                    Toast.makeText(UploadEvent.this, "OK", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
                    Img.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
