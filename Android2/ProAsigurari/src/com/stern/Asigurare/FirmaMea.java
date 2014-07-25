package com.stern.Asigurare;

import java.io.StringReader;
import java.util.UUID;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class FirmaMea extends Activity {
		 
	public static EditText edtNumeFirma,edtCuiFirma,edtJudLocFirma,edtAdresaFirma,edtTelefonFirma,edtEmailFirma;
	Button btn_renunta,btn_salveaza;
	public static ProgressDialog dlg;
	AppSettings sett;
	StringBuilder mesajOK=null;
	String contUser = "";
	String contParola = "";
	String limba = "ro";
	String versiune = "";
	String mesaj;
	String judet="",localitate="";
	Boolean checkcui = true;
	YTOPersoana persoanaJur = new YTOPersoana ();
	static Boolean salveaza=true;;


	
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.firma_mea);
			
			sett = AppSettings.get(this);
			
			edtNumeFirma = (EditText)findViewById (R.id.edtNumeFirma);
			edtNumeFirma.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			        	
			          ContulMeu.tooltip.setVisibility (View.VISIBLE);
			          ContulMeu.tvTooltip.setText (getString(R.string.i254));
			        }else{
			        	edtNumeFirma.setText(YTOUtils.replaceDiacritice(edtNumeFirma.getText().toString()));
			        		ContulMeu.tvTooltip.setText ("");
			        	 ContulMeu.tooltip.setVisibility (View.GONE); 
			        }
			    }
			});
			edtCuiFirma = (EditText)findViewById (R.id.edtCUIFirma);
			edtCuiFirma.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          ContulMeu.tooltip.setVisibility (View.VISIBLE);
			          ContulMeu.tvTooltip.setText (getString(R.string.i256));
			          if (!YTOUtils.isTablet(getParent().getParent()))
			        	  ContulMeu.tvTooltip.setTextSize(13);
			        }else{
			        		ContulMeu.tvTooltip.setText ("");
			        	 ContulMeu.tooltip.setVisibility (View.GONE);
			        	 
			        	 
			        	 if (YTOUtils.verifyCUI(edtCuiFirma.getText().toString())){
			        	 if (!persoanaJur.isDirty){
			        		 new Trimite().execute(null, null, null);
			        	 }
			        	 }
			        }
			    }
			});
			
			edtCuiFirma.addTextChangedListener(new TextWatcher() {
	        	 
	            @Override
	            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	                // When user changed the Text
	            }
	 
	            @Override
	            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                    int arg3) {
	                // TODO Auto-generated method stub
	 
	            }
	 
	            @Override
	            public void afterTextChanged(Editable arg0) {
			    	if (!YTOUtils.verifyCUI(edtCuiFirma.getText().toString()))
		        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
		        	 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
	            }
	        });
			
			
			
			edtJudLocFirma = (EditText)findViewById (R.id.edtJudLocFirma);
			edtJudLocFirma.setOnClickListener (new OnClickListener()
			{
			    @Override
			    public void onClick(View v)
			    {
			    	 if (YTOUtils.verifyCUI(edtCuiFirma.getText().toString()) && !persoanaJur.isDirty && checkcui){
			        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
			        		 new Trimite().execute(null, null, null);
			        		 checkcui=false;
			        	 }else {
			        		 if (!YTOUtils.verifyCUI(edtCuiFirma.getText().toString()))
			        			 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
			    		salveaza=false;
			    		Lista.tipDate = "judete";
			        	Intent i = new Intent (FirmaMea.this,Lista.class);
			        	i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			        	startActivity (i);
			        	 }
			    }
			});
			edtJudLocFirma.setFocusable(false);
			edtAdresaFirma = (EditText)findViewById (R.id.edtAdresaFirma);
			edtAdresaFirma.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          ContulMeu.tooltip.setVisibility (View.VISIBLE);
			          ContulMeu.tvTooltip.setText (getString(R.string.i258));
			        }else{
			        	edtAdresaFirma.setText(YTOUtils.replaceDiacritice(edtAdresaFirma.getText().toString()));
			        		ContulMeu.tvTooltip.setText ("");
			        	 ContulMeu.tooltip.setVisibility (View.GONE); 
			        }
			    }
			});
			edtTelefonFirma = (EditText)findViewById (R.id.edtTelefonFirma);
			edtTelefonFirma.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          ContulMeu.tooltip.setVisibility (View.VISIBLE);
			          ContulMeu.tvTooltip.setText (getString(R.string.i245));
			        }else{
			        		ContulMeu.tvTooltip.setText ("");
			        	 ContulMeu.tooltip.setVisibility (View.GONE); 
			        }
			    }
			});
			edtEmailFirma = (EditText)findViewById (R.id.edtEmailFirma);
			edtEmailFirma.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          ContulMeu.tooltip.setVisibility (View.VISIBLE);
			          ContulMeu.tvTooltip.setText (getString(R.string.i247));
			        }else{
			        		ContulMeu.tvTooltip.setText ("");
			        	 ContulMeu.tooltip.setVisibility (View.GONE); 
			        	 edtEmailFirma.setText(YTOUtils.replaceInEmail(edtEmailFirma.getText().toString()));
			        }
			    }
			});
			
			edtEmailFirma.addTextChangedListener(new TextWatcher() {
	        	 
	            @Override
	            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	                // When user changed the Text
	            }
	 
	            @Override
	            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                    int arg3) {
	                // TODO Auto-generated method stub
	 
	            }
	 
	            @Override
	            public void afterTextChanged(Editable arg0) {
			    	if (!YTOUtils.eMailValidation(edtEmailFirma.getText().toString()))
		        		 ((ImageView) findViewById(R.id.wrong_textmail)).setVisibility(View.VISIBLE);
		        	 else ((ImageView) findViewById(R.id.wrong_textmail)).setVisibility(View.GONE);
	            }
	        });
			load();
			
			btn_renunta = (Button) findViewById (R.id.renuntaFirma);
			btn_renunta.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					salveaza=false;
					InputMethodManager imm = (InputMethodManager)getSystemService(
						      Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(edtNumeFirma.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtAdresaFirma.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtCuiFirma.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtTelefonFirma.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtEmailFirma.getWindowToken(), 0);
					finish();
					
				}
			});
			
			btn_salveaza = (Button) findViewById (R.id.salveazaFirma);
			btn_salveaza.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {	
					salveaza=false;//daca salveaza=true salvez si la onPause()
					AsyncTask<Object, Object, Object> saveContactTask = 
		                new AsyncTask<Object, Object, Object>() 
		                {
		                   @Override
		                   protected Object doInBackground(Object... params) 
		                   {
		                       saveContact();
		                       return null;
		                   }
		       
		                   @Override
		                   protected void onPostExecute(Object result) 
		                   {
		                      finish();
		                   }
		                }; 
		               
		             saveContactTask.execute((Object[]) null); 
		             InputMethodManager imm = (InputMethodManager) 
		     		        getSystemService(Context.INPUT_METHOD_SERVICE);
		     		    imm.hideSoftInputFromWindow(edtAdresaFirma.getWindowToken(), 0);
					}
			});
			
			Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
			
			edtNumeFirma.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtCuiFirma.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtJudLocFirma.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtAdresaFirma.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtTelefonFirma.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtEmailFirma.setTypeface(Typeface.create(tf,Typeface.BOLD));
			
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
			
			if (edtCuiFirma.getText().toString().length()==0)
			new Handler().post(new Runnable() {

	              @Override
	              public void run() {
	                   edtCuiFirma.requestFocus();
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(edtCuiFirma, InputMethodManager.SHOW_IMPLICIT);
	              }
	        });
	 
		}
		
		@Override
		public void onBackPressed(){
			super.onBackPressed();
			
		}
		
		 private Boolean shouldSave ()
		   {
			   Boolean saveIt=false;
			   if (!edtNumeFirma.getText().toString().equals("")) saveIt=true;
			   if (!edtCuiFirma.getText().toString().equals("")) saveIt=true;
			   if (!edtJudLocFirma.getText().toString().equals("") && !edtJudLocFirma.getText().toString().equals(",")) saveIt=true;
			   if (!edtAdresaFirma.getText().toString().equals("")) saveIt=true;
			   if (!edtTelefonFirma.getText().toString().equals("")) saveIt=true;
			   if (!edtEmailFirma.getText().toString().equals("")) saveIt=true;
			   return saveIt;
		   }
		
		
		public void onResume()
		{
			super.onResume();
			if (Lista.tipDate.equals("localitati")) {edtJudLocFirma.setText(Lista.date);
			try{
    		judet = Lista.date.substring(0, Lista.date.indexOf(','));
    		localitate = Lista.date.substring(Lista.date.indexOf(',')+ 1, Lista.date.length());
			}catch (Exception e) {
				// TODO: handle exception
			}
    		Lista.tipDate="";
			}
    		Lista.date="";
    		Lista.tipDate="";
    		salveaza=true;
		}
		
		public void onPause ()
		{
			super.onPause();
			if (salveaza)
				saveContact();
		}

		
		 public void saveContact() 
		   { 
			 if (shouldSave ()){
			   persoanaJur.nume = edtNumeFirma.getText().toString();
			   persoanaJur.codUnic = edtCuiFirma.getText().toString();
			   persoanaJur.judet = judet;
			   persoanaJur.localitate = localitate;
			   persoanaJur.adresa = edtAdresaFirma.getText().toString();
			   persoanaJur.telefon = edtTelefonFirma.getText().toString();
			   persoanaJur.email = edtEmailFirma.getText().toString();
			   persoanaJur.email = YTOUtils.replaceInEmail(persoanaJur.email);
			   persoanaJur.tipPersoana = "juridica";
			   persoanaJur.proprietar = "da";
			   String toJSON = persoanaJur.persoanaToJSON (persoanaJur);
		      DatabaseConnector dbConnector = new DatabaseConnector(this);
		      if (persoanaJur.isDirty)
		      {
		    	  dbConnector.updateObiectAsigurat(persoanaJur.idIntern,
			        		 "1",
			        		 toJSON);
				     if (AltePersoane.persoanaAsigurata.isDirty)
				    	 if (AltePersoane.persoanaAsigurata.idIntern.equals(persoanaJur.idIntern));
				     {
				    	 persoanaJur = persoanaJur.persoanaFromJSON(persoanaJur, toJSON);
				    	 AltePersoane.persoanaAsigurata = new YTOPersoana();
				    	 AltePersoane.persoanaAsigurata = persoanaJur;
				     }
		      }
		      else {
		    	  persoanaJur.isDirty=true;
		    	  persoanaJur.idIntern = UUID.randomUUID().toString();
		    	  dbConnector.insertObiectAsigurat(persoanaJur.idIntern,
		    			  "1",
		    			  toJSON);
		    	  if (AltePersoane.persoanaAsigurata.isDirty)
				    	 if (AltePersoane.persoanaAsigurata.idIntern.equals(persoanaJur.idIntern));
				     {
				    	 persoanaJur = persoanaJur.persoanaFromJSON(persoanaJur, toJSON);
				    	 AltePersoane.persoanaAsigurata = new YTOPersoana();
				    	 AltePersoane.persoanaAsigurata = persoanaJur;
				     }
		      }
		      new RegisterPersoanaWebService(persoanaJur,contUser,contParola,limba,versiune).execute(null, null, null);
	    	  GetObiecte.getPersoane(dbConnector);
		      }
		   }
			private void load()
			 {
				 if (GetObiecte.persoane!=null &&  GetObiecte.persoane.size()!=0)
				 {
					   if (GetObiecte.persoanaJuridica.isDirty)
					   {
						    persoanaJur=GetObiecte.persoanaJuridica;
						   	edtNumeFirma.setText(persoanaJur.nume);
							edtCuiFirma.setText(persoanaJur.codUnic);
							if (!persoanaJur.judet.equals("")) 
							{
							edtJudLocFirma.setText(persoanaJur.judet+","+persoanaJur.localitate);
							judet=persoanaJur.judet;
						   	localitate=persoanaJur.localitate;
							}
							edtAdresaFirma.setText(persoanaJur.adresa);
							edtTelefonFirma.setText((persoanaJur.telefon.equals("") ? sett.getTelefonComanda():persoanaJur.telefon));
							edtEmailFirma.setText((persoanaJur.email.equals("") ? sett.getEmailContact():persoanaJur.email));
							if (!YTOUtils.eMailValidation(edtEmailFirma.getText().toString()))
				        		 ((ImageView) findViewById(R.id.wrong_textmail)).setVisibility(View.VISIBLE);
				        	 else ((ImageView) findViewById(R.id.wrong_textmail)).setVisibility(View.GONE);
							persoanaJur.idIntern=persoanaJur.idIntern;
							checkcui=false;
						}else {
							edtTelefonFirma.setText (sett.getTelefonComanda());
							edtEmailFirma.setText(sett.getEmailContact());
						}
				 }else {
					 checkcui=true;
					edtTelefonFirma.setText (sett.getTelefonComanda());
					edtEmailFirma.setText(sett.getEmailContact());
				}
					
			}	
