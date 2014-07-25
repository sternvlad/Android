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
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
 
public class InfoLocuinta extends Activity {
	EditText edtJudLoc,edtAdresa,edtStructuraRezistenta,edtInaltime,edtEtaj,edtAnConstructie,edtNrCamere,edtSuprafata,edtNrLoc,edtAlteInf;
	String judet="";
	int completedOpen,completedClose; //numarul de campuri completate la deschiderea si la inchiderea formularului
	String toJSON;
	RadioButton radioButtonTipLoc,radioAp,radioCVC,radioCVI;
	RadioGroup radioTipLoc;
	String localitate="";
	Boolean salveaza;
	AppSettings sett;
	TextView txt_etaj;
	String contUser = "";
	String contParola = "";
	String limba = "ro";
	String versiune = "";
	LinearLayout linearLoc;
	public static int [] detalii_loc  = new int[9];//detalii despre locuinta in lista
	public static String tipLoad;
	Button btn_renunta,btn_salveaza,btnPlusCam,btnMinusCam,btnPlusLoc,btnMinusLoc;
	YTOLocuinta locuinta = new YTOLocuinta ();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_locuinta);
		 
             
        for (int i=0;i<=8;i++) detalii_loc[i]=0;
        edtJudLoc = (EditText)findViewById (R.id.edtJudLocLocuinta);	
		edtJudLoc.setOnClickListener (new OnClickListener()
		{
		    @Override
		    public void onClick(View v)
		    {
		    		salveaza=false;
		        	Lista.tipDate = "judete";
		        	Intent i = new Intent (InfoLocuinta.this,Lista.class);
		        	i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		        	startActivity (i);
		        	
		    }
		});
		edtJudLoc.setFocusable(false);
		edtAdresa = (EditText)findViewById (R.id.edtAdresaLocuinta);
		edtAdresa.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		        	Locuinta.tooltip.setImageResource(R.drawable.tooltip_locuinta);
		          Locuinta.tooltip.setVisibility (View.VISIBLE); 
		          Locuinta.tvTooltip.setText (getString (R.string.i375));
		        }else{
		        	edtAdresa.setText(YTOUtils.replaceDiacritice(edtAdresa.getText().toString()));
		        	Locuinta.tvTooltip.setText ("");
		        	Locuinta.tooltip.setVisibility (View.GONE); 
		        }
		    }
		});
		
		radioTipLoc = (RadioGroup) findViewById (R.id.radio_tip_loc);
		radioAp = (RadioButton) findViewById (R.id.radio_ap);
		radioAp.setTag("apartament-in-bloc");
		radioCVC = (RadioButton) findViewById (R.id.radio_cvc);
		radioCVC.setTag("casa-vila-comuna");
		radioCVI = (RadioButton) findViewById (R.id.radio_cvi);
		radioCVI.setTag("casa-vila-individuala");
		
		edtStructuraRezistenta = (EditText)findViewById (R.id.edtStruncturaRezistenta);	
		edtStructuraRezistenta.setOnClickListener (new OnClickListener()
		{
		    @Override
		    public void onClick(View v)
		    {
		    	salveaza=false;
		    	String date = "";
		    	if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i504)))
		    		date="beton-armat";
		    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i505)))
		    		date="beton";
		    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i506)))
		    		date="bca";
		    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i507)))
		    		date="caramida";
		    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i508)))
		    		date="caramida-nearsa";
		    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i509)))
		    		date="chirpici-piatra";
		    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i510)))
		    		date="lemn";
		    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i511)))
		    		date="zidarie-lemn";
		    	RadioButtons.date=date;
		    	RadioButtons.tipDate="StructuraLocuinta";
		    	Intent i = new Intent (InfoLocuinta.this,RadioButtons.class);
		    	i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        	startActivity (i);
		    }
		});
		edtStructuraRezistenta.setFocusable(false);
		edtInaltime = (EditText)findViewById (R.id.edtInaltimeLoc);
		edtInaltime.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		        	Locuinta.tooltip.setImageResource(R.drawable.tooltip_locuinta);
		          Locuinta.tooltip.setVisibility (View.VISIBLE); 
		          Locuinta.tvTooltip.setText (getString (R.string.i391));
		        }else{
		        	Locuinta.tvTooltip.setText ("");
		        	Locuinta.tooltip.setVisibility (View.GONE); 
		        }
		    }
		});
		
		edtInaltime.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				int etaj,inaltime;
				try{
					etaj = Integer.parseInt(edtEtaj.getText().toString());
				}catch (Exception e) {
					// TODO: handle exception
					etaj=0;
				}
				try{
					inaltime = Integer.parseInt(edtInaltime.getText().toString());
				}catch (Exception e) {
					// TODO: handle exception
					inaltime=0;
				}
				if (etaj<=inaltime)
					((ImageView) findViewById(R.id.wrong_textetaj)).setVisibility(View.GONE);
				else if (edtEtaj.getText().toString().length()>0 && inaltime>0)
					((ImageView) findViewById(R.id.wrong_textetaj)).setVisibility(View.VISIBLE);
			}
		});
		
		txt_etaj= (TextView) findViewById (R.id.textEtaj);
		edtEtaj = (EditText)findViewById (R.id.edtEtajLoc);
		edtEtaj.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		        	Locuinta.tooltip.setImageResource(R.drawable.tooltip_locuinta);
		          Locuinta.tooltip.setVisibility (View.VISIBLE); 
		          Locuinta.tvTooltip.setText (getString (R.string.i393));
		        }else{
		        	Locuinta.tvTooltip.setText ("");
		        	Locuinta.tooltip.setVisibility (View.GONE); 
		        }
		    }
		});
		
		edtEtaj.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				int etaj,inaltime;
				try{
					etaj = Integer.parseInt(edtEtaj.getText().toString());
				}catch (Exception e) {
					// TODO: handle exception
					etaj=0;
				}
				try{
					inaltime = Integer.parseInt(edtInaltime.getText().toString());
				}catch (Exception e) {
					// TODO: handle exception
					inaltime=0;
				}
				if (etaj<=inaltime)
					((ImageView) findViewById(R.id.wrong_textetaj)).setVisibility(View.GONE);
				else if (edtEtaj.getText().toString().length()>0 && inaltime>0)
					((ImageView) findViewById(R.id.wrong_textetaj)).setVisibility(View.VISIBLE);
			}
		});
		
		radioAp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
				edtEtaj.setText("");
				edtInaltime.setText("");
				edtEtaj.setVisibility(View.VISIBLE);
				txt_etaj.setVisibility(View.VISIBLE);}
			}
		});
		
		radioCVC.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
				edtEtaj.setText("0");
				edtEtaj.setVisibility(View.GONE);
				txt_etaj.setVisibility(View.GONE);}
			}
		});
		
		radioCVI.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
				edtEtaj.setText("0");
				edtEtaj.setVisibility(View.GONE);
				txt_etaj.setVisibility(View.GONE);}
			}
		});
		
		edtAnConstructie = (EditText)findViewById (R.id.edtAnConstructieLoc);
		edtAnConstructie.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		        	Locuinta.tooltip.setImageResource(R.drawable.tooltip_locuinta);
		          Locuinta.tooltip.setVisibility (View.VISIBLE); 
		          Locuinta.tvTooltip.setText (getString (R.string.i395));
		        }else{
		        	Locuinta.tvTooltip.setText ("");
		        	Locuinta.tooltip.setVisibility (View.GONE); 
		        }
		    }
		});
		
		edtAnConstructie.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				int cYear = Calendar.getInstance().get(Calendar.YEAR);
				int setYear = 0;
				try{
				setYear = Integer.parseInt(edtAnConstructie.getText().toString());
				}catch (Exception e){
					setYear=0;
				}
				if (1899<setYear && setYear<=cYear)
					((ImageView) findViewById(R.id.wrong_textan)).setVisibility(View.GONE);
				else ((ImageView) findViewById(R.id.wrong_textan)).setVisibility(View.VISIBLE);
				
			}
		});
		
		edtNrCamere = (EditText)findViewById (R.id.edtNrCamere);
		btnPlusCam = (Button) findViewById (R.id.nr_cam_plus);
		btnMinusCam = (Button) findViewById (R.id.nr_cam_minus);
		btnPlusCam.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(edtNrCamere.getText().toString())<9) 
				{
					if (Integer.parseInt(edtNrCamere.getText().toString())==1) btnMinusCam.setBackgroundResource(R.drawable.minus);
					btnPlusCam.setBackgroundResource(R.drawable.plus);
					int nr=Integer.parseInt(edtNrCamere.getText().toString());
					nr++;
					edtNrCamere.setText(nr+"");
				}
				else 
				{
					edtNrCamere.setText("10");
					btnPlusCam.setBackgroundResource(R.drawable.plus_pressed);
				}
			}
		});
		btnMinusCam.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(edtNrCamere.getText().toString())>2) 
				{
					if (Integer.parseInt(edtNrCamere.getText().toString())==10) btnPlusCam.setBackgroundResource(R.drawable.plus);
					btnMinusCam.setBackgroundResource(R.drawable.minus);
					int nr=Integer.parseInt(edtNrCamere.getText().toString());
					nr--;
					edtNrCamere.setText(nr+"");
				}
				else 
				{
					edtNrCamere.setText("1");
					btnMinusCam.setBackgroundResource(R.drawable.minus_pressed);
				}
				
			}
		});
		edtNrCamere.setFocusable(false);
		edtSuprafata = (EditText)findViewById (R.id.edtSuprafataLoc);
		edtSuprafata.setOnFocusChangeListener(new OnFocusChangeListener()
		{
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true)
		        {
		        	Locuinta.tooltip.setImageResource(R.drawable.tooltip_locuinta);
		          Locuinta.tooltip.setVisibility (View.VISIBLE); 
		          Locuinta.tvTooltip.setText (getString (R.string.i398));
		          if (locuinta.suprafataUtila!=-1) edtSuprafata.setText(locuinta.suprafataUtila+"");
		        }else{
		        	try{
		        	locuinta.suprafataUtila = Integer.parseInt(edtSuprafata.getText().toString());
		        	}catch (Exception e){
		        	locuinta.suprafataUtila=-1;
		        	}
		        	if (locuinta.suprafataUtila!=-1) edtSuprafata.setText(locuinta.suprafataUtila+" mp");
		        	Locuinta.tvTooltip.setText ("");
		        	Locuinta.tooltip.setVisibility (View.GONE); 
		        }
		    }
		});
		edtNrLoc = (EditText)findViewById (R.id.edtNrLocatari);
		btnPlusLoc = (Button) findViewById (R.id.nr_loc_plus);
		btnMinusLoc = (Button) findViewById (R.id.nr_loc_minus);
		btnPlusLoc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(edtNrLoc.getText().toString())<9) 
				{
					if (Integer.parseInt(edtNrLoc.getText().toString())==1) btnMinusLoc.setBackgroundResource(R.drawable.minus);
					btnPlusLoc.setBackgroundResource(R.drawable.plus);
					int nr=Integer.parseInt(edtNrLoc.getText().toString());
					nr++;
					edtNrLoc.setText(nr+"");
				}
				else 
				{
					edtNrLoc.setText("10");
					btnPlusLoc.setBackgroundResource(R.drawable.plus_pressed);
				}
			}
		});
		btnMinusLoc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(edtNrLoc.getText().toString())>2) 
				{
					if (Integer.parseInt(edtNrLoc.getText().toString())==10) btnPlusLoc.setBackgroundResource(R.drawable.plus);
					btnMinusLoc.setBackgroundResource(R.drawable.minus);
					int nr=Integer.parseInt(edtNrLoc.getText().toString());
					nr--;
					edtNrLoc.setText(nr+"");
				}
				else 
				{
					edtNrLoc.setText("1");
					btnMinusLoc.setBackgroundResource(R.drawable.minus_pressed);
				}
				
			}
		});
		edtNrLoc.setFocusable(false);
		edtAlteInf = (EditText)findViewById (R.id.edtAlteInformatiiLoc);	
		edtAlteInf.setOnClickListener (new OnClickListener()
		{
		    @Override
		    public void onClick(View v)
		    {
		    	salveaza=false;
	        	Lista.tipDate = "detalii";
	        	Intent i = new Intent (InfoLocuinta.this,Lista.class);
	        	i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        	startActivity (i); 	
		    }
		});
		edtAlteInf.setFocusable(false);
		
	      btn_salveaza =(Button) findViewById(R.id.salveaza_locuinta);
	      btn_salveaza.setOnClickListener(new OnClickListener() {
			
	    	  public void onClick(View v) 
	          {
	    		  onBackPressed ();//pe onBackPressed salvez
	          } 
		 });
	      
	      btn_renunta = (Button)findViewById (R.id.renunta_locuinta);
	      btn_renunta.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				salveaza=false;
				finish();
			}
		});
	      
	      sett = AppSettings.get(getParent().getParent());
			if (sett.getLanguage().equals("hu")&& !YTOUtils.isTablet(getParent().getParent()))
			{
			 	((RadioButton) findViewById(R.id.radio_ap)).setTextSize(10);
			}
	      
	      
	      load();
	      
	      if (LocuinteleMele.tipDate.equals("Asigurare") && locuinta.isDirty)
			{
				new Handler().post(new Runnable() {

		              @Override
		              public void run() {
		            	  EditText edt = null;
		            	   int cYear = Calendar.getInstance().get(Calendar.YEAR);
			   				int setYear = locuinta.anConstructie;
		            	   if (edtAdresa.getText().toString().length() == 0){
		            			  edt = edtAdresa;
		            		  }else if (edtInaltime.getText().toString().length() == 0){
		            			  edt = edtInaltime;
		            		  }else if (edtEtaj.getText().toString().length() == 0){
		            			  edt = edtEtaj;
		            		  }else if (edtAnConstructie.getText().toString().length() == 0){
		            			  edt = edtAnConstructie;
		            		  }else if (edtSuprafata.getText().toString().length() == 0){
		            			  edt = edtSuprafata;
		            		  }else if (1899>setYear ||  setYear>cYear){
				   				edt = edtAnConstructie;
		              			}else if (locuinta.etaj>=locuinta.regimInaltime){
				   				edt = edtEtaj;
		              		}
		            	   if (edt!=null){
		                    edt.requestFocus();
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT);
			  				Locuinta.tvTooltip.setText(getString (R.string.i442));}
			  				if (locuinta.completedFields() == 10)
			  				{
			  					if (edtAdresa.getText().toString().length() == 0){
			  						Locuinta.tvTooltip.setText(getString(R.string.i640));
			            		  }else if (edtInaltime.getText().toString().length() == 0){
			            			  Locuinta.tvTooltip.setText(getString(R.string.i641));
			            		  }else if (edtEtaj.getText().toString().length() == 0){
			            			  Locuinta.tvTooltip.setText(getString(R.string.i642));
			            		  }else if (edtAnConstructie.getText().toString().length() == 0){
			            			  Locuinta.tvTooltip.setText(getString(R.string.i644));
			            		  }else if (edtSuprafata.getText().toString().length() == 0){
			            			  Locuinta.tvTooltip.setText(getString(R.string.i647));
			            		  }else if (edtJudLoc.getText().toString().length() == 0){
			            			  Locuinta.tvTooltip.setText(getString(R.string.i639));
			            		  }
			  				}else if (locuinta.completedPercent() == 1 && !locuinta.isValidForLocuinta()) {
			  					 if (1899>setYear){
			            			  Locuinta.tvTooltip.setText(getString(R.string.i645));
			              			}else if (locuinta.etaj>=locuinta.regimInaltime){
			              			  Locuinta.tvTooltip.setText(getString(R.string.i643));
			              			}else if (setYear>cYear){
			              			Locuinta.tvTooltip.setText(getString(R.string.i646));
			              		}
			  				}
			  				
			  					
							Locuinta.tooltip.setImageResource(R.drawable.tooltip_atentie);
							Locuinta.tooltip.setVisibility(View.VISIBLE);
		              }
		        });
			}
	      
	      completedOpen=getCompletedCount();
			Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
			
			edtJudLoc.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtAdresa.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtStructuraRezistenta.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtInaltime.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtEtaj.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtAnConstructie.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtNrCamere.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtSuprafata.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtNrLoc.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtAlteInf.setTypeface(Typeface.create(tf,Typeface.BOLD));
			
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
		if (tipLoad.equals("add")){
			Locuinta.changeHeader(1, getApplicationContext());
		}else {
			Locuinta.changeHeader(2, getApplicationContext());
		}
		if (Lista.tipDate.equals("detalii"))
		{
			setTextAlteInf ();
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
		if (RadioButtons.tipDate.equals("StructuraLocuinta"))
		{
			String structura = "";
			if (RadioButtons.date.equals("beton-armat"))
	    		structura=getString(R.string.i504);
	    	else if (RadioButtons.date.equals("beton"))
	    		structura=getString(R.string.i505);
	    	else if (RadioButtons.date.equals("bca"))
	    		structura=getString(R.string.i506);
	    	else if (RadioButtons.date.equals("caramida"))
	    		structura=getString(R.string.i385);
	    	else if (RadioButtons.date.equals("caramida-nearsa"))
	    		structura=getString(R.string.i386);
	    	else if (RadioButtons.date.equals("chirpici-piatra"))
	    		structura=getString(R.string.i387);
	    	else if (RadioButtons.date.equals("lemn"))
	    		structura=getString(R.string.i510);
	    	else if (RadioButtons.date.equals("zidarie-lemn"))
	    		structura=getString(R.string.i511);
			edtStructuraRezistenta.setText(structura);
			RadioButtons.tipDate="";
		}
		Lista.date="";
		RadioButtons.date="";
		salveaza=true;
	}
	
	public void onPause ()
	{
		super.onPause();
		if (salveaza)
                  saveContact();
		InputMethodManager imm = (InputMethodManager) 
		        getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(edtAdresa.getWindowToken(), 0);
		    
	}
	
	 private void saveContact() 
	   {    
		   	
		   DatabaseConnector dbConnector = new DatabaseConnector(this);
		   if (tipLoad.equals("add") && shouldSave())
		   {
			   saveFromFormular();
			   DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		        String currentDate = dateFormat.format(new Date());
		        
		        Calendar c = Calendar.getInstance();
				try {
					c.setTime(dateFormat.parse(currentDate));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tipLoad = "view";
			   locuinta._dataCreare = dateFormat.format(c.getTime());
			   locuinta.idIntern = UUID.randomUUID().toString();
			   locuinta.isDirty = true;
			   AlerteLocuinta.idObiect=locuinta.idIntern;
			   dbConnector.insertObiectAsigurat(locuinta.idIntern,
	    			  "3",
	    			  toJSON
	    			  );
			   locuinta=locuinta.locuintaFromJSON(locuinta, toJSON);
			   new RegisterLocuintaWebService(locuinta.locuintaFromJSON(locuinta, toJSON),contUser,contParola,limba,versiune).execute(null, null, null);
			   LocuinteleMele.locuintaCurenta = new YTOLocuinta();
	    		locuinta.isDirty=true;
	    		LocuinteleMele.locuintaCurenta = locuinta;
//	    		if (locuinta.completedPercent()!=1 || !locuinta.isValidForLocuinta())
//	    			AsigurareLocuinta.skipNextPage = true;
//	    		else AsigurareLocuinta.skipNextPage = false;
		  }
		   else if (tipLoad.equals("view"))
		   {
			   saveFromFormular();
			   dbConnector.updateObiectAsigurat(locuinta.idIntern,
		    			  "3",
		    			  toJSON
		    			  );  
			   locuinta=locuinta.locuintaFromJSON(locuinta, toJSON);
			   new RegisterLocuintaWebService(locuinta.locuintaFromJSON(locuinta, toJSON),contUser,contParola,limba,versiune).execute(null, null, null);
			   LocuinteleMele.locuintaCurenta = new YTOLocuinta();
	    		locuinta.isDirty=true;
	    		LocuinteleMele.locuintaCurenta = locuinta;
//	    		if (locuinta.completedPercent()!=1 || !locuinta.isValidForLocuinta())
//	    			AsigurareLocuinta.skipNextPage = true;
//	    		else AsigurareLocuinta.skipNextPage = false;
		   }
	    	  GetObiecte.getLocuinte(dbConnector);
		    		
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
        	   super.onBackPressed();
	   }
	   
	   private void load()
	   {
		if (tipLoad.equals("add")){
			AlerteLocuinta.idObiect="";
			detalii_loc[5]=1;
			setTextAlteInf ();
			Locuinta.changeHeader(1, getApplicationContext());
			edtStructuraRezistenta.setText(getString(R.string.i504));
			if (GetObiecte.persoane!=null && GetObiecte.persoane.size()!=0)
			{
				if (GetObiecte.persoanaFizica.isDirty )
					{
								if (!GetObiecte.persoanaFizica.judet.equals("")) 
								{
								edtJudLoc.setText(GetObiecte.persoanaFizica.judet+","+GetObiecte.persoanaFizica.localitate);
								judet=GetObiecte.persoanaFizica.judet;
							   	localitate=GetObiecte.persoanaFizica.localitate;
							   	edtAdresa.setText(GetObiecte.persoanaFizica.adresa);
								}
					}
				else if (GetObiecte.persoanaJuridica.isDirty)
				{
					if (!GetObiecte.persoanaJuridica.judet.equals("")) 
					{
					edtJudLoc.setText(GetObiecte.persoanaJuridica.judet+","+GetObiecte.persoanaJuridica.localitate);
					judet=GetObiecte.persoanaJuridica.judet;
				   	localitate=GetObiecte.persoanaJuridica.localitate;
				   	edtAdresa.setText(GetObiecte.persoanaJuridica.adresa);
					}
				}
			}

		}
		   else if (tipLoad.equals("view")) 
		   {
			   Locuinta.changeHeader(2, getApplicationContext());
			   locuinta = GetObiecte.locuinte.get(LocuinteleMele.positionId);
			   edtJudLoc.setText (locuinta.judet+","+locuinta.localitate);
			   judet=locuinta.judet;
			   localitate=locuinta.localitate;
			   edtAdresa.setText (locuinta.adresa);
			   String structura = "";
			   if (locuinta.structuraLocuinta.equals("beton-armat"))
		    		structura=getString(R.string.i504);
		    	else if (locuinta.structuraLocuinta.equals("beton"))
		    		structura=getString(R.string.i505);
		    	else if (locuinta.structuraLocuinta.equals("bca"))
		    		structura=getString(R.string.i506);
		    	else if (locuinta.structuraLocuinta.equals("caramida"))
		    		structura=getString(R.string.i507);
		    	else if (locuinta.structuraLocuinta.equals("caramida-nearsa"))
		    		structura=getString(R.string.i508);
		    	else if (locuinta.structuraLocuinta.equals("chirpici-piatra"))
		    		structura=getString(R.string.i509);
		    	else if (locuinta.structuraLocuinta.equals("lemn"))
		    		structura=getString(R.string.i510);
		    	else if (locuinta.structuraLocuinta.equals("zidarie-lemn"))
		    		structura=getString(R.string.i511);
			   edtStructuraRezistenta.setText (structura);
			   if (locuinta.tipLocuinta.equals("casa-vila-comuna")) radioCVC.setChecked(true);
			   else if (locuinta.tipLocuinta.equals("casa-vila-individuala")) radioCVI.setChecked(true);
			   locuinta.idIntern=locuinta.idIntern;
			   AlerteLocuinta.idObiect = locuinta.idIntern;
			   if (locuinta.regimInaltime!=-1) edtInaltime.setText(locuinta.regimInaltime+"");
			   if (locuinta.etaj!=-1) edtEtaj.setText(locuinta.etaj+"");
			   if (locuinta.anConstructie!=-1) edtAnConstructie.setText(locuinta.anConstructie+"");
			   if (locuinta.suprafataUtila!=-1) 
			   {	
				edtSuprafata.setText(locuinta.suprafataUtila+" mp");
			   	locuinta.suprafataUtila=locuinta.suprafataUtila;
			   }
			   edtNrCamere.setText(locuinta.nrCamere+"");
			   if (locuinta.nrCamere==10) btnPlusCam.setBackgroundResource(R.drawable.plus_pressed);
			   if (locuinta.nrCamere==1) btnMinusCam.setBackgroundResource(R.drawable.minus_pressed);
			   edtNrLoc.setText (locuinta.nrLocatari+"");
			   if (locuinta.nrLocatari==10) btnPlusLoc.setBackgroundResource(R.drawable.plus_pressed);
			   if (locuinta.nrLocatari==1) btnMinusLoc.setBackgroundResource(R.drawable.minus_pressed);
			   
			   if (locuinta.areAlarma.equals("da")) detalii_loc[0]=1;
			   if (locuinta.areGrilajeGeam.equals("da")) detalii_loc[1]=1;
			   if (locuinta.detectieIncendiu.equals("da")) detalii_loc[2]=1;
			   if (locuinta.arePaza.equals("da")) detalii_loc[3]=1;
			   if (locuinta.zonaIzolata.equals ("da")) detalii_loc[4]=1;
			   if (locuinta.locuitPermanent.equals ("da")) detalii_loc[5]=1;
			   if (locuinta.clauzaFurtBunuri.equals ("da")) detalii_loc[6]=1;
			   if (locuinta.clauzaApaConducta.equals("da")) detalii_loc[7]=1;
			   if (locuinta.areTeren.equals("da")) detalii_loc[8]=1;
				int cYear = Calendar.getInstance().get(Calendar.YEAR);
				int setYear = locuinta.anConstructie;
				if (1899<setYear && setYear<=cYear)
					((ImageView) findViewById(R.id.wrong_textan)).setVisibility(View.GONE);
				else ((ImageView) findViewById(R.id.wrong_textan)).setVisibility(View.VISIBLE);
				if (locuinta.etaj<=locuinta.regimInaltime)
					((ImageView) findViewById(R.id.wrong_textetaj)).setVisibility(View.GONE);
				else ((ImageView) findViewById(R.id.wrong_textetaj)).setVisibility(View.VISIBLE);
			   setTextAlteInf ();
		   }
	   }
	   
	   private void saveFromFormular()
	   {
		   locuinta.judet = judet;
		   locuinta.localitate = localitate;
		   locuinta.adresa = edtAdresa.getText().toString();   
		   int radioChecked = radioTipLoc.getCheckedRadioButtonId();
		   radioButtonTipLoc = (RadioButton) findViewById(radioChecked);
		   locuinta.tipLocuinta = radioButtonTipLoc.getTag().toString();
		   String structura = "";
		   if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i504)))
	    		structura="beton-armat";
	    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i505)))
	    		structura="beton";
	    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i506)))
	    		structura="bca";
	    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i507)))
	    		structura="caramida";
	    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i508)))
	    		structura="caramida-nearsa";
	    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i509)))
	    		structura="chirpici-piatra";
	    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i510)))
	    		structura="lemn";
	    	else if (edtStructuraRezistenta.getText().toString().equals(getString(R.string.i511)))
	    		structura="zidarie-lemn";
		   locuinta.structuraLocuinta = structura;
		   if (!edtInaltime.getText().toString().equals("")) 
			   try {
				   locuinta.regimInaltime = Integer.parseInt(edtInaltime.getText().toString());
			   }catch (Exception e) {
				// TODO: handle exception
				   locuinta.regimInaltime = -1;
			}
		   else locuinta.regimInaltime = -1;
		   if (!edtEtaj.getText().toString().equals("")) 
		   try{
		   locuinta.etaj = Integer.parseInt(edtEtaj.getText().toString());
		   }catch (Exception e) {
			// TODO: handle exception
			   locuinta.etaj = -1;
		   }
		   else locuinta.etaj = -1;
		   if (locuinta.tipLocuinta.equals("casa-vila-comuna")) locuinta.etaj=0;
		   if (locuinta.tipLocuinta.equals("casa-vila-individuala")) locuinta.etaj=0;
		   if (!edtAnConstructie.getText().toString().equals("")) 
			   try {
				   locuinta.anConstructie = Integer.parseInt(edtAnConstructie.getText().toString());
			   }catch (Exception e) {
				// TODO: handle exception
				   locuinta.anConstructie = -1; 
			}
		   else locuinta.anConstructie = -1; 
		   if (!edtSuprafata.getText().toString().equals("")) 
			   try{
				   locuinta.suprafataUtila = Integer.parseInt(edtSuprafata.getText().toString());
			   }catch (Exception e){
				   try{
			   locuinta.suprafataUtila = Integer.parseInt(edtSuprafata.getText().toString().substring(0,edtSuprafata.getText().toString().indexOf(' ')));
			   }catch (Exception f) {
			   try {
				// TODO: handle exception
				   if (edtSuprafata.getText().toString().length()>9)
					   try{
					   locuinta.suprafataUtila  = Integer.parseInt(edtSuprafata.getText().toString().substring(0,9));
					   }catch (Exception e2) {
						// TODO: handle exception
						   locuinta.suprafataUtila = -1;
					}
				   else  locuinta.suprafataUtila = -1;
			}catch (Exception g) {
				// TODO: handle exception
				locuinta.suprafataUtila = -1;
			}
			   }
			   }
		   else  locuinta.suprafataUtila = -1;
		   locuinta.nrCamere = Integer.parseInt(edtNrCamere.getText().toString());
		   locuinta.nrLocatari = Integer.parseInt(edtNrLoc.getText().toString());
		   toJSON = locuinta.locuintaToJSON (locuinta);
	   }
	   
	   private void setTextAlteInf ()
	   {
			String detalii="";
			   if (detalii_loc[0]==1) {locuinta.areAlarma="da"; detalii+=getString(R.string.i555)+" | ";}
			   else locuinta.areAlarma="";
			   if (detalii_loc[1]==1) {locuinta.areGrilajeGeam="da"; detalii+=getString(R.string.i556)+" | ";}
			   else locuinta.areGrilajeGeam="";
			   if (detalii_loc[2]==1) {locuinta.detectieIncendiu="da"; detalii+=getString(R.string.i557)+" | ";}
			   else locuinta.detectieIncendiu="";
			   if (detalii_loc[3]==1) {locuinta.arePaza="da"; detalii+=getString(R.string.i558)+" | ";}
			   else locuinta.arePaza="";
			   if (detalii_loc[4]==1) {locuinta.zonaIzolata="da";detalii+=getString(R.string.i559)+" | ";}
			   else locuinta.zonaIzolata="";
			   if (detalii_loc[5]==1) {locuinta.locuitPermanent="da";detalii+=getString(R.string.i560)+" | ";}
			   else locuinta.locuitPermanent="";
			   if (detalii_loc[6]==1) {locuinta.clauzaFurtBunuri="da";detalii+=getString(R.string.i561)+" | ";}
			   else locuinta.clauzaFurtBunuri="";
			   if (detalii_loc[7]==1) {locuinta.clauzaApaConducta="da";detalii+=getString(R.string.i562)+" | ";}
			   else locuinta.clauzaApaConducta="";
			   if (detalii_loc[8]==1) {locuinta.areTeren="da";detalii+=getString(R.string.i563)+" |";}
			   else locuinta.areTeren="";
			   edtAlteInf.setText (detalii);
	   }
	   
	   private int getCompletedCount ()
	   {
		   int nr=1;
		   if (edtJudLoc.getText().toString().length()!=0) nr++;
		   if (edtAdresa.getText().toString().length()!=0) nr++;
		   if (edtStructuraRezistenta.getText().toString().length()!=0)nr++;
		   if (edtInaltime.getText().toString().length()!=0) nr++;
		   if (edtEtaj.getText().toString().length()!=0) nr++;
		   if (edtAnConstructie.getText().toString().length()!=0) nr++;
		   if (edtNrCamere.getText().toString().length()!=0) nr++;
		   if (edtSuprafata.getText().toString().length()!=0) nr++;
		   if (edtNrLoc.getText().toString().length()!=0) nr++;
		   if (edtAlteInf.getText().toString().length()!=0) nr++;
		   return nr;
	   }

}

