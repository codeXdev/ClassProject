package com.theteam.classproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecordList extends AppCompatActivity {

    private ListView recordList;
    private ArrayList<String> fileNames;

    //variable to manage whether playing any item or not
    private boolean stopped;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        recordList = findViewById(R.id.recordList);

        final File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SARecords/");

        File[] dirFiles = folder.listFiles();

        final ArrayList<String> files = new ArrayList<>();
        fileNames = new ArrayList<>();

        if (dirFiles.length != 0) {
            // loops through the array of files, outputting the name to console
            for (File dirFile : dirFiles) {
                String fileOutput = dirFile.toString();
                files.add(fileOutput);
                fileNames.add(dirFile.getName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileNames);
        recordList.setAdapter(adapter);

        stopped = true;
        recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String uri =  files.get(position);
                if(stopped){
                    stopped = false;
                    playItem(uri);
                }
                else stopPlaying();
            }
        });
    }


    private void playItem(String uri){
        player = new MediaPlayer();
        try {
            player.setDataSource(uri);
            player.prepare();
            player.start();
            Toast.makeText(getApplicationContext(), uri.substring(uri.lastIndexOf("/")+1) + "Started Playing", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Playing Media", "prepare() failed");
        }
    }

    private void stopPlaying(){
        player.stop();
        player.release();
        player = null;

        Toast.makeText(getApplicationContext(), "Stopped Playing Media", Toast.LENGTH_SHORT).show();
    }
}
