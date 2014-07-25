package com.stern.Asigurare;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.Bundle;
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

public class PersoanaView extends Activity {
	EditText edtNumeAltePers,edtCnpAltePers,edtJudLocAltePers,edtAdresaAltePers,edtSerieAltePersoane;
	Button btn_renunta,btn_salveaza;
	String judet="",localitate="";
	String tip;
	String contUser = "";
	String contParola = "";
	String limba = "ro";
	String versiune = "";
	public static ProgressDialog dlg;
	StringBuilder mesajOK=null;
	String mesaj;
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
//	    	if (YTOUtils.isTablet(this)) {    	
//		    	window.setGravity(Gravity.CENTER); 
//		    	window.setLayout(320, 480);
//	    	}
	    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.informatii_persoana);
			MainController.tvTitlu.setText("Informatii Persoana");
			
	        Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
			((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface.create(th,Typeface.BOLD));
			((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface.create(th,Typeface.BOLD));
			((TextView) findViewById(R.id.text_header3)).setTypeface(Typeface.create(th,Typeface.BOLD));
			sett = AppSettings.get(this);
			sett.updateTitluGroup1(MainController.tvTitlu.getText().toString());
			
			tip=GetObiecte.persoaneNu.get(AltePersoane.positionId).tipPersoana;
			
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
			        	Intent i = new Intent (PersoanaView.this,Lista.class);
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
			          tooltipPersoana.setVisibility (View.VISIBLE); 
			          if (tip.equals("fizica")){ tvTooltipPersoana.setText (getString(R.string.i243));}
			          else { tvTooltipPersoana.setText ("Sediul social al firmei");}
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
						imm.hideSoftInputFromWindow(edtSerieAltePersoane.getWindowToken(), 0);
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
		     persoana = new YTOPersoana ();
		     persoana.nume=edtNumeAltePers.getText().toString();
		     persoana.codUnic=edtCnpAltePers.getText().toString();
 			 persoana.judet=judet;
 			 persoana.localitate=localitate;
 			 persoana.adresa=edtAdresaAltePers.getText().toString();
		     persoana.tipPersoana=tip;
		     persoana.proprietar="nu";
		     persoana.codCaen=caen;
		     persoana.serieAct=edtSerieAltePersoane.getText().toString();
		     String toJSON = persoana.persoanaToJSON (persoana);
		         dbConnector.updateObiectAsigurat(GetObiecte.persoaneNu.get(AltePersoane.positionId).idIntern,
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
			   edtNumeAltePers.setText(GetObiecte.persoaneNu.get(AltePersoane.positionId).nume.toString());
			   edtCnpAltePers.setText(GetObiecte.persoaneNu.get(AltePersoane.positionId).codUnic.toString());
			   if ((!GetObiecte.persoaneNu.get(AltePersoane.positionId).judet.equals(""))){
				   	edtJudLocAltePers.setText(GetObiecte.persoaneNu.get(AltePersoane.positionId).judet+","+GetObiecte.persoaneNu.get(AltePersoane.positionId).localitate);
				   	judet=GetObiecte.persoaneNu.get(AltePersoane.positionId).judet;
				   	localitate=GetObiecte.persoaneNu.get(AltePersoane.positionId).localitate;
			   }
			   edtAdresaAltePers.setText(GetObiecte.persoaneNu.get(AltePersoane.positionId).adresa.toString());
			   
			   if (!tip.equals("fizica")){
	        		 if (edtCnpAltePers.getText().toString().length()==0 || edtCnpAltePers.getText().toString().length()>10)
	        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
	        		 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
	        		 
	        		 edtSerieAltePersoane.setVisibility(View.GONE);
		        	 ((TextView) findViewById(R.id.textSerieAltePersV)).setVisibility(View.GONE);
	        		 
	        	 }else {		        	 
	        	 if (edtCnpAltePers.getText().toString().length()!=13 || !YTOUtils.verificaCNP(edtCnpAltePers.getText().toString()))
	        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
	        	 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
	        	 edtSerieAltePersoane.setText(GetObiecte.persoaneNu.get(AltePersoane.positionId).serieAct.toString());
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

		  
			}


