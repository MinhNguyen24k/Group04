package com.example.group04_readbookonline;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group04_readbookonline.databinding.ActivitySplashBinding;

public class Splash_Activity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        animationSplash();

        new Handler(). postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(Splash_Activity.this,LoginActivity.class);
                startActivity(intent);
            }
        },5000);
    }

    private void animationSplash() {
        // Tạo ObjectAnimator để quay ImageView 360 độ
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(binding.img, "rotation", 0f, 360f);
        rotateAnimator.setDuration(1000);
        // LinearInterpolator -> tốc độ di chuyển của animation được duy trì một cách đều nhau.
        rotateAnimator.setInterpolator(new LinearInterpolator());

        // Tạo ObjectAnimator để di chuyển ImageView xuống dưới
        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(binding.img, "translationY", 0f, 900f);
        translateYAnimator.setDuration(1000);
        //AccelerateInterpolator -> animation sẽ di chuyển chậm ở đầu, sau đó tăng tốc dần lên và trở nên nhanh hơn ở cuối.
        translateYAnimator.setInterpolator(new AccelerateInterpolator());

        // Tạo ObjectAnimator để phóng to và thu nhỏ TextView
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(binding.textview, "scaleX", 0f, 1.1f);
        scaleXAnimator.setDuration(1000);
        scaleXAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(binding.textview, "scaleY", 0f, 1.1f);
        scaleYAnimator.setDuration(1000);
        scaleYAnimator.setInterpolator(new AccelerateInterpolator());

        // Kết hợp các ObjectAnimator lại để chạy cùng lúc
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotateAnimator, translateYAnimator, scaleXAnimator, scaleYAnimator);

        animatorSet.start();
    }

}