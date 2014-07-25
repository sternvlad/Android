package com.stern.Asigurare;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

@SuppressWarnings("deprecation")
public class MainController extends TabActivity {
	String TAG = getClass().getName();
	private float MENU_WIDTH = .8f;
	private TabHost tabHost;
	Button btnAsigurari;
	Button btnDate;
	Button btnAlerte;
	Button btnInfo;
	public static TextView tvTitlu;
	public static TextView txtCount;
	ProgressDialog dlg ;
	public RelativeLayout linearCount;//badge layout
	public RelativeLayout badge;//badge image
	public static Button buttonSync;
	public static Button buttonNotificari; 
	DatabaseConnector dbConnector;
	AppSettings sett;
	private RelativeLayout header;
	private RelativeLayout content;
	private RelativeLayout contentViewOverlay;
	private TextView headerTitle;
	private MainControllerGestureEventListener gestureEventListenerHeaderView;
	private MainControllerGestureEventListener gestureEventListenerMenuButton;
	private GestureDetector gestureScannerHeaderView;
	private GestureDetector gestureScannerBackButton;
	Map<String, String> mapActivityTitles;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	Window window = getWindow(); 
    	int width = display.getWidth(); 
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        sett = AppSettings.get(this);
        try{
    		GCMRegistrar.checkDevice(this);
    		GCMRegistrar.checkManifest(this);
    		String regId = GCMRegistrar.getRegistrationId(this);
    		if (regId.equals("")) {
    			GCMRegistrar.register(getApplicationContext(),GetObiecte.SENDER_ID);
    			  if (sett.getTokenSent()==0 && !GetObiecte.deviceToken.equals("")) 
    			  {
    				  new PushNotificationsToken(GetObiecte.deviceToken).execute(null,null, null);
    				  sett.updatePushToken (GetObiecte.deviceToken);
    				  sett.updateTokenSent(1);
    			  }
    		} else {
    		   	Log.v("GCM", "Already registered");
    			  if (sett.getTokenSent()==0 && !GCMRegistrar.getRegistrationId(this).equals("")) 
    			  {
    				  new PushNotificationsToken(regId).execute(null,null, null);
    				  sett.updatePushToken (GetObiecte.deviceToken);
    				  sett.updateTokenSent(1);
    			  }
    		}}catch (Exception e) {
    			// TODO: handle exception
    		}
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        
        buttonSync = (Button) findViewById (R.id.button_sinc);		
		buttonNotificari = (Button) findViewById(R.id.button_notificare);
		if (sett.getNumberOfNewNotifications() > 0)
		buttonNotificari.setBackgroundResource(R.drawable.notificari_noi_icon);
		
		txtCount = (TextView) findViewById (R.id.txtCount);
        dbConnector = new DatabaseConnector(MainController.this);
        linearCount = (RelativeLayout) findViewById (R.id.linear_count);
        badge = (RelativeLayout) findViewById (R.id.badge_image);
        int nr = width/8+width/32;
        linearCount.setPadding(nr, 0, 0, 0);
        
        if (GetObiecte.persoane==null || SplashScreen.udid==null || SplashScreen.udid.equals(""))
        {
        	GetObiecte.getPersoane(dbConnector);
        	if (GetObiecte.persoane!=null && GetObiecte.persoane.size()!=0){
                if (GetObiecte.persoanaFizica.isDirty) AltePersoane.persoanaAsigurata=GetObiecte.persoanaFizica;
                else if (GetObiecte.persoanaJuridica.isDirty) AltePersoane.persoanaAsigurata=GetObiecte.persoanaJuridica;
                else AltePersoane.persoanaAsigurata=new YTOPersoana();}
        	GetObiecte.getAutovehicule(dbConnector);
            MasinileMele.autovehiculCurent=new YTOAutovehicul();
            GetObiecte.getLocuinte(dbConnector);
            LocuinteleMele.locuintaCurenta=new YTOLocuinta();
            GetObiecte.getAsigurari(dbConnector);
            GetObiecte.getDate(dbConnector);
            GetObiecte.getAlerte(dbConnector);
            TelephonyManager tel =(TelephonyManager)MainController.this.getSystemService(Context.TELEPHONY_SERVICE);
    		SplashScreen.udid = tel.getDeviceId();
        }
        
