package com.stern.Asigurare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class SumarComandaRCA extends Activity{
	public static int durata;
	
	TextView tvMarca,tvSerieSas,tvNrInmat;
	TextView tvNumeAsig,tvCnpASig,tvJudLocAsig;
	TextView tvPretAsig,tvDurata,tvBonusMalus;
	Button btnContinua;
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
        setContentView(R.layout.sumar_rca);
        
        MainController.tvTitlu.setText(getString(R.string.i810));
        
        sett= AppSettings.get(this);
        tvMarca = (TextView) findViewById (R.id.marca_model_masina);
        tvSerieSas = (TextView) findViewById (R.id.serie_sasiu);
        tvNrInmat = (TextView) findViewById (R.id.nr_inmatriculare);
        
        tvNumeAsig = (TextView) findViewById (R.id.nume_asigurat);
        tvCnpASig = (TextView) findViewById (R.id.cnp_asigurat);
        tvJudLocAsig = (TextView) findViewById (R.id.judloc_asigurat);
        
        tvPretAsig = (TextView) findViewById (R.id.pret_asigurare);
        tvDurata = (TextView) findViewById (R.id.durata_asig);
        tvBonusMalus = (TextView) findViewById(R.id.bonus_malus);
        
        imgFirmaAsig = (ImageView) findViewById (R.id.image_firma_asig);
        
        btnContinua = (Button) findViewById (R.id.btn_continua);
        
		if (sett.getLanguage().equals("hu"))
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_rca_hu);
        } else if (sett.getLanguage().equals("en"))
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_rca_en);
        }else 
        {
        	((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.asig_rca);
        }
        
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
        btnContinua.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent finalCom = new Intent(getParent(), FinalizareComandaRCA.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("FinalizareComanda", finalCom);
			}
		});
        
        loadElementsByPosition();
        
    }
	
	private void loadElementsByPosition()
	{
		sett.updateTipPlata("RCA");
		
		tvMarca.setText(MasinileMele.autovehiculCurent.marcaAuto+","+MasinileMele.autovehiculCurent.modelAuto);
		tvSerieSas.setText (MasinileMele.autovehiculCurent.serieSasiu);
		tvNrInmat.setText (MasinileMele.autovehiculCurent.nrInmatriculare);
		
		tvNumeAsig.setText(AltePersoane.persoanaAsigurata.nume);
		tvCnpASig.setText (AltePersoane.persoanaAsigurata.codUnic);
		tvJudLocAsig.setText (AltePersoane.persoanaAsigurata.judet+","+AltePersoane.persoanaAsigurata.localitate);
		
		if (durata == 6)
		{
			tvPretAsig.setText (CallWebServiceRCA.prima6l.get(CalculatieRCA.positionId)+" lei");
			sett.updatePrima(CallWebServiceRCA.prima6l.get(CalculatieRCA.positionId)+"");
			sett.updateCompanie(CallWebServiceRCA.lista6l.get(CalculatieRCA.positionId));
			tvDurata.setText ("Durata 6 luni");
			tvBonusMalus.setText ("Bonus/Malus:"+CallWebServiceRCA.clasa6l.get(CalculatieRCA.positionId));
			String numeComp = CallWebServiceRCA.lista6l.get(CalculatieRCA.positionId).toLowerCase();
			int resID = getResources().getIdentifier(numeComp, "drawable", getPackageName());
			
				imgFirmaAsig.setImageResource(resID);
		}
		else if (durata == 12)
		{
			tvPretAsig.setText (CallWebServiceRCA.prima12l.get(CalculatieRCA.positionId)+"");
			sett.updatePrima(CallWebServiceRCA.prima12l.get(CalculatieRCA.positionId)+"");
			sett.updateCompanie(CallWebServiceRCA.lista12l.get(CalculatieRCA.positionId));
			tvDurata.setText ("Durata 12 luni");
			tvBonusMalus.setText ("Bonus/Malus:"+CallWebServiceRCA.clasa12l.get(CalculatieRCA.positionId));
			String numeComp = CallWebServiceRCA.lista12l.get(CalculatieRCA.positionId).toLowerCase();
			int resID = getResources().getIdentifier(numeComp, "drawable", getPackageName());
			
				imgFirmaAsig.setImageResource(resID);
		}
	}

}