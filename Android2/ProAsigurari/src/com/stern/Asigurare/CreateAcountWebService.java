package com.stern.Asigurare;
import android.os.AsyncTask;

public class CreateAcountWebService extends AsyncTask<Void, Void, Void> {
		 		YTOPersoana persoana = new YTOPersoana();
		 		String Operator = ""; 
		 		public CreateAcountWebService(YTOPersoana persoana,String op) {
		 			// TODO Auto-generated constructor stub
		 			this.Operator = op;
		 			this.persoana=persoana;
		 		}
		 		@Override
		 		protected void onPreExecute(){  
		 			super.onPreExecute();
		 		}
		 		@Override
		 		protected Void doInBackground(Void... arg0) {

		 			String xml ="<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
		   "<soap:Body>" +
		     "<CreateAccount2 xmlns='http://tempuri.org/'>" +
		       "<user>vreaurca</user>" +
		       "<password>123</password>" +
		       "<nume>"+persoana.nume+"</nume>" +
		       "<cod_unic>"+persoana.codUnic+"</cod_unic>" +
		       "<judet>"+persoana.judet+"</judet>" +
		       "<localitate>"+persoana.localitate+"</localitate>" +
		       "<adresa>"+persoana.adresa+"</adresa>" +
		       "<telefon>"+persoana.telefon+"</telefon>" +
		       "<email>"+persoana.email+"</email>" +
		       "<udid>"+SplashScreen.udid+"</udid>" +
		       "<platforma>Android</platforma>" +
		       "<operator_tel>"+Operator+"</operator_tel> " +
		     "</CreateAccount2>" +
		   "</soap:Body>" +
		 "</soap:Envelope>";
		 			
		 			
		 			String url = GetObiecte.link+"sync.asmx";
		 			String soapAction = "http://tempuri.org/CreateAccount2";
		 			String resp = HttpHelper.callWebService( url, soapAction,xml);
		 			return null;
		 		}

		 		@Override
		 		protected void onPostExecute(final Void unused){			
		 			super.onPostExecute(null);
		 	}
		 	} 