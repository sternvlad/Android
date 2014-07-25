package com.stern.Asigurare;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FinalizareComandaRCA extends Activity {

	EditText edtJudLoc,edtAdresa,edtTelefon,edtEmail;
	RadioGroup radioTipPlata;
	RadioButton radioCASH,radioOP,radioCARD,radioPlata;
	Button btnComanda;
	Dialog dialog;
	YTOOferta oferta = new YTOOferta();
	ProgressBar progressBar;
	AppSettings sett;
    TextView tvTitlu;
    int width;
	TextView tvContinut;
    ImageView imgTitlu;
    Button btn_nu,btn_da;
	ProgressDialog dlg;
	String contUser = "";
	String contParola = "";
	String limba = "ro";
	String versiune = "";
	public String modPlata,judet,localitate,mesaj,mesajOK,adresa,telefon,email;
	public void onCreate(Bundle savedInstanceState) {
		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	width = display.getWidth(); 
    	Window window = getWindow(); 
//    	if (width > 800 || YTOUtils.isTablet(this)) {	    	
//	    	window.setGravity(Gravity.CENTER); 
//	    	window.setLayout(320, 480);
//    	}
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalizare_comanda);
        
        MainController.tvTitlu.setText(getString(R.string.i449));
        
        
        sett = AppSettings.get(this);
        sett.updateTitluGroup2(getString(R.string.i449));
   	
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
		
        edtJudLoc = (EditText)findViewById (R.id.edt_jud_loc_livrare);	
		edtJudLoc.setOnClickListener (new OnClickListener()
		{
		    @Override
		    public void onClick(View v)
		    {
		        	Lista.tipDate = "judete";
		        	Intent i = new Intent (FinalizareComandaRCA.this,Lista.class);
		        	startActivity (i);
		        	
		    }
		});
		
		if (sett.getLanguage().equals("hu"))
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_rca_hu);
        } else if (sett.getLanguage().equals("en"))
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_rca_en);
        }else 
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_rca);
        }
		
		if (sett.getLanguage().equals("hu")&& !YTOUtils.isTablet(getParent().getParent()))
		{
			((TextView) findViewById(R.id.text_header1)).setTextSize(16);
			((TextView) findViewById(R.id.text_header2)).setTextSize(10);
			((TextView) findViewById(R.id.text_header3)).setTextSize(10);
			((TextView) findViewById(R.id.radio_cash)).setTextSize(10);
			
		}else if (sett.getLanguage().equals("hu")&& YTOUtils.isTablet(getParent().getParent()))
		{
			((TextView) findViewById(R.id.text_header1)).setTextSize(45);
			((TextView) findViewById(R.id.text_header2)).setTextSize(25);
			((TextView) findViewById(R.id.text_header3)).setTextSize(25);
		}
		
		edtAdresa = (EditText) findViewById (R.id.edt_adresa_contact);
		edtTelefon = (EditText) findViewById (R.id.edt_telefon_contact);
		edtEmail = (EditText) findViewById (R.id.edt_email_contact);
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
		
		edtAdresa.setTypeface(Typeface.create(tf,Typeface.BOLD));
		edtTelefon.setTypeface(Typeface.create(tf,Typeface.BOLD));
		edtEmail.setTypeface(Typeface.create(tf,Typeface.BOLD));
		edtJudLoc.setTypeface(Typeface.create(tf,Typeface.BOLD));
		
		btnComanda = (Button) findViewById (R.id.btn_comanda);
		btnComanda.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!isOnline())
					errorDialog (getString(R.string.i450));
				else if (edtTelefon.getText().toString().length()==0)
				{
					Toast msg = Toast.makeText(getParent(), "Introduceti numarul de telefon", Toast.LENGTH_LONG);
					msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
					msg.show();
				}
				else if (edtTelefon.getText().toString().length()!=10)
				{
					Toast msg = Toast.makeText(getParent(), "Ati introdus gresit numarul de telefon", Toast.LENGTH_LONG);
					msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
					msg.show();
				}
				else if (edtAdresa.getText().toString().length()==0)
				{
					Toast msg = Toast.makeText(getParent(), "Introduceti adresa de livrare", Toast.LENGTH_LONG);
					msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
					msg.show();
				}
				else if (edtEmail.getText().toString().length()==0)
				{
					Toast msg = Toast.makeText(getParent(), "Introduceti adresa de email", Toast.LENGTH_LONG);
					msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
					msg.show();
				}
				else if (!YTOUtils.eMailValidation(edtEmail.getText().toString()))
				{
					Toast msg = Toast.makeText(getParent(), "Ati introdus gresti adresa de email", Toast.LENGTH_LONG);
					msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
					msg.show();
				}
				else if (edtJudLoc.getText().toString().length()==0)
				{
					Toast msg = Toast.makeText(getParent(), "Introduceti judetul si localitatea de livrare", Toast.LENGTH_LONG);
					msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
					msg.show();
				}
				else
				{
					confirmDialog();
				}
			}
		});
		
		edtJudLoc.setFocusable(false);
		
		radioTipPlata = (RadioGroup) findViewById (R.id.radio_tip_plata);
		radioCASH = (RadioButton) findViewById (R.id.radio_cash);
		radioOP = (RadioButton) findViewById (R.id.radio_op);
		radioCARD = (RadioButton) findViewById (R.id.radio_card);
		
		load();
		
	
		
	}
	

	
	public void onResume ()
	{
		super.onResume();
		if (Lista.tipDate.equals("localitati")) 
		{
			edtJudLoc.setText(Lista.date);
			judet = Lista.date.substring(0, Lista.date.indexOf(','));
			localitate = Lista.date.substring(Lista.date.indexOf(',')+ 1, Lista.date.length());
			Lista.tipDate="";
		}
	}
	
	private void load()
	{
			if (!sett.getJudContact().equals("")){
				edtJudLoc.setText (sett.getJudContact() + "," + sett.getLocalitate());
				localitate = sett.getLocalitate();
				judet = sett.getJudContact();
			}
			else{
				try{
			edtJudLoc.setText(AltePersoane.persoanaAsigurata.judet+","+AltePersoane.persoanaAsigurata.localitate);
			}
			catch (Exception e) {
				// TODO: handle exception
			}try{
			judet = AltePersoane.persoanaAsigurata.judet;
			}
			catch (Exception e) {
				// TODO: handle exception
			}try{
			localitate = AltePersoane.persoanaAsigurata.localitate;
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			}
			if (!sett.getStrada().equals("")) 
			{
				edtAdresa.setText(sett.getStrada());
			}
			else {
			try{
			edtAdresa.setText (AltePersoane.persoanaAsigurata.adresa);
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			}
			if (!sett.getTelefonComanda().equals(""))
			{
				edtTelefon.setText(sett.getTelefonComanda());
			}
			else {
			try{
			edtTelefon.setText(AltePersoane.persoanaAsigurata.telefon);
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			}
			if (!sett.getEmailContact().equals(""))
			{
				edtEmail.setText(sett.getEmailContact());
			}
			else
			{
			try{
			edtEmail.setText(AltePersoane.persoanaAsigurata.email);
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			}

	}
	
	
	public void confirmDialog () { //functie pop up
	     dialog=new Dialog(getParent());
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog);
	     
	     tvTitlu = (TextView)dialog.findViewById (R.id.text_titlu);
	     tvContinut = (TextView)dialog. findViewById (R.id.text_continut);
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     TextView textHeader = (TextView) dialog.findViewById(R.id.text_vrei_sa_stergi);
	    
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
	     
	     tvTitlu.setText(getString(R.string.i451));
	     tvContinut.setText (getString(R.string.i452));
	     textHeader.setTextColor(getResources().getColor(R.color.verde));
	     textHeader.setText(getString(R.string.i808));

	     btn_nu=(Button)dialog.findViewById(R.id.button_nu);
	     btn_nu.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	     }
	     });

	     
	     btn_da=(Button)dialog.findViewById(R.id.button_da);
	     btn_da.setOnClickListener(new OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	 adresa = edtAdresa.getText().toString();
		    	 telefon = edtTelefon.getText().toString();
		    	 email = edtEmail.getText().toString();
		    	 sett.updateStrada(adresa);
		    	 sett.updateEmailContact(email);
		    	 sett.updateTelefonComanda(telefon);
		    	 sett.updateJudContact(judet);
		    	 sett.updateLocalitate(localitate);
		    	 sett.updateTipProdus("RCA");
		    	 sett.updateMoneda ("lei");
		    	 sett.updateIdIntern(MasinileMele.autovehiculCurent.idIntern);
		    	new Trimite().execute(null, null, null);
		    	dialog.dismiss();
	            }	
		     });
	   dialog.show();
	}
	
	private class Trimite extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute(){  
			dlg = new ProgressDialog(getParent());
			dlg.setMessage(getString(R.string.i471));
			dlg.setCancelable(false);
			dlg.show();    
		}
		@Override
		protected Void doInBackground(Void... arg0) {
			Double prima = 0.0;
			String companie="",codOferta="",clasaBM="";
			if (radioCASH.isChecked()) modPlata="1";
			else if (radioOP.isChecked()) modPlata="2";
			else if (radioCARD.isChecked()) modPlata="3";
			TelephonyManager tel =(TelephonyManager)FinalizareComandaRCA.this.getSystemService(Context.TELEPHONY_SERVICE);
			if (SumarComandaRCA.durata == 6)
			{
				 prima = CallWebServiceRCA.prima6l.get(CalculatieRCA.positionId);
				 companie = CallWebServiceRCA.lista6l.get(CalculatieRCA.positionId);
				 codOferta = CallWebServiceRCA.cod6l.get(CalculatieRCA.positionId);
				 clasaBM = CallWebServiceRCA.clasa6l.get(CalculatieRCA.positionId);
			}
			else if (SumarComandaRCA.durata == 12)
			{
				 prima = CallWebServiceRCA.prima12l.get(CalculatieRCA.positionId);
				 companie = CallWebServiceRCA.lista12l.get(CalculatieRCA.positionId);
				 codOferta = CallWebServiceRCA.cod12l.get(CalculatieRCA.positionId);
				 clasaBM = CallWebServiceRCA.clasa12l.get(CalculatieRCA.positionId);
			}
			
			String toJSON="";
			DatabaseConnector dbConnector=new DatabaseConnector (FinalizareComandaRCA.this);
			
			oferta.tipAsigurare="RCA";
			oferta.idAsigurat=AltePersoane.persoanaAsigurata.idIntern;
			oferta.obiecteAsigurate="autovehicul";
			oferta.companie=companie;
			oferta.cod_oferta=codOferta;
			oferta.prima=prima;
			oferta.detaliiAsigurare=MasinileMele.autovehiculCurent.nrInmatriculare;
			oferta.moneda="RON";
			oferta.dataInceput=AsigurareRca.dataInceput;
			oferta.durataAsigurare=SumarComandaRCA.durata;
			toJSON=oferta.ofertaToJSON();
			
			String xml = "<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"+
			  "<soap:Body>" +
			"<InregistrareComandaSmartphone3 xmlns='http://tempuri.org/'>" +
			"<user>vreaurca</user>" +
			"<password>123</password>" +
			"<oferta_prima>" + prima+""+ "</oferta_prima>" +
			"<oferta_companie>" + companie + "</oferta_companie>" +
			"<oferta_cod>" + codOferta + "</oferta_cod>" +
			"<oferta_clasa_bm> Bonus/Malus: " + clasaBM + "</oferta_clasa_bm>" +
			"<livrare_adresa>" + adresa + "</livrare_adresa>" +
			"<livrare_localitate>" + localitate + "</livrare_localitate>" +
			"<livrare_judet>" + judet + "</livrare_judet>" +
			"<telefon>" + telefon + "</telefon>" +
			"<email>" + email + "</email>" +
			"<mod_plata>" + modPlata + "</mod_plata>" +
			"<udid>" + tel.getDeviceId() + "</udid>" +
			"<id_masina>" +MasinileMele.autovehiculCurent.idIntern+ "</id_masina>" +//
			"<durata>"+SumarComandaRCA.durata+"</durata>" +
			"<platforma>Android</platforma>" +
			"<cont_user>" + contUser + "</cont_user>" +
		    "<cont_parola>" + contParola + "</cont_parola>" +
		    "<limba>" + limba + "</limba>" +
		    "<versiune>" + versiune + "</versiune>" +
			"</InregistrareComandaSmartphone3>" +
			"</soap:Body>" +
			"</soap:Envelope>";
			String url = GetObiecte.link +"rca.asmx";
			String soapAction = "http://tempuri.org/InregistrareComandaSmartphone3";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			if (resp.equals("")) {
				dlg.dismiss();
				mesaj = "Serviciul de comanda RCA nu este disonibil momentan. Va rugam incercati mai tarziu";
			}
			else {
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();
					XMLHelperFinalizareRCA myXmlHelper = new XMLHelperFinalizareRCA();
					xr.setContentHandler(myXmlHelper);
					InputSource is = new InputSource(new StringReader(resp)); 
					xr.parse(is);
					String raspuns[] = myXmlHelper.getParsedData();
					if (raspuns[0] != null) {
						sett.updateCodRaspuns(raspuns[0]);
						mesajOK = "Cererea a fost trimisa. In cel mai scurt timp veti fi contactat pentru confirmarea datelor.";
				    	  dbConnector.insertObiectAsigurat(oferta.getCod(),
					        		 "4",
					        		 toJSON);
				    	  GetObiecte.getAsigurari(dbConnector);
					} else {
						dlg.dismiss();
						mesaj = raspuns[1];
					}
				} catch (Exception ex) {
					dlg.dismiss();							
				}
			}
			return null;
		}
  
		@Override
		protected void onPostExecute(final Void unused){			
				if (dlg.isShowing() && mesajOK.length() > 0) {
					dlg.dismiss();
					if (modPlata.equals("3"))
					{	
						dialog=new Dialog(getParent());
						dialog.setCancelable(false);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.dialog);
				     
						tvTitlu = (TextView)dialog.findViewById (R.id.text_titlu);
						tvContinut = (TextView)dialog. findViewById (R.id.text_continut);
						TextView textHeader = (TextView) dialog.findViewById(R.id.text_vrei_sa_stergi);
						Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
					     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
					     
					     textHeader.setTextColor(getResources().getColor(R.color.verde));
					     textHeader.setText(getString(R.string.i808));
				     
						tvTitlu.setText(getString(R.string.i470));
						tvContinut.setText (mesajOK);
						if (YTOUtils.isTablet(getParent()))
							tvContinut.setTextSize(25);
						else tvContinut.setTextSize(12);
						textHeader.setTextColor(getResources().getColor(R.color.verde));
					     textHeader.setText(getString(R.string.i809));
						
						btn_nu=(Button)dialog.findViewById(R.id.button_nu);
						btn_nu.setVisibility(View.GONE);
						
						btn_da=(Button)dialog.findViewById(R.id.button_da);
						btn_da.setText("      OK");	
						btn_da.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							dialog.dismiss();
			               	startActivity(new Intent(FinalizareComandaRCA.this, Payment.class));
						}
					});
					}				     
				     else 
				     {
						dialog.setCancelable(false);
				    	 tvTitlu.setText(getString(R.string.i470));
				    	 tvContinut.setText (mesajOK);
				    	 if (YTOUtils.isTablet(getParent()))
								tvContinut.setTextSize(25);
							else tvContinut.setTextSize(12);
				    	 TextView textHeader = (TextView) dialog.findViewById(R.id.text_vrei_sa_stergi);
				    	 Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
					     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
					     textHeader.setTextColor(getResources().getColor(R.color.verde));
					     textHeader.setText(getString(R.string.i809));
				    	 btn_nu=(Button)dialog.findViewById(R.id.button_nu);
//				    	 if (appInstalledOrNot("com.facebook.katana")){
//				    		 btn_nu.setBackgroundResource(R.drawable.buton_share_fb);
//				    		 btn_nu.setText("");
//				    	 }
				    	 btn_nu.setVisibility(View.GONE);
//				    	 Button btnTweet = (Button) dialog.findViewById(R.id.button_tweet);
//				    	 if (appInstalledOrNot("com.twitter.android"))
//				    		 btnTweet.setVisibility(View.VISIBLE);
//				    	 btnTweet.setVisibility(View.GONE);//scoate asta pt tweeter share
//				    	 btnTweet.setOnClickListener(new OnClickListener() {
//							
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
//								shareIntent.setType("text/plain");
//								shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Smart choice: am cumparat asigurarea RCA direct de pe " + YTOUtils.getDeviceName() +" ! :) http://goo.gl/D55rl");
//								PackageManager pm = v.getContext().getPackageManager();
//								List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
//								for (final ResolveInfo app : activityList) {
//								    if ("com.twitter.android.PostActivity".equals(app.activityInfo.name)) {
//								        final ActivityInfo activity = app.activityInfo;
//								        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
//								        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//								        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |             Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//								        shareIntent.setComponent(name);
//								        v.getContext().startActivity(shareIntent);
//								        break;
//								   }
//								}
//								dialog.dismiss();
//								showPopupDupaComanda();
//							}
//						});
//				    	 
				    	 btn_nu.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
								shareIntent.setType("text/plain");
								shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Smart choice: am cumparat asigurarea RCA direct de pe " + YTOUtils.getDeviceName() +" ! :) http://goo.gl/D55rl");
								PackageManager pm = v.getContext().getPackageManager();
								List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
								for (final ResolveInfo app : activityList) {
								    if ((app.activityInfo.name).contains("facebook")) {
								        final ActivityInfo activity = app.activityInfo;
								        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
								        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
								        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |             Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
								        shareIntent.setComponent(name);
								        v.getContext().startActivity(shareIntent);
								        break;
								   }
								}
								dialog.dismiss();
								showPopupDupaComanda();
							}
						});
     
				    	 btn_da=(Button)dialog.findViewById(R.id.button_da);
				    	 btn_da.setText("OK");		
				    	 btn_da.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								showPopupDupaComanda ();
							}
						});
				     	}
					dialog.show();	
				}
				else 
				{
					errorDialog(mesaj);
				}
		}
	}
	
	 private boolean appInstalledOrNot(String uri)
	    {
	        PackageManager pm = getPackageManager();
	        boolean app_installed = false;
	        try
	        {
	               pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
	               app_installed = true;
	        }
	        catch (PackageManager.NameNotFoundException e)
	        {
	               app_installed = false;
	        }
	        return app_installed ;
	}
	
	
	 
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	    	
	        return true;
	    }
	    return false;
	}
	
	public void errorDialog (String mesaj) { //eroare
	     final Dialog dialog=new Dialog(getParent());
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog_error);
	     TextView tvContinut;
	     ImageView imgContinut;
	     Button btn_ok=(Button)dialog.findViewById(R.id.button_ok);
	     tvContinut = (TextView)dialog.findViewById (R.id.text_error);
	     if (mesaj==null)
	    	 tvContinut.setText (getString (R.string.i473));
	     else if (!mesaj.equals(""))  tvContinut.setText (mesaj);
	     TextView textHeader;
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     textHeader = (TextView) dialog.findViewById(R.id.text_eroare_header);
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
	     if(!isOnline())
	     {
	    	   textHeader.setTextColor(getResources().getColor(R.color.rosu_ter));
			     textHeader.setText(getString(R.string.i799));
			     if (YTOUtils.isTablet(getParent()))
						tvContinut.setTextSize(25);
					else tvContinut.setTextSize(12);
	     }
	     
	     btn_ok.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	     }
	     });

	   dialog.show();
	}
	
	public void showPopupDupaComanda () { //dupacomanda
	     Dialog dialog = new Dialog(getParent(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//	     if (YTOUtils.isTablet(this)) 
//	    	 dialog.getWindow().setLayout(320, 480);
//	     else dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	     dialog.setContentView(R.layout.popup_dupa_comanda);
	     Button btn_ok=(Button)dialog.findViewById(R.id.button_ok);
	     btn_ok.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	    	 sett.updateEventsUntilPrompt(0);
			 sett.updateUsesUntilPrompt(0);
			Intent i = new Intent(FinalizareComandaRCA.this, MainController.class);
	    	 sett.updateTitluGroup2("Incheie Asigurare");
	    	 i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				
	     }
	     });
	     
	   dialog.show();
	}

}