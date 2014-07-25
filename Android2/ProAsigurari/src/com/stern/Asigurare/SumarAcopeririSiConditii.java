package com.stern.Asigurare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class SumarAcopeririSiConditii extends Activity {
	 AppSettings sett;
     WebView wv;
     static String tip="";
     ImageView header;
     public static String tipDate="";
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wv = new WebView(this);
        setContentView(wv);

        MainController.tvTitlu.setText("Sumar locuinta");
        if (tip.equals("calatorie"))
        	MainController.tvTitlu.setText("Sumar calatorie");
        
        sett= AppSettings.get(this);

        wv.getSettings().setJavaScriptEnabled(true);
        //wv.getSettings().setPluginsEnabled(true);
        final String mimeType = "text/html";
        final String encoding = "utf-8";
        if (tipDate.equals("sumar"))
        	if (tip.equals("calatorie"))
        		wv.loadDataWithBaseURL("", (LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getConditiiHint()!= null ? LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getConditiiHint().toString() : "" ), mimeType, encoding, "");
        	else
        		wv.loadDataWithBaseURL("", CallWebServiceLoc.oferta.get(CalculatieLocuinta.positionId).ConditiiHint, mimeType, encoding, "");
        else 
        	{
        	wv.setWebViewClient(new GoogleWebViewClient());
        	wv.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        	 WebSettings settings = wv.getSettings();
        	 settings.setUseWideViewPort(true);
        	 settings.setLoadWithOverviewMode(true);
        	 if (tip.equals("calatorie"))
        		 wv.loadUrl("http://docs.google.com/gview?embedded=true&url="+LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getLinkConditii());
        	 else 
        		 wv.loadUrl("http://docs.google.com/gview?embedded=true&url="+CallWebServiceLoc.oferta.get(CalculatieLocuinta.positionId).LinkConditii);
        	}
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    	setContentView(wv);
    }

}

class GoogleWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
    
    
}