package com.stern.Asigurare;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class CalculatieLocuinta extends Activity {
	
	public static ListView listView;
	static ArrayList<String> sort = new ArrayList<String>();
	TextView tvPret,tvBm,tvProduse;
	ImageView imageTitlu;
	LinearLayout linearButtons;
	int durata;
	public static int positionId;
	ImageView imvCompanie;
	AppSettings sett;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
//			Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//	    	int width = display.getWidth(); 
	    	Window window = getWindow(); 
//	    	if (YTOUtils.isTablet(this)) {	    	
//		    	window.setGravity(Gravity.CENTER); 
//		    	window.setLayout(320, 480);
//	    	}
	    	
	    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.tarife_rca);
	
	        sett = AppSettings.get(this);
	        linearButtons = (LinearLayout) findViewById (R.id.ll_butonae);
	        linearButtons.setVisibility(View.GONE);
	        
	        tvProduse = (TextView) findViewById(R.id.tv_tiltu_prod);
	        tvProduse.setVisibility(View.VISIBLE);
	        tvProduse.setText (getString(R.string.i464));
	        
//	        imageTitlu = (ImageView) findViewById (R.id.image_tarife_header);
	        ((RelativeLayout) findViewById(R.id.header_tarife)).setVisibility(View.VISIBLE);
