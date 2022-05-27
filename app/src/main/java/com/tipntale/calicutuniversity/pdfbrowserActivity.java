package com.tipntale.calicutuniversity;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class pdfbrowserActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    SwipeRefreshLayout swipeUniv;
    WebView webView;
    String url,heading;
    TextView browserHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfbrowser);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
//        url = "https://www.jobx.online";
        heading = intent.getStringExtra("heading");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.fullad_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        browserHead = (TextView)findViewById(R.id.browserHead);

        if(heading.length()>=27) {
            browserHead.setText(heading.substring(0, 24) + "...");
        }
        else
        {
            browserHead.setText(heading);
        }

        webView = (WebView)findViewById(R.id.cuweb);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setAppCacheEnabled(true);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            webView.getSettings().setMixedContentMode(0);
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }else {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }

        swipeUniv = (SwipeRefreshLayout)findViewById(R.id.swipeUniv);
        swipeUniv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(webView.getUrl());
                swipeUniv.setRefreshing(true);
                webView.setWebViewClient(new WebViewClient()
                {
                    public void onReceivedError(WebView view,int errorCode,String description,String failingUrl)
                    {
//                        webView.loadUrl("file:///android_asset/error.html");
                    }
                    public void onPageFinished(WebView view, String url){
                        swipeUniv.setRefreshing(false);
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        swipeUniv.setRefreshing(true);
                    }
                });
            }
        });

        loadWeb(url);

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, final String userAgent, final String
                    contentDisposition, final String mimeType, long contentLength) {

                loadWeb("http://drive.google.com/viewerng/viewer?embedded=true&url="+url);
                final AlertDialog.Builder builder = new AlertDialog.Builder(pdfbrowserActivity.this);
                builder.setMessage("Do you want to download this pdf file?");
                builder.setCancelable(true);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                builder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {


                            DownloadManager.Request request = new DownloadManager.Request(
                                    Uri.parse(url));


                            request.setMimeType(mimeType);


                            String cookies = CookieManager.getInstance().getCookie(url);


                            request.addRequestHeader("cookie", cookies);


                            request.addRequestHeader("User-Agent", userAgent);


                            request.setDescription("Downloading file...");


                            request.setTitle(URLUtil.guessFileName(url, contentDisposition,
                                    mimeType));


                            request.allowScanningByMediaScanner();


                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(
                                    Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
                                            url, contentDisposition, mimeType));
                            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            dm.enqueue(request);
                            Toast.makeText(getApplicationContext(), "Downloading File",
                                    Toast.LENGTH_LONG).show();


                        }catch (Exception ex)
                        {

                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);



                        }

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });
    }

    public void loadWeb(String url)
    {
        webView.loadUrl(url);
        swipeUniv.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient()
        {
            public void onReceivedError(WebView view,int errorCode,String description,String failingUrl)
            {
//                webView.loadUrl("file:///android_asset/error.html");
            }
            public void onPageFinished(WebView view, String url){
                swipeUniv.setRefreshing(false);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                swipeUniv.setRefreshing(true);
            }
        });

    }

    public void onBackPressed(){
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            if(HomeActivity.adcount%4==0)
            {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
            finish();
        }
    }

    public void closepBrowser(View v)
    {
        if(HomeActivity.adcount%4==0)
        {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
        finish();

    }

    public void sharePost(View v)
    {
        if(heading.equals("Contacts")) {

            Toast.makeText(this,"You can't share this page",Toast.LENGTH_SHORT).show();

        }
        else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String body = heading + "\n\nLink:\nhttp://pareekshabhavan.uoc.ac.in/index.php/2016-06-16-10-20-34/2018-07-13-06-54-05\n\nDownload University App:\nhttps://play.google.com/store/apps/details?id=com.tipntale.calicutuniversity";
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent, "Share using"));
        }

    }
}
