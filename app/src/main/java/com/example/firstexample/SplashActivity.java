package com.example.firstexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    ImageView iconIv,titleIv;
    Animation upAnim,downAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iconIv = findViewById(R.id.spalsh_grid_iv);
        titleIv = findViewById(R.id.splash_text_tv);

        upAnim = AnimationUtils.loadAnimation(this,R.anim.up_animation);
        downAnim = AnimationUtils.loadAnimation(this,R.anim.down_animation);
        iconIv.setAnimation(upAnim);
        titleIv.setAnimation(downAnim);

        Model.instance.executor.execute(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            toMainActivity();

        });
    }

    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}