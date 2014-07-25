package com.stern.Asigurare;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FinalizareComandaCalatorie extends Activity {

	EditText edtTelefon,edtEmail,edtCI;
	Button btnComanda;
	ListView listView;
	Dialog dialog;
	StringBuilder jsonPersoane;
	YTOOferta oferta = new YTOOferta();
	ProgressBar progressBar;
	int width;
	AppSettings sett;
    TextView tvTitlu,tv_CI;
    String version;
	TextView tvContinut;
    ImageView imgTitlu;
    Button btn_nu,btn_da;
	ProgressDialog dlg;
	public String mesaj,mesajOK,adresa,telefon,email;
	public void onCreate(Bundle savedInstanceState) {
		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	width = display.getWidth(); 
    	Window window = getWindow(); 
//    	if (YTOUtils.isTablet(this)) {	    	
//	    	window.setGravity(Gravity.CENTER); 
//	    	window.setLayout(320, 480);
//    	}
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalizare_comanda_calatorie);
        
        MainController.tvTitlu.setText(getString(R.string.i449));
        
        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout2);
        Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
        sett = AppSettings.get(getParent());
        
//        for (int i=0;i<AltePersoaneCalatorie.calatori.size();i++){
//        // add text view
//        TextView tv = new TextView(this);
//        tv.setText("Serie CI/Pasaport "+AltePersoaneCalatorie.calatori.get(i).nume);
//        tv.setPadding(5, 5, 0,0);
//        if (YTOUtils.isTablet(getParent()))
//        	tv.setTextSize(20);
//        else tv.setTextSize(13);
//        ll.addView(tv);
// 
//        // add edit text
//        EditText et = new EditText(this);
//        et.setBackgroundResource(R.drawable.edit_text);
//        et.setLines(1);
//        if (YTOUtils.isTablet(getParent()))
//        	et.setTextSize(27);
//        else et.setTextSize(20);
//        et.setPadding(5, 5, 0,0);
//        et.setHint("Serie CI/Pasaport");
//        et.setId(i);
//		et.setTypeface(Typeface.create(tf,Typeface.BOLD));
//		et.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
//        ll.addView(et);
//        }
//        
//        loadCIPasaport ();
        
        if (sett.getLanguage().equals("hu")&& YTOUtils.isTablet(getParent().getParent()))
		{
			((TextView) findViewById(R.id.text_header1)).setTextSize(45);
			((TextView) findViewById(R.id.text_header2)).setTextSize(30);
			((TextView) findViewById(R.id.text_header3)).setTextSize(30);
		}
        
        if (sett.getLanguage().equals("hu"))
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_calatorie_hu);
        } else if (sett.getLanguage().equals("en"))
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_calatorie_en);
        }else 
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_calatorie);
        }
        
		if (sett.getLanguage().equals("hu")&& !YTOUtils.isTablet(getParent().getParent()))
		{
			((TextView) findViewById(R.id.text_header1)).setTextSize(16);
		}
        
    	PackageInfo pInfo;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			version = pInfo.versionName.toString();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			version = "2.2";
		}
        
        sett = AppSettings.get(this);
        sett.updateTitluGroup2(getString(R.string.i449));

        EditText edText = new EditText(getParent());
        edText .setId(1);
        edText .setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                1f));

                edText .setWidth(100);
        edText.setText("merge");
        edText .setInputType(InputType.TYPE_CLASS_NUMBER);
        edText .setMaxLines(1);
        
		edtTelefon = (EditText) findViewById (R.id.edt_telefon_contact);
		edtEmail = (EditText) findViewById (R.id.edt_email_contact);
		

		edtTelefon.setTypeface(Typeface.create(tf,Typeface.BOLD));
		edtEmail.setTypeface(Typeface.create(tf,Typeface.BOLD));
			
		btnComanda = (Button) findViewById (R.id.btn_comanda);
		btnComanda.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Boolean continua = true;
