package com.tipntale.calicutuniversity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class settingsActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private String APP_LINK = "https://play.google.com/store/apps/details?id=com.tipntale.calicutuniversity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.fullad_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    public void aboutClick(View v)
    {
            Intent openweb=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/shameemlukmanpk"));
            startActivity(openweb);

    }

    public void shareFunction(View v)
    {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String sub = "Download Calicut University App";
            String body = "The best app for Calicut University students to get instant notifications from the university. Get Exam notifications, time table and more..\n\n"+APP_LINK;
            intent.putExtra(Intent.EXTRA_SUBJECT, sub);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent, "Share using"));

    }

    public void openFeed(View v)
    {
        Intent intent =new Intent(settingsActivity.this, feedbackActivity.class);
        startActivity(intent);

    }

    public void openDevWeb(View v)
    {
        Intent openweb=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tipntale.com"));
        startActivity(openweb);

    }
    public void closeSettings(View v)
    {
        if(HomeActivity.adcount%4==0)
        {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
        finish();

    }

    public void onBackPressed() {
        if(HomeActivity.adcount%4==0)
        {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
        finish();
    }

    public void checkUpdate(View v)
    {
        Intent openweb=new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.tipntale.calicutuniversity"));
        startActivity(openweb);
    }
    public void rateApp(View v)
    {
        Intent openweb=new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.tipntale.calicutuniversity"));
        startActivity(openweb);
    }


}
