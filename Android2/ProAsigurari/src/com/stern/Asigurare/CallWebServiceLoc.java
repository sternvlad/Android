package com.stern.Asigurare;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;

public class CallWebServiceLoc extends AsyncTask<Integer, Integer, Boolean> {

	public static String s="";
	String denumire="",cui="",codCaen="",nume="",cnp="";
	String url=GetObiecte.link+"locuinta.asmx";
	String soapAction="http://tempuri.org/CallCalculLocuinta";
	String xml;
	String eroare="";
	String clauzaFurtBunuri="nu",clauzaApaConducta="nu",areTeren="nu",areAlarma="nu",detectieIncendiu="nu",areGrilajeGeam="nu";
	String arePaza="nu",locuitPermanent="nu",zonaIzolata="nu";
	public String mesaj="";
	public int durata=0;
	RaspunsRCA of = new RaspunsRCA();
	public static ArrayList<YTOOfertaLocuinta> oferta = null;
	final long SPLASH_MIN_MILLIS = 1 * 1000L;
	String deviceID;
	public Boolean completed=false;
	public long difftime;
	
	private long  time;
	public CallWebServiceLoc() {

		if (LocuinteleMele.locuintaCurenta.clauzaFurtBunuri.equals("da"))
			clauzaFurtBunuri="da";
		if (LocuinteleMele.locuintaCurenta.clauzaApaConducta.equals("da"))
			clauzaApaConducta="da";
		if (LocuinteleMele.locuintaCurenta.areAlarma.equals("da"))
			areAlarma="da";
		if (LocuinteleMele.locuintaCurenta.areTeren.equals("da"))
			areTeren="da";
		if (LocuinteleMele.locuintaCurenta.detectieIncendiu.equals("da"))
			detectieIncendiu="da";
		if (LocuinteleMele.locuintaCurenta.areGrilajeGeam.equals("da"))
			areGrilajeGeam="da";
		if (LocuinteleMele.locuintaCurenta.arePaza.equals("da"))
			arePaza="da";
		if (LocuinteleMele.locuintaCurenta.locuitPermanent.equals("da"))
			locuitPermanent="da";
		if (LocuinteleMele.locuintaCurenta.zonaIzolata.equals("da"))
			zonaIzolata="da";
		
		String dataInceput = AsigurareLocuinta.dataInceput;
		String year = dataInceput.substring(6,dataInceput.length());
		String mm = dataInceput.substring(3,5);
		String dd = dataInceput.substring(0,2);
		dataInceput = year+"-"+mm+"-"+dd;
		
		
		xml="<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
 "<soap:Body>" +
    "<CallCalculLocuinta xmlns='http://tempuri.org/'>" +
     "<user>vreaurca</user>" +
      "<password>123</password>" +
      "<id_intern>"+LocuinteleMele.locuintaCurenta.idIntern+"</id_intern>" +
      "<nume_asigurat>"+AltePersoane.persoanaAsigurata.nume+"</nume_asigurat>" +
      "<cod_unic>"+AltePersoane.persoanaAsigurata.codUnic+"</cod_unic>" +
      "<telefon>"+AltePersoane.persoanaAsigurata.telefon+"</telefon>" +
      "<email>"+"email@gmail.com"+"</email>" +
      "<data_inceput>"+dataInceput+"</data_inceput>" +
      "<durata_asigurare>"+"12"+"</durata_asigurare>" +
      "<moneda>"+"eur"+"</moneda>" +
      "<numar_rate>"+"1"+"</numar_rate>" +
      "<asigurat_paid>"+"neasigurat"+"</asigurat_paid>" +
      "<nr_polita_paid>"+""+"</nr_polita_paid>" +
      "<limita_paid>"+""+"</limita_paid>" +
      "<asigurat_locuinta>"+"neasigurat"+"</asigurat_locuinta>" +
      "<nr_polita_incendiu>"+""+"</nr_polita_incendiu>" +
      "<tip_persoana>"+AltePersoane.persoanaAsigurata.tipPersoana+"</tip_persoana>" +
      "<calitate_asigurat>"+"proprietar"+"</calitate_asigurat>" +
      "<pensionar>"+"nu"+"</pensionar>" +
      "<grad_handicap>"+"nu"+"</grad_handicap>" +
      "<judet>"+LocuinteleMele.locuintaCurenta.judet+"</judet>" +
      "<localitate>"+LocuinteleMele.locuintaCurenta.localitate+"</localitate>" +
      "<tip_strada>"+"Strada"+"</tip_strada>" +
      "<strada>"+LocuinteleMele.locuintaCurenta.adresa+"</strada>" +
      "<nr_strada>"+"2"+"</nr_strada>" +
      "<cod_strada>"+"021177"+"</cod_strada>" +
      "<etaj>"+LocuinteleMele.locuintaCurenta.etaj+"</etaj>" +
      "<bloc>"+"A"+"</bloc>" +
      "<scara>"+"A"+"</scara>" +
      "<apartament>"+"12"+"</apartament>" +
      "<mod_evaluare>"+LocuinteleMele.locuintaCurenta.modEvaluare+"</mod_evaluare>" +
      "<sa_locuinta>"+LocuinteleMele.locuintaCurenta.sumaAsigurata+"</sa_locuinta>" +
      "<sa_bunuri_generale>"+"0"+"</sa_bunuri_generale>" +
      "<sa_bunuri_de_valoare>"+"0"+"</sa_bunuri_de_valoare>" +
      "<sa_raspundere_civila>"+(LocuinteleMele.locuintaCurenta.sumaAsigurataRC == -1? 0 : LocuinteleMele.locuintaCurenta.sumaAsigurataRC) +"</sa_raspundere_civila>" +
      "<sa_spargere_geamuri>"+"0"+"</sa_spargere_geamuri>" +
      "<sa_centrala_termica>"+"0"+"</sa_centrala_termica>" +
      "<tip_geam>"+"termopan"+"</tip_geam>" +
      "<vechime_centrala>"+"0"+"</vechime_centrala>" +
      "<clauza_furt_bunuri>"+clauzaFurtBunuri+"</clauza_furt_bunuri>" +
      "<clauza_apa_conducta>"+clauzaApaConducta+"</clauza_apa_conducta>" +
      "<tip_cladire>"+LocuinteleMele.locuintaCurenta.tipLocuinta+"</tip_cladire>" +
      "<structura_rezistenta>"+LocuinteleMele.locuintaCurenta.structuraLocuinta+"</structura_rezistenta>" +
      "<regim_inaltime>"+(LocuinteleMele.locuintaCurenta.regimInaltime<0 ? 0 :LocuinteleMele.locuintaCurenta.regimInaltime )+"</regim_inaltime>" +
      "<nr_camere>"+LocuinteleMele.locuintaCurenta.nrCamere+"</nr_camere>" +
      "<an_constructie>"+LocuinteleMele.locuintaCurenta.anConstructie+"</an_constructie>" +
      "<suprafata_utila>"+LocuinteleMele.locuintaCurenta.suprafataUtila+"</suprafata_utila>" +
      "<nr_locatari>"+LocuinteleMele.locuintaCurenta.nrLocatari+"</nr_locatari>" +
      "<are_teren>"+areTeren+"</are_teren>" +
      "<alarma>"+areAlarma+"</alarma>" +
      "<detectie_incendiu>"+detectieIncendiu+"</detectie_incendiu>" +
      "<grilaje_geam>"+areGrilajeGeam+"</grilaje_geam>" +
      "<paza>"+arePaza+"</paza>" +
      "<locuit_permanent>"+locuitPermanent+"</locuit_permanent>" +
      "<zona_izolata>"+zonaIzolata+"</zona_izolata>" +
      "<udid>"+SplashScreen.udid+"</udid>" +
      "<platforma>"+"Android"+"</platforma>" +
    "</CallCalculLocuinta>" +
  "</soap:Body>" +
"</soap:Envelope>";
	}

	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		time = System.currentTimeMillis();
		completed=false;
	}

	@Override
	protected Boolean doInBackground(Integer... params) {
	    s=HttpHelper.callWebService( url, soapAction,xml);
	    if (s.equals("")){
	    	mesaj="Nu s-a realizat conexiunea cu serviciul de calculatie. Va rugam verificati conexiunea la Internet si reveniti!";
	    	difftime = System.currentTimeMillis() - time;
	    	completed=true;
			return false;
	    }
		else {
			try {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				XMLHelperLocuinta myXmlHelper = new XMLHelperLocuinta();
				xr.setContentHandler(myXmlHelper);
				InputSource is = new InputSource(new StringReader(s)); 
				xr.parse(is);
				StringBuilder of = null;
				of = myXmlHelper.getParsedData();
				String JSONText = of.toString();
				oferta = new  ArrayList<YTOOfertaLocuinta>();
				eroare = getEroare(JSONText);
				if (!eroare.equals("success")) {
					mesaj = eroare;
					completed=true;
				}
				else {
					Adaugare(JSONText);
					Collections.sort(oferta, new Comparator<YTOOfertaLocuinta>(){							 
						@Override
						public int compare(YTOOfertaLocuinta object1, YTOOfertaLocuinta object2) {								
			                return object1.Prima.compareTo(object2.Prima);	
						}				 
			        });		
					completed=true;
				}
			} catch (Exception ex) {						
				mesaj = "S-a produs o eroare la conexiunea cu Serviciul de calculatie! Va rugam reveniti!";
				difftime = System.currentTimeMillis() - time;
				completed=true;
			}
	    }
	    return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
	    super.onPostExecute(result);
	}
	
	protected void onProgressUpdate(Integer... values) {
        // set the current progress of the progress dialog
        super.onProgressUpdate(values);
    }
	
	public String getEroare(String JSONText)
	{
		String eroare="";
        JSONArray inputArray = null;
        try {
			inputArray = new JSONArray(JSONText);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			JSONObject jo = inputArray.getJSONObject(0);
			eroare =  jo.getString("Eroare_ws");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return eroare;
	}
	
	public void Adaugare(String JSONText) 
	{
		JSONArray inputArray = null;
        try {
			inputArray = new JSONArray(JSONText);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i=0;i<inputArray.length();i++){
		{
			YTOOfertaLocuinta of = new YTOOfertaLocuinta();
			try {
				JSONObject jo = inputArray.getJSONObject(i);
				of.Prima =  Double.parseDouble(jo.getString("Prima"));
				of.PrimaRON =  Double.parseDouble(jo.getString("PrimaRON"));
				of.Companie = jo.getString("Companie");
				of.Cod = jo.getString("Cod");
				of.Clasa_bm = jo.getString("Clasa_bm");
				of.SumaAsigurata = jo.getString("SumaAsigurata");
				of.Moneda = jo.getString("Moneda");
				of.Fransiza = jo.getString("Fransiza");
				of.TipProdus = jo.getString("TipProdus");
				of.SABunuriGenerale = jo.getString("SABunuriGenerale");
				of.SABunuriValoare = jo.getString("SABunuriValoare");
				of.SARaspundere = jo.getString("SARaspundere");
				of.RiscFurt = jo.getString("RiscFurt");
				of.RiscApaConducta = jo.getString("RiscApaConducta");
				of.AcoperireSportAgrement = jo.getString("AcoperireSportAgrement");
				of.LinkConditii = jo.getString("LinkConditii");
				of.Informatii = jo.getString("Informatii");
				of.ConditiiHint = jo.getString("ConditiiHint");
				oferta.add(of);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	}
}
	
