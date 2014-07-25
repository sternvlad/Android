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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterPage extends Activity {
	EditText edtEmail,edtEmailConfirmed,edtPassword,edtPasswordConfirmed;
	ImageView imgWrongEmail,imgWrongPassword,imgWrongEmailConfirmed;
	Button btnRegister;
	AppSettings sett;
	ProgressDialog dlg;
	
	public void onCreate(Bundle savedInstanceState) {
    	Window window = getWindow(); 
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
		setContentView(R.layout.register_page);
		sett = AppSettings.get(getParent());
		
		MainController.tvTitlu.setText(getString(R.string.i901));
		
		edtEmail = (EditText) findViewById(R.id.edt_mail_login);
		edtEmailConfirmed = (EditText) findViewById(R.id.edt_mail_login_confirm);
		edtPassword = (EditText) findViewById(R.id.edt_parola_login);
		edtPasswordConfirmed = (EditText) findViewById(R.id.edt_parola_login_confirm);
		imgWrongEmail = (ImageView) findViewById(R.id.wrong_email);
		imgWrongEmailConfirmed = (ImageView) findViewById(R.id.wrong_email_confirmed);
		imgWrongPassword = (ImageView) findViewById(R.id.wrong_password_confirmed);
		btnRegister = (Button) findViewById(R.id.btn_register);
		
		
		edtEmail.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if (!YTOUtils.eMailValidation(edtEmail.getText().toString()))
					imgWrongEmail.setVisibility(View.VISIBLE);
				else imgWrongEmail.setVisibility(View.GONE);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		edtEmail.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			Boolean checkEmail = false;
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) 
		    {
		        if (hasFocus == true){
		          checkEmail = true;
		        }else{
		        	if (checkEmail && YTOUtils.eMailValidation(edtEmail.getText().toString()))
		        		new CheckEmail2().execute (null,null,null);	        		
		        }
		    }
		});
		
		edtEmailConfirmed.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (!YTOUtils.eMailValidation(edtEmailConfirmed.getText().toString()) || !edtEmail.getText().toString().equals(edtEmailConfirmed.getText().toString()))
					imgWrongEmailConfirmed.setVisibility(View.VISIBLE);
				else imgWrongEmailConfirmed.setVisibility(View.GONE);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		edtPasswordConfirmed.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (!edtPassword.getText().toString().equals(edtPasswordConfirmed.getText().toString()))
					imgWrongPassword.setVisibility(View.VISIBLE);
				else imgWrongPassword.setVisibility(View.GONE);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!YTOUtils.eMailValidation(edtEmail.getText().toString()))
				{
					edtEmail.requestFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(edtEmail, InputMethodManager.SHOW_IMPLICIT);
				}
				else if (!YTOUtils.eMailValidation(edtEmailConfirmed.getText().toString()) || !edtEmail.getText().toString().equals(edtEmailConfirmed.getText().toString()))
				{
					edtEmailConfirmed.requestFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(edtEmailConfirmed, InputMethodManager.SHOW_IMPLICIT);
				}
				else if (!edtPassword.getText().toString().equals(edtPasswordConfirmed.getText().toString()))
				{
					edtPassword.requestFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(edtPassword, InputMethodManager.SHOW_IMPLICIT);
				}
				else if (!isOnline())
				{
					errorDialog ("",0);
				}
				else 
				{
					new CheckEmail().execute(null,null,null);
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
	     if (i==1)
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
	
	private class CheckEmail extends AsyncTask<Void, Void, Void> {
		Boolean isOk = false;
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
    "<VerificareEmail xmlns='http://tempuri.org/'>" +
      "<username>vreaurca</username>" +
      "<password>123</password>" +
      "<email>"+edtEmail.getText().toString()+"</email>" +
    "</VerificareEmail>" +
  "</soap:Body>" +
"</soap:Envelope>";
			
			
			String url = GetObiecte.link + "sync.asmx";
			String soapAction = "http://tempuri.org/VerificareEmail";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			if (resp.equals("")) {
				dlg.dismiss();
			}
			else {
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();
					XMLHelperCheckEmail myXmlHelper = new XMLHelperCheckEmail();
					xr.setContentHandler(myXmlHelper);
					InputSource is = new InputSource(new StringReader(resp)); 
					xr.parse(is);
					StringBuilder raspuns = myXmlHelper.getParsedData();
					if (raspuns != null) {
						if (raspuns.toString().equals("true"))
							isOk = true;
					}
				}catch (Exception ex) {
					dlg.dismiss();							
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(final Void unused){	
			if (isOk)
			{
				new Register().execute (null,null,null);
			}
			else 
			{
				dlg.dismiss();
				errorDialog("Adresa de email apartine unui cont ",0);
			}
		}
	}
	
	private class Register extends AsyncTask<Void, Void, Void> {
		String mesajOK = "";
		@Override
		protected void onPreExecute(){  

		}
		@Override
		protected Void doInBackground(Void... arg0) {

			String xml ="<?xml version='1.0' encoding='utf-8'?>" +
"<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<soap:Body>" +
    "<AccountRegister xmlns='http://tempuri.org/'>" +
      "<username>vreaurca</username>" +
      "<password>123</password>" +
      "<account_username>"+edtEmail.getText().toString()+"</account_username>" +
      "<account_password>"+edtPassword.getText().toString()+"</account_password>" +
      "<account_telefon>"+sett.getTelefonComanda()+"</account_telefon>" +
    "</AccountRegister>" +
  "</soap:Body>" +
"</soap:Envelope>";
			
			
			String url = GetObiecte.link + "sync.asmx";
			String soapAction = "http://tempuri.org/AccountRegister";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			if (resp.equals("")) {
				dlg.dismiss();
			}
			else {
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();
					XMLHelperAcountRegister myXmlHelper = new XMLHelperAcountRegister();
					xr.setContentHandler(myXmlHelper);
					InputSource is = new InputSource(new StringReader(resp)); 
					xr.parse(is);
					StringBuilder raspuns = myXmlHelper.getParsedData();
					if (raspuns != null) {
						mesajOK = raspuns.toString();
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
			if (dlg.isShowing() && !mesajOK.equals("")){
				dlg.dismiss();
				errorDialog(mesajOK,1);
			}
			else {
				dlg.dismiss();
				errorDialog("A aparut o eroare,te rugam sa incerci din nou bla",0);
			}
		}
	}
	
	private class CheckEmail2 extends AsyncTask<Void, Void, Void> {
		Boolean isOk = false;
		@Override
		protected void onPreExecute(){  
		}
		@Override
		protected Void doInBackground(Void... arg0) {

			String xml ="<?xml version='1.0' encoding='utf-8'?>" +
"<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
  "<soap:Body>" +
    "<VerificareEmail xmlns='http://tempuri.org/'>" +
      "<username>vreaurca</username>" +
      "<password>123</password>" +
      "<email>"+edtEmail.getText().toString()+"</email>" +
    "</VerificareEmail>" +
  "</soap:Body>" +
"</soap:Envelope>";
			
			
			String url = GetObiecte.link + "sync.asmx";
			String soapAction = "http://tempuri.org/VerificareEmail";
			String resp = HttpHelper.callWebService( url, soapAction,xml);
			if (resp.equals("")) {

			}
			else {
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();
					XMLHelperCheckEmail myXmlHelper = new XMLHelperCheckEmail();
					xr.setContentHandler(myXmlHelper);
					InputSource is = new InputSource(new StringReader(resp)); 
					xr.parse(is);
					StringBuilder raspuns = myXmlHelper.getParsedData();
					if (raspuns != null) {
						if (raspuns.toString().equals("true"))
							isOk = true;
					}
				}catch (Exception ex) {
								
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(final Void unused){	
			if (!isOk){
				Toast msg = Toast.makeText(getParent(), "Adresa de email apartine unui cont ", Toast.LENGTH_LONG);
				msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
				msg.show();
				imgWrongEmail.setVisibility(View.VISIBLE);
			}else
				imgWrongEmail.setVisibility(View.GONE);
		}
	}

}
