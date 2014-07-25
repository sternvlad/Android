package com.stern.Asigurare;

import android.app.ProgressDialog;
import android.app.TabActivity;
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

public class Persoana extends TabActivity {
	public static ImageView tooltipPersoana;
	public static TextView tvTooltipPersoana;
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
		setContentView(R.layout.persoana);
		
		MainController.tvTitlu.setText (getString(R.string.i443));
		sett = AppSettings.get(this);
		sett.updateTitluGroup1(MainController.tvTitlu.getText().toString());
		sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());
		
        Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
		
		((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface.create(th,Typeface.BOLD));
		((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface.create(th,Typeface.BOLD));
		((TextView) findViewById(R.id.text_header3)).setTypeface(Typeface.create(th,Typeface.BOLD));

		PersoanaJuridica.dlg = new ProgressDialog(getParent());
		tooltipPersoana = (ImageView)findViewById (R.id.tooltip_persoana);
		tvTooltipPersoana=(TextView)findViewById (R.id.textTooltipPersoana);
		
		 final TabHost tabHost = getTabHost();
		 
		 
	        // Tab for DespreMine
	        final TabSpec persoanaFizica = tabHost.newTabSpec("Persoana Fizica");
	        // setting Title and Icon for the Tab
	        persoanaFizica.setIndicator(getString(R.string.i422));
	        Intent persoanaFizicaIntent = new Intent(this, PersoanaFizica.class);
	        persoanaFizica.setContent(persoanaFizicaIntent);
	        
	        // Tab for FirmaMea
	        final TabSpec persoanaJuridica = tabHost.newTabSpec("Persoana Juridica");
	        persoanaJuridica.setIndicator(getString(R.string.i423));
	        Intent persoanaJuridicaIntent = new Intent(this, PersoanaJuridica.class);
	        persoanaJuridica.setContent(persoanaJuridicaIntent);
	        
	   

	        tabHost.addTab(persoanaFizica); //adaugare tab despreMine 
	        tabHost.addTab(persoanaJuridica);

	        //Design
	        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
			{
	        	tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.persoana_pj);
			}
	        tabHost.getTabWidget().setCurrentTab(1);
	        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.persoana_pf);

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
				     	tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.persoana_pj);
				     	TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
			            tv.setTextColor(Color.parseColor("#000000"));
				     }
				     tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.persoana_pf);
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
		

}
