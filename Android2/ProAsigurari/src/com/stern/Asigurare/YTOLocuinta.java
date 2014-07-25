package com.stern.Asigurare;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

public class YTOLocuinta {
	
	String idIntern;
	String tipLocuinta;
	String structuraLocuinta;
	int    regimInaltime;
	int    etaj;
	int    anConstructie;
	int    nrCamere;
	int    suprafataUtila;
	int    nrLocatari;
	String tipGeam;
	String areAlarma;
	String areGrilajeGeam;
	String zonaIzolata;
	String clauzaFurtBunuri;
	String clauzaApaConducta;
	String detectieIncendiu;
	String arePaza;
	String areTeren;
	String locuitPermanent;
	String judet;
	String localitate;
	String adresa;
	String modEvaluare;
	int    nrRate;
	int    sumaAsigurata;
	int    sumaAsigurataRC;
	String idProprietar;
	String _dataCreare;
	Boolean cesiune;
	String mentiuneCesiune;
	String cuiBanca;
	Boolean isDirty;

public YTOLocuinta()
{
	this.idIntern="";
	this.tipLocuinta="";
	this.structuraLocuinta="";
	this.regimInaltime=-1;
	this.etaj=-1;
	this.anConstructie=-1;
	this.nrCamere=-1;
	this.suprafataUtila=-1;
	this.nrLocatari=-1;
	this.tipGeam="nu";
	this.areAlarma="nu";
	this.areGrilajeGeam="nu";
	this.zonaIzolata="nu";
	this.clauzaFurtBunuri="nu";
	this.clauzaApaConducta="nu";
	this.detectieIncendiu="nu";
	this.arePaza="nu";
	this.areTeren="nu";
	this.locuitPermanent="da";
	this.judet="";
	this.localitate="";
	this.adresa="";
	this.modEvaluare="";
	this.nrRate=-1;
	this.sumaAsigurata=-1;
	this.sumaAsigurataRC=-1;
	this.idProprietar="";
	this._dataCreare="";
	this.cesiune=false;
	this.mentiuneCesiune = "";
	this.cuiBanca="";
	this.isDirty=false;
}

public String locuintaToJSON (YTOLocuinta locuinta)
{
	JSONObject object = new JSONObject();
	try {
		object.put("tip_locuinta",(locuinta.tipLocuinta !=null ? locuinta.tipLocuinta : ""));
		object.put("structura_locuinta",(locuinta.structuraLocuinta !=null ? locuinta.structuraLocuinta : ""));
		object.put("regim_inaltime",(locuinta.regimInaltime!=-1 ? locuinta.regimInaltime : "-1"));
		object.put("etaj",(locuinta.etaj !=-1 ? locuinta.etaj : "-1"));
		object.put("an_constructie",(locuinta.anConstructie !=-1 ? locuinta.anConstructie : "-1"));
		object.put("nr_camere",(locuinta.nrCamere !=-1 ? locuinta.nrCamere : "-1"));
		object.put("suprafata_utila",(locuinta.suprafataUtila !=-1 ? locuinta.suprafataUtila : "-1"));
		object.put("nr_locatari",(locuinta.nrLocatari !=-1 ? locuinta.nrLocatari : "-1"));
		object.put("tip_geam",(locuinta.tipGeam !=null ? locuinta.tipGeam : ""));
		object.put("are_alarma",(locuinta.areAlarma !=null ? locuinta.areAlarma : ""));
		object.put("are_grilaje_geam",(locuinta.areGrilajeGeam !=null ? locuinta.areGrilajeGeam : ""));
		object.put("zona_izolata",(locuinta.zonaIzolata !=null ? locuinta.zonaIzolata : ""));
		object.put("clauza_furt_bunuri",(locuinta.clauzaFurtBunuri !=null ? locuinta.clauzaFurtBunuri : ""));
		object.put("clauza_apa_conducta",(locuinta.clauzaApaConducta !=null ? locuinta.clauzaApaConducta : ""));
		object.put("detectie_incendiu",(locuinta.detectieIncendiu !=null ? locuinta.detectieIncendiu : ""));
		object.put("are_paza",(locuinta.arePaza !=null ? locuinta.arePaza : ""));
		object.put("are_teren",(locuinta.areTeren !=null ? locuinta.areTeren : ""));
		object.put("locuit_permanent",(locuinta.locuitPermanent !=null ? locuinta.locuitPermanent : ""));
		object.put("judet",(locuinta.judet !=null ? locuinta.judet : ""));
		object.put("localitate",(locuinta.localitate !=null ? locuinta.localitate : ""));
		object.put("adresa",(locuinta.adresa !=null ? locuinta.adresa : ""));
		object.put("mod_evaluare",(locuinta.modEvaluare !=null ? locuinta.modEvaluare : ""));
		object.put("nr_rate",(locuinta.nrRate !=-1 ? locuinta.nrRate : "-1"));
		object.put("suma_asigurata",(locuinta.sumaAsigurata !=-1 ? locuinta.sumaAsigurata : "-1"));
		object.put("suma_asigurata_rc",(locuinta.sumaAsigurataRC !=-1 ? locuinta.sumaAsigurataRC : "-1"));
		object.put("id_proprietar",(locuinta.idProprietar !=null ? locuinta.idProprietar : ""));
		object.put("data_creare",(locuinta._dataCreare !=null ? locuinta._dataCreare : ""));
		object.put("mentiune_cesiune",(locuinta.mentiuneCesiune !=null ? locuinta.mentiuneCesiune : ""));
		object.put("cui_banca",(locuinta.cuiBanca !=null ? locuinta.cuiBanca : ""));
	} catch (JSONException e) {
e.printStackTrace();
}
	return object.toString();
}

public YTOLocuinta locuintaFromJSON (YTOLocuinta locuinta, String JSONText)
{
	JSONObject mainObject;
	try {
		mainObject = new JSONObject(JSONText);
		locuinta.tipLocuinta = mainObject.getString("tip_locuinta");
		locuinta.structuraLocuinta = mainObject.getString("structura_locuinta");
		locuinta.regimInaltime = Integer.parseInt(mainObject.getString("regim_inaltime"));
		locuinta.etaj = Integer.parseInt(mainObject.getString("etaj"));
		locuinta.anConstructie = Integer.parseInt(mainObject.getString("an_constructie"));
		locuinta.nrCamere = Integer.parseInt(mainObject.getString("nr_camere"));
		locuinta.suprafataUtila = Integer.parseInt(mainObject.getString("suprafata_utila"));
		locuinta.nrLocatari = Integer.parseInt(mainObject.getString("nr_locatari"));
		locuinta.tipGeam = mainObject.getString("tip_geam");
		locuinta.areAlarma = mainObject.getString("are_alarma");
		locuinta.areGrilajeGeam = mainObject.getString("are_grilaje_geam");
		locuinta.zonaIzolata = mainObject.getString("zona_izolata");
		locuinta.clauzaFurtBunuri = mainObject.getString("clauza_furt_bunuri");
		locuinta.clauzaApaConducta = mainObject.getString("clauza_apa_conducta");
		locuinta.detectieIncendiu = mainObject.getString("detectie_incendiu");
		locuinta.arePaza = mainObject.getString("are_paza");
		locuinta.areTeren = mainObject.getString("are_teren");
		locuinta.locuitPermanent = mainObject.getString("locuit_permanent");
		locuinta.judet = mainObject.getString("judet");
		locuinta.localitate = mainObject.getString("localitate");
		locuinta.adresa = mainObject.getString("adresa");
		locuinta.modEvaluare = mainObject.getString("mod_evaluare");
		locuinta.nrRate = Integer.parseInt(mainObject.getString("nr_rate"));
		locuinta.sumaAsigurata = Integer.parseInt(mainObject.getString("suma_asigurata"));
		locuinta.sumaAsigurataRC = Integer.parseInt (mainObject.getString("suma_asigurata_rc"));
		locuinta.idProprietar = mainObject.getString("id_proprietar");
		locuinta.cesiune = false;
		locuinta.cuiBanca = mainObject.getString ("cui_banca");
		locuinta.mentiuneCesiune = mainObject.getString ("mentiune_cesiune");
		locuinta._dataCreare = mainObject.getString ("data_creare");
		
	} catch (JSONException e) {
		e.printStackTrace();
	}
	return locuinta;

}

public Boolean isValidForLocuinta ()
{
	Boolean valid = true;
	if (YTOLocuinta.this.judet.length() == 0)
		valid = false;
	if (YTOLocuinta.this.localitate.length() == 0)
		valid = false;
	if (YTOLocuinta.this.tipLocuinta.length() == 0)
		valid = false;
	if (YTOLocuinta.this.structuraLocuinta.length() == 0)
		valid = false;
	if (YTOLocuinta.this.regimInaltime <0)
		valid = false;
	if (YTOLocuinta.this.etaj>YTOLocuinta.this.regimInaltime)
		valid = false;
	if (YTOLocuinta.this.tipLocuinta.equals("apartament-in-bloc"))
		if (YTOLocuinta.this.etaj <0)
			valid = false;
	if (YTOLocuinta.this.anConstructie <=0)
		valid = false;
	if (YTOLocuinta.this.anConstructie<1900 || YTOLocuinta.this.anConstructie>Calendar.getInstance().get(Calendar.YEAR))
		valid=false;
	if (YTOLocuinta.this.nrCamere <= 0)
		valid = false;
	if (YTOLocuinta.this.suprafataUtila <=0)
		valid = false;
	if (YTOLocuinta.this.nrLocatari <= 0)
		valid=false;
	
	return valid;
}

public float completedPercent ()
{
	int numarCampuri=10;
	float campuriCompletate = 0;
	
	if (YTOLocuinta.this.judet.length()>0)
		campuriCompletate++;
	if (YTOLocuinta.this.localitate.length()>0)
		campuriCompletate++;
	if (YTOLocuinta.this.adresa.length()>0)
		campuriCompletate++;
	if (YTOLocuinta.this.tipLocuinta.length()>0)
		campuriCompletate++;
	if (YTOLocuinta.this.structuraLocuinta.length()>0)
		campuriCompletate++;
	if (YTOLocuinta.this.regimInaltime > 0 || (YTOLocuinta.this.regimInaltime==0 && !YTOLocuinta.this.tipLocuinta.equals("apartament-in-bloc")))
		campuriCompletate++;
	if (YTOLocuinta.this.anConstructie > 0)
		campuriCompletate++;
	if (YTOLocuinta.this.nrCamere > 0)
		campuriCompletate++;
	if (YTOLocuinta.this.suprafataUtila > 0)
		campuriCompletate++;
	if (YTOLocuinta.this.nrLocatari > 0)
		campuriCompletate++;
	
	return campuriCompletate/numarCampuri;
}

public int completedFields ()
{
	int campuriCompletate = 0;
	
	if (YTOLocuinta.this.judet.length()>0)
		campuriCompletate++;
	if (YTOLocuinta.this.localitate.length()>0)
		campuriCompletate++;
	if (YTOLocuinta.this.adresa.length()>0)
		campuriCompletate++;
	if (YTOLocuinta.this.tipLocuinta.length()>0)
		campuriCompletate++;
	if (YTOLocuinta.this.structuraLocuinta.length()>0)
		campuriCompletate++;
	if (YTOLocuinta.this.regimInaltime > 0 || (YTOLocuinta.this.regimInaltime==0 && !YTOLocuinta.this.tipLocuinta.equals("apartament-in-bloc")))
		campuriCompletate++;
	if (YTOLocuinta.this.anConstructie > 0)
		campuriCompletate++;
	if (YTOLocuinta.this.nrCamere > 0)
		campuriCompletate++;
	if (YTOLocuinta.this.suprafataUtila > 0)
		campuriCompletate++;
	if (YTOLocuinta.this.nrLocatari > 0)
		campuriCompletate++;
	if (YTOLocuinta.this.etaj>=0)
		campuriCompletate ++;
	return campuriCompletate;
}

}

