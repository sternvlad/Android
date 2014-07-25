package com.stern.Asigurare;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

public class YTOPersoana {
	String idIntern;
	String nume;
	String codUnic;
	String judet;
	String localitate;
	String adresa;
	String tipPersoana;
	String casatorit;
	String copiiMinori;
	String pensionar;
	String nrBugetari;
	String dataPermis;
	String codCaen;
	String handicapLocomotor;
	String serieAct;
	String elevStudent;
	String boliNeuro;
	String boliCardio;
	String boliInterne;
	String boliAparatRespirator;
	String alteBoli;
	String boliDefinitive;
	String stareSanatate;
	String telefon;
	String email;
	String proprietar;
	Boolean isDirty;

	public YTOPersoana()
	{
		this.idIntern="";
		this.nume="";
		this.codUnic="";
		this.judet="";
		this.localitate="";
		this.adresa="";
		this.tipPersoana="fizica";
		this.casatorit="nu";
		this.copiiMinori="nu";
		this.pensionar="nu";
		this.nrBugetari="0";
		this.dataPermis="";
		this.codCaen="";
		this.handicapLocomotor="nu";
		this.serieAct="";
		this.elevStudent="nu";
		this.boliNeuro="nu";
		this.boliCardio="nu";
		this.boliInterne="nu";
		this.boliAparatRespirator="nu";
		this.alteBoli="nu";
		this.boliDefinitive="nu";
		this.stareSanatate="buna";
		this.telefon="";
		this.email="";
		this.proprietar="";
		this.isDirty=false;
	}
	
	

	public String persoanaToJSON (YTOPersoana persoana)
	{
		JSONObject object = new JSONObject();
		try {
			object.put("nume_asigurat",(persoana.nume !=null ? persoana.nume : ""));
			object.put("cod_unic",(persoana.codUnic!=null ? persoana.codUnic : ""));
			object.put("judet",(persoana.judet!=null ? persoana.judet : ""));
			object.put("localitate",(persoana.localitate!=null ? persoana.localitate : ""));
			object.put("adresa",(persoana.adresa!=null ? persoana.adresa : ""));
			object.put("tip_persoana",(persoana.tipPersoana!=null ? persoana.tipPersoana : ""));
			object.put("casatorit",(persoana.casatorit!=null ? persoana.casatorit : ""));
			object.put("copii_minori",(persoana.copiiMinori!=null ? persoana.copiiMinori : ""));
			object.put("pensionar",(persoana.pensionar!=null ? persoana.pensionar : ""));
			object.put("nr_bugetari",(persoana.nrBugetari!=null ? persoana.nrBugetari : "0"));
			object.put("data_permis",(persoana.dataPermis!=null ? persoana.dataPermis : ""));
			object.put("cod_caen",(persoana.codCaen!=null ? persoana.codCaen : ""));
			object.put("handicap",(persoana.handicapLocomotor!=null ? persoana.handicapLocomotor : ""));
			object.put("serie_act",(persoana.serieAct!=null ? persoana.serieAct : ""));
			object.put("elev",(persoana.elevStudent != null ? persoana.elevStudent : ""));
			object.put("boli_neuro",(persoana.boliNeuro!=null ? persoana.boliNeuro :""));
			object.put("boli_cardio",(persoana.boliCardio!=null ? persoana.boliCardio:""));
			object.put("boli_interne",(persoana.boliInterne!=null ? persoana.boliInterne : ""));
			object.put("boli_aparat_respirator", (persoana.boliAparatRespirator!=null ? persoana.boliAparatRespirator : ""));
			object.put("alte_boli", (persoana.alteBoli!=null ? persoana.alteBoli : ""));
			object.put("boli_definitive", (persoana.boliDefinitive!=null ? persoana.boliDefinitive : ""));
			object.put("stare_sanatate", (persoana.stareSanatate!=null ? persoana.stareSanatate : ""));
			object.put("telefon", (persoana.telefon!=null ? persoana.telefon : ""));
			object.put("email", (persoana.email!=null ? persoana.email : ""));
			object.put("proprietar", (persoana.proprietar.equals("da") ? "da" : "nu"));
		} catch (JSONException e) {
	e.printStackTrace();
	}
		return object.toString();
	}

