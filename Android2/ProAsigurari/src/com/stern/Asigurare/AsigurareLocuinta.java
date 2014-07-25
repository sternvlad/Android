package com.stern.Asigurare;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AsigurareLocuinta extends Activity {
	
	RelativeLayout alegeLocuintaLay,alegePersoanaLay;
	TextView persoanaAleasa,locuintaAleasa;
	AppSettings sett;
	public static boolean skipNextPage = false;
	String mesajEroare="";
	String toJSONLoc;
	Button btn_daCesiune,btn_nuCesiune;
	Boolean salveaza;
	boolean goToEditAuto = false;
	public static String dataInceput = null;
	RadioGroup radioModEval;
	RadioButton radio1,radio2,radio3,radio4;
	DatabaseConnector dbConnector;
	int anCurent;
	ScrollView scroll;
	EditText edtDataInceput,edtSuma,edtSumaRaspCiv;
	Button btnPlusDataInceput,btnMinusDataInceput,btnCalculeaza;
	
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
        setContentView(R.layout.asigurare_locuinta);
        goToEditAuto = false;
        sett = AppSettings.get(this);
        MainController.tvTitlu.setText(getString(R.string.i18));
        sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());
        dbConnector = new DatabaseConnector(this);
        mesajEroare = sett.getMesajEroare();
        
        scroll = (ScrollView) findViewById (R.id.scrollview_asig_loc);
        
        alegeLocuintaLay = (RelativeLayout) findViewById (R.id.alege_loc_lay);
        alegeLocuintaLay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LocuinteleMele.tipDate="Asigurare";
				salveaza = false;
				if (goToEditAuto && LocuinteleMele.locuintaCurenta.isDirty){
						Intent locuinta = new Intent(getParent(), Locuinta.class);
						InfoLocuinta.tipLoad="view";
						skipNextPage = true;
						TabGroupActivity parentActivity = (TabGroupActivity)getParent();
						parentActivity.startChildActivity("Locuinta", locuinta);
				}
				else if (GetObiecte.locuinte!=null && GetObiecte.locuinte.size()>0){
				Intent locuinteleMele = new Intent(getParent(), LocuinteleMele.class);
				skipNextPage = false;
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("LocuinteleMele", locuinteleMele);
				}else{
					Intent locuinta = new Intent(getParent(), Locuinta.class);
					InfoLocuinta.tipLoad="add";
					skipNextPage = true;
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("Locuinta", locuinta);
				}
			}
		});
        locuintaAleasa = (TextView) findViewById (R.id.alege_locuinta);

        alegePersoanaLay = (RelativeLayout) findViewById (R.id.alege_persoana_loc_lay);
        alegePersoanaLay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (GetObiecte.persoane == null || GetObiecte.persoane.size()==0){
					Intent contulMeu = new Intent(getParent(), ContulMeu.class);
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("ContulMeu", contulMeu);
				}else{
				AltePersoane.tipDate="Asigurare";
				salveaza = false;
				Intent altePersoane = new Intent(getParent(), AltePersoane.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("AltePersoane", altePersoane);
				}
			}
		});
        
        persoanaAleasa = (TextView) findViewById (R.id.alege_persoana_loc);
        if (AltePersoane.persoanaAsigurata.isDirty && !AltePersoane.persoanaAsigurata.nume.equals(""))
			persoanaAleasa.setText(AltePersoane.persoanaAsigurata.nume +"\n"+AltePersoane.persoanaAsigurata.codUnic+","+AltePersoane.persoanaAsigurata.judet);
        
       	radioModEval = (RadioGroup) findViewById (R.id.radio_mod_eval);
       	radio1 = (RadioButton) findViewById(R.id.radio_1);
       	radio1.setText(getString(R.string.i50));
       	radio1.setTag("valoare-reala");
       	radio2 = (RadioButton) findViewById(R.id.radio_2);
       	radio2.setTag("valoare-piata");
       	radio2.setText(getString(R.string.i51));
       	radio3 = (RadioButton) findViewById(R.id.radio_3);
       	radio3.setTag("evaluare-banca");
       	radio3.setText(getString(R.string.i52));
       	radio4 = (RadioButton) findViewById(R.id.radio_4);
        radio4.setTag("valoare-inlocuire");
        radio4.setText(getString(R.string.i49));
       	
        edtSuma = (EditText) findViewById (R.id.edtSumaAsig);
        edtSuma.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true && LocuinteleMele.locuintaCurenta!=null)
		        {
		          if (LocuinteleMele.locuintaCurenta.sumaAsigurata!=-1) edtSuma.setText(LocuinteleMele.locuintaCurenta.sumaAsigurata+"");
		        }else if (LocuinteleMele.locuintaCurenta!=null){
		        	try{
		        		LocuinteleMele.locuintaCurenta.sumaAsigurata = Integer.parseInt(edtSuma.getText().toString());
		        	}catch (Exception e){
		        		LocuinteleMele.locuintaCurenta.sumaAsigurata=-1;
		        	}
		        	if (LocuinteleMele.locuintaCurenta.sumaAsigurata!=-1) edtSuma.setText(LocuinteleMele.locuintaCurenta.sumaAsigurata+" EUR");
		        }
		    }
		});
        
        edtSumaRaspCiv = (EditText) findViewById (R.id.edtSumaAsigRaspCiv);
        edtSumaRaspCiv.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true && LocuinteleMele.locuintaCurenta!=null)
		        {
		          if (LocuinteleMele.locuintaCurenta.sumaAsigurataRC!=-1) edtSumaRaspCiv.setText(LocuinteleMele.locuintaCurenta.sumaAsigurataRC+"");
		        }else if (LocuinteleMele.locuintaCurenta!=null){
		        	try{
		        		LocuinteleMele.locuintaCurenta.sumaAsigurataRC = Integer.parseInt(edtSumaRaspCiv.getText().toString());
		        	}catch (Exception e){
		        		LocuinteleMele.locuintaCurenta.sumaAsigurataRC=-1;
		        	}
		        	if (LocuinteleMele.locuintaCurenta.sumaAsigurataRC!=-1) edtSumaRaspCiv.setText(LocuinteleMele.locuintaCurenta.sumaAsigurataRC+" EUR");
		        }
		    }
		});
        
        anCurent = Calendar.getInstance().get(Calendar.YEAR);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String currentDate = dateFormat.format(new Date());
        
        Calendar c = Calendar.getInstance();
		try {
			c.setTime(dateFormat.parse(currentDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int hour = new Time(System.currentTimeMillis()).getHours();
		if (hour<20)
			c.add(Calendar.DATE, 1);  // number of days to add
		else c.add(Calendar.DATE, 2);
		currentDate = dateFormat.format(c.getTime());  // dt is now the new date
		final String cd = currentDate;
		currentDate = dateFormat.format(c.getTime());
		if (hour<20)
			c.add(Calendar.DATE,29);
		else c.add(Calendar.DATE,28);
		currentDate = dateFormat.format(c.getTime());
		final String fd = currentDate;
		
		btn_daCesiune = (Button) findViewById (R.id.da_cesiune);
		btn_daCesiune.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn_daCesiune.setTextColor(Color.WHITE);
				btn_nuCesiune.setTextColor(Color.BLACK);
				LocuinteleMele.locuintaCurenta.cesiune=true;
				btn_daCesiune.setBackgroundResource(R.drawable.da_nu_da_da_locuinta);
				btn_nuCesiune.setBackgroundResource(R.drawable.da_nu_da_nu_locuinta);
			}
		});
		
		btn_nuCesiune = (Button) findViewById (R.id.nu_cesiune);
		btn_nuCesiune.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn_nuCesiune.setTextColor(Color.WHITE);
				btn_daCesiune.setTextColor(Color.BLACK);
				LocuinteleMele.locuintaCurenta.cesiune=false;
				btn_nuCesiune.setBackgroundResource(R.drawable.da_nu_nu_nu_locuinta);
				btn_daCesiune.setBackgroundResource(R.drawable.da_nu_nu_da_locuinta);
			}
		});

		
        edtDataInceput = (EditText)findViewById (R.id.edtDataInceput);
		edtDataInceput.setText(cd);
		btnPlusDataInceput = (Button) findViewById (R.id.data_inceput_plus);
		btnMinusDataInceput = (Button) findViewById (R.id.data_inceput_minus);
		
		btnPlusDataInceput.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String dt = edtDataInceput.getText().toString();  // Start date
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				Calendar c = Calendar.getInstance();
				try {
					c.setTime(sdf.parse(dt));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String dt1 = sdf.format(c.getTime());
				c.add(Calendar.DATE, 1);  // number of days to add
				dt = sdf.format(c.getTime());  // dt is now the new date
				if (!dt1.equals(fd)){	
				edtDataInceput.setText(dt);	
				btnMinusDataInceput.setBackgroundResource(R.drawable.minus);
				}
				else btnPlusDataInceput.setBackgroundResource(R.drawable.plus_pressed);
			}
		});
		
		btnMinusDataInceput.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				String dt = edtDataInceput.getText().toString();  // Start date
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				Calendar c = Calendar.getInstance();
				try {
					c.setTime(sdf.parse(dt));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String dt1 = sdf.format(c.getTime());
				c.add(Calendar.DATE, -1);  // number of days to add
				dt = sdf.format(c.getTime());  // dt is now the new date
				if (!dt1.equals(cd)){
				edtDataInceput.setText(dt);	
				btnPlusDataInceput.setBackgroundResource(R.drawable.plus);
				}
				else btnMinusDataInceput.setBackgroundResource(R.drawable.minus_pressed);
			}
		});
		edtDataInceput.setFocusable(false);
		
        if (LocuinteleMele.locuintaCurenta.isDirty){
        	String tipLocuinta = "";
			if (LocuinteleMele.locuintaCurenta.tipLocuinta.equals("apartament-in-bloc"))
				tipLocuinta = getString(R.string.i94);
			else if (LocuinteleMele.locuintaCurenta.tipLocuinta.equals("casa-vila-comuna"))
				tipLocuinta = getString(R.string.i97);
			else if (LocuinteleMele.locuintaCurenta.tipLocuinta.equals("casa-vila-individuala"))
				tipLocuinta = getString(R.string.i119);
        	locuintaAleasa.setText (tipLocuinta+"\n"+LocuinteleMele.locuintaCurenta.judet+", "+LocuinteleMele.locuintaCurenta.localitate);
        	if (LocuinteleMele.locuintaCurenta.sumaAsigurata>0) edtSuma.setText(LocuinteleMele.locuintaCurenta.sumaAsigurata+" EUR");
        	if (LocuinteleMele.locuintaCurenta.sumaAsigurataRC>0) edtSumaRaspCiv.setText(LocuinteleMele.locuintaCurenta.sumaAsigurataRC+" EUR");
        	if (!LocuinteleMele.locuintaCurenta.modEvaluare.equals(""))
        	{
        		if (LocuinteleMele.locuintaCurenta.modEvaluare.equals("valoare-reala")) radio1.setChecked(true);
        		else if (LocuinteleMele.locuintaCurenta.modEvaluare.equals("valoare-piata")) radio2.setChecked(true);
        		else if (LocuinteleMele.locuintaCurenta.modEvaluare.equals("evaluare-banca")) radio3.setChecked(true);
        		else if (LocuinteleMele.locuintaCurenta.modEvaluare.equals("valoare-inlocuire")) radio4.setChecked(true);
        	}
        }
        
        btnCalculeaza = (Button) findViewById (R.id.calculeaza_loc);
        btnCalculeaza.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Boolean isOk=true;
				dataInceput = edtDataInceput.getText().toString();
				if (edtSuma.getText().toString().length()!=0)
				{
					int suma=0;
					   try{
						   suma = Integer.parseInt(edtSuma.getText().toString());
						   if (suma<10000) 
							   isOk=false;
					   }catch (Exception e){
						   try{
						   suma = Integer.parseInt(edtSuma.getText().toString().substring(0,edtSuma.getText().toString().indexOf(' ')));
						   if (suma<10000) 
							   isOk=false;
					   }catch (Exception f) {
						   if (edtSuma.getText().toString().length()>9)
						   try{
						   suma = Integer.parseInt(edtSuma.getText().toString().substring(0,9));
						   }catch (Exception e2) {
							// TODO: handle exception
							   edtSuma.setText("0");
							   suma =0;
						}
						   else{ 
							   suma =0;
							   edtSuma.setText("0");
						   }
						   if (suma<10000) 
							   isOk=false;
					}
					   }
					   
				}
				if (!AltePersoane.persoanaAsigurata.isDirty  || !AltePersoane.persoanaAsigurata.isValidForCompute()) 
				{
					persoanaAleasa.setTextColor(Color.RED);
					scroll.fullScroll(ScrollView.FOCUS_UP);
				}
				else if (!LocuinteleMele.locuintaCurenta.isDirty || !LocuinteleMele.locuintaCurenta.isValidForLocuinta() || LocuinteleMele.locuintaCurenta.completedPercent()!=1) 
				{
					locuintaAleasa.setTextColor(Color.RED);
					goToEditAuto = true;
					scroll.fullScroll(ScrollView.FOCUS_UP);
				}
				else if (edtSuma.getText().toString().length()==0 || !isOk)
				{
					edtSuma.requestFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(edtSuma, InputMethodManager.SHOW_IMPLICIT);
				}
				else if (!isOnline())
				{
					errorDialog ("");
				}
				else 
				{
							salveaza = false;
		      				saveFromFormular();
		      				dbConnector.updateObiectAsigurat(LocuinteleMele.locuintaCurenta.idIntern,
		      			   			  "3",
		      			   			  toJSONLoc
		      			   			  );
		      				GetObiecte.getAutovehicule(dbConnector);
							Intent loadingScreen = new Intent(getParent(), LoadingScreenLoc.class);
							TabGroupActivity parentActivity = (TabGroupActivity)getParent();
							parentActivity.startChildActivity("LoadingScreenLocuita", loadingScreen);
		                    	
		                
				}
		}
	});
        
        Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");

		 persoanaAleasa.setTypeface(Typeface.create(tf,Typeface.BOLD));
		 locuintaAleasa.setTypeface(Typeface.create(tf,Typeface.BOLD));
		 edtSuma.setTypeface(Typeface.create(tf,Typeface.BOLD));
		 edtSumaRaspCiv.setTypeface(Typeface.create(tf,Typeface.BOLD));
		 
			if (sett.getLanguage().equals("hu")&& !YTOUtils.isTablet(getParent().getParent()))
			{
				((TextView) findViewById(R.id.text_header1)).setTextSize(16);
				((TextView) findViewById(R.id.text_header2)).setTextSize(10);
				((TextView) findViewById(R.id.text_header3)).setTextSize(10);
			}else if (sett.getLanguage().equals("hu")&& YTOUtils.isTablet(getParent().getParent()))
			{
				((TextView) findViewById(R.id.text_header1)).setTextSize(45);
				((TextView) findViewById(R.id.text_header2)).setTextSize(27);
				((TextView) findViewById(R.id.text_header3)).setTextSize(27);
			}
	}
	
	public void onPause()
	{
		super.onPause();
		if (salveaza){
		if (LocuinteleMele.locuintaCurenta.isDirty){
			saveFromFormular();
			dbConnector.updateObiectAsigurat(LocuinteleMele.locuintaCurenta.idIntern,
   			  "3",
   			  toJSONLoc
   			  );  
		 GetObiecte.getAutovehicule(dbConnector);
		} 
		}
		InputMethodManager imm = (InputMethodManager) 
		        getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(edtSuma.getWindowToken(), 0);
	}
	
	private void saveFromFormular()
	{
		if (LocuinteleMele.locuintaCurenta.isDirty){
		if (!edtSuma.getText().toString().equals("")) 
			   try{
				   LocuinteleMele.locuintaCurenta.sumaAsigurata = Integer.parseInt(edtSuma.getText().toString());
			   }catch (Exception e){
				   try{
				   LocuinteleMele.locuintaCurenta.sumaAsigurata = Integer.parseInt(edtSuma.getText().toString().substring(0,edtSuma.getText().toString().indexOf(' ')));
			   }catch (Exception f) {
				// TODO: handle exception
				   if (edtSumaRaspCiv.getText().toString().length()>9)
					   try{
				   LocuinteleMele.locuintaCurenta.sumaAsigurata = Integer.parseInt(edtSuma.getText().toString().substring(0,9));
					   }catch (Exception e2) {
						// TODO: handle exception
						   LocuinteleMele.locuintaCurenta.sumaAsigurata=0;
						   edtSuma.setText("0");
					}
				   else{ 
					   LocuinteleMele.locuintaCurenta.sumaAsigurata=0;
					   edtSuma.setText("0");
				   }
			}
			   }
		   else LocuinteleMele.locuintaCurenta.sumaAsigurata = -1; 
		if (!edtSumaRaspCiv.getText().toString().equals("")) 
			   try{
				   LocuinteleMele.locuintaCurenta.sumaAsigurataRC = Integer.parseInt(edtSumaRaspCiv.getText().toString());
			   }catch (Exception e){
				   try{
				   LocuinteleMele.locuintaCurenta.sumaAsigurataRC = Integer.parseInt(edtSumaRaspCiv.getText().toString().substring(0,edtSumaRaspCiv.getText().toString().indexOf(' ')));
			   }catch (Exception f) {
				// TODO: handle exception
				   if (edtSumaRaspCiv.getText().toString().length()>9)
					   try{
				   LocuinteleMele.locuintaCurenta.sumaAsigurataRC = Integer.parseInt(edtSumaRaspCiv.getText().toString().substring(0,9));
					   }catch (Exception e2) {
						// TODO: handle exception
						   LocuinteleMele.locuintaCurenta.sumaAsigurataRC=0;
						   edtSumaRaspCiv.setText("0");
					}
				   else{
					   LocuinteleMele.locuintaCurenta.sumaAsigurataRC=0;
					   edtSumaRaspCiv.setText("0");
				   }
			}
			   }
		   else LocuinteleMele.locuintaCurenta.sumaAsigurataRC = -1; 
		int radioChecked = radioModEval.getCheckedRadioButtonId();
		radio1 = (RadioButton) findViewById(radioChecked);
		LocuinteleMele.locuintaCurenta.modEvaluare = radio1.getTag().toString();
		}
		if (LocuinteleMele.locuintaCurenta.isDirty) toJSONLoc = LocuinteleMele.locuintaCurenta.locuintaToJSON(LocuinteleMele.locuintaCurenta);
		dataInceput = edtDataInceput.getText().toString();
	}
	

	public void onResume()
	{
		
		super.onResume();
		goToEditAuto = false;
		salveaza = true;
		((View) findViewById(R.id.view1)).setBackgroundColor(getResources().getColor(R.color.albastru_locuinta));
		((View) findViewById(R.id.view2)).setBackgroundColor(getResources().getColor(R.color.albastru_locuinta));
		if (dataInceput!=null && dataInceput.length()>0)edtDataInceput.setText(dataInceput);
		MainController.tvTitlu.setText(getString(R.string.i18));
        sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());
		persoanaAleasa.setTextColor(Color.parseColor("#464646"));
		locuintaAleasa.setTextColor(Color.parseColor("#464646"));
		mesajEroare = sett.getMesajEroare();
		if (!LocuinteleMele.locuintaCurenta.isDirty)
		{
			edtSuma.setFocusable(false);
			edtSumaRaspCiv.setFocusable(false);
		}
		else
		{
			edtSuma.setFocusable(true);
			edtSumaRaspCiv.setFocusable(true);
		}

		if (AltePersoane.tipDate.equals("Asigurare")){
			AltePersoane.tipDate="";
			if (AltePersoane.persoanaAsigurata.isDirty && !AltePersoane.persoanaAsigurata.nume.equals(""))
				persoanaAleasa.setText(AltePersoane.persoanaAsigurata.nume +"\n"+AltePersoane.persoanaAsigurata.codUnic+","+AltePersoane.persoanaAsigurata.judet);
		}
		if (LocuinteleMele.locuintaCurenta.isDirty){
			String tipLocuinta = "";
			if (LocuinteleMele.locuintaCurenta.tipLocuinta.equals("apartament-in-bloc"))
				tipLocuinta = getString(R.string.i94);
			else if (LocuinteleMele.locuintaCurenta.tipLocuinta.equals("casa-vila-comuna"))
				tipLocuinta = getString(R.string.i97);
			else if (LocuinteleMele.locuintaCurenta.tipLocuinta.equals("casa-vila-individuala"))
				tipLocuinta = getString(R.string.i119);
        	locuintaAleasa.setText (tipLocuinta+"\n"+LocuinteleMele.locuintaCurenta.judet+", "+LocuinteleMele.locuintaCurenta.localitate);
        	if (LocuinteleMele.locuintaCurenta.sumaAsigurata>0) edtSuma.setText(LocuinteleMele.locuintaCurenta.sumaAsigurata+" EUR");
        	if (LocuinteleMele.locuintaCurenta.sumaAsigurataRC>0) edtSumaRaspCiv.setText(LocuinteleMele.locuintaCurenta.sumaAsigurataRC+" EUR");
        	LocuinteleMele.tipDate="";
		}
		if (!mesajEroare.equals(""))
		{
			errorDialog(mesajEroare);
			sett.updateMesajEroare("");
		}
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	    	
	        return true;
	    }
	    return false;
	}
	
	public void errorDialog (String mesaj) { //eroare
	     final Dialog dialog=new Dialog(getParent());
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog_error);
	     TextView tvContinut;
	     ImageView imgContinut;
	     Button btn_ok=(Button)dialog.findViewById(R.id.button_ok);
	     tvContinut = (TextView)dialog.findViewById (R.id.text_error);
	     if (!mesaj.equals(""))  tvContinut.setText (mesaj);
	     TextView textHeader;
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     textHeader = (TextView) dialog.findViewById(R.id.text_eroare_header);
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
	     if(!isOnline())
	     {
	    	 textHeader.setTextColor(getResources().getColor(R.color.rosu_ter));
		     textHeader.setText(getString(R.string.i799));
	    	 if (YTOUtils.isTablet(getParent()))
					tvContinut.setTextSize(25);
				else tvContinut.setTextSize(12);
	     }
	     
	     btn_ok.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	     }
	     });

	   dialog.show();
	}

}
