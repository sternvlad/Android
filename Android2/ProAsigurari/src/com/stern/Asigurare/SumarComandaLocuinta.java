package com.stern.Asigurare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SumarComandaLocuinta extends Activity{
	public static int durata;
	
	TextView tvAsig,tvSumaAsig,tvSupStruc;
	TextView tvNumeAsig,tvCnpASig,tvJudLocAsig;
	TextView tvPretAsig;
	Button btnContinua,btnConditiiPdf,btnSumar;
	ImageView imgFirmaAsig;
	AppSettings sett;
    
	public void onCreate(Bundle savedInstanceState) {
//		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//    	int width = display.getWidth(); 
    	Window window = getWindow(); 
//    	if (YTOUtils.isTablet(this)) {    	
//	    	window.setGravity(Gravity.CENTER); 
//	    	window.setLayout(320, 480);
//    	}
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sumar_locuinta);
        
        MainController.tvTitlu.setText(getString(R.string.i465));

        sett= AppSettings.get(this);
        
        tvAsig = (TextView) findViewById (R.id.tv_asig_loc);
        tvSumaAsig = (TextView) findViewById (R.id.suma_asig);
        tvSupStruc = (TextView) findViewById (R.id.tv_suprafata_structura);
        
        tvNumeAsig = (TextView) findViewById (R.id.nume_asigurat);
        tvCnpASig = (TextView) findViewById (R.id.cnp_asigurat);
        tvJudLocAsig = (TextView) findViewById (R.id.judloc_asigurat);
        
        tvPretAsig = (TextView) findViewById (R.id.pret_asigurare);
        
        imgFirmaAsig = (ImageView) findViewById (R.id.image_firma_asig);
        
        btnContinua = (Button) findViewById (R.id.btn_continua);
        
        btnContinua.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent finalCom = new Intent(getParent(), FinalizareComandaLocuinta.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("FinalizareComanda", finalCom);
			}
		});
        
        loadElementsByPosition();
		if (sett.getLanguage().equals("hu")&& !YTOUtils.isTablet(getParent().getParent()))
		{
			((TextView) findViewById(R.id.text_header1)).setTextSize(20);
			((TextView) findViewById(R.id.text_header2)).setTextSize(11);
			((TextView) findViewById(R.id.text_header3)).setTextSize(11);
		}else if (sett.getLanguage().equals("hu")&& YTOUtils.isTablet(getParent().getParent()))
		{
			((TextView) findViewById(R.id.text_header1)).setTextSize(45);
			((TextView) findViewById(R.id.text_header2)).setTextSize(30);
			((TextView) findViewById(R.id.text_header3)).setTextSize(30);
		}
    }
	
	private void loadElementsByPosition()
	{

		
		Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
		
		tvAsig.setTypeface(Typeface.create(tf,Typeface.BOLD));
		tvSumaAsig.setText (getString(R.string.i126)+" " + LocuinteleMele.locuintaCurenta.sumaAsigurata);
		tvSupStruc.setText (LocuinteleMele.locuintaCurenta.suprafataUtila +" mp, "+LocuinteleMele.locuintaCurenta.structuraLocuinta);
		
		tvNumeAsig.setText(AltePersoane.persoanaAsigurata.nume);
		tvNumeAsig.setTypeface(Typeface.create(tf,Typeface.BOLD));
		tvCnpASig.setText (AltePersoane.persoanaAsigurata.codUnic);
		tvJudLocAsig.setText (AltePersoane.persoanaAsigurata.judet+","+AltePersoane.persoanaAsigurata.localitate);
			
		tvPretAsig.setText (CallWebServiceLoc.oferta.get(CalculatieLocuinta.positionId).Prima+" EUR");
		sett.updatePrima(CallWebServiceLoc.oferta.get(CalculatieLocuinta.positionId).Prima+
				"");
		tvPretAsig.setTypeface(Typeface.create(tf,Typeface.BOLD));
		sett.updateCompanie(CallWebServiceLoc.oferta.get(CalculatieLocuinta.positionId).Companie);
		String numeComp = CallWebServiceLoc.oferta.get(CalculatieLocuinta.positionId).Companie.toLowerCase();
		int resID = getResources().getIdentifier(numeComp, "drawable", getPackageName());
			
		imgFirmaAsig.setImageResource(resID);
		
		btnConditiiPdf =(Button) findViewById (R.id.btn_conditii_pdf);
		btnSumar = (Button) findViewById (R.id.btn_conditii_sumar);
		
		btnConditiiPdf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SumarAcopeririSiConditii.tip="";
				SumarAcopeririSiConditii.tipDate = "link";
				Intent sumarAc = new Intent(getParent(), SumarAcopeririSiConditii.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("SumarAcoperiri", sumarAc);
			}
		});
		
		btnSumar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SumarAcopeririSiConditii.tip="";
				SumarAcopeririSiConditii.tipDate = "sumar";
				Intent sumarAc = new Intent(getParent(), SumarAcopeririSiConditii.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("SumarAcoperiri", sumarAc);
				
			}
		});
		
	}
	
	public void onResume()
	{
		super.onResume();
		SumarAcopeririSiConditii.tipDate = "";
	}

}