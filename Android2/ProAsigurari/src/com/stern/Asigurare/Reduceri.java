package com.stern.Asigurare;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Reduceri extends Activity {
	
	Button btnPlusBugetari,btnMinusBugetari,btnPlusAnPermis,btnMinusAnPermis;
	EditText edtNrBugetari,edtAnObtinerePermis;
	CheckBox checkCasatorit,checkCopii,checkPensionar,checkHandicap;
	int anMinim = 0, anCurent;
	Button okReduceri;
	AppSettings sett;
	static String from = "";
	public void onCreate (Bundle savedInstanceState)
	{
		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	int width = display.getWidth(); 
    	Window window = getWindow(); 
//    	if (width > 800 || YTOUtils.isTablet(this)) {	    	
//	    	window.setGravity(Gravity.CENTER); 
//	    	window.setLayout(320, 480);
//    	}
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.reduceri);
		
		MainController.tvTitlu.setText("Reduceri");
		sett = AppSettings.get(this);
        sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());
		checkCasatorit = (CheckBox) findViewById (R.id.check_casatorit);
        checkCopii = (CheckBox) findViewById (R.id.check_copii_minori);
        checkPensionar = (CheckBox) findViewById (R.id.check_pensionar);
        checkHandicap = (CheckBox) findViewById (R.id.check_handicap);
        okReduceri = (Button) findViewById(R.id.ok_reduceri);
        
        edtAnObtinerePermis = (EditText)findViewById (R.id.edtAnPermis);
        
        anCurent = Calendar.getInstance().get(Calendar.YEAR);
        btnPlusAnPermis = (Button) findViewById (R.id.an_permis_plus);
        btnMinusAnPermis = (Button) findViewById (R.id.an_permis_minus);
        btnPlusAnPermis.setBackgroundResource(R.drawable.plus);
        btnMinusAnPermis.setBackgroundResource(R.drawable.minus);
        
		if (AltePersoane.persoanaAsigurata.codUnic=="" || AltePersoane.persoanaAsigurata.codUnic.length()<3)
		{
			btnPlusAnPermis.setBackgroundResource(R.drawable.plus_pressed);
			btnMinusAnPermis.setBackgroundResource(R.drawable.minus_pressed);
			edtAnObtinerePermis.setText ("");
		}
		btnPlusAnPermis.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (anMinim!=0)
				{
				if (Integer.parseInt(edtAnObtinerePermis.getText().toString())<anCurent-1) 
				{
					if (Integer.parseInt(edtAnObtinerePermis.getText().toString())==anMinim)
					btnMinusAnPermis.setBackgroundResource(R.drawable.minus);
					btnPlusAnPermis.setBackgroundResource(R.drawable.plus);
					int nr=Integer.parseInt(edtAnObtinerePermis.getText().toString());
					nr++;
					edtAnObtinerePermis.setText(nr+"");
				}
				else 
				{
					edtAnObtinerePermis.setText(anCurent+"");
					btnPlusAnPermis.setBackgroundResource(R.drawable.plus_pressed);
				}
				}
			}
		});
		btnMinusAnPermis.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (anMinim!=0)
				{
				if (Integer.parseInt(edtAnObtinerePermis.getText().toString())>anMinim+1) 
				{
					if (Integer.parseInt(edtAnObtinerePermis.getText().toString())==anCurent) 
					btnPlusAnPermis.setBackgroundResource(R.drawable.plus);
					btnMinusAnPermis.setBackgroundResource(R.drawable.minus);
					int nr=Integer.parseInt(edtAnObtinerePermis.getText().toString());
					nr--;
					edtAnObtinerePermis.setText(nr+"");
				}
				else 
				{
					edtAnObtinerePermis.setText(anMinim+"");
					btnMinusAnPermis.setBackgroundResource(R.drawable.minus_pressed);
				}
				}
			}
		});
		edtAnObtinerePermis.setFocusable(false);
        
		edtNrBugetari = (EditText)findViewById (R.id.edt_nr_bugetari);
		btnPlusBugetari = (Button) findViewById (R.id.nr_bugetari_plus);
		btnMinusBugetari = (Button) findViewById (R.id.nr_bugetari_minus);
		btnPlusBugetari.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(edtNrBugetari.getText().toString())<1) 
				{
					if (Integer.parseInt(edtNrBugetari.getText().toString())==0) btnMinusBugetari.setBackgroundResource(R.drawable.minus);
					btnPlusBugetari.setBackgroundResource(R.drawable.plus);
					int nr=Integer.parseInt(edtNrBugetari.getText().toString());
					nr++;
					edtNrBugetari.setText(nr+"");
				}
				else 
				{
					edtNrBugetari.setText("2");
					btnPlusBugetari.setBackgroundResource(R.drawable.plus_pressed);
				}
			}
		});
		btnMinusBugetari.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(edtNrBugetari.getText().toString())>1) 
				{
					if (Integer.parseInt(edtNrBugetari.getText().toString())==2) btnPlusBugetari.setBackgroundResource(R.drawable.plus);
					btnMinusBugetari.setBackgroundResource(R.drawable.minus);
					int nr=Integer.parseInt(edtNrBugetari.getText().toString());
					nr--;
					edtNrBugetari.setText(nr+"");
				}
				else 
				{
					edtNrBugetari.setText("0");
					btnMinusBugetari.setBackgroundResource(R.drawable.minus_pressed);
				}
				
			}
		});
		
		okReduceri.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		load();
		Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
		edtNrBugetari.setFocusable(false);
		edtNrBugetari.setTypeface(Typeface.create(tf,Typeface.BOLD));
		edtAnObtinerePermis.setTypeface(Typeface.create(tf,Typeface.BOLD));
	}
	
	@Override
	public void onBackPressed ()
	{
		super.onBackPressed();
		saveFromFormular();
	}
	
	
	
	private void load ()
	{
		if (AltePersoane.persoanaAsigurata.casatorit.equals("da")) checkCasatorit.setChecked(true);
		if (AltePersoane.persoanaAsigurata.copiiMinori.equals("da")) checkCopii.setChecked(true);
		if (AltePersoane.persoanaAsigurata.pensionar.equals("da")) checkPensionar.setChecked(true);
		if (AltePersoane.persoanaAsigurata.handicapLocomotor.equals("da")) checkHandicap.setChecked(true);
		if (!AltePersoane.persoanaAsigurata.nrBugetari.equals("")) edtNrBugetari.setText (AltePersoane.persoanaAsigurata.nrBugetari);
		
		if (AltePersoane.persoanaAsigurata.dataPermis!="") 
		{
			try{
				anMinim = Integer.parseInt(AltePersoane.persoanaAsigurata.dataPermis);
			}catch (Exception e) {
				// TODO: handle exception
				if (AltePersoane.persoanaAsigurata.codUnic!="" && AltePersoane.persoanaAsigurata.codUnic.length()>3)
									anMinim = AltePersoane.persoanaAsigurata.getDataPermisMinByCnp();
			}edtAnObtinerePermis.setText(anMinim+"");
			if (AltePersoane.persoanaAsigurata.codUnic!="" && AltePersoane.persoanaAsigurata.codUnic.length()>3)
			{
					anMinim = AltePersoane.persoanaAsigurata.getDataPermisMinByCnp();
				if (Integer.parseInt(edtAnObtinerePermis.getText().toString())==anMinim) btnMinusAnPermis.setBackgroundResource(R.drawable.minus_pressed);
				if (Integer.parseInt(edtAnObtinerePermis.getText().toString())==anCurent) btnPlusAnPermis.setBackgroundResource(R.drawable.plus_pressed);
				if (Integer.parseInt(edtAnObtinerePermis.getText().toString())>anCurent) 
					anMinim=0;
				if (Integer.parseInt(AltePersoane.persoanaAsigurata.nrBugetari)==0)
					btnMinusBugetari.setBackgroundResource(R.drawable.minus_pressed);
				else btnMinusBugetari.setBackgroundResource(R.drawable.minus);
				if (Integer.parseInt(AltePersoane.persoanaAsigurata.nrBugetari)==2)
					btnPlusBugetari.setBackgroundResource(R.drawable.plus_pressed);
				else btnPlusBugetari.setBackgroundResource(R.drawable.plus);
			}
		}
		if (anMinim==0) 
		{
			btnPlusAnPermis.setBackgroundResource(R.drawable.plus_pressed);
			btnMinusAnPermis.setBackgroundResource(R.drawable.minus_pressed);
			edtAnObtinerePermis.setText("");
			}
			if (AltePersoane.persoanaAsigurata.isDirty){
				if (Integer.parseInt(AltePersoane.persoanaAsigurata.nrBugetari)==0)
					btnMinusBugetari.setBackgroundResource(R.drawable.minus_pressed);
				else btnMinusBugetari.setBackgroundResource(R.drawable.minus);
				if (Integer.parseInt(AltePersoane.persoanaAsigurata.nrBugetari)==2)
					btnPlusBugetari.setBackgroundResource(R.drawable.plus_pressed);
				else btnPlusBugetari.setBackgroundResource(R.drawable.plus);	
		}
	}
	
	private void saveFromFormular ()
	{
		AltePersoane.persoanaAsigurata.nrBugetari = edtNrBugetari.getText().toString();
		AltePersoane.persoanaAsigurata.dataPermis = edtAnObtinerePermis.getText().toString();
		if (checkCasatorit.isChecked()==true) AltePersoane.persoanaAsigurata.casatorit="da";
		else AltePersoane.persoanaAsigurata.casatorit="nu";
		if (checkCopii.isChecked()==true) AltePersoane.persoanaAsigurata.copiiMinori="da";
		else AltePersoane.persoanaAsigurata.copiiMinori="nu";
		if (checkPensionar.isChecked()==true) AltePersoane.persoanaAsigurata.pensionar="da";
		else AltePersoane.persoanaAsigurata.pensionar="nu";
		if (checkHandicap.isChecked()==true) AltePersoane.persoanaAsigurata.handicapLocomotor="da";
		else AltePersoane.persoanaAsigurata.handicapLocomotor="nu";
		if (from.equals("rca"))AsigurareRca.loadEditText(getApplicationContext());
		if (from.equals("casco")) AsigurareCasco.loadEditText(getApplicationContext());
	}

}

