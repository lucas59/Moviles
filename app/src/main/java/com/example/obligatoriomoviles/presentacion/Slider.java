package com.example.obligatoriomoviles.presentacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.obligatoriomoviles.Clases.Slider_Adapter;
import com.example.obligatoriomoviles.R;

public class Slider extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private TextView[] mDots;
    private LinearLayout mDotLayout;
    private Slider_Adapter slider_adapter;
    private Button nNextbtn;
    private Button nBackbtn;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        SharedPreferences prefs = getSharedPreferences("session", Context.MODE_PRIVATE);
        String email = prefs.getString("sessionCorreo", null);
        if (email != null) {
            Intent i = new Intent(getApplicationContext(), Menu_principal.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.infade, R.anim.outfade);
        } else {
            mSlideViewPager = (ViewPager) findViewById(R.id.slide_view);
            mDotLayout = (LinearLayout) findViewById(R.id.dots);
            slider_adapter = new Slider_Adapter(this);
            mSlideViewPager.setAdapter(slider_adapter);
            nNextbtn = (Button) findViewById(R.id.sig);
            nBackbtn = (Button) findViewById(R.id.prev);
            addDotsIndicator(0);
            mSlideViewPager.addOnPageChangeListener(viewListener);
            nNextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int contador_botones = mCurrentPage + 1;
                    if (contador_botones == 3) {
                        Intent i = new Intent(getApplicationContext(), login.class);
                        startActivity(i);
                        finish();
                        contador_botones = 0;

                    } else {
                        mSlideViewPager.setCurrentItem(mCurrentPage + 1);
                    }
                }
            });

            nBackbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSlideViewPager.setCurrentItem(mCurrentPage - 1);

                }
            });
        }
    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[3];
        mDotLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.texto));

            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.comentario));
        }


    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {


        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;
            if (i == 0) {
                nNextbtn.setEnabled(true);
                nBackbtn.setEnabled(false);
                nBackbtn.setVisibility(View.INVISIBLE);
                nNextbtn.setText("Siguiente");
                nBackbtn.setText("");
            } else if (i == mDots.length - 1) {
                nNextbtn.setEnabled(true);
                nBackbtn.setEnabled(true);
                nBackbtn.setVisibility(View.VISIBLE);
                nNextbtn.setText("Iniciar");
                nBackbtn.setText("Atras");
            } else {
                nNextbtn.setEnabled(true);
                nBackbtn.setEnabled(true);
                nBackbtn.setVisibility(View.VISIBLE);
                nNextbtn.setText("Siguiente");
                nBackbtn.setText("Atras");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}