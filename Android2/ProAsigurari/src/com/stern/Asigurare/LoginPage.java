package com.stern.Asigurare;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPage extends Activity {
	EditText edtMail,edtPassword;
	Button btnLogin,btnForgotPass;
	ProgressDialog dlg;
	AppSettings sett;
	public void onCreate(Bundle savedInstanceState) {
    	Window window = getWindow(); 
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);
		
		sett = AppSettings.get(getParent());
		
		MainController.tvTitlu.setText(getString(R.string.i900));
		edtMail = (EditText) findViewById(R.id.edt_mail_login);
		edtPassword = (EditText) findViewById(R.id.edt_parola_login);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnForgotPass = (Button) findViewById(R.id.btn_forgot);
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!YTOUtils.eMailValidation(edtMail.getText().toString()))
				{
					edtMail.requestFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(edtMail, InputMethodManager.SHOW_IMPLICIT);
				}
				else if (edtPassword.getText().toString().equals(""))
				{
					edtPassword.requestFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(edtPassword, InputMethodManager.SHOW_IMPLICIT);
				}
				else if (!isOnline())
				{
					errorDialog("", 0);
				}
				else
				{
					new Login().execute(null,null,null);
				}
					
			}
		});
		
		btnForgotPass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!YTOUtils.eMailValidation(edtMail.getText().toString()))
				{
					errorDialog("Avem nevoie de adresa ta de e-mail pentru a-ti trimite parola bla", 0);
				}
				else
				{
					new ForgotPassword().execute (null,null,null);
				}
			}
		});
		
		
	}
	
	@SuppressLint("ResourceAsColor")
	public void errorDialog (String mesaj,final int i) 
	{ //eroare
	     final Dialog dialog=new Dialog(getParent());
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog_error);
	     TextView tvContinut;
	     TextView textHeader;
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     textHeader = (TextView) dialog.findViewById(R.id.text_eroare_header);
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
	   //  ImageView imgContinut;
	     if (i==1 || i==2)
	     {
	    	 textHeader.setText("INFO");
	    	 textHeader.setTextColor(getResources().getColor(R.color.verde));
	     }
	     tvContinut = (TextView)dialog.findViewById (R.id.text_error);
	     Button btn_ok=(Button)dialog.findViewById(R.id.button_ok);
	     if (!mesaj.equals(""))  tvContinut.setText (mesaj);
	    //imgContinut = (ImageView)dialog. findViewById (R.id.imagine_error);
	     
	     btn_ok.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	        if (i==1)
	        	onBackPressed();
	     }
	     });

	   dialog.show();
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
	private class Login extends AsyncTask<Void, Void, Void> {
		String clientId = "0";
		@Override
		protected void onPreExecute(){  
			dlg = new ProgressDialog(getParent());
			dlg.setMessage(getString(R.string.i444));
			dlg.setCancelable(false);
			dlg.show();
		}
		@Override
		protected Void doInBackground(Void... arg0) {

			String xml ="<?xml version='1.0' encoding='utf-8'?>" +
"<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"+ 
  "<soap:Body>"+ 
    "<AccountLogin xmlns='http://tempuri.org/'>"+ 
      "<username>"+edtMail.getText().toString()+"</username>"+ 
      "<password>"+edtPassword.getText().toString()+"</password>"+ 
    "</AccountLogin>"+ 
  "</soap:Body>"+ 
"</soap:Envelope>";
			
			
			String url = GetObiecte.link + "sync.asmx";
			String soapAction = "http://tempuri.org/AccountLogin";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			if (resp.equals("")) {
				dlg.dismiss();
			}
			else {
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();
					XMLHelperLogin myXmlHelper = new XMLHelperLogin();
					xr.setContentHandler(myXmlHelper);
					InputSource is = new InputSource(new StringReader(resp)); 
					xr.parse(is);
					StringBuilder raspuns = myXmlHelper.getParsedData();
					if (raspuns != null) {
						clientId = raspuns.toString();
					} else {
						dlg.dismiss();
					}
				} catch (Exception ex) {
					dlg.dismiss();							
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(final Void unused){	
			if (dlg.isShowing() && !clientId.equals("0") && !clientId.equals("")){
				dlg.dismiss();
				errorDialog("Ai fost logat cu succes",1);
//				RijndaelCrypt cript = new RijndaelCrypt("sharedSecret");
//				String encripted = cript.encrypt(edtPassword.getText().toString().getBytes());
				sett.updateUsername(edtMail.getText().toString());
//				sett.updatePassword(encripted);
			}
			else {
				dlg.dismiss();
				errorDialog("Adresa de e-mail sau parola sunt gresite",0);
			}
		}
	}
	
	private class ForgotPassword extends AsyncTask<Void, Void, Void> {
		String mesajOk = "";
		@Override
		protected void onPreExecute(){  
			dlg = new ProgressDialog(getParent());
			dlg.setMessage(getString(R.string.i444));
			dlg.setCancelable(false);
			dlg.show();
		}
		@Override
		protected Void doInBackground(Void... arg0) {

			String xml ="<?xml version='1.0' encoding='utf-8'?>" +
"<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<soap:Body>" +
    "<AccountForgotPassword xmlns='http://tempuri.org/'>" +
      "<username>vreaurca</username>" +
      "<password>123</password>" +
      "<email>"+edtMail.getText().toString()+"</email>" +
    "</AccountForgotPassword>" +
  "</soap:Body>" +
"</soap:Envelope>";
			
			
			String url = GetObiecte.link + "sync.asmx";
			String soapAction = "http://tempuri.org/AccountForgotPassword";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			if (resp.equals("")) {
				dlg.dismiss();
			}
			else {
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();
					XMLHelperForgotPassword myXmlHelper = new XMLHelperForgotPassword();
					xr.setContentHandler(myXmlHelper);
					InputSource is = new InputSource(new StringReader(resp)); 
					xr.parse(is);
					StringBuilder raspuns = myXmlHelper.getParsedData();
					if (raspuns != null) {
						mesajOk = raspuns.toString();
					} else {
						dlg.dismiss();
					}
				} catch (Exception ex) {
					dlg.dismiss();							
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(final Void unused){	
			if (dlg.isShowing() && !mesajOk.equals("")){
				dlg.dismiss();
				errorDialog(mesajOk,2);
			}
			else {
				dlg.dismiss();
				errorDialog("A aparut o eroare,te rugam sa incerci din nou bla",0);
			}
		}
	}
}
