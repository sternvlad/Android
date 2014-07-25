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

public class PersoanaJuridica extends Activity {
		 
	public static EditText edtNumeFirmaAltePers,edtCuiFirmaAltePers,edtJudLocFirmaAltePers,edtAdresaFirmaAltePers;
	Button btn_renunta,btn_salveaza;
	String judet="",localitate="";
	public static ProgressDialog dlg;
	StringBuilder mesajOK=null;
	String mesaj;
	AppSettings sett;
	String contUser = "";
	String contParola = "";
	String limba = "ro";
	String versiune = "";
	Boolean checkCui=true;
	YTOPersoana persoanaJur = new YTOPersoana ();
	
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.persoana_juridica);

			sett = AppSettings.get(getParent().getParent());
			edtNumeFirmaAltePers = (EditText)findViewById (R.id.edtNumeFirmaAltePers);
			edtNumeFirmaAltePers.setText(persoanaJur.nume);
			edtNumeFirmaAltePers.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          Persoana.tooltipPersoana.setVisibility (View.VISIBLE);
			          Persoana.tvTooltipPersoana.setText (getString(R.string.i254));
			        }else{
			        	edtNumeFirmaAltePers.setText(YTOUtils.replaceDiacritice(edtNumeFirmaAltePers.getText().toString()));
			        		Persoana.tvTooltipPersoana.setText ("");
			        	 Persoana.tooltipPersoana.setVisibility (View.GONE); 
			        }
			    }
			});
			edtCuiFirmaAltePers = (EditText)findViewById (R.id.edtCUIFirmaAltePers);
			edtCuiFirmaAltePers.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			        	checkCui=false;
			          Persoana.tooltipPersoana.setVisibility (View.VISIBLE);
			          Persoana.tvTooltipPersoana.setText (getString(R.string.i256));
			        }else{
			        		Persoana.tvTooltipPersoana.setText ("");
			        	 Persoana.tooltipPersoana.setVisibility (View.GONE); 
			        	 
			        	 if (YTOUtils.verifyCUI(edtCuiFirmaAltePers.getText().toString())){
			        	 if (!checkCui){
			        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
			        		 new Trimite().execute(null, null, null);
			        		 checkCui=true;
			        	 }
			        	 }else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
			        }
			    }
			});
			
			edtCuiFirmaAltePers.addTextChangedListener(new TextWatcher() {
	        	 
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
			    	if (!YTOUtils.verifyCUI(edtCuiFirmaAltePers.getText().toString()))
		        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
		        	 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
	            }
	        });

			edtJudLocFirmaAltePers = (EditText)findViewById (R.id.edtJudLocFirmaAltePers);
			edtJudLocFirmaAltePers.setOnClickListener (new OnClickListener()
			{
			    @Override
			    public void onClick(View v)
			    {
			    	 if (YTOUtils.verifyCUI(edtCuiFirmaAltePers.getText().toString()) && !checkCui){
			        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
			        		 new Trimite().execute(null, null, null);
			        		 checkCui=true;
			        	 }else {
			        		 if (!YTOUtils.verifyCUI(edtCuiFirmaAltePers.getText().toString()))
			        			 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
			    		Lista.tipDate = "judete";
			        	Intent i = new Intent (PersoanaJuridica.this,Lista.class);
			        	startActivity (i);
			        	 }
			        	
			    }
			});
			edtJudLocFirmaAltePers.setFocusable(false);
			edtAdresaFirmaAltePers = (EditText)findViewById (R.id.edtAdresaFirmaAltePers);
			edtAdresaFirmaAltePers.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          Persoana.tooltipPersoana.setVisibility (View.VISIBLE);
			          Persoana.tvTooltipPersoana.setText (getString(R.string.i258));
			        }else{
			        	edtAdresaFirmaAltePers.setText(YTOUtils.replaceDiacritice(edtAdresaFirmaAltePers.getText().toString()));
			        		Persoana.tvTooltipPersoana.setText ("");
			        	 Persoana.tooltipPersoana.setVisibility (View.GONE); 
			        }
			    }
			});
			
		      btn_salveaza =(Button) findViewById(R.id.salveazaFirmaAltePers);
		      btn_salveaza.setOnClickListener(new OnClickListener() {
				
		    	  public void onClick(View v) 
		          {
		    		    saveContact();
						InputMethodManager imm = (InputMethodManager)getSystemService(
							      Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(edtNumeFirmaAltePers.getWindowToken(), 0);
							imm.hideSoftInputFromWindow(edtAdresaFirmaAltePers.getWindowToken(), 0);
							imm.hideSoftInputFromWindow(edtCuiFirmaAltePers.getWindowToken(), 0);
		          		finish();
		          }
			 });
		      
		      btn_renunta = (Button)findViewById (R.id.renuntaFirmaAltePers);
		      btn_renunta.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					InputMethodManager imm = (InputMethodManager)getSystemService(
						      Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(edtNumeFirmaAltePers.getWindowToken(), 0);
					imm.hideSoftInputFromWindow(edtAdresaFirmaAltePers.getWindowToken(), 0);
					imm.hideSoftInputFromWindow(edtCuiFirmaAltePers.getWindowToken(), 0);
					finish();
				}
			});		
		      
		      Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
				
		      edtNumeFirmaAltePers.setTypeface(Typeface.create(tf,Typeface.BOLD));
		      edtCuiFirmaAltePers.setTypeface(Typeface.create(tf,Typeface.BOLD));
		      edtJudLocFirmaAltePers.setTypeface(Typeface.create(tf,Typeface.BOLD));
		      edtAdresaFirmaAltePers.setTypeface(Typeface.create(tf,Typeface.BOLD));
		      
		      new Handler().post(new Runnable() {

	              @Override
	              public void run() {
	                   edtCuiFirmaAltePers.requestFocus();
	                   InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(edtCuiFirmaAltePers, InputMethodManager.SHOW_IMPLICIT);
	              }
	        });
		      
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
	 		}
		
		public void onResume()
		{
			super.onResume();
			if (!Lista.date.equals("")) {edtJudLocFirmaAltePers.setText(Lista.date);
			try{
    		judet = Lista.date.substring(0, Lista.date.indexOf(','));
    		localitate = Lista.date.substring(Lista.date.indexOf(',')+ 1, Lista.date.length());
			}catch (Exception e) {
				// TODO: handle exception
			}
			}
    		Lista.date="";
    		Lista.tipDate="";
		}
		
		 private void saveContact() 
		   {
			 if (shouldSave()){
			   persoanaJur.nume = edtNumeFirmaAltePers.getText().toString();
			   persoanaJur.codUnic = edtCuiFirmaAltePers.getText().toString();
			   persoanaJur.judet = judet;
			   persoanaJur.localitate = localitate;
			   persoanaJur.adresa = edtAdresaFirmaAltePers.getText().toString();
			   persoanaJur.tipPersoana = "juridica";
			   persoanaJur.proprietar="nu";
			   persoanaJur.idIntern = UUID.randomUUID().toString();
			   String toJSON = persoanaJur.persoanaToJSON (persoanaJur);
		      DatabaseConnector dbConnector = new DatabaseConnector(this);

		    	  dbConnector.insertObiectAsigurat(persoanaJur.idIntern,
		    			  "1",
		    			  toJSON
		    			  );
		    	  GetObiecte.getPersoane(dbConnector);
		    	  persoanaJur = persoanaJur.persoanaFromJSON(persoanaJur, toJSON);
		    	  new RegisterPersoanaWebService(persoanaJur ,contUser,contParola,limba,versiune).execute(null, null, null);
		    	  if (AltePersoane.tipDate.equals("Asigurare")){
		    		  persoanaJur.isDirty=true;
		    		  AltePersoane.persoanaAsigurata=persoanaJur;
		    	  }
			 }
		    }
		 
		   private Boolean shouldSave ()
		   {
			   Boolean saveIt=false;
			   if (!edtNumeFirmaAltePers.getText().toString().equals("")) saveIt=true;
			   if (!edtCuiFirmaAltePers.getText().toString().equals("")) saveIt=true;
			   if (!edtJudLocFirmaAltePers.getText().toString().equals("")  && !edtJudLocFirmaAltePers.getText().toString().equals(",")) saveIt=true;
			   if (!edtAdresaFirmaAltePers.getText().toString().equals("")) saveIt=true;
			   return saveIt;
		   }
		   public void onBackPressed ()
		   {
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
    			    imm.hideSoftInputFromWindow(edtAdresaFirmaAltePers.getWindowToken(), 0);
            
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
			      "<cui>"+edtCuiFirmaAltePers.getText().toString()+"</cui>" +
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
							checkCui=true;
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
								edtNumeFirmaAltePers.setText(nume);
								if (judet.length()!=0)
								edtJudLocFirmaAltePers.setText(judet+","+localitate);
								if (strada.length()!=0)
								edtAdresaFirmaAltePers.setText("Str. "+strada+",nr. "+numar+",bl. "+bloc+",sc. "+scara+",ap. "+apartament);
								persoanaJur.codUnic = edtCuiFirmaAltePers.getText().toString();
								
								
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
		   

			}
