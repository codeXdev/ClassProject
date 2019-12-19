package com.theteam.classproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.lang.invoke.CallSite;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecordList extends AppCompatActivity {

    ListView recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        recordList = (ListView) findViewById(R.id.recordList);

        ArrayList<String> list = new ArrayList<>();

        list.add("Tomato");
        list.add("is");
        list.add("a");
        list.add("good");
        list.add("vegetable");
        list.add("it");
        list.add("is");
        list.add("android");

        list.add("Tomato");
        list.add("is");
        list.add("a");
        list.add("good");
        list.add("vegetable");
        list.add("it");
        list.add("is");
        list.add("android");

        list.add("Tomato");
        list.add("is");
        list.add("a");
        list.add("good");
        list.add("vegetable");
        list.add("it");
        list.add("is");
        list.add("android");

//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
//        recordList.setAdapter(adapter);


        final File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SARecords/");


        // gets the files in the directory
//        File fileDirectory = new File(Environment.getDataDirectory() + "/YourDirectory/");
        // lists all the files into an array
//        File[] dirFiles = fileDirectory.listFiles();
        File[] dirFiles = folder.listFiles();

        ArrayList<String> files = new ArrayList<>();

        if (dirFiles.length != 0) {
            // loops through the array of files, outputing the name to console
            for (int ii = 0; ii < dirFiles.length; ii++) {
                String fileOutput = dirFiles[ii].toString();
                files.add(fileOutput);
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, files);
        recordList.setAdapter(adapter);
    }
}
