package com.stern.Asigurare;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


public class CallWebServiceRCA extends Activity {
	
	ImageView imageLoading;
	public static ArrayList<YTOPersoana> persoane;
	DatabaseConnector dbConnector;
	AppSettings sett;
	String contUser = "";
	String contParola = "";
	String limba = "ro";
	String versiune = "";
	public static ArrayList<Double> prima6l = new ArrayList<Double>();
	public static ArrayList<String> lista6l = new ArrayList<String>();	
	public static ArrayList<String> cod6l = new ArrayList<String>();
	public static ArrayList<String> clasa6l = new ArrayList<String>();	
	public static ArrayList<Double> prima12l = new ArrayList<Double>();
	public static ArrayList<String> lista12l = new ArrayList<String>();	
	public static ArrayList<String> cod12l = new ArrayList<String>();
	public static ArrayList<String> clasa12l = new ArrayList<String>();
	Context ctx = CallWebServiceRCA.this;
	WebServiceRCA callTarife1 = new WebServiceRCA(6,ctx);
	WebServiceRCA callTarife2 = new WebServiceRCA(12,ctx);
	final int[] imgid = { R.drawable.loading_rca1,
			R.drawable.loading_rca2, R.drawable.loading_rca3,
			R.drawable.loading_rca4 };

	 int i=0;
	
	  protected void onCreate(Bundle savedInstanceState) {
//			Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//	    	int width = display.getWidth(); 
	    	Window window = getWindow(); 
//	    	if (YTOUtils.isTablet(this)) {   	
//		    	window.setGravity(Gravity.CENTER); 
//		    	window.setLayout(320, 480);
//	    	}
	    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.loading_screenrca);
			imageLoading = (ImageView) findViewById (R.id.loading_img);	
			sett = AppSettings.get(ctx);
			limba = sett.getLanguage();
			PackageInfo pInfo = null;
			try {
				pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			versiune = pInfo.versionName;
			contUser = sett.getUsername();
			contParola = sett.getPassword();
			sett.updateRcaNrLuni(6);
			
			

			new Handler().postDelayed(new Runnable(){ 
				
			    public void run() { 
		              //Simulating a long running task
			  /* Create an Intent that will start the ProfileData-Activity. */
			    	callTarife1.execute();
			    	callTarife2.execute();
				}
			},100);
			
			updateUI();

	       	
		    }
	  
		@Override
		public void onResume()
		{
			super.onResume();
		}
	  
	  RefreshHandler refreshHandler=new RefreshHandler();
	    
