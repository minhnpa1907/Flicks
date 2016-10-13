package com.minhnpa.coderschool.flicks.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.minhnpa.coderschool.flicks.R;

public class MainActivity extends AppCompatActivity {
    ListView lvMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMovie = (ListView) findViewById(R.id.lvMovie);
    }
}
