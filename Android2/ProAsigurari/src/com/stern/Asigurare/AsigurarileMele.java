package com.stern.Asigurare;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AsigurarileMele extends ListActivity {

	ImageView imzero;//se afiseaza in cazul in care nu exista nicio inregistrare
	TextView tvzero;//se afiseaza in cazul in care nu exista nicio inregistrare
	int nrAsig;//numarul de asig
	AppSettings sett;
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
		setContentView(R.layout.asigurarile_mele);
		imzero = (ImageView)findViewById (R.id.iv_zero_asig);
		tvzero = (TextView)findViewById (R.id.tv_zero_asig);
		
        Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
		
		((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface.create(th,Typeface.BOLD));
		((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface.create(th,Typeface.BOLD));
		((TextView) findViewById(R.id.text_header3)).setTypeface(Typeface.create(th,Typeface.BOLD));
		
		MainController.tvTitlu.setText (getString(R.string.i433));
		sett = AppSettings.get(this);
		sett.updateTitluGroup1(MainController.tvTitlu.getText().toString());

		nrAsig=GetObiecte.asigurari.size();
		if (nrAsig!=0)
		{
			imzero.setVisibility(View.GONE);
			tvzero.setVisibility(View.GONE);
		conAdapter =(new ImageAndTextAdapter(AsigurarileMele.this,
				R.layout.element_lista_asigurari, null, null, null));
		setListAdapter(conAdapter);
		}
		else{
			imzero.setVisibility(View.VISIBLE);
			tvzero.setVisibility(View.VISIBLE);
		}
	}
	
		

	
	private class ImageAndTextAdapter extends SimpleCursorAdapter {
		private Context adContext;

		public int getCount() {
			return nrAsig;
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
				v = inflater.inflate(R.layout.element_lista_asigurari, null);
			}
			
			 String numeComp = GetObiecte.asigurari.get(pos).companie.toLowerCase();
			 int resID = getResources().getIdentifier(numeComp, "drawable", getPackageName());

			 ((ImageView) v.findViewById(R.id.img_companie)).setImageResource(resID);
			 ((TextView) v.findViewById(R.id.tip_asig)).setText(GetObiecte.asigurari.get (pos).tipAsigurare+","+GetObiecte.asigurari.get (pos).detaliiAsigurare);
			 ((TextView) v.findViewById(R.id.pret_asig)).setText(GetObiecte.asigurari.get (pos).prima+" "+GetObiecte.asigurari.get (pos).moneda);
			 ((TextView) v.findViewById(R.id.data_inceput)).setText(GetObiecte.asigurari.get (pos).dataInceput);
				
			return v;	
	}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
	}


}




