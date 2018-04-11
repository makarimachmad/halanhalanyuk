package main.halanhalanyuk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class tips extends AppCompatActivity {

    private ViewPager mviewPager;
    private LinearLayout mdotlayout;

    private TextView[] mdots;
    private Button tombol_sebelum, tombol_sesudah;

    private tips_isi objek_slideradapter_tips;

    int patokan = 0;
    String penanda = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        //deklarasi variabel
        mviewPager = findViewById(R.id.viewPager);
        mdotlayout = findViewById(R.id.dotspager);
        tombol_sebelum = findViewById(R.id.button_sebelumnya);
        tombol_sesudah = findViewById(R.id.button_lanjut);

        //memanggil kelas tips_isi
        objek_slideradapter_tips = new tips_isi(this);

        mviewPager.setAdapter(objek_slideradapter_tips);

        //menentukan posisi tips
        addDotsindicator(0);
        mviewPager.addOnPageChangeListener(viewlistener);

        //menambah dan mengurangi halaman
        tombol_sesudah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("curentpos", patokan+"");
                mviewPager.setCurrentItem(patokan + 1);

                if(patokan > 4){
                    finishonboarding();
                }
            }
        });
        tombol_sebelum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mviewPager.setCurrentItem(patokan - 1);
            }
        });



    }

    //mengatur isi dari tips
    public void addDotsindicator(int position){
        mdots = new TextView[5];
        mdotlayout.removeAllViews();

        for (int i=0; i<mdots.length; i++){
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.colordots));

            mdotlayout.addView(mdots[i]);
        }

        if(mdots.length > 0){
            mdots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private void  finishonboarding(){

        Intent penghubung = new Intent(tips.this,Login.class);
        startActivity(penghubung);
        finish();
    }


    //membaca posisi
    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsindicator(position);
            patokan = position;

            if(patokan == 4){
                tombol_sesudah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finishonboarding();
                    }
                });
            }

            if(position == 0){
                tombol_sesudah.setEnabled(true);
                tombol_sebelum.setEnabled(false);
                tombol_sebelum.setVisibility(View.INVISIBLE);

                tombol_sesudah.setText("Lanjut");
                tombol_sebelum.setText("");
            }
            else if(position == mdots.length - 1){
                tombol_sesudah.setEnabled(true);
                tombol_sebelum.setEnabled(true);
                tombol_sebelum.setVisibility(View.VISIBLE);

                tombol_sesudah.setText("Selesai");
                tombol_sebelum.setText("Kembali");

            }else{
                tombol_sesudah.setEnabled(true);
                tombol_sebelum.setEnabled(true);
                tombol_sebelum.setVisibility(View.VISIBLE);

                tombol_sesudah.setText("Lanjut");
                tombol_sebelum.setText("Kembali");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
