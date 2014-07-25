package com.stern.Asigurare;
import android.os.AsyncTask;

public class PushNotificationsToken extends AsyncTask<Void, Void, Void> {
	String token;
		public PushNotificationsToken(String registerId) {
			// TODO Auto-generated constructor stub
			this.token=registerId;
		}
		@Override
		protected void onPreExecute(){  
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... arg0) {

			String xml ="<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<soap:Body>" +
    "<SaveDeviceToken xmlns='http://tempuri.org/'>" +
      "<user>vreaurca</user>" +
      "<password>123</password>" +
      "<udid>"+SplashScreen.udid+"</udid>" +
      "<token>"+token+"</token>" +
      "<platforma>Android</platforma>" +
    "</SaveDeviceToken>" +
  "</soap:Body>" +
"</soap:Envelope>";
			
			
			String url = GetObiecte.link + "sync.asmx";
			String soapAction = "http://tempuri.org/SaveDeviceToken";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			return null;
		}

		@Override
		protected void onPostExecute(final Void unused){			
			super.onPostExecute(null);
	}
	}