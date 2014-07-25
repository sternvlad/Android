package com.stern.Asigurare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class VeziAlerte extends ListActivity {

	ImageView imzero;//se afiseaza in cazul in care nu exista nicio inregistrare
	TextView tvzero;//se afiseaza in cazul in care nu exista nicio inregistrare
	int nrAlerte;//numarul de alerte
	AppSettings sett;
	int i=1;
	public static int scrollToHere = 0;
	public static ArrayList<YTOAlerta> alerte = new ArrayList<YTOAlerta>();
	private SimpleCursorAdapter conAdapter;
	DatabaseConnector dbConnector;
	
	
	public void onCreate(Bundle savedInstanceState) {
		//Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	//int width = display.getWidth(); 
    	Window window = getWindow(); 
//    	if (YTOUtils.isTablet(this)) {    	
//	    	window.setGravity(Gravity.CENTER); 
//	    	window.setLayout(400, 600);
//    	}
    	
		
		
		
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
		setContentView(R.layout.vezi_alerte);
		
		Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
		
		((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface.create(th,Typeface.BOLD));
		((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface.create(th,Typeface.BOLD));
		((TextView) findViewById(R.id.text_header3)).setTypeface(Typeface.create(th,Typeface.BOLD));
		imzero = (ImageView)findViewById (R.id.iv_zero_alerte);
		tvzero = (TextView)findViewById (R.id.tv_zero_alerte);
		//j = GetObiecte.getNrAlerteFromInterval();
		MainController.tvTitlu.setText (getString(R.string.i438));
		sett= AppSettings.get(this);
		
		RelativeLayout relative = (RelativeLayout) findViewById (R.id.hint_alerte);
		relative.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (i%3==0){
					errorDialog(getString(R.string.i475));
					i++;
				}
				else i++;
			}
		});
		

		
//		getAlerteFromInterval();
		alerte = GetObiecte.alerte;
		nrAlerte=alerte.size();
		if (nrAlerte!=0)
		{
//			getAlerteFromInterval();
			imzero.setVisibility(View.GONE);
			tvzero.setVisibility(View.GONE);
		conAdapter =(new ImageAndTextAdapter(VeziAlerte.this,
				R.layout.element_lista_alerte, null, null, null));
		setListAdapter(conAdapter);
		}
		else{
			imzero.setVisibility(View.VISIBLE);
			tvzero.setVisibility(View.VISIBLE);
		}
		
		ListView listView = (ListView) findViewById (android.R.id.list);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (i%3==0){
					errorDialog(getString(R.string.i475));
					i++;
				}
				else i++;
			}
		});
		getListView().setSelection(VeziAlerte.scrollToHere);
		//getListView().smoothScrollToPosition(VeziAlerte.scrollToHere);
		
		
	}
	
	  public void showRateDialog(final Context mContext) {
		     final Dialog dialog = new Dialog(this);
		     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		     dialog.setCancelable(false);
		     Typeface tf = Typeface.createFromAsset(getAssets(), "Angelina.ttf");

		     dialog.setContentView(R.layout.rate_dialog);
		     TextView likeit = (TextView) dialog.findViewById(R.id.likeit);
		     TextView text10 = (TextView) dialog.findViewById(R.id.text10);
		     likeit.setTypeface(Typeface.create(tf,Typeface.BOLD));
		     text10.setTypeface(Typeface.create(tf,Typeface.BOLD));
		     Button evaluate = (Button) dialog.findViewById(R.id.evaluate);
		     Button later = (Button) dialog.findViewById(R.id.remind_me_later);
		     Button never = (Button) dialog.findViewById(R.id.no_thanks);
		     evaluate.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sett.updateRatedApp(1);
             	Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
				    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				    try {
				    	mContext.startActivity(goToMarket);
				    } catch (ActivityNotFoundException e) {

				    }
				    sett.updateRatedApp(1);
                 dialog.dismiss();
				}
			});
		     
		     later.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final Calendar c = Calendar.getInstance();
                 int mYear = c.get(Calendar.YEAR);
                 int mMonth = c.get(Calendar.MONTH);
                 int mDay = c.get(Calendar.DAY_OF_MONTH);
                 sett.updateInstalledDate((mDay < 10 ? "0" + mDay : mDay) + "." + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "." + mYear);
                 sett.updateUsesUntilPrompt(5);
                 dialog.dismiss();
				}
			});
	             
		     never.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 sett.updateRatedApp(1);
                  dialog.dismiss();
				}
			});
	 
	        dialog.show();
	    }
	
	public void onResume()
	{
		super.onResume();
		if (sett.getRatedApp()==0)
        {
        	Date date1=null,date2 = null;
    		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    		Calendar c = Calendar.getInstance();
    		try {
    			date1 = sdf.parse(sett.getInstalledDate());
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		try {
    			String day;
    			c.add(Calendar.DATE, -5); 
        		int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
    			if (mDay<10) day="0"+mDay;
    			else day=mDay+"";
    			date2 = sdf.parse(day + "." + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "." + mYear);
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		if (date2!=null && date1!=null)
    			if (date2.after(date1))
    			{
    				showRateDialog(VeziAlerte.this);
    			}
    			else if (sett.getEventsUntilPrompt()<=0 && sett.getUsesUntilPrompt()<=0)
        		showRateDialog(getParent());
        }

		alerte = GetObiecte.alerte;
		//j = GetObiecte.getNrAlerteFromInterval();
//		getAlerteFromInterval();
		nrAlerte=alerte.size();
		if (nrAlerte!=0)
		{
//			getAlerteFromInterval();
			imzero.setVisibility(View.GONE);
			tvzero.setVisibility(View.GONE);
		conAdapter =(new ImageAndTextAdapter(VeziAlerte.this,
				R.layout.element_lista_alerte, null, null, null));
		setListAdapter(conAdapter);
		}
		else{
			imzero.setVisibility(View.VISIBLE);
			tvzero.setVisibility(View.VISIBLE);
		}
		getListView().setSelection(VeziAlerte.scrollToHere);
	}

		

	
	private class ImageAndTextAdapter extends SimpleCursorAdapter {
		private Context adContext;

		public int getCount() {
			return nrAlerte;
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
				v = inflater.inflate(R.layout.element_lista_alerte, null);
			}

			 ImageView img = (ImageView) v.findViewById(R.id.icon_alerta);
			 TextView dataAlerta = (TextView) v.findViewById(R.id.data_alerta);
			 TextView tipAlerta = (TextView) v.findViewById(R.id.tip_alerta);
			 TextView expirareAlerta = (TextView) v.findViewById(R.id.expirare);
			 TextView detaliiAlerta = (TextView) v.findViewById(R.id.detalii);
			 
			 dataAlerta.setText(alerte.get(pos).dataAlerta);
			 if (alerte.get(pos).tipAlerta==1)
			 {
				 img.setImageResource(R.drawable.icon_alerta_rca);
				 tipAlerta.setText(getString(R.string.i348));
				 expirareAlerta.setText(getString(R.string.i476));
				 for (YTOAutovehicul autovehicul: GetObiecte.autovehicule)
					 if (autovehicul.idIntern .equals(alerte.get(pos).idObiect))
					 {
						detaliiAlerta.setText(autovehicul.marcaAuto +"\n"+autovehicul.nrInmatriculare);
						break;
					 }
			 }
			 else if (alerte.get(pos).tipAlerta==2)
			 {
				 img.setImageResource(R.drawable.icon_alerta_itp);
				 tipAlerta.setText(getString(R.string.i349));
				 expirareAlerta.setText(getString(R.string.i479));
				 for (YTOAutovehicul autovehicul: GetObiecte.autovehicule)
					 if (autovehicul.idIntern .equals(alerte.get(pos).idObiect))
					 {
						detaliiAlerta.setText(autovehicul.marcaAuto +"\n"+autovehicul.nrInmatriculare);
						break;
					 }
			 }
			 else if (alerte.get(pos).tipAlerta==3)
			 {
				 img.setImageResource(R.drawable.icon_alerta_rovinieta);
				 tipAlerta.setText(getString(R.string.i350));
				 expirareAlerta.setText(getString(R.string.i477));
				 for (YTOAutovehicul autovehicul: GetObiecte.autovehicule)
					 if (autovehicul.idIntern .equals(alerte.get(pos).idObiect))
					 {
						detaliiAlerta.setText(autovehicul.marcaAuto +"\n"+autovehicul.nrInmatriculare);
						break;
					 }
			 }
			 else if (alerte.get(pos).tipAlerta==5)
			 {
				 img.setImageResource(R.drawable.icon_alerta_casco);
				 tipAlerta.setText(getString(R.string.i351));
				 expirareAlerta.setText(getString(R.string.i476));
				 for (YTOAutovehicul autovehicul: GetObiecte.autovehicule)
					 if (autovehicul.idIntern .equals(alerte.get(pos).idObiect))
					 {
						detaliiAlerta.setText(autovehicul.marcaAuto +"\n"+autovehicul.nrInmatriculare);
						break;
					 }
			 }
			 else if (alerte.get(pos).tipAlerta==7)
			 {
				 img.setImageResource(R.drawable.icon_alerta_rata_casco);
				 tipAlerta.setText(getString(R.string.i352));
				 expirareAlerta.setText(getString(R.string.i478));
				 for (YTOAutovehicul autovehicul: GetObiecte.autovehicule)
					 if (autovehicul.idIntern .equals(alerte.get(pos).idObiect))
					 {
						detaliiAlerta.setText(autovehicul.marcaAuto +"\n"+autovehicul.nrInmatriculare);
						break;
					 }
			 }
			 else if (alerte.get(pos).tipAlerta==6)
			 {
				 img.setImageResource(R.drawable.icon_alerta_locuinta);
				 tipAlerta.setText(getString(R.string.i411));
				 expirareAlerta.setText(getString(R.string.i476));
				 for (YTOLocuinta locuinta: GetObiecte.locuinte)
					 if (locuinta.idIntern .equals(alerte.get(pos).idObiect))
					 {
						detaliiAlerta.setText(locuinta.judet +"\n"+(locuinta.suprafataUtila < 0 ? "" : locuinta.suprafataUtila+"mp"));
						break;
					 }
			 }
			 else if (alerte.get(pos).tipAlerta==8)
			 {
				 img.setImageResource(R.drawable.icon_alerta_rata_locuinta);
				 tipAlerta.setText(getString(R.string.i412));
				 expirareAlerta.setText(getString(R.string.i478));
				 for (YTOLocuinta locuinta: GetObiecte.locuinte)
					 if (locuinta.idIntern .equals(alerte.get(pos).idObiect))
					 {
						detaliiAlerta.setText(locuinta.judet +"\n"+(locuinta.suprafataUtila < 0 ? "" : locuinta.suprafataUtila+"mp"));
						break;
					 }
			 }
						
			return v;	
	}
	}
	
	@Override
	public void onBackPressed ()
	{
		exitDialog();
	}
	
	public void errorDialog (String mesaj) 
	{ //eroare
	     final Dialog dialog=new Dialog(this);
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog_error);
	     TextView tvContinut;
	     //ImageView imgContinut;
	     TextView titlu;
	     titlu = (TextView) dialog.findViewById(R.id.text_titlu);
	     TextView textHeader;
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     textHeader = (TextView) dialog.findViewById(R.id.text_eroare_header);
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));

	     textHeader.setTextColor(getResources().getColor(R.color.verde));
	     textHeader.setText(getString(R.string.i803));
	     titlu.setText("INFO");
	     tvContinut = (TextView)dialog.findViewById (R.id.text_error);
	     tvContinut.setText(getString (R.string.i475));
	     Button btn_ok=(Button)dialog.findViewById(R.id.button_ok);
	     
	     btn_ok.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	     }
	     });

	   dialog.show();
	}
	
	public void exitDialog () {
	     final Dialog dialog=new Dialog(this);
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog);

	     ((TextView) dialog.findViewById(R.id.text_titlu)).setText("INFO");
	     ((TextView) dialog.findViewById(R.id.text_continut)).setText(getString(R.string.i435));
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     TextView textHeader = (TextView) dialog.findViewById(R.id.text_vrei_sa_stergi);
	     textHeader.setTextColor(getResources().getColor(R.color.rosu_ter));
	     textHeader.setText(getString(R.string.i804));
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
	     
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
		    	 sett.updateLanguageWasChanged("false");
		    		finish();
		    		Intent intent = new Intent(Intent.ACTION_MAIN);
		    		intent.addCategory(Intent.CATEGORY_HOME);
		    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    		startActivity(intent);
		    		android.os.Process.killProcess(android.os.Process.myPid());
	            }	
		     });
	   dialog.show();
	}
	

}