	public YTOPersoana persoanaFromJSON (YTOPersoana persoana, String JSONText)
	{
		JSONObject mainObject;
		try {
			mainObject = new JSONObject(JSONText);
			persoana.nume = mainObject.getString("nume_asigurat");
			persoana.codUnic = mainObject.getString("cod_unic");
			persoana.judet = mainObject.getString("judet");
			persoana.localitate = mainObject.getString("localitate");
			persoana.adresa = mainObject.getString("adresa");
			persoana.tipPersoana = mainObject.getString("tip_persoana");
			persoana.casatorit = mainObject.getString("casatorit");
			persoana.copiiMinori = mainObject.getString ("copii_minori");
			persoana.pensionar = mainObject.getString ("pensionar");
			persoana.nrBugetari = mainObject.getString("nr_bugetari");
			persoana.dataPermis = mainObject.getString("data_permis");
			persoana.codCaen = mainObject.getString ("cod_caen");
			persoana.handicapLocomotor = mainObject.getString ("handicap");
			persoana.serieAct = mainObject.getString("serie_act");
			persoana.elevStudent = mainObject.getString("elev");
			persoana.boliNeuro = mainObject.getString ("boli_neuro");
			persoana.boliCardio = mainObject.getString("boli_cardio");
			persoana.boliInterne = mainObject.getString("boli_interne");
			persoana.boliAparatRespirator = mainObject.getString("boli_aparat_respirator");
			persoana.alteBoli = mainObject.getString("alte_boli");
			persoana.boliDefinitive = mainObject.getString("boli_definitive");
			persoana.stareSanatate = mainObject.getString ("stare_sanatate");
			persoana.telefon = mainObject.getString ("telefon");
			persoana.email = mainObject.getString("email");
			persoana.proprietar = mainObject.getString ("proprietar");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return persoana;

	}
	
	public int getDataPermisMinByCnp()
	{
		int an =  Integer.parseInt(AltePersoane.persoanaAsigurata.codUnic.substring(1, 3));
		int year = Calendar.getInstance().get(Calendar.YEAR);
		year %=1000;
		year %=100;
		if (an<year) an=2000+an;
		else an=1900+an+18;
		return an;
		
	}
	
	public int getVarstaPersoana()
	{
		if (AltePersoane.persoanaAsigurata.codUnic.length()>=3)
		{
			int anNastere = Integer.parseInt(YTOPersoana.this.codUnic.substring(1, 3));
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int an = year - 2000;
			if (anNastere<an) anNastere=2000+anNastere;
			else anNastere=1900+anNastere;
			return year-anNastere;
		}else return -1;
	}
	
	public String toJSONCalator ()
	{
		JSONObject object = new JSONObject();
		try {
			object.put("stare_sanatate",("buna"));
			object.put("alte_boli",(YTOPersoana.this.alteBoli));
			object.put("boli_interne",(YTOPersoana.this.boliInterne));
			object.put("boli_neuro",(YTOPersoana.this.boliNeuro));
			object.put("grad_invaliditate",(YTOPersoana.this.handicapLocomotor));
			object.put("boli_definitive",(YTOPersoana.this.boliDefinitive));
			object.put("boli_aparat_respirator",(YTOPersoana.this.boliAparatRespirator));
			object.put("boli_cardio",(YTOPersoana.this.boliCardio));
			if (YTOPersoana.this.codUnic.length()>3)
				object.put("varsta",(YTOPersoana.this.getVarstaPersoana()+""));
			else object.put("varsta",("28"));
			object.put("elev",(YTOPersoana.this.elevStudent));
			object.put("sport_agrement",("nu"));
			object.put("sa_bagaje",("0"));
			object.put("sa_echipament",("0"));
			object.put("nume_asigurat",(YTOPersoana.this.nume));
			object.put("cod_unic",(YTOPersoana.this.codUnic));
			object.put("adresa",(YTOPersoana.this.adresa));
			object.put("id_intern",(YTOPersoana.this.idIntern));
			object.put("judet",(YTOPersoana.this.judet));
			object.put("localitate",(YTOPersoana.this.localitate));
			object.put("pasaport_ci",(YTOPersoana.this.serieAct));
		} catch (JSONException e) {
	e.printStackTrace();
	}
		return object.toString();
	}
	
	public YTOPersoana getPersoanaByIdIntern(String idIntern)
	{
		YTOPersoana persoana = new YTOPersoana();
		for (int i=0;i<GetObiecte.persoane.size();i++)
			if (GetObiecte.persoane.get(i).equals(idIntern))
					return persoana;
		return null;
	}
	
	public boolean isValidForComputeCalatorie ()
	{
		boolean valid=true;
		
		 if (YTOPersoana.this.nume.length() == 0)
		        valid = false;
		    if (YTOPersoana.this.codUnic.length() == 0)
		        valid = false;
		    if (YTOPersoana.this.judet.length() == 0)
		        valid = false;
		    if (YTOPersoana.this.localitate.length() == 0)
		        valid = false;
		    if (YTOPersoana.this.tipPersoana.equals("fizica") && YTOPersoana.this.codUnic.length()!=13)
		    	valid = false;
		    if (YTOPersoana.this.adresa.length() == 0)
		    	valid = false;
		    if (YTOPersoana.this.serieAct.length() == 0)
		    	valid = false;
		    return  valid;
	}
	
	public boolean isValidForCompute ()
	{
		boolean valid=true;
		
		 if (YTOPersoana.this.nume.length() == 0)
		        valid = false;
		    if (YTOPersoana.this.codUnic.length() == 0)
		        valid = false;
		    if (YTOPersoana.this.judet.length() == 0)
		        valid = false;
		    if (YTOPersoana.this.localitate.length() == 0)
		        valid = false;
		    if (YTOPersoana.this.tipPersoana.equals("fizica") && YTOPersoana.this.codUnic.length()!=13)
		    	valid = false;
		    return  valid;
	}
	
	
	
	
	
}