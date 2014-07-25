package com.stern.Asigurare;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoadingScreenLoc extends Activity {
	
	ImageView imageLoading;
	public static ArrayList<YTOPersoana> persoane;
	DatabaseConnector dbConnector;
	AppSettings sett;
	TextView tv1,tv2;
	LinearLayout ln1;
	CallWebServiceLoc callTarife = new CallWebServiceLoc();
	final int[] imgid = { R.drawable.loading_locuinta1,
			R.drawable.loading_locuinta2, R.drawable.loading_locuinta3,
			R.drawable.loading_locuinta4 };

	 int i=0;
	
	  protected void onCreate(Bundle savedInstanceState) {
//			Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//	    	int width = display.getWidth(); 
	    	Window window = getWindow(); 
//	    	if (YTOUtils.isTablet(this)) {    	
//		    	window.setGravity(Gravity.CENTER); 
//		    	window.setLayout(320, 480);
//	    	}
	    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.loading_screenloc);
			imageLoading = (ImageView) findViewById (R.id.loading_img);		

			sett=AppSettings.get(this);


			new Handler().postDelayed(new Runnable(){ 
				
			    public void run() { 
		              //Simulating a long running task
			  /* Create an Intent that will start the ProfileData-Activity. */
			    	callTarife.execute();
				}
			},100);
			
			updateUI();

	       	
		    }
	  
		@Override
		public void onResume()
		{
			super.onResume();
		}
	  
	  RefreshHandler refreshHandler=new RefreshHandler();
	    
	    @SuppressLint("HandlerLeak")
		class RefreshHandler extends Handler{
	        @Override
	        public void handleMessage(Message msg) {
	            // TODO Auto-generated method stub
	            LoadingScreenLoc.this.updateUI();
	        }
	        public void sleep(long delayMillis){
	            this.removeMessages(0);
	            sendMessageDelayed(obtainMessage(0), delayMillis);
	        }
	    };
	    public void updateUI(){
	    	if (!callTarife.completed){
	            refreshHandler.sleep(400);
	            if(i<imgid.length){
	                imageLoading.setImageResource(imgid[i]);
	                i++;
	                if (i>3) i=0;
	            }
	        }
	    	else if (callTarife.mesaj.equals(""))
			{				
				LoadingScreenLoc.this.finish(); 
				Intent calcLoc = new Intent(getParent(), CalculatieLocuinta.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("CalculatieLocuinta", calcLoc);
			} 
			else 
			{
				LoadingScreenLoc.this.finish();
				finish();
				try{
				sett.updateMesajEroare(callTarife.mesaj);
				}catch (Exception e) {
					// TODO: handle exception
					sett.updateMesajEroare("Te rugam sa te asiguri ca ai o conexiune la internet activa si sa incerci din nou.\n Iti multumim!");
				}
				Intent asigLoc = new Intent(getParent(), AsigurareLocuinta.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("AsigurareLocuinta", asigLoc);
			}
	    }

	  
}


    
   