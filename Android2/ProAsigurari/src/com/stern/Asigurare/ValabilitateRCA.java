package com.stern.Asigurare;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class ValabilitateRCA extends Activity {
	RelativeLayout alegeMasinaLay;
	TextView masinaAleasa,detaliiMasina;
	Button btnVerificaRCA;
	AppSettings sett;
	String mesaj,mesajOK=null;
    TextView tvTitlu;
	TextView tvContinut;
    ImageView imgTitlu;
    int width;
    Button btn_nu,btn_da;
	ProgressDialog dlg;
	Dialog dialog;
	ScrollView valabilitate_scroll;
    public void onCreate(Bundle savedInstanceState) {
    	Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	width = display.getWidth(); 
    	Window window = getWindow(); 
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valabilitate_rca);
        sett = AppSettings.get(this);
		Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
		
		((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface.create(th,Typeface.BOLD));
		((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface.create(th,Typeface.BOLD));
		((TextView) findViewById(R.id.text_header3)).setTypeface(Typeface.create(th,Typeface.BOLD));

        alegeMasinaLay = (RelativeLayout) findViewById (R.id.alege_masina_valabilitate_lay);
        alegeMasinaLay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MasinileMele.tipDate="Valabilitate";
				Intent masinileMele = new Intent(getParent(), MasinileMele.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("AltePersoane", masinileMele);
		        MainController.tvTitlu.setText("Lista Masini");
		        sett.updateTitluGroup4(MainController.tvTitlu.getText().toString());
			}
		});
        
        
        masinaAleasa = (TextView) findViewById (R.id.alege_masina_valabilitate);
        detaliiMasina = (TextView) findViewById (R.id.detalii_masina);
        if (MasinileMele.autovehiculCurent.isDirty && !MasinileMele.autovehiculCurent.marcaAuto.equals("")) {
        	masinaAleasa.setText(MasinileMele.autovehiculCurent.marcaAuto + " " + MasinileMele.autovehiculCurent.modelAuto );
        	detaliiMasina.setText (MasinileMele.autovehiculCurent.nrInmatriculare + ", " + MasinileMele.autovehiculCurent.serieSasiu);
        }
        
        
        valabilitate_scroll = (ScrollView) findViewById (R.id.scroll_valabilitate);
        btnVerificaRCA = (Button) findViewById (R.id.btn_verifica_rca);
        btnVerificaRCA.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!MasinileMele.autovehiculCurent.isDirty) 
				{
					masinaAleasa.setTextColor(Color.RED);
					valabilitate_scroll.fullScroll(ScrollView.FOCUS_UP);
				}
				else if (!isOnline())
				{
					errorDialog ("");
				}
				else 
				{
					new Trimite().execute(null, null, null);
				}
		}
	});

    }

    public void onResume()
	{
		super.onResume();
		 sett = AppSettings.get(this);
	     MainController.tvTitlu.setText(getString(R.string.i21));
	     sett.updateTitluGroup4(MainController.tvTitlu.getText().toString());
		if (MasinileMele.tipDate.equals("Asigurare")){
			MasinileMele.tipDate="";
			if (MasinileMele.autovehiculCurent.isDirty){
	        	masinaAleasa.setText(MasinileMele.autovehiculCurent.marcaAuto + " " + MasinileMele.autovehiculCurent.modelAuto );
	        	detaliiMasina.setText (MasinileMele.autovehiculCurent.nrInmatriculare + ", " + MasinileMele.autovehiculCurent.serieSasiu);
	        }
		}
	}
    
    private class Trimite extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute(){  
			dlg = new ProgressDialog(getParent());
			dlg.setMessage(getString(R.string.i498));
			dlg.setCancelable(false);
			dlg.show();    
		}
		@Override
		protected Void doInBackground(Void... arg0) {

			String xml ="<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<soap:Body>" +
    "<VerificaRCA xmlns='http://tempuri.org/'>" +
      "<serie>" + MasinileMele.autovehiculCurent.serieSasiu + "</serie>" +
      "<nrinmatriculare>" + MasinileMele.autovehiculCurent.nrInmatriculare + "</nrinmatriculare>" +
    "</VerificaRCA>" +
  "</soap:Body>" +
"</soap:Envelope>";
			
			
			String url = GetObiecte.link + "utils.asmx";
			String soapAction = "http://tempuri.org/VerificaRCA";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			if (resp.equals("")) {
				dlg.dismiss();
				mesaj = "Serviciul Verificare RCA nu este disonibil momentan. Va rugam incercati mai tarziu";
			}
			else {
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();
					XMLHelperValabilitateRCA myXmlHelper = new XMLHelperValabilitateRCA();
					xr.setContentHandler(myXmlHelper);
					InputSource is = new InputSource(new StringReader(resp)); 
					xr.parse(is);
					String raspuns = myXmlHelper.getParsedData();
					if (raspuns != null && !raspuns.equals("")) {
						sett.updateCodRaspuns(raspuns);
						mesajOK = raspuns;
					} else {
						dlg.dismiss();
						mesaj = "Serviciul de Verificare RCA nu este disonibil momentan. Va rugam verificati conexiunea la internet si incercati mai tarziu";
					}
				} catch (Exception ex) {
					dlg.dismiss();							
				}
			}
			return null;
		}
 
		@Override
		protected void onPostExecute(final Void unused){			
				if (dlg.isShowing() && mesajOK!=null) {
					dlg.dismiss();
					
					JSONArray inputArray = null;
					int status = 0;
					String rezultat = "";
					//String data="";
					
					try {
						inputArray = new JSONArray(mesajOK);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    try {
						JSONObject jo = inputArray.getJSONObject(0);
						
						status =  Integer.parseInt(jo.getString("status"));
						rezultat = jo.getString("mesaj");
						//data = jo.getString("data-expirare");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					dialog=new Dialog(getParent());
				     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				     dialog.setContentView(R.layout.popup_valabilitate_rca);
//				     if (YTOUtils.isTablet(getParent()))
//				    	 dialog.getWindow().setLayout(400, 580);
//				     else dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				     
				     TextView titlu = (TextView) dialog.findViewById(R.id.text_titlu_valabilitate);
				     TextView result = (TextView)dialog.findViewById(R.id.text_result);
				     switch (status){
				     	case 0:
				     		titlu.setText (getString(R.string.i501));
				     		break;
				     	case 1:
				     		titlu.setText (getString(R.string.i499));
				     		break;
				     	case 2:
				     		titlu.setText (getString(R.string.i500));
				     		break;
				     }
				     result.setText (rezultat);
				     btn_da=(Button)dialog.findViewById(R.id.button_ok);
				     btn_da.setOnClickListener(new OnClickListener() {
				     @Override
				     public void onClick(View v) {
				        dialog.dismiss();
				     }
				     });
				   dialog.show();
				     	}	
				else 
				{
					errorDialog(mesaj);
					dlg.dismiss();
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
	     ImageView imgContinut;
	     Button btn_ok=(Button)dialog.findViewById(R.id.button_ok);
	     tvContinut = (TextView)dialog.findViewById (R.id.text_error);
	     if (mesaj==null)  
	    	 tvContinut.setText (getString(R.string.i473));
	     else if (!mesaj.equals(""))  tvContinut.setText (mesaj);
	     TextView textHeader;
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     textHeader = (TextView) dialog.findViewById(R.id.text_eroare_header);
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
	     textHeader.setTextColor(getResources().getColor(R.color.rosu_ter));
		 textHeader.setText(getString(R.string.i799));
		 tvContinut.setText(getString(R.string.i473));
    	
	     if (!mesaj.equals(""))  tvContinut.setText (mesaj);
	     
	     btn_ok.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	     }
	     });

	   dialog.show();
	}

}
