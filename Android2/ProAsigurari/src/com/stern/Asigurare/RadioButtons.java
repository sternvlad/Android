package com.stern.Asigurare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class RadioButtons extends Activity {
	RadioGroup radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5,radioGroup6,radioGroup7;
	RadioButton radio1,radio2,radio3,radio4,radio5,radio6,radio7,radio8,radio9,radio10,radio11,radio12,radio13,radio14,radio15,radio16,radio17,radio18,radio19,radio20,radio21,radioSelected;
	Button okButton;
	OnCheckedChangeListener listener1,listener2,listener3,listener4,listener5,listener6,listener7;
	int radioGroupSelected;
	RelativeLayout layout;
	public static String tipDate="",date="",tag="";
	TextView titlu_pag,titlu_lay;
	public void onCreate(Bundle savedInstanceState) {
//		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//    	int width = display.getWidth(); 
    	Window window = getWindow(); 
//    	if (YTOUtils.isTablet(this)) {    	
//	    	window.setGravity(Gravity.CENTER); 
//	    	window.setLayout(400, 580);
//    	}
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.radio_buttons);
		
//		Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");

		
		titlu_pag = (TextView) findViewById (R.id.titlu_pagina_radio);
		titlu_lay = (TextView) findViewById (R.id.titlu_lay_radio);
		radioGroup1 = (RadioGroup) findViewById (R.id.radio_group1);
		radioGroup2 = (RadioGroup) findViewById (R.id.radio_group2);
		radioGroup3 = (RadioGroup) findViewById (R.id.radio_group3);
		radioGroup4 = (RadioGroup) findViewById (R.id.radio_group4);
		radioGroup5 = (RadioGroup) findViewById (R.id.radio_group5);
		radioGroup6 = (RadioGroup) findViewById (R.id.radio_group6);
		radioGroup7 = (RadioGroup) findViewById (R.id.radio_group7);
		layout = (RelativeLayout) findViewById (R.id.layout_popup);
		
		listener1 = new OnCheckedChangeListener(){
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					radioGroup2.setOnCheckedChangeListener(null);
					radioGroup3.setOnCheckedChangeListener(null);
					radioGroup4.setOnCheckedChangeListener(null);
					radioGroup5.setOnCheckedChangeListener(null);
					radioGroup6.setOnCheckedChangeListener(null);
					radioGroup7.setOnCheckedChangeListener(null);
					radioGroup2.clearCheck();
					radioGroup3.clearCheck();
					radioGroup4.clearCheck();
					radioGroup5.clearCheck();
					radioGroup6.clearCheck();
					radioGroup7.clearCheck();
					radioGroup2.setOnCheckedChangeListener(listener2);
					radioGroup3.setOnCheckedChangeListener(listener3);
					radioGroup4.setOnCheckedChangeListener(listener4);
					radioGroup5.setOnCheckedChangeListener(listener5); 
					radioGroup6.setOnCheckedChangeListener(listener6);
					radioGroup7.setOnCheckedChangeListener(listener7);
					radioGroupSelected = 1;
				}
			};

		
		listener2 = new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				radioGroup1.setOnCheckedChangeListener(null);
				radioGroup3.setOnCheckedChangeListener(null);
				radioGroup4.setOnCheckedChangeListener(null);
				radioGroup5.setOnCheckedChangeListener(null);
				radioGroup6.setOnCheckedChangeListener(null);
				radioGroup7.setOnCheckedChangeListener(null);
				radioGroup1.clearCheck();
				radioGroup3.clearCheck();
				radioGroup4.clearCheck();
				radioGroup5.clearCheck();
				radioGroup6.clearCheck();
				radioGroup7.clearCheck();
				radioGroup1.setOnCheckedChangeListener(listener1);
				radioGroup3.setOnCheckedChangeListener(listener3);
				radioGroup4.setOnCheckedChangeListener(listener4);
				radioGroup5.setOnCheckedChangeListener(listener5);
				radioGroup6.setOnCheckedChangeListener(listener6);
				radioGroup7.setOnCheckedChangeListener(listener7);
				radioGroupSelected = 2;
			}
		};
		
			
				
		listener3 = new OnCheckedChangeListener() {
					
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				radioGroup2.setOnCheckedChangeListener(null);
				radioGroup1.setOnCheckedChangeListener(null);
				radioGroup4.setOnCheckedChangeListener(null);
				radioGroup5.setOnCheckedChangeListener(null);
				radioGroup6.setOnCheckedChangeListener(null);
				radioGroup7.setOnCheckedChangeListener(null);
				radioGroup2.clearCheck();
				radioGroup1.clearCheck();
				radioGroup4.clearCheck();
				radioGroup5.clearCheck();
				radioGroup6.clearCheck();
				radioGroup7.clearCheck();
				radioGroup2.setOnCheckedChangeListener(listener2);
				radioGroup1.setOnCheckedChangeListener(listener1);
				radioGroup4.setOnCheckedChangeListener(listener4);
				radioGroup5.setOnCheckedChangeListener(listener5);
				radioGroup6.setOnCheckedChangeListener(listener6);
				radioGroup7.setOnCheckedChangeListener(listener7);
				radioGroupSelected = 3;
			}
		};
		
		listener4 = new OnCheckedChangeListener(){
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				radioGroup2.setOnCheckedChangeListener(null);
				radioGroup3.setOnCheckedChangeListener(null);
				radioGroup1.setOnCheckedChangeListener(null);
				radioGroup5.setOnCheckedChangeListener(null);
				radioGroup6.setOnCheckedChangeListener(null);
				radioGroup7.setOnCheckedChangeListener(null);
				radioGroup2.clearCheck();
				radioGroup3.clearCheck();
				radioGroup1.clearCheck();
				radioGroup5.clearCheck();
				radioGroup6.clearCheck();
				radioGroup7.clearCheck();
				radioGroup2.setOnCheckedChangeListener(listener2);
				radioGroup3.setOnCheckedChangeListener(listener3);
				radioGroup1.setOnCheckedChangeListener(listener1);
				radioGroup5.setOnCheckedChangeListener(listener5);
				radioGroup6.setOnCheckedChangeListener(listener6);
				radioGroup7.setOnCheckedChangeListener(listener7);
				radioGroupSelected = 4;
			}
		};

	
	listener5 = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			radioGroup2.setOnCheckedChangeListener(null);
			radioGroup3.setOnCheckedChangeListener(null);
			radioGroup4.setOnCheckedChangeListener(null);
			radioGroup1.setOnCheckedChangeListener(null);
			radioGroup6.setOnCheckedChangeListener(null);
			radioGroup7.setOnCheckedChangeListener(null);
			radioGroup2.clearCheck();
			radioGroup3.clearCheck();
			radioGroup4.clearCheck();
			radioGroup1.clearCheck();
			radioGroup6.clearCheck();
			radioGroup7.clearCheck();
			radioGroup2.setOnCheckedChangeListener(listener2);
			radioGroup3.setOnCheckedChangeListener(listener3);
			radioGroup4.setOnCheckedChangeListener(listener4);
			radioGroup1.setOnCheckedChangeListener(listener1);
			radioGroup6.setOnCheckedChangeListener(listener6);
			radioGroup7.setOnCheckedChangeListener(listener7);
			radioGroupSelected = 5;
		}
	};
	
		
			
	listener6 = new OnCheckedChangeListener() {
				
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			radioGroup2.setOnCheckedChangeListener(null);
			radioGroup3.setOnCheckedChangeListener(null);
			radioGroup4.setOnCheckedChangeListener(null);
			radioGroup5.setOnCheckedChangeListener(null);
			radioGroup1.setOnCheckedChangeListener(null);
			radioGroup7.setOnCheckedChangeListener(null);
			radioGroup2.clearCheck();
			radioGroup3.clearCheck();
			radioGroup4.clearCheck();
			radioGroup5.clearCheck();
			radioGroup1.clearCheck();
			radioGroup7.clearCheck();
			radioGroup2.setOnCheckedChangeListener(listener2);
			radioGroup3.setOnCheckedChangeListener(listener3);
			radioGroup4.setOnCheckedChangeListener(listener4);
			radioGroup5.setOnCheckedChangeListener(listener5);
			radioGroup1.setOnCheckedChangeListener(listener6);
			radioGroup7.setOnCheckedChangeListener(listener7);
			radioGroupSelected = 6;
		}
	};
	
	
	listener7 = new OnCheckedChangeListener() {
				
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			radioGroup2.setOnCheckedChangeListener(null);
			radioGroup3.setOnCheckedChangeListener(null);
			radioGroup4.setOnCheckedChangeListener(null);
			radioGroup5.setOnCheckedChangeListener(null);
			radioGroup6.setOnCheckedChangeListener(null);
			radioGroup1.setOnCheckedChangeListener(null);
			radioGroup2.clearCheck();
			radioGroup3.clearCheck();
			radioGroup4.clearCheck();
			radioGroup5.clearCheck();
			radioGroup6.clearCheck();
			radioGroup1.clearCheck();
			radioGroup2.setOnCheckedChangeListener(listener2);
			radioGroup3.setOnCheckedChangeListener(listener3);
			radioGroup4.setOnCheckedChangeListener(listener4);
			radioGroup5.setOnCheckedChangeListener(listener5);
			radioGroup6.setOnCheckedChangeListener(listener6);
			radioGroup1.setOnCheckedChangeListener(listener7);
			radioGroupSelected = 7;
		}
	};
	
		
		radioGroup1.setOnCheckedChangeListener(listener1);
		radioGroup2.setOnCheckedChangeListener(listener2);
		radioGroup3.setOnCheckedChangeListener(listener3);
		radioGroup4.setOnCheckedChangeListener(listener4);
		radioGroup5.setOnCheckedChangeListener(listener5);
		radioGroup6.setOnCheckedChangeListener(listener6);
		radioGroup7.setOnCheckedChangeListener(listener7);



		radio1 = (RadioButton) findViewById (R.id.r1);
		radio2 = (RadioButton) findViewById (R.id.r2);
		radio3 = (RadioButton) findViewById (R.id.r3);
		radio4 = (RadioButton) findViewById (R.id.r4);
		radio5 = (RadioButton) findViewById (R.id.r5);
		radio6 = (RadioButton) findViewById (R.id.r6);
		radio7 = (RadioButton) findViewById (R.id.r7);
		radio8 = (RadioButton) findViewById (R.id.r8);
		radio9 = (RadioButton) findViewById (R.id.r9);
		radio10 = (RadioButton) findViewById (R.id.r10);
		radio11 = (RadioButton) findViewById (R.id.r11);
		radio12 = (RadioButton) findViewById (R.id.r12);
		radio13 = (RadioButton) findViewById (R.id.r13);
		radio14 = (RadioButton) findViewById (R.id.r14);
		radio15 = (RadioButton) findViewById (R.id.r15);
		radio16 = (RadioButton) findViewById (R.id.r16);
		radio17 = (RadioButton) findViewById (R.id.r17);
		radio18 = (RadioButton) findViewById (R.id.r18);
		radio19 = (RadioButton) findViewById (R.id.r19);
		radio20 = (RadioButton) findViewById (R.id.r20);
		radio21 = (RadioButton) findViewById (R.id.r21);
		okButton = (Button) findViewById (R.id.ok_button_radio);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				save();
				finish();
			}
		});
		
		load ();
	}
	
	public void onBackPressed ()
	{
		save();
		finish();
	}

	private void load ()
	{
		if (tipDate.equals("StructuraLocuinta")){
			titlu_pag.setText(getString(R.string.i364));
			titlu_lay.setText (getString(R.string.i503));
			radio1.setTag ("beton-armat");
			radio1.setText (getString(R.string.i504));
			radio2.setTag ("beton");
			radio2.setText (getString(R.string.i505));
			radio3.setTag ("bca");
			radio3.setText (getString(R.string.i506));
			radio4.setTag ("caramida");
			radio4.setText (getString(R.string.i507));
			radio4.setPadding(0, 0, 0, 16);
			radio5.setTag ("caramida-nearsa");
			radio5.setText (getString(R.string.i508));
			radio6.setTag ("chirpici-paianta");
			radio6.setText (getString(R.string.i509));
			radio7.setTag ("lemn");
			radio7.setText (getString(R.string.i510));
			radio8.setTag ("zidarie-lemn");
			radio8.setText (getString(R.string.i511));
			radio9.setVisibility(View.GONE);
			radio10.setVisibility(View.GONE);
			radio11.setVisibility(View.GONE);
			radio12.setVisibility(View.GONE);
			radio13.setVisibility(View.GONE);
			radio14.setVisibility(View.GONE);
			radio15.setVisibility(View.GONE);
			radio16.setVisibility(View.GONE);
			radio17.setVisibility(View.GONE);
			radio18.setVisibility(View.GONE);
			radio19.setVisibility(View.GONE);
			radio20.setVisibility(View.GONE);
			radio21.setVisibility(View.GONE);
			if (date.equals("beton-armat")) {radio1.setChecked(true); radioGroupSelected=1;}
			if (date.equals("beton")) {radio2.setChecked(true); radioGroupSelected=1;}
			if (date.equals("bca")) {radio3.setChecked(true); radioGroupSelected=1;}
			if (date.equals("caramida")) {radio4.setChecked(true);radioGroupSelected=2;}
			if (date.equals("caramida-nearsa")) {radio5.setChecked(true);radioGroupSelected=2;}
			if (date.equals("chirpici-paianta")) {radio6.setChecked(true);radioGroupSelected=2;}
			if (date.equals("lemn")) {radio7.setChecked(true);radioGroupSelected=3;}
			if (date.equals("zidarie-lemn")) {radio8.setChecked(true);radioGroupSelected=3;}
		}
		else if (tipDate.equals("Categorie")){//trebuie sa fac ceva cu tagul
			titlu_pag.setText(getString(R.string.i531));
			titlu_lay.setText(getString(R.string.i532));
			okButton.setBackgroundResource(R.drawable.ok_button_rca);
			layout.setBackgroundResource(R.drawable.popup_rca);
			radio1.setBackgroundResource(R.drawable.button_radio_masina);
			radio2.setBackgroundResource(R.drawable.button_radio_masina);
			radio3.setBackgroundResource(R.drawable.button_radio_masina);
			radio4.setBackgroundResource(R.drawable.button_radio_masina);
			radio5.setBackgroundResource(R.drawable.button_radio_masina);
			radio6.setBackgroundResource(R.drawable.button_radio_masina);
			radio7.setBackgroundResource(R.drawable.button_radio_masina);
			radio8.setBackgroundResource(R.drawable.button_radio_masina);
			radio9.setBackgroundResource(R.drawable.button_radio_masina);
			radio10.setBackgroundResource(R.drawable.button_radio_masina);
			radio11.setBackgroundResource(R.drawable.button_radio_masina);
			radio12.setBackgroundResource(R.drawable.button_radio_masina);
			radio13.setBackgroundResource(R.drawable.button_radio_masina);
			radio14.setBackgroundResource(R.drawable.button_radio_masina);
			radio15.setBackgroundResource(R.drawable.button_radio_masina);
			radio16.setBackgroundResource(R.drawable.button_radio_masina);
			radio17.setBackgroundResource(R.drawable.button_radio_masina);
			radio18.setBackgroundResource(R.drawable.button_radio_masina);
			radio19.setBackgroundResource(R.drawable.button_radio_masina);
			radio20.setBackgroundResource(R.drawable.button_radio_masina);
			radio21.setBackgroundResource(R.drawable.button_radio_masina);
			radio1.setTag ("Autoturism");
			radio1.setTag  (R.string.tag,"1");
			radio1.setText(getString(R.string.i533));
			radio2.setTag ("Automobil-mixt");
			radio2.setTag  (R.string.tag,"1");
			radio2.setText(getString(R.string.i534));
			radio3.setTag ("Autoturism-de-teren");
			radio3.setTag  (R.string.tag,"1");
			radio3.setText(getString(R.string.i535));
			radio4.setTag ("Motocicleta");
			radio4.setTag  (R.string.tag,"7");
			radio4.setText(getString(R.string.i536));
			radio5.setTag ("Moped");
			radio5.setTag  (R.string.tag,"7");
			radio5.setText(getString(R.string.i537));
			radio6.setTag ("Atas");
			radio6.setTag  (R.string.tag,"7");
			radio6.setText(getString(R.string.i538));
			radio7.setTag ("Scuter");
			radio7.setTag  (R.string.tag,"7");
			radio7.setText(getString(R.string.i539));
			radio8.setTag ("ATV");
			radio8.setTag  (R.string.tag,"7");
			radio8.setText(getString(R.string.i540));
			radio9.setTag ("Autosanitara");
			radio9.setTag  (R.string.tag,"2");
			radio9.setText(getString(R.string.i541));
			radio10.setTag ("Autorulota");
			radio10.setTag  (R.string.tag,"2");
			radio10.setText(getString(R.string.i542));
			radio11.setTag ("Microbuz");
			radio11.setTag  (R.string.tag,"3");
			radio11.setText(getString(R.string.i543));
			radio12.setTag ("Autobuz");
			radio12.setTag  (R.string.tag,"3");
			radio12.setText(getString(R.string.i544));
			radio13.setTag ("Autocar");
			radio13.setTag  (R.string.tag,"3");
			radio13.setText(getString(R.string.i545));
			radio14.setTag ("Autoutilitara");
			radio14.setTag  (R.string.tag,"4");
			radio14.setText(getString(R.string.i546));
			radio15.setTag ("Autofurgon");
			radio15.setTag  (R.string.tag,"4");
			radio15.setText(getString(R.string.i547));
			radio16.setTag ("Autocamion");
			radio16.setTag  (R.string.tag,"4");
			radio16.setText(getString(R.string.i548));
			radio17.setTag ("Camion");
			radio17.setTag  (R.string.tag,"4");
			radio17.setText(getString(R.string.i549));
			radio18.setTag ("Autotractor");
			radio18.setTag  (R.string.tag,"5");
			radio18.setText(getString(R.string.i550));
			radio19.setTag ("Tractor-rutier");
			radio19.setTag  (R.string.tag,"6");
			radio19.setText(getString(R.string.i551));
			radio20.setTag ("Remorca");
			radio20.setTag  (R.string.tag,"8");
			radio20.setText(getString(R.string.i552));
			radio21.setTag ("Semiremorca");
			radio21.setTag  (R.string.tag,"8");
			radio21.setText(getString(R.string.i553));
			if (date.equals("Autoturism")) {radio1.setChecked(true); radioGroupSelected=1;}
			if (date.equals("Automobil-mixt")) {radio2.setChecked(true); radioGroupSelected=1;}
			if (date.equals("Autoturism-de-teren")) {radio3.setChecked(true); radioGroupSelected=1;}
			if (date.equals("Motocicleta")) {radio4.setChecked(true);radioGroupSelected=2;}
			if (date.equals("Moped")) {radio5.setChecked(true);radioGroupSelected=2;}
			if (date.equals("Atas")) {radio6.setChecked(true);radioGroupSelected=2;}
			if (date.equals("Scuter")) {radio7.setChecked(true);radioGroupSelected=3;}
			if (date.equals("ATV")) {radio8.setChecked(true);radioGroupSelected=3;}
			if (date.equals("Autosanitara")) {radio9.setChecked(true); radioGroupSelected=3;}
			if (date.equals("Autorulota")) {radio10.setChecked(true); radioGroupSelected=4;}
			if (date.equals("Microbuz")) {radio11.setChecked(true);radioGroupSelected=4;}
			if (date.equals("Autobuz")) {radio12.setChecked(true);radioGroupSelected=4;}
			if (date.equals("Autocar")) {radio13.setChecked(true);radioGroupSelected=5;}
			if (date.equals("Autoutilitara")) {radio14.setChecked(true);radioGroupSelected=5;}
			if (date.equals("Aautofurgon")) {radio15.setChecked(true);radioGroupSelected=5;}
			if (date.equals("Autocamion")) {radio16.setChecked(true); radioGroupSelected=6;}
			if (date.equals("Camion")) {radio17.setChecked(true); radioGroupSelected=6;}
			if (date.equals("Autotractor")) {radio18.setChecked(true);radioGroupSelected=6;}
			if (date.equals("Tractor-rutier")) {radio19.setChecked(true);radioGroupSelected=7;}
			if (date.equals("Remorca")) {radio20.setChecked(true);radioGroupSelected=7;}
			if (date.equals("Semiremorca")) {radio21.setChecked(true);radioGroupSelected=7;}
		}
		else if (tipDate.equals("Destinatie"))
		{
			titlu_pag.setText(getString(R.string.i524));
			titlu_lay.setText(getString(R.string.i512));
			okButton.setBackgroundResource(R.drawable.ok_button_rca);
			layout.setBackgroundResource(R.drawable.popup_rca);
			radio1.setBackgroundResource(R.drawable.button_radio_masina);
			radio2.setBackgroundResource(R.drawable.button_radio_masina);
			radio3.setBackgroundResource(R.drawable.button_radio_masina);
			radio4.setBackgroundResource(R.drawable.button_radio_masina);
			radio5.setBackgroundResource(R.drawable.button_radio_masina);
			radio6.setBackgroundResource(R.drawable.button_radio_masina);
			radio7.setBackgroundResource(R.drawable.button_radio_masina);
			radio8.setBackgroundResource(R.drawable.button_radio_masina);
			radio9.setBackgroundResource(R.drawable.button_radio_masina);
			radio10.setBackgroundResource(R.drawable.button_radio_masina);
			radio11.setBackgroundResource(R.drawable.button_radio_masina);
			radio1.setText (getString(R.string.i513));
			radio1.setTag ("interes-personal");
			radio2.setText (getString(R.string.i514));
			radio2.setTag("distributie-marfa");
			radio3.setText (getString(R.string.i515));
			radio3.setTag ("transport-persoane");
			radio4.setText (getString(R.string.i516));
			radio4.setTag ("transport-international");
			radio5.setText (getString(R.string.i517));
			radio5.setTag ("paza-protectie-interventie");
			radio6.setText (getString(R.string.i518));
			radio6.setTag ("taxi");
			radio7.setText (getString(R.string.i519));
			radio7.setTag ("maxi-taxi");
			radio8.setText (getString(R.string.i520));
			radio8.setTag ("scoala-soferi");
			radio9.setText(getString(R.string.i521));
			radio9.setTag("inchiriere");
			radio10.setText(getString(R.string.i522));
			radio10.setTag("curierat");
			radio11.setText(getString(R.string.i523));
			radio11.setTag("alte-activitati");
			radio12.setVisibility(View.GONE);
			radio13.setVisibility(View.GONE);
			radio14.setVisibility(View.GONE);
			radio15.setVisibility(View.GONE);
			radio16.setVisibility(View.GONE);
			radio17.setVisibility(View.GONE);
			radio18.setVisibility(View.GONE);
			radio19.setVisibility(View.GONE);
			radio20.setVisibility(View.GONE);
			radio21.setVisibility(View.GONE);
			if (date.equals("interes-personal")) {radio1.setChecked(true); radioGroupSelected=1;}
			if (date.equals("distributie-marfa")) {radio2.setChecked(true); radioGroupSelected=1;}
			if (date.equals("transport-persoane")) {radio3.setChecked(true); radioGroupSelected=1;}
			if (date.equals("transport-international")) {radio4.setChecked(true);radioGroupSelected=2;}
			if (date.equals("paza-protectie-interventie")) {radio5.setChecked(true);radioGroupSelected=2;}
			if (date.equals("taxi")) {radio6.setChecked(true);radioGroupSelected=2;}
			if (date.equals("maxi-taxi")) {radio7.setChecked(true);radioGroupSelected=3;}
			if (date.equals("scoala-soferi")) {radio8.setChecked(true);radioGroupSelected=3;}
			if (date.equals("inchiriere")) {radio9.setChecked(true); radioGroupSelected=3;}
			if (date.equals("curierat")) {radio10.setChecked(true); radioGroupSelected=4;}
			if (date.equals("alte-activitati")) {radio11.setChecked(true);radioGroupSelected=4;}
		}
		else if (tipDate.equals("Combustibil"))
		{
			titlu_pag.setText(getString(R.string.i36));
			titlu_lay.setText(getString(R.string.i565));
			okButton.setBackgroundResource(R.drawable.ok_button_rca);
			layout.setBackgroundResource(R.drawable.popup_rca);
			radio1.setBackgroundResource(R.drawable.button_radio_masina);
			radio2.setBackgroundResource(R.drawable.button_radio_masina);
			radio3.setBackgroundResource(R.drawable.button_radio_masina);
			radio4.setBackgroundResource(R.drawable.button_radio_masina);
			radio5.setBackgroundResource(R.drawable.button_radio_masina);
			radio1.setTag ("benzina");
			radio1.setText(getString(R.string.i525));
			radio2.setTag ("motorina");
			radio2.setText(getString(R.string.i526));
			radio3.setTag ("gpl");
			radio3.setText(getString(R.string.i527));
			radio4.setTag ("electric/hibrid");
			radio4.setText(getString(R.string.i528));
			radio5.setTag ("fara");
			radio5.setText(getString(R.string.i529));
			radio6.setVisibility(View.GONE);
			radio7.setVisibility(View.GONE);
			radio8.setVisibility(View.GONE);
			radio9.setVisibility(View.GONE);
			radio10.setVisibility(View.GONE);
			radio11.setVisibility(View.GONE);
			radio12.setVisibility(View.GONE);
			radio13.setVisibility(View.GONE);
			radio14.setVisibility(View.GONE);
			radio15.setVisibility(View.GONE);
			radio16.setVisibility(View.GONE);
			radio17.setVisibility(View.GONE);
			radio18.setVisibility(View.GONE);
			radio19.setVisibility(View.GONE);
			radio20.setVisibility(View.GONE);
			radio21.setVisibility(View.GONE);
			if (date.equals("benzina")) {radio1.setChecked(true); radioGroupSelected=1;}
			if (date.equals("motorina")) {radio2.setChecked(true); radioGroupSelected=1;}
			if (date.equals("gpl")) {radio3.setChecked(true); radioGroupSelected=1;}
			if (date.equals("electric/hibrid")) {radio4.setChecked(true);radioGroupSelected=2;}
			if (date.equals("fara")) {radio5.setChecked(true);radioGroupSelected=2;}
		}
		else if (tipDate.equals("CASCO"))
		{
			titlu_pag.setText(getString(R.string.i446));
			titlu_lay.setText(getString(R.string.i530));
			okButton.setBackgroundResource(R.drawable.ok_button_rca);
			layout.setBackgroundResource(R.drawable.popup_rca);
			radio1.setBackgroundResource(R.drawable.button_radio_masina);
			radio2.setBackgroundResource(R.drawable.button_radio_masina);
			radio3.setBackgroundResource(R.drawable.button_radio_masina);
			radio4.setBackgroundResource(R.drawable.button_radio_masina);
			radio5.setBackgroundResource(R.drawable.button_radio_masina);
			radio6.setBackgroundResource(R.drawable.button_radio_masina);
			radio7.setBackgroundResource(R.drawable.button_radio_masina);
			radio8.setBackgroundResource(R.drawable.button_radio_masina);
			radio9.setBackgroundResource(R.drawable.button_radio_masina);
			radio10.setVisibility(View.GONE);
			radio11.setVisibility(View.GONE);
			radio12.setVisibility(View.GONE);
			radio13.setVisibility(View.GONE);
			radio14.setVisibility(View.GONE);
			radio15.setVisibility(View.GONE);
			radio16.setVisibility(View.GONE);
			radio17.setVisibility(View.GONE);
			radio18.setVisibility(View.GONE);
			radio19.setVisibility(View.GONE);
			radio20.setVisibility(View.GONE);
			radio21.setVisibility(View.GONE);
			radio1.setText("Allianz");
			radio2.setText("Asirom");
			radio3.setText("Astra");
			radio4.setText("Carpatica");
			radio5.setText("Euroins");
			radio6.setText("Generali");
			radio7.setText("Groupama");
			radio8.setText("Omniasig");
			radio9.setText("Uniqa");
			radio1.setTag("Allianz");
			radio2.setTag("Asirom");
			radio3.setTag("Astra");
			radio4.setTag("Carpatica");
			radio5.setTag("Euroins");
			radio6.setTag("Generali");
			radio7.setTag("Groupama");
			radio8.setTag("Omniasig");
			radio9.setTag("Uniqa");
			if (date.equals("Allianz")) {radio1.setChecked(true); radioGroupSelected=1;}
			if (date.equals("Asirom")) {radio2.setChecked(true); radioGroupSelected=1;}
			if (date.equals("Astra")) {radio3.setChecked(true); radioGroupSelected=1;}
			if (date.equals("Carpatica")) {radio4.setChecked(true);radioGroupSelected=2;}
			if (date.equals("Euroins")) {radio5.setChecked(true);radioGroupSelected=2;}
			if (date.equals("Generali")) {radio6.setChecked(true);radioGroupSelected=2;}
			if (date.equals("Groupama")) {radio7.setChecked(true);radioGroupSelected=3;}
			if (date.equals("Omniasig")) {radio8.setChecked(true);radioGroupSelected=3;}
			if (date.equals("Uniqa")) {radio9.setChecked(true); radioGroupSelected=3;}
		}
	}
	
	private void save ()
	{
		if (radioGroupSelected==1)
		{
			int radioChecked = radioGroup1.getCheckedRadioButtonId();
			radioSelected = (RadioButton) findViewById(radioChecked);
			date = radioSelected.getTag().toString();
			if (tipDate.equals("Categorie")) tag = radioSelected.getTag(R.string.tag).toString();
		}
		else if (radioGroupSelected==2)
		{
			int radioChecked = radioGroup2.getCheckedRadioButtonId();
			radioSelected = (RadioButton) findViewById(radioChecked);
			date = radioSelected.getTag().toString();
			if (tipDate.equals("Categorie")) tag = radioSelected.getTag(R.string.tag).toString();
		}
		else if (radioGroupSelected==3)
		{
			int radioChecked = radioGroup3.getCheckedRadioButtonId();
			radioSelected = (RadioButton) findViewById(radioChecked);
			date = radioSelected.getTag().toString();
			if (tipDate.equals("Categorie") ) tag = radioSelected.getTag(R.string.tag).toString();
		}
		else if (radioGroupSelected==4)
		{
			int radioChecked = radioGroup4.getCheckedRadioButtonId();
			radioSelected = (RadioButton) findViewById(radioChecked);
			date = radioSelected.getTag().toString();
			if (tipDate.equals("Categorie")) tag = radioSelected.getTag(R.string.tag).toString();
		}
		else if (radioGroupSelected==5)
		{
			int radioChecked = radioGroup5.getCheckedRadioButtonId();
			radioSelected = (RadioButton) findViewById(radioChecked);
			date = radioSelected.getTag().toString();
			if (tipDate.equals("Categorie")) tag = radioSelected.getTag(R.string.tag).toString();
		}
		else if (radioGroupSelected==6)
		{
			int radioChecked = radioGroup6.getCheckedRadioButtonId();
			radioSelected = (RadioButton) findViewById(radioChecked);
			date = radioSelected.getTag().toString();
			if (tipDate.equals("Categorie")) tag = radioSelected.getTag(R.string.tag).toString();
		}
		
		else if (radioGroupSelected==7)
		{
			int radioChecked = radioGroup7.getCheckedRadioButtonId();
			radioSelected = (RadioButton) findViewById(radioChecked);
			date = radioSelected.getTag().toString();
			if (tipDate.equals("Categorie")) tag = radioSelected.getTag(R.string.tag).toString();
		}
	}

}