//				for (int i=0;i<AltePersoaneCalatorie.calatori.size();i++)
//				{
//					EditText et = (EditText) findViewById (i);
//					if (et.getText().toString().equals("")){
//						Toast msg = Toast.makeText(getParent(), "Introduceti seriaCI/Pasaport", Toast.LENGTH_LONG);
//						msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
//						msg.show();
//						continua=false;
//						break;
//					}
//				}
				if (continua){
					if (!isOnline())
					{
						errorDialog (getString(R.string.i450));
					}
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
					else{
						confirmDialog();
					}
				
				}
			}
		});
		
		load();

	}
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
	
	private void load()
	{
		if (!sett.getTelefonComanda().equals(""))
		{
			edtTelefon.setText(sett.getTelefonComanda());
		}
		else {
		try{
		edtTelefon.setText(AltePersoaneCalatorie.calatori.get(0).telefon);
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
		edtEmail.setText(AltePersoaneCalatorie.calatori.get(0).email);
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
		    	 telefon = edtTelefon.getText().toString();
		    	 email = edtEmail.getText().toString();
		    	 sett.updateEmailContact(email);
		    	 sett.updateTelefonComanda(telefon);
		    	 sett.updateTipProdus("Calatorie");
		    	 sett.updateMoneda("");
		    	 sett.updateNume(AltePersoaneCalatorie.calatori.get(0).nume);
		    	 sett.updateStrada(AltePersoaneCalatorie.calatori.get(0).adresa);
		    	 sett.updateLocalitate(AltePersoaneCalatorie.calatori.get(0).localitate);
		    	 sett.updateJudContact(AltePersoaneCalatorie.calatori.get(0).judet);
		    	 sett.updateIdIntern("");//id intern nu stiu ce sa pun
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
			TelephonyManager tel =(TelephonyManager)FinalizareComandaCalatorie.this.getSystemService(Context.TELEPHONY_SERVICE);
			sett.updateDeviceID(tel.getDeviceId());
			
			String toJSON="";
			DatabaseConnector dbConnector=new DatabaseConnector (FinalizareComandaCalatorie.this);
			
			oferta.tipAsigurare="Calatorie";
			oferta.idAsigurat=AltePersoaneCalatorie.calatori.get(0).idIntern;
			oferta.obiecteAsigurate="persoane";
			oferta.companie=LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getCompanie();
			oferta.cod_oferta=LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getCodOferta();
			if (sett.getOperaor().equals("Vodafone") && LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getReducere() &&  LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getIsRedusByVodafone() ){
				oferta.prima = LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getPrimaReducere();
			}else if ( LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getReducere() &&  !LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getIsRedusByVodafone() )
			{
				oferta.prima = LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getPrimaReducere();
			}
				else
			{
				oferta.prima = LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getPrima();
			}
		
			oferta.detaliiAsigurare= (sett.getTaraDestinatie().length()>8 ? sett.getTaraDestinatie().substring(0,8) : sett.getTaraDestinatie());
			oferta.moneda="RON";
			oferta.dataInceput=AsigurareCalatorie.dataInceput;
			oferta.durataAsigurare=sett.getNrZileCalatorie();
			toJSON=oferta.ofertaToJSON();
			
			jsonPersoane = new StringBuilder();
    		jsonPersoane.delete(0, jsonPersoane.length());
    		jsonPersoane.append("[");
    		for (int i=0;i<AltePersoaneCalatorie.calatori.size();i++)
	    	{
	    		String toJson=AltePersoaneCalatorie.calatori.get(i).toJSONCalator();
    			jsonPersoane.append(toJson);
	    		if (i==AltePersoaneCalatorie.calatori.size()-1)
	    			jsonPersoane.append("]");
	    		else jsonPersoane.append(",");
    		}
    		
    		String dataInceput = AsigurareCalatorie.dataInceput;
			String year = dataInceput.substring(6,dataInceput.length());
			String mm = dataInceput.substring(3,5);
			String dd = dataInceput.substring(0,2);
			dataInceput = year+"-"+mm+"-"+dd;
			
			String xml = "<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<soap:Body>" +
    "<CallInregistrareComanda3 xmlns='http://tempuri.org/'>" +
      "<user>vreaurca</user>" +
      "<password>123</password>" +
      "<oferta_prima>" + oferta.prima + "</oferta_prima>" +
      "<oferta_companie>" + oferta.companie + "</oferta_companie>" +
      "<oferta_produs>" + LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getTipProdus() + "</oferta_produs>" +
      "<oferta_cod>" + oferta.cod_oferta + "</oferta_cod>" +
      "<datainceput>" + dataInceput + "</datainceput>" +
      "<jsonPersoane>" + jsonPersoane.toString() + "</jsonPersoane>" +
      "<email>" + sett.getEmailContact() + "</email>" +
      "<telefon>" + sett.getTelefonComanda() + "</telefon>" +
      "<mod_plata>3</mod_plata>" +
      "<udid>" + sett.getDeviceId() + "</udid>" +
      "<platforma>" + "Android" + "</platforma>" +
      "<sendEmail>1</sendEmail>" +
      "<tip_emitere>1</tip_emitere>" +
      "<idReducere>"+ LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getIdReducere() +"</idReducere>" +
      "<versiune>"+version+"</versiune>"+
    "</CallInregistrareComanda3>" +
  "</soap:Body>" +
"</soap:Envelope>";
			String url = GetObiecte.link +"travel.asmx";
			String soapAction = "http://tempuri.org/CallInregistrareComanda3";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			if (resp.equals("")) {
				dlg.dismiss();
				mesaj = "Serviciul de comanda asigurare nu este disonibil momentan. Va rugam incercati mai tarziu";
			}
			else {
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();
					XMLHelperFinalizareCalatorie myXmlHelper = new XMLHelperFinalizareCalatorie();
					xr.setContentHandler(myXmlHelper);
					InputSource is = new InputSource(new StringReader(resp)); 
					xr.parse(is);
					String raspuns[] = myXmlHelper.getParsedData();
					if (raspuns[0] != null) {
						sett.updateCodRaspuns(raspuns[0]);
						mesajOK = raspuns[1];
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
				     
						tvTitlu.setText(getString(R.string.i449));
						tvContinut.setText (mesajOK);
						if (YTOUtils.isTablet(getParent()))
							tvContinut.setTextSize(25);
						else tvContinut.setTextSize(12);
						textHeader.setTextColor(getResources().getColor(R.color.verde));
					     textHeader.setText(getString(R.string.i809));
						
						btn_nu=(Button)dialog.findViewById(R.id.button_nu);
						btn_nu.setVisibility(View.GONE);
						
						btn_da=(Button)dialog.findViewById(R.id.button_da);
						btn_da.setText("OK");	
						btn_da.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							dialog.dismiss();
							sett.updateEventsUntilPrompt(0);
							sett.updateUsesUntilPrompt(0);
			               	startActivity(new Intent(FinalizareComandaCalatorie.this, Payment.class));
						}
					});
						dialog.show();
					}				     
				else 
				{
					errorDialog(mesaj);
				}
		}
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
	    // ImageView imgContinut;
	     Button btn_ok=(Button)dialog.findViewById(R.id.button_ok);
	     tvContinut = (TextView)dialog.findViewById (R.id.text_error);
	     if (mesaj==null)
	    	 tvContinut.setText (getString(R.string.i473));
	     else if (!mesaj.equals(""))  tvContinut.setText (mesaj);
	     TextView textHeader;
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     textHeader = (TextView) dialog.findViewById(R.id.text_eroare_header);
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
	     if(!isOnline())
	     {
	    	   textHeader.setTextColor(getResources().getColor(R.color.rosu_ter));
			     textHeader.setText(getString(R.string.i799));
	    	 tvContinut.setTextSize(12);
	     }
	     
	     btn_ok.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	     }
	     });

	   dialog.show();
	}

