package com.stern.Asigurare;

import java.util.ArrayList;

public class YTOObiectAsigurat {
	String IdIntern;
	String TipObiect;
	String JSONText;

public YTOObiectAsigurat (String idIntern,String tipObiect, String jSONText) {
	this.IdIntern = idIntern;
	this.TipObiect = tipObiect;
	this.JSONText = jSONText;
}

public static ArrayList<String> getIdIntern(ArrayList<YTOObiectAsigurat> vec) {
	ArrayList<String> ids = new ArrayList<String>();
	for (YTOObiectAsigurat item : vec) {
		ids.add(item.IdIntern);
	}
	return ids;
}

public static ArrayList<String> getTipObiect(ArrayList<YTOObiectAsigurat> vec) {
	ArrayList<String> tipObject = new ArrayList<String>();
	for (YTOObiectAsigurat item : vec) {
		tipObject.add(item.TipObiect);
	}
	return tipObject;
}

public static ArrayList<String> getJSONText(ArrayList<YTOObiectAsigurat> vec) {
	ArrayList<String> json = new ArrayList<String>();
	for (YTOObiectAsigurat item : vec) {
		json.add(item.JSONText);
	}
	return json;
}
}
