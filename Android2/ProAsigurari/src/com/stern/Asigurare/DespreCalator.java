package com.stern.Asigurare;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class DespreCalator extends Activity{
	public static YTOPersoana calator;
	TextView titlu;
	CheckBox elevStud,sportAg,gradInv;
	CheckBox boliCardio,boliNeuro,boliInterne,boliApResp,boliDef,alteBoli;
	Button ok_button;
	AppSettings sett;
	String contUser = "";
	String contParola = "";
	String limba = "ro";
	String versiune = "";
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
		setContentView(R.layout.select_calatorie);
		sett = AppSettings.get(this);
		
		titlu = (TextView) findViewById (R.id.titlu_lay);
		titlu.setText(getString(R.string.i469)+" "+calator.nume);
		
		elevStud = (CheckBox) findViewById(R.id.checkelev);
		sportAg = (CheckBox) findViewById (R.id.checksport);
		gradInv = (CheckBox) findViewById (R.id.checkgradinv);
		
		if (YTOUtils.isTablet(this) && sett.getLanguage().equals("hu"))
			gradInv.setTextSize(14);
		else if (sett.getLanguage().equals("hu"))
			gradInv.setTextSize(10);
		
		boliCardio = (CheckBox) findViewById(R.id.checkbolicadio);
		boliNeuro = (CheckBox) findViewById (R.id.checkbolineuro);
		boliInterne  = (CheckBox) findViewById (R.id.checkboliinterne);
		
		boliApResp = (CheckBox) findViewById (R.id.checkboliaparat);
		boliDef = (CheckBox) findViewById (R.id.checkbolidef);
		alteBoli = (CheckBox) findViewById (R.id.checkalteboli);
		
		ok_button = (Button) findViewById (R.id.ok_button_calator);
		ok_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				save();
				onBackPressed();
			}
		});
		
		load();
		
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
		save();
	}
	
	private void load()
	{
		if (calator.elevStudent.equals("da")) elevStud.setChecked(true);
		if (calator.handicapLocomotor.equals("da")) gradInv.setChecked(true);
		
		if (calator.boliCardio.equals("da")) boliCardio.setChecked(true);
		if (calator.boliNeuro.equals("da")) boliNeuro.setChecked(true);
		if (calator.boliInterne.equals("da")) boliInterne.setChecked(true);
		
		if (calator.boliAparatRespirator.equals("da")) boliApResp.setChecked(true);
		if (calator.boliDefinitive.equals ("da")) boliDef.setChecked(true);
		if (calator.alteBoli.equals("da")) alteBoli.setChecked(true);
	}
	
	private void save()
	{
		if (elevStud.isChecked()) calator.elevStudent="da";
		else calator.elevStudent="nu";
		//if (sportAg.isChecked()) calator.sp="da";
		if (gradInv.isChecked()) calator.handicapLocomotor="da";
		else calator.handicapLocomotor="nu";
		
		if (boliCardio.isChecked()) calator.boliCardio="da";
		else calator.boliCardio="nu";
		if (boliNeuro.isChecked()) calator.boliNeuro="da";
		else calator.boliNeuro="nu";
		if (boliInterne.isChecked()) calator.boliInterne="da";
		else calator.boliInterne="nu";

		if (boliApResp.isChecked()) calator.boliAparatRespirator="da";
		else calator.boliAparatRespirator = "nu";
		if (boliDef.isChecked()) calator.boliDefinitive="da";
		else calator.boliDefinitive="nu";
		if (alteBoli.isChecked()) calator.alteBoli="da";
		else calator.alteBoli="nu";
		String toJSON = calator.persoanaToJSON(calator);
		DatabaseConnector dbConnector = new DatabaseConnector(this);

  	  	dbConnector.updateObiectAsigurat(calator.idIntern,
  			  "1",
  			  toJSON
  			  );
  	  	new RegisterPersoanaWebService(calator,contUser,contParola,limba,versiune).execute(null, null, null);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
}