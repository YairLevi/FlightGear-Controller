package com.example.myapplication22.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
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
//        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(400, 400);
//        JoystickView jsv = new JoystickView(this);
//        addContentView(jsv, lp);
        setTitle("FlightGear Joystick\n");
        ActivityMainBinding b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vm = new ViewModel(new Model());
        b.setVm(vm);
        //SeekBar aileronBar = findViewById(R.id.aileronBar);
        //SeekBar elevatorBar = findViewById(R.id.elevatorBar);
        SeekBar rudderBar = findViewById(R.id.rudderBar);
        SeekBar throttleBar = findViewById(R.id.throttleBar);
//
//        aileronBar.setMin(-1);
//        aileronBar.setMax(1);
//        aileronBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                vm.update(aileron, (float) aileronBar.getProgress());
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) { }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) { }
//        });
//
//        elevatorBar.setMin(-1);
//        elevatorBar.setMax(1);
//        elevatorBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                vm.update(elevator, (float) elevatorBar.getProgress());
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) { }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) { }
//        });
//
        rudderBar.setMin(-1);
        rudderBar.setMax(1);
        rudderBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vm.update(rudder, (float) rudderBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        throttleBar.setMin(0);
        throttleBar.setMax(1);
        throttleBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vm.update(throttle, (float) throttleBar.getProgress());
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
        if (0.0 <= xPercent && 1.0 >= xPercent)
            vm.update(aileron, xPercent);
        if (0.0 <= yPercent && 1.0 >= yPercent)
            vm.update(elevator, -yPercent);
        System.out.println("X percent: " + xPercent + " Y percent: " + yPercent);
    }
}




