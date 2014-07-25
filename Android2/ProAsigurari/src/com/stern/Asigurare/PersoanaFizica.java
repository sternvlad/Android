package com.stern.Asigurare;

import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PersoanaFizica extends Activity {
	EditText edtNumeAltePers,edtCnpAltePers,edtJudLocAltePers,edtAdresaAltePers,edtSerieAltePersoane;
	Button btn_renunta,btn_salveaza;
	String judet="",localitate="";
	String contUser = "";
	String contParola = "";
	String limba = "ro";
	String versiune = "";
	AppSettings sett;
	YTOPersoana persoanaFiz = new YTOPersoana ();
	
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.persoana_fizica);
			
			sett = AppSettings.get(getParent().getParent());
			edtNumeAltePers = (EditText)findViewById (R.id.edtNumeAltePers);
			edtNumeAltePers.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          Persoana.tooltipPersoana.setVisibility (View.VISIBLE); 
			          Persoana.tvTooltipPersoana.setText (getString(R.string.i238));
			        }else{
			        	edtNumeAltePers.setText(YTOUtils.replaceDiacritice(edtNumeAltePers.getText().toString()));
			        	Persoana.tvTooltipPersoana.setText ("");
			        	 Persoana.tooltipPersoana.setVisibility (View.GONE); 
			        }
			    }
			});
		    edtCnpAltePers = (EditText)findViewById (R.id.edtCNPAltePers);
		    edtCnpAltePers.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          Persoana.tooltipPersoana.setVisibility (View.VISIBLE); 
			          Persoana.tvTooltipPersoana.setText (getString(R.string.i240));
			        }else{
			        	Persoana.tvTooltipPersoana.setText ("");
			        	 Persoana.tooltipPersoana.setVisibility (View.GONE); 
			        }
			    }
			});
		    
			edtCnpAltePers.addTextChangedListener(new TextWatcher() {
	        	 
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
	            	if (edtCnpAltePers.getText().toString().length()!=13 || !YTOUtils.verificaCNP(edtCnpAltePers.getText().toString()))
		        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
		        	 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
	            }
	        });
		    
			edtJudLocAltePers = (EditText)findViewById (R.id.edtJudLocAltePers);
			edtJudLocAltePers.setOnClickListener (new OnClickListener()
			{
			    @Override
			    public void onClick(View v)
			    {
			    	if (edtCnpAltePers.getText().toString().length()!=13)
		        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
		        	 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
			    		Lista.tipDate = "judete";
			        	Intent i = new Intent (PersoanaFizica.this,Lista.class);
			        	startActivity (i);
			        	
			    }
			});
			edtJudLocAltePers.setFocusable(false);
			edtAdresaAltePers = (EditText)findViewById (R.id.edtAdresaAltePers);
			edtAdresaAltePers.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          Persoana.tooltipPersoana.setVisibility (View.VISIBLE); 
			          Persoana.tvTooltipPersoana.setText (getString(R.string.i243));
			        }else{
			        	edtAdresaAltePers.setText(YTOUtils.replaceDiacritice(edtAdresaAltePers.getText().toString()));
			        	Persoana.tvTooltipPersoana.setText ("");
			        	 Persoana.tooltipPersoana.setVisibility (View.GONE); 
			        }
			    }
			});
			edtSerieAltePersoane = (EditText) findViewById(R.id.edtSerieAltePers);
			edtSerieAltePersoane.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          Persoana.tooltipPersoana.setVisibility (View.VISIBLE); 
			          Persoana.tvTooltipPersoana.setText (getString(R.string.i251));
			          if (!YTOUtils.isTablet(getParent().getParent()))
			        	  Persoana.tvTooltipPersoana.setTextSize(12);
			        }else{
			        	if (!YTOUtils.isTablet(getParent().getParent()))
				        	  Persoana.tvTooltipPersoana.setTextSize(14);
			        	Persoana.tvTooltipPersoana.setText ("");
			        	 Persoana.tooltipPersoana.setVisibility (View.GONE); 
			        }
			    }
			});
		      
		      btn_salveaza =(Button) findViewById(R.id.salveazaAltePers);
		      btn_salveaza.setOnClickListener(new OnClickListener() {
				
		    	  public void onClick(View v) 
		          {
		    		    saveContact();
		    		    InputMethodManager imm = (InputMethodManager)getSystemService(
							      Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(edtNumeAltePers.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtCnpAltePers.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtAdresaAltePers.getWindowToken(), 0);
		          		finish();
		          }
		          });
		      
		      btn_renunta = (Button)findViewById (R.id.renuntaAltePers);
		      btn_renunta.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					InputMethodManager imm = (InputMethodManager)getSystemService(
						      Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(edtNumeAltePers.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtCnpAltePers.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtAdresaAltePers.getWindowToken(), 0);
					finish();
				}
			});
		      
		      Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
				
		      edtNumeAltePers.setTypeface(Typeface.create(tf,Typeface.BOLD));
		      edtCnpAltePers.setTypeface(Typeface.create(tf,Typeface.BOLD));
		      edtJudLocAltePers.setTypeface(Typeface.create(tf,Typeface.BOLD));
		      edtAdresaAltePers.setTypeface(Typeface.create(tf,Typeface.BOLD));
		      edtSerieAltePersoane.setTypeface(Typeface.create(tf,Typeface.BOLD));
		      
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
			if (!Lista.date.equals("")) {edtJudLocAltePers.setText(Lista.date);
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
			  if (shouldSave ()){ 
			   persoanaFiz.nume = edtNumeAltePers.getText().toString();
			   persoanaFiz.codUnic = edtCnpAltePers.getText().toString();
			   persoanaFiz.judet = judet;
			   persoanaFiz.localitate = localitate;
			   persoanaFiz.adresa = edtAdresaAltePers.getText().toString();
			   persoanaFiz.tipPersoana = "fizica";
			   persoanaFiz.idIntern = UUID.randomUUID().toString();
			   persoanaFiz.proprietar="nu";
			   persoanaFiz.serieAct=edtSerieAltePersoane.getText().toString();
			   String toJSON = persoanaFiz.persoanaToJSON (persoanaFiz);
		      DatabaseConnector dbConnector = new DatabaseConnector(this);

		    	  dbConnector.insertObiectAsigurat(persoanaFiz.idIntern,
		    			  "1",
		    			  toJSON
		    			  );
		    	  GetObiecte.getPersoane(dbConnector);
		    	  persoanaFiz = persoanaFiz.persoanaFromJSON(persoanaFiz, toJSON);
		    	  new RegisterPersoanaWebService(persoanaFiz,contUser,contParola,limba,versiune).execute(null, null, null);
		    	  if (AltePersoane.tipDate.equals("Asigurare")){
		    		  persoanaFiz.isDirty=true;
		    		  AltePersoane.persoanaAsigurata=persoanaFiz;		  
		    	  }
			  }
		      }
		   
		   private Boolean shouldSave ()
		   {
			   Boolean saveIt=false;
			   if (!edtNumeAltePers.getText().toString().equals("")) saveIt=true;
			   if (!edtCnpAltePers.getText().toString().equals("")) saveIt=true;
			   if (!edtJudLocAltePers.getText().toString().equals("")  && !edtJudLocAltePers.getText().toString().equals(",")) saveIt=true;
			   if (!edtAdresaAltePers.getText().toString().equals("")) saveIt=true;
			   if (!edtSerieAltePersoane.getText().toString().equals("")) saveIt=true;
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
    			    imm.hideSoftInputFromWindow(edtCnpAltePers.getWindowToken(), 0);
		   }

		}

