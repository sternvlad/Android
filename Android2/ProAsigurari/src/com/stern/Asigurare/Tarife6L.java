package com.stern.Asigurare;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class Tarife6L 	extends Activity {

		public static ListView listView;
		static ArrayList<String> sort = new ArrayList<String>();
		TextView tvPret,tvBm;
		public static int positionId;
		ImageView imvCompanie;

		 @Override
		    public void onCreate(Bundle savedInstanceState) {
//				Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//		    	int width = display.getWidth(); 
		    	Window window = getWindow(); 
//		    	if (YTOUtils.isTablet(this)) {    	
//			    	window.setGravity(Gravity.CENTER); 
//			    	window.setLayout(320, 480);
//		    	}
		    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.tarife);

		        
		        	listView = (ListView) findViewById(android.R.id.list);   
		    		listView.setAdapter(new ImageAndTextAdapter6l(Tarife6L.this,
		    				R.layout.element_lista_rca, null, null, null));
		    		listView.setOnItemClickListener(new OnItemClickListener() {
		    			public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
		    				positionId=position;
		    				SumarComandaRCA.durata=6;
		    				Intent i = new Intent (Tarife6L.this,SumarComandaRCA.class);
		    				startActivity(i);
		    			}
					});
          }
		 
		 
    private class ImageAndTextAdapter6l extends SimpleCursorAdapter {
		private Context adContext;

		public int getCount() 
		{
			return CallWebServiceRCA.lista6l.size();
		}

		public ImageAndTextAdapter6l(Context context, int layout, Cursor c,
				String[] from, int[] to) {
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
				((TextView) v.findViewById(R.id.pret_rca)).setText(CallWebServiceRCA.prima6l.get(pos)+"");
				((TextView) v.findViewById(R.id.clasa_bm)).setText("Clasa B/M: "+CallWebServiceRCA.clasa6l.get(pos));

				
			return v;
		}
	}

    
   
}