package com.stern.Asigurare;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class YTOAlerta {
 
	int idExtern;
	String idIntern;
	int tipAlerta;
	String dataAlerta;
	String esteRata;
	int numarTotalRate;
	int numarRata;
	String idObiect;
	String dataCreare;
	Date date;
	boolean isNew;


public String alertaToJSON ()
{
	JSONObject object = new JSONObject();
	try {
		object.put("id_extern",(YTOAlerta.this.idExtern !=-1 ? YTOAlerta.this.idExtern : "-1"));
		object.put("id_intern",(YTOAlerta.this.idIntern !=null ? YTOAlerta.this.idIntern : ""));
		object.put("tip_alerta",(YTOAlerta.this.tipAlerta !=-1 ? YTOAlerta.this.tipAlerta : "-1"));
		object.put("id_obiect",(YTOAlerta.this.idObiect !=null ? YTOAlerta.this.idObiect : ""));
		object.put("este_rata",(YTOAlerta.this.esteRata !=null ? YTOAlerta.this.esteRata : ""));
		object.put("numar_total_rate",(YTOAlerta.this.numarTotalRate !=-1 ? YTOAlerta.this.numarTotalRate : "-1"));
		object.put("data_creare",(YTOAlerta.this.dataCreare !=null ? YTOAlerta.this.dataCreare : ""));
		object.put("data_alerta",(YTOAlerta.this.dataAlerta !=null ? YTOAlerta.this.dataAlerta : ""));
	} catch (JSONException e) {
		e.printStackTrace();
		}
			return object.toString();
	}

public YTOAlerta alertaFromJSON (String JSONText)
{
	JSONObject mainObject;
	try {
		mainObject = new JSONObject(JSONText);
		YTOAlerta.this.idExtern =Integer.parseInt(mainObject.getString("id_extern"));
		YTOAlerta.this.idIntern = mainObject.getString("id_intern");
		YTOAlerta.this.tipAlerta = Integer.parseInt(mainObject.getString("tip_alerta"));
		YTOAlerta.this.idObiect = mainObject.getString("id_obiect");
		YTOAlerta.this.esteRata = mainObject.getString("este_rata");
		YTOAlerta.this.numarTotalRate = Integer.parseInt(mainObject.getString("numar_total_rate"));
		YTOAlerta.this.dataCreare = mainObject.getString("data_creare");
		YTOAlerta.this.dataAlerta = mainObject.getString("data_alerta");
	} catch (JSONException e) {
		e.printStackTrace();
	}
	return YTOAlerta.this;

}


}