package com.example.myapplication22.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.example.myapplication22.R;
import com.example.myapplication22.databinding.ActivityMainBinding;
import com.example.myapplication22.model.Model;
import com.example.myapplication22.view_model.ViewModel;

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

        SeekBar rudderBar = findViewById(R.id.rudderBar);
        SeekBar throttleBar = findViewById(R.id.throttleBar);

        //rudderBar.setMin(-1000);
        rudderBar.setMax(2000);
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

        //throttleBar.setMin(0);
        throttleBar.setMax(2000);
        throttleBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vm.update(throttle, (float) (throttleBar.getProgress() - 1000) / 1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> vm.connect());
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {
        if (-1.0 <= xPercent && 1.0 >= xPercent)
            vm.update(aileron, xPercent);
        if (-1.0 <= yPercent && 1.0 >= yPercent)
            vm.update(elevator, -yPercent);
    }
}




