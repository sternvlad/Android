package com.stern.Asigurare;
import android.os.AsyncTask;

public class DeleteMasinaWebService extends AsyncTask<Void, Void, Void> {
	String idMasina;
	String contUser = "";
	String contParola = ""; 
	String limba = "ro"; 
	String versiune = "";
		public DeleteMasinaWebService(String idIntern, String contUser,String contParola, String limba, String versiune) {
			// TODO Auto-generated constructor stub
			this.idMasina=idIntern;
			this.contParola = contParola;
			this.contUser = contUser;
			this.versiune = versiune;
			this.limba = limba;
		}
		@Override
		protected void onPreExecute(){  
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... arg0) {

			String xml ="<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<soap:Body>" +
    "<DeleteMasina2 xmlns='http://tempuri.org/'>" +
      "<user>vreaurca</user>" +
      "<password>123</password>" +
      "<udid>"+SplashScreen.udid+"</udid>" +
      "<id_masina>"+idMasina+"</id_masina>" +
      "<cont_user>"+contUser+"</cont_user>" +
      "<cont_parola>"+contParola+"</cont_parola>" +
      "<limba>"+limba+"</limba>" +
      "<versiune>"+versiune+"</versiune>" +
    "</DeleteMasina2>" +
  "</soap:Body>" +
"</soap:Envelope>";
			
			
			String url = GetObiecte.link +"sync.asmx";
			String soapAction = "http://tempuri.org/DeleteMasina2";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			return null;
		}

		@Override
		protected void onPostExecute(final Void unused){			
			super.onPostExecute(null);
	}
	}