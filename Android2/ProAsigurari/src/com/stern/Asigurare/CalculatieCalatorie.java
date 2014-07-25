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
import android.view.Display;
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
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class CalculatieCalatorie extends Activity {
	
	public static ListView listView;
	static ArrayList<String> sort = new ArrayList<String>();
	TextView tvPret,tvBm,tvProduse;
	ImageView imageTitlu;
	LinearLayout linearButtons;
	String suma;
	Button btn5000,btn10000,btn30000,btn50000;
	public static int positionId;
	ImageView imvCompanie;
	AppSettings sett;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
			Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    	int width = display.getWidth(); 
	    	Window window = getWindow(); 
//	    	if (YTOUtils.isTablet(this)) {	    	
//		    	window.setGravity(Gravity.CENTER); 
//		    	window.setLayout(320, 480);
//	    	}
	    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.tarife_calatorie);
	
	        sett = AppSettings.get(this);
	        MainController.tvTitlu.setText(getString(R.string.i463));
	        
			Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
			((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface.create(th,Typeface.BOLD));
			((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface.create(th,Typeface.BOLD));
			((TextView) findViewById(R.id.text_header3)).setTypeface(Typeface.create(th,Typeface.BOLD));

	        suma = sett.getSumaAsigurataCalatorie();
	        
	        btn5000=(Button) findViewById (R.id.btn_5000);
	        btn10000=(Button) findViewById (R.id.btn_10000);
	        btn30000=(Button) findViewById (R.id.btn_30000);
	        btn50000=(Button) findViewById (R.id.btn_50000);
	        
	        if (sett.getLanguage().equals("hu"))
	        {
	        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.header_tarife_calatorie_hu);
	        } else if (sett.getLanguage().equals("en"))
	        {
	        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.header_tarife_calatorie_en);
	        }else 
	        {
	        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.header_tarife_calatorie);
	        }
	        
	        btn5000.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sett.updateSumaAsigurataCalatorie("5.000-eur");
					btn5000.setBackgroundResource(R.drawable.selectat_tarife);
					btn10000.setBackgroundResource(R.drawable.neselectat_tarife);
					btn30000.setBackgroundResource(R.drawable.neselectat_tarife);
					btn50000.setBackgroundResource(R.drawable.neselectat_tarife);
					btn5000.setTextColor(Color.WHITE);
					btn10000.setTextColor(Color.BLACK);
					btn30000.setTextColor(Color.BLACK);
					btn50000.setTextColor(Color.BLACK);
					
					Intent loadingScreenCa = new Intent(getParent(), LoadingCalatorie.class);
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("LoadingScreenCa", loadingScreenCa);
				}
			});
	        btn10000.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sett.updateSumaAsigurataCalatorie("10.000-eur");
					btn10000.setBackgroundResource(R.drawable.selectat_tarife);
					btn5000.setBackgroundResource(R.drawable.neselectat_tarife);
					btn30000.setBackgroundResource(R.drawable.neselectat_tarife);
					btn50000.setBackgroundResource(R.drawable.neselectat_tarife);
					btn10000.setTextColor(Color.WHITE);
					btn5000.setTextColor(Color.BLACK);
					btn30000.setTextColor(Color.BLACK);
					btn50000.setTextColor(Color.BLACK);
					
					Intent loadingScreenCa = new Intent(getParent(), LoadingCalatorie.class);
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("LoadingScreenCa", loadingScreenCa);
					
				}
			});
	        
	        
	        btn30000.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sett.updateSumaAsigurataCalatorie("30.000-eur");
					btn30000.setBackgroundResource(R.drawable.selectat_tarife);
					btn10000.setBackgroundResource(R.drawable.neselectat_tarife);
					btn5000.setBackgroundResource(R.drawable.neselectat_tarife);
					btn50000.setBackgroundResource(R.drawable.neselectat_tarife);
					btn30000.setTextColor(Color.WHITE);
					btn10000.setTextColor(Color.BLACK);
					btn5000.setTextColor(Color.BLACK);
					btn50000.setTextColor(Color.BLACK);
					
					Intent loadingScreenCa = new Intent(getParent(), LoadingCalatorie.class);
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("LoadingScreenCa", loadingScreenCa);
					
				}
			});
	        
	        
	        btn50000.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sett.updateSumaAsigurataCalatorie("50.000-eur");
					btn50000.setBackgroundResource(R.drawable.selectat_tarife);
					btn10000.setBackgroundResource(R.drawable.neselectat_tarife);
					btn30000.setBackgroundResource(R.drawable.neselectat_tarife);
					btn5000.setBackgroundResource(R.drawable.neselectat_tarife);
					btn50000.setTextColor(Color.WHITE);
					btn10000.setTextColor(Color.BLACK);
					btn30000.setTextColor(Color.BLACK);
					btn5000.setTextColor(Color.BLACK);
					
					Intent loadingScreenCa = new Intent(getParent(), LoadingCalatorie.class);
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("LoadingScreenCa", loadingScreenCa);
					
				}
			});
	        
	        if (sett.getSumaAsigurataCalatorie().equals("5.000-eur")){
				btn5000.setBackgroundResource(R.drawable.selectat_tarife);
				btn10000.setBackgroundResource(R.drawable.neselectat_tarife);
				btn30000.setBackgroundResource(R.drawable.neselectat_tarife);
				btn50000.setBackgroundResource(R.drawable.neselectat_tarife);
				btn5000.setTextColor(Color.WHITE);
				btn10000.setTextColor(Color.BLACK);
				btn30000.setTextColor(Color.BLACK);
				btn50000.setTextColor(Color.BLACK);
			}
	        else if (sett.getSumaAsigurataCalatorie().equals("10.000-eur")){
				btn10000.setBackgroundResource(R.drawable.selectat_tarife);
				btn5000.setBackgroundResource(R.drawable.neselectat_tarife);
				btn30000.setBackgroundResource(R.drawable.neselectat_tarife);
				btn50000.setBackgroundResource(R.drawable.neselectat_tarife);
				btn10000.setTextColor(Color.WHITE);
				btn5000.setTextColor(Color.BLACK);
				btn30000.setTextColor(Color.BLACK);
				btn50000.setTextColor(Color.BLACK);
	        }
	        else if (sett.getSumaAsigurataCalatorie().equals("30.000-eur")){
	        	btn30000.setBackgroundResource(R.drawable.selectat_tarife);
				btn10000.setBackgroundResource(R.drawable.neselectat_tarife);
				btn5000.setBackgroundResource(R.drawable.neselectat_tarife);
				btn50000.setBackgroundResource(R.drawable.neselectat_tarife);
				btn30000.setTextColor(Color.WHITE);
				btn10000.setTextColor(Color.BLACK);
				btn5000.setTextColor(Color.BLACK);
				btn50000.setTextColor(Color.BLACK);
	        }
	        else if (sett.getSumaAsigurataCalatorie().equals("50.000-eur")){
				btn50000.setBackgroundResource(R.drawable.selectat_tarife);
				btn10000.setBackgroundResource(R.drawable.neselectat_tarife);
				btn30000.setBackgroundResource(R.drawable.neselectat_tarife);
				btn5000.setBackgroundResource(R.drawable.neselectat_tarife);
				btn50000.setTextColor(Color.WHITE);
				btn10000.setTextColor(Color.BLACK);
				btn30000.setTextColor(Color.BLACK);
				btn5000.setTextColor(Color.BLACK);
	        }
	        
	        tvProduse = (TextView) findViewById(R.id.tv_tiltu_prod);
	        tvProduse.setText((AltePersoaneCalatorie.calatori.size()==1? "1 "+ getString(R.string.i416) + " , " : AltePersoaneCalatorie.calatori.size() +getString(R.string.i415) + " , ") + sett.getNrZileCalatorie()+getString(R.string.i468)+" , " + "in " +sett.getTaraDestinatie());
	        
	        listView = (ListView) findViewById(android.R.id.list);   
	        if (LoadingCalatorie.oferta !=null && LoadingCalatorie.oferta.size()>1)
	        {
	        	if (LoadingCalatorie.oferta.get(0).getReducere() && LoadingCalatorie.oferta.get(0).getIsRedusByVodafone()){
			        listView.setAdapter(new ImageAndTextAdapter(CalculatieCalatorie.this,
			        		R.layout.element_lista_calatorie, null, null, null));}
	        	else {
	        		listView.setAdapter(new ImageAndTextAdapter2
	        			(CalculatieCalatorie.this, 
	        					R.layout.element_lista_calatorie_noreducere, null, null, null));
	        	}
	        }
	        listView.setOnItemClickListener(new OnItemClickListener() {
	        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
			positionId=position;
			if (LoadingCalatorie.oferta.get(position).getCompanie().equals("Platinum") && YTOUtils.isWeekend())
			{
				showDialogPlatinum();
			}else{
			Intent sumarCom = new Intent(getParent(), SumarComandaCalatorie.class);
			TabGroupActivity parentActivity = (TabGroupActivity)getParent();
			parentActivity.startChildActivity("SumarComandaCalatorie", sumarCom);
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
			 return LoadingCalatorie.oferta.size();
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
				 v = inflater.inflate(R.layout.element_lista_calatorie, null);
			 }

			 String numeComp = LoadingCalatorie.oferta.get(pos).getCompanie().toLowerCase();

			 int resID = getResources().getIdentifier(numeComp, "drawable", getPackageName());

			 ((ImageView) v.findViewById(R.id.img_companie)).setImageResource(resID);
			 ((TextView) v.findViewById(R.id.tv_tip_asig)).setText(LoadingCalatorie.oferta.get(pos).getTipProdus());
			 ((TextView) v.findViewById(R.id.tv_suma_asig)).setText("SA : " + LoadingCalatorie.oferta.get(pos).getSumaAsigurata());
			 ((TextView) v.findViewById(R.id.tv_tip_asig)).setTypeface(Typeface.create(tf,Typeface.BOLD));
			 if (!YTOUtils.isTablet(getParent())&& LoadingCalatorie.oferta.get(pos).getPrima()<100) 
				 ((TextView) v.findViewById(R.id.pret_loc)).setTextSize(18);
			 ((TextView) v.findViewById(R.id.pret_loc)).setText(LoadingCalatorie.oferta.get(pos).getPrima()+" "+LoadingCalatorie.oferta.get(pos).getMoneda());
			 
			 TextView textPretIntreg = (TextView) v.findViewById(R.id.pret_intreg);
			 TextView textPretLoc = (TextView) v.findViewById(R.id.pret_loc);
			 TextView textPretClient = (TextView) v.findViewById(R.id.pret_client);
			 TextView textPretLocClient = (TextView) v.findViewById(R.id.pret_loc_client);
			 //ImageView logoVdf = (ImageView) v.findViewById(R.id.vodafone_text);
			 
			 if (LoadingCalatorie.oferta.get(pos).getReducere()) ((TextView) v.findViewById(R.id.pret_loc_client)).setText(LoadingCalatorie.oferta.get(pos).getPrimaReducere()+" "+LoadingCalatorie.oferta.get(pos).getMoneda());
			 else{
				 ((TextView) v.findViewById(R.id.pret_loc_client)).setText(LoadingCalatorie.oferta.get(pos).getPrima()+" "+LoadingCalatorie.oferta.get(pos).getMoneda());
				 LoadingCalatorie.oferta.get(pos).setPrimaReducere(LoadingCalatorie.oferta.get(pos).getPrima());
			 }
			 if (sett.getOperaor().equals("Vodafone"))
			 {
				 textPretIntreg.setTextColor(Color.parseColor("#a2a2a2"));
				 textPretLoc.setTextColor(Color.parseColor("#a2a2a2"));
				 textPretClient.setTextColor(Color.BLACK);
				 textPretLocClient.setTextColor(Color.parseColor("#359742"));
				 textPretLocClient.setTypeface(tf, Typeface.BOLD);
				 ((LinearLayout) v.findViewById(R.id.relativeLayout1)).setBackgroundResource(R.drawable.layout1);
				 if (YTOUtils.isTablet(getParent()))
						 ((LinearLayout) v.findViewById(R.id.relativeLayout3)).setBackgroundResource(R.drawable.layout3);
				 else ((LinearLayout) v.findViewById(R.id.relativeLayout3)).setBackgroundResource(R.drawable.layout2);
				// logoVdf.setImageResource(R.drawable.vodafone_text);
			 }else{
				 textPretIntreg.setTextColor(Color.BLACK);
				 textPretLoc.setTextColor(Color.parseColor("#359742"));
				 textPretLoc.setTypeface(tf, Typeface.BOLD);
				 textPretClient.setTextColor(Color.parseColor("#a2a2a2"));
				 textPretLocClient.setTextColor(Color.parseColor("#a2a2a2"));
				 if (YTOUtils.isTablet(getParent()))
					 ((LinearLayout) v.findViewById(R.id.relativeLayout3)).setBackgroundResource(R.drawable.layout3);
			 else ((LinearLayout) v.findViewById(R.id.relativeLayout3)).setBackgroundResource(R.drawable.layout2);
				 ((LinearLayout) v.findViewById(R.id.relativeLayout3)).setBackgroundResource(R.drawable.layout1);
			//	 logoVdf.setImageResource(R.drawable.vodafone_text_fade);
			 }
			 
			 if (!LoadingCalatorie.oferta.get(pos).getFransiza().equals("fara") && !LoadingCalatorie.oferta.get(pos).getFransiza().equals("nu")){
				if (LoadingCalatorie.oferta.get(pos).getFransiza().length()>8) 
					text = LoadingCalatorie.oferta.get(pos).getFransiza().substring(0,7)+"...";
				else text = LoadingCalatorie.oferta.get(pos).getFransiza();
			 ((TextView) v.findViewById(R.id.tvFransiza)).setText(text);
			 ((TextView) v.findViewById(R.id.tvFransiza)).setTextColor(Color.RED);}
			 if (!LoadingCalatorie.oferta.get(pos).getSaBagaje().equals("nu") && !LoadingCalatorie.oferta.get(pos).getSaBagaje().equals("0") && !LoadingCalatorie.oferta.get(pos).getSaBagaje().equals("fara"))
				 ((ImageView) v.findViewById(R.id.checkedSAbagaje)).setImageResource(R.drawable.icon_check);
			 if (!LoadingCalatorie.oferta.get(pos).getSaEei().equals("nu") && !LoadingCalatorie.oferta.get(pos).getSaEei().equals("0") && !LoadingCalatorie.oferta.get(pos).getSaEei().equals("fara"))
				 ((ImageView) v.findViewById(R.id.checkedSaelectronice)).setImageResource(R.drawable.icon_check);
			 if (!LoadingCalatorie.oferta.get(pos).getAcoperireSport().equals("nu") && !LoadingCalatorie.oferta.get(pos).getAcoperireSport().equals("fara") && !LoadingCalatorie.oferta.get(pos).getAcoperireSport().equals("0"))
				 ((ImageView) v.findViewById(R.id.checkedAcoperiresport)).setImageResource(R.drawable.icon_check);
			 
			 
			 return v;
		 }
	 }
	 
	 private class ImageAndTextAdapter2 extends SimpleCursorAdapter {
		 private Context adContext;
		 Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
		 String text;
		 
		 public int getCount() 
		 {
			 return LoadingCalatorie.oferta.size();
		 }

		 public ImageAndTextAdapter2(Context context, int layout, Cursor c,String[] from, int[] to) {
			 super(context, layout, c, from, to);
			 this.adContext = context;
		 }

		 public View getView(int pos, View inView, ViewGroup parent) {
			 View v = inView;
			 if (v == null) {
				 LayoutInflater inflater = (LayoutInflater) adContext
				 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				 v = inflater.inflate(R.layout.element_lista_calatorie_noreducere, null);
			 }
			 View viewPretTaiat = (View) v.findViewById(R.id.view_pret_taiat);
			 String numeComp = LoadingCalatorie.oferta.get(pos).getCompanie().toLowerCase();
			 int resID = getResources().getIdentifier(numeComp, "drawable", getPackageName());
			 TextView pretTaiat = (TextView) v.findViewById(R.id.tv_pret_taiat);
			 ((ImageView) v.findViewById(R.id.img_companie)).setImageResource(resID);
			 ((TextView) v.findViewById(R.id.tv_tip_asig)).setText(LoadingCalatorie.oferta.get(pos).getTipProdus());
			 ((TextView) v.findViewById(R.id.tv_tip_asig)).setTypeface(Typeface.create(tf,Typeface.BOLD));
			 if (LoadingCalatorie.oferta.get(pos).getPrima()<1000) ((TextView) v.findViewById(R.id.pret_loc)).setTextSize(18);
			 if (YTOUtils.isTablet(getParent())){
			 		((TextView) v.findViewById(R.id.pret_loc)).setTextSize(35);
			 		pretTaiat.setTextSize(27);
			 }
			 else{ 
				 ((TextView) v.findViewById(R.id.pret_loc)).setTextSize(16);
				 pretTaiat.setTextSize(12);
			 }
			 if (!LoadingCalatorie.oferta.get(pos).getReducere()){
				 viewPretTaiat.setVisibility(View.GONE);
			 	pretTaiat.setVisibility(View.GONE);
			 	((TextView) v.findViewById(R.id.pret_loc)).setText(LoadingCalatorie.oferta.get(pos).getPrima()+" "+LoadingCalatorie.oferta.get(pos).getMoneda());
			 }else{
				 viewPretTaiat.setVisibility(View.VISIBLE);
				 	pretTaiat.setVisibility(View.VISIBLE);
				 	pretTaiat.setText(LoadingCalatorie.oferta.get(pos).getPrima()+" "+LoadingCalatorie.oferta.get(pos).getMoneda());
				 	((TextView) v.findViewById(R.id.pret_loc)).setText(LoadingCalatorie.oferta.get(pos).getPrimaReducere()+" "+LoadingCalatorie.oferta.get(pos).getMoneda());
			 }
			 if (!LoadingCalatorie.oferta.get(pos).getFransiza().equals("fara") && !LoadingCalatorie.oferta.get(pos).getFransiza().equals("nu")){
				if (LoadingCalatorie.oferta.get(pos).getFransiza().length()>8) 
					text = LoadingCalatorie.oferta.get(pos).getFransiza().substring(0,7)+"...";
				else text = LoadingCalatorie.oferta.get(pos).getFransiza();
			 ((TextView) v.findViewById(R.id.tvFransiza)).setText(text);
			 ((TextView) v.findViewById(R.id.tvFransiza)).setTextColor(Color.RED);}
			 if (!LoadingCalatorie.oferta.get(pos).getSaBagaje().equals("nu") && !LoadingCalatorie.oferta.get(pos).getSaBagaje().equals("0") && !LoadingCalatorie.oferta.get(pos).getSaBagaje().equals("fara"))
				 ((ImageView) v.findViewById(R.id.checkedSAbagaje)).setImageResource(R.drawable.icon_check);
			 if (!LoadingCalatorie.oferta.get(pos).getSaEei().equals("nu") && !LoadingCalatorie.oferta.get(pos).getSaEei().equals("0") && !LoadingCalatorie.oferta.get(pos).getSaEei().equals("fara"))
				 ((ImageView) v.findViewById(R.id.checkedSaelectronice)).setImageResource(R.drawable.icon_check);
			 if (!LoadingCalatorie.oferta.get(pos).getAcoperireSport().equals("nu") && !LoadingCalatorie.oferta.get(pos).getAcoperireSport().equals("fara") && !LoadingCalatorie.oferta.get(pos).getAcoperireSport().equals("0"))
				 ((ImageView) v.findViewById(R.id.checkedAcoperiresport)).setImageResource(R.drawable.icon_check);
			 return v;
		 }
	 }
	 
		@Override
		public void onResume()
		{
			super.onResume();
			sett.updateEventsUntilPrompt(sett.getEventsUntilPrompt()-1);
			if (LoadingCalatorie.oferta !=null && LoadingCalatorie.oferta.size()>1)
	        {
	        	if (LoadingCalatorie.oferta.get(0).getReducere() && LoadingCalatorie.oferta.get(0).getIsRedusByVodafone()){
			        listView.setAdapter(new ImageAndTextAdapter(CalculatieCalatorie.this,
			        		R.layout.element_lista_calatorie, null, null, null));}
	        	else {
	        		listView.setAdapter(new ImageAndTextAdapter2
	        			(CalculatieCalatorie.this, 
	        					R.layout.element_lista_calatorie_noreducere, null, null, null));
	        	}
	        }
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
			    	Intent sumarCom = new Intent(getParent(), SumarComandaCalatorie.class);
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					parentActivity.startChildActivity("SumarComandaCalatorie", sumarCom);
		            }	
			     });
		   dialog.show();
		}
	 


}
	