package com.example.mecanimex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        //Agregar Animaciones

        Animation animacion1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.desplazar_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.desplazar_abajo);

        final TextView Appname = findViewById(R.id.Appname);
        final ImageView logoimg = findViewById(R.id.logoimg);

        Appname.setAnimation(animacion2);
        logoimg.setAnimation(animacion1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Login.class);

                Pair[] pairs= new Pair[2];
                pairs[0] = new Pair<View, String>(logoimg,"imgregistroTrans");
                pairs[1] = new Pair<View, String>(Appname,"BienvenidoTrans");

                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                    startActivity(intent, options.toBundle());
                }else{
                    startActivity(intent);
                    finish();
                }
            }
        }, 4000);


    }
}
