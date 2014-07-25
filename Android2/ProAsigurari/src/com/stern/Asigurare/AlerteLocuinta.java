package com.stern.Asigurare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import android.app.ActivityGroup;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AlerteLocuinta extends ActivityGroup {

	public static String idObiect;
	static final int DATE_DIALOG_ID = 1;
	public static Dialog dialog;
	int push;
	int[] images = { R.drawable.icon_alerta_locuinta,
			R.drawable.icon_alerta_rata_locuinta};
	Button menu2;
	YTOAlerta alertaLocuinta=null,alertaRata=null;
	ListView listView; 
	private int mYear;
	private int mMonth;
	private int mDay;
	String contUser = "";
	String contParola = "";
	String limba = "ro";
	String versiune = "";
	Boolean alerta=false;
	String[] labelsTop = new String [2];
	String[] labels = new String [2];

	AppSettings appSett;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	int width = display.getWidth(); 
    	Window window = getWindow(); 
    	
    	labelsTop [0] = getString(R.string.i411);
    	labelsTop [1] = getString(R.string.i412);
    	labels [0] =  getString(R.string.i413);
    	labels [1] =  getString(R.string.i413);
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		appSett = AppSettings.get(this);
		listView = (ListView) findViewById (android.R.id.list);
		final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        limba = appSett.getLanguage();
		PackageInfo pInfo = null;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		versiune = pInfo.versionName;
		contUser = appSett.getUsername();
		contParola = appSett.getPassword();

		if (!idObiect.equals(""))
		{
			loadAlerte();
		}
		else
		{
			errorDialog("");
		}
		if (appSett.getLanguage().equals("hu")&& !YTOUtils.isTablet(getParent().getParent()))
		{
			Locuinta.textHeader1.setTextSize(16);
			Locuinta.textHeader2.setTextSize(16);
		}
		
		if (appSett.getLanguage().equals("hu") && YTOUtils.isTablet(getParent().getParent()))
        {
        	Locuinta.textHeader1.setTextSize(38);
        	Locuinta.textHeader2.setTextSize(38);
        }
		Locuinta.changeHeader(3, getApplicationContext());
		listView.setAdapter(new ImageAndTextAdapter(AlerteLocuinta.this,
				R.layout.element_lista_alerta, null, null, null));
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mYear = c.get(Calendar.YEAR);
    	        mMonth = c.get(Calendar.MONTH);
    	        mDay = c.get(Calendar.DAY_OF_MONTH);
    	        mDay = mDay + 1;
				if (position == 0 && !idObiect.equals("")){
					showDialog(DATE_DIALOG_ID);
					push = 6;
				}
				if (position == 1 && !idObiect.equals("")){
					showDialog(DATE_DIALOG_ID);
					push = 8;
				}
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0 && alertaLocuinta!=null)
				{
					deleteDialog(alertaLocuinta.idIntern,alertaLocuinta);
					labels [0] = getString(R.string.i413);
					return true;
				}
				else if (position == 1 && alertaRata!=null)
				{
					deleteDialog(alertaRata.idIntern,alertaRata);
					labels[1] = getString(R.string.i413);
					return true;
				}
				else return false;
			}
		});
	}
	private class ImageAndTextAdapter extends SimpleCursorAdapter {
		private Context adContext;
		Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");

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
				v = inflater.inflate(R.layout.element_lista_alerta, null);
			}
			if (pos == 0 && !labels[pos].equals(getString(R.string.i413))){
				((TextView) v.findViewById(R.id.text_List)).setTextSize(22);
				((TextView) v.findViewById(R.id.text_List)).setTypeface(Typeface.create(tf,Typeface.BOLD));
				((TextView) v.findViewById(R.id.text_List)).setTextColor(Color.parseColor("#464646"));
			}
			if (pos == 1 && !labels[pos].equals(getString(R.string.i413))) {
				((TextView) v.findViewById(R.id.text_List)).setTextSize(22);
				((TextView) v.findViewById(R.id.text_List)).setTypeface(Typeface.create(tf,Typeface.BOLD));
				((TextView) v.findViewById(R.id.text_List)).setTextColor(Color.parseColor("#464646"));
			}
			
			((TextView) v.findViewById(R.id.text_List)).setText(labels[pos]);
			((TextView) v.findViewById(R.id.text_listTop)).setText(labelsTop[pos]);
			((ImageView) v.findViewById(R.id.left_img)).setImageResource(images[pos]);
			return v;
		}
		
		
	}
	
	public void loadAlerte()
	{
		GetObiecte.getAlerteByIdObiect (idObiect);
		for (int i=0;i<GetObiecte.alertebyid.size();i++)
		{
			if (GetObiecte.alertebyid.get(i).tipAlerta==6)//locuinta
			{
				labels[0]=GetObiecte.alertebyid.get(i).dataAlerta;
				alertaLocuinta = new YTOAlerta();
				alertaLocuinta = GetObiecte.alertebyid.get(i);
			}
				
			else if (GetObiecte.alertebyid.get(i).tipAlerta==8)//ratalocuinta
			{
				labels[1]=GetObiecte.alertebyid.get(i).dataAlerta;
				alertaRata = new YTOAlerta();
				alertaRata = GetObiecte.alertebyid.get(i);
			}
		}
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
 
        case DATE_DIALOG_ID:
        	if (!alerta) 
        	{
        		final Calendar c = Calendar.getInstance();
    	        mYear = c.get(Calendar.YEAR);
    	        mMonth = c.get(Calendar.MONTH);
    	        mDay = c.get(Calendar.DAY_OF_MONTH);
    	        mDay = mDay + 1;
        	}
            return new DatePickerDialog(getParent().getParent(),
                mDateSetListener,
                mYear, mMonth, mDay);
        }
        return null;
    }
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
 
        case DATE_DIALOG_ID:
            ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
            break;
        }
    }    
    private void updateDisplay() {
    	Date date1=null,date2 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Calendar c = Calendar.getInstance();
		String dataMinima = sdf.format(c.getTime());
		//String dataSetata = sdf.format(mDay+"."+mMonth+"."+mYear);
		try {
			date1 = sdf.parse(dataMinima);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String day;
			if (mDay<10) day="0"+mDay;
			else day=mDay+"";
			date2 = sdf.parse(day + "." + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "." + mYear);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	if (date1.before(date2))
    	{
    		alerta=true;
        if (push == 6)
        {
        	labels[0] = (mDay < 10 ? "0" + mDay : mDay) + "." + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "." + mYear;
        	saveAlerta(idObiect,push,(alertaLocuinta==null) ? false:true,labels[0]);
        	onResume();
        	dialog.dismiss();
        }
        else if (push == 8 )
        {
        	labels[1] =  (mDay < 10 ? "0" + mDay : mDay) + "." + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "." + mYear;
        	saveAlerta(idObiect,push,(alertaRata==null) ? false:true,labels[1]);
        	onResume();
        	dialog.dismiss();
        }
    	}
        else 
        {
        	errorDialog("Data expirarii trebuie sa fie mai mare ca data curenta!");
        }
        	
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener =
        new DatePickerDialog.OnDateSetListener() {
 
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };
    
    private void saveAlerta (String idObiect,int push,boolean isDirty,String data)
    {
    	YTOAlerta alerta = new YTOAlerta();
    	alerta.dataAlerta = data;
    	alerta.idObiect = idObiect;
    	final Calendar c = Calendar.getInstance();
    	alerta.dataCreare = c.get(Calendar.DAY_OF_MONTH)+"."+c.get(Calendar.MONTH)+"."+c.get(Calendar.YEAR);
    	alerta.idIntern = UUID.randomUUID().toString();
    	alerta.tipAlerta=push;
    	String idIntern="";
    	if (isDirty && push == 6) idIntern = alertaLocuinta.idIntern;
    	else if (isDirty && push == 8) idIntern = alertaRata.idIntern; 
    	String toJSON = alerta.alertaToJSON();
	    DatabaseConnector dbConnector = new DatabaseConnector(this);
	    if (!isDirty)
	      {
	    	  dbConnector.insertObiectAsigurat(alerta.idIntern,
		        		 "5",
		        		 toJSON);
	    	  if (push==6) {alertaLocuinta=alerta;}
	    	  else if (push==8){alertaRata=alerta;}
	    	  alerta = alerta.alertaFromJSON(toJSON);

	      }
	    else
	    {
	    	 dbConnector.updateObiectAsigurat(idIntern,
	        		 "5",
	        		 toJSON);
	    	 alerta = alerta.alertaFromJSON(toJSON);

	    }
	    new TrimiteAlerta(alerta).execute(null, null, null);
	    GetObiecte.getAlerte(dbConnector);
		MainController.setBadge();
	 }
    public void onResume()
    {
    	super.onResume();
    	Locuinta.changeHeader(3, getApplicationContext());
    	listView.setAdapter(new ImageAndTextAdapter(AlerteLocuinta.this,
				R.layout.element_lista_alerta, null, null, null));
    }
    
    
    public void deleteDialog (final String idIntern,final YTOAlerta alerta) {
	     final Dialog dialog=new Dialog(getParent().getParent());
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog);

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
		    	DatabaseConnector dbConnector=new DatabaseConnector (AlerteLocuinta.this);
		    	dbConnector.deleteObiectAsigurat(idIntern); 
		    	new StergeAlerta(alerta).execute(null, null, null);
		    	GetObiecte.getAlerte(dbConnector);
				MainController.setBadge();
	            onResume();
			    dialog.dismiss();
	            }	
		     });
	   dialog.show();
	}
    
	public void errorDialog (String mesaj) 
	{ //eroare
	     final Dialog dialog=new Dialog(getParent().getParent());
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog_error);
	     TextView tvContinut,titlu;
	     titlu = (TextView) dialog.findViewById(R.id.text_titlu);
	
	     
	     tvContinut = (TextView)dialog.findViewById (R.id.text_error);
	     
	     TextView textHeader;
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     textHeader = (TextView) dialog.findViewById(R.id.text_eroare_header);
	     textHeader.setTextColor(getResources().getColor(R.color.verde));
	     textHeader.setText(getString(R.string.i803));
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
	     titlu.setText("INFO");
	     tvContinut.setText(getString(R.string.i373));
	     
	     Button btn_ok=(Button)dialog.findViewById(R.id.button_ok);

	     if (!mesaj.equals("")){
	    	 tvContinut.setText (mesaj);
	    	 textHeader.setTextColor(getResources().getColor(R.color.rosu_termeni));
		     textHeader.setText(getString(R.string.i799));
	     }
	     
	     btn_ok.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	     }
	     });

	   dialog.show();
	}
	
	private class TrimiteAlerta extends AsyncTask<Void, Void, Void> {
		YTOAlerta alerta = new YTOAlerta();
		String email;
		public TrimiteAlerta(YTOAlerta alerta) {
			// TODO Auto-generated constructor stub
			this.alerta=alerta;
			if (!appSett.getEmailContact().equals("")) email=appSett.getEmailContact();
			else email="fara email";
		}
		@Override
		protected void onPreExecute(){  
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... arg0) {

			String dataAlerta = alerta.dataAlerta;
			String year = dataAlerta.substring(6,dataAlerta.length());
			String mm = dataAlerta.substring(3,5);
			String dd = dataAlerta.substring(0,2);
			dataAlerta = year+"-"+mm+"-"+dd;
			
			String xml ="<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<soap:Body>" +
    "<InregistrareAlerte4 xmlns='http://tempuri.org/'>" +
     "<user>vreaurca</user>" +
     "<password>123</password>" +
      "<tip>"+alerta.tipAlerta+"</tip>" +
      "<data_alerta>"+dataAlerta+"</data_alerta>" +
      "<nr_rata>"+alerta.numarRata+"</nr_rata>" +
      "<prima>0.0</prima>" +
      "<moneda>lei</moneda>" +
      "<udid>"+appSett.getDeviceId()+"</udid>" +
      "<id_intern>"+idObiect+"</id_intern>" +
      "<platforma>Android</platforma>" +
      "<email>"+email+"</email>" +
      "<cont_user>"+contUser+"</cont_user>" +
      "<cont_parola>"+contParola+"</cont_parola>" +
      "<limba>"+limba+"</limba>" +
      "<versiune>"+versiune+"</versiune>" +
    "</InregistrareAlerte4>" +
  "</soap:Body>" +
"</soap:Envelope>";
			
			
			String url = GetObiecte.link+"rca.asmx";
			String soapAction = "http://tempuri.org/InregistrareAlerte4";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			return null;
		}

		@Override
		protected void onPostExecute(final Void unused){			
			super.onPostExecute(null);
	}
	}
	
	private class StergeAlerta extends AsyncTask<Void, Void, Void> {
		YTOAlerta alerta = new YTOAlerta();
		public StergeAlerta(YTOAlerta alerta) {
			// TODO Auto-generated constructor stub
			this.alerta=alerta;
		}
		@Override
		protected void onPreExecute(){  
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... arg0) {

			String xml ="<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<soap:Body>" +
    "<DisableAlert xmlns='http://tempuri.org/'>" +
      "<user>vreaurca</user>" +
      "<password>123</password>" +
      "<udid>"+appSett.getDeviceId()+"</udid>" +
      "<id_intern>"+idObiect+"</id_intern>" +
      "<tip_alerta>"+alerta.tipAlerta+"</tip_alerta>" +
    "</DisableAlert>" +
  "</soap:Body>" +
"</soap:Envelope>";
			
			
			String url = GetObiecte.link+"sync.asmx";
			String soapAction = "http://tempuri.org/DisableAlert";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			return null;
		}

		@Override
		protected void onPostExecute(final Void unused){			
			super.onPostExecute(null);
	}
	}
	
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
	}
	
}
