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
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class notificationActivity extends AppCompatActivity {

    private String current="ALL";
    LinearLayout ALLtab,UGtab,PGtab,OTHERtab;
    Boolean AllLoaded=false,UGLoaded=false,PGLoaded=false,OTHERLoaded=false;
    TextView ALLText,UGText,PGText,OTHERText;
    ImageView ALLFilterImg,UGFilterImg,PGFilterImg,OTHRFilterImg;

    private static final String POST_URL = "https://appdatasample.000webhostapp.com/cunotificationall.php";
    private static final String UGPOST_URL = "https://appdatasample.000webhostapp.com/cunotificationug.php";
    private static final String PGPOST_URL = "https://appdatasample.000webhostapp.com/cunotificationpg.php";
    private static final String OTHPOST_URL = "https://appdatasample.000webhostapp.com/cunotificationother.php";

    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;
    private List<ListItem> UGlistItems;
    private List<ListItem> PGlistItems;
    private List<ListItem> OTHERlistItems;

    SwipeRefreshLayout swipe;
    SharedPreferences oP;
    String head1,head2,head3,head4,head5,head6,link1,link2,link3,link4,link5,link6,date1,date2,date3,date4,date5,date6;
    int image1,image2,image3,image4,image5,image6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        HomeActivity.adcount = HomeActivity.adcount+1;

        ALLtab = (LinearLayout)findViewById(R.id.notialltab);
        UGtab = (LinearLayout)findViewById(R.id.notiugtab);
        PGtab = (LinearLayout)findViewById(R.id.notipgtab);
        OTHERtab = (LinearLayout)findViewById(R.id.notiothertab);
        ALLText = (TextView)findViewById(R.id.AllTextview);
        UGText = (TextView)findViewById(R.id.UGTextview);
        PGText = (TextView)findViewById(R.id.PGTextview);
        OTHERText = (TextView)findViewById(R.id.OTHTextview);
        ALLFilterImg = (ImageView)findViewById(R.id.ALLFilterIcon);
        UGFilterImg = (ImageView)findViewById(R.id.UGFilterIcon);
        PGFilterImg = (ImageView)findViewById(R.id.PGFilterIcon);
        OTHRFilterImg = (ImageView)findViewById(R.id.OTHRFilterIcon);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.fullad_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipe= (SwipeRefreshLayout)findViewById(R.id.swipenotif);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                int paddingDp = 50;
                float density = notificationActivity.this.getResources().getDisplayMetrics().density;
                int paddingPixel = (int)(paddingDp * density);
                recyclerView.setPadding(0,0,0,paddingPixel);
            }
        });

        listItems = new ArrayList<>();
        UGlistItems = new ArrayList<>();
        PGlistItems = new ArrayList<>();
        OTHERlistItems = new ArrayList<>();

        oP = getSharedPreferences("CUNotification",MODE_PRIVATE);

        if(oP.getBoolean("Added",false)){
            head1 = oP.getString("Head1","");
            head2 = oP.getString("Head2","");
            head3 = oP.getString("Head3","");
            head4 = oP.getString("Head4","");
            head5 = oP.getString("Head5","");
            head6 = oP.getString("Head6","");
            date1 = oP.getString("Date1","");
            date2 = oP.getString("Date2","");
            date3 = oP.getString("Date3","");
            date4 = oP.getString("Date4","");
            date5 = oP.getString("Date5","");
            date6 = oP.getString("Date6","");
            link1= oP.getString("Link1","");
            link2= oP.getString("Link2","");
            link3= oP.getString("Link3","");
            link4= oP.getString("Link4","");
            link5= oP.getString("Link5","");
            link6= oP.getString("Link6","");
            image1=oP.getInt("Image1",R.drawable.notilogo);
            image2=oP.getInt("Image2",R.drawable.notilogo);
            image3=oP.getInt("Image3",R.drawable.notilogo);
            image4=oP.getInt("Image4",R.drawable.notilogo);
            image5=oP.getInt("Image5",R.drawable.notilogo);
            image6=oP.getInt("Image6",R.drawable.notilogo);

            ListItem listItem1 = new ListItem(head1, link1, date1,image1);
            listItems.add(listItem1);
            ListItem listItem2 = new ListItem(head2, link2, date2,image2);
            listItems.add(listItem2);
            ListItem listItem3 = new ListItem(head3, link3, date3,image3);
            listItems.add(listItem3);
            ListItem listItem4 = new ListItem(head4, link4, date4,image4);
            listItems.add(listItem4);
            ListItem listItem5 = new ListItem(head5, link5, date5,image5);
            listItems.add(listItem5);
            ListItem listItem6 = new ListItem(head6, link6, date6,image6);
            listItems.add(listItem6);
            adapter = new MyAdapter(listItems, notificationActivity.this);
            recyclerView.setAdapter(adapter);



        }
        else
        {
            ListItem listItemf = new ListItem("Notifications are shown here once they get loaded", " ", " ",R.drawable.notilogo);
            listItems.add(listItemf);
            adapter = new MyAdapter(listItems, notificationActivity.this);
            recyclerView.setAdapter(adapter);
        }



        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                switch (current)
                {
                    case "ALL":
                    {
                        LoadAllnoti();
                        break;
                    }
                    case "UG":
                    {
                        LoadUGnoti();
                        break;
                    }
                    case "PG":
                    {
                        LoadPGnoti();
                        break;
                    }
                    case "OTHER":
                    {
                        LoadOthernoti();
                        break;
                    }
                }

            }
        });

        LoadAllnoti();

    }
    public void LoadAllnoti()
    {
        swipe.setRefreshing(true);
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
                                    String date = postObject.getString("date");

                                    int imageid;

                                    String current = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
                                    if(current.equals(date))
                                    {
                                        imageid = R.drawable.newnotilogo;

                                    }
                                    else
                                    {
                                        imageid = R.drawable.notilogo;
                                    }

                                    ListItem listItem = new ListItem(title, link, date,imageid);
                                    listItems.add(listItem);

                                    if (i == 0) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head1", title).apply();
                                        oP.edit().putString("Date1", date).apply();
                                        oP.edit().putString("Link1", link).apply();
                                        oP.edit().putInt("Image1",imageid).apply();
                                    }
                                    if (i == 1) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head2", title).apply();
                                        oP.edit().putString("Date2", date).apply();
                                        oP.edit().putString("Link2", link).apply();
                                        oP.edit().putInt("Image2",imageid).apply();
                                    }
                                    if (i == 2) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head3", title).apply();
                                        oP.edit().putString("Date3", date).apply();
                                        oP.edit().putString("Link3", link).apply();
                                        oP.edit().putInt("Image3",imageid).apply();
                                    }
                                    if (i == 3) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head4", title).apply();
                                        oP.edit().putString("Date4", date).apply();
                                        oP.edit().putString("Link4", link).apply();
                                        oP.edit().putInt("Image4",imageid).apply();
                                    }
                                    if (i == 4) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head5", title).apply();
                                        oP.edit().putString("Date5", date).apply();
                                        oP.edit().putString("Link5", link).apply();
                                        oP.edit().putInt("Image5",imageid).apply();
                                    }
                                    if (i == 5) {
                                        oP.edit().putBoolean("Added", true).apply();
                                        oP.edit().putString("Head6", title).apply();
                                        oP.edit().putString("Date6", date).apply();
                                        oP.edit().putString("Link6", link).apply();
                                        oP.edit().putInt("Image6",imageid).apply();
                                    }


                                }
                            }
                            else
                            {
                                listItems.clear();
                                ListItem listItem1 = new ListItem("No new notifications available", "", "When a new notification arrives, it is displayed here",R.drawable.notilogo);
                                listItems.add(listItem1);
                            }

                            adapter = new MyAdapter(listItems, notificationActivity.this);
                            recyclerView.setAdapter(adapter);
                            AllLoaded=true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
                Snackbar.make(findViewById(R.id.activity_notification), "Connection Timeout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                AllLoaded=false;
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }


    public void notiWeb(View v)
    {
        String url = "https://pareekshabhavan.uoc.ac.in/index.php/examination/notifications";
        String heading = "Notifications";
        Intent intent = new Intent(notificationActivity.this,browserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("heading", heading);
        startActivity(intent);
    }
    public void closeNoti(View v)
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


    public void LoadUGnoti()
    {
        ListItem listItemload = new ListItem("Please wait, Loading data..... ","","",R.drawable.loadingicon);
        UGlistItems.add(listItemload);
        adapter = new MyAdapter(UGlistItems,notificationActivity.this);
        recyclerView.setAdapter(adapter);

        swipe.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UGPOST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipe.setRefreshing(false);
                        UGlistItems.clear();
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
                                    String date = postObject.getString("date");

                                    int imageid;

                                    String current = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
                                    if(current.equals(date))
                                    {
                                        imageid = R.drawable.newnotilogo;

                                    }
                                    else
                                    {
                                        imageid = R.drawable.notilogo;
                                    }

                                    ListItem listItem = new ListItem(title, link, date,imageid);
                                    UGlistItems.add(listItem);

                                }
                            }
                            else
                            {
                                UGlistItems.clear();
                                ListItem listItem1 = new ListItem("No new notifications available", "", "When a new notification arrives, it is displayed here",R.drawable.notilogo);
                                UGlistItems.add(listItem1);
                            }

                            adapter = new MyAdapter(UGlistItems, notificationActivity.this);
                            recyclerView.setAdapter(adapter);
                            UGLoaded = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
                UGlistItems.clear();
                Snackbar.make(findViewById(R.id.activity_notification), "Connection Timeout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ListItem listItem = new ListItem("Connection timeout!","","Turn on your data connection and try again",R.drawable.timeoutlogo);
                UGlistItems.add(listItem);
                adapter = new MyAdapter(UGlistItems,notificationActivity.this);
                recyclerView.setAdapter(adapter);
                UGLoaded=false;
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void LoadPGnoti()
    {
        ListItem listItemload = new ListItem("Please wait, Loading data..... ","","",R.drawable.loadingicon);
        PGlistItems.add(listItemload);
        adapter = new MyAdapter(PGlistItems,notificationActivity.this);
        recyclerView.setAdapter(adapter);

        swipe.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PGPOST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipe.setRefreshing(false);
                        PGlistItems.clear();
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
                                    String date = postObject.getString("date");

                                    int imageid;

                                    String current = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
                                    if(current.equals(date))
                                    {
                                        imageid = R.drawable.newnotilogo;

                                    }
                                    else
                                    {
                                        imageid = R.drawable.notilogo;
                                    }

                                    ListItem listItem = new ListItem(title, link, date,imageid);
                                    PGlistItems.add(listItem);

                                }
                            }
                            else
                            {
                                PGlistItems.clear();
                                ListItem listItem1 = new ListItem("No new notifications available", "", "When a new notification arrives, it is displayed here",R.drawable.notilogo);
                                PGlistItems.add(listItem1);
                            }

                            adapter = new MyAdapter(PGlistItems, notificationActivity.this);
                            recyclerView.setAdapter(adapter);
                            PGLoaded = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
                PGlistItems.clear();
                Snackbar.make(findViewById(R.id.activity_notification), "Connection Timeout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ListItem listItem = new ListItem("Connection timeout!","","Turn on your data connection and try again",R.drawable.timeoutlogo);
                PGlistItems.add(listItem);
                adapter = new MyAdapter(PGlistItems,notificationActivity.this);
                recyclerView.setAdapter(adapter);
                PGLoaded=false;
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void LoadOthernoti()
    {
        ListItem listItemload = new ListItem("Please wait, Loading data..... ","","",R.drawable.loadingicon);
        OTHERlistItems.add(listItemload);
        adapter = new MyAdapter(OTHERlistItems,notificationActivity.this);
        recyclerView.setAdapter(adapter);

        swipe.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, OTHPOST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipe.setRefreshing(false);
                        OTHERlistItems.clear();
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
                                    String date = postObject.getString("date");

                                    int imageid;

                                    String current = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
                                    if(current.equals(date))
                                    {
                                        imageid = R.drawable.newnotilogo;

                                    }
                                    else
                                    {
                                        imageid = R.drawable.notilogo;
                                    }

                                    ListItem listItem = new ListItem(title, link, date,imageid);
                                    OTHERlistItems.add(listItem);

                                }
                            }
                            else
                            {
                                OTHERlistItems.clear();
                                ListItem listItem1 = new ListItem("No new notifications available", "", "When a new notification arrives, it is displayed here",R.drawable.notilogo);
                                OTHERlistItems.add(listItem1);
                            }

                            adapter = new MyAdapter(OTHERlistItems, notificationActivity.this);
                            recyclerView.setAdapter(adapter);
                            OTHERLoaded = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
                OTHERlistItems.clear();
                Snackbar.make(findViewById(R.id.activity_notification), "Connection Timeout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ListItem listItem = new ListItem("Connection timeout!","","Turn on your data connection and try again",R.drawable.timeoutlogo);
                OTHERlistItems.add(listItem);
                adapter = new MyAdapter(OTHERlistItems,notificationActivity.this);
                recyclerView.setAdapter(adapter);
                OTHERLoaded=false;
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }


    public void AllClicked(View v)
    {
        current="ALL";
        if(AllLoaded.equals(true))
        {
            adapter = new MyAdapter(listItems, notificationActivity.this);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            listItems.clear();
            if(oP.getBoolean("Added",false)){
                head1 = oP.getString("Head1","");
                head2 = oP.getString("Head2","");
                head3 = oP.getString("Head3","");
                head4 = oP.getString("Head4","");
                head5 = oP.getString("Head5","");
                head6 = oP.getString("Head6","");
                date1 = oP.getString("Date1","");
                date2 = oP.getString("Date2","");
                date3 = oP.getString("Date3","");
                date4 = oP.getString("Date4","");
                date5 = oP.getString("Date5","");
                date6 = oP.getString("Date6","");
                link1= oP.getString("Link1","");
                link2= oP.getString("Link2","");
                link3= oP.getString("Link3","");
                link4= oP.getString("Link4","");
                link5= oP.getString("Link5","");
                link6= oP.getString("Link6","");
                image1=oP.getInt("Image1",R.drawable.notilogo);
                image2=oP.getInt("Image2",R.drawable.notilogo);
                image3=oP.getInt("Image3",R.drawable.notilogo);
                image4=oP.getInt("Image4",R.drawable.notilogo);
                image5=oP.getInt("Image5",R.drawable.notilogo);
                image6=oP.getInt("Image6",R.drawable.notilogo);

                ListItem listItem1 = new ListItem(head1, link1, date1,image1);
                listItems.add(listItem1);
                ListItem listItem2 = new ListItem(head2, link2, date2,image2);
                listItems.add(listItem2);
                ListItem listItem3 = new ListItem(head3, link3, date3,image3);
                listItems.add(listItem3);
                ListItem listItem4 = new ListItem(head4, link4, date4,image4);
                listItems.add(listItem4);
                ListItem listItem5 = new ListItem(head5, link5, date5,image5);
                listItems.add(listItem5);
                ListItem listItem6 = new ListItem(head6, link6, date6,image6);
                listItems.add(listItem6);
                adapter = new MyAdapter(listItems, notificationActivity.this);
                recyclerView.setAdapter(adapter);



            }
            else
            {
                listItems.clear();
                ListItem listItemf = new ListItem("Notifications are shown here once they get loaded", " ", " ",R.drawable.notilogo);
                listItems.add(listItemf);
                adapter = new MyAdapter(listItems, notificationActivity.this);
                recyclerView.setAdapter(adapter);
            }

            LoadAllnoti();

        }
        ALLtab.setBackgroundResource(R.drawable.filterbutton);
        UGtab.setBackgroundResource(R.drawable.filterbuttonunsel);
        PGtab.setBackgroundResource(R.drawable.filterbuttonunsel);
        OTHERtab.setBackgroundResource(R.drawable.filterbuttonunsel);

        ALLText.setTextColor(getResources().getColor(R.color.white));
        UGText.setTextColor(getResources().getColor(R.color.darkGray));
        PGText.setTextColor(getResources().getColor(R.color.darkGray));
        OTHERText.setTextColor(getResources().getColor(R.color.darkGray));

        ALLFilterImg.setBackgroundResource(R.drawable.filtericonselect);
        UGFilterImg.setBackgroundResource(R.drawable.filtericonunselect);
        PGFilterImg.setBackgroundResource(R.drawable.filtericonunselect);
        OTHRFilterImg.setBackgroundResource(R.drawable.filtericonunselect);

    }
    public void UGClicked(View v)
    {
        current="UG";
        if(UGLoaded.equals(true))
        {
            adapter = new MyAdapter(UGlistItems, notificationActivity.this);
            recyclerView.setAdapter(adapter);

        }
        else
        {
            UGlistItems.clear();
            LoadUGnoti();
        }
        UGtab.setBackgroundResource(R.drawable.filterbutton);
        ALLtab.setBackgroundResource(R.drawable.filterbuttonunsel);
        PGtab.setBackgroundResource(R.drawable.filterbuttonunsel);
        OTHERtab.setBackgroundResource(R.drawable.filterbuttonunsel);

        UGText.setTextColor(getResources().getColor(R.color.white));
        ALLText.setTextColor(getResources().getColor(R.color.darkGray));
        PGText.setTextColor(getResources().getColor(R.color.darkGray));
        OTHERText.setTextColor(getResources().getColor(R.color.darkGray));

        UGFilterImg.setBackgroundResource(R.drawable.filtericonselect);
        ALLFilterImg.setBackgroundResource(R.drawable.filtericonunselect);
        PGFilterImg.setBackgroundResource(R.drawable.filtericonunselect);
        OTHRFilterImg.setBackgroundResource(R.drawable.filtericonunselect);

    }
    public void PGClicked(View v)
    {
        current="PG";
        if(PGLoaded.equals(true))
        {
            adapter = new MyAdapter(PGlistItems, notificationActivity.this);
            recyclerView.setAdapter(adapter);

        }
        else
        {
            PGlistItems.clear();
            LoadPGnoti();
        }
        PGtab.setBackgroundResource(R.drawable.filterbutton);
        ALLtab.setBackgroundResource(R.drawable.filterbuttonunsel);
        UGtab.setBackgroundResource(R.drawable.filterbuttonunsel);
        OTHERtab.setBackgroundResource(R.drawable.filterbuttonunsel);

        PGText.setTextColor(getResources().getColor(R.color.white));
        ALLText.setTextColor(getResources().getColor(R.color.darkGray));
        UGText.setTextColor(getResources().getColor(R.color.darkGray));
        OTHERText.setTextColor(getResources().getColor(R.color.darkGray));

        PGFilterImg.setBackgroundResource(R.drawable.filtericonselect);
        ALLFilterImg.setBackgroundResource(R.drawable.filtericonunselect);
        UGFilterImg.setBackgroundResource(R.drawable.filtericonunselect);
        OTHRFilterImg.setBackgroundResource(R.drawable.filtericonunselect);

    }
    public void OTHERClicked(View v)
    {
        current="OTHER";
        if(OTHERLoaded.equals(true))
        {
            adapter = new MyAdapter(OTHERlistItems, notificationActivity.this);
            recyclerView.setAdapter(adapter);

        }
        else
        {
            OTHERlistItems.clear();
            LoadOthernoti();
        }
        OTHERtab.setBackgroundResource(R.drawable.filterbutton);
        ALLtab.setBackgroundResource(R.drawable.filterbuttonunsel);
        UGtab.setBackgroundResource(R.drawable.filterbuttonunsel);
        PGtab.setBackgroundResource(R.drawable.filterbuttonunsel);

        OTHERText.setTextColor(getResources().getColor(R.color.white));
        ALLText.setTextColor(getResources().getColor(R.color.darkGray));
        UGText.setTextColor(getResources().getColor(R.color.darkGray));
        PGText.setTextColor(getResources().getColor(R.color.darkGray));

        OTHRFilterImg.setBackgroundResource(R.drawable.filtericonselect);
        ALLFilterImg.setBackgroundResource(R.drawable.filtericonunselect);
        UGFilterImg.setBackgroundResource(R.drawable.filtericonunselect);
        PGFilterImg.setBackgroundResource(R.drawable.filtericonunselect);

    }

}
