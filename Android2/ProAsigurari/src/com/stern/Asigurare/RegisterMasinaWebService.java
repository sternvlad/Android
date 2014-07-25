package com.stern.Asigurare;
import android.os.AsyncTask;

public class RegisterMasinaWebService extends AsyncTask<Void, Void, Void> {
		YTOAutovehicul masina = new YTOAutovehicul();
		String contUser = "";
		String contParola = ""; 
		String limba = "ro"; 
		String versiune = "";
		public RegisterMasinaWebService(YTOAutovehicul masina , String contUser,String contParola, String limba, String versiune) {
			// TODO Auto-generated constructor stub
			this.masina=masina;
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
    "<RegisterMasina2 xmlns='http://tempuri.org/'>" +
      "<user>vreaurca</user>" +
      "<password>123</password>" +
      "<marca>"+masina.marcaAuto+"</marca>" +
      "<model>"+masina.modelAuto+"</model>" +
      "<index_categorie>"+masina.categorieAuto+"</index_categorie>" +
      "<subcategorie>"+masina.subcategorieAuto+"</subcategorie>" +
      "<judet>"+masina.judet+"</judet>" +
      "<localitate>"+masina.localitate+"</localitate>" +
      "<nr_inmatriculare>"+masina.nrInmatriculare+"</nr_inmatriculare>" +
      "<serie_sasiu>"+masina.serieSasiu+"</serie_sasiu>" +
      "<cm3>"+masina.cm3+"</cm3>" +
      "<putere>"+masina.putere+"</putere>" +
      "<nr_locuri>"+masina.nrLocuri+"</nr_locuri>" +
      "<masa_maxima>"+masina.masaMaxima+"</masa_maxima>" +
      "<an_fabricatie>"+masina.anFabricatie+"</an_fabricatie>" +
      "<serie_talon>"+masina.serieCiv+"</serie_talon>" +
      "<destinatie_auto>"+masina.destinatieAuto+"</destinatie_auto>" +
      "<combustibil>"+masina.combustibil+"</combustibil>" +
      "<in_leasing>"+masina.inLeasing+"</in_leasing>" +//
      "<firma_leasing>"+masina.firmaLeasing+"</firma_leasing>" +
      "<casco_la>"+masina.cascoLa+"</casco_la>" +
      "<nr_km>"+masina.nrKm+"</nr_km>" +
      "<culoare>"+masina.culoare+"</culoare>" +
      "<udid>"+SplashScreen.udid+"</udid>" +
      "<id_intern>"+masina.idIntern+"</id_intern>" +
      "<platforma>Android</platforma>" +
      "<cont_user>"+contUser+"</cont_user>" +
      "<cont_parola>"+contParola+"</cont_parola>" +
      "<limba>"+limba+"</limba>" +
      "<versiune>"+versiune+"</versiune>" +
    "</RegisterMasina2>" +
  "</soap:Body>" +
"</soap:Envelope>";
			
			
			String url = GetObiecte.link + "sync.asmx";
			String soapAction = "http://tempuri.org/RegisterMasina2";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			return null;
		}

		@Override
		protected void onPostExecute(final Void unused){			
			super.onPostExecute(null);
	}
	}