package com.tipntale.calicutuniversity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class sdenotificationActivity extends AppCompatActivity {

    private static final String POST_URL = "https://appdatasample.000webhostapp.com/sdenotification.php";

    private AdView mAdViewSDE;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItemSDE> listItems;
    SwipeRefreshLayout swipe;
    SharedPreferences oP;
    String head1,head2,head3,head4,head5,head6,head7,head8,head9,head10,link1,link2,link3,link4,link5,link6,link7,link8,link9,link10;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdenotification);

        HomeActivity.adcount = HomeActivity.adcount+1;

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.fullad_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mAdViewSDE = findViewById(R.id.adViewSDE);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewSDE.loadAd(adRequest);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipe= (SwipeRefreshLayout)findViewById(R.id.swipenotif);

        mAdViewSDE.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                int paddingDp = 50;
                float density = sdenotificationActivity.this.getResources().getDisplayMetrics().density;
                int paddingPixel = (int)(paddingDp * density);
                recyclerView.setPadding(0,0,0,paddingPixel);
            }
        });

        listItems = new ArrayList<>();
        oP = getSharedPreferences("SDENotification",MODE_PRIVATE);

        if(oP.getBoolean("Added",false)){
            head1 = oP.getString("Head1","");
            head2 = oP.getString("Head2","");
            head3 = oP.getString("Head3","");
            head4 = oP.getString("Head4","");
            head5 = oP.getString("Head5","");
            head6 = oP.getString("Head6","");
            head7 = oP.getString("Head7","");
            head8 = oP.getString("Head8","");
            head9 = oP.getString("Head9","");
            head10 = oP.getString("Head10","");
            link1= oP.getString("Link1","");
            link2= oP.getString("Link2","");
            link3= oP.getString("Link3","");
            link4= oP.getString("Link4","");
            link5= oP.getString("Link5","");
            link6= oP.getString("Link6","");
            link7= oP.getString("Link7","");
            link8= oP.getString("Link8","");
            link9= oP.getString("Link9","");
            link10= oP.getString("Link10","");


            ListItemSDE listItem1 = new ListItemSDE(head1, link1);
            listItems.add(listItem1);
            ListItemSDE listItem2 = new ListItemSDE(head2, link2);
            listItems.add(listItem2);
            ListItemSDE listItem3 = new ListItemSDE(head3, link3);
            listItems.add(listItem3);
            ListItemSDE listItem4 = new ListItemSDE(head4, link4);
            listItems.add(listItem4);
            ListItemSDE listItem5 = new ListItemSDE(head5, link5);
            listItems.add(listItem5);
            ListItemSDE listItem6 = new ListItemSDE(head6, link6);
            listItems.add(listItem6);
            ListItemSDE listItem7 = new ListItemSDE(head7, link7);
            listItems.add(listItem7);
            ListItemSDE listItem8 = new ListItemSDE(head8, link8);
            listItems.add(listItem8);
            ListItemSDE listItem9 = new ListItemSDE(head9, link9);
            listItems.add(listItem9);
            ListItemSDE listItem10 = new ListItemSDE(head10, link10);
            listItems.add(listItem10);
            adapter = new MyAdapterSDE(listItems, sdenotificationActivity.this);
            recyclerView.setAdapter(adapter);



        }
        else
        {
            ListItemSDE listItemf = new ListItemSDE("Notifications are shown here once they get loaded", " ");
            listItems.add(listItemf);
            adapter = new MyAdapterSDE(listItems, sdenotificationActivity.this);
            recyclerView.setAdapter(adapter);
        }



        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                StringRequest stringRequest = new StringRequest(Request.Method.GET, POST_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                swipe.setRefreshing(false);
                                listItems.clear();

                                try {
                                    JSONArray posts=new JSONArray(response);

                                    if(posts.length()!=0) {
                                        for (int i = 0; i < posts.length(); i++) {
                                            JSONObject postObject = posts.getJSONObject(i);

                                            String title = postObject.getString("title");
                                            if(title.equals(""))
                                            {
                                                title="Notification title not available. Check university website";
                                            }
                                            String link = postObject.getString("link");

                                            ListItemSDE listItem = new ListItemSDE(title, link);
                                            listItems.add(listItem);

                                            if (i == 0) {
                                                oP.edit().putBoolean("Added", true).apply();
                                                oP.edit().putString("Head1", title).apply();
                                                oP.edit().putString("Link1", link).apply();
                                            }
                                            if (i == 1) {
                                                oP.edit().putBoolean("Added", true).apply();
                                                oP.edit().putString("Head2", title).apply();
                                                oP.edit().putString("Link2", link).apply();
                                            }
                                            if (i == 2) {
                                                oP.edit().putBoolean("Added", true).apply();
                                                oP.edit().putString("Head3", title).apply();
                                                oP.edit().putString("Link3", link).apply();
                                            }
                                            if (i == 3) {
                                                oP.edit().putBoolean("Added", true).apply();
                                                oP.edit().putString("Head4", title).apply();
                                                oP.edit().putString("Link4", link).apply();
                                            }
                                            if (i == 4) {
                                                oP.edit().putBoolean("Added", true).apply();
                                                oP.edit().putString("Head5", title).apply();
                                                oP.edit().putString("Link5", link).apply();
                                            }
                                            if (i == 5) {
                                                oP.edit().putBoolean("Added", true).apply();
                                                oP.edit().putString("Head6", title).apply();
                                                oP.edit().putString("Link6", link).apply();
                                            }
                                            if (i == 6) {
                                                oP.edit().putBoolean("Added", true).apply();
                                                oP.edit().putString("Head7", title).apply();
                                                oP.edit().putString("Link7", link).apply();
                                            }
                                            if (i == 7) {
                                                oP.edit().putBoolean("Added", true).apply();
                                                oP.edit().putString("Head8", title).apply();
                                                oP.edit().putString("Link8", link).apply();
                                            }
                                            if (i == 8) {
                                                oP.edit().putBoolean("Added", true).apply();
                                                oP.edit().putString("Head9", title).apply();
                                                oP.edit().putString("Link9", link).apply();
                                            }
                                            if (i == 9) {
                                                oP.edit().putBoolean("Added", true).apply();
                                                oP.edit().putString("Head10", title).apply();
                                                oP.edit().putString("Link10", link).apply();
                                            }


                                        }

                                    }
                                    else
                                    {

                                        listItems.clear();
                                        ListItemSDE listItem1 = new ListItemSDE("No new notifications available",  "When a new notification arrives, it is displayed here");
                                        listItems.add(listItem1);
                                    }

                                    adapter = new MyAdapterSDE(listItems, sdenotificationActivity.this);
                                    recyclerView.setAdapter(adapter);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipe.setRefreshing(false);
                        Snackbar.make(findViewById(R.id.activity_sdenotification), "Connection Timeout", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                });
                Volley.newRequestQueue(sdenotificationActivity.this).add(stringRequest);
            }
        });

        LoadPosts();
    }


    public void LoadPosts() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, POST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        swipe.setRefreshing(false);
                        listItems.clear();
                        try {
                            JSONArray posts = new JSONArray(response);

                            if (posts.length() != 0) {
                                for (int i = 0; i < posts.length(); i++) {
                                    JSONObject postObject = posts.getJSONObject(i);

                                    String title = postObject.getString("title");
                                    if(title.equals(""))
                                    {
                                        title="Notification title not available. Check university website";
                                    }
                                    String link = postObject.getString("link");

                                    ListItemSDE listItem = new ListItemSDE(title, link);
                                    listItems.add(listItem);

                                    if (i == 0) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head1", title).apply();
                                        oP.edit().putString("Link1", link).apply();
                                    }
                                    if (i == 1) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head2", title).apply();
                                        oP.edit().putString("Link2", link).apply();
                                    }
                                    if (i == 2) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head3", title).apply();
                                        oP.edit().putString("Link3", link).apply();
                                    }
                                    if (i == 3) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head4", title).apply();
                                        oP.edit().putString("Link4", link).apply();
                                    }
                                    if (i == 4) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head5", title).apply();
                                        oP.edit().putString("Link5", link).apply();
                                    }
                                    if (i == 5) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head6", title).apply();
                                        oP.edit().putString("Link6", link).apply();
                                    }
                                    if (i == 6) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head7", title).apply();
                                        oP.edit().putString("Link7", link).apply();
                                    }
                                    if (i == 7) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head8", title).apply();
                                        oP.edit().putString("Link8", link).apply();
                                    }
                                    if (i == 8) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head9", title).apply();
                                        oP.edit().putString("Link9", link).apply();
                                    }
                                    if (i == 9) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head10", title).apply();
                                        oP.edit().putString("Link10", link).apply();
                                    }


                                }
                            } else {
                                listItems.clear();
                                ListItemSDE listItem1 = new ListItemSDE("No new notifications available", "When a new notification arrives, it is displayed here");
                                listItems.add(listItem1);
                            }

                            adapter = new MyAdapterSDE(listItems, sdenotificationActivity.this);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
                progressDialog.dismiss();
                Snackbar.make(findViewById(R.id.activity_sdenotification), "Connection Timeout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ListItemSDE listItem = new ListItemSDE("Connection timeout!", "Turn on your data connection and try again");
                listItems.add(listItem);
                adapter = new MyAdapterSDE(listItems, sdenotificationActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void sdeWeb(View v)
    {
        String url = "http://sdeuoc.ac.in/?q=content/notifications";
        String heading = "SDE Notifications";
        Intent intent = new Intent(sdenotificationActivity.this,browserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("heading", heading);
        startActivity(intent);
    }
    public void closeSDE(View v)
    {
        if(HomeActivity.adcount%4==0)
        {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
        finish();

    }

    @Override
    public void onBackPressed() {
        if(HomeActivity.adcount%4==0)
        {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
        finish();
    }
}