//	private void saveCIPasapot ()
//	{
//		DatabaseConnector dbConnector = new DatabaseConnector(this);
//		for (int i=0;i<AltePersoaneCalatorie.calatori.size();i++)
//		{
//			EditText et = (EditText) findViewById (i);
//			if (!et.equals(""))
//				AltePersoaneCalatorie.calatori.get(i).serieAct = et.getText().toString();
//		}
//		for (int i=0;i<AltePersoaneCalatorie.calatori.size();i++)
//		{
//			YTOPersoana calator = AltePersoaneCalatorie.calatori.get(i);
//			if (!calator.serieAct.equals("")){
//			String toJSON = calator.persoanaToJSON(calator);
//			new RegisterPersoanaWebService(calator).execute(null, null, null);
//			dbConnector.updateObiectAsigurat(calator.idIntern,
//		  			  "1",
//		  			  toJSON
//		  			  );
//			}
//		}
//		GetObiecte.getPersoane(dbConnector);
//	}
	
//	private void loadCIPasaport ()
//	{
//		for (int i=0;i<AltePersoaneCalatorie.calatori.size();i++)
//		{
//			YTOPersoana calator = AltePersoaneCalatorie.calatori.get(i);
//			EditText ed = (EditText) findViewById(i);
//			if (!calator.serieAct.equals(""))
//				ed.setText(calator.serieAct);
//		}
//	}
	
}