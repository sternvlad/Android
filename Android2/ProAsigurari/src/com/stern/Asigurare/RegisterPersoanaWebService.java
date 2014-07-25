package com.stern.Asigurare;
import android.os.AsyncTask;

public class RegisterPersoanaWebService extends AsyncTask<Void, Void, Void> {
		YTOPersoana persoana = new YTOPersoana();
		String contUser = "";
		String contParola = ""; 
		String limba = "ro"; 
		String versiune = "";
		public RegisterPersoanaWebService(YTOPersoana persoana , String contUser,String contParola, String limba, String versiune) {
			// TODO Auto-generated constructor stub
			this.persoana=persoana;
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
    "<RegisterPersoana2 xmlns='http://tempuri.org/'>" +
      "<user>vreaurca</user>" +
      "<password>123</password>" +
      "<nume>"+persoana.nume+"</nume>" +
      "<tip_persoana>"+persoana.tipPersoana+"</tip_persoana>" +
      "<cod_unic>"+persoana.codUnic+"</cod_unic>" +
      "<judet>"+persoana.judet+"</judet>" +
      "<localitate>"+persoana.localitate+"</localitate>" +
      "<adresa>"+persoana.adresa+"</adresa>" +
      "<telefon>"+persoana.telefon+"</telefon>" +
      "<email>"+persoana.email+"</email>" +
      "<udid>"+SplashScreen.udid+"</udid>" +
      "<id_intern>"+persoana.idIntern+"</id_intern>" +
      "<proprietar>"+persoana.proprietar+"</proprietar>" +
      "<platforma>"+"Android"+"</platforma>" +
      "<cont_user>"+contUser+"</cont_user>" +
      "<cont_parola>"+contParola+"</cont_parola>" +
      "<limba>"+limba+"</limba>" +
      "<versiune>"+versiune+"</versiune>" +
    "</RegisterPersoana2>" +
  "</soap:Body>" +
"</soap:Envelope>";
			
			
			String url = GetObiecte.link + "sync.asmx";
			String soapAction = "http://tempuri.org/RegisterPersoana2";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			return null;
		}

		@Override
		protected void onPostExecute(final Void unused){			
			super.onPostExecute(null);
	}
	}