package com.example.myapplication22.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.example.myapplication22.R;
import com.example.myapplication22.databinding.ActivityMainBinding;
import com.example.myapplication22.model.Model;
import com.example.myapplication22.view_model.ViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.net.URI;

public class MainActivity extends AppCompatActivity implements JoystickView.JoystickListener {

    ViewModel vm;
    String aileron = "flight/aileron";
    String elevator = "flight/elevator";
    String rudder = "flight/rudder";
    String throttle = "engines/current-engine/throttle";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("FlightGear Joystick\n");
        ActivityMainBinding b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vm = new ViewModel(new Model());
        b.setVm(vm);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int rawId = getResources().getIdentifier("gif_entrance",  "raw", getPackageName());
        String path = "android.resource://" + getPackageName() + "/" + rawId;
        VideoView vv = (VideoView)findViewById(R.id.gif_video_view);
        vv.setVideoURI(Uri.parse(path));

        vv.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            vv.start();
        });



        SeekBar rudderBar = findViewById(R.id.rudderBar);
        SeekBar throttleBar = findViewById(R.id.throttleBar);

        rudderBar.setMax(2000);
        rudderBar.setProgress(1000);
        rudderBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vm.update(rudder, (float) (rudderBar.getProgress() - 1000) / 1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        throttleBar.setMax(1000);
        throttleBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vm.update(throttle, (float) throttleBar.getProgress() / 1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            if (!vm.connect()) {
                Snackbar sb = Snackbar.make(v, R.string.alert, Snackbar.LENGTH_SHORT);
                sb.show();
            } else {
                MediaPlayer mp = MediaPlayer.create(this, R.raw.ready_for_takeoff);
                mp.start();
                float volume = 100;
                mp.setVolume(volume, volume);
                changeScreen();
            }
        });
        ((JoystickView)findViewById(R.id.joystick)).addImages(
                findViewById(R.id.base),
                findViewById(R.id.controller)
        );

    }

    public void changeScreen() {
        findViewById(R.id.editTextTextPersonName).setVisibility(View.INVISIBLE);
        findViewById(R.id.editTextTextPersonName2).setVisibility(View.INVISIBLE);
        findViewById(R.id.button).setVisibility(View.INVISIBLE);
        findViewById(R.id.button).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView2).setVisibility(View.INVISIBLE);
        findViewById(R.id.joystick).setVisibility(View.VISIBLE);
        findViewById(R.id.rudderBar).setVisibility(View.VISIBLE);
        findViewById(R.id.throttleBar).setVisibility(View.VISIBLE);
        findViewById(R.id.gif_video_view).setVisibility(View.INVISIBLE);
        findViewById(R.id.gradiant).setVisibility(View.INVISIBLE);
        findViewById(R.id.logo).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {
        if (-1.0 <= xPercent && 1.0 >= xPercent)
            vm.update(aileron, xPercent);
        if (-1.0 <= yPercent && 1.0 >= yPercent)
            vm.update(elevator, -yPercent);
    }
}




