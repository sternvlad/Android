package com.stern.Asigurare;

import java.util.UUID;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DespreMine extends Activity {
		 
	EditText edtNume,edtCnp,edtJudLoc,edtAdresa,edtTelefon,edtEmail,edtOperator,edtSerie;
	Button btn_renunta,btn_salveaza;
	String operator = "";
	Dialog dialog;
	AppSettings sett;
	static Boolean salveaza=true;
	String judet="",localitate="";//salveaza verifica daca salvez la onPause() in principiu cand schimb tabul trebuie salvat
	YTOPersoana persoanaFiz = new YTOPersoana();
	
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.despre_mine);
			
			sett = AppSettings.get(this);

			edtNume = (EditText)findViewById (R.id.edtNume);
			edtNume.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          ContulMeu.tooltip.setVisibility (View.VISIBLE); 
			          ContulMeu.tvTooltip.setText (getParent().getResources().getString(R.string.i238));
			        }else{
			        	edtNume.setText(YTOUtils.replaceDiacritice(edtNume.getText().toString()));
			        	ContulMeu.tvTooltip.setText ("");
			        	 ContulMeu.tooltip.setVisibility (View.GONE); 
			        }
			    }
			});


			edtCnp = (EditText)findViewById (R.id.edtCNP);
		    edtCnp.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          ContulMeu.tooltip.setVisibility (View.VISIBLE); 
			          ContulMeu.tvTooltip.setText (getParent().getResources().getString(R.string.i240));
			        }else{
			        	ContulMeu.tvTooltip.setText ("");
			        	 ContulMeu.tooltip.setVisibility (View.GONE);
			        }
			    }
			});
		    
			edtCnp.addTextChangedListener(new TextWatcher() {
	        	 
	            @Override
	            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	                // When user changed the Text
	            }
	 
	            @Override
	            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                    int arg3) {
	                // TODO Auto-generated method stub
	 
	            }
	 
	            @Override
	            public void afterTextChanged(Editable arg0) {
	            	if (edtCnp.getText().toString().length()!=13 || !YTOUtils.verificaCNP(edtCnp.getText().toString()))
		        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
		        	 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
	            }
	        });
		    

			edtJudLoc = (EditText)findViewById (R.id.edtJudLoc);	
			edtJudLoc.setOnClickListener (new OnClickListener()
			{
			    @Override
			    public void onClick(View v)
			    {
			    	if (edtCnp.getText().toString().length()!=13 || !YTOUtils.verificaCNP(edtCnp.getText().toString()))
		        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
		        	 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
			    		salveaza=false;//nu salvez cand trebuie setat judetul
			        	Lista.tipDate = "judete";
			        	Intent i = new Intent (DespreMine.this,Lista.class);
			        	i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			        	startActivity (i);
			        	
			    }
			});
			edtJudLoc.setFocusable(false);
			edtAdresa = (EditText)findViewById (R.id.edtAdresa);
			edtAdresa.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          ContulMeu.tooltip.setVisibility (View.VISIBLE); 
			          ContulMeu.tvTooltip.setText (getParent().getResources().getString(R.string.i243));
			        }else{
			        	edtAdresa.setText(YTOUtils.replaceDiacritice(edtAdresa.getText().toString()));
			        	ContulMeu.tvTooltip.setText ("");
			        	 ContulMeu.tooltip.setVisibility (View.GONE); 
			        }
			    }
			});
			edtTelefon = (EditText)findViewById (R.id.edtTelefon);
			edtTelefon.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          ContulMeu.tooltip.setVisibility (View.VISIBLE); 
			          ContulMeu.tvTooltip.setText (getParent().getResources().getString(R.string.i245));
			        }else{
			        	ContulMeu.tvTooltip.setText ("");
			        	 ContulMeu.tooltip.setVisibility (View.GONE); 
			        }
			    }
			});
			edtEmail = (EditText)findViewById (R.id.edtEmail);
			edtEmail.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          ContulMeu.tooltip.setVisibility (View.VISIBLE);
			          ContulMeu.tvTooltip.setText (getParent().getResources().getString(R.string.i247));
			        }else{
			        		ContulMeu.tvTooltip.setText ("");
			        	 ContulMeu.tooltip.setVisibility (View.GONE); 
			        	 edtEmail.setText(YTOUtils.replaceInEmail(edtEmail.getText().toString()));
			        }
			    }
			});
			
			edtEmail.addTextChangedListener(new TextWatcher() {
	        	 
	            @Override
	            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	                // When user changed the Text
	            }
	 
	            @Override
	            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                    int arg3) {
	                // TODO Auto-generated method stub
	 
	            }
	 
	            @Override
	            public void afterTextChanged(Editable arg0) {
			    	if (!YTOUtils.eMailValidation(edtEmail.getText().toString()))
		        		 ((ImageView) findViewById(R.id.wrong_textmail)).setVisibility(View.VISIBLE);
		        	 else ((ImageView) findViewById(R.id.wrong_textmail)).setVisibility(View.GONE);
	            }
	        });
			
			edtOperator = (EditText) findViewById(R.id.edtOperator);
			edtOperator.setFocusable(false);
			edtOperator.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					selectOperator();
				}
			});
			
			edtSerie = (EditText)findViewById (R.id.edtSerie);
			edtSerie.setOnFocusChangeListener(new OnFocusChangeListener()
			{
			    @Override
			    public void onFocusChange(View v, boolean hasFocus) 
			    {
			        if (hasFocus == true)
			        {
			          ContulMeu.tooltip.setVisibility (View.VISIBLE);
			          ContulMeu.tvTooltip.setText (getParent().getResources().getString(R.string.i251));
			          if (!YTOUtils.isTablet(getParent().getParent()))
			        	  ContulMeu.tvTooltip.setTextSize(12);
			        }else{
			        	if (!YTOUtils.isTablet(getParent().getParent()))
				        	  ContulMeu.tvTooltip.setTextSize(14);
			        		ContulMeu.tvTooltip.setText ("");
			        	 ContulMeu.tooltip.setVisibility (View.GONE); 
			        }
			    }
			});
			
			load();
			
			btn_renunta = (Button) findViewById (R.id.renunta);
			btn_renunta.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					salveaza=false;
					InputMethodManager imm = (InputMethodManager)getSystemService(
						      Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(edtNume.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtCnp.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtAdresa.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtTelefon.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtEmail.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(edtSerie.getWindowToken(), 0);
						finish();
					
				}
			});
			
			btn_salveaza = (Button) findViewById (R.id.salveaza);
			btn_salveaza.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					salveaza=false;//daca salveaza=true salvez si la onPause()
					AsyncTask<Object, Object, Object> saveContactTask = 
		                   new AsyncTask<Object, Object, Object>() 
		                   {
		                      @Override
		                      protected Object doInBackground(Object... params) 
		                      {

		                         saveContact();
		                         return null;
		                      }
		          
		                      @Override
		                      protected void onPostExecute(Object result) 
		                      {
		                         finish();
		                      }
		                   }; 
		                  
		                saveContactTask.execute((Object[]) null); 
		                InputMethodManager imm = (InputMethodManager) 
		    			        getSystemService(Context.INPUT_METHOD_SERVICE);
		    			    imm.hideSoftInputFromWindow(edtTelefon.getWindowToken(), 0);
					}
			});
			
			Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
			
			edtNume.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtCnp.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtJudLoc.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtAdresa.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtTelefon.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtEmail.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtOperator.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtSerie.setTypeface(Typeface.create(tf,Typeface.BOLD));
			edtOperator.setText(sett.getOperaor()!=null? sett.getOperaor():"");

		}

		
		public void onResume()
		{
			super.onResume();
			if (Lista.tipDate.equals("localitati")) {edtJudLoc.setText(Lista.date);
			try{
    		judet = Lista.date.substring(0, Lista.date.indexOf(','));
    		localitate = Lista.date.substring(Lista.date.indexOf(',')+ 1, Lista.date.length());
			}catch (Exception e) {
				// TODO: handle exception
			}
    		Lista.tipDate="";
			}
    		Lista.date="";
    		salveaza=true;
		}
		
		public void onPause ()
		{
			super.onPause();
			if (salveaza)
				saveContact();
		}
		
		@Override
		public void onBackPressed(){
			
			super.onBackPressed();
		}

		
		
		 public void saveContact() 
		   { 
			 if (shouldSave ()){
			   persoanaFiz.nume = edtNume.getText().toString();
			   persoanaFiz.codUnic = edtCnp.getText().toString();
			   persoanaFiz.judet = judet;
			   persoanaFiz.localitate = localitate;
			   persoanaFiz.adresa = edtAdresa.getText().toString();
			   sett.updateStrada(persoanaFiz.adresa);
			   persoanaFiz.telefon = edtTelefon.getText().toString();
			   sett.updateTelefonComanda(persoanaFiz.telefon);
			   persoanaFiz.email = edtEmail.getText().toString();
			   persoanaFiz.email=YTOUtils.replaceInEmail (persoanaFiz.email);
			   persoanaFiz.serieAct = edtSerie.getText().toString();
			   if (!persoanaFiz.email.equals(""))sett.updateEmailContact(persoanaFiz.email);
			   persoanaFiz.tipPersoana = "fizica";
			   persoanaFiz.proprietar = "da";
			   String toJSON = persoanaFiz.persoanaToJSON (persoanaFiz);
		      DatabaseConnector dbConnector = new DatabaseConnector(this);
		      if (persoanaFiz.isDirty)
		      {
		    	  dbConnector.updateObiectAsigurat(persoanaFiz.idIntern,
			        		 "1",
			        		 toJSON);
				     if (AltePersoane.persoanaAsigurata.isDirty)
				    	 if (AltePersoane.persoanaAsigurata.idIntern.equals(persoanaFiz.idIntern));
				     {
				    	 persoanaFiz = persoanaFiz.persoanaFromJSON(persoanaFiz, toJSON);
				    	 AltePersoane.persoanaAsigurata = new YTOPersoana();
				    	 AltePersoane.persoanaAsigurata = persoanaFiz;
				     }
				     
		      }
		      else {
		    	  persoanaFiz.isDirty=true;
		    	  persoanaFiz.idIntern = UUID.randomUUID().toString();
		    	  dbConnector.insertObiectAsigurat(persoanaFiz.idIntern,
		    			  "1",
		    			  toJSON);
		    	  if (AltePersoane.persoanaAsigurata.isDirty)
				    	 if (AltePersoane.persoanaAsigurata.idIntern.equals(persoanaFiz.idIntern));
				     {
				    	 persoanaFiz = persoanaFiz.persoanaFromJSON(persoanaFiz, toJSON);
				    	 AltePersoane.persoanaAsigurata = new YTOPersoana();
				    	 AltePersoane.persoanaAsigurata = persoanaFiz;
				     }
		      }
		      new CreateAcountWebService(persoanaFiz,sett.getOperaor()).execute(null,null,null);
	    	  GetObiecte.getPersoane(dbConnector);
		      }
		   }
		 
		   private Boolean shouldSave ()
		   {
			   Boolean saveIt=false;
			   if (!edtNume.getText().toString().equals("")) saveIt=true;
			   if (!edtCnp.getText().toString().equals("")) saveIt=true;
			   if (!edtJudLoc.getText().toString().equals("") && !edtJudLoc.getText().toString().equals(",")) saveIt=true;
			   if (!edtAdresa.getText().toString().equals("")) saveIt=true;
			   if (!edtTelefon.getText().toString().equals("")) saveIt=true;
			   if (!edtEmail.getText().toString().equals("")) saveIt=true;
			   if (!edtSerie.getText().toString().equals("")) saveIt=true;
			   return saveIt;
		   }
		 
		 private void load()
		 {
			 if (GetObiecte.persoane!=null && GetObiecte.persoane.size()!=0)
			 {
				   if (GetObiecte.persoanaFizica.isDirty)
				   {
					   	persoanaFiz=GetObiecte.persoanaFizica;
					   	edtNume.setText(persoanaFiz.nume);
						edtCnp.setText(persoanaFiz.codUnic);
						if (!persoanaFiz.judet.equals("")) 
						{
							edtJudLoc.setText(persoanaFiz.judet+","+persoanaFiz.localitate);
							judet=persoanaFiz.judet;
						   	localitate=persoanaFiz.localitate;
						}
						edtAdresa.setText(persoanaFiz.adresa);
						edtTelefon.setText((persoanaFiz.telefon.equals("") ? sett.getTelefonComanda():persoanaFiz.telefon));
						edtEmail.setText((persoanaFiz.email.equals("") ? sett.getEmailContact():persoanaFiz.email));
						edtSerie.setText(persoanaFiz.serieAct);
						if (edtCnp.getText().toString().length()!=13 && edtCnp.getText().toString().length()!=0)
			        		 ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.VISIBLE);
			        	 else ((ImageView) findViewById(R.id.wrong_text)).setVisibility(View.GONE);
						if (!YTOUtils.eMailValidation(edtEmail.getText().toString()))
			        		 ((ImageView) findViewById(R.id.wrong_textmail)).setVisibility(View.VISIBLE);
			        	 else ((ImageView) findViewById(R.id.wrong_textmail)).setVisibility(View.GONE);
					}
				   else
					{
					   edtEmail.setText(sett.getEmailContact());
					   edtTelefon.setText(sett.getTelefonComanda());
					}
			}
			
		}

		  private void selectOperator ()
		  {
		 		dialog=new Dialog(getParent().getParent());
		 	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 	     dialog.setContentView(R.layout.dialog_operator_mic);
		 	     dialog.setCancelable(false);
//		 	     if (YTOUtils.isTablet(this))
//		 	    	 dialog.getWindow().setLayout(320, 450);
		 	     Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
		 	     TextView titlu = (TextView) dialog.findViewById(R.id.text_titlu);
		 	     TextView second = (TextView) dialog.findViewById(R.id.text_second);
		 	     titlu.setTypeface(Typeface.create(tf,Typeface.NORMAL));
		 	     second.setTypeface(Typeface.create(tf,Typeface.NORMAL));
		 	     //alte texte pentru despre mine meniu
		 	     
		 	     second.setText(getParent().getResources().getString(R.string.i249));
		 	     
		 	     RadioButton radioVdf = (RadioButton) dialog.findViewById(R.id.radio_vdf);
		 	     RadioButton radioOrg = (RadioButton) dialog.findViewById(R.id.radio_org);
		 	     RadioButton radioCosmo = (RadioButton) dialog.findViewById(R.id.radio_cosmo);
		 	     
		 	     //radioVdf.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.op,0, 0);
		 	     
		 	     operator = sett.getOperaor();
		 	     if (!operator.equals(""))
		 	     {
		 	    	 if (operator.equals("Vodafone"))
		 	    	 {
		 	    		 radioVdf.setChecked(true);
		 	    	 }else if (operator.equals("Orange"))
		 	    	 {
		 	    		 radioOrg.setChecked(true);
		 	    	 }else if (operator.equals("Cosmote"))
		 	    	 {
		 	    		 radioCosmo.setChecked(true);
		 	    	 }
		 	     }
		 	     
		 	     final RadioGroup radioOperaor = (RadioGroup) dialog.findViewById(R.id.radio_operator);
		 	     
		 	     Button okButton = (Button) dialog.findViewById(R.id.ok_button_operator);
		 	     okButton.setOnClickListener(new OnClickListener() {
		 			
		 			@Override
		 			public void onClick(View v) {
		 				// TODO Auto-generated method stub
		 				 int radioChecked = radioOperaor.getCheckedRadioButtonId();
		 				 RadioButton radio = (RadioButton) dialog.findViewById(radioChecked);
		 				 if (radio!=null)
		 					 operator = radio.getText().toString();
		 				 sett.updateOperaor(operator);
		 				 edtOperator.setText(operator);
		 				 dialog.dismiss();
		 			}
		 		});
		 	   dialog.show();
		  }  

		 
	}


		
	