	    @SuppressLint("HandlerLeak")
		class RefreshHandler extends Handler{
	        @Override
	        public void handleMessage(Message msg) {
	            // TODO Auto-generated method stub
	        	CallWebServiceRCA.this.updateUI();
	        }
	        public void sleep(long delayMillis){
	            this.removeMessages(0);
	            sendMessageDelayed(obtainMessage(0), delayMillis);
	        }
	    };
	    public void updateUI(){
	    	if (!callTarife1.completed || !callTarife2.completed){
	            refreshHandler.sleep(400);
	            if(i<imgid.length){
	                imageLoading.setImageResource(imgid[i]);
	                i++;
	                if (i>3) i=0;
	            }
	        }
	    	else if (callTarife1.mesaj.equals(""))
			{				
	    		CallWebServiceRCA.this.finish(); 
				Intent calcRCA = new Intent(getParent(), CalculatieRCA.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("CalculatieRca", calcRCA);
			} 
			else 
			{
				CallWebServiceRCA.this.finish();
				finish();
				sett.updateMesajEroare(callTarife1.mesaj);
				Intent asigRca = new Intent(getParent(), AsigurareRca.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("AsigurareRCA", asigRca);
			}
	    }

		  
		  private class WebServiceRCA extends AsyncTask<Integer, Integer, Boolean> {

				public String s="";
				String denumire="",cui="",codCaen="",nume="",cnp="";
				String url=GetObiecte.link+"rca.asmx";
				String soapAction="http://tempuri.org/CalculRca5";
				String xml;
				String dataInceput;
				public String mesaj="";
				public int durata=0;
				RaspunsRCA of = new RaspunsRCA();
				ArrayList<YTOOferta> oferta = null;
				public Boolean completed=false;
				
				public WebServiceRCA(int durata,Context ctx) {
					if (AltePersoane.persoanaAsigurata.tipPersoana.equals("juridica"))
					{
						this.durata=durata;
						denumire = AltePersoane.persoanaAsigurata.nume;
						nume=AltePersoane.persoanaAsigurata.nume;
						codCaen = AltePersoane.persoanaAsigurata.codCaen;
					}
					else 
					{
						this.durata=durata;
						denumire="";
						nume = AltePersoane.persoanaAsigurata.nume;
						codCaen =  AltePersoane.persoanaAsigurata.codCaen;
					}
					
					dataInceput = AsigurareRca.dataInceput;
					String year = dataInceput.substring(6,dataInceput.length());
					String mm = dataInceput.substring(3,5);
					String dd = dataInceput.substring(0,2);
					dataInceput = year+"-"+mm+"-"+dd;			
					
				}

				@Override
				protected void onPreExecute(){
					super.onPreExecute();
					completed=false;
				}

				@Override
				protected Boolean doInBackground(Integer... params) {
					xml="<?xml version='1.0' encoding='utf-8'?>"+
							"<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"+
								"<soap:Body>"+
								"<CalculRca5 xmlns='http://tempuri.org/'>"+
							      "<user>vreaurca</user>"+
							      "<password>123</password>"+
							      "<nume>"+nume+"</nume>"+
							      "<cnp>"+AltePersoane.persoanaAsigurata.codUnic+"</cnp>"+
							      "<telefon>"+AltePersoane.persoanaAsigurata.telefon+"</telefon>"+
							      "<email>"+AltePersoane.persoanaAsigurata.email+"</email>"+
							      "<strada>"+AltePersoane.persoanaAsigurata.adresa+"</strada>"+
							      "<judet>"+MasinileMele.autovehiculCurent.judet+"</judet>"+
							      "<marca>"+MasinileMele.autovehiculCurent.marcaAuto+"</marca>"+
							      "<model>"+MasinileMele.autovehiculCurent.modelAuto+"</model>"+
							      "<nr_inmatriculare>"+MasinileMele.autovehiculCurent.nrInmatriculare+"</nr_inmatriculare>"+
							      "<serie_sasiu>"+MasinileMele.autovehiculCurent.serieSasiu+"</serie_sasiu>"+
							      "<casco>"+MasinileMele.autovehiculCurent.cascoLa+"</casco>"+//cascola?
							      "<localitate>"+MasinileMele.autovehiculCurent.localitate+"</localitate>"+
							      "<data_permis>"+ (AltePersoane.persoanaAsigurata.dataPermis.equals("") ? "" : YTOUtils.setAnPermis(AltePersoane.persoanaAsigurata.dataPermis)) +"</data_permis>"+
							      "<marca_id>"+""+"</marca_id>"+
							      "<auto_nou>nu</auto_nou>"+
							      "<cm3>"+MasinileMele.autovehiculCurent.cm3+"</cm3>"+
							      "<kw>"+MasinileMele.autovehiculCurent.putere+"</kw>"+
							      "<combustibil>"+MasinileMele.autovehiculCurent.combustibil+"</combustibil>"+
							      "<nr_locuri>"+MasinileMele.autovehiculCurent.nrLocuri+"</nr_locuri>"+
							      "<masa_maxima>"+MasinileMele.autovehiculCurent.masaMaxima+"</masa_maxima>"+
							      "<an_fabricatie>"+MasinileMele.autovehiculCurent.anFabricatie+"</an_fabricatie>"+
							      "<destinatie_auto>"+MasinileMele.autovehiculCurent.destinatieAuto+"</destinatie_auto>"+
							      "<tip_persoana>"+AltePersoane.persoanaAsigurata.tipPersoana+"</tip_persoana>"+
							      "<cui>"+AltePersoane.persoanaAsigurata.codUnic+"</cui>"+//cui daca e pj
							      "<denumire_pj>"+denumire+"</denumire_pj>"+//denumire daca e pj
							      "<nr_daune_ultim_an>0</nr_daune_ultim_an>"+
							      "<ani_fara_daune>0</ani_fara_daune>"+
							      "<durata_asigurare>"+durata+"</durata_asigurare>"+
							      "<data_inceput_rca>"+dataInceput+"</data_inceput_rca>"+
							      "<udid>"+SplashScreen.udid+"</udid>"+
							      "<id_intern>"+MasinileMele.autovehiculCurent.idIntern+"</id_intern>"+
							      "<platforma>Android</platforma>"+
							      "<tip_inregistrare>inmatriculat</tip_inregistrare>"+
							      "<caen>"+codCaen+"</caen>"+//codCaen daca e pj
							      "<subtip_activitate>altele</subtip_activitate>"+
							      "<index_categorie_auto>"+MasinileMele.autovehiculCurent.categorieAuto+"</index_categorie_auto>"+
							      "<subcategorie_auto>"+MasinileMele.autovehiculCurent.subcategorieAuto+"</subcategorie_auto>"+
							      "<in_leasing>"+"nu"+"</in_leasing>"+
							      "<serie_civ>"+MasinileMele.autovehiculCurent.serieCiv+"</serie_civ>"+
							      "<casatorit>"+AltePersoane.persoanaAsigurata.casatorit+"</casatorit>"+
							      "<copii_minori>"+AltePersoane.persoanaAsigurata.copiiMinori+"</copii_minori>"+
							      "<pensionar>"+AltePersoane.persoanaAsigurata.pensionar+"</pensionar>"+
							      "<nr_bugetari>"+AltePersoane.persoanaAsigurata.nrBugetari+"</nr_bugetari>"+
							      "<cont_user>"+contUser+"</cont_user>"+
							      "<cont_parola>"+contParola+"</cont_parola>"+
							      "<limba>"+limba+"</limba>"+
							      "<versiune>"+versiune+"</versiune>"+
							      "</CalculRca5>"+
							  "</soap:Body>"+
							"</soap:Envelope>";
				    s=HttpHelper.callWebService( url, soapAction,xml);
				    if (s.equals("")){
				    	mesaj="Nu s-a realizat conexiunea cu serviciul de calculare a politei RCA. Va rugam verificati conexiunea la Internet si reveniti!";
				    	completed=true;
						return false;
				    }
					else {
						try {
							SAXParserFactory spf = SAXParserFactory.newInstance();
							SAXParser sp = spf.newSAXParser();
							XMLReader xr = sp.getXMLReader();
							XMLHelperRCA myXmlHelper = new XMLHelperRCA();
							xr.setContentHandler(myXmlHelper);
							InputSource is = new InputSource(new StringReader(s)); 
							xr.parse(is);
							of = new RaspunsRCA();
							oferta = new  ArrayList<YTOOferta>();					
							of = myXmlHelper.getParsedData();
							if (!(of.getEroare_ws() == null)) {
								mesaj = of.getEroare_ws();
								completed=true;
							}
							else {
								Adaugare(of);
								Collections.sort(oferta, new Comparator<YTOOferta>(){							 
									@Override
									public int compare(YTOOferta object1, YTOOferta object2) {								
						                return object1.getPrima().compareTo(object2.getPrima());								
									}				 
						        });
								if (durata==6)
								{
								prima6l.clear();
								lista6l.clear();
								cod6l.clear();
								clasa6l.clear();
								for (YTOOferta item : oferta) {							
									prima6l.add(item.getPrima());
									lista6l.add(item.getNume());
									cod6l.add(item.getCod());
									clasa6l.add(item.getClasa());
									onPostExecute(true);
								}
								completed=true;
								}
								else if (durata==12)
								{
									prima12l.clear();
									lista12l.clear();
									cod12l.clear();
									clasa12l.clear();
									for (YTOOferta item : oferta) {							
										prima12l.add(item.getPrima());
										lista12l.add(item.getNume());
										cod12l.add(item.getCod());
										clasa12l.add(item.getClasa());
										onPostExecute(true);
								}
									completed=true;
							}	
							}
						} catch (Exception ex) {						
							mesaj = "S-a produs o eroare la conexiunea cu Serviciul RCA! Va rugam verificati conexiunea la internet si reveniti!";
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
				
				public void Adaugare(RaspunsRCA obj) {
				    if (obj.getAllianz_status_response()!=null && obj.getAllianz_status_response().equals("true")) {
				    	YTOOferta aux = new YTOOferta();
				    	aux.setNume("Allianz");
				    	try {
				    		aux.setPrima(Double.parseDouble(obj.getAllianz_prima()));
					    	aux.setCod(obj.getAllianz_cod_oferta());
					    	aux.setClasa(obj.getAllianz_clasa_bm());
					    	oferta.add(aux);
				    	}
			    		catch (Exception ex) {
			    			ex.printStackTrace();
			    		} 
				    }
//				    if (obj.getArdaf_status_response().equals("true")) {
//				    	YTOOferta aux = new YTOOferta();
//				    	aux.setNume("Ardaf");
//				    	try {
//					    	aux.setPrima(Double.parseDouble(obj.getArdaf_prima()));
//					    	aux.setCod(obj.getArdaf_cod_oferta());
//					    	aux.setClasa(obj.getArdaf_clasa_bm());
//					    	oferta.add(aux);
//				    	}
//			    		catch (Exception ex) {
//			    			ex.printStackTrace();
//			    		} 
//				    }
				    if (obj.getAsirom_status_response()!=null && obj.getAsirom_status_response().equals("true")) {
				    	YTOOferta aux = new YTOOferta();
				    	aux.setNume("Asirom");
				    	try {
					    	aux.setPrima(Double.parseDouble(obj.getAsirom_prima()));
					    	aux.setCod(obj.getAsirom_cod_oferta());
					    	aux.setClasa(obj.getAsirom_clasa_bm());
				    		oferta.add(aux);
				    	}
			    		catch (Exception ex) {
			    			ex.printStackTrace();
			    		} 
			    	}

			    	if (obj.getAstra_status_response()!=null && obj.getAstra_status_response().equals("true")) {
			    		YTOOferta aux = new YTOOferta();
			    		aux.setNume("Astra");
			    		try {
				    		aux.setPrima(Double.parseDouble(obj.getAstra_prima()));
				    		aux.setCod(obj.getAstra_cod_oferta());
				    		aux.setClasa(obj.getAstra_clasa_bm());
				    		oferta.add(aux);
			    		}
			    		catch (Exception ex) {
			    			ex.printStackTrace();
			    		} 
			    	}
//			    	if (obj.getBCR_status_response().equals("true")) {
//			    		YTOOferta aux = new YTOOferta();
//			    		aux.setNume("BCR");
//			    		try {
//				    		aux.setPrima(Double.parseDouble(obj.getBCR_prima()));
//				    		aux.setCod(obj.getBCR_cod_oferta());
//				    		aux.setClasa(obj.getBCR_clasa_bm());
//				    		oferta.add(aux);
//			    		}
//			    		catch (Exception ex) {
//			    			ex.printStackTrace();
//			    		} 
//			    	}
			    	if (obj.getCarpatica_status_response()!=null && obj.getCarpatica_status_response().equals("true")) {
			    		YTOOferta aux = new YTOOferta();
			    		aux.setNume("Carpatica");
			    		try {
				    		aux.setPrima(Double.parseDouble(obj.getCarpatica_prima()));
				    		aux.setCod(obj.getCarpatica_cod_oferta());
				    		aux.setClasa(obj.getCarpatica_clasa_bm());
				    		oferta.add(aux);
				    	}
						catch (Exception ex) {
							ex.printStackTrace();
						} 
			    	}
				    if (obj.getCity_status_response()!=null && obj.getCity_status_response().equals("true")) {
				    	YTOOferta aux = new YTOOferta();
				    	aux.setNume("City");
				    	try {
					    	aux.setPrima(Double.parseDouble(obj.getCity_prima()));
					    	aux.setCod(obj.getCity_cod_oferta());
					    	aux.setClasa(obj.getCity_clasa_bm());
				    		oferta.add(aux);
				    	}
			    		catch (Exception ex) {
			    			ex.printStackTrace();
			    		} 
			    	}
			    	if (obj.getEuroins_status_response()!=null && obj.getEuroins_status_response().equals("true")) {
			    		YTOOferta aux = new YTOOferta();
			    		aux.setNume("Euroins");
			    		try {
				    		aux.setPrima(Double.parseDouble(obj.getEuroins_prima()));
				    		aux.setCod(obj.getEuroins_cod_oferta());
				    		aux.setClasa(obj.getEuroins_clasa_bm());
				    		oferta.add(aux);
			    		}
			    		catch (Exception ex) {
			    			ex.printStackTrace();
			    		} 
			    	}
			    	if (obj.getGenerali_status_response()!=null && obj.getGenerali_status_response().equals("true")) {
			    		YTOOferta aux = new YTOOferta();
			    		aux.setNume("Generali");
			    		try {
				    		aux.setPrima(Double.parseDouble(obj.getGenerali_prima()));
				    		aux.setCod(obj.getGenerali_cod_oferta());
				    		aux.setClasa(obj.getGenerali_clasa_bm());
				    		oferta.add(aux);
			    		}
			    		catch (Exception ex) {
			    			ex.printStackTrace();
			    		} 
			    	}
			    	if (obj.getGroupama_status_response()!=null && obj.getGroupama_status_response().equals("true")) {
			    		YTOOferta aux = new YTOOferta();
			    		aux.setNume("Groupama");
			    		try {
				    		aux.setPrima(Double.parseDouble(obj.getGroupama_prima()));
				    		aux.setCod(obj.getGroupama_cod_oferta());
				    		aux.setClasa(obj.getGroupama_clasa_bm());
				    		oferta.add(aux);
			    		}
			    		catch (Exception ex) {
			    			ex.printStackTrace();
			    		} 
			    	}
			    	if (obj.getOmniasig_status_response()!=null && obj.getOmniasig_status_response().equals("true")) {
			    		YTOOferta aux = new YTOOferta();
			    		aux.setNume("Omniasig");
			    		try {
				    		aux.setPrima(Double.parseDouble(obj.getOmniasig_prima()));
				    		aux.setCod(obj.getOmniasig_cod_oferta());
				    		aux.setClasa(obj.getOmniasig_clasa_bm());
				    		oferta.add(aux);
			    		}
			    		catch (Exception ex) {
			    			ex.printStackTrace();
			    		} 
			    	}
			    	if (obj.getUniqa_status_response()!=null && obj.getUniqa_status_response().equals("true")) {
			    		YTOOferta aux = new YTOOferta();
			    		aux.setNume("Uniqa");
			    		try {
			    			aux.setPrima(Double.parseDouble(obj.getUniqa_prima()));
			    			aux.setCod(obj.getUniqa_cod_oferta());
			        		aux.setClasa(obj.getUniqa_clasa_bm());
			        		oferta.add(aux);
			    		}
			    		catch (Exception ex) {
			    			ex.printStackTrace();
			    		}    		
			    	}
				 }
				

			}

}


    
   