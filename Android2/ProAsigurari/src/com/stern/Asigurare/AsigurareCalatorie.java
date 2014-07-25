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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AsigurareCalatorie extends Activity {

	RelativeLayout alegePersoanaLay;
	AppSettings sett;
	ScrollView scrollCalatorie;
	EditText edtTaraDestinatie,edtNrZile,edtDataInceput;
	Button btnPlusZile,btnMinusZile,btn_daTranzit,btn_nuTranzit,btnPlusDataInceput,btnMinusDataInceput,btnCalculeaza;
	TextView tvNrCalatori;
	RadioGroup radioScop,radioSuma;
	static String dataInceput=null;
	static String tranzit="nu";
	int anCurent;
	static int i;
	String mesajEroare;
	RadioButton radioTurism,radioAfaceri,radioSofer,radioStudii,radio5,radio10,radio30,radio50;
	
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
        setContentView(R.layout.asigurare_calatorie);
        
        MainController.tvTitlu.setText (getString(R.string.i456));
		sett = AppSettings.get(this);
		sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());
        radioScop = (RadioGroup) findViewById (R.id.radio_scop);
        radioTurism = (RadioButton) findViewById(R.id.radio_turism);
        radioAfaceri = (RadioButton) findViewById(R.id.radio_afaceri);
        radioSofer = (RadioButton) findViewById (R.id.radio_sofer);
        radioStudii = (RadioButton) findViewById (R.id.radio_studii);
        
        if (sett.getLanguage().equals("hu"))
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_calatorie_hu);
        } else if (sett.getLanguage().equals("en"))
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_calatorie_en);
        }else 
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_calatorie);
        }
        
        radioTurism.setTag("turism");
        radioAfaceri.setTag("afaceri");
        radioSofer.setTag ("sofer-profesionist");
        radioStudii.setTag("studii");
        
        edtNrZile = (EditText)findViewById (R.id.edtNrZile);
        edtNrZile.setFocusable(false);
		btnPlusZile = (Button) findViewById (R.id.nr_zile_plus);
		btnMinusZile = (Button) findViewById (R.id.nr_zile_minus);
		btnPlusZile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(edtNrZile.getText().toString())<365) 
				{
					if (Integer.parseInt(edtNrZile.getText().toString())==2) btnMinusZile.setBackgroundResource(R.drawable.minus);
					btnPlusZile.setBackgroundResource(R.drawable.plus);
					int nr=Integer.parseInt(edtNrZile.getText().toString());
					nr++;
					edtNrZile.setText(nr+"");
				}
				else 
				{
					edtNrZile.setText("365");
					btnPlusZile.setBackgroundResource(R.drawable.plus_pressed);
				}
			}
		});
		
			btnMinusZile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(edtNrZile.getText().toString())>3) 
				{
					if (Integer.parseInt(edtNrZile.getText().toString())==500) btnPlusZile.setBackgroundResource(R.drawable.plus);
					btnMinusZile.setBackgroundResource(R.drawable.minus);
					int nr=Integer.parseInt(edtNrZile.getText().toString());
					nr--;
					edtNrZile.setText(nr+"");
				}
				else 
				{
					edtNrZile.setText("2");
					btnMinusZile.setBackgroundResource(R.drawable.minus_pressed);
				}
				
			}
		});
        
        edtTaraDestinatie = (EditText) findViewById(R.id.edtTaraDest);
		edtTaraDestinatie.setOnClickListener (new OnClickListener()
		{
		    @Override
		    public void onClick(View v)
		    {
		        	Lista.tipDate = "tari";
		        	Intent i = new Intent (AsigurareCalatorie.this,Lista.class);
		        	i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		        	startActivity (i);
		        	
		    }
		});
		edtTaraDestinatie.setFocusable(false);
		
		btn_daTranzit = (Button) findViewById (R.id.da_calatorie);
		btn_daTranzit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn_daTranzit.setTextColor(Color.WHITE);
				btn_nuTranzit.setTextColor(Color.BLACK);
				tranzit="da";
				btn_daTranzit.setBackgroundResource(R.drawable.da_nu_da_da_calatorie);
				btn_nuTranzit.setBackgroundResource(R.drawable.da_nu_da_nu_rca);
			}
		});
		
		btn_nuTranzit = (Button) findViewById (R.id.nu_calatorie);
		btn_nuTranzit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn_nuTranzit.setTextColor(Color.WHITE);
				btn_daTranzit.setTextColor(Color.BLACK);
				tranzit="nu";
				btn_nuTranzit.setBackgroundResource(R.drawable.da_nu_nu_nu_calatorie);
				btn_daTranzit.setBackgroundResource(R.drawable.da_nu_nu_da_rca);
			}
		});
		
		if (sett.getLanguage().equals("hu")&& !YTOUtils.isTablet(getParent().getParent()))
		{
			((TextView) findViewById(R.id.text_header1)).setTextSize(14);
			((TextView) findViewById(R.id.text_header2)).setTextSize(10);
			((TextView) findViewById(R.id.text_header3)).setTextSize(10);
		}else if (sett.getLanguage().equals("hu")&& YTOUtils.isTablet(getParent().getParent()))
		{
			((TextView) findViewById(R.id.text_header1)).setTextSize(40);
			((TextView) findViewById(R.id.text_header2)).setTextSize(25);
			((TextView) findViewById(R.id.text_header3)).setTextSize(25);
		}
		
		radioSuma = (RadioGroup) findViewById(R.id.radio_suma_asig);
		radio5 = (RadioButton) findViewById (R.id.radio_5);
		radio10 = (RadioButton) findViewById (R.id.radio_10);
		radio30 = (RadioButton) findViewById(R.id.radio_30);
		radio50 = (RadioButton) findViewById(R.id.radio_50);
		
		radio5.setTag  ("5.000-eur");
		radio10.setTag ("10.000-eur");
		radio30.setTag ("30.000-eur");
		radio50.setTag ("50.000-eur");
		
		
        alegePersoanaLay = (RelativeLayout) findViewById (R.id.alege_calator_lay);
        alegePersoanaLay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent altePersoaneCalatorie = new Intent(getParent(), AltePersoaneCalatorie.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("AltePersoaneCalatorie", altePersoaneCalatorie);
			}
		});
        
        tvNrCalatori = (TextView) findViewById (R.id.alege_calator); 
        
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
		int hour =  new Time(System.currentTimeMillis()).getHours();
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
		
		scrollCalatorie = (ScrollView) findViewById (R.id.scroll_calatorie);
		
		btnCalculeaza = (Button) findViewById (R.id.calculeaza_calatorie);
		btnCalculeaza.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dataInceput = edtDataInceput.getText().toString();
				if (AltePersoaneCalatorie.calatori==null) 
				{
					tvNrCalatori.setTextColor(Color.RED);
				}
				else if (AltePersoaneCalatorie.calatori.size()==0) 
				{
					tvNrCalatori.setTextColor(Color.RED);
				}
				else if (!isOnline())
				{
					errorDialog ("");
				}
				else 
				{
					save();
					Intent loadingScreenCa = new Intent(getParent(), LoadingCalatorie.class);
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("LoadingScreenCa", loadingScreenCa);
				}
				
			}
		});
		load();
	}
	public void onResume ()
	{
		super.onResume();
		if (dataInceput!=null && dataInceput.length()>0)edtDataInceput.setText(dataInceput);
		MainController.tvTitlu.setText (getString(R.string.i456));
		sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());
		mesajEroare = sett.getMesajEroare();
		tvNrCalatori.setTextColor(Color.parseColor("#464646"));
		if (AltePersoaneCalatorie.calatori!=null)
        	if (AltePersoaneCalatorie.calatori.size()!=0){
        		tvNrCalatori.setText(getString(R.string.i457) + " "+AltePersoaneCalatorie.calatori.size()+"\n"+getString(R.string.i459)); 
        		}
        	else tvNrCalatori.setText(getString(R.string.i458)+"\n" + getString(R.string.i459));
		if (Lista.tipDate!="")
		{
			edtTaraDestinatie.setText(Lista.date);
			Lista.tipDate="";
			Lista.date="";
		}
		if (i==1)
		{
			scrollCalatorie.post(new Runnable() { 
                public void run() { 
                    scrollCalatorie.smoothScrollTo(0, btnCalculeaza.getBottom());
                } 
            });
			i=0;
		}
		if (!mesajEroare.equals(""))
		{
			errorDialog(mesajEroare);
			sett.updateMesajEroare("");
		}
	}
	
	@Override
	public void onPause ()
	{
		super.onPause();
			save();
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		AltePersoaneCalatorie.calatori.clear();
	}
	
	
	private void load()
	{
		if (!sett.getScopCalatorie().equals(""))
			if (sett.getScopCalatorie().equals("turism")) radioTurism.setChecked(true);
			else if (sett.getScopCalatorie().equals("afaceri")) radioAfaceri.setChecked(true);
			else if (sett.getScopCalatorie().equals("sofer-profesionist")) radioSofer.setChecked(true);
			else if (sett.getScopCalatorie().equals("studii")) radioStudii.setChecked(true);
		
		edtNrZile.setText(sett.getNrZileCalatorie()+"");
		if (edtNrZile.getText().toString().equals("2"))
			btnMinusZile.setBackgroundResource(R.drawable.minus_pressed);
		
		edtTaraDestinatie.setText(sett.getTaraDestinatie());
		if (tranzit.equals("da"))
		{
			btn_daTranzit.performClick();
		}
		
		if (!sett.getSumaAsigurataCalatorie().equals(""))
			if (sett.getSumaAsigurataCalatorie().equals("5.000-eur")) radio5.setChecked(true);
			else if (sett.getSumaAsigurataCalatorie().equals("10.000-eur")) radio10.setChecked(true);
			else if (sett.getSumaAsigurataCalatorie().equals("30.000-eur")) radio30.setChecked(true);
			else if (sett.getSumaAsigurataCalatorie().equals("50.000-eur")) radio50.setChecked(true);
	}
	
	private void save()
	{
		int radioChecked = radioScop.getCheckedRadioButtonId();
		RadioButton radioButtonScop = (RadioButton) findViewById(radioChecked);
		sett.updateScopCalatorie(radioButtonScop.getTag().toString());
		
		sett.updateNrZileCalatorie(Integer.parseInt(edtNrZile.getText().toString()));
		
		sett.updateTaraDestinatie(edtTaraDestinatie.getText().toString());
		
		radioChecked = radioSuma.getCheckedRadioButtonId();
		RadioButton radioButtonSuma = (RadioButton) findViewById(radioChecked);
		sett.updateSumaAsigurataCalatorie(radioButtonSuma.getTag().toString());
		
		dataInceput = edtDataInceput.getText().toString();
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
	     Button btn_ok=(Button)dialog.findViewById(R.id.button_ok);
	     tvContinut = (TextView)dialog.findViewById (R.id.text_error);
	     if (!mesaj.equals(""))  tvContinut.setText (mesaj);
	     TextView textHeader;
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     textHeader = (TextView) dialog.findViewById(R.id.text_eroare_header);
	     textHeader.setTextColor(getResources().getColor(R.color.rosu_ter));
	     textHeader.setText(getString(R.string.i799));
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
