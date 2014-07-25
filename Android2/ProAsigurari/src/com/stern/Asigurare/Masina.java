package com.stern.Asigurare;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Masina extends TabActivity {
	public static ImageView tooltip,headerMasina;
	public static TextView tvTooltip,textHeader1,textHeader2,textHeader3;
	AppSettings sett;
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
		setContentView(R.layout.masina);
		tooltip = (ImageView) findViewById (R.id.tooltip_masina);
		tvTooltip = (TextView) findViewById (R.id.textTooltipM);
		headerMasina = (ImageView) findViewById(R.id.header_masinaimg);
		textHeader1 = (TextView) findViewById(R.id.text_header1);
		textHeader2 = (TextView) findViewById(R.id.text_header2);
		textHeader3 = (TextView) findViewById(R.id.text_header3);
		
        Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
		
		((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface.create(th,Typeface.BOLD));
		((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface.create(th,Typeface.BOLD));
		((TextView) findViewById(R.id.text_header3)).setTypeface(Typeface.create(th,Typeface.BOLD));
		
		AlerteMasina.dialog = new Dialog(getParent());
		
		   MainController.tvTitlu.setText (getString(R.string.i440));
		   sett = AppSettings.get(this);
		   if (MasinileMele.tipDate.equals("Asigurare")){sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());}
		   else {sett.updateTitluGroup1(MainController.tvTitlu.getText().toString());}
		
		 final TabHost tabHost = getTabHost();
		 
	        // Tab for InfoMasina
	        final TabSpec infoMasina = tabHost.newTabSpec("Info Autovehicul");
	        // setting Title and Icon for the Tab
	        infoMasina.setIndicator(getString(R.string.i273));
	        Intent infoMasinaIntent = new Intent(this, InfoMasina.class);
	        infoMasina.setContent(infoMasinaIntent);
	        
	        // Tab for alerte
	        final TabSpec alerteMasina = tabHost.newTabSpec("Alerte");
	        alerteMasina.setIndicator(getString(R.string.i274));
	        Intent alerteIntent = new Intent(this, AlerteMasina.class);
	        alerteMasina.setContent(alerteIntent);
	        
	   
	        
	        tabHost.addTab(infoMasina); //adaugare tab info 
	        tabHost.addTab(alerteMasina);
	        
	      //Design
	        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
			{
	        	tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selectat_dreapta_masina);
			}
	        tabHost.getTabWidget().setCurrentTab(1);
	        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.selectat_stanga_masina);

	        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
	        {
	            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
	            tv.setTextColor(Color.parseColor("#000000"));
	            if (YTOUtils.isTablet(getParent()))
	            	tv.setTextSize(25);
	        }
	        TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
	        tv.setTextColor(Color.parseColor("#ffffff"));
	        if (YTOUtils.isTablet(getParent()))
            	tv.setTextSize(25);
	        if (!YTOUtils.isTablet(getParent()))
		        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) 
		            tabHost.getTabWidget().getChildAt(i).getLayoutParams().height /=2;
	        else 
	        	for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) 
		            tabHost.getTabWidget().getChildAt(i).getLayoutParams().height *=0.75;
	     

			 tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

				 @Override
				 public void onTabChanged(String tabId) {
				 	// TODO Auto-generated method stub
				     for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
				     {
				     	tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selectat_dreapta_masina);
				     	TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
			            tv.setTextColor(Color.parseColor("#000000"));
				     }
				     tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.selectat_stanga_masina);
				        TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
				        tv.setTextColor(Color.parseColor("#ffffff"));
				 }
			       
			 });

	}
	@Override
	public void onResume()
	{
		super.onResume();
	}
	
	public static void changeHeader(int i,Context ctx)
	{
		if (i==1)
		{
			headerMasina.setImageResource(R.drawable.header_autovehicul_nou);
			textHeader1.setText(ctx.getString(R.string.i731));
			textHeader1.setTextColor(ctx.getResources().getColor(R.color.verde));
			textHeader2.setText(ctx.getString(R.string.i732));
			textHeader2.setTextColor(ctx.getResources().getColor(R.color.ColorTitlu));
			textHeader3.setText(ctx.getString(R.string.i733));
		}else if (i==2) {
			headerMasina.setImageResource(R.drawable.header_autovehicul_salvat);
			textHeader1.setText(ctx.getString(R.string.i734));
			textHeader1.setTextColor(ctx.getResources().getColor(R.color.ColorTitlu));
			textHeader2.setText(ctx.getString(R.string.i735));
			textHeader2.setTextColor(ctx.getResources().getColor(R.color.verde));
			textHeader3.setText(ctx.getString(R.string.i736));
		}else if (i==3) {
			headerMasina.setImageResource(R.drawable.header_autovehicul_alerte);
			textHeader1.setText(ctx.getString(R.string.i755));
			textHeader1.setTextColor(ctx.getResources().getColor(R.color.verde));
			textHeader2.setText(ctx.getString(R.string.i756));
			textHeader2.setTextColor(ctx.getResources().getColor(R.color.ColorTitlu));
			textHeader3.setText(ctx.getString(R.string.i757));
		}
	}

}
