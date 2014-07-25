package com.stern.Asigurare;
import android.os.AsyncTask;

public class RegisterLocuintaWebService extends AsyncTask<Void, Void, Void> {
		YTOLocuinta locuinta = new YTOLocuinta();
		String contUser = "";
		String contParola = ""; 
		String limba = "ro"; 
		String versiune = "";
		String clauzaFurtBunuri="nu",clauzaApaConducta="nu",areTeren="nu",areAlarma="nu",detectieIncendiu="nu",areGrilajeGeam="nu";
		String arePaza="nu",locuitPermanent="nu",zonaIzolata="nu";
		public RegisterLocuintaWebService(YTOLocuinta locuinta , String contUser,String contParola, String limba, String versiune) {
			// TODO Auto-generated constructor stub
			this.locuinta=locuinta;
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

			if (locuinta.clauzaFurtBunuri.equals("da"))
				clauzaFurtBunuri="da";
			if (locuinta.clauzaApaConducta.equals("da"))
				clauzaApaConducta="da";
			if (locuinta.areAlarma.equals("da"))
				areAlarma="da";
			if (locuinta.areTeren.equals("da"))
				areTeren="da";
			if (locuinta.detectieIncendiu.equals("da"))
				detectieIncendiu="da";
			if (locuinta.areGrilajeGeam.equals("da"))
				areGrilajeGeam="da";
			if (locuinta.arePaza.equals("da"))
				arePaza="da";
			if (locuinta.locuitPermanent.equals("da"))
				locuitPermanent="da";
			if (locuinta.zonaIzolata.equals("da"))
				zonaIzolata="da";
			
			String xml ="<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<soap:Body>" +
    "<RegisterLocuinta2 xmlns='http://tempuri.org/'>" +
      "<user>vreaurca</user>" +
      "<password>123</password>" +
      "<tip_locuinta>"+locuinta.tipLocuinta+"</tip_locuinta>" +
      "<structura>"+locuinta.structuraLocuinta+"</structura>" +
      "<inaltime>"+locuinta.regimInaltime+"</inaltime>" +
      "<etaj>"+locuinta.etaj+"</etaj>" +
      "<an_constructie>"+locuinta.anConstructie+"</an_constructie>" +
      "<nr_camere>"+locuinta.nrCamere+"</nr_camere>" +
      "<suprafata>"+locuinta.suprafataUtila+"</suprafata>" +
      "<nr_locatari>"+locuinta.nrLocatari+"</nr_locatari>" +
      "<tip_geam>"+"termopan"+"</tip_geam>" +
      "<are_alarma>"+areAlarma+"</are_alarma>" +
      "<are_grilaje_geam>"+areGrilajeGeam+"</are_grilaje_geam>" +
      "<zona_izolata>"+zonaIzolata+"</zona_izolata>" +
      "<clauza_furt_bunuri>"+clauzaFurtBunuri+"</clauza_furt_bunuri>" +
      "<clauza_apa_conducta>"+clauzaApaConducta+"</clauza_apa_conducta>" +
      "<detectie_incendiu>"+detectieIncendiu+"</detectie_incendiu>" +
      "<are_paza>"+arePaza+"</are_paza>" +
      "<are_teren>"+areTeren+"</are_teren>" +
      "<locuit_permanent>"+locuitPermanent+"</locuit_permanent>" +
      "<judet>"+locuinta.judet+"</judet>" +
      "<localitate>"+locuinta.localitate+"</localitate>" +
      "<adresa>"+locuinta.adresa+"</adresa>" +
      "<mod_evaluare>"+locuinta.modEvaluare+"</mod_evaluare>" +
      "<nr_rate>"+locuinta.nrRate+"</nr_rate>" +
      "<suma_asigurata>"+locuinta.sumaAsigurata+"</suma_asigurata>" +
      "<suma_asigurata_rc>"+locuinta.sumaAsigurataRC+"</suma_asigurata_rc>" +
      "<udid>"+SplashScreen.udid+"</udid>" +
      "<id_locuinta>"+locuinta.idIntern+"</id_locuinta>" +
      "<platforma>"+"Android"+"</platforma>" +
      "<cont_user>"+contUser+"</cont_user>" +
      "<cont_parola>"+contParola+"</cont_parola>" +
      "<limba>"+limba+"</limba>" +
      "<versiune>"+versiune+"</versiune>" +
    "</RegisterLocuinta2>" +
  "</soap:Body>" +
"</soap:Envelope>";
			
			
			String url = GetObiecte.link + "sync.asmx";
			String soapAction = "http://tempuri.org/RegisterLocuinta2";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			
			return null;
		}

		@Override
		protected void onPostExecute(final Void unused){			
			super.onPostExecute(null);
	}
	}