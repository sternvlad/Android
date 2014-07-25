package com.stern.Asigurare;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
; 
public class InfoMasina extends Activity {
	EditText edtMarcaAuto,edtModelAuto,edtJudLoc,edtNrInmatriculare,edtSerieSasiu,edtCm3,edtPutere,edtNumarLocuri,edtMasaMax,edtAnFabricatie,edtSerieTalon,edtFirmaLeasing;
	Boolean salveaza, leasing=false;
	TextView textCM3,textPutere;
	public static String tipLoad;
	RadioButton radioButtonCategorie,radioButtonDestinatie,radioButtonCombustibil,radio1,radio2,radio3,radio4,radio5,radio6,radio7,radio8,radio9;
	RadioGroup radioCategorie,radioDestinatie,radioCombustibil;
	YTOAutovehicul autovehicul=new YTOAutovehicul();
	int completedOpen,completedClose; //numarul de campuri completate la deschiderea si la inchiderea formularului
	Button btn_salveaza,btn_renunta,btn_daLeasing,btn_nuLeasing,alteleCategorie,alteleDestinatie,alteleCombustibil;
	String judet="",localitate="",toJSON,subcategorie,destinatie,combustibil;
	int categorie;
	AppSettings sett;
	String contUser = "";
	String contParola = "";
	String limba = "ro";
	String versiune = "";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_masina);
        
        sett = AppSettings.get(getParent().getParent());

        textCM3 = (TextView) findViewById(R.id.text_cm3);
        textPutere = (TextView) findViewById (R.id.text_putere);
        
        edtMarcaAuto = (EditText)findViewById (R.id.edtMarcaAuto);
        edtMarcaAuto.setOnClickListener (new OnClickListener()
		{
		    @Override
		    public void onClick(View v)
		    {
		    		salveaza=false;
		        	Lista.tipDate = "marci_auto";
		        	Intent i = new Intent (InfoMasina.this,Lista.class);
		        	i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		        	startActivity (i);
		        	
		    }
		});
		edtMarcaAuto.setFocusable(false);
		
		edtModelAuto = (EditText) findViewById(R.id.ModelAuto);
		edtModelAuto.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		          Masina.tooltip.setImageResource(R.drawable.tooltip_rca);	
		          Masina.tooltip.setVisibility (View.VISIBLE); 
		          Masina.tvTooltip.setText (getString(R.string.i279));
		        }else{
		        	Masina.tvTooltip.setText ("");
		        	Masina.tooltip.setVisibility (View.GONE); 
		        }
		    }
		});
		
		radioCategorie = (RadioGroup) findViewById (R.id.radioCategorie);
		radio1 = (RadioButton) findViewById (R.id.radio_1);
		radio1.setTag("1");
		radio1.setText(getText(R.string.i533));
		radio2 = (RadioButton) findViewById (R.id.radio_2);
		radio2.setTag("1");
		radio2.setText(getText(R.string.i535));
		radio3 = (RadioButton) findViewById (R.id.radio_3);
		radio3.setTag("7");
		radio3.setText(getText(R.string.i536));
		
		alteleCategorie = (Button) findViewById (R.id.alteleCategAuto);
		alteleCategorie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RadioButtons.tipDate="Categorie";
				int radioChecked = radioCategorie.getCheckedRadioButtonId();
				radioButtonCategorie = (RadioButton) findViewById(radioChecked);
				subcategorie = radioButtonCategorie.getText().toString();
				String subcategorieMasina = "";
				if (subcategorie.equals(getString(R.string.i533)))
					subcategorieMasina = "Autoturism";
				else if (subcategorie.equals(getString(R.string.i534)))
					subcategorieMasina = "Automobil-mixt";
				else if (subcategorie.equals(getString(R.string.i535)))
					subcategorieMasina = "Autoturism-de-teren";
				else if (subcategorie.equals(getString(R.string.i536)))
					subcategorieMasina = "Motocicleta";
				else if (subcategorie.equals(getString(R.string.i537)))
					subcategorieMasina = "Moped";
				else if (subcategorie.equals(getString(R.string.i538)))
					subcategorieMasina = "Atas";
				else if (subcategorie.equals(getString(R.string.i539)))
					subcategorieMasina = "Scuter";
				else if (subcategorie.equals(getString(R.string.i540)))
					subcategorieMasina = "ATV";
				else if (subcategorie.equals(getString(R.string.i541)))
					subcategorieMasina = "Autosanitara";
				else if (subcategorie.equals(getString(R.string.i542)))
					subcategorieMasina = "Autorulota";
				else if (subcategorie.equals(getString(R.string.i543)))
					subcategorieMasina = "Microbuz";
				else if (subcategorie.equals(getString(R.string.i544)))
					subcategorieMasina = "Autobuz";
				else if (subcategorie.equals(getString(R.string.i545)))
					subcategorieMasina = "Autocar";
				else if (subcategorie.equals(getString(R.string.i546)))
					subcategorieMasina = "Autoutilitara";
				else if (subcategorie.equals(getString(R.string.i547)))
					subcategorieMasina = "Autofurgon";
				else if (subcategorie.equals(getString(R.string.i548)))
					subcategorieMasina = "Autocamion";
				else if (subcategorie.equals(getString(R.string.i549)))
					subcategorieMasina = "Camion";
				else if (subcategorie.equals(getString(R.string.i550)))
					subcategorieMasina = "Autotractor";
				else if (subcategorie.equals(getString(R.string.i551)))
					subcategorieMasina = "Tractor-rutier";
				else if (subcategorie.equals(getString(R.string.i552)))
					subcategorieMasina = "Remorca";
				else if (subcategorie.equals(getString(R.string.i553)))
					subcategorieMasina = "Semiremorca";
				RadioButtons.date=subcategorieMasina;
				Intent i = new Intent (InfoMasina.this,RadioButtons.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity (i);
			}
		});
		 
        edtJudLoc = (EditText)findViewById (R.id.edtJudLocTalon);	
		edtJudLoc.setOnClickListener (new OnClickListener()
		{
		    @Override
		    public void onClick(View v)
		    {
		    		salveaza=false;
		        	Lista.tipDate = "judete";
		        	Intent i = new Intent (InfoMasina.this,Lista.class);
		        	i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		        	startActivity (i);
		        	
		    }
		});
		edtJudLoc.setFocusable(false);
		
		edtNrInmatriculare = (EditText)findViewById (R.id.edtNrInmatriculare);
		edtNrInmatriculare.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		        	Masina.tooltip.setImageResource(R.drawable.tooltip_rca);	
		          Masina.tooltip.setVisibility (View.VISIBLE); 
		          Masina.tvTooltip.setText (getString(R.string.i307));
		        }else{
		        	Masina.tvTooltip.setText ("");
		        	Masina.tooltip.setVisibility (View.GONE); 
		        }
		    }
		});
		
		edtSerieSasiu = (EditText) findViewById (R.id.edtSerieSasiu);
		edtSerieSasiu.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		        	Masina.tooltip.setImageResource(R.drawable.tooltip_rca);	
		          Masina.tooltip.setVisibility (View.VISIBLE); 
		          Masina.tvTooltip.setText (getString(R.string.i309));
		        }else{
		        	Masina.tvTooltip.setText ("");
		        	Masina.tooltip.setVisibility (View.GONE); 
		        	edtSerieSasiu.setText(YTOUtils.replaceInSerieSasiu(edtSerieSasiu.getText().toString()));
		        	if (edtSerieSasiu.getText().toString()!=null)
		        		if (edtSerieSasiu.getText().toString().length()>17 || edtSerieSasiu.getText().toString().length()<3)
			        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
			        	 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
		        }
		    }
		});
		
		edtCm3 = (EditText) findViewById (R.id.edtCM3);
		edtCm3.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		        	Masina.tooltip.setImageResource(R.drawable.tooltip_rca);	
		          Masina.tooltip.setVisibility (View.VISIBLE); 
		          Masina.tvTooltip.setText (getString(R.string.i311));
		        }else{
		        	Masina.tvTooltip.setText ("");
		        	Masina.tooltip.setVisibility (View.GONE); 
		        }
		    }
		});
		
		edtPutere = (EditText) findViewById (R.id.edtPutere);
		edtPutere.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		        	Masina.tooltip.setImageResource(R.drawable.tooltip_rca);	
		          Masina.tooltip.setVisibility (View.VISIBLE); 
		          Masina.tvTooltip.setText (getString(R.string.i313));
		          if (autovehicul.putere!=-1) edtPutere.setText(autovehicul.putere+"");
		        }else{
		        	Masina.tvTooltip.setText ("");
		        	Masina.tooltip.setVisibility (View.GONE); 
		        	if (!edtPutere.getText().toString().equals("")) 
		 			   try{
		 				   autovehicul.putere = Integer.parseInt(edtPutere.getText().toString());
		 			   }catch (Exception e){
		 				  autovehicul.putere = -1;
		 			   }
		 		   else autovehicul.putere = -1; 
		        	if (autovehicul.putere!=-1) edtPutere.setText(autovehicul.putere+" kw");
		        }
		    }
		});
		
		edtNumarLocuri = (EditText) findViewById (R.id.edtNumarLocuri);
		edtNumarLocuri.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		        	Masina.tooltip.setImageResource(R.drawable.tooltip_rca);	
		          Masina.tooltip.setVisibility (View.VISIBLE); 
		          Masina.tvTooltip.setText (getString(R.string.i315));
		        }else{
		        	Masina.tvTooltip.setText ("");
		        	Masina.tooltip.setVisibility (View.GONE); 
		        }
		    }
		});
		
		edtMasaMax = (EditText) findViewById (R.id.edtMasaMaxima);
		edtMasaMax.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		        	Masina.tooltip.setImageResource(R.drawable.tooltip_rca);	
		          Masina.tooltip.setVisibility (View.VISIBLE); 
		          Masina.tvTooltip.setText (getString(R.string.i317));
		          if (autovehicul.masaMaxima!=-1) edtMasaMax.setText(autovehicul.masaMaxima+"");
		        }else{
		        	Masina.tvTooltip.setText ("");
		        	Masina.tooltip.setVisibility (View.GONE);
		        	if (!edtMasaMax.getText().toString().equals("")) 
			 			   try{
			 				   autovehicul.masaMaxima = Integer.parseInt(edtMasaMax.getText().toString());
			 			   }catch (Exception e){
			 				  autovehicul.masaMaxima = -1;
			 			   }
			 		   else autovehicul.masaMaxima = -1; 
		        	if (autovehicul.masaMaxima!=-1) edtMasaMax.setText(autovehicul.masaMaxima+" kg");

		        }
		    }
		});
		
		edtAnFabricatie = (EditText) findViewById (R.id.edtAnFabricatie);
		edtAnFabricatie.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		        	Masina.tooltip.setImageResource(R.drawable.tooltip_rca);	
		          Masina.tooltip.setVisibility (View.VISIBLE); 
		          Masina.tvTooltip.setText (getString(R.string.i319));
		        }else{
		        	Masina.tvTooltip.setText ("");
		        	Masina.tooltip.setVisibility (View.GONE); 
		        }
		    }
		});
		
		edtSerieTalon = (EditText) findViewById (R.id.edt_SerieTalon);
		edtSerieTalon.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		        	Masina.tooltip.setImageResource(R.drawable.tooltip_rca);	
		          Masina.tooltip.setVisibility (View.VISIBLE); 
		          Masina.tvTooltip.setText (getString(R.string.i321));
		        }else{
		        	Masina.tvTooltip.setText ("");
		        	Masina.tooltip.setVisibility (View.GONE); 
		        	if (edtSerieTalon.getText().toString().length()<7 || edtSerieTalon.getText().toString().length()>10)
		        		 ((ImageView) findViewById(R.id.wrong_text2)).setVisibility(View.VISIBLE);
		        	 else ((ImageView) findViewById(R.id.wrong_text2)).setVisibility(View.GONE);
		        }
		    }
		});
		
		radioDestinatie = (RadioGroup) findViewById (R.id.radioDestinatieAuto);
		radio4 = (RadioButton) findViewById (R.id.radio_4);
		radio4.setText(getString(R.string.i513));
		radio5 = (RadioButton) findViewById (R.id.radio_5);
		radio5.setText(getString(R.string.i514));
		radio6 = (RadioButton) findViewById (R.id.radio_6);
		radio6.setText(getString(R.string.i518));
		
		radio4.setTag("interes-personal");
		radio5.setTag("distributie-marfa");
		radio6.setTag("taxi");
		
		alteleDestinatie = (Button) findViewById (R.id.alteleDestinatie);
		alteleDestinatie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RadioButtons.tipDate="Destinatie";
				int radioChecked = radioDestinatie.getCheckedRadioButtonId();
				radioButtonDestinatie = (RadioButton) findViewById(radioChecked);
				destinatie = radioButtonDestinatie.getText().toString();
				String destinatieTag = "";
				if (destinatie.equals(getString(R.string.i513)))
					destinatieTag = "interes-personal";
				else if (destinatie.equals(getString(R.string.i514)))
					destinatieTag = "distributie-marfa";
				else if (destinatie.equals(getString(R.string.i515)))
					destinatieTag = "transport-persoane";
				else if (destinatie.equals(getString(R.string.i516)))
					destinatieTag = "transport-international";
				else if (destinatie.equals(getString(R.string.i517)))
					destinatieTag = "paza-protectie-interventie";
				else if (destinatie.equals(getString(R.string.i518)))
					destinatieTag = "taxi";
				else if (destinatie.equals(getString(R.string.i519)))
					destinatieTag = "maxi-taxi";
				else if (destinatie.equals(getString(R.string.i520)))
					destinatieTag = "scoala-soferi";
				else if (destinatie.equals(getString(R.string.i521)))
					destinatieTag = "inchiriere";
				else if (destinatie.equals(getString(R.string.i522)))
					destinatieTag = "curierat";
				else if (destinatie.equals(getString(R.string.i523)))
					destinatieTag = "alte-activitati";
				RadioButtons.date=destinatieTag;
				Intent i = new Intent (InfoMasina.this,RadioButtons.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity (i);
			}
		});
		
		radioCombustibil = (RadioGroup) findViewById (R.id.radioCombustibil);
		radio7 = (RadioButton) findViewById (R.id.radio_7);
		radio8 = (RadioButton) findViewById (R.id.radio_8);
		radio9 = (RadioButton) findViewById (R.id.radio_9);
		radio7.setText(getString(R.string.i525));
		radio8.setText(getString(R.string.i526));
		radio9.setText(getString(R.string.i527));
		alteleCombustibil = (Button) findViewById (R.id.alteleCombustibil);
		alteleCombustibil.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RadioButtons.tipDate="Combustibil";
				int radioChecked = radioCombustibil.getCheckedRadioButtonId();
				radioButtonCombustibil = (RadioButton) findViewById(radioChecked);
				combustibil = radioButtonCombustibil.getText().toString();
				String combustibilMasina = "";
				if (combustibil.equals(getString(R.string.i525)))
					   combustibilMasina  = "benzina";
				   else if (combustibil.equals(getString(R.string.i526)))
					   combustibilMasina  = "motorina";
				   else if (combustibil.equals(getString(R.string.i527)))
					   combustibilMasina  = "gpl";
				   else if (combustibil.equals(getString(R.string.i528)))
					   combustibilMasina  = "electric/hibrid";
				   else if (combustibil.equals(getString(R.string.i529)))
					   combustibilMasina  = "fara";
				
				RadioButtons.date=combustibilMasina;
				Intent i = new Intent (InfoMasina.this,RadioButtons.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity (i);
			}
		});
		
		edtFirmaLeasing = (EditText) findViewById (R.id.edtDenumireFirmaLeasing);
		edtFirmaLeasing.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		          Masina.tooltip.setVisibility (View.VISIBLE); 
		          Masina.tvTooltip.setText (getString(R.string.i441));
		        }else{
		        	Masina.tvTooltip.setText ("");
		        	Masina.tooltip.setVisibility (View.GONE); 
		        }
		    }
		});
		
		btn_daLeasing = (Button) findViewById (R.id.da_rca);
		btn_daLeasing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn_daLeasing.setTextColor(Color.WHITE);
				btn_nuLeasing.setTextColor(Color.BLACK);
				leasing=true;
				btn_daLeasing.setBackgroundResource(R.drawable.da_nu_da_da_rca);
				btn_nuLeasing.setBackgroundResource(R.drawable.da_nu_da_nu_rca);
				TextView tv = (TextView) findViewById (R.id.tvDenumireFirmaLeasing);
				tv.setVisibility(View.VISIBLE);
				edtFirmaLeasing.setVisibility(View.VISIBLE);	
			}
		});
		
		btn_nuLeasing = (Button) findViewById (R.id.nu_rca);
		btn_nuLeasing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn_nuLeasing.setTextColor(Color.WHITE);
				btn_daLeasing.setTextColor(Color.BLACK);
				leasing=false;
				btn_nuLeasing.setBackgroundResource(R.drawable.da_nu_nu_nu_rca);
				btn_daLeasing.setBackgroundResource(R.drawable.da_nu_nu_da_rca);
				TextView tv = (TextView) findViewById (R.id.tvDenumireFirmaLeasing);
				tv.setVisibility(View.GONE);
				edtFirmaLeasing.setVisibility(View.GONE);	
			}
		});
		
		btn_salveaza =(Button) findViewById(R.id.salveaza_masina);
	    btn_salveaza.setOnClickListener(new OnClickListener() {
			
	    	 public void onClick(View v) 
	          {
	    		  onBackPressed ();//pe onBackPressed salvez
	          }
		 });
	      
	      btn_renunta = (Button)findViewById (R.id.renunta_masina);
	      btn_renunta.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				salveaza=false;
				finish();
			}
		});
	      
	      
	      load();
	      completedOpen=getCompletedCount();
			if (MasinileMele.tipDate.equals("Asigurare") && autovehicul.isDirty)
			{
				new Handler().post(new Runnable() {

		              @Override
		              public void run() {
		            	  EditText edt = null;
		            	   if (edtModelAuto.getText().toString().length() == 0){
		            			  edt = edtModelAuto;
		            		  }else if (edtNrInmatriculare.getText().toString().length() == 0){
		            			  edt = edtNrInmatriculare;
		            		  }else if (edtSerieSasiu.getText().toString().length()>17 || edtSerieSasiu.getText().toString().length()<3){
		            			  edt = edtSerieSasiu;
		            		  }else if (edtCm3.getText().toString().length() == 0){
		            			  edt = edtCm3;
		            		  }else if (edtPutere.getText().toString().length() == 0){
		            			  edt = edtPutere;
		            		  }else if (edtNumarLocuri.getText().toString().length() == 0){
		            			  edt = edtNumarLocuri;
		            		  }else if (edtMasaMax.getText().toString().length() == 0){
		            			  edt = edtMasaMax;
		            		  }else if (edtAnFabricatie.getText().toString().length() == 0){
		            			  edt = edtAnFabricatie;
		            		  }else if (edtSerieTalon.getText().toString().length() == 0){
		            			  edt = edtSerieTalon;
		            		  }else if (edtSerieTalon.getText().toString().length()<7 || edtSerieTalon.getText().toString().length()>10){
		     	        		 edt = edtSerieTalon;
		              		  }else if (autovehicul.inLeasing.equals("da") && edtFirmaLeasing.getText().toString().length() == 0){
		     	        		 edt = edtFirmaLeasing;
		              		  }
		            	    if (edt!=null){
		                    edt.requestFocus();
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT);
		            	    }
			  				Masina.tvTooltip.setText(getString(R.string.i442));
			            	if ((completedOpen == 12 && autovehicul.inLeasing.equals("nu")) || (completedOpen == 12 && autovehicul.inLeasing.equals("")) || (completedOpen == 13 && autovehicul.inLeasing.equals("da")))
			            	   {
			            			if (edtMarcaAuto.getText().toString().length() == 0){
			            				Masina.tvTooltip.setText(getString(R.string.i616));
			            			}else if (autovehicul.judet.length() == 0){
			            				Masina.tvTooltip.setText(getString(R.string.i618));
			            			}else if (edtModelAuto.getText().toString().length() == 0){
			            			   Masina.tvTooltip.setText(getString(R.string.i617));
				            		  }else if (edtNrInmatriculare.getText().toString().length() == 0){
				            			  Masina.tvTooltip.setText(getString(R.string.i619));
				            		  }else if (edtSerieSasiu.getText().toString().length()>17 || edtSerieSasiu.getText().toString().length()<3){
				            			  if (edtSerieSasiu.getText().toString().length() == 0)
				            				  Masina.tvTooltip.setText(getString(R.string.i620));
				            			  else Masina.tvTooltip.setText(getString(R.string.i621));
				            		  }else if (edtCm3.getText().toString().length() == 0){
				            			  Masina.tvTooltip.setText(getString(R.string.i622));
				            		  }else if (edtPutere.getText().toString().length() == 0){
				            			  Masina.tvTooltip.setText(getString(R.string.i623));
				            		  }else if (edtNumarLocuri.getText().toString().length() == 0){
				            			  Masina.tvTooltip.setText(getString(R.string.i624));
				            		  }else if (edtMasaMax.getText().toString().length() == 0){
				            			  Masina.tvTooltip.setText(getString(R.string.i625));
				            		  }else if (edtAnFabricatie.getText().toString().length() == 0){
				            			  Masina.tvTooltip.setText(getString(R.string.i626));
				            		  }else if (edtSerieTalon.getText().toString().length()<7 || edtSerieTalon.getText().toString().length()>10){
				            			  if (edtSerieTalon.getText().toString().length() == 0)
					            			  Masina.tvTooltip.setText(getString(R.string.i627));
					            		  else Masina.tvTooltip.setText(getString(R.string.i628));
				              		  }else if (autovehicul.inLeasing.equals("da") && edtFirmaLeasing.getText().toString().length() == 0){
				              			Masina.tvTooltip.setText(getString(R.string.i442));
				              		  }
			            	   }else if (autovehicul.completedPercent() == 1 && !autovehicul.isValidForRCA())
			            	   {
			            		   if (!(edtSerieTalon.getText().toString().length()<7 || edtSerieTalon.getText().toString().length()>10) && (edtSerieSasiu.getText().toString().length()>17 || edtSerieSasiu.getText().toString().length()<3))
			            		   {
			            			   Masina.tvTooltip.setText(getString(R.string.i621));
			            		   }else if ((edtSerieTalon.getText().toString().length()<7 || edtSerieTalon.getText().toString().length()>10) && !(edtSerieSasiu.getText().toString().length()>17 || edtSerieSasiu.getText().toString().length()<3))
			            			   Masina.tvTooltip.setText(getString(R.string.i628));
			            	   }
							Masina.tooltip.setImageResource(R.drawable.tooltip_atentie);
							Masina.tooltip.setVisibility(View.VISIBLE);
		              }
		        });
			}
			
	      
	      
			Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
			
			edtMarcaAuto.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtModelAuto.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtJudLoc.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtNrInmatriculare.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtSerieSasiu.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtCm3.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtPutere.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtNumarLocuri.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtMasaMax.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtAnFabricatie.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtSerieTalon.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtFirmaLeasing.setTypeface(Typeface.create(tf,Typeface.BOLD));
			
			if (autovehicul.categorieAuto==8)
			{
				edtPutere.setText("0");
	    		edtCm3.setText("0");
	    		edtPutere.setVisibility(View.GONE);
	    		edtCm3.setVisibility(View.GONE);
	    		textCM3.setVisibility(View.GONE);
	    		textPutere.setVisibility(View.GONE);
			}
			
			radio1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked)
					setVisibilityByCategory(1);
				}
			});
			radio2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked)
					setVisibilityByCategory(1);
				}
			});
			radio3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked)
					setVisibilityByCategory(Integer.parseInt(radio3.getTag().toString()));
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
  
    public void onPause()
    {
    	super.onPause();
    	if (salveaza)
                  saveContact();
		InputMethodManager imm = (InputMethodManager) 
		        getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(edtAnFabricatie.getWindowToken(), 0);
  	 }
    
  
    
    public void onResume()
	{
		super.onResume();
		if (tipLoad.equals("add"))
    	{
    			Masina.changeHeader(1, getApplicationContext());
    	}else{
    		Masina.changeHeader(2, getApplicationContext());
    	}
		if (Lista.tipDate.equals("marci_auto"))
		{
			if (!Lista.date.equals(""))
				edtMarcaAuto.setText (Lista.date);
			Lista.tipDate="";
		}
		else if (Lista.tipDate.equals("localitati")) 
		{
			edtJudLoc.setText(Lista.date);
			try{
			judet = Lista.date.substring(0, Lista.date.indexOf(','));
			localitate = Lista.date.substring(Lista.date.indexOf(',')+ 1, Lista.date.length());
			}catch (Exception e) {
				// TODO: handle exception
			}
			Lista.tipDate="";
		}
		if (RadioButtons.tipDate.equals("Categorie"))
		{
			subcategorie=RadioButtons.date;
			try{
			categorie=Integer.parseInt(RadioButtons.tag);
			}catch (Exception e) {
				// TODO: handle exception
				categorie=1;
			}
				
			if (subcategorie.equals("Autoturism")) {radio1.setChecked(true); radio1.setTag("1");}
			else if (subcategorie.equals("Autoturism-de-teren")) {radio2.setChecked(true); radio2.setTag ("1");}
			else
		{
				String subcategorieMasina = "";
				if (subcategorie.equals("Autoturism"))
					subcategorieMasina = getString(R.string.i533);
				else if (subcategorie.equals("Automobil-mixt"))
					subcategorieMasina = getString(R.string.i534);
				else if (subcategorie.equals("Autoturism-de-teren"))
					subcategorieMasina = getString(R.string.i535);
				else if (subcategorie.equals("Motocicleta"))
					subcategorieMasina = getString(R.string.i536);
				else if (subcategorie.equals("Moped"))
					subcategorieMasina = getString(R.string.i537);
				else if (subcategorie.equals("Atas"))
					subcategorieMasina = getString(R.string.i538);
				else if (subcategorie.equals("Scuter"))
					subcategorieMasina = getString(R.string.i539);
				else if (subcategorie.equals("ATV"))
					subcategorieMasina = getString(R.string.i540);
				else if (subcategorie.equals("Autosanitara"))
					subcategorieMasina = getString(R.string.i541);
				else if (subcategorie.equals("Autorulota"))
					subcategorieMasina = getString(R.string.i542);
				else if (subcategorie.equals("Microbuz"))
					subcategorieMasina = getString(R.string.i543);
				else if (subcategorie.equals("Autobuz"))
					subcategorieMasina = getString(R.string.i544);
				else if (subcategorie.equals("Autocar"))
					subcategorieMasina = getString(R.string.i545);
				else if (subcategorie.equals("Autoutilitara"))
					subcategorieMasina = getString(R.string.i546);
				else if (subcategorie.equals("Autofurgon"))
					subcategorieMasina = getString(R.string.i547);
				else if (subcategorie.equals("Autocamion"))
					subcategorieMasina = getString(R.string.i548);
				else if (subcategorie.equals("Camion"))
					subcategorieMasina = getString(R.string.i549);
				else if (subcategorie.equals("Autotractor"))
					subcategorieMasina = getString(R.string.i550);
				else if (subcategorie.equals("Tractor-rutier"))
					subcategorieMasina = getString(R.string.i551);
				else if (subcategorie.equals("Remorca"))
					subcategorieMasina = getString(R.string.i552);
				else if (subcategorie.equals("Semiremorca"))
					subcategorieMasina = getString(R.string.i553);
				radio3.setText (subcategorieMasina);
				radio3.setTag(categorie+"");
				radio3.setChecked(true);
				setVisibilityByCategory(categorie);
					
		}
		}
		if (RadioButtons.tipDate.equals("Combustibil"))
		{
			combustibil=RadioButtons.date;
			if (combustibil.equals("benzina")) {radio7.setChecked(true);}
			else if (combustibil.equals("motorina")) {radio8.setChecked(true);}
			else 
		{
				String combustibilText = "";
				if (combustibil.equals("electric/hibrid"))
					combustibilText = getString(R.string.i528);
				else if (combustibil.equals("fara"))
					combustibilText = getString(R.string.i529);
				radio9.setText (combustibilText);
				radio9.setChecked(true);
		}
		}
		if (RadioButtons.tipDate.equals("Destinatie"))
		{
			destinatie=RadioButtons.date;
			if (destinatie.equals("interes-personal")) {radio4.setChecked(true);}
			else if (destinatie.equals("distributie-marfa")) {radio5.setChecked(true);}
			else 
		{
				String destinatieText = "";
				if (destinatie.equals("transport-persoane"))
					destinatieText = getString(R.string.i515);
				else if (destinatie.equals("transport-international"))
					destinatieText = getString(R.string.i516);
				else if (destinatie.equals("paza-protectie-interventie"))
					destinatieText = getString(R.string.i517);
				else if (destinatie.equals("taxi"))
					destinatieText = getString(R.string.i518);
				else if (destinatie.equals("maxi-taxi"))
					destinatieText = getString(R.string.i519);
				else if (destinatie.equals("scoala-soferi"))
					destinatieText = getString(R.string.i520);
				else if (destinatie.equals("inchiriere"))
					destinatieText = getString(R.string.i521);
				else if (destinatie.equals("curierat"))
					destinatieText = getString(R.string.i522);
				else if (destinatie.equals("alte-activitati"))
					destinatieText = getString(R.string.i523);
				radio6.setText (destinatieText);
				radio6.setChecked(true);
				radio6.setTag(RadioButtons.tag);
				
		}
		}
		Lista.date="";
		RadioButtons.tipDate="";
		RadioButtons.date="";
		salveaza=true;
	}
    
    private void saveContact() 
	   {    
		   DatabaseConnector dbConnector = new DatabaseConnector(this);
		   if (tipLoad.equals("add") && shouldSave())
		   {
			   saveFromFormular();
			   tipLoad = "view";
			   autovehicul.idIntern = UUID.randomUUID().toString();
			   AlerteMasina.idObiect=autovehicul.idIntern;
			   DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		        String currentDate = dateFormat.format(new Date());
		        Calendar c = Calendar.getInstance();
				try {
					c.setTime(dateFormat.parse(currentDate));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				autovehicul._dataCreare = dateFormat.format(c.getTime());
				autovehicul.isDirty = true;
			   dbConnector.insertObiectAsigurat(autovehicul.idIntern,
	    			  "2",
	    			  toJSON
	    			  );
		    	  autovehicul = autovehicul.autovehiculFromJSON(autovehicul, toJSON);
		    	  new RegisterMasinaWebService(autovehicul,contUser,contParola,limba,versiune).execute(null, null, null);
		    	  MasinileMele.autovehiculCurent = new YTOAutovehicul();
		    		autovehicul.isDirty=true;
					MasinileMele.autovehiculCurent = autovehicul;
//					if (autovehicul.completedPercent() != 1 || !autovehicul.isValidForRCA())
//						AsigurareRca.skipNextPage = true;
//					else AsigurareRca.skipNextPage = false;
		  }
		   else if (tipLoad.equals("view"))
		   {
			   saveFromFormular();
			   dbConnector.updateObiectAsigurat(autovehicul.idIntern,
		    			  "2",
		    			  toJSON
		    			  );  
		    	  autovehicul = autovehicul.autovehiculFromJSON(autovehicul, toJSON);
		    	  new RegisterMasinaWebService(autovehicul,contUser,contParola,limba,versiune).execute(null, null, null);
		    	  MasinileMele.autovehiculCurent = new YTOAutovehicul();
		    		autovehicul.isDirty=true;
					MasinileMele.autovehiculCurent = autovehicul;
//					if (autovehicul.completedPercent() != 1 || !autovehicul.isValidForRCA())
//						AsigurareRca.skipNextPage = true;
//					else AsigurareRca.skipNextPage = false;
		   }
		   
	    	  GetObiecte.getAutovehicule(dbConnector);
	    		
	      }

	   private Boolean shouldSave ()
	   {
		   Boolean saveIt=false;
		   completedClose=getCompletedCount();
		   if (completedClose>completedOpen) saveIt=true;
		   return saveIt;
	   }
	   
	   @Override
	   public void onBackPressed ()
	   {
		   salveaza=false;//daca salveaza= true salvez si pe onPause();
                saveContact();
                hideKeyboard();
           super.onBackPressed();
	   }
	   
	   private void hideKeyboard() {
		    InputMethodManager imm = (InputMethodManager) 
		        getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(edtSerieSasiu.getWindowToken(), 0);
		}
	   
    
    private void load ()
    {
    	if (tipLoad.equals("add"))
    	{
    			AlerteMasina.idObiect="";
    			Masina.changeHeader(1, getApplicationContext());
    			edtNumarLocuri.setText("5");
    			if (GetObiecte.persoane!=null && GetObiecte.persoane.size()!=0)
    			{
    				if (GetObiecte.persoanaFizica.isDirty)
    					{
    								if (!GetObiecte.persoanaFizica.judet.equals("")) 
    								{
    								edtJudLoc.setText(GetObiecte.persoanaFizica.judet+","+GetObiecte.persoanaFizica.localitate);
    								judet=GetObiecte.persoanaFizica.judet;
    							   	localitate=GetObiecte.persoanaFizica.localitate;
    								}
    					}
    				else if (GetObiecte.persoanaJuridica.isDirty)
    				{
    					if (!GetObiecte.persoanaJuridica.judet.equals("")) 
						{
						edtJudLoc.setText(GetObiecte.persoanaJuridica.judet+","+GetObiecte.persoanaJuridica.localitate);
						judet=GetObiecte.persoanaJuridica.judet;
					   	localitate=GetObiecte.persoanaJuridica.localitate;
						}
    				}
    			}
				   	radio3.setText(getString(R.string.i536));
					radio3.setTag("7");
    		}
    	else if (tipLoad.equals("view"))
    	{
     		   autovehicul=	GetObiecte.autovehicule.get(MasinileMele.positionId);
     		  Masina.changeHeader(2, getApplicationContext());
    		   edtJudLoc.setText (autovehicul.judet+","+autovehicul.localitate);
			   judet=autovehicul.judet;
			   localitate=autovehicul.localitate;
			   edtMarcaAuto.setText (autovehicul.marcaAuto);
			   edtModelAuto.setText (autovehicul.modelAuto);
			   edtNrInmatriculare.setText(autovehicul.nrInmatriculare);
			   edtSerieSasiu.setText(autovehicul.serieSasiu);
			   autovehicul.idIntern=autovehicul.idIntern;
			   AlerteMasina.idObiect=autovehicul.idIntern;
			   subcategorie = autovehicul.subcategorieAuto;
			   if (autovehicul.subcategorieAuto.equals("Autoturism")) {radio1.setChecked(true);radio3.setText (getString(R.string.i536)); radio3.setTag("7");}
			   else if (autovehicul.subcategorieAuto.equals("Autoturism-de-teren")) {radio2.setChecked(true);radio3.setText (getString(R.string.i536));radio3.setTag("7");}
			   else if (!autovehicul.subcategorieAuto.equals("")) 
			   {
				   radio3.setChecked (true); 
				   String subcategorieMasina = "";
				   if (subcategorie.equals("Autoturism"))
						subcategorieMasina = getString(R.string.i533);
					else if (subcategorie.equals("Automobil-mixt"))
						subcategorieMasina = getString(R.string.i534);
					else if (subcategorie.equals("Autoturism-de-teren"))
						subcategorieMasina = getString(R.string.i535);
					else if (subcategorie.equals("Motocicleta"))
						subcategorieMasina = getString(R.string.i536);
					else if (subcategorie.equals("Moped"))
						subcategorieMasina = getString(R.string.i537);
					else if (subcategorie.equals("Atas"))
						subcategorieMasina = getString(R.string.i538);
					else if (subcategorie.equals("Scuter"))
						subcategorieMasina = getString(R.string.i539);
					else if (subcategorie.equals("ATV"))
						subcategorieMasina = getString(R.string.i540);
					else if (subcategorie.equals("Autosanitara"))
						subcategorieMasina = getString(R.string.i541);
					else if (subcategorie.equals("Autorulota"))
						subcategorieMasina = getString(R.string.i542);
					else if (subcategorie.equals("Microbuz"))
						subcategorieMasina = getString(R.string.i543);
					else if (subcategorie.equals("Autobuz"))
						subcategorieMasina = getString(R.string.i544);
					else if (subcategorie.equals("Autocar"))
						subcategorieMasina = getString(R.string.i545);
					else if (subcategorie.equals("Autoutilitara"))
						subcategorieMasina = getString(R.string.i546);
					else if (subcategorie.equals("Autofurgon"))
						subcategorieMasina = getString(R.string.i547);
					else if (subcategorie.equals("Autocamion"))
						subcategorieMasina = getString(R.string.i548);
					else if (subcategorie.equals("Camion"))
						subcategorieMasina = getString(R.string.i549);
					else if (subcategorie.equals("Autotractor"))
						subcategorieMasina = getString(R.string.i550);
					else if (subcategorie.equals("Tractor-rutier"))
						subcategorieMasina = getString(R.string.i551);
					else if (subcategorie.equals("Remorca"))
						subcategorieMasina = getString(R.string.i552);
					else if (subcategorie.equals("Semiremorca"))
						subcategorieMasina = getString(R.string.i553);
				  radio3.setText(subcategorieMasina); 
				  radio3.setTag(autovehicul.categorieAuto+"");
				} 
			   
			   
			   if (autovehicul.cm3!=-1) 
				   {	
					    edtCm3.setText(autovehicul.cm3+"");
				   }
			   if (autovehicul.putere!=-1) 
			   {
				   edtPutere.setText(autovehicul.putere+" kw");
					autovehicul.putere=autovehicul.putere;
			   }
			   if (autovehicul.nrLocuri!=-1) edtNumarLocuri.setText(autovehicul.nrLocuri+"");
			   if (autovehicul.masaMaxima!=-1) 
			   {
				   edtMasaMax.setText(autovehicul.masaMaxima+" kg");
					autovehicul.masaMaxima=autovehicul.masaMaxima;
			   }
			   if (autovehicul.anFabricatie!=-1) edtAnFabricatie.setText(autovehicul.anFabricatie+"");
			   edtSerieTalon.setText(autovehicul.serieCiv);
			   if (autovehicul.destinatieAuto.equals("interes-personal")) {radio4.setChecked(true);}
			   else if (autovehicul.destinatieAuto.equals("distributie-marfa")) {radio5.setChecked(true);}
			   else if (!autovehicul.destinatieAuto.equals("")) 
			   {
				   radio6.setChecked (true); 
				   String destinatieText = "";
				   if (autovehicul.destinatieAuto.equals("transport-persoane"))
						destinatieText = getString(R.string.i515);
					else if (autovehicul.destinatieAuto.equals("transport-international"))
						destinatieText = getString(R.string.i516);
					else if (autovehicul.destinatieAuto.equals("paza-protectie-interventie"))
						destinatieText = getString(R.string.i517);
					else if (autovehicul.destinatieAuto.equals("taxi"))
						destinatieText = getString(R.string.i518);
					else if (autovehicul.destinatieAuto.equals("maxi-taxi"))
						destinatieText = getString(R.string.i519);
					else if (autovehicul.destinatieAuto.equals("scoala-soferi"))
						destinatieText = getString(R.string.i520);
					else if (autovehicul.destinatieAuto.equals("inchiriere"))
						destinatieText = getString(R.string.i521);
					else if (autovehicul.destinatieAuto.equals("curierat"))
						destinatieText = getString(R.string.i522);
					else if (autovehicul.destinatieAuto.equals("alte-activitati"))
						destinatieText = getString(R.string.i523);
				   radio6.setText(destinatieText);
				   radio6.setTag(autovehicul.destinatieAuto);}
				   
			   if (autovehicul.combustibil.equals("benzina")) {radio7.setChecked(true);}
			   else if (autovehicul.combustibil.equals("motorina")) {radio8.setChecked(true);}
			   else if (autovehicul.combustibil.equals("gpl")){radio9.setChecked (true); radio9.setText(getString(R.string.i527));}
			   else if (autovehicul.combustibil.equals("electric/hibrid")){radio9.setChecked (true); radio9.setText(getString(R.string.i528));}
			   else if (autovehicul.combustibil.equals("fara")){radio9.setChecked (true); radio9.setText(getString(R.string.i529));}
			   if (autovehicul.inLeasing.equals("da"))
			   {
					btn_daLeasing.setTextColor(Color.WHITE);
					btn_nuLeasing.setTextColor(Color.BLACK);
					leasing=true;
					btn_daLeasing.setBackgroundResource(R.drawable.da_nu_da_da_rca);
					btn_nuLeasing.setBackgroundResource(R.drawable.da_nu_da_nu_rca);
					TextView tv = (TextView) findViewById (R.id.tvDenumireFirmaLeasing);
					tv.setVisibility(View.VISIBLE);
					edtFirmaLeasing.setVisibility(View.VISIBLE);	
					edtFirmaLeasing.setText(autovehicul.firmaLeasing);
			   }
			   else
			   {
				   
			   }
				if (edtSerieTalon.getText().toString().length()<7 || edtSerieTalon.getText().toString().length()>10)
	        		 ((ImageView) findViewById(R.id.wrong_text2)).setVisibility(View.VISIBLE);
	        	 else ((ImageView) findViewById(R.id.wrong_text2)).setVisibility(View.GONE);

	        	if (edtSerieSasiu.getText().toString().length()>17 || edtSerieSasiu.getText().toString().length()<3)
	        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
	        	 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
	        	
    	}
    }
    
    private void saveFromFormular()
	   {
		   autovehicul.judet = judet;
		   autovehicul.localitate = localitate;
		   autovehicul.marcaAuto=edtMarcaAuto.getText().toString();   
		   autovehicul.modelAuto = edtModelAuto.getText().toString();
		   int radioChecked = radioCategorie.getCheckedRadioButtonId();
		   radioButtonCategorie = (RadioButton) findViewById(radioChecked);
		   categorie = Integer.parseInt((String) radioButtonCategorie.getTag());
		   subcategorie = radioButtonCategorie.getText().toString();
		   String subcategorieMasina = "";
		   if (subcategorie.equals(getString(R.string.i533)))
				subcategorieMasina = "Autoturism";
			else if (subcategorie.equals(getString(R.string.i534)))
				subcategorieMasina = "Automobil-mixt";
			else if (subcategorie.equals(getString(R.string.i535)))
				subcategorieMasina = "Autoturism-de-teren";
			else if (subcategorie.equals(getString(R.string.i536)))
				subcategorieMasina = "Motocicleta";
			else if (subcategorie.equals(getString(R.string.i537)))
				subcategorieMasina = "Moped";
			else if (subcategorie.equals(getString(R.string.i538)))
				subcategorieMasina = "Atas";
			else if (subcategorie.equals(getString(R.string.i539)))
				subcategorieMasina = "Scuter";
			else if (subcategorie.equals(getString(R.string.i540)))
				subcategorieMasina = "ATV";
			else if (subcategorie.equals(getString(R.string.i541)))
				subcategorieMasina = "Autosanitara";
			else if (subcategorie.equals(getString(R.string.i542)))
				subcategorieMasina = "Autorulota";
			else if (subcategorie.equals(getString(R.string.i543)))
				subcategorieMasina = "Microbuz";
			else if (subcategorie.equals(getString(R.string.i544)))
				subcategorieMasina = "Autobuz";
			else if (subcategorie.equals(getString(R.string.i545)))
				subcategorieMasina = "Autocar";
			else if (subcategorie.equals(getString(R.string.i546)))
				subcategorieMasina = "Autoutilitara";
			else if (subcategorie.equals(getString(R.string.i547)))
				subcategorieMasina = "Autofurgon";
			else if (subcategorie.equals(getString(R.string.i548)))
				subcategorieMasina = "Autocamion";
			else if (subcategorie.equals(getString(R.string.i549)))
				subcategorieMasina = "Camion";
			else if (subcategorie.equals(getString(R.string.i550)))
				subcategorieMasina = "Autotractor";
			else if (subcategorie.equals(getString(R.string.i551)))
				subcategorieMasina = "Tractor-rutier";
			else if (subcategorie.equals(getString(R.string.i552)))
				subcategorieMasina = "Remorca";
			else if (subcategorie.equals(getString(R.string.i553)))
				subcategorieMasina = "Semiremorca";

		   autovehicul.categorieAuto = categorie;
		   if (!subcategorieMasina.equals("")) autovehicul.subcategorieAuto = subcategorieMasina;
		   else autovehicul.subcategorieAuto = subcategorie;
		   radioChecked = radioCombustibil.getCheckedRadioButtonId();
		   radioButtonCombustibil = (RadioButton) findViewById(radioChecked);
		   combustibil = radioButtonCombustibil.getText().toString();
		   String combustibilMasina = "";
		   if (combustibil.equals(getString(R.string.i525)))
			   combustibilMasina  = "benzina";
		   else if (combustibil.equals(getString(R.string.i526)))
			   combustibilMasina  = "motorina";
		   else if (combustibil.equals(getString(R.string.i527)))
			   combustibilMasina  = "gpl";
		   else if (combustibil.equals(getString(R.string.i528)))
			   combustibilMasina  = "electric/hibrid";
		   else if (combustibil.equals(getString(R.string.i529)))
			   combustibilMasina  = "fara";
		   if (!combustibilMasina.equals(""))
			   autovehicul.combustibil = combustibilMasina;
		   else autovehicul.combustibil = combustibil;
		   radioChecked = radioDestinatie.getCheckedRadioButtonId();
		   radioButtonDestinatie = (RadioButton) findViewById(radioChecked);
		   destinatie = radioButtonDestinatie.getText().toString();
		   String destinatieTag = "";
		   if (destinatie.equals(getString(R.string.i513)))
				destinatieTag = "interes-personal";
			else if (destinatie.equals(getString(R.string.i514)))
				destinatieTag = "distributie-marfa";
			else if (destinatie.equals(getString(R.string.i515)))
				destinatieTag = "transport-persoane";
			else if (destinatie.equals(getString(R.string.i516)))
				destinatieTag = "transport-international";
			else if (destinatie.equals(getString(R.string.i517)))
				destinatieTag = "paza-protectie-interventie";
			else if (destinatie.equals(getString(R.string.i518)))
				destinatieTag = "taxi";
			else if (destinatie.equals(getString(R.string.i519)))
				destinatieTag = "maxi-taxi";
			else if (destinatie.equals(getString(R.string.i520)))
				destinatieTag = "scoala-soferi";
			else if (destinatie.equals(getString(R.string.i521)))
				destinatieTag = "inchiriere";
			else if (destinatie.equals(getString(R.string.i522)))
				destinatieTag = "curierat";
			else if (destinatie.equals(getString(R.string.i523)))
				destinatieTag = "alte-activitati";
			if (destinatieTag.equals("")) autovehicul.destinatieAuto = destinatieTag;
			else autovehicul.destinatieAuto = destinatie;
		   autovehicul.destinatieAuto = destinatieTag;
		   autovehicul.nrInmatriculare = edtNrInmatriculare.getText().toString();
		   autovehicul.serieSasiu = edtSerieSasiu.getText().toString();
		   autovehicul.serieSasiu = YTOUtils.replaceInSerieSasiu(autovehicul.serieSasiu);
		   int cm3=-1;
		   if (!edtCm3.getText().toString().equals("")){
		   try {
			   cm3 = Integer.parseInt(edtCm3.getText().toString());
		   }catch (Exception e) {
			// TODO: handle exception
		   }
		   autovehicul.cm3 = cm3;
		   }
		   else autovehicul.cm3 = -1;
		   if (!edtPutere.getText().toString().equals("")) 
 			   try{
 				   autovehicul.putere = Integer.parseInt(edtPutere.getText().toString());
 			   }catch (Exception e){
 				   try{
 				  autovehicul.putere = Integer.parseInt(edtPutere.getText().toString().substring(0,edtPutere.getText().toString().indexOf(' ')));
 				   }catch (Exception f) {
 				   try {
 					   if (edtPutere.getText().toString().length()>9)
 						   try{
 						   	autovehicul.putere = Integer.parseInt(edtPutere.getText().toString().substring(0,9));
 						   }catch (Exception e2) {
								// TODO: handle exception
 						   	autovehicul.putere = -1;
							}
 					   else autovehicul.putere = -1;
				}catch (Exception g) {
					// TODO: handle exception
					  autovehicul.putere = -1;
				}
 				   }
 			   }
 		   else autovehicul.putere = -1;
		  if (!edtNumarLocuri.getText().toString().equals(""))
			  try
		  		{
				  autovehicul.nrLocuri = Integer.parseInt(edtNumarLocuri.getText().toString());
		  		}catch (Exception e) {
					// TODO: handle exception
		  			autovehicul.nrLocuri = -1; 
				}
		   else autovehicul.nrLocuri = -1; 
		   if (!edtMasaMax.getText().toString().equals(""))
 			   try{
 				   autovehicul.masaMaxima = Integer.parseInt(edtMasaMax.getText().toString());
 			   }catch (Exception e){
 				   try{
 				  autovehicul.masaMaxima = Integer.parseInt(edtMasaMax.getText().toString().substring(0,edtMasaMax.getText().toString().indexOf(' ')));
 			   }catch (Exception f) {
 				  try{
 					  if (edtMasaMax.getText().toString().length()>9)
 						  try{
 						  autovehicul.masaMaxima = Integer.parseInt(edtMasaMax.getText().toString().substring(0,9));
 						  }catch (Exception e2) {
							// TODO: handle exception
 							 autovehicul.masaMaxima = -1;
						}
 					 else autovehicul.masaMaxima = -1;
 				  }catch (Exception g){
 				  autovehicul.masaMaxima=-1;
 				  }
			}
 			   }
 		   else autovehicul.masaMaxima = -1;
		   if (!edtAnFabricatie.getText().toString().equals(""))
			   try {
				   autovehicul.anFabricatie = Integer.parseInt(edtAnFabricatie.getText().toString());
			   }catch (Exception e) {
				// TODO: handle exception
				   autovehicul.anFabricatie=-1;
			}
		   else autovehicul.anFabricatie = -1; 
		   autovehicul.serieCiv = edtSerieTalon.getText().toString();
		   if (leasing==true)
		   {
			   autovehicul.inLeasing="da";
			   autovehicul.firmaLeasing=edtFirmaLeasing.getText().toString();
		   }
		   else 
		   {
			   autovehicul.inLeasing="nu";
		   }
		   toJSON = autovehicul.autovehiculToJSON (autovehicul);
	   }
    
    private int getCompletedCount ()
	   {
		   int nr=2;
		   if (edtMarcaAuto.getText().toString().length()!=0) nr++;
		   if (edtModelAuto.getText().toString().length()!=0) nr++;
		   if (edtJudLoc.getText().toString().length()!=0) nr++;
		   if (edtNrInmatriculare.getText().toString().length()!=0)nr++;
		   if (edtSerieSasiu.getText().toString().length()!=0) nr++;
		   if (edtCm3.getText().toString().length()!=0) nr++;
		   if (edtPutere.getText().toString().length()!=0) nr++;
		   if (edtNumarLocuri.getText().toString().length()!=0) nr++;
		   if (edtMasaMax.getText().toString().length()!=0) nr++;
		   if (edtAnFabricatie.getText().toString().length()!=0) nr++;
		   if (edtSerieTalon.getText().toString().length()!=0) nr++;
		   if (autovehicul.inLeasing.equals("da"))
			   if (edtFirmaLeasing.getText().toString().length() !=0 ) nr ++;
		   return nr;
	   }
    
    private void setVisibilityByCategory (int categorie)
    {
    	if (categorie==8)
    	{
    		edtPutere.setVisibility(View.GONE);
    		edtCm3.setVisibility(View.GONE);
    		textCM3.setVisibility(View.GONE);
    		textPutere.setVisibility(View.GONE);
    		edtPutere.setText("0");
    		edtCm3.setText("0");
    	}
    	else
    	{
    		edtPutere.setVisibility(View.VISIBLE);
    		edtCm3.setVisibility(View.VISIBLE);
    		textCM3.setVisibility(View.VISIBLE);
    		textPutere.setVisibility(View.VISIBLE);
    		if (autovehicul.putere>0)
    			edtPutere.setText(autovehicul.putere+"");
    		else edtPutere.setText("");
    		if (autovehicul.cm3>0)
    			edtCm3.setText(autovehicul.cm3+"");
    		else edtCm3.setText("");
    	}
    }
    


}