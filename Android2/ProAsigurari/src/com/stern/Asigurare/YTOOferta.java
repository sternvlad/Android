package com.stern.Asigurare;

import org.json.JSONException;
import org.json.JSONObject;

public class YTOOferta {
	Double prima;
	String cod_oferta;
	String nume_companie;
	int id_companie;
	String img;
	String clasa_bm;
	int idExtern;
	String idIntern;
	int status;
	String tipAsigurare;
	String numeAsigurare;
	String idAsigurat;
	String obiecteAsigurate;
	String detaliiAsigurare;
	String companie;
	String moneda;
	String dataInceput;
	String dataSfarsit;
	int durataAsigurare;
	
	public Double getPrima() {
		return prima;
	}
	public void setPrima(Double value) {
		this.prima = value;
	}
	
	public String getCod() {
		return cod_oferta;
	}
	public void setCod(String value) {
		this.cod_oferta = value;
	}
	
	public String getNume() {
		return nume_companie;
	}
	public void setNume(String value) {
		this.nume_companie = value;
	}
	
	public int getId() {
		return id_companie;
	}
	public void setId(int value) {
		this.id_companie = value;
	}
	
	public String getImg() {
		return img;
	}
	public void setImg(String value) {
		this.img = value;
	}
	
	public String getClasa() {
		return clasa_bm;
	}
	public void setClasa(String value) {
		this.clasa_bm = value;
	}
	
	public String ofertaToJSON ()
	{
		JSONObject object = new JSONObject();
		try {
			object.put("id_extern",(YTOOferta.this.idExtern!=-1 ? YTOOferta.this.idExtern : -1));
			object.put("id_intern",(YTOOferta.this.idIntern !=null ? YTOOferta.this.idIntern : ""));
			object.put("status",(YTOOferta.this.status !=-1 ? YTOOferta.this.status : "-1"));
			object.put("tip_asigurare",(YTOOferta.this.tipAsigurare !=null ? YTOOferta.this.tipAsigurare : ""));
			object.put("nume_asigurare",(YTOOferta.this.numeAsigurare !=null ? YTOOferta.this.numeAsigurare : ""));
			object.put("id_asigurat",(YTOOferta.this.idAsigurat !=null ? YTOOferta.this.idAsigurat : ""));
			object.put("obiecte_asigurate",(YTOOferta.this.obiecteAsigurate !=null ? YTOOferta.this.obiecteAsigurate : ""));
			object.put("detalii_asigurare",(YTOOferta.this.detaliiAsigurare !=null ? YTOOferta.this.detaliiAsigurare : ""));
			object.put("companie",(YTOOferta.this.companie !=null ? YTOOferta.this.companie : ""));
			object.put("cod_oferta",(YTOOferta.this.cod_oferta !=null ? YTOOferta.this.cod_oferta : ""));
			object.put("prima",(YTOOferta.this.prima !=0.0 ? YTOOferta.this.prima : "0.0"));
			object.put("moneda",(YTOOferta.this.moneda !=null ? YTOOferta.this.moneda : ""));
			object.put("data_inceput",(YTOOferta.this.dataInceput !=null ? YTOOferta.this.dataInceput : ""));
			object.put("data_sfarsit",(YTOOferta.this.dataSfarsit !=null ? YTOOferta.this.dataSfarsit : ""));
			object.put("durata_asigurare",(YTOOferta.this.durataAsigurare !=-1 ? YTOOferta.this.durataAsigurare : "-1"));
		} catch (JSONException e) {
			e.printStackTrace();
			}
	return object.toString();		
}
	
	public YTOOferta ofertaFromJSON (String JSONText)
	{
		JSONObject mainObject;
		try {
			mainObject = new JSONObject(JSONText);
			YTOOferta.this.idExtern = Integer.parseInt(mainObject.getString("id_extern"));
			YTOOferta.this.idIntern = mainObject.getString("id_intern");
			YTOOferta.this.status = Integer.parseInt(mainObject.getString("status"));
			YTOOferta.this.tipAsigurare = mainObject.getString("tip_asigurare");
			YTOOferta.this.numeAsigurare = mainObject.getString("nume_asigurare");
			YTOOferta.this.idAsigurat = mainObject.getString("id_asigurat");
			YTOOferta.this.obiecteAsigurate = mainObject.getString("obiecte_asigurate");
			YTOOferta.this.detaliiAsigurare = mainObject.getString("detalii_asigurare");
			YTOOferta.this.companie =  mainObject.getString("companie");
			YTOOferta.this.cod_oferta = mainObject.getString("cod_oferta");
			YTOOferta.this.prima = Double.parseDouble(mainObject.getString("prima"));
			YTOOferta.this.moneda = mainObject.getString("moneda");
			YTOOferta.this.dataInceput = mainObject.getString("data_inceput");
			YTOOferta.this.dataSfarsit = mainObject.getString("data_sfarsit");
			YTOOferta.this.durataAsigurare = Integer.parseInt(mainObject.getString("durata_asigurare"));
	} catch (JSONException e) {
		e.printStackTrace();
	}
	return YTOOferta.this;
	}
}