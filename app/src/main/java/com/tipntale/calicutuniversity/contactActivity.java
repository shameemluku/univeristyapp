package com.tipntale.calicutuniversity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class contactActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItemContact> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        HomeActivity.adcount = HomeActivity.adcount+1;

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.fullad_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        recyclerView = (RecyclerView)findViewById(R.id.contact1recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();

        Loadcontacs();
    }

    public void Loadcontacs()
    {
        ListItemContact listItem1 = new ListItemContact("Office", "0494-2400269");
        listItems.add(listItem1);
        ListItemContact listItem2 = new ListItemContact("Vice Chancellor", "0494-2400241");
        listItems.add(listItem2);
        ListItemContact listItem3 = new ListItemContact("Pro-Vice Chancellor", "0494-2400243");
        listItems.add(listItem3);
        ListItemContact listItem4 = new ListItemContact("Registrar", "0494-2400252");
        listItems.add(listItem4);
        ListItemContact listItem5 = new ListItemContact("Controller of Examinations", "0494-2400291");
        listItems.add(listItem5);
        ListItemContact listItem6 = new ListItemContact("Enquiry 1", "0494-2407227");
        listItems.add(listItem6);
        ListItemContact listItem7 = new ListItemContact("Enquiry 2", "0494-2400816");
        listItems.add(listItem7);
        ListItemContact listItem8 = new ListItemContact("Digital Wing 1", "0494-2407204");
        listItems.add(listItem8);
        ListItemContact listItem9 = new ListItemContact("Digital Wing 2", " 0494-2407485");
        listItems.add(listItem9);
        ListItemContact listItem10 = new ListItemContact("School of DE 1", "0494-2400288");
        listItems.add(listItem10);
        ListItemContact listItem11 = new ListItemContact("School of DE 2", " 0494-2407356");
        listItems.add(listItem11);
        ListItemContact listItem12 = new ListItemContact("School of DE 3", " 0494-2407452");
        listItems.add(listItem12);
        adapter = new MyAdapterContact(listItems, contactActivity.this);
        recyclerView.setAdapter(adapter);
    }


    public void morecontact(View v)
    {
        String url = "http://drive.google.com/viewerng/viewer?embedded=true&url=http://14.139.185.6/website/news/new39.pdf";
        String heading = "Contacts";
        Intent intent = new Intent(contactActivity.this,pdfbrowserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("heading", heading);
        startActivity(intent);

    }

    public void closeContact(View v)
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


}
