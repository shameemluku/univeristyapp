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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class timetableActivity extends AppCompatActivity {

    private String current="ALL";
    LinearLayout ALLtab,UGtab,PGtab,OTHERtab;
    Boolean AllLoaded=false,UGLoaded=false,PGLoaded=false,OTHERLoaded=false;
    TextView ALLText,UGText,PGText,OTHERText;
    ImageView ALLFilterImg,UGFilterImg,PGFilterImg,OTHRFilterImg;

    private static final String POST_URL = "https://appdatasample.000webhostapp.com/timetableall.php";
    private static final String UGPOST_URL = "https://appdatasample.000webhostapp.com/timetableug.php";
    private static final String PGPOST_URL = "https://appdatasample.000webhostapp.com/timetablepg.php";
    private static final String OTHPOST_URL = "https://appdatasample.000webhostapp.com/timetableother.php";

    private InterstitialAd mInterstitialAd;
    private AdView mAdViewTT;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItemTT> listItems;
    private List<ListItemTT> UGlistItems;
    private List<ListItemTT> PGlistItems;
    private List<ListItemTT> OTHERlistItems;

    SwipeRefreshLayout swipe;
    SharedPreferences oP;
    String head1,head2,head3,head4,head5,head6,link1,link2,link3,link4,link5,link6,date1,date2,date3,date4,date5,date6;
    int image1,image2,image3,image4,image5,image6;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        mAdViewTT = findViewById(R.id.adViewTT);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewTT.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.fullad_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        HomeActivity.adcount = HomeActivity.adcount+1;


        ALLtab = (LinearLayout)findViewById(R.id.timealltab);
        UGtab = (LinearLayout)findViewById(R.id.timeugtab);
        PGtab = (LinearLayout)findViewById(R.id.timepgtab);
        OTHERtab = (LinearLayout)findViewById(R.id.timeothertab);
        ALLText = (TextView)findViewById(R.id.tAllTextview);
        UGText = (TextView)findViewById(R.id.tUGTextview);
        PGText = (TextView)findViewById(R.id.tPGTextview);
        OTHERText = (TextView)findViewById(R.id.tOTHTextview);
        ALLFilterImg = (ImageView)findViewById(R.id.tALLFilterIcon);
        UGFilterImg = (ImageView)findViewById(R.id.tUGFilterIcon);
        PGFilterImg = (ImageView)findViewById(R.id.tPGFilterIcon);
        OTHRFilterImg = (ImageView)findViewById(R.id.tOTHRFilterIcon);


        recyclerView = (RecyclerView)findViewById(R.id.trecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipe= (SwipeRefreshLayout)findViewById(R.id.swipetime);

        mAdViewTT.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                int paddingDp = 50;
                float density = timetableActivity.this.getResources().getDisplayMetrics().density;
                int paddingPixel = (int)(paddingDp * density);
                recyclerView.setPadding(0,0,0,paddingPixel);
            }
        });

        listItems = new ArrayList<>();
        UGlistItems = new ArrayList<>();
        PGlistItems = new ArrayList<>();
        OTHERlistItems = new ArrayList<>();
        
        oP = getSharedPreferences("TTNotification",MODE_PRIVATE);

        if(oP.getBoolean("Added",false)) {
            head1 = oP.getString("Head1", "");
            head2 = oP.getString("Head2", "");
            head3 = oP.getString("Head3", "");
            head4 = oP.getString("Head4", "");
            head5 = oP.getString("Head5", "");
            head6 = oP.getString("Head6", "");
            date1 = oP.getString("Date1", "");
            date2 = oP.getString("Date2", "");
            date3 = oP.getString("Date3", "");
            date4 = oP.getString("Date4", "");
            date5 = oP.getString("Date5", "");
            date6 = oP.getString("Date6", "");
            link1 = oP.getString("Link1", "");
            link2 = oP.getString("Link2", "");
            link3 = oP.getString("Link3", "");
            link4 = oP.getString("Link4", "");
            link5 = oP.getString("Link5", "");
            link6 = oP.getString("Link6", "");
            image1 = oP.getInt("Image1", R.drawable.timelogo);
            image2 = oP.getInt("Image2", R.drawable.timelogo);
            image3 = oP.getInt("Image3", R.drawable.timelogo);
            image4 = oP.getInt("Image4", R.drawable.timelogo);
            image5 = oP.getInt("Image5", R.drawable.timelogo);
            image6 = oP.getInt("Image6", R.drawable.timelogo);

            ListItemTT listItem1 = new ListItemTT(head1, link1, date1, image1);
            listItems.add(listItem1);
            ListItemTT listItem2 = new ListItemTT(head2, link2, date2, image2);
            listItems.add(listItem2);
            ListItemTT listItem3 = new ListItemTT(head3, link3, date3, image3);
            listItems.add(listItem3);
            ListItemTT listItem4 = new ListItemTT(head4, link4, date4, image4);
            listItems.add(listItem4);
            ListItemTT listItem5 = new ListItemTT(head5, link5, date5, image5);
            listItems.add(listItem5);
            ListItemTT listItem6 = new ListItemTT(head6, link6, date6, image6);
            listItems.add(listItem6);
            adapter = new MyAdapterTT(listItems, timetableActivity.this);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            ListItemTT listItemf = new ListItemTT("Timetables are shown here once they get loaded", " ", " ",R.drawable.timelogo);
            listItems.add(listItemf);
            adapter = new MyAdapterTT(listItems, timetableActivity.this);
            recyclerView.setAdapter(adapter);
        }

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (current)
                {
                    case "ALL":
                    {
                        LoadAlltime();
                        break;
                    }
                    case "UG":
                    {
                        LoadUGtime();
                        break;
                    }
                    case "PG":
                    {
                        LoadPGtime();
                        break;
                    }
                    case "OTHER":
                    {
                        LoadOthertime();
                        break;
                    }
                }
            }
        });

        LoadAlltime();
            
    }
    public void LoadAlltime()
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
                                        title="Time table's title not available. Check university website";
                                    }
                                    String link = postObject.getString("link");
                                    String date = postObject.getString("date");

                                    int imageid;

                                    String current = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
                                    if(current.equals(date))
                                    {
                                        imageid = R.drawable.newtimelogo;

                                    }
                                    else
                                    {
                                        imageid = R.drawable.timelogo;
                                    }

                                    ListItemTT listItem = new ListItemTT(title, link, date,imageid);
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
                                ListItemTT listItem1 = new ListItemTT("No new data available", "", "When time-table data arrives, it is displayed here",R.drawable.timelogo);
                                listItems.add(listItem1);
                            }

                            adapter = new MyAdapterTT(listItems, timetableActivity.this);
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
                Snackbar.make(findViewById(R.id.activity_timetable), "Connection Timeout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                AllLoaded=true;
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }
    public void timeWeb(View v)
    {
        String url = "http://pareekshabhavan.uoc.ac.in/index.php/2016-06-16-10-20-34/2018-07-13-06-56-15";
        String heading = "Time table";
        Intent intent = new Intent(timetableActivity.this,browserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("heading", heading);
        startActivity(intent);
    }
    public void closeTime(View v)
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

    public void LoadUGtime()
    {
        ListItemTT listItemload = new ListItemTT("Please wait, Loading data..... ","","",R.drawable.loadingicon);
        UGlistItems.add(listItemload);
        adapter = new MyAdapterTT(UGlistItems,timetableActivity.this);
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
                                        title="Time-Table title not available. Check university website";
                                    }
                                    String link = postObject.getString("link");
                                    String date = postObject.getString("date");

                                    int imageid;

                                    String current = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
                                    if(current.equals(date))
                                    {
                                        imageid = R.drawable.newtimelogo;

                                    }
                                    else
                                    {
                                        imageid = R.drawable.timelogo;
                                    }

                                    ListItemTT listItem = new ListItemTT(title, link, date,imageid);
                                    UGlistItems.add(listItem);

                                }
                            }
                            else
                            {
                                UGlistItems.clear();
                                ListItemTT listItem1 = new ListItemTT("No data available", "", "When time-table data arrives, it is displayed here",R.drawable.timelogo);
                                UGlistItems.add(listItem1);
                            }

                            adapter = new MyAdapterTT(UGlistItems, timetableActivity.this);
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
                Snackbar.make(findViewById(R.id.activity_timetable), "Connection Timeout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ListItemTT listItem = new ListItemTT("Connection timeout!","","Turn on your data connection and try again",R.drawable.timeoutlogo);
                UGlistItems.add(listItem);
                adapter = new MyAdapterTT(UGlistItems,timetableActivity.this);
                recyclerView.setAdapter(adapter);
                UGLoaded=false;
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void LoadPGtime()
    {
        ListItemTT listItemload = new ListItemTT("Please wait, Loading data..... ","","",R.drawable.loadingicon);
        PGlistItems.add(listItemload);
        adapter = new MyAdapterTT(PGlistItems,timetableActivity.this);
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
                                        title="Time-Table title not available. Check university website";
                                    }
                                    String link = postObject.getString("link");
                                    String date = postObject.getString("date");

                                    int imageid;

                                    String current = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
                                    if(current.equals(date))
                                    {
                                        imageid = R.drawable.newtimelogo;

                                    }
                                    else
                                    {
                                        imageid = R.drawable.timelogo;
                                    }

                                    ListItemTT listItem = new ListItemTT(title, link, date,imageid);
                                    PGlistItems.add(listItem);

                                }
                            }
                            else
                            {
                                PGlistItems.clear();
                                ListItemTT listItem1 = new ListItemTT("No data available", "", "When time-table data arrives, it is displayed here",R.drawable.timelogo);
                                PGlistItems.add(listItem1);
                            }

                            adapter = new MyAdapterTT(PGlistItems, timetableActivity.this);
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
                Snackbar.make(findViewById(R.id.activity_timetable), "Connection Timeout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ListItemTT listItem = new ListItemTT("Connection timeout!","","Turn on your data connection and try again",R.drawable.timeoutlogo);
                PGlistItems.add(listItem);
                adapter = new MyAdapterTT(PGlistItems,timetableActivity.this);
                recyclerView.setAdapter(adapter);
                PGLoaded=false;
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void LoadOthertime()
    {
        ListItemTT listItemload = new ListItemTT("Please wait, Loading data..... ","","",R.drawable.loadingicon);
        OTHERlistItems.add(listItemload);
        adapter = new MyAdapterTT(OTHERlistItems,timetableActivity.this);
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
                                        title="Time-Table title not available. Check university website";
                                    }
                                    String link = postObject.getString("link");
                                    String date = postObject.getString("date");

                                    int imageid;

                                    String current = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
                                    if(current.equals(date))
                                    {
                                        imageid = R.drawable.newtimelogo;

                                    }
                                    else
                                    {
                                        imageid = R.drawable.timelogo;
                                    }

                                    ListItemTT listItem = new ListItemTT(title, link, date,imageid);
                                    OTHERlistItems.add(listItem);

                                }
                            }
                            else
                            {
                                OTHERlistItems.clear();
                                ListItemTT listItem1 = new ListItemTT("No data available", "", "When time-table data arrives, it is displayed here",R.drawable.timelogo);
                                OTHERlistItems.add(listItem1);
                            }

                            adapter = new MyAdapterTT(OTHERlistItems, timetableActivity.this);
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
                Snackbar.make(findViewById(R.id.activity_timetable), "Connection Timeout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ListItemTT listItem = new ListItemTT("Connection timeout!","","Turn on your data connection and try again",R.drawable.timeoutlogo);
                OTHERlistItems.add(listItem);
                adapter = new MyAdapterTT(OTHERlistItems,timetableActivity.this);
                recyclerView.setAdapter(adapter);
                OTHERLoaded=false;
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void tAllClicked(View v)
    {
        current="ALL";
        if(AllLoaded.equals(true))
        {
            adapter = new MyAdapterTT(listItems, timetableActivity.this);
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
                image1=oP.getInt("Image1",R.drawable.timelogo);
                image2=oP.getInt("Image2",R.drawable.timelogo);
                image3=oP.getInt("Image3",R.drawable.timelogo);
                image4=oP.getInt("Image4",R.drawable.timelogo);
                image5=oP.getInt("Image5",R.drawable.timelogo);
                image6=oP.getInt("Image6",R.drawable.timelogo);

                ListItemTT listItem1 = new ListItemTT(head1, link1, date1,image1);
                listItems.add(listItem1);
                ListItemTT listItem2 = new ListItemTT(head2, link2, date2,image2);
                listItems.add(listItem2);
                ListItemTT listItem3 = new ListItemTT(head3, link3, date3,image3);
                listItems.add(listItem3);
                ListItemTT listItem4 = new ListItemTT(head4, link4, date4,image4);
                listItems.add(listItem4);
                ListItemTT listItem5 = new ListItemTT(head5, link5, date5,image5);
                listItems.add(listItem5);
                ListItemTT listItem6 = new ListItemTT(head6, link6, date6,image6);
                listItems.add(listItem6);
                adapter = new MyAdapterTT(listItems, timetableActivity.this);
                recyclerView.setAdapter(adapter);



            }
            else
            {
                listItems.clear();
                ListItemTT listItemf = new ListItemTT("Time-tables are shown here once they get loaded", " ", " ",R.drawable.timelogo);
                listItems.add(listItemf);
                adapter = new MyAdapterTT(listItems, timetableActivity.this);
                recyclerView.setAdapter(adapter);
            }

            LoadAlltime();

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
    public void tUGClicked(View v)
    {
        current="UG";
        if(UGLoaded.equals(true))
        {
            adapter = new MyAdapterTT(UGlistItems, timetableActivity.this);
            recyclerView.setAdapter(adapter);

        }
        else
        {
            UGlistItems.clear();
            LoadUGtime();
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
    public void tPGClicked(View v)
    {
        current="PG";
        if(PGLoaded.equals(true))
        {
            adapter = new MyAdapterTT(PGlistItems, timetableActivity.this);
            recyclerView.setAdapter(adapter);

        }
        else
        {
            PGlistItems.clear();
            LoadPGtime();
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
    public void tOTHERClicked(View v)
    {
        current="OTHER";
        if(OTHERLoaded.equals(true))
        {
            adapter = new MyAdapterTT(OTHERlistItems, timetableActivity.this);
            recyclerView.setAdapter(adapter);

        }
        else
        {
            OTHERlistItems.clear();
            LoadOthertime();
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
