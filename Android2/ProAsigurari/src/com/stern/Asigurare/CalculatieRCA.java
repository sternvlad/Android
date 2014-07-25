package com.stern.Asigurare;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;


public class CalculatieRCA extends Activity {
	
	public static ListView listView;
	static ArrayList<String> sort = new ArrayList<String>();
	TextView tvPret,tvBm,tvTitlu;
	int durata;
	RelativeLayout cellB0,headerImg;
	TextView textB0;
	Button btn6Luni,btn12Luni,btnX;
	public static int positionId;
	ImageView imvCompanie;
	AppSettings sett;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	Window window = getWindow(); 
	    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.tarife_rca);
	
	        sett = AppSettings.get(this);
	        
	        MainController.tvTitlu.setText(getString(R.string.i448));
	        
	        durata = sett.getRcaNrLuni();
	        cellB0 = (RelativeLayout) findViewById(R.id.cellb0);
	        textB0 = (TextView) findViewById (R.id.text_b01);
	        headerImg = (RelativeLayout) findViewById(R.id.header_tarife);
	        btn6Luni = (Button) findViewById (R.id.btn_6luni);
	        btn12Luni = (Button) findViewById (R.id.btn_12luni);
	        btnX = (Button) findViewById(R.id.button_x);
			Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
			((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface.create(th,Typeface.BOLD));
			((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface.create(th,Typeface.BOLD));
			((TextView) findViewById(R.id.text_header3)).setTypeface(Typeface.create(th,Typeface.BOLD));
			
			if (sett.getLanguage().equals("hu"))
	        {
	        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.header_tarife_rca_hu);
	        } else if (sett.getLanguage().equals("en"))
	        {
	        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.header_tarife_rca_en);
	        }else 
	        {
	        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.header_tarife_rca);
	        }

	        String text="";
	        if (sett.getLanguage().equals("hu"))
	        	text = "";
	        else if (sett.getLanguage().equals("en"))
	        	text = "<font color=\"#f15a24\"><b>You've got BO Bonus. The fees could be higher!</b></font> Possible causes:";
	        else text = "<font color=\"#f15a24\"><b>Ai obtinut Bonus B0. Tarifele pot fi mai mari!</b></font> Cauze Posibile:";
			textB0.setText(Html.fromHtml(text), BufferType.SPANNABLE);
			if (YTOUtils.isTablet(getParent()))
				textB0.setTextSize(20);
	        
	        btn6Luni.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btn6Luni.setBackgroundResource(R.drawable.selectat_stanga_masina);
					btn12Luni.setBackgroundResource(R.drawable.selectat_dreapta_masina);
					btn6Luni.setTextColor(Color.WHITE);
					btn12Luni.setTextColor(Color.BLACK);
					durata = 6;
					sett.updateRcaNrLuni(6);
					listView.setAdapter(new ImageAndTextAdapter6l(CalculatieRCA.this,
			        		R.layout.element_lista_rca, null, null, null));
					
				}
			});
	        
	        btn12Luni.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btn12Luni.setBackgroundResource(R.drawable.selectat_stanga_masina);
					btn6Luni.setBackgroundResource(R.drawable.selectat_dreapta_masina);
					btn12Luni.setTextColor(Color.WHITE);
					btn6Luni.setTextColor(Color.BLACK);
					durata = 12;
					sett.updateRcaNrLuni(12);
					listView.setAdapter(new ImageAndTextAdapter12l(CalculatieRCA.this,
			        		R.layout.element_lista_rca, null, null, null));
				}
			});
	        
	        btnX.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					headerImg.setVisibility(View.VISIBLE);
		        	cellB0.setVisibility(View.GONE);
				}
			});
	        if (CallWebServiceRCA.clasa6l.get(0).equals("B0"))
	        {
	        	cellB0.setVisibility(View.VISIBLE);
	        	headerImg.setVisibility(View.GONE);
	        }else{
	        	headerImg.setVisibility(View.VISIBLE);
	        	cellB0.setVisibility(View.GONE);
	        }
	        
	        listView = (ListView) findViewById(android.R.id.list);   
	        
	        if (sett.getRcaNrLuni()==12) btn12Luni.performClick();
	        else btn6Luni.performClick();

	        listView.setOnItemClickListener(new OnItemClickListener() {
	        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
			positionId=position;
			SumarComandaRCA.durata=durata;
			Intent sumar = new Intent(getParent(), SumarComandaRCA.class);
			TabGroupActivity parentActivity = (TabGroupActivity)getParent();
			parentActivity.startChildActivity("SumarComanda", sumar);
		}
	});
}


	 private class ImageAndTextAdapter6l extends SimpleCursorAdapter {
		 private Context adContext;

		 public int getCount() 
		 {
			 return CallWebServiceRCA.lista6l.size();
		 }

		 public ImageAndTextAdapter6l(Context context, int layout, Cursor c,String[] from, int[] to) {
			 super(context, layout, c, from, to);
			 this.adContext = context;
		 }

		 public View getView(int pos, View inView, ViewGroup parent) {
			 View v = inView;
			 if (v == null) {
				 LayoutInflater inflater = (LayoutInflater) adContext
				 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				 v = inflater.inflate(R.layout.element_lista_rca, null);
			 }

			 String numeComp = CallWebServiceRCA.lista6l.get(pos).toLowerCase();
			 int resID = getResources().getIdentifier(numeComp, "drawable", getPackageName());

			 ((ImageView) v.findViewById(R.id.img_companie)).setImageResource(resID);
			 ((TextView) v.findViewById(R.id.pret_rca)).setText(CallWebServiceRCA.prima6l.get(pos)+" lei");
			 ((TextView) v.findViewById(R.id.clasa_bm)).setText("Clasa B/M: "+CallWebServiceRCA.clasa6l.get(pos));


			 return v;
		 }
	 }
	 
	 private class ImageAndTextAdapter12l extends SimpleCursorAdapter {
		 private Context adContext;

		 public int getCount() 
		 {
			 return CallWebServiceRCA.lista12l.size();
		 }

		 public ImageAndTextAdapter12l(Context context, int layout, Cursor c,String[] from, int[] to) {
			 super(context, layout, c, from, to);
			 this.adContext = context;
		 }

		 public View getView(int pos, View inView, ViewGroup parent) {
			 View v = inView;
			 if (v == null) {
				 LayoutInflater inflater = (LayoutInflater) adContext
				 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				 v = inflater.inflate(R.layout.element_lista_rca, null);
			 }

			 String numeComp = CallWebServiceRCA.lista12l.get(pos).toLowerCase();
			 int resID = getResources().getIdentifier(numeComp, "drawable", getPackageName());

			 ((ImageView) v.findViewById(R.id.img_companie)).setImageResource(resID);
			 ((TextView) v.findViewById(R.id.pret_rca)).setText(CallWebServiceRCA.prima12l.get(pos)+" lei");
			 ((TextView) v.findViewById(R.id.clasa_bm)).setText("Clasa B/M: "+CallWebServiceRCA.clasa12l.get(pos));


			 return v;
		 }
	 }
		@Override
		public void onResume()
		{
			super.onResume();
			sett.updateEventsUntilPrompt(sett.getEventsUntilPrompt()-1);
		}
		

}
	