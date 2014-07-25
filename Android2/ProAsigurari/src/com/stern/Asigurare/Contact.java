package com.stern.Asigurare;

import android.app.ActivityGroup;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.ClipboardManager;
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
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Contact extends ActivityGroup {

	
	int[] images = {R.drawable.contact_email,
			R.drawable.contact_vodafone,};
	ListView listView; 
	String[] labelsTop = {"sternvlad@gmail.com", "0736.997.235",};
	String[] labels = {"Email", "Vodafone"};

	public AppSettings appSett;

	
	@Override
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
		setContentView(R.layout.contact);
		appSett = AppSettings.get(this);
		listView = (ListView) findViewById (android.R.id.list);

		MainController.tvTitlu.setText(getString(R.string.i488));
		appSett.updateTitluGroup4(getString(R.string.i488));
		
		Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
		
		((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface.create(th,Typeface.BOLD));
		((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface.create(th,Typeface.BOLD));
		
		listView.setAdapter(new ImageAndTextAdapter(Contact.this,
				R.layout.element_lista_altele, null, null, null));
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0) {
					Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
					emailIntent.setType("plain/text");
					emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"sternvlad@gmail.com"});
					startActivity(emailIntent);
				}
				if (position == 1){
					try {
	                    copyToClipBoard("0753701451");
						errorDialog();
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		});
	}
	private class ImageAndTextAdapter extends SimpleCursorAdapter {
		private Context adContext;
		Typeface tf = Typeface.createFromAsset(getAssets(), "Arial.ttf");

		public int getCount() {
			return labels.length;
			
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
				v = inflater.inflate(R.layout.element_lista_altele, null);
			}
			if ((pos == 5 || pos == 6|| pos==0) && !YTOUtils.isTablet(getParent()))
			{
				
				((TextView) v.findViewById(R.id.text_listTop)).setTextSize(16);
				((TextView) v.findViewById(R.id.text_List)).setTextSize(14);
			}
			if ((pos == 8 || pos == 7) && !YTOUtils.isTablet(getParent()))
			{
				((TextView) v.findViewById(R.id.text_listTop)).setTextSize(14);
				((TextView) v.findViewById(R.id.text_List)).setTextSize(14);
			}
			((ImageView) v.findViewById(R.id.arrow)).setVisibility(View.GONE);
			((TextView) v.findViewById(R.id.text_List)).setText(labels[pos]);
			((TextView) v.findViewById(R.id.text_listTop)).setText(labelsTop[pos]);
			((ImageView) v.findViewById(R.id.left_img)).setImageResource(images[pos]);
			((TextView) v.findViewById(R.id.text_List)).setTypeface(Typeface.create(tf,Typeface.NORMAL));
			((TextView) v.findViewById(R.id.text_listTop)).setTypeface(Typeface.create(tf,Typeface.NORMAL));
			return v;
		}
	}

	
	public void errorDialog () 
	{ //eroare
	     final Dialog dialog=new Dialog(getParent());
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog_error);
	     TextView tvContinut,textTitlu;
	     //ImageView imgContinut;
	     textTitlu = (TextView) dialog.findViewById(R.id.text_titlu);
	     TextView textHeader;
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     textHeader = (TextView) dialog.findViewById(R.id.text_eroare_header);
	     textHeader.setVisibility(View.INVISIBLE);
	     textTitlu.setText("Numar copiat");
	     tvContinut = (TextView)dialog.findViewById (R.id.text_error);
	     Button btn_ok=(Button)dialog.findViewById(R.id.button_ok);
	     tvContinut.setText ("Numarul de telefon a fost copiat. Pentru a suna la acest numar, mergi in ecranul de apelare si “lipeste” numarul copiat.\nIti multumim!" );
	     
	     btn_ok.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	     }
	     });

	   dialog.show();
	}
	
	private void copyToClipBoard(String text) 
	{
		ClipboardManager ClipMan = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	    ClipMan.setText(text);

	}

	
}
