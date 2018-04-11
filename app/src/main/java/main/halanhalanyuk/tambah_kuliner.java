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

import main.halanhalanyuk.Model.KulinerModel;

public class tambah_kuliner extends AppCompatActivity {

    EditText namaKuliner, keteranganKuliner;
    Uri filePath;
    Button pilihGambar, uploadKuliner;
    ImageView img;
    FirebaseDatabase database;
    DatabaseReference dbref;
    FirebaseStorage storege;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kuliner);

        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("kuliner");
        storege = FirebaseStorage.getInstance();
        storageReference = storege.getReference();
        namaKuliner = findViewById(R.id.namaKuliner);
        keteranganKuliner = findViewById(R.id.keteranganKuliner);
        pilihGambar = findViewById(R.id.btnUploadKuliner);
        uploadKuliner = findViewById(R.id.btnKirimKuliner);
        img = findViewById(R.id.img_preview_kuliner);
        pilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_gambar();
            }
        });

        uploadKuliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload_kuliner();
            }
        });
    }

    private void upload_kuliner() {
        if(filePath != null && namaKuliner.getText() != null && keteranganKuliner.getText() != null){
           final ProgressDialog mDialog = new ProgressDialog(tambah_kuliner.this);
           mDialog.show();
            StorageReference ref = storageReference.child("images/kuliner/"+ UUID.randomUUID().toString());
            ref.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    String url = task.getResult().getDownloadUrl().toString();

                    KulinerModel kulinerModel = new KulinerModel();
                    kulinerModel.setJudul(namaKuliner.getText().toString());
                    kulinerModel.setKeterangan(keteranganKuliner.getText().toString());
                    kulinerModel.setUrl(url);


                    dbref.child(System.currentTimeMillis()+"").setValue(kulinerModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(tambah_kuliner.this, "Sukses", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                            finish();

                        }
                    });

                }
            });

        }else{
            Toast.makeText(this, "Tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
    }

    private void load_gambar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,72);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == 72){
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