private class Trimite extends AsyncTask<Void, Void, Void> {
	@Override
	protected void onPreExecute(){  
		dlg.setMessage(getString(R.string.i424));
		dlg.setCancelable(false);
		dlg.show();    
	}
	@Override
	protected Void doInBackground(Void... arg0) {

		String xml ="<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<soap:Body>" +
    "<GetCUIInfo xmlns='http://tempuri.org/'>" +
      "<user>vreaurca</user>" +
      "<password>123</password>" +
      "<cui>"+edtCuiFirma.getText().toString()+"</cui>" +
    "</GetCUIInfo>" +
  "</soap:Body>" +
"</soap:Envelope>";
		
		
		String url = GetObiecte.link + "utils.asmx";
		String soapAction = "http://tempuri.org/GetCUIInfo";
		String resp = HttpHelper.callWebService( url, soapAction,xml);
		if (resp.equals("")) {
			dlg.dismiss();
		}
		else {
			try {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				XMLHelperCuiInfo myXmlHelper = new XMLHelperCuiInfo();
				xr.setContentHandler(myXmlHelper);
				InputSource is = new InputSource(new StringReader(resp)); 
				xr.parse(is);
				StringBuilder raspuns = myXmlHelper.getParsedData();
				if (raspuns != null) {
					mesajOK = raspuns;
				} else {
					dlg.dismiss();
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
				String nume = "";
				String caen = "";
				String strada ="";
				String numar ="";
				String bloc ="";
				String scara="";
				String apartament="";
				
			    try {
					JSONObject jo = new JSONObject(mesajOK.toString());
					nume = jo.getString("Nume");
					caen = jo.getString("ClasaCaen");
					judet = jo.getString("Judet");
					localitate = jo.getString("Localitate");
					strada = jo.getString("Strada");
					numar = jo.getString("Numar");
					bloc = jo.getString("Bloc");
					scara = jo.getString("Scara");
					apartament = jo.getString("Apartament");
					if (caen.length()!=0)
					persoanaJur.codCaen = caen;
					if (nume.length()!=0)
					edtNumeFirma.setText(nume);
					if (judet.length()!=0)
					edtJudLocFirma.setText(judet+","+localitate);
					if (strada.length()!=0)
					edtAdresaFirma.setText("Str. "+strada+",nr. "+numar+",bl. "+bloc+",sc. "+scara+",ap. "+apartament);
					persoanaJur.codUnic=edtCuiFirma.getText().toString();
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else 
			{
				dlg.dismiss();
			}
	}
}

@Override
public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    // your stuff or nothing
}

@Override
public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    // your stuff or nothing
}

}