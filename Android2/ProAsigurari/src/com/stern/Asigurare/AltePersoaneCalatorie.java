package com.stern.Asigurare;

import java.util.ArrayList;

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

public class AltePersoaneCalatorie extends ListActivity {

	Button btn_adauga;
	Boolean salveaza;
	AppSettings sett;
	Boolean varstaNeg = false;
	Boolean conditieVarstaChecked = false;
	String contUser = "";
	String contParola = ""; 
	String limba = "ro"; 
	String versiune = "";
	public static ArrayList<YTOPersoana>  calatori = new ArrayList <YTOPersoana> ();
	public static ArrayList<YTOPersoana> persoane = new ArrayList<YTOPersoana>(); 
	ImageView imzero;//se afiseaza in cazul in care nu exista nicio inregistrare
	TextView tvzero;//se afiseaza in cazul in care nu exista nicio inregistrare
	int nrPers;//numarul de persoane
	private SimpleCursorAdapter conAdapter;
	Button buttonOK;
	public static int positionId;
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
		setContentView(R.layout.alte_persoane_calatorie);
		imzero = (ImageView)findViewById (R.id.iv_zero_pers);
		tvzero = (TextView)findViewById (R.id.tv_zero_pers);
		sett = AppSettings.get(this);
		String text="";
		if (sett.getLanguage().equals("hu"))
			text = "Nincs elmentett személy.<br>Egy új személy adatainak bevezetéséhez nyomd meg a gombot<br> <font color=\"#f15a24\"> \"Személy  bevezetése\"</font> ";
		else if (sett.getLanguage().equals("en"))
			text = "There aren\'t persons saved in the list.<br> In order to add a new person,<br> press the button above<br> <font color=\"#f15a24\"> \"Choose traveller\"</font>";
		else text="Nu exista persoane fizice salvate.<br> Pentru a adauga o noua persoana,<br> apasa butonul de mai sus <br> <font color=\"#f15a24\">\"Adauga calator\"</font>";
		tvzero.setText(Html.fromHtml(text), BufferType.SPANNABLE);
		MainController.tvTitlu.setText (getString(R.string.i417));
		AsigurareCalatorie.i=1;
		sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());
		getPersoaneFizice();
		nrPers=persoane.size();
		if (nrPers!=0)
		{
			imzero.setVisibility(View.GONE);
			tvzero.setVisibility(View.GONE);
		conAdapter =(new ImageAndTextAdapter(AltePersoaneCalatorie.this,
				R.layout.meniu_lista_element, null, null, null));
		setListAdapter(conAdapter);
		}
		else{
			imzero.setVisibility(View.VISIBLE);
			tvzero.setVisibility(View.VISIBLE);
		}
		
		buttonOK = (Button) findViewById (R.id.button_ok);
		if (calatori!=null)
			if (calatori.size()!=0)buttonOK.setVisibility(View.VISIBLE);
			else buttonOK.setVisibility(View.GONE);
		else{
			buttonOK.setVisibility(View.GONE);
			AsigurareCalatorie.i=0;
		}
		
		
		buttonOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		btn_adauga = (Button)findViewById (R.id.adauga_persoana);
		btn_adauga.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				salveaza = false;
				Intent persoanaCalatorie = new Intent(getParent(), PersoanaCalatorie.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("PersoanaCalatorie", persoanaCalatorie);
				
			}
		});
		
		final ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				boolean isCalator = false;//daca e in lista de caltori e true
				 DespreCalator.calator = persoane.get(position);
				 for (int i=0;i<calatori.size();i++)
					 if (calatori.get(i)!=null && DespreCalator.calator!=null)
						if (calatori.get(i).idIntern.equals(DespreCalator.calator.idIntern)) 
						{
							calatori.remove(i);
							isCalator=true;
							DespreCalator.calator = null;
							break;
						}
				 if (isCalator==false)
				 {	 
					if (!conditieVarstaChecked && (persoane.get(position).getVarstaPersoana()>=65 || persoane.get(position).getVarstaPersoana()<=3)) varstaNeg = true;
					if (varstaNeg && calatori.size()>=1)
					{
						showPopupVarsta(position);
					}else{
					if (persoane.get(position).isValidForComputeCalatorie())
					{
					 calatori.add(DespreCalator.calator);
					 positionId=position;
					 salveaza = false;
					 Intent i = new Intent(AltePersoaneCalatorie.this, DespreCalator.class);
					 i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					 startActivity (i);
					}
					else {
						positionId = position;
						Intent persoanaView = new Intent(getParent(), PersoanaViewCalatorie.class);
						TabGroupActivity parentActivity = (TabGroupActivity)getParent();
						parentActivity.startChildActivity("PersoanaViewCalatorie", persoanaView);
					}
					}
				 }
				 else onResume();
			}
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					if (persoane.get(position).proprietar.equals("da")) return false;
					else deleteDialog(persoane.get(position).idIntern);
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
		AsigurareCalatorie.i=1;
		getPersoaneFizice();
		nrPers=persoane.size();
		if (calatori!=null)
			if (calatori.size()!=0)buttonOK.setVisibility(View.VISIBLE);
			else buttonOK.setVisibility(View.GONE);
		else buttonOK.setVisibility(View.GONE);
		
	if (nrPers!=0)
	{
		imzero.setVisibility(View.GONE);
		tvzero.setVisibility(View.GONE);
	conAdapter =(new ImageAndTextAdapter(AltePersoaneCalatorie.this,
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
				((ImageView) v.findViewById(R.id.image_right)).setVisibility(View.GONE);
				((TextView) v.findViewById(R.id.textListTopElem)).setText(persoane.get (pos).nume);
				((TextView) v.findViewById(R.id.textListTopElem)).setTypeface(Typeface.create(tf,Typeface.BOLD));
				if (!persoane.get (pos).judet.equals("")) ((TextView) v.findViewById(R.id.textListElem)).setText(persoane.get (pos).judet+","+persoane.get (pos).localitate);
				else ((TextView) v.findViewById(R.id.textListElem)).setText("");
				
				if(persoane.get(pos).proprietar.equals("da"))
				{
					if (persoane.get(pos).tipPersoana.equals("fizica")) ((ImageView) v.findViewById(R.id.imgListElem)).setImageResource(R.drawable.icon_foto_person_xs_profil);
					else ((ImageView) v.findViewById(R.id.imgListElem)).setImageResource(R.drawable.icon_foto_person_xs_profil_pj);
				}
																			  
				else 
				{
					if (persoane.get(pos).tipPersoana.equals("fizica")) ((ImageView) v.findViewById(R.id.imgListElem)).setImageResource(R.drawable.icon_foto_person);
					else ((ImageView) v.findViewById(R.id.imgListElem)).setImageResource(R.drawable.icon_foto_person_pj);
				}
				if (persoane.get (pos).isValidForComputeCalatorie())
					((ImageView) v.findViewById(R.id.image_right)).setVisibility(View.GONE);
				else ((ImageView) v.findViewById(R.id.image_right)).setVisibility(View.VISIBLE);   
				for (int i=0;i<calatori.size();i++)
					if (calatori.get(i).idIntern.equals(persoane.get(pos).idIntern)) 
					{
						((ImageView) v.findViewById(R.id.image_right)).setVisibility(View.VISIBLE);
						((ImageView) v.findViewById(R.id.image_right)).setImageResource(R.drawable.checked);
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
			    	dbConnector=new DatabaseConnector (AltePersoaneCalatorie.this);
			    	dbConnector.deleteObiectAsigurat(idIntern); 
			    	GetObiecte.getPersoane(dbConnector);
			    	new DeletePersoanaWebService(idIntern,contUser,contParola,limba,versiune).execute(null, null, null);
		            onResume();
		            if (AltePersoane.persoanaAsigurata.isDirty) if (AltePersoane.persoanaAsigurata.idIntern.equals(idIntern)) AltePersoane.persoanaAsigurata.isDirty=false;
				    dialog.dismiss();
		            }	
			     });
		   dialog.show();
		}

	void getPersoaneFizice()
	{
		persoane.clear();
//		DatabaseConnector dbConnector = new DatabaseConnector(this);
//		GetObiecte.getPersoane(dbConnector);
		for (YTOPersoana persoana : GetObiecte.persoane)
			if (persoana.tipPersoana.equals("fizica")) persoane.add(persoana);
	}
	  
	  private void showPopupVarsta(final int position)
	  {
		  final Dialog dialog=new Dialog(getParent());
		  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  dialog.setContentView(R.layout.dialog_varsta);
		  
		  Button btnDeAcord,btnContinua;
		  btnDeAcord = (Button) dialog.findViewById(R.id.button_da);
		  btnContinua = (Button) dialog.findViewById(R.id.button_nu);
		  
		  btnDeAcord.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				conditieVarstaChecked = true;
				varstaNeg = false;
				dialog.dismiss();
			}
		});
		  
		  btnContinua.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				conditieVarstaChecked = true;
				varstaNeg = false;
				if (persoane.get(position).isValidForComputeCalatorie())
				{
				 calatori.add(DespreCalator.calator);
				 positionId=position;
				 salveaza = false;
				 dialog.dismiss();
				 Intent i = new Intent(AltePersoaneCalatorie.this, DespreCalator.class);
				 i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				 startActivity (i);
				}
				else {
					positionId = position;
					
					Intent persoanaView = new Intent(getParent(), PersoanaViewCalatorie.class);
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("PersoanaViewCalatorie", persoanaView);
				}
			}
		});
		  dialog.show();
	  }
	
	 

}




