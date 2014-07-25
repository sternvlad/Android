package com.stern.Asigurare;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

 
public class SplashScreen extends Activity {
	
	DatabaseConnector dbConnector;
	AppSettings appSett;
	public static String udid="";
	
    public void onCreate(Bundle savedInstanceState) {
    	Window window = getWindow(); 
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Bundle extras = getIntent().getExtras();
		int open  = 0;
		appSett = AppSettings.get(this);
		try{
			extras.getInt("open");
		}catch (Exception e) {
			// TODO: handle exception
		}
        Lista.tipDate="";
        RadioButtons.tipDate="";
        if (appSett.getDeviceId().equals(""))
        {
	        try{
			TelephonyManager tel =(TelephonyManager)SplashScreen.this.getSystemService(Context.TELEPHONY_SERVICE);
			udid = tel.getDeviceId();
			appSett.updateDeviceID(udid);
	        }catch (Exception e){
	        	udid = java.util.UUID.randomUUID().toString() + "-udid";
				appSett.updateDeviceID(udid);
	        }
			if (udid==null)
				udid = java.util.UUID.randomUUID().toString() + "-udid";
			appSett.updateDeviceID(udid);

        }
        appSett.updateMesajEroare("");
        appSett.updateTitluGroup1(getString(R.string.i437));
        appSett.updateTitluGroup2(getString(R.string.i436));
        appSett.updateTitluGroup4(getString(R.string.i439));
        appSett.updateEventsUntilPrompt(2);
        appSett.updateUsesUntilPrompt(appSett.getUsesUntilPrompt()-1);
        appSett.updateOpenNotification(open);
        Log.d ("udid",appSett.getDeviceId());
        appSett.updateLink(GetObiecte.link);
        appSett.updateLanguage("ro");
        
        String language = appSett.getLanguage();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
             
        dbConnector = new DatabaseConnector(SplashScreen.this);
                   
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
               
        
        Handler handler = new Handler();
        
        handler.postDelayed(new Runnable() {
 
            @Override
            public void run() {
 
                // make sure we close the splash screen so the user won't come back when it presses back key
                finish();
                Intent i = new Intent (SplashScreen.this,MainController.class);
                startActivity(i);
                
                

 
            }
 
        }, 1000); // time in milliseconds (1 second = 1000 milliseconds) until the run() method will be called
 
    }
    
}
 