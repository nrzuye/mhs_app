package com.siakadakademik.mhs_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.siakadakademik.mhs_app.intro.Intro1;
import com.siakadakademik.mhs_app.intro.Intro2;
import com.siakadakademik.mhs_app.intro.Intro3;


/**
 * Created by Irsyad on 8/25/2017.
 */

public class IntroActivity extends AppIntro {

    Intro1 intro1 = new Intro1();
    Intro2 intro2 = new Intro2();
    Intro3 intro3 = new Intro3();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
//        addSlide(intro1);
//        addSlide(intro2);
//        addSlide(intro3);
//        addSlide(firstFragment);
//        addSlide(secondFragment);
//        addSlide(thirdFragment);
//        addSlide(fourthFragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
//        addSlide(AppIntroFragment.newInstance(title, description, image, backgroundColor));
        addSlide(AppIntroFragment.newInstance("SELAMAT DATANG", "Aplikasi siakad universitas muhammadiyah gresik!", R.drawable.umg, getResources().getColor(R.color.colorPrimaryDark)));
        addSlide(AppIntroFragment.newInstance("SIAKAD UMG", "Bagi Mahasiswa, Login ke sistem menggunakan :\n" +
                        "NIM sebagai USERNAME dan TANGGAL LAHIR sebagai PASSWORD (dengan format YYYYMMDD).\n",
                R.drawable.umg, getResources().getColor(R.color.colorPrimaryDark)));
        addSlide(AppIntroFragment.newInstance("", "Version 1.0 ", R.drawable.umg, getResources().getColor(R.color.colorPrimaryDark)));

        // OPTIONAL METHODS
        // Override bar/separator color.
        //setBarColor(getResources().getColor(R.color.colorPrimaryDark));

        setBarColor(getResources().getColor(R.color.colorPrimaryDark));
//        setSeparatorColor(Color.parseColor("#2196F3"));
        setSeparatorColor(getResources().getColor(R.color.card_color));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
