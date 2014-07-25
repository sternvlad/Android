package com.stern.Asigurare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SumarComandaCalatorie extends Activity{
	public static int durata;
	
	TextView tvAsig,tvSumaAsig,tvSupStruc;
	TextView tvNumeAsig,tvCnpASig,tvJudLocAsig,textNrPersoane,textSerieCI;
	TextView tvPretAsig;
	ImageView headerSumar,icon,arrow1,arrow2,arrow3,arrow4;
	Button btnContinua,btnConditiiPdf,btnSumar;
	ImageView imgFirmaAsig;
	AppSettings sett;
    
	public void onCreate(Bundle savedInstanceState) {
//		Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//    	int width = display.getWidth(); 
    	Window window = getWindow(); 
//    	if (YTOUtils.isTablet(this)) {	    	
//	    	window.setGravity(Gravity.CENTER); 
//	    	window.setLayout(320, 480);
//    	}
    	window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sumar_locuinta);
        
        MainController.tvTitlu.setText(getString(R.string.i466));
        
        sett= AppSettings.get(this);

        
        icon = (ImageView) findViewById(R.id.image_icon);
        icon.setImageResource(R.drawable.icon_foto_person_xs);
        arrow1 = (ImageView) findViewById(R.id.image_arrow_locuinta1);
        arrow2 = (ImageView) findViewById(R.id.image_arrow_locuinta2);
        arrow3 = (ImageView) findViewById(R.id.image_arrow_locuinta3);
        arrow4 = (ImageView) findViewById(R.id.image_arrow_locuinta4);
        
        arrow1.setImageResource(R.drawable.arrow_calatorie);
        arrow2.setImageResource(R.drawable.arrow_calatorie);
        arrow3.setImageResource(R.drawable.arrow_calatorie);
        arrow4.setImageResource(R.drawable.arrow_calatorie);
        
        ((View) findViewById(R.id.view1)).setBackgroundColor(getResources().getColor(R.color.portocaliu_persoana));
        ((View) findViewById(R.id.view2)).setBackgroundColor(getResources().getColor(R.color.portocaliu_persoana));
        ((TextView) findViewById(R.id.text_header1)).setTextColor(getResources().getColor(R.color.portocaliu_persoana));
        
        if (sett.getLanguage().equals("hu"))
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_calatorie_hu);
        } else if (sett.getLanguage().equals("en"))
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_calatorie_en);
        }else 
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_calatorie);
        }
        
        LinearLayout lay1 = (LinearLayout) findViewById(R.id.nr_pers_calatorie);
        LinearLayout lay2 = (LinearLayout) findViewById(R.id.serie_calatorie); 
        lay1.setVisibility(View.VISIBLE);
        lay2.setVisibility(View.VISIBLE);
        tvAsig = (TextView) findViewById (R.id.tv_asig_loc);
        tvSumaAsig = (TextView) findViewById (R.id.suma_asig);
        tvSupStruc = (TextView) findViewById (R.id.tv_suprafata_structura);
        
        tvNumeAsig = (TextView) findViewById (R.id.nume_asigurat);
        tvCnpASig = (TextView) findViewById (R.id.cnp_asigurat);
        tvJudLocAsig = (TextView) findViewById (R.id.judloc_asigurat);
        
        tvPretAsig = (TextView) findViewById (R.id.pret_asigurare);
        
        imgFirmaAsig = (ImageView) findViewById (R.id.image_firma_asig);
        textNrPersoane = (TextView) findViewById(R.id.tv_numar_persoane);
        textSerieCI = (TextView) findViewById(R.id.text_serie);
        btnContinua = (Button) findViewById (R.id.btn_continua);
        btnContinua.setBackgroundResource(R.drawable.calculeaza_calatorie);
        btnContinua.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent finalCom = new Intent(getParent(), FinalizareComandaCalatorie.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("FinalizareComanda", finalCom);
			}
		});
        
        loadElementsByPosition();
        
		if (sett.getLanguage().equals("hu")&& !YTOUtils.isTablet(getParent().getParent()))
		{
			((TextView) findViewById(R.id.text_header1)).setTextSize(20);
			((TextView) findViewById(R.id.text_header2)).setTextSize(11);
			((TextView) findViewById(R.id.text_header3)).setTextSize(11);
		}else if (sett.getLanguage().equals("hu")&& YTOUtils.isTablet(getParent().getParent()))
		{
			((TextView) findViewById(R.id.text_header1)).setTextSize(45);
			((TextView) findViewById(R.id.text_header2)).setTextSize(25);
			((TextView) findViewById(R.id.text_header3)).setTextSize(25);
		}
    }
	
	
	
	private void loadElementsByPosition()
	{

		
		Typeface tf = Typeface.createFromAsset(getAssets(), "ArialRoundedMT.ttf");
		
		tvAsig.setTypeface(Typeface.create(tf,Typeface.BOLD));
		tvAsig.setText (sett.getTaraDestinatie()+" , "+LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getSumaAsigurata());
		String scop = "";
		if (sett.getScopCalatorie().equals("turism"))
			scop = getString(R.string.i65) ;
		else if (sett.getScopCalatorie().equals("afaceri"))
			scop = getString(R.string.i71) ;
		else if (sett.getScopCalatorie().equals("sofer-profesionist"))
			scop = getString(R.string.i81) ;
		else if (sett.getScopCalatorie().equals("studii"))
			scop = getString(R.string.i82) ;
		tvSumaAsig.setText (getString(R.string.i461)+" "+scop);
		tvSupStruc.setText (getString(R.string.i462)+ " " + sett.getNrZileCalatorie()+" "+getString(R.string.i468));
		
		tvNumeAsig.setText(AltePersoaneCalatorie.calatori.get(0).nume);
		tvNumeAsig.setTypeface(Typeface.create(tf,Typeface.BOLD));
		tvCnpASig.setText (AltePersoaneCalatorie.calatori.get(0).codUnic);
		tvJudLocAsig.setText (AltePersoaneCalatorie.calatori.get(0).judet+","+AltePersoaneCalatorie.calatori.get(0).localitate);
			
		if (sett.getOperaor().equals("Vodafone") && LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getReducere() &&  LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getIsRedusByVodafone() ){
			tvPretAsig.setText (LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getPrimaReducere()+ " " +LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getMoneda());
			
			sett.updatePrima(LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getPrimaReducere()+"");
		}else if ( LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getReducere() &&  !LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getIsRedusByVodafone() )
		{
			tvPretAsig.setText (LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getPrimaReducere()+ " " +LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getMoneda());
			
			sett.updatePrima(LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getPrimaReducere()+"");
		}
			else
		{
			tvPretAsig.setText (LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getPrima()+ " " +LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getMoneda());
			
			sett.updatePrima(LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getPrima()+"");
		}
		tvPretAsig.setTypeface(Typeface.create(tf,Typeface.BOLD));
		sett.updateCompanie(LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getCompanie());
		String numeComp = LoadingCalatorie.oferta.get(CalculatieCalatorie.positionId).getCompanie().toLowerCase();
		int resID = getResources().getIdentifier(numeComp, "drawable", getPackageName());
		textSerieCI.setText(AltePersoaneCalatorie.calatori.get(0).serieAct);
		imgFirmaAsig.setImageResource(resID);
		
		btnConditiiPdf =(Button) findViewById (R.id.btn_conditii_pdf);
		btnSumar = (Button) findViewById (R.id.btn_conditii_sumar);
		
		btnConditiiPdf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SumarAcopeririSiConditii.tip="calatorie";
				SumarAcopeririSiConditii.tipDate = "link";
				Intent sumarAc = new Intent(getParent(), SumarAcopeririSiConditii.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("SumarAcoperiri", sumarAc);
			}
		});
		
		btnSumar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SumarAcopeririSiConditii.tip="calatorie";
				SumarAcopeririSiConditii.tipDate = "sumar";
				Intent sumarAc = new Intent(getParent(), SumarAcopeririSiConditii.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("SumarAcoperiri", sumarAc);
				
			}
		});
		
	}
	
	public void onResume()
	{
		super.onResume();
		loadElementsByPosition();
		SumarAcopeririSiConditii.tipDate = "";
	}

}