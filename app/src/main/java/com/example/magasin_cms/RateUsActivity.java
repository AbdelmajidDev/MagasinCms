package com.example.magasin_cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;


public class RateUsActivity extends AppCompatActivity {
    ImageView ratingImage;
    AppCompatButton rateNow;
    AppCompatButton Maybe_later;
    AppCompatRatingBar ratingBar;
    private float userRate = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        final AppCompatButton rateNowBtn = findViewById(R.id.rateNowBtn);
        final AppCompatButton laterBtn = findViewById(R.id.laterBtn);
        final RatingBar ratingBar = findViewById(R.id.ratingBar);
        final ImageView ratingImage = findViewById(R.id.ratingImage);

        rateNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Your code goes here
            }
        });

        laterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hide rating dialog
                finish();
            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                if (rating <= 1) {
                    ratingImage.setImageResource(R.drawable.angry);
                } else if (rating <= 2) {
                    ratingImage.setImageResource(R.drawable.sad);
                } else if (rating <= 3) {
                    ratingImage.setImageResource(R.drawable.confused);
                } else if (rating <= 4) {
                    ratingImage.setImageResource(R.drawable.smile);
                } else if (rating <= 5) {
                    ratingImage.setImageResource(R.drawable.in_love);
                }

                //animate emoji image
                animateImage(ratingImage);

                //selected rating by user
                userRate = rating;
            }
        });

    }
    private void animateImage (ImageView ratingImage){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);
    }





        //show rating dialog
        /*RateUsDialog rateUsDialog = new RateUsDialog(RateUsActivity.this);
        rateUsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        rateUsDialog.setCancelable(false);
        rateUsDialog.show();*/

    }





