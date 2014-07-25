package com.stern.Asigurare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import android.app.ActivityGroup;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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



public class AlerteMasina extends ActivityGroup {

	public static String idObiect;
	public static Dialog dialog;
	static final int DATE_DIALOG_ID = 1;
	Boolean alerta=false;
	YTOAlerta alertaRCA=null,alertaITP=null,alertaCASCO=null,alertaRovinieta=null,alertaRataCASCO=null;
	int[] images = { R.drawable.icon_alerta_rca,
			R.drawable.icon_alerta_itp, R.drawable.icon_alerta_rovinieta,
			R.drawable.icon_alerta_casco, R.drawable.icon_alerta_rata_casco,};
	Button menu2;
	private int mYear;
	private int mMonth;
	private int mDay;
	String contUser = "";
	String contParola = "";
	String limba = "ro";
	String versiune = "";
	int push;
	ListView listView; 
	String[] labelsTop = new String [5];
	String[] labels =  new String [5];

	AppSettings appSett;
	

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
//		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//    	int width = display.getWidth(); 
    	Window window = getWindow(); 
//    	if (YTOUtils.isTablet(this)) {   	
//	    	window.setGravity(Gravity.CENTER); 
//	    	window.setLayout(320, 480);
//    	}
    	
    	labelsTop [0] = getString(R.string.i348);
    	labelsTop [1] = getString(R.string.i349);
    	labelsTop [2] = getString(R.string.i350);
    	labelsTop [3] = getString(R.string.i351);
    	labelsTop [4] = getString(R.string.i352);
    	labels [0] = getString(R.string.i354);
    	labels [1] = getString(R.string.i354);
    	labels [2] = getString(R.string.i354);
    	labels [3] = getString(R.string.i354);
    	labels [4] = getString(R.string.i354);
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		appSett = AppSettings.get(this);
		listView = (ListView) findViewById (android.R.id.list);
		Masina.changeHeader(3, getApplicationContext());
		
		final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
		
        appSett = AppSettings.get(this);
        
		if (!idObiect.equals(""))
		{
			loadAlerte();
		}
		else
		{
			errorDialog("");
		}
		
        if (appSett.getLanguage().equals("hu") && YTOUtils.isTablet(getParent().getParent()))
        {
        	Masina.textHeader3.setTextSize(23);
        }
        
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

		
		
