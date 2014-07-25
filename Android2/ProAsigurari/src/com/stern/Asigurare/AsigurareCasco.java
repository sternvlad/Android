package com.stern.Asigurare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ActivityGroup;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class AsigurareCasco extends ActivityGroup {

	RelativeLayout alegeMasinaLay,alegePersoanaLay;
	RadioGroup radioAreCasco,radioRate;
	Boolean salveaza;
	Dialog dialog;
	ProgressDialog dlg;
	ProgressBar progressBar;
	AppSettings sett;
	ScrollView scroll_casco;
    TextView tvTitlu;
    TextView tvReduceri;
    View viewCasco;
    RelativeLayout layoutReduceri;
    int width;
    public static String culoare = "";
	TextView tvContinut;
    ImageView imgTitlu;
    Button btn_nu,btn_da;
	String toJSONPers="",toJSONAuto="";
	final DatabaseConnector dbConnector = new DatabaseConnector(this);
	TextView persoanaAleasa,masinaAleasa;
	RadioButton radioAllianz,radioOmniasig,radio3,radioCasco,radioInt,radio2Rate,radio4Rate;
	String firmaCasco="";
	public String denumire,nume,cnp,cui,codCaen,mesaj,mesajOK;
	int nrrate;
	boolean goToEditAuto = false;
	Button alteleCasco,cereOferta;
	EditText edtnrKmAuto,edtCuloare;
	static EditText edtReduceri;
	
	public void onCreate(Bundle savedInstanceState) {
		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	width = display.getWidth(); 
    	Window window = getWindow(); 
//    	if (YTOUtils.isTablet(this)) {	    	
//	    	window.setGravity(Gravity.CENTER); 
//	    	window.setLayout(400, 580);
//    	}
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asigurare_casco);
        sett = AppSettings.get(this);
        MainController.tvTitlu.setText("Asigurare Casco");
        sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());
        
        goToEditAuto = false;
        scroll_casco = (ScrollView) findViewById (R.id.scrollview_casco);
        
        alegeMasinaLay = (RelativeLayout) findViewById (R.id.alege_masina_casco_lay);
        alegeMasinaLay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MasinileMele.tipDate="Asigurare";
				salveaza = false;
				if (goToEditAuto && MasinileMele.autovehiculCurent.isDirty){
					Intent masina = new Intent(getParent(), Masina.class);
					InfoMasina.tipLoad="view";
					AsigurareRca.skipNextPage = true;
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("Masina", masina);
				}
				else if (GetObiecte.autovehicule!=null && GetObiecte.autovehicule.size()>0){
					Intent masinileMele = new Intent(getParent(), MasinileMele.class);
					AsigurareRca.skipNextPage = false;
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("Masinile Mele", masinileMele);
					}else{
						Intent masina = new Intent(getParent(), Masina.class);
						InfoMasina.tipLoad="add";
						AsigurareRca.skipNextPage = true;
						TabGroupActivity parentActivity = (TabGroupActivity)getParent();
						parentActivity.startChildActivity("Masina", masina);
					}
			}
		});
        masinaAleasa = (TextView) findViewById (R.id.alege_masina_casco);
        if (MasinileMele.autovehiculCurent.isDirty && !MasinileMele.autovehiculCurent.marcaAuto.equals("")) masinaAleasa.setText(MasinileMele.autovehiculCurent.marcaAuto  + "\n" + MasinileMele.autovehiculCurent.modelAuto + "," + MasinileMele.autovehiculCurent.nrInmatriculare);
        
        alegePersoanaLay = (RelativeLayout) findViewById (R.id.alege_persoana_casco_lay);
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
        
        persoanaAleasa = (TextView) findViewById (R.id.alege_persoana_casco);
        if (AltePersoane.persoanaAsigurata.isDirty && !AltePersoane.persoanaAsigurata.nume.equals("")) persoanaAleasa.setText(AltePersoane.persoanaAsigurata.nume  + "\n" + AltePersoane.persoanaAsigurata.codUnic + "," + AltePersoane.persoanaAsigurata.judet);
        
        edtnrKmAuto = (EditText) findViewById (R.id.edt_km_auto);
        edtCuloare = (EditText) findViewById (R.id.edt_culoare_auto);
        
		radioAreCasco = (RadioGroup) findViewById (R.id.radioCASCO_casco);
		radioAllianz = (RadioButton) findViewById (R.id.radio_1_casco);
		radioOmniasig = (RadioButton) findViewById(R.id.radio_2_casco);
		radio3 = (RadioButton) findViewById (R.id.radio_3_casco);
		
		alteleCasco = (Button) findViewById (R.id.alteleCASCO_casco);
		alteleCasco.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RadioButtons.tipDate="CASCO";
				int radioChecked = radioAreCasco.getCheckedRadioButtonId();
				radioCasco = (RadioButton) findViewById(radioChecked);
				if (radioCasco!=null){
				firmaCasco = radioCasco.getText().toString();
				RadioButtons.date=firmaCasco;}
				Intent i = new Intent (AsigurareCasco.this,RadioButtons.class);
				startActivity (i);
			}
		});
		
		radioRate = (RadioGroup) findViewById (R.id.radioRATA);
		radioInt = (RadioButton) findViewById (R.id.radio_integral);
		radio2Rate = (RadioButton) findViewById (R.id.radio_2_rate);
		radio4Rate = (RadioButton) findViewById (R.id.radio_4_rate);
		
		edtReduceri = (EditText) findViewById(R.id.edt_reduceri);
		tvReduceri = (TextView) findViewById(R.id.textReduceri);
		layoutReduceri = (RelativeLayout) findViewById(R.id.layout_reduceri);
		viewCasco = (View) findViewById(R.id.viewCasco);
		hideIfJuridica();
		layoutReduceri.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				salveaza = false;
				Reduceri.from = "casco";
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
				Reduceri.from = "casco";
				culoare = edtCuloare.getText().toString();
				Intent reduceri = new Intent(getParent(), Reduceri.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("Reduceri", reduceri);
			}
		});
		
		edtReduceri.setFocusable(false);
		edtReduceri.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				salveaza = false;
				Reduceri.from = "casco";
				Intent reduceri = new Intent(getParent(), Reduceri.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("Reduceri", reduceri);
			}
		});
 		
		
		cereOferta = (Button) findViewById (R.id.calculeaza_casco);
		cereOferta.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isOnline())
					{
						errorDialog ("Ne pare rau! \nCererea nu a fost trimisa pentru ca nu esti conectat la internet. \nTe rugam sa te asiguri ca ai o conexiune la internet activa si calculeaza din nou.\n Iti multumim!",0);
					}
				else if (!AltePersoane.persoanaAsigurata.isDirty  || !AltePersoane.persoanaAsigurata.isValidForCompute()) 
				{
					persoanaAleasa.setTextColor(Color.RED);
					scroll_casco.fullScroll(ScrollView.FOCUS_UP);
				}
				else if (!MasinileMele.autovehiculCurent.isDirty || !MasinileMele.autovehiculCurent.isValidForRCA() || MasinileMele.autovehiculCurent.completedPercent()!=1) 
				{
					masinaAleasa.setTextColor(Color.RED);
					goToEditAuto = true;
					scroll_casco.fullScroll(ScrollView.FOCUS_UP);
				}
				else if (MasinileMele.autovehiculCurent.anFabricatie + 10 < Calendar.getInstance().get(Calendar.YEAR))
				{
					errorDialog ("Din pacate, nicio companie de asigurare nu ofera CASCO pentru masini mai vechi de 10 ani.De aceea masina ta nu poate fi asigurata CASCO",1);//text masina mai veche de 10 ani
				}
				else if (edtCuloare.getText().toString().equals(""))
				{
					edtCuloare.requestFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(edtCuloare, InputMethodManager.SHOW_IMPLICIT);
				}
				else if (sett.getTelefonComanda().equals("") || sett.getEmailContact().equals(""))
				{
					showErrorDialogDate();
				}
				else 
				{
					saveFromFormular();
					dbConnector.updateObiectAsigurat(MasinileMele.autovehiculCurent.idIntern,
					   			  "2",
					   			  toJSONAuto
					   			  );  
					GetObiecte.getAutovehicule(dbConnector);
					confirmDialog ();
				}
			}
		});
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");

		 persoanaAleasa.setTypeface(Typeface.create(tf,Typeface.BOLD));
		 masinaAleasa.setTypeface(Typeface.create(tf,Typeface.BOLD));
		 edtnrKmAuto.setTypeface(Typeface.create(tf,Typeface.BOLD));
		 edtCuloare.setTypeface(Typeface.create(tf,Typeface.BOLD));
		 
		 loadEditText(getParent());
	}
	
	
	
	
	private void load()
	{
		if (MasinileMele.autovehiculCurent.isDirty)
		{
			if (MasinileMele.autovehiculCurent.nrKm!=-1) edtnrKmAuto.setText(MasinileMele.autovehiculCurent.nrKm + "");
			else edtnrKmAuto.setText("0");
			edtCuloare.setText (MasinileMele.autovehiculCurent.culoare);
		}
		if (MasinileMele.autovehiculCurent.isDirty && !MasinileMele.autovehiculCurent.cascoLa.equals(""))
		{
			if (MasinileMele.autovehiculCurent.cascoLa.equals("Allianz")) {radioAllianz.setChecked(true); radio3.setText("Generali");}
			   else 
				   if (MasinileMele.autovehiculCurent.cascoLa.equals("Omniasig")) {radioOmniasig.setChecked(true);radio3.setText("Generali");}
			   else 
				   if (!MasinileMele.autovehiculCurent.cascoLa.equals("")) {radio3.setChecked (true); radio3.setText(MasinileMele.autovehiculCurent.cascoLa);}
		}
	}
	
	public void onPause()
	{
		super.onPause();
		if (salveaza){
		if (MasinileMele.autovehiculCurent.isDirty){
			saveFromFormular();
			dbConnector.updateObiectAsigurat(MasinileMele.autovehiculCurent.idIntern,
   			  "2",
   			  toJSONAuto
   			  );  
		 GetObiecte.getAutovehicule(dbConnector);
		}
		}
		InputMethodManager imm = (InputMethodManager) 
		        getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(edtCuloare.getWindowToken(), 0);
	}
	
	private void saveFromFormular()
	{
		try{
		MasinileMele.autovehiculCurent.nrKm=Integer.parseInt(edtnrKmAuto.getText().toString());
		}catch (Exception e) {
			// TODO: handle exception
			edtnrKmAuto.setText("0");
			MasinileMele.autovehiculCurent.nrKm = 0;
		}
		MasinileMele.autovehiculCurent.culoare=edtCuloare.getText().toString();
		if (MasinileMele.autovehiculCurent.isDirty){
		int radioChecked = radioAreCasco.getCheckedRadioButtonId();
		radioCasco = (RadioButton) findViewById(radioChecked);
		if (radioChecked!=-1)
			MasinileMele.autovehiculCurent.cascoLa = radioCasco.getText().toString();
		}
		if (MasinileMele.autovehiculCurent.isDirty) toJSONAuto = MasinileMele.autovehiculCurent.autovehiculToJSON(MasinileMele.autovehiculCurent);
	}
	
	public void onResume()
	{
		super.onResume();
		goToEditAuto = false;
		hideIfJuridica();
		loadEditText(getParent());
		salveaza = true;
		MainController.tvTitlu.setText("Asigurare Casco");
        sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());
		load();
		persoanaAleasa.setTextColor(Color.parseColor("#464646"));
		masinaAleasa.setTextColor(Color.parseColor("#464646"));

			if (AltePersoane.persoanaAsigurata.isDirty && AltePersoane.persoanaAsigurata.nume!="")
				persoanaAleasa.setText(AltePersoane.persoanaAsigurata.nume  + "\n" + AltePersoane.persoanaAsigurata.codUnic + "," + AltePersoane.persoanaAsigurata.judet);
		if (MasinileMele.autovehiculCurent.isDirty){
				masinaAleasa.setText (MasinileMele.autovehiculCurent.marcaAuto + "\n" + MasinileMele.autovehiculCurent.modelAuto + "," + MasinileMele.autovehiculCurent.nrInmatriculare);
				if (!MasinileMele.autovehiculCurent.culoare.equals(""))
					edtCuloare.setText(MasinileMele.autovehiculCurent.culoare);
				else edtCuloare.setText(culoare);
		}
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
	}
	
	public void confirmDialog () { //functie pop up
	     dialog=new Dialog(getParent());
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.popup_inalt);
//	     if (YTOUtils.isTablet(this))
//	    	 dialog.getWindow().setLayout(400, 580);
//	     else dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

	     btn_nu=(Button)dialog.findViewById(R.id.button_nu);
	     btn_nu.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	     }
	     });

	     
	     btn_da=(Button)dialog.findViewById(R.id.button_da);
	     btn_da.setOnClickListener(new OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	new Trimite().execute(null, null, null);
		    	dialog.dismiss();
	            }	
		     });
	   dialog.show();
	}
	
	private class Trimite extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute(){  
			dlg = new ProgressDialog(getParent());
			dlg.setMessage("se trimit datele...");
			dlg.setCancelable(false);
			dlg.show();    
		}
		@Override
		protected Void doInBackground(Void... arg0) {
	    	if (radioInt.isChecked()) nrrate=1;
	    	else if (radio2Rate.isChecked()) nrrate=2;
	    	else if (radio4Rate.isChecked()) nrrate=4;
			
			if (AltePersoane.persoanaAsigurata.tipPersoana.equals("juridica"))
			{
				codCaen = AltePersoane.persoanaAsigurata.codCaen;
			}
			else 
			{
				codCaen = "";
			}    	
	    	
			String telefon = sett.getTelefonComanda();
			String email = sett.getEmailContact();
			String xml ="<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<soap:Body>" + 
    "<RequestCasco2 xmlns='http://tempuri.org/'>" + 
     "<user>vreaurca</user>" + 
      "<password>123</password>" + 
      "<tip_persoana>" + AltePersoane.persoanaAsigurata.tipPersoana +"</tip_persoana>" + 
      "<nume>" + AltePersoane.persoanaAsigurata.nume +"</nume>" + 
      "<codunic>" + AltePersoane.persoanaAsigurata.codUnic +"</codunic>" + 
      "<telefon>" + telefon +"</telefon>" + 
      "<email>" + email +"</email>" + 
      "<strada>" + AltePersoane.persoanaAsigurata.adresa +"</strada>" + 
      "<judet>" + AltePersoane.persoanaAsigurata.judet +"</judet>" + 
      "<localitate>" + AltePersoane.persoanaAsigurata.localitate +"</localitate>" + 
      "<data_permis>" + YTOUtils.setAnPermis(AltePersoane.persoanaAsigurata.dataPermis) +"</data_permis>" + 
      "<casatorit>" + (AltePersoane.persoanaAsigurata.casatorit.equals("nu") ? "" : AltePersoane.persoanaAsigurata.casatorit)  +"</casatorit>" + 
      "<copii_minori>" + (AltePersoane.persoanaAsigurata.copiiMinori.equals("nu") ? "" :AltePersoane.persoanaAsigurata.copiiMinori) +"</copii_minori>" + 
      "<pensionar>" + (AltePersoane.persoanaAsigurata.pensionar.equals("nu") ? "" : AltePersoane.persoanaAsigurata.pensionar) +"</pensionar>" + 
      "<nr_bugetari>" + AltePersoane.persoanaAsigurata.nrBugetari +"</nr_bugetari>" + 
      "<tip_inregistrare>" + "inmatriculat" +"</tip_inregistrare>" + 
      "<caen>" + codCaen +"</caen>" + 
      "<subtip_activitate>" + "" +"</subtip_activitate>" + 
      "<index_categorie_auto>" + MasinileMele.autovehiculCurent.categorieAuto +"</index_categorie_auto>" + 
      "<subcategorie_auto>" + MasinileMele.autovehiculCurent.subcategorieAuto +"</subcategorie_auto>" + 
      "<in_leasing>" + MasinileMele.autovehiculCurent.inLeasing +"</in_leasing>" + 
      "<leasing_firma>" + MasinileMele.autovehiculCurent.firmaLeasing +"</leasing_firma>" + 
      "<leasing_cui>" + "" +"</leasing_cui>" + 
      "<leasing_judet>" + "" +"</leasing_judet>" + 
      "<leasing_localitate>" + "" +"</leasing_localitate>" + 
      "<serie_civ>" + MasinileMele.autovehiculCurent.serieCiv +"</serie_civ>" + 
      "<marca>" + MasinileMele.autovehiculCurent.marcaAuto +"</marca>" + 
      "<model>" + MasinileMele.autovehiculCurent.modelAuto +"</model>" + 
      "<nr_inmatriculare>" + MasinileMele.autovehiculCurent.nrInmatriculare +"</nr_inmatriculare>" + 
      "<serie_sasiu>" + MasinileMele.autovehiculCurent.serieSasiu +"</serie_sasiu>" + 
      "<casco>" + MasinileMele.autovehiculCurent.cascoLa +"</casco>" + 
      "<marca_id>" + "" +"</marca_id>" + 
      "<auto_nou>" + "nu" +"</auto_nou>" + 
      "<cm3>" + MasinileMele.autovehiculCurent.cm3 +"</cm3>" + 
      "<kw>" + MasinileMele.autovehiculCurent.putere +"</kw>" + 
      "<combustibil>" + MasinileMele.autovehiculCurent.combustibil +"</combustibil>" + 
      "<nr_locuri>" + MasinileMele.autovehiculCurent.nrLocuri +"</nr_locuri>" + 
      "<masa_maxima>" + MasinileMele.autovehiculCurent.masaMaxima +"</masa_maxima>" + 
      "<an_fabricatie>" + MasinileMele.autovehiculCurent.anFabricatie +"</an_fabricatie>" + 
      "<destinatie_auto>" + MasinileMele.autovehiculCurent.destinatieAuto +"</destinatie_auto>" + 
      "<culoare>" + edtCuloare.getText().toString() +"</culoare>" + 
      "<nr_km>" + edtnrKmAuto.getText().toString() +"</nr_km>" + 
      "<nr_rate>" + nrrate +"</nr_rate>" + 
      "<udid>" + sett.getDeviceId() +"</udid>" + 
      "<id_intern>" + MasinileMele.autovehiculCurent.idIntern +"</id_intern>" + 
      "<platforma>" +  "Android" +"</platforma>" + 
    "</RequestCasco2>" + 
  "</soap:Body>" + 
"</soap:Envelope>";
			
			
			String url = GetObiecte.link+"casco.asmx";
			String soapAction = "http://tempuri.org/RequestCasco2";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			if (resp.equals("")) {
				dlg.dismiss();
				mesaj = "Serviciul de oferta CASCO nu este disonibil momentan. Va rugam incercati mai tarziu";
			}
			else {
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();
					XMLHelperCasco myXmlHelper = new XMLHelperCasco();
					xr.setContentHandler(myXmlHelper);
					InputSource is = new InputSource(new StringReader(resp)); 
					xr.parse(is);
					String raspuns = myXmlHelper.getParsedData();
					if (raspuns != null) {
						sett.updateCodRaspuns(raspuns);
						mesajOK = raspuns;
					} else {
						dlg.dismiss();
						mesaj = "Serviciul de oferta CASCO nu este disonibil momentan. Va rugam incercati mai tarziu";
					}
				} catch (Exception ex) {
					dlg.dismiss();							
				}
			}
			return null;
		}
 
		@Override
		protected void onPostExecute(final Void unused){			
				if (dlg.isShowing() && mesajOK.length() > 0) {
					dlg.dismiss();
					
						dialog=new Dialog(getParent());
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.dialog);
			     
						 tvTitlu = (TextView)dialog.findViewById (R.id.text_titlu);
						 tvContinut = (TextView)dialog. findViewById (R.id.text_continut);
						 //imgTitlu = (ImageView)dialog. findViewById (R.id.imagine_titlu);
				    	 tvTitlu.setText("Cerere CASCO");
				    	 tvContinut.setText (mesajOK);
				    	 if (YTOUtils.isTablet(getParent()))
								tvContinut.setTextSize(25);
							else tvContinut.setTextSize(12);
				    	 Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
							
					     TextView textHeader = (TextView) dialog.findViewById(R.id.text_vrei_sa_stergi);
					     textHeader.setTextColor(getResources().getColor(R.color.rosu_ter));
					     textHeader.setText(getString(R.string.i805));
					     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
				    	 btn_nu=(Button)dialog.findViewById(R.id.button_nu);
//				    	 if (appInstalledOrNot("com.facebook.katana")){
//				    		 //btn_nu.setBackgroundResource(R.drawable.buton_share_fb);
//				    		 btn_nu.setText("");
//				    		 if (YTOUtils.isTablet(getParent())) btn_nu.setTextSize(25);
//				    	 }
				    	 btn_nu.setVisibility(View.GONE);//scoate asta pt facebook share
//				    	 //Button btnTweet = (Button) dialog.findViewById(R.id.button_tweet);
//				    	// if (appInstalledOrNot("com.twitter.android"))
//				    		// btnTweet.setVisibility(View.VISIBLE);
//				    	 if (YTOUtils.isTablet(getParent())) //btnTweet.setTextSize(25);
//				    	 //btnTweet.setText("");
//				    	 btnTweet.setVisibility(View.GONE);//scoate asta pt tweeter share
//				    	 //btnTweet.setOnClickListener(new OnClickListener() {
//							
//							@Override
//							//public void onClick(View v) {
//								// TODO Auto-generated method stub
//								Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
//								shareIntent.setType("text/plain");
//								shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Smart choice: am cerut rapid & usor o oferta CASCO direct de pe " + YTOUtils.getDeviceName() +" ! :) http://goo.gl/D55rl");
//								PackageManager pm = v.getContext().getPackageManager();
//								List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
//								for (final ResolveInfo app : activityList) {
//								    if ("com.twitter.android.PostActivity".equals(app.activityInfo.name)) {
//								        final ActivityInfo activity = app.activityInfo;
//								        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
//								        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//								        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |             Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//								        shareIntent.setComponent(name);
//								        v.getContext().startActivity(shareIntent);
//								        break;
//								   }
//								}
//								dialog.dismiss();
//								finish();
//							}
//						});
				    	 
//				    	 btn_nu.setOnClickListener(new OnClickListener() {
//							
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								
//								Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
//								shareIntent.setType("text/plain");
//								String text = "Smart choice: am cerut rapid & usor o oferta CASCO, direct de pe " + YTOUtils.getDeviceName() +" ! :) http://goo.gl/D55rl";
//								shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,text );
//								PackageManager pm = v.getContext().getPackageManager();
//								List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
//								for (final ResolveInfo app : activityList) {
//								    if ((app.activityInfo.name).contains("facebook")) {
//								        final ActivityInfo activity = app.activityInfo;
//								        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
//								        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//								        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |             Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//								        shareIntent.setComponent(name);
//								        v.getContext().startActivity(shareIntent);
//								        break;
//								   }
//								}
//
//    							
//     
//
//								dialog.dismiss();
//								finish();
//							}
//
//				
//						});
//								
    
				    	 
				    	 btn_da=(Button)dialog.findViewById(R.id.button_da);
				    	 btn_da.setText("OK");		
				    	 btn_da.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								finish();
							}
						});
				    	 dialog.show();
				     	}	
				else 
				{
					errorDialog(mesaj,0);
				}
		}
	}
	
	void createExternalStoragePrivateFile() {
	    // Create a path where we will place our private file on external
	    // storage.
	    File file = new File(getExternalFilesDir(null), "DemoFile.jpg");

	    try {
	        // Very simple code to copy a picture from the application's
	        // resource into the external file.  Note that this code does
	        // no error checking, and assumes the picture is small (does not
	        // try to copy it in chunks).  Note that if external storage is
	        // not currently mounted this will silently fail.
	        InputStream is = getResources().openRawResource(R.drawable.socialmedia_android_casco);
	        OutputStream os = new FileOutputStream(file);
	        byte[] data = new byte[is.available()];
	        is.read(data);
	        os.write(data);
	        is.close();
	        os.close();
	    } catch (IOException e) {
	        // Unable to create file, likely because external storage is
	        // not currently mounted.
	        Log.w("ExternalStorage", "Error writing " + file, e);
	    }
	}

	
	 private boolean appInstalledOrNot(String uri)
	    {
	        PackageManager pm = getPackageManager();
	        boolean app_installed = false;
	        try
	        {
	               pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
	               app_installed = true;
	        }
	        catch (PackageManager.NameNotFoundException e)
	        {
	               app_installed = false;
	        }
	        return app_installed ;
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
	
	private void hideIfJuridica ()
	{
		if (AltePersoane.persoanaAsigurata.isDirty && AltePersoane.persoanaAsigurata.tipPersoana.equals("fizica"))
		{
			viewCasco.setVisibility(View.VISIBLE);
			tvReduceri.setVisibility(View.VISIBLE);
			layoutReduceri.setVisibility(View.VISIBLE);
			edtReduceri.setVisibility(View.VISIBLE);
		}else{
			viewCasco.setVisibility(View.GONE);
			tvReduceri.setVisibility(View.GONE);
			layoutReduceri.setVisibility(View.GONE);
			edtReduceri.setVisibility(View.GONE);
		}
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
				text +="1 | "+ctx.getString (R.string.i578);
			else text += AltePersoane.persoanaAsigurata.nrBugetari +  ctx.getString (R.string.i578)+" | ";
			i++;
		}else if (i==3) {
			text += "...";
			i++;
		}
		edtReduceri.setText(text);
	}
	
	public void showErrorDialogDate()
	{
		 final Dialog dialog=new Dialog(getParent());
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog_error_date);
	     TextView textHeader;
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     textHeader = (TextView) dialog.findViewById(R.id.text_eroare_header);
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
	     Button btn_ok=(Button)dialog.findViewById(R.id.button_ok);
	     btn_ok.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	     }
	     });

	   dialog.show();
	}
	
	public void errorDialog (String mesaj,int index) { //eroare
	     final Dialog dialog=new Dialog(getParent());
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog_error);
	     TextView tvContinut;
	     Button btn_ok=(Button)dialog.findViewById(R.id.button_ok);
	     tvContinut = (TextView)dialog.findViewById (R.id.text_error);
	     if (mesaj==null)
	    	 tvContinut.setText ("Te rugam sa te asiguri ca ai o conexiune la internet activa si sa incerci din nou.\n Iti multumim!");
	     else if (!mesaj.equals(""))  tvContinut.setText (mesaj);
	     //imgContinut = (ImageView)dialog. findViewById (R.id.imagine_error);
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
	     if (index == 1)
	     {
	    	 textHeader.setTextColor(getResources().getColor(R.color.ColorTitlu));
		     textHeader.setText(getString(R.string.i806));
	    	 ((TextView)dialog.findViewById(R.id.text_titlu)).setText("");
	     }
	     btn_ok.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	     }
	     });

	   dialog.show();
	}

	  
	  
	  void share(String nameApp, String imagePath) {
		  try
		  {
		      List<Intent> targetedShareIntents = new ArrayList<Intent>();
		      Intent share = new Intent(android.content.Intent.ACTION_SEND);
		      share.setType("image/jpeg");
		      List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
		      if (!resInfo.isEmpty()){
		          for (ResolveInfo info : resInfo) {
		              Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
		              targetedShare.setType("image/jpeg"); // put here your mime type
		              if (info.activityInfo.packageName.toLowerCase().contains(nameApp) || info.activityInfo.name.toLowerCase().contains(nameApp)) {
		                  targetedShare.putExtra(Intent.EXTRA_SUBJECT, "Sample Photo");
		               targetedShare.putExtra(Intent.EXTRA_TEXT,"This photo is created by App Name");
		                  targetedShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)) );
		                  targetedShare.setPackage(info.activityInfo.packageName);
		                  targetedShareIntents.add(targetedShare);
		              }
		          }
		          Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
		          chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
		          startActivity(chooserIntent);
		      }
		  }
		  catch(Exception e){
		      Log.v("VM","Exception while sending image on" + nameApp + " "+  e.getMessage());
		  }
		}
}
