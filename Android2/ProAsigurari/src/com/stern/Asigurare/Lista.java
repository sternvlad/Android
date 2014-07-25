package com.stern.Asigurare;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Lista extends ListActivity {

	public static ListView listView;
	static ArrayList<String> sort = new ArrayList<String>();
	EditText edt;
	AppSettings set;
	TextView title_pag,textCodCaen;
	ArrayList <String> items;
	public static String date="",date1,date2;
	public static String tipDate="";
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
//			Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//	    	int width = display.getWidth(); 
	    	Window window = getWindow(); 
//	    	if (YTOUtils.isTablet(this)) {	    	
//		    	window.setGravity(Gravity.CENTER); 
//		    	window.setLayout(320, 480);
//	    	}
	    	set = AppSettings.get(this);
	    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.list_items);
	        
	        edt = (EditText) findViewById(R.id.edit_search);
	        listView = (ListView) findViewById(android.R.id.list);     
	        title_pag = (TextView) findViewById (R.id.titlu_pagina);
	        edt.setVisibility(View.VISIBLE);
	        
	        edt.addTextChangedListener(new TextWatcher() {
	        	 
	            @Override
	            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	                // When user changed the Text
	            	 
	                int textlength = edt.getText().length();
	               	sort.clear();


	                for (int i = 0; i < items.size(); i++)
	                {
	                 if (textlength <= items.get(i).length())
	                 {
	                  if (edt.getText().toString().equalsIgnoreCase((String) items.get(i).subSequence(0, textlength)))
	                  {
	                   sort.add(items.get(i));
	                  }
	                 }
	                }
	                if (tipDate.equals("marci_auto"))
	                	listView.setAdapter(new CarsAndLogo(Lista.this,
	    	    				R.layout.element_lista_items, null, null, null,sort));
	                else listView.setAdapter(new ArrayAdapter<String>(Lista.this, android.R.layout.simple_list_item_1, sort));
	            }
	 
	            @Override
	            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                    int arg3) {
	                // TODO Auto-generated method stub
	 
	            }
	 
	            @Override
	            public void afterTextChanged(Editable arg0) {

	            }
	        });
	 

	        listView = (ListView) findViewById(android.R.id.list);    
	        if (tipDate.equals("judete")){
	        	title_pag.setText(getString(R.string.i304));
	        	items = new ArrayList <String> ();
	        	items=DatabaseConnector.numeJud;
	        	listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));}
	        else if (tipDate.equals("detalii")) 
	        {
	        	edt.setVisibility(View.GONE);
	        	title_pag.setText(getString(R.string.i554));
	        	items=new ArrayList <String>();
	        	items.add(getString(R.string.i555));
	        	items.add(getString(R.string.i556));
	        	items.add(getString(R.string.i557));
	        	items.add(getString(R.string.i558));
	        	items.add(getString(R.string.i559));
	        	items.add(getString(R.string.i560));
	        	items.add(getString(R.string.i561));
	        	items.add (getString(R.string.i562));
	        	items.add(getString(R.string.i563));
	        	Button btnOk = (Button)findViewById (R.id.ok_button_lista);
	        	btnOk.setVisibility(View.VISIBLE);
	        	btnOk.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						onBackPressed();
					}
				});
	    		listView.setAdapter(new ImageAndTextAdapter(Lista.this,
	    				R.layout.element_lista_detalii, null, null, null,items));
	        }
	        else if (tipDate.equals("marci_auto"))
	        {
	        	title_pag.setText (getString (R.string.i276));
	        	items = new ArrayList <String> ();
	        	items = DatabaseConnector.marcaAuto;
	        	listView.setAdapter(new CarsAndLogo(Lista.this,
	    				R.layout.element_lista_items, null, null, null,items));
	        }
	        else if (tipDate.equals("cod_caen"))
	        {
	        	edt.setVisibility(View.GONE);
	        	items = new ArrayList <String> ();
	        	items.clear();
	        	title_pag.setText (getString (R.string.i164));
	    		listView.setAdapter(new ImageAndTextAdapter(Lista.this,
	    				R.layout.element_lista_detalii, null, null, null,null));
	        }
	        else if (tipDate.equals("tari"))
	        {
	        	title_pag.setText ("Tara Destinatie");
	        	items  = new ArrayList<String>(Arrays.asList("Turcia", "Bulgaria", "Italia", "Spania", "Franta", "Austria", "Cehia", "Ungaria", 
	        			"Olanda", "Aland-Islands",  "Albania", "Andorra", "Belarus", "Belgia", "Bosnia-Herzegovina", 
	        			"Croatia", "Danemarca", "Elvetia", "Estonia", "Finlanda", "Germania", "Gibraltar", "Grecia", 
	        			"Insulele-Faroe", "Irlanda", "Islanda", "Israel", "Letonia", "Liechtenstein", "Lituania", 
	        			"Luxembourg", "Macedonia", "Malta", "Marea-Britanie", "Moldova", "Monaco", "Norvegia", "Polonia",
	        			"Portugalia", "Romania", "Rusia", "San-Marino", "Serbia-Muntenegru", "Slovacia", "Slovenia", "SUA", 
	        			"SUA-Minor-Outlying-Islands", "Suedia", "Svalbard-Jan-Mayen", "Ucraina", "Vatican"));
	        	listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
	        }
	        
	       }
	 
	 public void onBackPressed ()
	 {
		 if (!tipDate.equals("judete") && !tipDate.equals("marci_auto") && !tipDate.equals("detalii") && !tipDate.equals("cod_caen") && !tipDate.equals("tari")){
			 items=DatabaseConnector.numeJud;
			 title_pag.setText("Judete");
			 listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
			 tipDate="judete";
		 }
		 else if (tipDate.equals("tari"))
		 {
			 date=set.getTaraDestinatie();
			 finish();
		 }
		 else finish();
	 }
	 
		@Override
		public void onResume()
		{
			super.onResume();
		}

			
	    

	    
	    @Override 
	    public void onListItemClick(ListView l, View v, int position, long id) {    
	    	if (tipDate.equals ("judete")){
	    		date1=l.getItemAtPosition(position).toString();
	    		int id1=1;
	    		for (int i=0; i<DatabaseConnector.idJud.size(); i++){
	    			if (DatabaseConnector.numeJud.get(i).equals(date1)) {
	    				id1=DatabaseConnector.idJud.get(i);
	    			}
	    		}
	    		DatabaseConnector dbConnector=new DatabaseConnector (this);
	    		dbConnector.getLocalitatiById(id1);
	    		tipDate="localitati";
	    		items = new ArrayList <String> ();
	    		items=DatabaseConnector.localitati;
	    		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
	    		title_pag.setText(getString(R.string.i305) +" "+date1);
	    		date="";
	            edt.setText("");
	    	}
	    	else if (tipDate.equals("marci_auto")){
	    		if (edt.getText().toString().equals(""))
	    			date=items.get(position);
	    		else date = sort.get(position);
	    		finish();
	    	}
	    	else if (tipDate.equals("detalii"))
	    	{
	    		if (InfoLocuinta.detalii_loc[position]==0)
	    			InfoLocuinta.detalii_loc[position]=1;
	    		else InfoLocuinta.detalii_loc[position]=0;
	    		listView.setAdapter(new ImageAndTextAdapter(Lista.this,
	    				R.layout.element_lista_detalii, null, null, null,items));
	    	}
	    	else if (tipDate.equals("cod_caen"))
	    	{
	    		date=DatabaseConnector.codCaen.get(position).toString();
	    		finish();
	    	}
	    	else if (tipDate.equals("tari"))
	    	{
	    		date=l.getItemAtPosition(position).toString();
	    		finish();
	    	}
	    	else {
	    		date2=date1+","+l.getItemAtPosition(position).toString();
	    		date=date2;
	    		finish();
	    	}
		   }
	    
		private class ImageAndTextAdapter extends SimpleCursorAdapter {
			private Context adContext;
			private ArrayList<String> lista;

			public int getCount() {
				int nr=0;
				if (tipDate.equals("detalii")) 
				{	
					nr = items.size();
				}
				else if (tipDate.equals("cod_caen"))
				{
					nr =  DatabaseConnector.codCaen.size();
				}
				return nr;
			}

			public ImageAndTextAdapter(Context context, int layout, Cursor c,
					String[] from, int[] to,ArrayList<String> lista) {
				super(context, layout, c, from, to);
				this.adContext = context;
				this.lista=lista;
			}
			
			public View getView(int pos, View inView, ViewGroup parent) {
				View v = inView;
				if (v == null) {
					LayoutInflater inflater = (LayoutInflater) adContext
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					v = inflater.inflate(R.layout.element_lista_detalii, null);
				}
				if (tipDate.equals("detalii")) 
				{
					((TextView) v.findViewById(R.id.textTextCaen)).setVisibility(View.GONE);
					if (InfoLocuinta.detalii_loc[pos]==1)((ImageView) v.findViewById(R.id.ivSelected)).setVisibility(View.VISIBLE);
					else ((ImageView) v.findViewById(R.id.ivSelected)).setVisibility(View.GONE);
					((TextView) v.findViewById(R.id.textElem)).setText(lista.get (pos));
				}
				else if ((tipDate.equals("cod_caen")))
				{
					((TextView) v.findViewById(R.id.textTextCaen)).setVisibility(View.VISIBLE);
					((TextView) v.findViewById(R.id.textTextCaen)).setText(DatabaseConnector.textCaen.get(pos));
					((TextView) v.findViewById(R.id.textElem)).setText(DatabaseConnector.codCaen.get(pos));
					((ImageView) v.findViewById(R.id.ivSelected)).setVisibility(View.GONE);
				}
					
				return v;
			}
		}
		
		private class CarsAndLogo extends SimpleCursorAdapter {
			private Context adContext;
			private ArrayList<String> lista;

			public int getCount() {
				return lista.size();
			}

			public CarsAndLogo(Context context, int layout, Cursor c,
					String[] from, int[] to,ArrayList<String> lista) {
				super(context, layout, c, from, to);
				this.adContext = context;
				this.lista=lista;
			}
			
			public View getView(int pos, View inView, ViewGroup parent) {
				View v = inView;
				if (v == null) {
					LayoutInflater inflater = (LayoutInflater) adContext
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					v = inflater.inflate(R.layout.element_lista_items, null);
				}

				((TextView) v.findViewById(R.id.textListTopElem)).setText(lista.get(pos));
				if (YTOUtils.isTablet(Lista.this))
					((TextView) v.findViewById(R.id.textListTopElem)).setTextSize(30);
				else ((TextView) v.findViewById(R.id.textListTopElem)).setTextSize(20);
				((TextView) v.findViewById(R.id.textListElem)).setText("");
				((ImageView) v.findViewById(R.id.image_right)).setVisibility(View.GONE);
				
				 String numeComp = lista.get(pos).toLowerCase();
				 int resID = getResources().getIdentifier(numeComp, "drawable", getPackageName());
				
				 if (resID!=0){
				((ImageView) v.findViewById(R.id.image_right)).getLayoutParams().height = 50;
				((ImageView) v.findViewById(R.id.image_right)).getLayoutParams().width = 50;
				((ImageView) v.findViewById(R.id.imgListElem)).setImageResource(resID);
				((ImageView) v.findViewById(R.id.imgListElem)).setVisibility(View.VISIBLE);
				 }
				 else ((ImageView) v.findViewById(R.id.imgListElem)).setVisibility(View.GONE);
					
				return v;
			}
		}
		
		
		
}