        if (sett.getSync() == 1)
        	buttonSync.setVisibility(View.VISIBLE);
        else
        	buttonSync.setVisibility(View.GONE);
        
        buttonSync.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				confirmDialog();
			}
		});
        setup();
        MainController.tvTitlu = new TextView(MainController.this);
        MainController.tvTitlu = (TextView) findViewById(R.id.textView1);
        addActivities();
        
   

	}
	
	private void addActivities() {
		final TabSpec dateleMele = tabHost.newTabSpec(getString(R.string.i437));
        // setting Title and Icon for the Tab
        dateleMele.setIndicator(getString(R.string.i437), getResources().getDrawable(R.drawable.datele_mele_tab));
        Intent dateleMeleIntent = new Intent(this, TabGroup1Activity.class);
        dateleMele.setContent(dateleMeleIntent);
        
        // Tab for Asigurari
        final TabSpec incheieAsigurare = tabHost.newTabSpec(getString(R.string.i436));
        incheieAsigurare.setIndicator(getString(R.string.i436), getResources().getDrawable(R.drawable.incheie_asigurare_tab));
        Intent incheieAsigurareaIntent = new Intent(this, TabGroup2Activity.class);
        incheieAsigurare.setContent(incheieAsigurareaIntent);
        
        // Tab for Alerte
        final TabSpec alerte = tabHost.newTabSpec(getString(R.string.i438));
        alerte.setIndicator(getString(R.string.i438), getResources().getDrawable(R.drawable.alerte_tab));
        Intent alerteIntent = new Intent(this, VeziAlerte.class);
        alerte.setContent(alerteIntent);
        
        // Tab for Altele
        final TabSpec altele = tabHost.newTabSpec(getString(R.string.i439));
        altele.setIndicator(getString(R.string.i439), getResources().getDrawable(R.drawable.altele_tab));
        Intent alteleIntent = new Intent(this, TabGroup3Activity.class);
        altele.setContent(alteleIntent);
        
   
        tabHost.addTab(incheieAsigurare);
        tabHost.addTab(dateleMele); //adaugare tab dateleMele 
        tabHost.addTab(alerte);
        tabHost.addTab(altele);
		tabHost.setCurrentTabByTag(getString(R.string.i436));
	}

	
	private void setup() {
		header = (RelativeLayout)findViewById(R.id.header);
		content = (RelativeLayout)findViewById(R.id.tabLayout);
		contentViewOverlay = (RelativeLayout)findViewById(R.id.contentOverlay);
		headerTitle = (TextView)findViewById(R.id.headerTitle);
		mapActivityTitles = new HashMap<String, String>();
		createGestureEventListener();
		bindHeaderView();		
		bindBackButton();
		bindContentViewOverlayOnTouchWhenMenuOpened();
		bindButtons();
		tabHost.setOnTabChangedListener(new OnTabChangeListener()
		{
		    @Override
		    public void onTabChanged(String className) {
		    	try{
		    		final View tabContent = tabHost.findViewById(android.R.id.tabcontent);
			    	if(tabContent!=null){
			    		Animation fadeIn = AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.fade_in);
			    	    tabContent.startAnimation(fadeIn);
			    	    headerTitle.startAnimation(fadeIn);
			    	}
		    	}catch(Exception e){
		    		Log.d(TAG, "onTabChanged exception: "+e.toString());
		    	}
		    }
		});
	}
	
	private void createGestureEventListener() {
		gestureEventListenerHeaderView = new MainControllerGestureEventListener() {
			@Override
			public void onSwipeRight() {
				if(!isMenuOpened())
					openMenu();
			}
			@Override
			public void onSwipeLeft() {
				if(isMenuOpened())
					closeMenu();
			}
			@Override
			public void onSwipeTop() {}
			@Override
			public void onSwipeBottom() {}
			@Override
			public void tapUp() {}
			@Override
			public void onScroll(float fromX, float fromY, float toX, float toY, float movedInX, float movedInY) {}
		};
		gestureScannerHeaderView = new GestureDetector(getApplicationContext(), new HeaderGestureListener(gestureEventListenerHeaderView));
		gestureEventListenerMenuButton = new MainControllerGestureEventListener() {
			@Override
			public void onSwipeRight() {
				if(!isMenuOpened())
					openMenu();
			}
			@Override
			public void onSwipeLeft() {
				if(isMenuOpened())
					closeMenu();
			}
			@Override
			public void onSwipeTop() {}
			@Override
			public void onSwipeBottom() {}
			@Override
			public void tapUp() {
				if(isMenuOpened())
					closeMenu();
				else
					openMenu();
			}
			@Override
			public void onScroll(float fromX, float fromY, float toX, float toY, float movedInX, float movedInY) {}
		};
		gestureScannerBackButton = new GestureDetector(getApplicationContext(), new HeaderGestureListener(gestureEventListenerMenuButton));
	}
	
	private void bindHeaderView() {
		header.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureScannerHeaderView.onTouchEvent(event);
			}
		});
	}
	
	private void manuallySetActivityLeftMargin(int left) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        params = (RelativeLayout.LayoutParams)content.getLayoutParams();
        params.setMargins(left,0,(left*(-1)),0);
        content.setLayoutParams(params);
	}
	
	public boolean isMenuOpened() {
		return (((RelativeLayout.LayoutParams)content.getLayoutParams()).leftMargin==0) ? false:true;
	}
	
	private void bindBackButton() {
		ImageView menuButton = (ImageView)header.findViewById(R.id.menuButton);
		menuButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureScannerBackButton.onTouchEvent(event);
			}
		});
	}
	
	private void bindContentViewOverlayOnTouchWhenMenuOpened() {
		contentViewOverlay.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(isMenuOpened()){
					closeMenu();
					return false;
				}else
					return true;
			}
		});
	}
	
	public void openMenu() {
		contentViewOverlay.setVisibility(View.VISIBLE);
		Animation moveRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_to_right);
		moveRight.setAnimationListener(
	        new AnimationListener() {
	            @Override
	            public void onAnimationStart(Animation animation) {}
	            @Override
	            public void onAnimationRepeat(Animation animation) {}
	            @Override
	            public void onAnimationEnd(Animation animation) {
	            	content.clearAnimation();
	            	manuallySetActivityLeftMargin((int)(getWindowWidth()*MENU_WIDTH));
	            }
	        }
	    );
		content.startAnimation(moveRight);
	}
	
	public int getWindowWidth() {
		DisplayMetrics metrics = new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	return metrics.widthPixels;
	}
	
	public void closeMenu() {
		contentViewOverlay.setVisibility(View.GONE);
		Animation moveLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_to_left);
		manuallySetActivityLeftMargin(0);
		moveLeft.setAnimationListener(
		        new AnimationListener() {
		            @Override
		            public void onAnimationStart(Animation animation) {}
		            @Override
		            public void onAnimationRepeat(Animation animation) {}
		            @Override
		            public void onAnimationEnd(Animation animation) {
		            	content.clearAnimation();
		            }
		        }
		    );
		content.startAnimation(moveLeft);
	}
	
	private void bindButtons() {
		btnAsigurari = (Button)findViewById(R.id.incheie_asigurare);
		btnDate = (Button)findViewById(R.id.datele_mele);
		btnAlerte = (Button)findViewById(R.id.alertele_mele);
		btnInfo = (Button) findViewById(R.id.alte_informatii);
		btnAsigurari.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AltePersoane.tipDate="Asigurare";
		    	MasinileMele.tipDate="Asigurare";
		    	LocuinteleMele.tipDate="Asigurare";
            	closeMenuAndStartIntent(getString(R.string.i436));
            	}
		});
		btnDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AltePersoane.tipDate="";
		    	MasinileMele.tipDate="";
		    	LocuinteleMele.tipDate="";
		    	closeMenuAndStartIntent(getString(R.string.i437));
			}
		});
		btnAlerte.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				closeMenuAndStartIntent(getString(R.string.i438));
			}
		});
		btnInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buttonNotificari.setVisibility(View.GONE);
            	MasinileMele.autovehiculCurent = new YTOAutovehicul();
              	MasinileMele.tipDate="Valabilitate";
            	closeMenuAndStartIntent(getString(R.string.i439));
			}
		});
		
		
	}
	
	public void confirmDialog () { //functie pop up
	     final Dialog dialog=new Dialog(MainController.this);
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog);
	     
	     TextView tvPop,tvContinut;
	     //ImageView imgTitlu;
	     Button btn_nu,btn_da;
	     
	     tvPop = (TextView)dialog.findViewById (R.id.text_titlu);
	     tvContinut = (TextView)dialog. findViewById (R.id.text_continut);
	     //imgTitlu = (ImageView)dialog. findViewById (R.id.imagine_titlu);
	     
	     tvPop.setText(MainController.this.getResources().getString(R.string.i225));
	     tvContinut.setText (MainController.this.getResources().getString(R.string.i227));
	     TextView textHeader;
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     textHeader = (TextView) dialog.findViewById(R.id.text_vrei_sa_stergi);
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
   	 textHeader.setTextColor(getResources().getColor(R.color.rosu_ter));
	     textHeader.setText(getString(R.string.i807));

	     btn_nu=(Button)dialog.findViewById(R.id.button_nu);
	     btn_nu.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	        onResume();
	     }
	     });

	     
	     btn_da=(Button)dialog.findViewById(R.id.button_da);
	     btn_da.setOnClickListener(new OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	sett.updateSync(0);
		    	buttonSync.setVisibility(View.GONE);
		    	new RecupereazaDateWebService().execute(null, null, null);
		    	dialog.dismiss();
	            }	
		     });
	   dialog.show();
	}
	
	private class RecupereazaDateWebService extends AsyncTask<Void, Void, Void> {
		public RecupereazaDateWebService() {
			// TODO Auto-generated constructor stub
		}
		@Override
		protected void onPreExecute(){  
			super.onPreExecute();
			dlg = new ProgressDialog(MainController.this);
			dlg.setMessage(MainController.this.getResources().getString(R.string.i434));
			dlg.setCancelable(false);
			dlg.show();
		}
		@Override
		protected Void doInBackground(Void... arg0) {

			String xml ="<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
 "<soap:Body>" +
   "<ExistingClient xmlns='http://tempuri.org/'>" +
     "<udid>"+sett.getDeviceId()+"</udid>" +
   "</ExistingClient>" +
 "</soap:Body>" +
"</soap:Envelope>";
			
			
			String url = GetObiecte.link+"sync.asmx";
			String soapAction = "http://tempuri.org/ExistingClient";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			if (resp.equals("")) {
				sett.updateSync(1);
				buttonSync.setVisibility(View.VISIBLE);
			}
			else {
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();
					XMLHelperExistingClient myXmlHelper = new XMLHelperExistingClient();
					xr.setContentHandler(myXmlHelper);
					InputSource is = new InputSource(new StringReader(resp)); 
					xr.parse(is);
					StringBuilder raspuns[] = myXmlHelper.getParsedData();
					if (!raspuns[0].equals("")) {
						String JSONText = raspuns[0].toString();
						YTOPersoana persoana = new YTOPersoana();
						persoana.proprietar="da";
						    try {
								JSONObject jo = new JSONObject(JSONText);
								persoana.nume = jo.getString("Nume");
								persoana.codUnic = jo.getString("CodUnic");
								persoana.telefon = jo.getString("Telefon");
								persoana.email = jo.getString("Email");
								persoana.judet = jo.getString("Judet");
								persoana.localitate = jo.getString("Localitate");
								persoana.adresa = jo.getString("Adresa");
								persoana.dataPermis = jo.getString("AnPermis");
								persoana.codCaen = jo.getString ("CodCaen");
						    } catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						if (persoana.codUnic.length()>10) persoana.tipPersoana="fizica";
						else persoana.tipPersoana="juridica";
						Boolean update = false;
						if (persoana.tipPersoana.equals("fizica")){ 
							if (GetObiecte.persoanaFizica.isDirty) {persoana.idIntern = GetObiecte.persoanaFizica.idIntern; update = true;}
							else persoana.idIntern= UUID.randomUUID().toString();}
						else if (persoana.tipPersoana.equals("juridica")){ 
							if (GetObiecte.persoanaJuridica.isDirty) {persoana.idIntern = GetObiecte.persoanaJuridica.idIntern; update = true;}
							else persoana.idIntern= UUID.randomUUID().toString();}
						JSONText = persoana.persoanaToJSON(persoana);
						if (update==false)
						dbConnector.insertObiectAsigurat(persoana.idIntern,
				    			  "1",
				    			  JSONText);
						else 
							dbConnector.updateObiectAsigurat(persoana.idIntern,
					    			  "1",
					    			  JSONText);
						GetObiecte.getPersoane(dbConnector);
						//add to database
					} 
					if (!raspuns[1].equals("[]")){
						String JSONText = raspuns[1].toString();
						JSONArray inputArray = null;
				        try {
							inputArray = new JSONArray(JSONText);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						for(int i=0;i<inputArray.length();i++)
						{
							try {
								JSONObject jo = inputArray.getJSONObject(i);
								YTOAutovehicul autovehicul = new YTOAutovehicul();
								autovehicul.marcaAuto = jo.getString("Marca");
								autovehicul.modelAuto = jo.getString("Model");
								autovehicul.categorieAuto = Integer.parseInt(jo.getString("Categorie"));
								autovehicul.subcategorieAuto = jo.getString("Subcategorie");
								autovehicul.judet = jo.getString("Judet");
								autovehicul.localitate = jo.getString("Localitate");
								autovehicul.nrInmatriculare = jo.getString("NrInmatriculare");
								autovehicul.serieSasiu = jo.getString("SerieSasiu");
								autovehicul.cm3 = Integer.parseInt(jo.getString("CC"));
								autovehicul.putere = Integer.parseInt(jo.getString("Putere"));
								autovehicul.nrLocuri = Integer.parseInt(jo.getString("NrLocuri"));
								autovehicul.masaMaxima = Integer.parseInt(jo.getString("MasaMax"));
								autovehicul.anFabricatie = Integer.parseInt(jo.getString("AnFabricatie"));
								autovehicul.serieCiv = jo.getString("CI");
								autovehicul.destinatieAuto = jo.getString("DestinatieAuto");
								autovehicul.combustibil = jo.getString("Combustibil");
								autovehicul.inLeasing = (jo.getString("Leasing").equals("false") ? "nu" : "da");
								autovehicul.firmaLeasing = jo.getString("FirmaLeasing");
								JSONText = autovehicul.autovehiculToJSON(autovehicul);	
								autovehicul.idIntern = jo.getString("IdIntern");
								if (autovehicul.idIntern.length()==0)
									autovehicul.idIntern = UUID.randomUUID().toString();
								dbConnector.insertObiectAsigurat(autovehicul.idIntern,
						    			  "2",
						    			  JSONText
						    			  );
								
								String JSONAlerte = jo.getString("Alerte");
								if (!JSONAlerte.equals("[]")){
								JSONArray alerteArray = null;
						        try {
									alerteArray = new JSONArray(JSONAlerte);
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						        for(int j=0;j<alerteArray.length();j++)
								{
									try {
										JSONObject js = alerteArray.getJSONObject(j);
										YTOAlerta alerta = new YTOAlerta();
										alerta.idExtern=-1;
										alerta.esteRata="";
										alerta.numarTotalRate=-1;
										alerta.numarRata=-1;
										alerta.idObiect = autovehicul.idIntern;
										alerta.idIntern = js.getString("IdIntern");
										alerta.tipAlerta = Integer.parseInt(js.getString("TipAlerta"));
										alerta.dataAlerta = js.getString("DataAlerta");
										alerta.dataCreare = "";
										String toJSON = alerta.alertaToJSON();	
										if (alerta.idIntern.length()==0 || alerta.idIntern.equals(alerta.idObiect))
											alerta.idIntern = UUID.randomUUID().toString();
										dbConnector.insertObiectAsigurat(alerta.idIntern,
								    			  "5",
								    			  toJSON
								    			  );
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								}	
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						GetObiecte.getAutovehicule(dbConnector);
						//from json
						//add to database
					}
					if (!raspuns[2].equals("[]")){
						String JSONText = raspuns[2].toString();
						JSONArray inputArray = null;
				        try {
							inputArray = new JSONArray(JSONText);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						for(int i=0;i<inputArray.length();i++)
						{
							try {
								JSONObject jo = inputArray.getJSONObject(i);
								YTOLocuinta locuinta = new YTOLocuinta();
								locuinta.idIntern = jo.getString("IdIntern");
								locuinta.judet = jo.getString("Judet");
								locuinta.localitate = jo.getString("Localitate");
								locuinta.adresa= jo.getString("Adresa");
								locuinta.tipLocuinta = jo.getString("TipLocuinta");
								locuinta.structuraLocuinta = jo.getString("StructuraLocuinta");
								locuinta.regimInaltime = Integer.parseInt(jo.getString("RegimInaltime"));
								locuinta.etaj = Integer.parseInt(jo.getString("Etaj"));
								locuinta.anConstructie = Integer.parseInt(jo.getString("AnConstructie"));
								locuinta.nrCamere = Integer.parseInt(jo.getString("NrCamere"));
								locuinta.suprafataUtila = Integer.parseInt(jo.getString("SuprafataUtila"));
								locuinta.nrLocatari = Integer.parseInt(jo.getString("NrLocatari"));
								locuinta.tipGeam = jo.getString("TipGeam");
								locuinta.areAlarma = jo.getString("AreAlarma");
								locuinta.areGrilajeGeam = jo.getString("AreGrilajeGeam");
								locuinta.detectieIncendiu = jo.getString("DetectieIncendiu");
								locuinta.arePaza= jo.getString("ArePaza");
								locuinta.zonaIzolata = jo.getString("ZonaIzolata");
								locuinta.locuitPermanent = jo.getString("LocuitPermanent");
								locuinta.clauzaFurtBunuri = jo.getString("ClauzaFurtBunuri");
								locuinta.clauzaApaConducta = jo.getString("ClauzaApaConducta");
								locuinta.areTeren = jo.getString("AreTeren");
								locuinta.modEvaluare = jo.getString("ModEvaluare");
								locuinta.nrRate = Integer.parseInt(jo.getString("NrRate"));
								locuinta.sumaAsigurata = Integer.parseInt(jo.getString("SumaAsigurata"));
								locuinta.sumaAsigurataRC = Integer.parseInt(jo.getString("SumaAsigurataRC"));
								JSONText = locuinta.locuintaToJSON(locuinta);	
								if (locuinta.idIntern.length()==0)
									locuinta.idIntern = UUID.randomUUID().toString();
								dbConnector.insertObiectAsigurat(locuinta.idIntern,
						    			  "3",
						    			  JSONText
						    			  );
								
								String JSONAlerte = jo.getString("Alerte");
								if (!JSONAlerte.equals("[]")){
								JSONArray alerteArray = null;
						        try {
									alerteArray = new JSONArray(JSONAlerte);
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						        for(int j=0;j<alerteArray.length();j++)
								{
									try {
										JSONObject js = alerteArray.getJSONObject(j);
										YTOAlerta alerta = new YTOAlerta();
										alerta.idExtern=-1;
										alerta.esteRata="";
										alerta.numarTotalRate=-1;
										alerta.numarRata=-1;
										alerta.idObiect = locuinta.idIntern;
										alerta.idIntern = js.getString("IdIntern");
										alerta.tipAlerta = Integer.parseInt(js.getString("TipAlerta"));
										alerta.dataAlerta = js.getString("DataAlerta");
										alerta.dataCreare = "";
										String toJSON = alerta.alertaToJSON();	
										if (alerta.idIntern.length()==0 || alerta.idIntern.equals(alerta.idObiect))
											alerta.idIntern = UUID.randomUUID().toString();
										dbConnector.insertObiectAsigurat(alerta.idIntern,
								    			  "5",
								    			  toJSON
								    			  );
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								}	
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						GetObiecte.getLocuinte(dbConnector);
						//from json
						//add to database
					}
					if (!raspuns[3].equals("[]")){
						String JSONText = raspuns[3].toString();
						JSONArray inputArray = null;
				        try {
							inputArray = new JSONArray(JSONText);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						for(int i=0;i<inputArray.length();i++)
						{
							try {
								JSONObject jo = inputArray.getJSONObject(i);
								YTOPersoana persoana = new YTOPersoana();
								persoana.nume = (!jo.getString("Nume").equals("null") ? jo.getString("Nume") : "");
								persoana.tipPersoana = (!jo.getString("TipPersoana").equals("null") ? jo.getString("TipPersoana") : "");
								persoana.idIntern = (!jo.getString("IdIntern").equals("null") ? jo.getString("IdIntern") : "");
								persoana.codUnic = (!jo.getString("CodUnic").equals("null") ? jo.getString("CodUnic") : "");
								persoana.telefon = (!jo.getString("Telefon").equals("null") ? jo.getString("Telefon") : "");
								persoana.email = (!jo.getString("Email").equals("null") ? jo.getString("Email") : "");
								persoana.judet = (!jo.getString("Judet").equals("null") ? jo.getString("Judet") : "");
								persoana.localitate = (!jo.getString("Localitate").equals("null") ? jo.getString("Localitate") : "");
								persoana.adresa = (!jo.getString("Adresa").equals("null") ? jo.getString("Adresa") : "");
								persoana.dataPermis = (!jo.getString("AnPermis").equals("null") ? jo.getString("AnPermis") : "");
								persoana.codCaen = (!jo.getString("CodCaen").equals("null") ? jo.getString("Nume") : "");
								persoana.proprietar = "nu";

								JSONText = persoana.persoanaToJSON(persoana);	
								if (persoana.idIntern.length()==0)
									persoana.idIntern = UUID.randomUUID().toString();
								dbConnector.insertObiectAsigurat(persoana.idIntern,
						    			  "1",
						    			  JSONText
						    			  );
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						GetObiecte.getPersoane(dbConnector);
						//from json
						//add to database
					}
				} catch (Exception ex) {						
				}
				
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(final Void unused){			
			super.onPostExecute(null);
			GetObiecte.getAlerte(dbConnector);
			MainController.setBadge();
			if (dlg.isShowing())
				dlg.dismiss();
	    	onResume();
	}
	}
	
	private void startIntent(final String tag) {
		final View tabContent = tabHost.findViewById(android.R.id.tabcontent);
    	if( tabContent != null ){
    		Animation fadeOut = AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.fade_out);
   			tabContent.startAnimation(fadeOut);
   			headerTitle.startAnimation(fadeOut);
    	}
    	tabHost.setCurrentTabByTag(tag);
    	headerTitle.setText(mapActivityTitles.get(tag));
	}
	
	private void closeMenuAndStartIntent(String tag) {
		closeMenu();
		if(!tabHost.getCurrentTabTag().equals(tag))
			startIntent(tag);
	}
	
	@Override
	public void onBackPressed() {
		if(isMenuOpened())
			closeMenu();
		else
			exitDialog();
	}
	
	public static void setBadge()
	{
	 	int nr = GetObiecte.getNrAlerteFromInterval();
	 	if (nr==0)
	 	{
//	 		badge.setVisibility(View.INVISIBLE);
	 		txtCount.setText("");
	 	}
	 	else 
	 	{
	 		txtCount.setVisibility(View.VISIBLE); 
//	 		badge.setVisibility(View.VISIBLE);
	 		txtCount.setText(nr+"");
	 	}
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
