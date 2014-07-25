package com.stern.Asigurare;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoadingCalatorie extends Activity {
	
	ImageView imageLoading;
	public static ArrayList<YTOPersoana> persoane;
	public static ArrayList<RaspunsCalatorie> oferta;
	DatabaseConnector dbConnector;
	AppSettings sett;
	StringBuilder jsonPersoane;
	TextView tv1,tv2;
	LinearLayout ln1;
	CallWebServiceCalatorie callCalatorie;//callwebservicecalatorie
	final int[] imgid = { R.drawable.loading_calatorie1,
			R.drawable.loading_calatorie2, R.drawable.loading_calatorie3,
			R.drawable.loading_calatorie4 };

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
			setContentView(R.layout.loading_screen_calatorie);
			imageLoading = (ImageView) findViewById (R.id.loading_img);	
			imageLoading.setImageResource(R.drawable.loading_calatorie1);
			
		
			
	    	
			
			sett = AppSettings.get(this);
			callCalatorie = new CallWebServiceCalatorie();
			new Handler().postDelayed(new Runnable(){ 
				
			    public void run() { 
			    	callCalatorie.execute();
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
	            LoadingCalatorie.this.updateUI();
	        }
	        public void sleep(long delayMillis){
	            this.removeMessages(0);
	            sendMessageDelayed(obtainMessage(0), delayMillis);
	        }
	    };
	    public void updateUI(){
	    	if (!callCalatorie.completed){
	            refreshHandler.sleep(400);
	            if(i<imgid.length){
	                imageLoading.setImageResource(imgid[i]);
	                i++;
	                if (i>3) i=0;
	            }
	        }
	    	else if (callCalatorie.mesaj.equals(""))
			{				
				LoadingCalatorie.this.finish(); 
				Intent calcCalatorie = new Intent(getParent(), CalculatieCalatorie.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("CalculatieCalatorie", calcCalatorie);
			} 
			else 
			{
				LoadingCalatorie.this.finish();
				finish();
				sett.updateMesajEroare(callCalatorie.mesaj);
				Intent asigCal = new Intent(getParent(), AsigurareCalatorie.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("AsigurareCalatorie", asigCal);
			}
	    }
	    
	    private class CallWebServiceCalatorie extends AsyncTask<Integer, Integer, Boolean> {
	    	String soapAction="http://tempuri.org/CallCalculTravel";
	    	String xml;
	    	String s;
	    	public String mesaj="";
	    	public Boolean completed=false;
	    	String url;
	    	
	    	
	    	
	    	public CallWebServiceCalatorie() {
	    		jsonPersoane = new StringBuilder();
	    		jsonPersoane.delete(0, jsonPersoane.length());
	    		jsonPersoane.append("[");
	    		for (int i=0;i<AltePersoaneCalatorie.calatori.size();i++)
		    	{
		    		String toJson=AltePersoaneCalatorie.calatori.get(i).toJSONCalator();
	    			jsonPersoane.append(toJson);
		    		if (i==AltePersoaneCalatorie.calatori.size()-1)
		    			jsonPersoane.append("]");
		    		else jsonPersoane.append(",");
	    		}
	    		url=GetObiecte.link +"travel.asmx";
	    		
	    		String dataInceput = AsigurareCalatorie.dataInceput;
	    		String year = dataInceput.substring(6,dataInceput.length());
	    		String mm = dataInceput.substring(3,5);
	    		String dd = dataInceput.substring(0,2);
	    		dataInceput = year+"-"+mm+"-"+dd;
	    		
	    		xml="<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
	      "<soap:Body>" +
	        "<CallCalculTravel xmlns='http://tempuri.org/'>" +
	          "<user>vreaurca</user>" +
	          "<password>123</password>" +
	          "<tip_persoana>fizica</tip_persoana>" +
	          "<udid>"+sett.getDeviceId()+"</udid>" +
	          "<data_inceput>"+dataInceput+"</data_inceput>" +
	          "<tranzit>"+AsigurareCalatorie.tranzit+"</tranzit>" +
	          "<numar_zile>"+sett.getNrZileCalatorie()+"</numar_zile>" +
	          "<tara_destinatie>"+sett.getTaraDestinatie()+"</tara_destinatie>" +
	          "<nationalitate>"+"Romania"+"</nationalitate>" +
	          "<program_asigurare>"+sett.getSumaAsigurataCalatorie()+"</program_asigurare>" +
	          "<scop_calatorie>"+sett.getScopCalatorie()+"</scop_calatorie>" +
	          "<tip_calatorie>"+(AltePersoaneCalatorie.calatori.size()==1 ? "individual" :"grup")+"</tip_calatorie>" +
	          "<jsonPersoane>"+jsonPersoane.toString()+"</jsonPersoane>" +
	          "<judet>"+AltePersoaneCalatorie.calatori.get(0).judet+"</judet>" +
	          "<localitate>"+AltePersoaneCalatorie.calatori.get(0).localitate+"</localitate>" +
	          "<platforma>"+"Android"+"</platforma>" +
	        "</CallCalculTravel>" +
	      "</soap:Body>" +
	    "</soap:Envelope>";
	    	}

	    	@Override
	    	protected void onPreExecute(){
	    		super.onPreExecute();
	    		completed=false;
	    	}

	    	@Override
	    	protected Boolean doInBackground(Integer... params) {
	    	    s=HttpHelper.callWebService( url, soapAction,xml);
	    	    if (s.equals("")){
	    	    	mesaj="Nu s-a realizat conexiunea cu serviciul de calculatie. Va rugam verificati conexiunea la Internet si reveniti!";
	    	    	completed=true;
	    			return false;
	    	    }
	    		else {
	    			try {
	    				SAXParserFactory spf = SAXParserFactory.newInstance();
	    				SAXParser sp = spf.newSAXParser();
	    				XMLReader xr = sp.getXMLReader();
	    				XMLHelperCalatorie myXmlHelper = new XMLHelperCalatorie();
	    				xr.setContentHandler(myXmlHelper);
	    				InputSource is = new InputSource(new StringReader(s)); 
	    				xr.parse(is);
	    				oferta = new  ArrayList<RaspunsCalatorie>();
	    				oferta.clear();
	    				try{
	    				oferta = myXmlHelper.getParsedData();
	    				if (oferta.size()==1){
	    					if (!oferta.get(0).getEroare_ws().equals("success") && !oferta.get(0).getEroare_ws().equals("succes")){
	    						mesaj = oferta.get(0).getEroare_ws();
	    						completed=true;
	    					}
	    				}
	    				else if (oferta.size()==0){
	    						mesaj = "S-a produs o eroare la conexiunea cu Serviciul de calculatie! Va rugam verificati datele si apoi reveniti!";
	    					completed=true;
	    				}
	    				} catch (Exception ex) {						
		    				mesaj = "S-a produs o eroare la conexiunea cu Serviciul de calculatie! Va rugam reveniti!";
		    				completed=true;
		    			}
	    				completed=true;
	    			} catch (Exception ex) {						
	    				mesaj = "S-a produs o eroare la conexiunea cu Serviciul de calculatie! Va rugam reveniti!";
	    				completed=true;
	    			}
	    	    }
	    	    return true;
	    	}

	    	@Override
	    	protected void onPostExecute(Boolean result) {
	    	    super.onPostExecute(result);

	    	}
	    	
	    	protected void onProgressUpdate(Integer... values) {
	            // set the current progress of the progress dialog
	            super.onProgressUpdate(values);
	        }
	    	
	    }	

	  
}


    
   