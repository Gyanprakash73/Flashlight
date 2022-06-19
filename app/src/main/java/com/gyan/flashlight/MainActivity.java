package com.gyan.flashlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    boolean hasCameraFlash = false;
    boolean isPressed;
    Toolbar toolbar;
    SwitchCompat switchCompat;
    ImageView tourchImg;
    private MediaPlayer mp;

    //New code
    boolean value = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar set
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tourchImg = findViewById(R.id.torchbtn);
        switchCompat = findViewById(R.id.btnswitch);
        //Switch button
//        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
//        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                torch(hasCameraFlash);
//            }
//        });
        SharedPreferences sharedPreferences = getSharedPreferences("isChecked", 0);
        value = sharedPreferences.getBoolean("isChecked", value);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedPreferences.edit().putBoolean("isChecked", true).apply();
                    tourchImg.setImageResource(R.drawable.torch_on);
                    switchCompat.setChecked(true);
                    playOnOffSound();
                    flashLightOn();
                } else {
                    sharedPreferences.edit().putBoolean("isChecked", false).apply();
                    tourchImg.setImageResource(R.drawable.torch_off);
                    switchCompat.setChecked(false);
                    playOnOffSound();
                    flashLightOff();
                }
            }
        });


    }

    //Call Switch button
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void torch(boolean hasCameraFlash) {
//        if (hasCameraFlash) {
//            if (isPressed) {
//                tourchImg.setImageResource(R.drawable.torch_off);
//                switchCompat.setChecked(false);
//                playOnOffSound();
//                flashLightOff();
//            } else {
//                tourchImg.setImageResource(R.drawable.torch_on);
//                switchCompat.setChecked(true);
//                playOnOffSound();
//                flashLightOn();
//            }
//            isPressed = !isPressed;
//        } else {
//            Toast.makeText(MainActivity.this, "No flash available on your device", Toast.LENGTH_SHORT).show();
//        }
//    }

    //Play Sound
    private void playOnOffSound() {
        mp = MediaPlayer.create(MainActivity.this, R.raw.sound);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mp.start();
    }

    //flash On
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    //flash Off
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

}