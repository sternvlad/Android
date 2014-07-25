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
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class AsigurareRca extends Activity {

	RelativeLayout alegeMasinaLay,alegePersoanaLay;
	String firmaCasco="",mesajEroare="";
	AppSettings sett;
	RelativeLayout layoutReduceri;
	Boolean salveaza;
	ScrollView rca_scroll;
	TextView tvReduceri,tvCodCaen;
	String toJSONPers="";
	public static boolean skipNextPage = false;
	String toJSONAuto="";
	View viewCasco;
	EditText edtDataInceput,edtCodCaen;
	static EditText edtReduceri;
	RadioGroup radioAreCasco;
	boolean goToEditAuto = false;
	boolean goToEditAsigurat = false;
	final DatabaseConnector dbConnector = new DatabaseConnector(this);
	public static String dataInceput = null;
	TextView persoanaAleasa,masinaAleasa;
	RadioButton radioAllianz,radioOmniasig,radio3,radioCasco;
	Button alteleCasco,btnPlusDataInceput,btnMinusDataInceput,btnCalculeazaRca;
	
	
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
        setContentView(R.layout.asigurare_rca);
        goToEditAuto = false;
        goToEditAsigurat = false;
        sett = AppSettings.get(this);
        mesajEroare = sett.getMesajEroare();

        if (sett.getLanguage().equals("hu"))
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_rca_hu);
        } else if (sett.getLanguage().equals("en"))
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_rca_en);
        }else 
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_rca);
        }
        

        
        MainController.tvTitlu.setText("Calculator");
        sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());

        layoutReduceri = (RelativeLayout) findViewById(R.id.layout_reduceri);
        viewCasco = (View) findViewById(R.id.viewCasco);
        rca_scroll = (ScrollView) findViewById (R.id.scrollview_rca);

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
		
		if (sett.getLanguage().equals("hu")&& !YTOUtils.isTablet(getParent().getParent()))
		{
			((TextView) findViewById(R.id.text_header1)).setTextSize(18);
			((TextView) findViewById(R.id.text_header2)).setTextSize(10);
			((TextView) findViewById(R.id.text_header3)).setTextSize(10);
		}else if (sett.getLanguage().equals("hu")&& YTOUtils.isTablet(getParent().getParent()))
		{
			((TextView) findViewById(R.id.text_header1)).setTextSize(45);
			((TextView) findViewById(R.id.text_header2)).setTextSize(25);
			((TextView) findViewById(R.id.text_header3)).setTextSize(25);
		}
        
        alegeMasinaLay = (RelativeLayout) findViewById (R.id.alege_masina_rca_lay);
        alegeMasinaLay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MasinileMele.tipDate="Asigurare";
				salveaza=false;
				 if (goToEditAuto && MasinileMele.autovehiculCurent.isDirty){
						Intent masina = new Intent(getParent(), Masina.class);
						InfoMasina.tipLoad="view";
						skipNextPage = true;
						TabGroupActivity parentActivity = (TabGroupActivity)getParent();
						parentActivity.startChildActivity("Masina", masina);
				 }else if (GetObiecte.autovehicule!=null && GetObiecte.autovehicule.size()>0){
				Intent masinileMele = new Intent(getParent(), MasinileMele.class);
				skipNextPage = false;
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("Masinile Mele", masinileMele);
				}else{
					Intent masina = new Intent(getParent(), Masina.class);
					InfoMasina.tipLoad="add";
					skipNextPage = true;
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("Masina", masina);
				}
			}
		});
        
        masinaAleasa = (TextView) findViewById (R.id.alege_masina_rca);
        
        if (MasinileMele.autovehiculCurent.isDirty && !MasinileMele.autovehiculCurent.marcaAuto.equals("") && !MasinileMele.autovehiculCurent.marcaAuto.equals("") ) masinaAleasa.setText(MasinileMele.autovehiculCurent.marcaAuto +"\n"+MasinileMele.autovehiculCurent.modelAuto+","+MasinileMele.autovehiculCurent.nrInmatriculare);
        
        alegePersoanaLay = (RelativeLayout) findViewById (R.id.alege_persoana_rca_lay);
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
        persoanaAleasa = (TextView) findViewById (R.id.alege_persoana_rca);
        if (AltePersoane.persoanaAsigurata.isDirty && !AltePersoane.persoanaAsigurata.nume.equals("") && !AltePersoane.persoanaAsigurata.nume.equals("") ) persoanaAleasa.setText(AltePersoane.persoanaAsigurata.nume +"\n"+AltePersoane.persoanaAsigurata.codUnic+","+AltePersoane.persoanaAsigurata.judet);
        
        tvReduceri = (TextView) findViewById (R.id.textReduceri);
		radioAreCasco = (RadioGroup) findViewById (R.id.radioCASCO_rca);
		radioAllianz = (RadioButton) findViewById (R.id.radio_1_rca);
		radioOmniasig = (RadioButton) findViewById(R.id.radio_2_rca);
		radio3 = (RadioButton) findViewById (R.id.radio_3_rca);
		
		edtReduceri = (EditText)findViewById (R.id.edt_reduceri);	
		edtReduceri.setFocusable(false);
		
		layoutReduceri.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				salveaza = false;
				Reduceri.from = "rca";
				Intent reduceri = new Intent(getParent(), Reduceri.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("Reduceri", reduceri);
			}
		});
		
		tvReduceri.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				salveaza = false;
				Reduceri.from = "rca";
				Intent reduceri = new Intent(getParent(), Reduceri.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("Reduceri", reduceri);
			}
		});
		
		edtReduceri.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				salveaza = false;
				Reduceri.from = "rca";
				Intent reduceri = new Intent(getParent(), Reduceri.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("Reduceri", reduceri);
			}
		});
 		
		alteleCasco = (Button) findViewById (R.id.alteleCASCO_rca);
		alteleCasco.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RadioButtons.tipDate="CASCO";
				salveaza = false;
				Reduceri.from = "rca";
				int radioChecked = radioAreCasco.getCheckedRadioButtonId();
				radioCasco = (RadioButton) findViewById(radioChecked);
				if (radioCasco!=null){
				firmaCasco = radioCasco.getText().toString();
				RadioButtons.date=firmaCasco;}
				Intent i = new Intent(AsigurareRca.this, RadioButtons.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
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
		
		btnCalculeazaRca = (Button) findViewById (R.id.calculeaza_rca);
		
		btnCalculeazaRca.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dataInceput = edtDataInceput.getText().toString();
				if (!AltePersoane.persoanaAsigurata.isDirty || !AltePersoane.persoanaAsigurata.isValidForCompute()) 
				{
					persoanaAleasa.setTextColor(Color.RED);
					rca_scroll.fullScroll(ScrollView.FOCUS_UP);
					goToEditAsigurat = false;
				}
				else if (!MasinileMele.autovehiculCurent.isDirty || !MasinileMele.autovehiculCurent.isValidForRCA() || MasinileMele.autovehiculCurent.completedPercent()!=1) 
				{
					masinaAleasa.setTextColor(Color.RED);
					rca_scroll.fullScroll(ScrollView.FOCUS_UP);
					goToEditAuto = true;
				}
				else if (AltePersoane.persoanaAsigurata.tipPersoana.equals("juridica") && AltePersoane.persoanaAsigurata.codCaen.equals("") && edtCodCaen.getText().toString().equals(""))
				{
					Toast msg = Toast.makeText(getParent(), "Selecteaza codul CAEN pentru\n" + AltePersoane.persoanaAsigurata.nume + " !", Toast.LENGTH_LONG);
					msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
					((TextView)((LinearLayout)msg.getView()).getChildAt(0))
				    .setGravity(Gravity.CENTER_HORIZONTAL);
					msg.show();
				}
				else if (!isOnline())
				{
					errorDialog ("");
				}
				else 
				{
		      					saveFromFormular();
		      					dbConnector.updateObiectAsigurat(AltePersoane.persoanaAsigurata.idIntern,
		      		    			  "1",
		      		    			  toJSONPers
		      		    			  );
		      				GetObiecte.getPersoane(dbConnector);
		      				dbConnector.updateObiectAsigurat(MasinileMele.autovehiculCurent.idIntern,
		      			   			  "2",
		      			   			  toJSONAuto
		      			   			  );
		      				GetObiecte.getAutovehicule(dbConnector);
							Intent loadingScreen = new Intent(getParent(), CallWebServiceRCA.class);
							TabGroupActivity parentActivity = (TabGroupActivity)getParent();
							parentActivity.startChildActivity("LoadingScreen", loadingScreen);
		                    	
		                
				}
		}
	});

		tvCodCaen = (TextView) findViewById (R.id.textCodCaen);
		edtCodCaen = (EditText) findViewById (R.id.edtCodCaen);
		edtCodCaen.setOnClickListener (new OnClickListener()
		{
		    @Override
		    public void onClick(View v)
		    {
		        	Lista.tipDate = "cod_caen";
		        	salveaza = false;
		        	Intent i = new Intent (AsigurareRca.this,Lista.class);
		        	i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		        	startActivity (i);
		        	
		        	
		    }
		});
		edtCodCaen.setFocusable(false);
		setFieldsByTipPersoana();
		

		Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
		
		edtDataInceput.setTypeface(Typeface.create(tf,Typeface.BOLD));
		edtCodCaen.setTypeface(Typeface.create(tf,Typeface.BOLD));
		persoanaAleasa.setTypeface(Typeface.create(tf,Typeface.BOLD));
		masinaAleasa.setTypeface(Typeface.create(tf,Typeface.BOLD));
		load();
		
	}

	public static void loadEditText (Context ctx)
	{
				String text = "";
				int i=0;
				if (AltePersoane.persoanaAsigurata.casatorit.equals("da")) {
					text += ctx.getString (R.string.i576) + " | ";
					i++;
				}
				if (AltePersoane.persoanaAsigurata.copiiMinori.equals("da")){
					text += ctx.getString (R.string.i574)+" | ";
					i+=2;
				}
				if (AltePersoane.persoanaAsigurata.pensionar.equals("da")){
					text += ctx.getString (R.string.i577) + " | ";
					i++;
				}else  if (i==3){
					text += "...";
					i++;
				}
				if (i<3 && AltePersoane.persoanaAsigurata.handicapLocomotor.equals("da")){
					text +=ctx.getString (R.string.i575)+" | ";
					i+=2;
				}else  if (i==3) {
					text += "...";
					i++;
				}
				if (i<3 && Integer.parseInt(AltePersoane.persoanaAsigurata.nrBugetari) > 0){
					if (Integer.parseInt(AltePersoane.persoanaAsigurata.nrBugetari) == 1)
						text +="1 "+ctx.getString (R.string.i578)+" | ";
					else text += AltePersoane.persoanaAsigurata.nrBugetari +  ctx.getString (R.string.i578)+" | ";
					i++;
				}else if (i==3) {
					text += "...";
					i++;
				}
				
				edtReduceri.setText(text);
	}
	private void load()
	{
		if (AltePersoane.persoanaAsigurata.isDirty)
		{ 
			if (AltePersoane.persoanaAsigurata.tipPersoana.equals("fizica"))
				loadEditText(getParent());
			else
				edtCodCaen.setText(AltePersoane.persoanaAsigurata.codCaen);
		}
		if (MasinileMele.autovehiculCurent.isDirty && !MasinileMele.autovehiculCurent.cascoLa.equals(""))
		{
			firmaCasco=MasinileMele.autovehiculCurent.cascoLa;
			if (firmaCasco.equals("Allianz")) {radioAllianz.setChecked(true);radio3.setText("Generali");}
			else if (firmaCasco.equals("Omniasig")) {radioOmniasig.setChecked(true);radio3.setText("Generali");}
			else if (!firmaCasco.equals(""))
		{
				radio3.setText (firmaCasco);
				radio3.setChecked(true);
		}
		}
	}

	private void saveFromFormular()
	{
		if (AltePersoane.persoanaAsigurata.isDirty && AltePersoane.persoanaAsigurata.tipPersoana.equals("juridica"))
		{
			AltePersoane.persoanaAsigurata.codCaen = edtCodCaen.getText().toString();
		}
		dataInceput = edtDataInceput.getText().toString();
		if (MasinileMele.autovehiculCurent!=null){
		int radioChecked = radioAreCasco.getCheckedRadioButtonId();
		radioCasco = (RadioButton) findViewById(radioChecked);
		if (radioChecked!=-1)
			MasinileMele.autovehiculCurent.cascoLa = radioCasco.getText().toString();
		}
		
		if (AltePersoane.persoanaAsigurata.isDirty) toJSONPers =AltePersoane.persoanaAsigurata.persoanaToJSON (AltePersoane.persoanaAsigurata);
		if (MasinileMele.autovehiculCurent.isDirty) toJSONAuto = MasinileMele.autovehiculCurent.autovehiculToJSON(MasinileMele.autovehiculCurent);
	}
	
	private void setFieldsByTipPersoana()
	{
		if (!AltePersoane.persoanaAsigurata.isDirty)
		{
			viewCasco.setVisibility(View.GONE);
			tvCodCaen.setVisibility(View.GONE);
			edtCodCaen.setVisibility(View.GONE);
			tvReduceri.setVisibility(View.GONE);
			layoutReduceri.setVisibility(View.GONE);
			
		}
		else if (AltePersoane.persoanaAsigurata.tipPersoana.equals("fizica"))
		{
			tvCodCaen.setVisibility(View.GONE);
			edtCodCaen.setVisibility(View.GONE);
			viewCasco.setVisibility(View.VISIBLE);
			tvReduceri.setVisibility(View.VISIBLE);
			layoutReduceri.setVisibility(View.VISIBLE);
		}
		else if (AltePersoane.persoanaAsigurata.tipPersoana.equals("juridica"))
		{
			tvCodCaen.setVisibility(View.VISIBLE);
			edtCodCaen.setVisibility(View.VISIBLE);
			tvReduceri.setVisibility(View.GONE);
			layoutReduceri.setVisibility(View.GONE);
			viewCasco.setVisibility(View.GONE);
		}
	}
	
	public void onResume()
	{
		super.onResume();
		goToEditAuto = false;
		goToEditAsigurat = false;
		if (dataInceput!=null && dataInceput.length()>0) edtDataInceput.setText(dataInceput);
		salveaza = true;
		MainController.tvTitlu.setText("Calculator");
        sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());
		setFieldsByTipPersoana();
		mesajEroare = sett.getMesajEroare();
		persoanaAleasa.setTextColor(Color.parseColor("#464646"));
		masinaAleasa.setTextColor(Color.parseColor("#464646"));
		load();
		if (MasinileMele.autovehiculCurent.isDirty)
				masinaAleasa.setText (MasinileMele.autovehiculCurent.marcaAuto+"\n"+MasinileMele.autovehiculCurent.modelAuto+","+MasinileMele.autovehiculCurent.nrInmatriculare);
		if (RadioButtons.tipDate.equals("CASCO"))
		{
			firmaCasco=RadioButtons.date;
			RadioButtons.tipDate="";
			if (firmaCasco.equals("Allianz")) {radioAllianz.setChecked(true);radio3.setText("Generali");}
			else if (firmaCasco.equals("Omniasig")) {radioOmniasig.setChecked(true);radio3.setText("Generali");}
			else if (!firmaCasco.equals(""))
		{
				radio3.setText (firmaCasco);
				radio3.setChecked(true);
		}
			RadioButtons.date="";
		}
		if (Lista.tipDate.equals("cod_caen"))
		{
			edtCodCaen.setText(Lista.date);
			Lista.tipDate="";
			Lista.date="";
		}
		if (!mesajEroare.equals(""))
		{
			errorDialog(mesajEroare);
			sett.updateMesajEroare("");
		}
	}
	
	public void errorDialog (String mesaj) 
	{ //eroare
	     final Dialog dialog=new Dialog(getParent());
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog_error);
	     TextView tvContinut;
	     TextView textHeader;
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     textHeader = (TextView) dialog.findViewById(R.id.text_eroare_header);
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
	   //  ImageView imgContinut;
	     tvContinut = (TextView)dialog.findViewById (R.id.text_error);
	     Button btn_ok=(Button)dialog.findViewById(R.id.button_ok);
	     if (!mesaj.equals(""))  tvContinut.setText (mesaj);
	    //imgContinut = (ImageView)dialog. findViewById (R.id.imagine_error);
	     
	     btn_ok.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	     }
	     });

	   dialog.show();
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

	public void onPause ()
	{
		super.onPause();
		if (salveaza){
		if (AltePersoane.persoanaAsigurata.isDirty){
			saveFromFormular();
			 dbConnector.updateObiectAsigurat(AltePersoane.persoanaAsigurata.idIntern,
		    			  "1",
		    			  toJSONPers
		    			  );  
			 GetObiecte.getPersoane(dbConnector);
			}
			if (MasinileMele.autovehiculCurent.isDirty){
				saveFromFormular();
				dbConnector.updateObiectAsigurat(MasinileMele.autovehiculCurent.idIntern,
	   			  "2",
	   			  toJSONAuto
	   			  );  
			 GetObiecte.getAutovehicule(dbConnector);
			}
		}
	}

}
