package com.stern.Asigurare;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;

public class HttpHelper {

public static String callWebService(String url, String soapAction, String envelope) {
	
	
	
	final DefaultHttpClient httpClient = new DefaultHttpClient();
	HttpParams params = httpClient.getParams();
	HttpConnectionParams.setConnectionTimeout(params, 0);
	HttpConnectionParams.setSoTimeout(params, 50000);
	HttpProtocolParams.setUseExpectContinue(httpClient.getParams(), true);	
	HttpPost httppost = new HttpPost(url);
	httppost.setHeader("soapaction", soapAction);
	httppost.setHeader("Content-Type", "text/xml; charset=utf-8");
	String responseString="";
	try {
		HttpEntity entity = new StringEntity(envelope);
		httppost.setEntity(entity);
		ResponseHandler<String> rh = new BasicResponseHandler() {
		    public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {		     
			    HttpEntity entity = response.getEntity();
			    StringBuffer out = new StringBuffer();
			    byte[] b = EntityUtils.toByteArray(entity);
			    out.append(new String(b, 0, b.length));
			    return out.toString();
		    }
	   };		   
	   responseString = httpClient.execute(httppost, rh); 
	}
	catch (Exception e) {
		Log.v("exception", e.toString());
	}
	httpClient.getConnectionManager().shutdown();
	return responseString;
}

public static String callWebServicePaymentNoPost(Context context) { 
	AppSettings settings = AppSettings.get(context);		
	String post = null;
	try {
		post = "?numar_oferta=" + settings.getCodRaspuns() +
				"&email=" + settings.getEmailContact() +
				"&nume=" + settings.getNume() +
				"&adresa=" + settings.getStrada() +					
				"&localitate=" + settings.getLocalitate() +  
				"&judet="+  settings.getJudContact() + 
				"&telefon=" + settings.getTelefonComanda() + 
				"&codProdus=" + settings.getTipProdus() + 
				"&valoare=" + settings.getPrima().replace(",", ".") + 
				"&companie=" + settings.getCompanie() +
				"&moneda="+ settings.getMoneda() +
				"&udid=" + settings.getDeviceId()+"---"+settings.getIdIntern();
	}
	catch (Exception e) {
		Log.v("exception", e.toString());
	}
	return post;
}

}