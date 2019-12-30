package com.theteam.classproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Recorder extends AppCompatActivity {

    private boolean paused;
    private Button playBtn;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);

        //Checking Permissions if they are accepted or request them again
        if (!CheckPermissions()) {
            RequestPermissions();
        }

        paused = false;

        playBtn = findViewById(R.id.btnPlay);
        playBtn.setEnabled(false);


        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SARecords/";

        File path = new File(mFileName);

        if (!path.exists()) {
            path.mkdirs();
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        mFileName += "/" + timestamp.getTime() + ".AMR";


        final Button pushBtn = findViewById(R.id.pushBtn);

        pushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //change the text start to stop
                if (pushBtn.getText().toString().toLowerCase().equals("record")) {

                    pushBtn.setText(R.string.recorder_record_stop);
                    pushBtn.setBackgroundResource(R.drawable.button_round_background_record_on);

                    recordAudio();
                } else {
                    pushBtn.setText(R.string.recorder_record_on);
                    pushBtn.setBackgroundResource(R.drawable.button_round_background_record_off);
                    stopRecording();

                    playBtn.setEnabled(true);
                }
            }
        });


        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paused) {
                    paused = false;
                    playBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play_button, 0, 0, 0);
                    playBtn.setText(R.string.recorder_play_start);
                    stopPlaying();
                } else {
                    paused = true;
                    playBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_button, 0, 0, 0);
                    playBtn.setText(R.string.recorder_play_stop);
                    playRecorded();
                }
            }
        });


        Button listViewBtn = findViewById(R.id.listViewBtn);

        listViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recorder.this, RecordList.class);
                startActivity(intent);
            }
        });
    }

    /*
    * recordAudio For recording audio
     */
    public void recordAudio() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(mFileName);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        mRecorder.start();
        Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();
    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();
    }

    public void playRecorded() {

        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
            Toast.makeText(getApplicationContext(), "Recording Started Playing", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public void stopPlaying() {

        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
        Toast.makeText(getApplicationContext(), "Playing Stopped", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestPermissions() {
        ActivityCompat.requestPermissions(Recorder.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }
}
