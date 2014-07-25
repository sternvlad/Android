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
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Locuinta extends TabActivity {
	public static ImageView tooltip,imv,headerLocuinta;
	public static TextView tvTooltip,textHeader1,textHeader2,textHeader3;
	public static LinearLayout top;
	AppSettings sett;
	public void onCreate(Bundle savedInstanceState) {
//		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//    	int width = display.getWidth(); 
    	Window window = getWindow(); 
//    	if (YTOUtils.isTablet(this)) {  	
//	    	window.setGravity(Gravity.CENTER); 
//	    	window.setLayout(320, 480);
//    	}
    	
    	 MainController.tvTitlu.setText (getString (R.string.i364));
 		sett = AppSettings.get(this);
 		if (LocuinteleMele.tipDate.equals("Asigurare")){sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());}
 		else {sett.updateTitluGroup1(MainController.tvTitlu.getText().toString());}
 		

		
		AlerteLocuinta.dialog = new Dialog(getParent());
    	
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locuinta);
		tooltip = (ImageView) findViewById (R.id.tooltip_locuinta);
		tvTooltip = (TextView) findViewById (R.id.textTooltip_loc);
		
        Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
		
		((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface.create(th,Typeface.BOLD));
		((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface.create(th,Typeface.BOLD));
		((TextView) findViewById(R.id.text_header3)).setTypeface(Typeface.create(th,Typeface.BOLD));
		
		headerLocuinta = (ImageView) findViewById(R.id.header_locuintaimg);
		textHeader1 = (TextView) findViewById(R.id.text_header1);
		textHeader2 = (TextView) findViewById(R.id.text_header2);
		textHeader3 = (TextView) findViewById(R.id.text_header3);
		 final TabHost tabHost = getTabHost();
		 

		 
	        // Tab for InfoLocuinta
	        final TabSpec infoLocuinta = tabHost.newTabSpec("Info Locuinta");
	        // setting Title and Icon for the Tab
	        infoLocuinta.setIndicator(getString (R.string.i367));
	        Intent infoLocuintaIntent = new Intent(this, InfoLocuinta.class);
	        infoLocuinta.setContent(infoLocuintaIntent);
	        
	        // Tab for alerte
	        final TabSpec alerteLocuinta = tabHost.newTabSpec("Alerte");
	        alerteLocuinta.setIndicator(getString (R.string.i370));
	        Intent alerteIntent = new Intent(this, AlerteLocuinta.class);
	        alerteLocuinta.setContent(alerteIntent);
	        
	   
	        
	        tabHost.addTab(infoLocuinta); //adaugare tab info 
	        tabHost.addTab(alerteLocuinta);
	        
	      //Design
	        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
			{
	        	tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selectat_dreapta_locuinta);
			}
	        tabHost.getTabWidget().setCurrentTab(1);
	        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.selectat_stanga_locuinta);

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
				     	tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selectat_dreapta_locuinta);
				     	TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
			            tv.setTextColor(Color.parseColor("#000000"));
				     }
				     tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.selectat_stanga_locuinta);
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
			headerLocuinta.setImageResource(R.drawable.header_locuinta_noua);
			textHeader1.setText(ctx.getString(R.string.i737));
			textHeader1.setTextColor(ctx.getResources().getColor(R.color.albastru_locuinta));
			textHeader2.setText(ctx.getString(R.string.i738));
			textHeader2.setTextColor(ctx.getResources().getColor(R.color.ColorTitlu));
			textHeader3.setText(ctx.getString(R.string.i739));
		}else if (i==2) {
			headerLocuinta.setImageResource(R.drawable.header_locuinta_salvata);
			textHeader1.setText(ctx.getString(R.string.i740));
			textHeader1.setTextColor(ctx.getResources().getColor(R.color.ColorTitlu));
			textHeader2.setText(ctx.getString(R.string.i741));
			textHeader2.setTextColor(ctx.getResources().getColor(R.color.albastru_locuinta));
			textHeader3.setText(ctx.getString(R.string.i742));
		}else if (i==3) {
			headerLocuinta.setImageResource(R.drawable.header_locuinta_alerte);
			textHeader1.setText(ctx.getString(R.string.i752));
			textHeader2.setTextColor(ctx.getResources().getColor(R.color.ColorTitlu));
			textHeader2.setText(ctx.getString(R.string.i753));
			textHeader1.setTextColor(ctx.getResources().getColor(R.color.albastru_locuinta));
			textHeader3.setText(ctx.getString(R.string.i754));
		}
	}
	
	

}