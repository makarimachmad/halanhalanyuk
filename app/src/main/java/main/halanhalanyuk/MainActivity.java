package main.halanhalanyuk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import main.halanhalanyuk.Common.LoginUser;
import main.halanhalanyuk.Interface.OnItemClickListener;
import main.halanhalanyuk.Model.Acara;
import main.halanhalanyuk.Model.KulinerModel;
import main.halanhalanyuk.ViewHolder.AcaraViewHolder;
import main.halanhalanyuk.ViewHolder.KulinerViewHolder;
import main.halanhalanyuk.helper.BottomNavHelper;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private NestedScrollView scrollView;
    FloatingActionButton fab;
    Button btn;
    FirebaseDatabase db;
    DatabaseReference dbref, dbrefKuliner;
    FirebaseRecyclerAdapter<Acara, AcaraViewHolder> adapter;
    FirebaseRecyclerAdapter<KulinerModel,KulinerViewHolder> kulinerAdapter;
    RecyclerView recyclerView,rec;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    RelativeLayout acara,r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab_menu);

        db = FirebaseDatabase.getInstance();
         dbref = db.getReference("event");
         dbrefKuliner = db.getReference("kuliner");
        layoutManager = new LinearLayoutManager(this);
        layoutManager2 = new LinearLayoutManager(this);
         acara = (RelativeLayout) getLayoutInflater().inflate(R.layout.acara_menu,null);
        r = (RelativeLayout) getLayoutInflater().inflate(R.layout.kuliner_menu,null) ;
        rec = (RecyclerView) r.findViewById(R.id.recycler_kuliner);

        recyclerView = acara.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FirebaseRecyclerAdapter<Acara, AcaraViewHolder>(Acara.class,
                R.layout.acara_menu_item,
                AcaraViewHolder.class,
                dbref) {
            @Override
            protected void populateViewHolder(AcaraViewHolder viewHolder, Acara model, final int position) {
                viewHolder.WaktuAcara.setText(model.getTanggal());
                viewHolder.JudulAcara.setText(model.getJudul());
                viewHolder.LokasiAcara.setText(model.getLokasi());
                Picasso.with(MainActivity.this).load(model.getGambarUri()).into(viewHolder.img);
                viewHolder.setListener(new OnItemClickListener() {
                    @Override
                    public void Onclick(View view, int pos) {


                        Intent intent = new Intent(MainActivity.this,Detail_acara.class);
                        intent.putExtra("idacara",""+adapter.getRef(position).getKey().toString());
                        startActivity(intent);
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
        kulinerAdapter = new FirebaseRecyclerAdapter<KulinerModel, KulinerViewHolder>(KulinerModel.class,
                R.layout.kuliner_menu_item,
                KulinerViewHolder.class,
                dbrefKuliner
        ) {
            @Override
            protected void populateViewHolder(KulinerViewHolder viewHolder, KulinerModel model, int position) {
                    viewHolder.judulKuliner.setText(model.getJudul());
                    Picasso.with(MainActivity.this).load(model.getUrl()).into(viewHolder.kuliner_detail_image);
                    viewHolder.setClick(new OnItemClickListener() {
                        @Override
                        public void Onclick(View view, int pos) {
                               Intent intent = new Intent(MainActivity.this,kuliner_menu.class);
                               intent.putExtra("idkuliner",kulinerAdapter.getRef(pos).getKey());
                               startActivity(intent);
                        }
                    });
            }
        };

        rec.setAdapter(kulinerAdapter);
        rec.setLayoutManager(layoutManager2);
        scrollView = findViewById(R.id.scrolls);
        CollapsingToolbarLayout tollbar = findViewById(R.id.collapsingtoolbar);
        tollbar.setTitle("");
        CircleImageView imgProfile = (CircleImageView) findViewById(R.id.profile_image);
        btn = findViewById(R.id.edit_profile);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UserProfile.class));
            }
        });
        if(LoginUser.user.getImgProfile() != null)Picasso.with(this).load(LoginUser.user.getImgProfile()).into(imgProfile);
        TextView tx = findViewById(R.id.profile_username);
        if(LoginUser.user.getNama() != null)tx.setText(LoginUser.user.getNama());


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavHelper helper = new BottomNavHelper();
        helper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        load_acara();




    }


    //memanggil menu-menu bottom navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_acara:
                    load_acara();

                    return true;
                case R.id.navigation_penginapan:
                        load_penginapan();
                    fab.hide();
                    return true;
                case R.id.navigation_wisata:
                        load_wisata();
                    fab.hide();
                    return true;
                case R.id.navigation_kuliner:
                        load_kuliner();
                    fab.show();
                    return true;
                case R.id.navigation_bintang:
                        load_bintang();
                    fab.hide();
                    return true;
            }
            return false;
        }
    };

    private void load_bintang() {
        RelativeLayout l = scrollView.findViewById(R.id.item_menu);
        l.removeAllViews();
    }

    private void load_kuliner() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,tambah_kuliner.class));
            }
        });

        RelativeLayout l = scrollView.findViewById(R.id.item_menu);
        l.removeAllViews();


        l.addView(r);
    }

    private void load_wisata() {
        RelativeLayout l = scrollView.findViewById(R.id.item_menu);
        l.removeAllViews();
        l.addView(getLayoutInflater().inflate(R.layout.activity_wisata,null));
    }

    private void load_acara() {
        RelativeLayout l = scrollView.findViewById(R.id.item_menu);
        l.removeAllViews();
        l.addView(acara);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UploadEvent.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }
    private void load_penginapan() {
        RelativeLayout l = scrollView.findViewById(R.id.item_menu);
        l.removeAllViews();
        l.addView(getLayoutInflater().inflate(R.layout.activity_penginapan,null));
    }

}