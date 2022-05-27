package com.tipntale.calicutuniversity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.MobileAds;

public class HomeActivity extends AppCompatActivity {

    public static int adcount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        adcount = 0;

        MobileAds.initialize(this, "ca-app-pub-1077388695437306~9422388900");
    }

    public void openNotification(View v)
    {
        Intent intent = new Intent(this,notificationActivity.class);
        startActivity(intent);
    }

    public void openTimetable(View v)
    {
        Intent intent = new Intent(this,timetableActivity.class);
        startActivity(intent);
    }

    public void openSDE(View v)
    {
        Intent intent = new Intent(this,sdenotificationActivity.class);
        startActivity(intent);
    }
    public void openCUWEB(View v)
    {
        String url = "https://pareekshabhavan.uoc.ac.in/";
        String heading = "Pareekshabhavan";
        Intent intent = new Intent(HomeActivity.this,browserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("heading", heading);
        startActivity(intent);
    }
    public void openResult(View v)
    {
        String url = "http://www.cupbresults.uoc.ac.in/CuPbhavan/index.php";
        String heading = "Results";
        Intent intent = new Intent(HomeActivity.this,browserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("heading", heading);
        startActivity(intent);
    }

    public void openContact(View v)
    {
        Intent intent = new Intent(this,contactActivity.class);
        startActivity(intent);
    }
    public void openSettings(View v)
    {
        Intent intent = new Intent(this,settingsActivity.class);
        startActivity(intent);
    }

    public void openAbout(View v)
    {
        Intent intent = new Intent(this,aboutActivity.class);
        startActivity(intent);
    }
}