//	        imageTitlu.setImageResource(R.drawable.tarife_locuinta_header);
	        MainController.tvTitlu.setText(getString(R.string.i463));
	        
			Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
			((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface.create(th,Typeface.BOLD));
			((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface.create(th,Typeface.BOLD));
			((TextView) findViewById(R.id.text_header3)).setTypeface(Typeface.create(th,Typeface.BOLD));

	        durata = sett.getRcaNrLuni();
	        
//	        ((View) findViewById(R.id.view1)).setBackgroundColor(getResources().getColor(R.color.albastru_locuinta));
//	        ((View) findViewById(R.id.view2)).setBackgroundColor(getResources().getColor(R.color.albastru_locuinta));
	        ((TextView) findViewById(R.id.text_header2)).setTextColor(getResources().getColor(R.color.albastru_locuinta));
	        ((TextView) findViewById(R.id.text_header3)).setText(getString(R.string.i790));
	        
	        if (sett.getLanguage().equals("hu"))
	        {
	        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.header_tarife_locuinta_hu);
	        } else if (sett.getLanguage().equals("en"))
	        {
	        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.header_tarife_locuinta_en);
	        }else 
	        {
	        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.header_tarife_locuinta);
	        }
	        
	        
	        listView = (ListView) findViewById(android.R.id.list);   
	        listView.setAdapter(new ImageAndTextAdapter(CalculatieLocuinta.this,
	        		R.layout.element_tarife_loc, null, null, null));

	        listView.setOnItemClickListener(new OnItemClickListener() {
	        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
			positionId=position;
			if (CallWebServiceLoc.oferta.get(position).Companie.equals("Platinum") && YTOUtils.isWeekend())
			{
				showDialogPlatinum();
			}else{
			Intent sumar = new Intent(getParent(), SumarComandaLocuinta.class);
			TabGroupActivity parentActivity = (TabGroupActivity)getParent();
			parentActivity.startChildActivity("SumarComandaLocuinta", sumar);
			}
		}
	});
}


	 private class ImageAndTextAdapter extends SimpleCursorAdapter {
		 private Context adContext;
		 Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
		 String text;
		 
		 public int getCount() 
		 {
			 return CallWebServiceLoc.oferta.size();
		 }

		 public ImageAndTextAdapter(Context context, int layout, Cursor c,String[] from, int[] to) {
			 super(context, layout, c, from, to);
			 this.adContext = context;
		 }

		 public View getView(int pos, View inView, ViewGroup parent) {
			 View v = inView;
			 if (v == null) {
				 LayoutInflater inflater = (LayoutInflater) adContext
				 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				 v = inflater.inflate(R.layout.element_tarife_loc, null);
			 }

			 String numeComp = CallWebServiceLoc.oferta.get(pos).Companie.toLowerCase();
			 int resID = getResources().getIdentifier(numeComp, "drawable", getPackageName());

			 ((ImageView) v.findViewById(R.id.img_companie)).setImageResource(resID);
			 
			 
			 
			 ((TextView) v.findViewById(R.id.tv_tip_asig)).setText(CallWebServiceLoc.oferta.get(pos).TipProdus);
			 ((TextView) v.findViewById(R.id.tv_tip_asig)).setTypeface(Typeface.create(tf,Typeface.BOLD));
			 if (!YTOUtils.isTablet(getParent())&& CallWebServiceLoc.oferta.get(pos).Prima<1000) 
				((TextView) v.findViewById(R.id.pret_loc)).setTextSize(18);
			 ((TextView) v.findViewById(R.id.pret_loc)).setText(CallWebServiceLoc.oferta.get(pos).Prima+" EUR");
			 
			 if (!CallWebServiceLoc.oferta.get(pos).Fransiza.equals("fara") && !CallWebServiceLoc.oferta.get(pos).Fransiza.equals("nu")){
				if (CallWebServiceLoc.oferta.get(pos).Fransiza.length()>10) 
					text = CallWebServiceLoc.oferta.get(pos).Fransiza.substring(0,10)+"...";
				else text = CallWebServiceLoc.oferta.get(pos).Fransiza;
			 ((TextView) v.findViewById(R.id.tvFransiza)).setText(text);
			 ((TextView) v.findViewById(R.id.tvFransiza)).setTextColor(Color.RED);}
			 if (!CallWebServiceLoc.oferta.get(pos).RiscApaConducta.equals("nu") && !CallWebServiceLoc.oferta.get(pos).RiscApaConducta.equals("0") && !CallWebServiceLoc.oferta.get(pos).RiscApaConducta.equals("fara"))
				 ((ImageView) v.findViewById(R.id.checkedAAC)).setImageResource(R.drawable.icon_check);
			 if (!CallWebServiceLoc.oferta.get(pos).RiscFurt.equals("nu") && !CallWebServiceLoc.oferta.get(pos).RiscFurt.equals("0") && !CallWebServiceLoc.oferta.get(pos).RiscFurt.equals("fara"))
				 ((ImageView) v.findViewById(R.id.checkedAF)).setImageResource(R.drawable.icon_check);
			 if (CallWebServiceLoc.oferta.get(pos).SARaspundere.equals("nu") || CallWebServiceLoc.oferta.get(pos).SARaspundere.equals("fara") || CallWebServiceLoc.oferta.get(pos).SARaspundere.equals("0")){
				 ((ImageView) v.findViewById(R.id.checkedRC)).setVisibility(View.VISIBLE);
				 ((TextView) v.findViewById(R.id.suma_rc)).setVisibility(View.GONE);
			 }else {
				 ((ImageView) v.findViewById(R.id.checkedRC)).setVisibility(View.GONE);
				 ((TextView) v.findViewById(R.id.suma_rc)).setVisibility(View.VISIBLE);
				 ((TextView) v.findViewById(R.id.suma_rc)).setText(CallWebServiceLoc.oferta.get(pos).SARaspundere + " EUR");
			 }
				 
			 return v;
		 }
	 }
	 
		@Override
		public void onResume()
		{
			super.onResume();
			sett.updateEventsUntilPrompt(sett.getEventsUntilPrompt()-1);

		}

	  
		public void showDialogPlatinum () { //functie pop up
		     final Dialog dialog=new Dialog(getParent());
		     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		     dialog.setContentView(R.layout.dialog_platinum);
//		     if (YTOUtils.isTablet(this))
//		    	 dialog.getWindow().setLayout(400, 580);
//		     else dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

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

			    	dialog.dismiss();
					Intent sumar = new Intent(getParent(), SumarComandaLocuinta.class);
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("SumarComandaLocuinta", sumar);
		            }	
			     });
		   dialog.show();
		}
	 


}
	