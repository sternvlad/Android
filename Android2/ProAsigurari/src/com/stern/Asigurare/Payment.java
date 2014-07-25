package com.stern.Asigurare;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


public class Payment extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.webview_payment);
        
        final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(GetObiecte.link + "pre-pay.aspx" + HttpHelper.callWebServicePaymentNoPost(Payment.this)));
        startActivity(intent);
	}
	
	public void onBackPressed ()
	{
		super.onBackPressed();
		Intent intent = new Intent(Payment.this, MainController.class);
	    startActivity(intent);
	}
}