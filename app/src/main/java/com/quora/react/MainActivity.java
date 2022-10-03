package com.quora.react;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.textfield.TextInputEditText;
import com.quora.react.databinding.ActivityMainBinding;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    int CurrentPods = 1;
    TextView Counter;

    EditText TimeWait;

    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // counter text
        Counter = findViewById(R.id.counter);
        Counter.setText(String.valueOf(CurrentPods));

        // Time wait
        TimeWait = findViewById(R.id.waittimebox);

        // Start Button
        Button StartButton = findViewById(R.id.startbutton);
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Algorithm();
            }
        });

        // init text to speech
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.GERMAN);
                }
            }
        });

        // Seekbar
        SeekBar Seekbar = findViewById(R.id.seekBar);
        Seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                CurrentPods = i;
                Counter.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void Algorithm() {
        // wait
        try {
            TimeUnit.SECONDS.sleep(Long.valueOf(String.valueOf(TimeWait.getText())));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CallNewPod();
    }

    public void CallNewPod() {
        /*int[] IntArray = new int[CurrentPods];
        for (int i: IntArray) {
            IntArray[i] = ThreadLocalRandom.current().nextInt(1, CurrentPods + 1);
        }*/
        int CalledPod = ThreadLocalRandom.current().nextInt(1, CurrentPods + 1);
        // text to speech
        tts.speak(String.valueOf(CalledPod), TextToSpeech.QUEUE_FLUSH, null);


        Algorithm();

    }

}