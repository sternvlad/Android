package com.stern.Asigurare;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PersoanaViewFromAsigurare extends Activity {
	EditText edtNumeAltePers,edtCnpAltePers,edtJudLocAltePers,edtAdresaAltePers,edtSerieAltePersoane;
	Button btn_renunta,btn_salveaza;
	String judet="",localitate="";
	String tip;
	public static ProgressDialog dlg;
	StringBuilder mesajOK=null;
	String mesaj;
	String contUser = "";
	String contParola = "";
	String limba = "ro";
	String versiune = "";
	String caen = "";
	Boolean checkCui=true;
	AppSettings sett;
	YTOPersoana persoana;
	public static ImageView tooltipPersoana,tipPers;
	public static TextView tvTooltipPersoana,tvPersoana,tvNume,tvCnp;
	
		public void onCreate(Bundle savedInstanceState) {
//			Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//	    	int width = display.getWidth(); 
	    	Window window = getWindow(); 
	    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.informatii_persoana);
			MainController.tvTitlu.setText(getString(R.string.i425));
			
	        Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
			((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface.create(th,Typeface.BOLD));
			((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface.create(th,Typeface.BOLD));
			((TextView) findViewById(R.id.text_header3)).setTypeface(Typeface.create(th,Typeface.BOLD));
			sett = AppSettings.get(this);
			sett.updateTitluGroup1(MainController.tvTitlu.getText().toString());
			
			persoana = YTOUtils.getPersoanaByIdIntern(AltePersoane.idIntern);
			
			tip=persoana.tipPersoana;
			
			tooltipPersoana = (ImageView)findViewById (R.id.tooltip_persoanaV);
			tvTooltipPersoana=(TextView)findViewById (R.id.textTooltipPersoanaV);
			tvPersoana= (TextView) findViewById (R.id.persoana);
			tvNume = (TextView) findViewById (R.id.textNumeAltePersV);
			tvCnp = (TextView) findViewById (R.id.textCNPAltePersV);
			
			
			
			
			edtNumeAltePers = (EditText)findViewById (R.id.edtNumeAltePersV);
			edtNumeAltePers.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			        	 tooltipPersoana.setImageResource(R.drawable.tooltip_calatorie);
			          tooltipPersoana.setVisibility (View.VISIBLE); 
			          if (tip.equals("fizica")){tvTooltipPersoana.setText (getString(R.string.i238));}
			          else {tvTooltipPersoana.setText (getString(R.string.i254));}
			        }else{
			        	edtNumeAltePers.setText(YTOUtils.replaceDiacritice(edtNumeAltePers.getText().toString()));
			        	tvTooltipPersoana.setText ("");
			        	 tooltipPersoana.setVisibility (View.GONE); 
			        }
			    }
			});
		    edtCnpAltePers = (EditText)findViewById (R.id.edtCNPAltePersV);
		    edtCnpAltePers.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			        	if (tip.equals("fizica")) checkCui=false;
			        	else checkCui=true;
			        	 tooltipPersoana.setImageResource(R.drawable.tooltip_calatorie);
			          tooltipPersoana.setVisibility (View.VISIBLE); 
			          if (tip.equals("fizica")) {tvTooltipPersoana.setText (getString(R.string.i240));}
			          else {tvTooltipPersoana.setText (getString(R.string.i256));}
			        }else{
			        	tvTooltipPersoana.setText ("");
			        	 tooltipPersoana.setVisibility (View.GONE);
			        	 
			        	 if (checkCui){
			        		 if (YTOUtils.verifyCUI(edtCnpAltePers.getText().toString()))
			        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
			        		 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
			        	 }else {		        	 
			        	 if (edtCnpAltePers.getText().toString().length()!=13 || !YTOUtils.verificaCNP(edtCnpAltePers.getText().toString()))
			        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
			        	 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
			        	 }
			        }
			    }
			});

			edtJudLocAltePers = (EditText)findViewById (R.id.edtJudLocAltePersV);
			edtJudLocAltePers.setOnClickListener (new OnClickListener()
			{
			    @Override
			    public void onClick(View v)
			    {
			    		Lista.tipDate = "judete";
			        	Intent i = new Intent (PersoanaViewFromAsigurare.this,Lista.class);
			        	startActivity (i);
			        	
			    }
			});
			edtJudLocAltePers.setFocusable(false);
			edtAdresaAltePers = (EditText)findViewById (R.id.edtAdresaAltePersV);
			edtAdresaAltePers.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			        	 tooltipPersoana.setImageResource(R.drawable.tooltip_calatorie);
			          tooltipPersoana.setVisibility (View.VISIBLE); 
			          if (tip.equals("fizica")){ tvTooltipPersoana.setText (getString(R.string.i243));}
			          else { tvTooltipPersoana.setText (getString(R.string.i258));}
			        }else{
			        	edtAdresaAltePers.setText(YTOUtils.replaceDiacritice(edtAdresaAltePers.getText().toString()));
			        	tvTooltipPersoana.setText ("");
			        	 tooltipPersoana.setVisibility (View.GONE); 
			        }
			    }
			});
			
			edtSerieAltePersoane = (EditText) findViewById(R.id.edtSerieAltePersV);
			edtSerieAltePersoane.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          tooltipPersoana.setVisibility (View.VISIBLE); 
			           tvTooltipPersoana.setText (getString(R.string.i251));
			           if (!YTOUtils.isTablet(getParent()))
			        	   tvTooltipPersoana.setTextSize(12);
			        }else{
			        	if (!YTOUtils.isTablet(getParent()))
			        		tvTooltipPersoana.setTextSize(14);
			        	tvTooltipPersoana.setText ("");
			        	 tooltipPersoana.setVisibility (View.GONE); 
			        }
			    }
			});
			load();
				new Handler().post(new Runnable() {

		              @Override
		              public void run() {
		            	  EditText edt = null;
		            	  if (edtNumeAltePers.getText().toString().length() == 0){
		            		  edt = edtNumeAltePers;
		            		  }else if (!YTOUtils.verificaCNP(edtCnpAltePers.getText().toString()) && persoana.tipPersoana.equals("fizica")){
		            					  edt  = edtCnpAltePers;
		            		  }else if (!YTOUtils.verifyCUI(edtCnpAltePers.getText().toString()) && persoana.tipPersoana.equals("juridica")){
            					  edt  = edtCnpAltePers;
		            		  }else if (edtAdresaAltePers.getText().toString().length() == 0){
		            			  edt = edtAdresaAltePers;
		            		  }
		            	  if (edt!=null){
		                    edt.requestFocus();
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT);
			  				tvTooltipPersoana.setText(getString(R.string.i442));
		            	  }
			  				if (mesajOneField(persoana) != null)
			            	  {
			  					tvTooltipPersoana.setText(mesajOneField(persoana));
			            	  }
							tooltipPersoana.setImageResource(R.drawable.tooltip_atentie);
							tooltipPersoana.setVisibility(View.VISIBLE);
		              }
		        });
		      
		      btn_salveaza =(Button) findViewById(R.id.salveazaAltePersV);
		      btn_salveaza.setOnClickListener(new OnClickListener() {
				
		    	  public void onClick(View v) 
		          {
		    		  saveContact();
						InputMethodManager imm = (InputMethodManager)getSystemService(
							      Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(edtNumeAltePers.getWindowToken(), 0);
							imm.hideSoftInputFromWindow(edtAdresaAltePers.getWindowToken(), 0);
							imm.hideSoftInputFromWindow(edtCnpAltePers.getWindowToken(), 0);
		    		  finish();
		          } 
			 });
		      
		      btn_renunta = (Button)findViewById (R.id.renuntaAltePersV);
		      btn_renunta.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					InputMethodManager imm = (InputMethodManager)getSystemService(
						      Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(edtNumeAltePers.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtAdresaAltePers.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtCnpAltePers.getWindowToken(), 0);
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
			  if (shouldUpdate()){ 
		      DatabaseConnector dbConnector = new DatabaseConnector(this);
		     persoana.nume=edtNumeAltePers.getText().toString();
		     persoana.codUnic=edtCnpAltePers.getText().toString();
 			 persoana.judet=judet;
 			 persoana.localitate=localitate;
 			 persoana.adresa=edtAdresaAltePers.getText().toString();
		     persoana.codCaen=caen;
		     persoana.serieAct=edtSerieAltePersoane.getText().toString();
		     String toJSON = persoana.persoanaToJSON (persoana);
		         dbConnector.updateObiectAsigurat(persoana.idIntern,
		        		 "1",
		        		 toJSON);
		         GetObiecte.getPersoane(dbConnector);
		     if (AltePersoane.persoanaAsigurata.isDirty)
		    		 if (AltePersoane.persoanaAsigurata.idIntern.equals(persoana.idIntern));
		     			{
		     				persoana = persoana.persoanaFromJSON(persoana, toJSON);
		     				AltePersoane.persoanaAsigurata = new YTOPersoana();
		     				AltePersoane.persoanaAsigurata = persoana;
		     			}
		     new RegisterPersoanaWebService(persoana,contUser,contParola,limba,versiune).execute(null, null, null);
		  }
		}
		
		   
		   private void load ()
		   {
			   edtCnpAltePers.setText(persoana.codUnic.toString());
			   if (tip.equals("fizica")){
					tvPersoana.setText("Persoana Fizica");
					tvNume.setText("NUME SI PRENUME");
					tvCnp.setText("CNP");
					if (edtCnpAltePers.getText().toString().length()!=13  || !YTOUtils.verificaCNP(edtCnpAltePers.getText().toString()))
		        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
		        	 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
				}
				else {
					tvPersoana.setText("Persoana Juridica");
					tvNume.setText("DENUMIRE");
					tvCnp.setText("CUI");
				}
			   edtNumeAltePers.setText(persoana.nume.toString());
			  
			   if ((!persoana.judet.equals(""))){
				   	edtJudLocAltePers.setText(persoana.judet+","+persoana.localitate);
				   	judet=persoana.judet;
				   	localitate=persoana.localitate;
			   }
			   edtAdresaAltePers.setText(persoana.adresa.toString());
			   
			   if (!tip.equals("fizica")){
	        		 if (edtCnpAltePers.getText().toString().length()==0 || edtCnpAltePers.getText().toString().length()>10)
	        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
	        		 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
	        		 edtSerieAltePersoane.setVisibility(View.GONE);
		        	 ((TextView)findViewById(R.id.textSerieAltePersV)).setVisibility(View.GONE);
	        	 }else {		        	 
	        	 if (edtCnpAltePers.getText().toString().length()!=13 || !YTOUtils.verificaCNP(edtCnpAltePers.getText().toString()))
	        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
	        	 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
	        	 edtSerieAltePersoane.setText(persoana.serieAct);
	        	 }
		   }
		   
		   private Boolean shouldUpdate ()
		   {
			   Boolean saveIt=false;
			   if (!edtNumeAltePers.getText().toString().equals("")) saveIt=true;
			   if (!edtCnpAltePers.getText().toString().equals("")) saveIt=true;
			   if (!edtJudLocAltePers.getText().toString().equals("")) saveIt=true;
			   if (!edtAdresaAltePers.getText().toString().equals("")) saveIt=true;
			   if (!edtSerieAltePersoane.getText().toString().equals("")) saveIt=true;
			   return saveIt;
		   }
		   
		   public void onBackPressed ()
		   {
			   super.onBackPressed();
			   saveContact();
               InputMethodManager imm = (InputMethodManager) 
   			        getSystemService(Context.INPUT_METHOD_SERVICE);
   			    imm.hideSoftInputFromWindow(edtCnpAltePers.getWindowToken(), 0);
		   }


			  public String  mesajOneField (YTOPersoana pers)
				{
					int i = 0;
					String mesaj = "";
					 if (pers.tipPersoana.equals("fizica"))
					 {
							if (pers.nume.length() == 0){
							        	i++ ;
							        	mesaj = getString (R.string.i629);
							}
						    if (pers.codUnic.length() == 0){
						    		i++ ;
						    		mesaj = getString(R.string.i630);
						    }
						    else if (!YTOUtils.verificaCNP(pers.codUnic)){
						    		i++ ;
						    		mesaj = getString(R.string.i631);
						    }
						    if (pers.adresa.length() == 0){
						    	i++;
						    	mesaj = getString(R.string.i633);
						    }
						    if (pers.judet.length() == 0){
						    	i++;
						    	mesaj = getString(R.string.i632);
						    }
					    }
					    else
					    {
								if (pers.nume.length() == 0){
								        i++ ;
								        mesaj = getString(R.string.i634);
								}
							    if (pers.codUnic.length() == 0){
							    		i++ ;
							    		mesaj = getString(R.string.i635);
							    }
							    else if (!YTOUtils.verifyCUI(pers.codUnic)){
							    		i++ ;
							    		mesaj = getString(R.string.i636);
							    }
							    if (pers.adresa.length() == 0){
								    	i++;
								    	mesaj = getString(R.string.i638);
							    }
							    if (pers.judet.length() == 0){
							    	i++;
							    	mesaj = getString(R.string.i639);
							    }
					    }
					    if (i == 1)
					    	return mesaj;
					    else return null;
				}
		  
			}