		listView.setAdapter(new ImageAndTextAdapter(AlerteMasina.this,
				R.layout.element_lista_alerta, null, null, null));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("deprecation")
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mYear = c.get(Calendar.YEAR);
    	        mMonth = c.get(Calendar.MONTH);
    	        mDay = c.get(Calendar.DAY_OF_MONTH);
    	        mDay = mDay + 1;
				if (position == 0 && !idObiect.equals("")){
					showDialog(DATE_DIALOG_ID);
					push = 1;//rca
					
				}
				if (position == 1 && !idObiect.equals("")) {
					showDialog(DATE_DIALOG_ID);
					push = 2;//itp
				}
				if (position == 2 && !idObiect.equals("")){
					showDialog(DATE_DIALOG_ID);
					push = 3;//rovinieta
				}
				if (position == 3 && !idObiect.equals("")){
					showDialog(DATE_DIALOG_ID);
					push = 5;//casco
				}
				if (position == 4 && !idObiect.equals("")){
					showDialog(DATE_DIALOG_ID);
					push = 7;//ratacasco
				}
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0 && alertaRCA!=null)
				{
					deleteDialog(alertaRCA.idIntern,alertaRCA);
					labels [0] =  getString(R.string.i354);
					return true;
				}
				else if (position == 1 && alertaITP!=null)
				{
					deleteDialog(alertaITP.idIntern,alertaITP);
					labels[1] =  getString(R.string.i354);
					return true;
				}
				else if (position == 2 && alertaRovinieta!=null)
				{
					deleteDialog(alertaRovinieta.idIntern,alertaRovinieta);
					labels[2] =  getString(R.string.i354);
					return true;
				}
				else if (position == 3 && alertaCASCO!=null)
				{
					deleteDialog(alertaCASCO.idIntern,alertaCASCO);
					labels[3] =  getString(R.string.i354);
					return true;
				}
				else if (position == 4 && alertaRataCASCO!=null)
				{
					deleteDialog(alertaRataCASCO.idIntern,alertaRataCASCO);
					labels[4] =  getString(R.string.i354);
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
			if (pos == 0 && !labels[pos].equals( getString(R.string.i354))){
				((TextView) v.findViewById(R.id.text_List)).setTextSize(22);
				((TextView) v.findViewById(R.id.text_List)).setTypeface(Typeface.create(tf,Typeface.BOLD));
				((TextView) v.findViewById(R.id.text_List)).setTextColor(Color.parseColor("#464646"));
			}
			if (pos == 1 && !labels[pos].equals(getString(R.string.i354))) {
				((TextView) v.findViewById(R.id.text_List)).setTextSize(22);
				((TextView) v.findViewById(R.id.text_List)).setTypeface(Typeface.create(tf,Typeface.BOLD));
				((TextView) v.findViewById(R.id.text_List)).setTextColor(Color.parseColor("#464646"));
			}
			if (pos == 2 && !labels[pos].equals(getString(R.string.i354))){
				((TextView) v.findViewById(R.id.text_List)).setTextSize(22);
				((TextView) v.findViewById(R.id.text_List)).setTypeface(Typeface.create(tf,Typeface.BOLD));
				((TextView) v.findViewById(R.id.text_List)).setTextColor(Color.parseColor("#464646"));
			}
			if (pos == 3 && !labels[pos].equals(getString(R.string.i354))) {
				((TextView) v.findViewById(R.id.text_List)).setTextSize(22);
				((TextView) v.findViewById(R.id.text_List)).setTypeface(Typeface.create(tf,Typeface.BOLD));
				((TextView) v.findViewById(R.id.text_List)).setTextColor(Color.parseColor("#464646"));
			}
			if (pos == 4 && !labels[pos].equals(getString(R.string.i354))){
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
	
	public void onResume()
	{
	  	super.onResume();
	  	Masina.changeHeader(3, getApplicationContext());
	   	listView.setAdapter(new ImageAndTextAdapter(AlerteMasina.this,
				R.layout.element_lista_alerta, null, null, null));
	}
	
	public void loadAlerte()
	{
		GetObiecte.getAlerteByIdObiect (idObiect);
		for (int i=0;i<GetObiecte.alertebyid.size();i++)
		{
			if (GetObiecte.alertebyid.get(i).tipAlerta==1)//rca
			{
				labels[0]=GetObiecte.alertebyid.get(i).dataAlerta;
				alertaRCA = new YTOAlerta();
				alertaRCA = GetObiecte.alertebyid.get(i);
			}
			else if (GetObiecte.alertebyid.get(i).tipAlerta==2)//itp
			{
				labels[1]=GetObiecte.alertebyid.get(i).dataAlerta;
				alertaITP = new YTOAlerta();
				alertaITP = GetObiecte.alertebyid.get(i);
			}
			else if (GetObiecte.alertebyid.get(i).tipAlerta==3)//rovinieta
			{
				labels[2]=GetObiecte.alertebyid.get(i).dataAlerta;
				alertaRovinieta = new YTOAlerta();
				alertaRovinieta = GetObiecte.alertebyid.get(i);
			}
			else if (GetObiecte.alertebyid.get(i).tipAlerta==5)//casco
			{
				labels[3]=GetObiecte.alertebyid.get(i).dataAlerta;
				alertaCASCO = new YTOAlerta();
				alertaCASCO = GetObiecte.alertebyid.get(i);
			}
			else if (GetObiecte.alertebyid.get(i).tipAlerta==7)//rata casco
			{
				labels[4]=GetObiecte.alertebyid.get(i).dataAlerta;
				alertaRataCASCO = new YTOAlerta();
				alertaRataCASCO = GetObiecte.alertebyid.get(i);
			}
		}
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
 
        case DATE_DIALOG_ID:
            return new DatePickerDialog(getParent().getParent(),
                mDateSetListener,
                mYear, mMonth, mDay);
        }
        return null;
    }
    protected void onPrepareDialog(int id, Dialog dialog) {
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
        if (push == 1)//rca
        {
        	labels[0] = (mDay < 10 ? "0" + mDay : mDay) + "." + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "." + mYear;
        	saveAlerta(idObiect,push,(alertaRCA==null) ? false:true,labels[0]);
        	onResume();
        	dialog.dismiss();
        }
        else if (push == 2)//itp
        {
        	labels[1] =  (mDay < 10 ? "0" + mDay : mDay) + "." + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "." + mYear;
        	saveAlerta(idObiect,push,(alertaITP==null) ? false:true,labels[1]);
        	onResume();
        	dialog.dismiss();
        }
        else if (push == 3)//rovinieta
        {
        	labels[2] =  (mDay < 10 ? "0" + mDay : mDay) + "." + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "." + mYear;
        	saveAlerta(idObiect,push,(alertaRovinieta==null) ? false:true,labels[2]);
        	onResume();
        	dialog.dismiss();
        }
        else if (push == 5)//casco
        {
        	labels[3] =  (mDay < 10 ? "0" + mDay : mDay) + "." + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "." + mYear;
        	saveAlerta(idObiect,push,(alertaCASCO==null) ? false:true,labels[3]);
        	onResume();
        	dialog.dismiss();
        }
        else if (push == 7)//ratacasco
        {
        	labels[4] =  (mDay < 10 ? "0" + mDay : mDay) + "." + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "." + mYear;
        	saveAlerta(idObiect,push,(alertaRataCASCO==null) ? false:true,labels[4]);
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
	    	if (isDirty && push == 1) idIntern = alertaRCA.idIntern;//rca
	    	else if (isDirty && push == 2) idIntern = alertaITP.idIntern;//itp
	    	else if (isDirty && push == 3) idIntern = alertaRovinieta.idIntern;//rovinieta
	    	else if (isDirty && push == 5) idIntern = alertaCASCO.idIntern;//casco
	    	else if (isDirty && push == 7) idIntern = alertaRataCASCO.idIntern;//ratacasco
	    	String toJSON = alerta.alertaToJSON();
		    DatabaseConnector dbConnector = new DatabaseConnector(this);
		    if (!isDirty)
		      {
		    	  dbConnector.insertObiectAsigurat(alerta.idIntern,
			        		 "5",
			        		 toJSON);
		    	  alerta = alerta.alertaFromJSON(toJSON);
		    	  if (push==1) {alertaRCA=alerta;}
		    	  else if (push==2){alertaITP=alerta;}
		    	  else if (push==3){alertaRovinieta=alerta;}
		    	  else if (push==5){alertaCASCO=alerta;}
		    	  else if (push==7){alertaRataCASCO=alerta;}
		    	  

		      }
		    else
		    {
		    	 dbConnector.updateObiectAsigurat(idIntern,
		        		 "5",
		        		 toJSON);
		    	 alerta = alerta.alertaFromJSON(toJSON);
		    	  if (push==1) {alertaRCA=alerta;}
		    	  else if (push==2){alertaITP=alerta;}
		    	  else if (push==3){alertaRovinieta=alerta;}
		    	  else if (push==5){alertaCASCO=alerta;}
		    	  else if (push==7){alertaRataCASCO=alerta;}
		    }
		    new TrimiteAlerta(alerta).execute(null, null, null);
		    GetObiecte.getAlerte(dbConnector);
		    MainController.setBadge();
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
		    	DatabaseConnector dbConnector=new DatabaseConnector (AlerteMasina.this);
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
	     TextView tvContinut;
	     TextView titlu;
	     titlu = (TextView) dialog.findViewById(R.id.text_titlu);
	     TextView textHeader;
	     
	     tvContinut = (TextView)dialog.findViewById (R.id.text_error);
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     textHeader = (TextView) dialog.findViewById(R.id.text_eroare_header);
	     textHeader.setTextColor(getResources().getColor(R.color.verde));
	     textHeader.setText(getString(R.string.i803));
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
	     titlu.setText("INFO");
	     tvContinut = (TextView)dialog.findViewById (R.id.text_error);
	     tvContinut.setText(getString(R.string.i347));
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
		String resp;
		ProgressDialog dlg = new ProgressDialog(getParent().getParent());
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
      "<prima>"+0.0+"</prima>" +
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
			resp = HttpHelper.callWebService( url, soapAction,xml);
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
      "<user>vreaurcA</user>" +
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
