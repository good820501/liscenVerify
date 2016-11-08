package com.example.homenet;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Activities extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setTheme(R.style.ActivityAlphaBackground);
        setContentView(R.layout.activity_activities);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_activities, menu);
        return true;
    }
}
