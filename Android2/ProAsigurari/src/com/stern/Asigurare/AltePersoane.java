package com.stern.Asigurare;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class AltePersoane extends ListActivity {

	public static String tipDate=""; //verifica daca am nevoie de datele selectate (daca intrarea vine din IncheieAsigurare.class
	Button btn_adauga;
	AppSettings sett;
	String contUser = "";
	String contParola = ""; 
	String limba = "ro"; 
	String versiune = "";
	public static YTOPersoana persoanaAsigurata = new YTOPersoana();//date persoana pentru IncheieAsigurare
	ImageView imzero;//se afiseaza in cazul in care nu exista nicio inregistrare
	TextView tvzero;//se afiseaza in cazul in care nu exista nicio inregistrare
	int nrPers;//numarul de persoane
	public static int positionId;//pozitia pe ale carei inregistrari vreau sa le vad
	public static String idIntern;
	private SimpleCursorAdapter conAdapter;
	DatabaseConnector dbConnector;
	
	
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
		setContentView(R.layout.alte_persoane);
		sett = AppSettings.get(getParent());
		imzero = (ImageView)findViewById (R.id.iv_zero_pers);
		tvzero = (TextView)findViewById (R.id.tv_zero_pers);
		String text= "";
		if (sett.getLanguage().equals("hu"))
			text = "Nincs elmentett személy.<br>Egy új személy adatainak bevezetéséhez nyomd meg a gombot <br> <font color=\"#f15a24\"> \"Személy  bevezetése\"</font>";
		else if (sett.getLanguage().equals("en"))
			text = "There aren\'t persons saved in the list.<br> In order to add a new person,<br> press the button above<br> <font color=\"#f15a24\"> \"Add insured person\"</font>";
		else 
			text="Nu exista persoane salvate.<br> Pentru a adauga o noua persoana,<br> apasa butonul de mai sus <br> <font color=\"#f15a24\">\"Adauga asigurat\"</font>";
		tvzero.setText(Html.fromHtml(text), BufferType.SPANNABLE);
		
		MainController.tvTitlu.setText (getString(R.string.i417));
		sett = AppSettings.get(this);
		if (tipDate.equals("Asigurare")){sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());}
		else sett.updateTitluGroup1(MainController.tvTitlu.getText().toString());
		
		
		if (tipDate.equals("Asigurare")) nrPers=GetObiecte.persoane.size();
		else nrPers=GetObiecte.persoaneNu.size();
		if (nrPers!=0)
		{
			imzero.setVisibility(View.GONE);
			tvzero.setVisibility(View.GONE);
		conAdapter =(new ImageAndTextAdapter(AltePersoane.this,
				R.layout.meniu_lista_element, null, null, null));
		setListAdapter(conAdapter);
		}
		else{
			imzero.setVisibility(View.VISIBLE);
			tvzero.setVisibility(View.VISIBLE);
		}
		
		btn_adauga = (Button)findViewById (R.id.adauga_persoana);
		btn_adauga.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent persoana = new Intent(getParent(), Persoana.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("Persoana", persoana);
				
			}
		});
		
		final ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
				positionId = position;
				if (tipDate.equals("Asigurare")) 
				{
					persoanaAsigurata=GetObiecte.persoane.get(position);
					if (persoanaAsigurata.isValidForCompute())
						onBackPressed();
					else {
						idIntern = persoanaAsigurata.idIntern;
						Intent persoanaView = new Intent(getParent(), PersoanaViewFromAsigurare.class);
						TabGroupActivity parentActivity = (TabGroupActivity)getParent();
						parentActivity.startChildActivity("PersoanaViewFromAsig", persoanaView);
					}
				}
				else {
					Intent persoanaView = new Intent(getParent(), PersoanaView.class);
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("PersoanaView", persoanaView);
				}
			}
			
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (tipDate.equals("Asigurare"))
				{ 
					if (GetObiecte.persoane.get(position).proprietar.equals("da")) return false;
					else deleteDialog(GetObiecte.persoane.get(position).idIntern);
				}
				else deleteDialog(GetObiecte.persoaneNu.get(position).idIntern);
				return true;
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
	
	public void onResume (){
		super.onResume();
		if (tipDate.equals("Asigurare")) 
		{
			nrPers=GetObiecte.persoane.size();
		}
		else nrPers=GetObiecte.persoaneNu.size();
		
	if (nrPers!=0)
	{
		imzero.setVisibility(View.GONE);
		tvzero.setVisibility(View.GONE);
	conAdapter =(new ImageAndTextAdapter(AltePersoane.this,
			R.layout.meniu_lista_element, null, null, null));
	setListAdapter(conAdapter);
	}
	else{
		imzero.setVisibility(View.VISIBLE);
		tvzero.setVisibility(View.VISIBLE);
	}
	}

		

	
	private class ImageAndTextAdapter extends SimpleCursorAdapter {
		private Context adContext;
		Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");

		public int getCount() {
			return nrPers;
		}

		public ImageAndTextAdapter(Context context, int layout, Cursor c,
				String[] from, int[] to) {
			super(context, layout, c, from, to);
			this.adContext = context;
		}
		
		public View getView(int pos, View inView, ViewGroup parent) {
			View v = inView;
			if (v == null) {
				LayoutInflater inflater = (LayoutInflater) adContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.element_lista_items, null);
			}
			
			if (tipDate.equals("Asigurare")){
				((TextView) v.findViewById(R.id.textListTopElem)).setText(GetObiecte.persoane.get (pos).nume);
				((TextView) v.findViewById(R.id.textListTopElem)).setTypeface(Typeface.create(tf,Typeface.BOLD));
				if (!GetObiecte.persoane.get (pos).judet.equals("")) ((TextView) v.findViewById(R.id.textListElem)).setText(GetObiecte.persoane.get (pos).judet+","+GetObiecte.persoane.get (pos).localitate);
				else ((TextView) v.findViewById(R.id.textListElem)).setText("");
				if(GetObiecte.persoane.get(pos).proprietar.equals("da"))
				{
					if (GetObiecte.persoane.get(pos).tipPersoana.equals("fizica")) ((ImageView) v.findViewById(R.id.imgListElem)).setImageResource(R.drawable.icon_foto_person_xs_profil);
					else ((ImageView) v.findViewById(R.id.imgListElem)).setImageResource(R.drawable.icon_foto_person_xs_profil_pj);
				}
																			  
				else 
				{
					if (GetObiecte.persoane.get(pos).tipPersoana.equals("fizica")) ((ImageView) v.findViewById(R.id.imgListElem)).setImageResource(R.drawable.icon_foto_person);
					else ((ImageView) v.findViewById(R.id.imgListElem)).setImageResource(R.drawable.icon_foto_person_pj);
					}
				if (GetObiecte.persoane.get (pos).isValidForCompute())
					((ImageView) v.findViewById(R.id.image_right)).setVisibility(View.GONE);
				else ((ImageView) v.findViewById(R.id.image_right)).setVisibility(View.VISIBLE);
			}
			else{
			((TextView) v.findViewById(R.id.textListTopElem)).setText(GetObiecte.persoaneNu.get (pos).nume);
			((TextView) v.findViewById(R.id.textListTopElem)).setTypeface(Typeface.create(tf,Typeface.BOLD));
			if (!GetObiecte.persoaneNu.get (pos).judet.equals("")) ((TextView) v.findViewById(R.id.textListElem)).setText(GetObiecte.persoaneNu.get (pos).judet+","+GetObiecte.persoaneNu.get (pos).localitate);
			else ((TextView) v.findViewById(R.id.textListElem)).setText("");
			if (GetObiecte.persoaneNu.get(pos).tipPersoana.equals("fizica")){((ImageView) v.findViewById(R.id.imgListElem)).setImageResource(R.drawable.icon_foto_person);}
			else {((ImageView) v.findViewById(R.id.imgListElem)).setImageResource(R.drawable.icon_foto_person_pj);
			if (GetObiecte.persoaneNu.get (pos).isValidForCompute())
				((ImageView) v.findViewById(R.id.image_right)).setVisibility(View.GONE);
			else ((ImageView) v.findViewById(R.id.image_right)).setVisibility(View.VISIBLE);}
			}
			
			return v;	
		}
	}
	
	public void deleteDialog (final String idIntern) { //functie de stergere din baza de date
		     final Dialog dialog=new Dialog(getParent());
		     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		     dialog.setContentView(R.layout.dialog);
		     TextView textHeader;
		     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
				
		     textHeader = (TextView) dialog.findViewById(R.id.text_vrei_sa_stergi);
		     textHeader.setTextColor(getResources().getColor(R.color.rosu_ter));
		     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
		     Button btn_nu=(Button)dialog.findViewById(R.id.button_nu);
		     btn_nu.setOnClickListener(new OnClickListener() {
		     @Override
		     public void onClick(View v) {
		        dialog.dismiss();
		     }
		     });

		     
		     Button btn_da=(Button)dialog.findViewById(R.id.button_da);
		     btn_da.setOnClickListener(new OnClickListener() {
			     @Override
			     public void onClick(View v) {
			    	dbConnector=new DatabaseConnector (AltePersoane.this);
			    	dbConnector.deleteObiectAsigurat(idIntern); 
			    	GetObiecte.getPersoane(dbConnector);
			    	if (persoanaAsigurata.isDirty) if (persoanaAsigurata.idIntern.equals(idIntern)) persoanaAsigurata.isDirty=false;
			    	new DeletePersoanaWebService(idIntern,contUser,contParola,limba,versiune).execute(null, null, null);
		            onResume();
				    dialog.dismiss();
		            }	
			     });
		   dialog.show();
		}


	

}




