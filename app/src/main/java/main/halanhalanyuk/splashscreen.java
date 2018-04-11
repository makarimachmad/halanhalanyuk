package main.halanhalanyuk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splashscreen extends AppCompatActivity {

    private ImageView logo_splash;
    private static int splash=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);



        SharedPreferences settings = getSharedPreferences("prefs", 0);
        final boolean firstRun = settings.getBoolean("firstRun", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splashscreen.this, firstRun ? tips.class : Login.class));

                if(firstRun){SharedPreferences settings = getSharedPreferences("prefs", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("firstRun", false);
                editor.commit();}finish();
            }
        },3000);




        //set fade in logo
        logo_splash = findViewById(R.id.logo);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animasi_logo);
        logo_splash.startAnimation(anim);

        //pause layout 3 detik



    }


}
